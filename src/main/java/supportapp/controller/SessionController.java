package supportapp.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import supportapp.model.*;
import supportapp.repository.MessageRepository;
import supportapp.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final MessageRepository messageRepository;
    private final AgentService agentService;
    private final QueueServiceImpl queueService;
    private final ProductServiceImpl productService;

    //Shows session
    @GetMapping("/session/{id}")
    public String viewSession(@PathVariable Long id, Model model, HttpSession httpSession) {
        Optional<Session> sessionOpt = sessionService.getById(id);
        if (sessionOpt.isEmpty()) {
            return "redirect:/dashboard/customer";
        }

        Session session = sessionOpt.get();

        User loggedInUser = (User) httpSession.getAttribute("loggedInUser");

        // Authorization check
        boolean isAuthorized = false;

        if (loggedInUser != null) {
            if (loggedInUser.getCustomer() != null && session.getCustomer().getCustId().equals(loggedInUser.getCustomer().getCustId())) {
                isAuthorized = true;
            } else if (loggedInUser.getAgent() != null && session.getAgent().getAgId().equals(loggedInUser.getAgent().getAgId())) {
                isAuthorized = true;
            }
        }

        if (!isAuthorized) {
            return "redirect:/login";
        }


        model.addAttribute("supportSession", session);
        model.addAttribute("messages", messageRepository.findBySession_SessionIdOrderBySentAtAsc(id));
        model.addAttribute("isSessionEnded", session.getEndedAt() != null);
        return "sessions/chat";
    }

    //Sends typed message
    @PostMapping("/session/{id}/send")
    public String sendMessage(@PathVariable Long id,
                              @RequestParam String content,
                              HttpSession httpSession) {
        User sender = (User) httpSession.getAttribute("loggedInUser");
        Optional<Session> sOpt = sessionService.getById(id);

        if (sOpt.isEmpty() || sender == null) {
            return "redirect:/login";
        }

        Session session = sOpt.get();

        boolean isAuthorized = false;
        if (sender.getCustomer() != null &&
                session.getCustomer().getCustId().equals(sender.getCustomer().getCustId())) {
            isAuthorized = true;
        } else if (sender.getAgent() != null &&
                session.getAgent().getAgId().equals(sender.getAgent().getAgId())) {
            isAuthorized = true;
        }

        if (!isAuthorized) {
            return "redirect:/login";
        }

        Message message = new Message();
        message.setSession(session);
        message.setSender(sender);
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());

        messageRepository.save(message);
        return "redirect:/session/" + id;
    }


    //Ends session (adds ended at date)
    @PostMapping("/session/{id}/end")
    @Transactional
    public String endSession(@PathVariable Long id, HttpSession httpSession) {
        User loggedInUser = (User) httpSession.getAttribute("loggedInUser");
        Optional<Session> sOpt = sessionService.getById(id);

        if (sOpt.isEmpty() || loggedInUser == null) {
            return "redirect:/login";
        }

        Session session = sOpt.get();

        boolean isAgentInSession = loggedInUser.getAgent() != null &&
                session.getAgent().getAgId().equals(loggedInUser.getAgent().getAgId());
        boolean isCustomerInSession = loggedInUser.getCustomer() != null &&
                session.getCustomer().getCustId().equals(loggedInUser.getCustomer().getCustId());

        if (!isAgentInSession && !isCustomerInSession) {
            return "redirect:/login";
        }

        // End the current session
        session.setEndedAt(LocalDateTime.now());
        sessionService.save(session);

        Agent agent = session.getAgent();
        agent.setIsAvailable(true);
        agentService.save(agent);

        // Get product IDs assigned to this agent
        List<Long> agentProductIds = agent.getProducts().stream()
                .map(Product::getProdId)
                .toList();


        Optional<QueueEntry> nextEntryOpt = queueService.getNextEntryForProducts(agentProductIds);
        if (nextEntryOpt.isPresent()) {
            QueueEntry entry = nextEntryOpt.get();
            Customer customer = entry.getCustomer();
            Product product = entry.getProduct();

            Session newSession = new Session();
            newSession.setAgent(agent);
            newSession.setCustomer(customer);
            newSession.setProduct(product);
            newSession.setStartedAt(LocalDateTime.now());
            sessionService.save(newSession);

            queueService.removeCustomerFromQueue(customer, product.getProdId());

            agent.setIsAvailable(false);
            agentService.save(agent);
        }

        return "redirect:/dashboard";
    }


    //Displays messages
    @GetMapping("/session/{id}/messages")
    public String getMessages(@PathVariable Long id, Model model, HttpSession httpSession) {
        Optional<Session> sessionOpt = sessionService.getById(id);
        if (sessionOpt.isEmpty()) {
            return "fragments/messages :: messageList"; // Empty state fallback
        }

        Session session = sessionOpt.get();
        User loggedInUser = (User) httpSession.getAttribute("loggedInUser");

        if (loggedInUser == null || (
                loggedInUser.getCustomer() != null && !loggedInUser.getCustomer().getCustId().equals(session.getCustomer().getCustId())
        ) && (
                loggedInUser.getAgent() != null && !loggedInUser.getAgent().getAgId().equals(session.getAgent().getAgId())
        )) {
            return "fragments/messages :: messageList";
        }

        model.addAttribute("messages", messageRepository.findBySession_SessionIdOrderBySentAtAsc(id));
        return "fragments/messages :: messageList";
    }


}

