package sita.sangita.notificationAPI.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import sita.sangita.notificationAPI.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

}
