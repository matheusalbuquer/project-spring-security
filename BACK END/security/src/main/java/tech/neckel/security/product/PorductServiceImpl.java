package tech.neckel.security.product;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PorductServiceImpl implements ProductService{

	private final ProductRepository productRepository;
	
	@Override
	public List<Product> listAll() {
		return productRepository.findAll();
	}

	@Override
	public Product create(Product product) {
		if(product.getId() != null) {
			throw new RuntimeException("To create a record, ypu cannot have an ID");
		}
		return productRepository.save(product);
	}

	@Override
	public Product update(Product product) {
		if(product.getId() == null) {
			throw new RuntimeException("To update a record, you must have an ID");
		}
		return productRepository.save(product);
	}

	@Override
	public void delete(Long id) {
		productRepository.deleteById(id);
	}

}
