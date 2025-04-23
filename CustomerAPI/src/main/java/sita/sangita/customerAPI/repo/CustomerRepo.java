package sita.sangita.customerAPI.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sita.sangita.customerAPI.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long>{

	
	public Customer findByEmail(String email);
}
