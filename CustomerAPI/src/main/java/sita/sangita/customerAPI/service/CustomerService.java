package sita.sangita.customerAPI.service;

import sita.sangita.customerAPI.dto.CustomerDTO;
import sita.sangita.customerAPI.dto.ResetPasswordDto;
import sita.sangita.customerAPI.response.AuthResponse;

public interface CustomerService {

	

	public Boolean isEmailUnique(String email);
	public Boolean register(CustomerDTO customerDto);
	public CustomerDTO getCustomerByEmail(String email);
	public Boolean restPwd(ResetPasswordDto passwordDto);
	public AuthResponse login(CustomerDTO customerDto);
	public Boolean forgotPwd(String email);


}
