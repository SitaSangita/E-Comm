package sita.sangita.notificationAPI.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import sita.sangita.notificationAPI.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
		public Customer findByEmail(String email);
}
