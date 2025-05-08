package supportapp.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import supportapp.model.Agent;
import supportapp.model.Customer;
import supportapp.model.Product;
import supportapp.model.User;
import supportapp.repository.AgentRepository;
import supportapp.repository.CustomerRepository;
import supportapp.repository.ProductRepository;
import supportapp.repository.UserRepository;

import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0 && userRepository.count() == 0) {
            // === PRODUCTS ===
            Product prodA = new Product();
            prodA.setName("CoolSoft");
            Product prodB = new Product();
            prodB.setName("BugFixer");
            Product prodC = new Product();
            prodC.setName("AwesomeApp");
            Product prodD = new Product();
            prodD.setName("TaskManager");
            Product prodE = new Product();
            prodE.setName("SecureVault");

            productRepository.saveAll(List.of(prodA, prodB, prodC, prodD, prodE));

            // === AGENTS ===
            Agent agent1 = new Agent();
            agent1.setFirstname("Alice");
            agent1.setLastname("Agent");
            agent1.setIsAvailable(true);
            Agent agent2 = new Agent();
            agent2.setFirstname("Bob");
            agent2.setLastname("Support");
            agent2.setIsAvailable(true);
            Agent agent3 = new Agent();
            agent3.setFirstname("Charlie");
            agent3.setLastname("Helper");
            agent3.setIsAvailable(true);
            Agent agent4 = new Agent();
            agent4.setFirstname("Dana");
            agent4.setLastname("Techie");
            agent4.setIsAvailable(true);
            Agent agent5 = new Agent();
            agent5.setFirstname("Eli");
            agent5.setLastname("Fixer");
            agent5.setIsAvailable(true);

            // Link agents to products and vice versa
            agent1.setProducts(Set.of(prodA, prodB));
            prodA.getAgents().add(agent1);
            prodB.getAgents().add(agent1);
            agent2.setProducts(Set.of(prodC, prodD));
            prodC.getAgents().add(agent2);
            prodD.getAgents().add(agent2);
            agent3.setProducts(Set.of(prodE));
            prodE.getAgents().add(agent3);
            agent4.setProducts(Set.of(prodA, prodC, prodE));
            prodA.getAgents().add(agent4);
            prodC.getAgents().add(agent4);
            prodE.getAgents().add(agent4);
            agent5.setProducts(Set.of(prodB, prodD));
            prodB.getAgents().add(agent5);
            prodD.getAgents().add(agent5);

            // === AGENT USERS ===
            User agentUser1 = new User();
            agentUser1.setUsername("agent1");
            agentUser1.setPassword("pass");
            agentUser1.setAgent(agent1);
            agent1.setUser(agentUser1);

            User agentUser2 = new User();
            agentUser2.setUsername("agent2");
            agentUser2.setPassword("pass");
            agentUser2.setAgent(agent2);
            agent2.setUser(agentUser2);

            User agentUser3 = new User();
            agentUser3.setUsername("agent3");
            agentUser3.setPassword("pass");
            agentUser3.setAgent(agent3);
            agent3.setUser(agentUser3);

            User agentUser4 = new User();
            agentUser4.setUsername("agent4");
            agentUser4.setPassword("pass");
            agentUser4.setAgent(agent4);
            agent4.setUser(agentUser4);

            User agentUser5 = new User();
            agentUser5.setUsername("agent5");
            agentUser5.setPassword("pass");
            agentUser5.setAgent(agent5);
            agent5.setUser(agentUser5);

            userRepository.saveAll(List.of(agentUser1, agentUser2, agentUser3, agentUser4, agentUser5));

            // === CUSTOMERS ===
            Customer customer1 = new Customer();
            customer1.setFirstname("Bob");
            customer1.setLastname("Customer");
            User customerUser1 = new User();
            customerUser1.setUsername("cust1");
            customerUser1.setPassword("pass");
            customerUser1.setCustomer(customer1);
            customer1.setUser(customerUser1);

            Customer customer2 = new Customer();
            customer2.setFirstname("Cait");
            customer2.setLastname("Client");
            User customerUser2 = new User();
            customerUser2.setUsername("cust2");
            customerUser2.setPassword("pass");
            customerUser2.setCustomer(customer2);
            customer2.setUser(customerUser2);

            Customer customer3 = new Customer();
            customer3.setFirstname("Dan");
            customer3.setLastname("User");
            User customerUser3 = new User();
            customerUser3.setUsername("cust3");
            customerUser3.setPassword("pass");
            customerUser3.setCustomer(customer3);
            customer3.setUser(customerUser3);

            Customer customer4 = new Customer();
            customer4.setFirstname("Eva");
            customer4.setLastname("Tester");
            User customerUser4 = new User();
            customerUser4.setUsername("cust4");
            customerUser4.setPassword("pass");
            customerUser4.setCustomer(customer4);
            customer4.setUser(customerUser4);

            Customer customer5 = new Customer();
            customer5.setFirstname("Frank");
            customer5.setLastname("Enduser");
            User customerUser5 = new User();
            customerUser5.setUsername("cust5");
            customerUser5.setPassword("pass");
            customerUser5.setCustomer(customer5);
            customer5.setUser(customerUser5);

            userRepository.saveAll(List.of(customerUser1, customerUser2, customerUser3, customerUser4, customerUser5));

            System.out.println("Demo data loaded");
        } else {
            System.out.println("Demo data already present, skipping DataLoader");
        }
    }

}



