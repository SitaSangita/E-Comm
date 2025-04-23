package sita.sangita.customerAPI.mapper;

import org.modelmapper.ModelMapper;

import sita.sangita.customerAPI.dto.CustomerDTO;
import sita.sangita.customerAPI.entity.Customer;

public class CustomerMapper {
	
	
	public static final ModelMapper mapper=new ModelMapper();
	
	public static CustomerDTO convertToDto(Customer entity) {
		return mapper.map(entity, CustomerDTO.class);
	}
	
	public static Customer convertToEntity(CustomerDTO dto) {
		return mapper.map(dto, Customer.class);
	}

}
