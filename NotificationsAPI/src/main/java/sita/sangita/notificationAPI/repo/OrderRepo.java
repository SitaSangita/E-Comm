package sita.sangita.notificationAPI.repo;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sita.sangita.notificationAPI.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{

	public Order findByRazorPayOrderId(String razorPayOrderId);
	
	public List<Order> findByDeliveryDate(LocalDate deliveryDate);
	
	public List<Order> findByEmail(String email);
}
