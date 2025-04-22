package tech.neckel.security.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface ProductRepository extends JpaRepository<Product, Long>{

}
