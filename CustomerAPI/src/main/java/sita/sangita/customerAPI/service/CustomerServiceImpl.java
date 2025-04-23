package sita.sangita.customerAPI.service;

import java.util.Collections;
import java.util.Random;

import org.modelmapper.internal.bytebuddy.asm.Advice.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sita.sangita.customerAPI.dto.CustomerDTO;
import sita.sangita.customerAPI.dto.ResetPasswordDto;
import sita.sangita.customerAPI.entity.Customer;
import sita.sangita.customerAPI.mapper.CustomerMapper;
import sita.sangita.customerAPI.repo.CustomerRepo;
import sita.sangita.customerAPI.response.AuthResponse;
@Service
public class CustomerServiceImpl implements CustomerService ,UserDetailsService{

	@Autowired
	private CustomerRepo customerRepo;
	
	private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public CustomerServiceImpl(AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }
	@Autowired
	private EmailService emailService;
	Random rnd=new Random();
	@Override
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Customer c = customerRepo.findByEmail(email);
		  return new  User(c.getEmail(), c.getPwd(), Collections.emptyList());
	}

	@Override
	public Boolean isEmailUnique(String email) {
		
		Customer c = customerRepo.findByEmail(email);
		
		return c==null ;
	}

	@Override
	public Boolean register(CustomerDTO customerDto) {

		String orgPwd = getRandomPwd();
		String encodePwd = passwordEncoder.encode(orgPwd);
		Customer entity = CustomerMapper.convertToEntity(customerDto);
	
		entity.setPwd(encodePwd);
		entity.setUpdatePwd("NO");
		Customer saveEntity = customerRepo.save(entity);
		
		if(saveEntity.getId()!=null) {
			String subject="Register success";
			String body="Login password "+orgPwd;
			boolean sendEmail = emailService.sendEmail(customerDto.getEmail(), subject, body);
			return sendEmail;
		}
		return false;
		
	}

	@Override
	public CustomerDTO getCustomerByEmail(String email) {

		Customer c = customerRepo.findByEmail(email);
		
		if(c!=null) {
			return CustomerMapper.convertToDto(c);

		}
		return null;
	}

	@Override
	public Boolean restPwd(ResetPasswordDto passwordDto) {
		Customer c = customerRepo.findByEmail(passwordDto.getEmail());
		if(c!=null) {
			String newPwd = passwordDto.getNewPwd();
			String encodePwd = passwordEncoder.encode(newPwd);
			c.setPwd(encodePwd);
			c.setUpdatePwd("YES");
			customerRepo.save(c);
			return true;
		}
		
		return false;
	}

	@Override
	public AuthResponse login(CustomerDTO customerDto) {
		
		AuthResponse response=new AuthResponse();
		UsernamePasswordAuthenticationToken authenticationToken=
				new UsernamePasswordAuthenticationToken(customerDto.getEmail(), customerDto.getPwd());

		
		Authentication authenticate = authenticationManager.authenticate(authenticationToken);
		
		if(authenticate.isAuthenticated()) {
			Customer c = customerRepo.findByEmail(customerDto.getEmail());
			response.setCustomerDto(CustomerMapper.convertToDto(c));
			response.setToken("");//TODO
		}
		
		return response;
	}
	

	private String getRandomPwd() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

		StringBuilder pwd=new StringBuilder();
		
		while (pwd.length() < 5) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			pwd.append(SALTCHARS.charAt(index));
		}

		return pwd.toString();
	}

	@Override
	public Boolean forgotPwd(String email) {

		Customer c = customerRepo.findByEmail(email);
		if(c!=null) {
			String subject = "🔐 Reset Your Password Request";

			String body = "Dear User,\n\n"
			           + "We received a request to reset your password. To proceed, please click the link below:\n\n"
			           + "[Reset Password Link]\n\n"
			           + "For security reasons, this link will expire in [Time Limit]. If you did not request this change, please ignore this email.\n\n"
			           + "If you need any assistance, feel free to contact our support team.\n\n"
			           + "Best regards,\n"
			           + "Your Company Name Team";
			emailService.sendEmail(email, subject, body);
			return true;
		}
		return false;
	}

}

