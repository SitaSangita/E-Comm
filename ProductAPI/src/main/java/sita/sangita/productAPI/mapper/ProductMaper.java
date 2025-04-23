package sita.sangita.productAPI.mapper;

import org.modelmapper.ModelMapper;

import sita.sangita.productAPI.dto.ProductDTO;
import sita.sangita.productAPI.entity.Product;

public class ProductMaper {
	
	public final static ModelMapper modelMapper=new ModelMapper();
	
	public static ProductDTO convertToDTO(Product entity) {
		return modelMapper.map(entity,ProductDTO.class);
	}

	public static Product convertToEntity(ProductDTO dto) {
		return modelMapper.map(dto,Product.class);
	}
}
