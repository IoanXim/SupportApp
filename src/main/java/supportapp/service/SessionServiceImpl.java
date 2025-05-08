package supportapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supportapp.model.Agent;
import supportapp.model.Customer;
import supportapp.model.Session;
import supportapp.repository.SessionRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Override
    public Optional<Session> getById(Long id) {
        return sessionRepository.findById(id);
    }

    @Override
    public List<Session> getByCustomerId(Long customerId) {
        return sessionRepository.findByCustomer_CustId(customerId);
    }

    @Override
    public List<Session> findPastSessionsByCustomer(Customer customer) {
        return sessionRepository.findByCustomerAndEndedAtIsNotNullOrderByEndedAtDesc(customer);
    }

    @Override
    public List<Session> findPastSessionsByAgent(Agent agent) {
        return sessionRepository.findByAgentAndEndedAtIsNotNullOrderByEndedAtDesc(agent);
    }


    @Override
    public Optional<Session> findActiveSessionByCustomer(Customer customer) {
        return sessionRepository.findByCustomerAndEndedAtIsNull(customer);
    }

    @Override
    public Optional<Session> findActiveSessionByAgent(Agent agent) {
        return sessionRepository.findByAgentAndEndedAtIsNull(agent);
    }

    @Override
    public List<Session> getByAgentId(Long agentId) {
        return sessionRepository.findByAgent_AgId(agentId);
    }

    @Override
    public Optional<Session> getByIdWithAgentAndProducts(long id) {
        return sessionRepository.findByIdWithAgentAndProducts(id);
    }

    @Override
    public Session save(Session session) {
        return sessionRepository.save(session);
    }
}
