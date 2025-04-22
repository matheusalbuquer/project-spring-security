package tech.neckel.security.product;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {

	List<Product> listAll();
	Product create(Product product);
	Product update(Product product);
	void delete(Long id);
}
