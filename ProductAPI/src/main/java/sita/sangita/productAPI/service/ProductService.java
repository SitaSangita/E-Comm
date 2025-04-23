package sita.sangita.productAPI.service;

import java.util.List;

import sita.sangita.productAPI.dto.ProductCategoryDTO;
import sita.sangita.productAPI.dto.ProductDTO;

public interface ProductService {

	public List<ProductCategoryDTO> findAllCategories();
	public List<ProductDTO> findProductByCategoryId(Long CategoryId);
	public ProductDTO findByProductId(Long productId);
	public List<ProductDTO> findByProductName(String productName);

}
