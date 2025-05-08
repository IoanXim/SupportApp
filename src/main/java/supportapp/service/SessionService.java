package supportapp.service;

import supportapp.model.Agent;
import supportapp.model.Customer;
import supportapp.model.Session;

import java.util.List;
import java.util.Optional;

public interface SessionService {
    Optional<Session> getById(Long id);
    List<Session> getByCustomerId(Long customerId);
    List<Session> findPastSessionsByCustomer(Customer customer);
    List<Session> findPastSessionsByAgent(Agent agent);
    Optional<Session> findActiveSessionByCustomer(Customer customer);
    Optional<Session> findActiveSessionByAgent(Agent agent);
    List<Session> getByAgentId(Long agentId);
    Optional<Session> getByIdWithAgentAndProducts(long id);
    Session save(Session session);
}
