package supportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import supportapp.model.Agent;
import supportapp.model.Customer;
import supportapp.model.Session;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByCustomer_CustId(Long customerId);
    List<Session> findByAgent_AgId(Long agentId);
    List<Session> findByCustomerAndEndedAtIsNotNullOrderByEndedAtDesc(Customer customer);
    List<Session> findByAgentAndEndedAtIsNotNullOrderByEndedAtDesc(Agent agent);
    Optional<Session> findByCustomerAndEndedAtIsNull(Customer customer);
    Optional<Session> findByAgentAndEndedAtIsNull(Agent agent);

    @Query("SELECT s FROM Session s " +
            "JOIN FETCH s.agent a " +
            "LEFT JOIN FETCH a.products " +
            "WHERE s.sessionId = :id")
    Optional<Session> findByIdWithAgentAndProducts(@Param("id") Long id);

}
