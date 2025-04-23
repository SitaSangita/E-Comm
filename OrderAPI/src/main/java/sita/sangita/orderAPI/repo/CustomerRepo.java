package sita.sangita.orderAPI.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sita.sangita.orderAPI.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
		public Customer findByEmail(String email);
}
