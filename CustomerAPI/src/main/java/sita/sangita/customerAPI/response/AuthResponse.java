package sita.sangita.customerAPI.response;

import lombok.Data;
import sita.sangita.customerAPI.dto.CustomerDTO;

@Data
public class AuthResponse {
	
	private CustomerDTO customerDto;
	private String token;

}
