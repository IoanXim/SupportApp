package supportapp.service;

import supportapp.model.Customer;
import supportapp.model.QueueEntry;

import java.util.List;
import java.util.Optional;

public interface QueueService {
    void addToQueue(Customer customer, Long prodId);
    Optional<Customer> getNextCustomerInQueue(Long prodId);
    Optional<QueueEntry> getNextEntryForProducts(List<Long> productIds);
    Optional<QueueEntry> getQueueEntryByCustomerId(Long customerId);
    void removeCustomerFromQueue(Customer customer, Long productId);
    boolean isCustomerQueued(Customer customer);
    boolean isQueued(Long productId, Customer customer);
}
