package supportapp.service;

import supportapp.model.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> getById(Long id);
    Customer save(Customer customer);
}
