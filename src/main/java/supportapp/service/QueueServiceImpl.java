package supportapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supportapp.model.Customer;
import supportapp.model.Product;
import supportapp.model.QueueEntry;
import supportapp.repository.QueueEntryRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {

    private final QueueEntryRepository queueEntryRepository;

    @Override
    public void addToQueue(Customer customer, Long productId) {
        QueueEntry entry = new QueueEntry();
        Product product = new Product();
        product.setProdId(productId); // minimal stub to avoid loading full product

        entry.setCustomer(customer);
        entry.setProduct(product);
        entry.setQueuedAt(LocalDateTime.now());

        queueEntryRepository.save(entry);
    }

    @Override
    public Optional<Customer> getNextCustomerInQueue(Long productId) {
        return queueEntryRepository.findFirstByProduct_ProdIdOrderByQueuedAtAsc(productId)
                .map(QueueEntry::getCustomer);
    }

    @Override
    public Optional<QueueEntry> getNextEntryForProducts(List<Long> productIds) {
        return queueEntryRepository.findTop1ByProduct_ProdIdInOrderByQueuedAtAsc(productIds);
    }

    @Override
    public Optional<QueueEntry> getQueueEntryByCustomerId(Long customerId) {
        return queueEntryRepository.findByCustomer_CustId(customerId);
    }

    @Override
    public void removeCustomerFromQueue(Customer customer, Long productId) {
        queueEntryRepository.deleteByCustomerAndProduct_ProdId(customer, productId);
    }

    @Override
    public boolean isCustomerQueued(Customer customer) {
        return queueEntryRepository.existsByCustomer(customer);
    }

    @Override
    public boolean isQueued(Long productId, Customer customer) {
        return queueEntryRepository.existsByCustomerAndProduct_ProdId(customer, productId);
    }
}
