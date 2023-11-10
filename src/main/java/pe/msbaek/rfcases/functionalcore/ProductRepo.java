package pe.msbaek.rfcases.functionalcore;

import java.util.List;
import java.util.Optional;

public interface ProductRepo {
    Product findByName(String name);
    Optional<Product> findById(Long id);
    List<Product> findAllById(List<Long> productIds);
}
