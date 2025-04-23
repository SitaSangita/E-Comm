package sita.sangita.productAPI.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sita.sangita.productAPI.dto.ProductCategoryDTO;
import sita.sangita.productAPI.dto.ProductDTO;
import sita.sangita.productAPI.entity.Product;
import sita.sangita.productAPI.mapper.ProductCategoryMapper;
import sita.sangita.productAPI.mapper.ProductMaper;
import sita.sangita.productAPI.repo.ProductCategoryRepo;
import sita.sangita.productAPI.repo.ProductRepo;

@Service

public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductCategoryRepo categoryRepo;

	@Autowired
	private ProductRepo productRepo;

	@Override
	public List<ProductCategoryDTO> findAllCategories() {

		return categoryRepo.findAll().stream().map(ProductCategoryMapper::convertToDto).collect(Collectors.toList());

	}

	@Override
	public List<ProductDTO> findProductByCategoryId(Long CategoryId) {
		return productRepo.findByCategoryId(CategoryId)
						  .stream()
						  .map(ProductMaper::convertToDTO)
				           .collect(Collectors.toList());

	}

	@Override
	public ProductDTO findByProductId(Long productId) {

		return productRepo.findById(productId).map(ProductMaper::convertToDTO).orElse(null);
	}

	@Override
	public List<ProductDTO> findByProductName(String productName) {
	    List<Product> products = productRepo.findByNameContainingIgnoreCase(productName); // Fetch products from the repository
	    if (products == null || products.isEmpty()) {
	        return Collections.emptyList(); // Return an empty list if no products are found
	    }
	    return products.stream()
	                   .map(ProductMaper::convertToDTO)
	                   .collect(Collectors.toList());
	}
}