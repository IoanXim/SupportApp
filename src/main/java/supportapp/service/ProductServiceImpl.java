package supportapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supportapp.model.Product;
import supportapp.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> getByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Long> getAllProductIds() {
        return productRepository.findAll().stream()
                .map(Product::getProdId)
                .toList();
    }


    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
