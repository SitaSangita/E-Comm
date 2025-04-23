package sita.sangita.productAPI.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sita.sangita.productAPI.entity.Product;
import sita.sangita.productAPI.entity.ProductCategory;

public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long>{

	
	
	public List<Product> findByCategoryName(String name);
}
