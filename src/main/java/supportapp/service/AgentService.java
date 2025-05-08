package supportapp.service;

import supportapp.model.Agent;
import java.util.Optional;

public interface AgentService {
    Optional<Agent> getById(Long id);
    Optional<Agent> findAvailableAgentForProduct(Long productId);
    Agent save(Agent agent);
    Agent update(Agent agent);
}
