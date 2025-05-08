package supportapp.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import supportapp.model.Agent;
import supportapp.model.User;
import supportapp.service.AgentService;
import supportapp.service.UserService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final AgentService agentService;

    @GetMapping("/")
    public String homePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("user", user);
        return "redirect:/login";
    }

    //Logs out current user and shows login page
    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model) {
        session.invalidate();
        return "LoginPage";
    }

    //Testing stuff
    @GetMapping("/debug/agent-products/{id}")
    @ResponseBody
    public String debugAgentProducts(@PathVariable Long id) {
        Agent agent = agentService.getById(id)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        return "Agent " + agent.getAgId() + " has " + agent.getProducts().size() + " products.";
    }


    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        Optional<User> userOpt = userService.getByUsername(username);

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            session.setAttribute("loggedInUser", userOpt.get());
            User user = userOpt.get();
            session.setAttribute("loggedInUser", user);

            if (user.getCustomer() != null) {
                return "redirect:/dashboard/customer";
            } else if (user.getAgent() != null) {
                return "redirect:/dashboard/agent";
            } else {
                model.addAttribute("error", "User has no associated role.");
                return "/login";
            }

        } else {
            model.addAttribute("error", "Invalid credentials");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
