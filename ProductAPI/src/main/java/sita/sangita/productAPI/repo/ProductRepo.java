package sita.sangita.productAPI.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import sita.sangita.productAPI.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

	// select * from product where categoruy_id=:categoryId;
	public List<Product> findByCategoryId(Long categoryId);

	//select * from product where name like %name%
	List<Product> findByNameContainingIgnoreCase(@Param("name") String name);
}
