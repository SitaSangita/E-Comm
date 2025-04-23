package sita.sangita.orderAPI.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sita.sangita.orderAPI.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

}
