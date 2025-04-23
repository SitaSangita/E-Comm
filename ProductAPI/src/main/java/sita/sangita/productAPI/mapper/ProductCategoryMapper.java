package sita.sangita.productAPI.mapper;

import org.modelmapper.ModelMapper;

import sita.sangita.productAPI.dto.ProductCategoryDTO;
import sita.sangita.productAPI.entity.ProductCategory;

public class ProductCategoryMapper {
	
	public final static ModelMapper modelMapper=new ModelMapper();
	
	public static ProductCategoryDTO convertToDto(ProductCategory entity) {
		return modelMapper.map(entity, ProductCategoryDTO.class);
	}
	public static ProductCategory convertToEntity(ProductCategoryDTO catagoryDTO) {
		return modelMapper.map(catagoryDTO, ProductCategory.class);
	}

}