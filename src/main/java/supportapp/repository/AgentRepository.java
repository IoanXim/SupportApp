package supportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import supportapp.model.Agent;

import java.util.List;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    @Query("SELECT a FROM Agent a JOIN a.products p WHERE a.isAvailable = true AND p.prodId = :productId")
    List<Agent> findAvailableAgentsForProduct(@Param("productId") Long productId);
}
