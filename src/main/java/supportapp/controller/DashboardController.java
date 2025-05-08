package supportapp.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import supportapp.model.*;
import supportapp.service.AgentService;
import supportapp.service.ProductService;
import supportapp.service.QueueServiceImpl;
import supportapp.service.SessionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ProductService productService;
    private final AgentService agentService;
    private final SessionService sessionService;
    private final QueueServiceImpl queueService;

    @GetMapping("")
    public String dashboard(HttpSession session, Model model) {

        //Check login status and user type
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        if (user.getCustomer() != null) return "redirect:/dashboard/customer";
        if (user.getAgent() != null) return "redirect:/dashboard/agent";

        return "redirect:/login";
    }

    //Shows customer dashboard
    @GetMapping("/customer")
    public String customerDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || user.getCustomer() == null) return "redirect:/login";

        Customer customer = user.getCustomer();
        model.addAttribute("customerName", customer.getFirstname());

        //Show active chat session
        Optional<Session> activeSessionOpt = sessionService.findActiveSessionByCustomer(customer);
        if (activeSessionOpt.isPresent()) {
            Session activeSession = activeSessionOpt.get();
            model.addAttribute("activeSession", activeSession);
        }

        //Show closed chat sessions
        List<Session> pastSessions = sessionService.findPastSessionsByCustomer(customer);
        model.addAttribute("pastSessions", pastSessions);

        model.addAttribute("products", productService.getAll());

        //Determine if the customer is in queue for any product
        Optional<QueueEntry> queueEntryOpt = queueService.getQueueEntryByCustomerId(customer.getCustId());
        queueEntryOpt.ifPresent(entry -> {
            productService.getById(entry.getProduct().getProdId())
                    .ifPresent(product -> model.addAttribute("queuedProduct", product));
        });

        return "dashboard/customerDashboard";
    }

    //Requests support for selected product
    @PostMapping("/request-support")
    public String requestSupport(@RequestParam Long productId,
                                 HttpSession httpSession,
                                 RedirectAttributes redirectAttributes) {

        User user = (User) httpSession.getAttribute("loggedInUser");

        if (user == null || user.getCustomer() == null) {
            return "redirect:/login";
        }

        Customer customer = user.getCustomer();

        //Check if customer is already in an active session
        if (sessionService.findActiveSessionByCustomer(customer).isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are already in an active session.");
            return "redirect:/dashboard/customer";
        }

        //Check if customer is in queue for support
        if (queueService.isCustomerQueued(customer)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are already in queue for support.");
            return "redirect:/dashboard/customer";
        }

        //Find available agent for the product or place customer in queue
        Optional<Product> product = productService.getById(productId);
        Optional<Agent> availableAgentOpt = agentService.findAvailableAgentForProduct(product.get().getProdId());

        if (availableAgentOpt.isPresent()) {
            Agent agent = availableAgentOpt.get();

            // Create session
            Session session = new Session();
            session.setCustomer(customer);
            session.setAgent(agent);
            session.setProduct(product.get());
            session.setStartedAt(LocalDateTime.now());

            sessionService.save(session);

            // Mark agent unavailable
            agent.setIsAvailable(false);
            agentService.update(agent);

            redirectAttributes.addFlashAttribute("message", "Session started with agent " + agent.getFirstname());
        } else {
            // Add to queue ()
            queueService.addToQueue(customer, product.get().getProdId());
            redirectAttributes.addFlashAttribute("message", "No agents available. You've been added to the queue.");
            System.out.println(queueService.isQueued(product.get().getProdId(), customer));
        }

        return "redirect:/dashboard/customer";
    }

    //Removes customer from queue
    @PostMapping("/leave-queue")
    @Transactional
    public String leaveQueue(HttpSession session, @RequestParam Long productId) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || user.getCustomer() == null) return "redirect:/login";

        queueService.removeCustomerFromQueue(user.getCustomer(), productId);
        return "redirect:/dashboard/customer";
    }

    //Shows agent dashboard
    @GetMapping("/agent")
    public String agentDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || user.getAgent() == null) return "redirect:/login";

        Agent agent = user.getAgent();

        //Welcome
        model.addAttribute("agentName", agent.getFirstname());

        //Active session
        Optional<Session> activeSession = sessionService.findActiveSessionByAgent(agent);
        activeSession.ifPresent(s -> model.addAttribute("activeSession", s));

        //Past sessions
        List<Session> pastSessions = sessionService.findPastSessionsByAgent(agent);
        model.addAttribute("pastSessions", pastSessions);

        //Assigned products
        model.addAttribute("assignedProducts", agentService.getById(agent.getAgId()).get().getProducts());

        return "dashboard/agentDashboard";
    }

}
