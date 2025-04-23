package sita.sangita.customerAPI.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sita.sangita.customerAPI.dto.CustomerDTO;
import sita.sangita.customerAPI.dto.ResetPasswordDto;
import sita.sangita.customerAPI.repo.CustomerRepo;
import sita.sangita.customerAPI.response.APIResponse;
import sita.sangita.customerAPI.response.AuthResponse;
import sita.sangita.customerAPI.service.CustomerService;

@RestController
public class CustomerRestController {
	@Autowired
	private CustomerService customerInter;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@PostMapping("/register")
	public ResponseEntity<APIResponse<String>> register(@RequestBody CustomerDTO customerDto){
		
		APIResponse<String> response=new APIResponse<>();
		Boolean emailUnique = customerInter.isEmailUnique(customerDto.getEmail());
		
		if(!emailUnique) {
			response.setStatus(400);
			response.setMessage("Failed");
			response.setData("Duplicate Email");
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		
		Boolean register = customerInter.register(customerDto);
		if(register) {
			response.setStatus(201);
			response.setMessage("success");
			response.setData("Register Success");
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}
		else {
			response.setStatus(500);
			response.setMessage("Failed");
			response.setData("Resgisteation Failed");
			return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<APIResponse<AuthResponse>> login(@RequestBody CustomerDTO customerDto){
		
		APIResponse<AuthResponse> response=new APIResponse<>();
		AuthResponse login = customerInter.login(customerDto);
		if(login !=null) {
			response.setStatus(200);
			response.setMessage("Login success");
			response.setData(login);
			return new ResponseEntity<APIResponse<AuthResponse>>(response,HttpStatus.OK);
		}
		else {
			response.setStatus(400);
			response.setMessage("Invalide Credential");
			response.setData(null);
			return new ResponseEntity<APIResponse<AuthResponse>>(response,HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@PostMapping("/resetPwd")
	public ResponseEntity<APIResponse<String>> resetPwd(@RequestBody ResetPasswordDto resetPasswordDto){
		APIResponse<String> response=new APIResponse<>();
		CustomerDTO customer = customerInter.getCustomerByEmail(resetPasswordDto.getEmail());
		
		if(!passwordEncoder.matches(resetPasswordDto.getOldPwd(), customer.getPwd())){
			response.setStatus(400);
			response.setMessage("Failed");
			response.setData("Current pwd is incorrect");
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		Boolean resetPwd = customerInter.restPwd(resetPasswordDto);
		 if(resetPwd) {
			 response.setStatus(201);
			 response.setMessage("Success");
			 response.setData("Password Updated");
			 return new ResponseEntity<>(response, HttpStatus.CREATED);
		 }
		 else {
			 response.setStatus(500);
			 response.setMessage("Failed");
			 response.setData("Pwd Reset Failed");
			 return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		 }	
	
	}
	
	@GetMapping("/forgot-pwd/{email}")
	public ResponseEntity<APIResponse<String>> forgotPwd(@PathVariable String email){
		
		APIResponse<String> response=new APIResponse<>();
		Boolean pwdStatus = customerInter.forgotPwd(email);
		
		if(pwdStatus) {
			response.setStatus(200);
			response.setMessage("Success");
			response.setData("Email sent to Rest Pwd");
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			response.setStatus(400);
			response.setMessage("Failed ");
			response.setData("No Account Found In this Email");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	
	}
	@GetMapping("/welcome")
	public String getWelcome() {
		String msg="Welcome to RestAPI......";
		return msg;
	}
	
}
