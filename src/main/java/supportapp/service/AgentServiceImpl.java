package supportapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supportapp.model.Agent;
import supportapp.repository.AgentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    @Override
    public Optional<Agent> getById(Long id) {
        return agentRepository.findById(id);
    }

    @Override
    public Optional<Agent> findAvailableAgentForProduct(Long productId) {
        List<Agent> agents = agentRepository.findAvailableAgentsForProduct(productId);
        return agents.stream().findFirst();
    }

    @Override
    public Agent save(Agent agent) {
        return agentRepository.save(agent);
    }

    @Override
    public Agent update(Agent agent) {
        return agentRepository.save(agent);
    }

}
