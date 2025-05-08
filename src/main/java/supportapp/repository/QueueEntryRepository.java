package supportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import supportapp.model.QueueEntry;
import supportapp.model.Customer;

import java.util.List;
import java.util.Optional;

public interface QueueEntryRepository extends JpaRepository<QueueEntry, Long> {

    Optional<QueueEntry> findFirstByProduct_ProdIdOrderByQueuedAtAsc(Long productId);
    Optional<QueueEntry> findByCustomer_CustId(Long customerId);
    Optional<QueueEntry> findTop1ByProduct_ProdIdInOrderByQueuedAtAsc(List<Long> productIds);
    void deleteByCustomerAndProduct_ProdId(Customer customer, Long productId);
    boolean existsByCustomerAndProduct_ProdId(Customer customer, Long productId);
    boolean existsByCustomer(Customer customer);
}
