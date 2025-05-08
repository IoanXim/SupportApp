package supportapp.service;

import supportapp.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getById(Long id);
    Optional<Product> getByName(String name);
    List<Product> getAll();
    List<Long> getAllProductIds();
    Product save(Product product);
}
