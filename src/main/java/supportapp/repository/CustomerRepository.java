package supportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supportapp.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
