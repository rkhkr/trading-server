package in.rkumar.orderservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rkumar.orderservice.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	// "Find by UserName" AND "Order by Created At (Descending)"
    List<Order> findByUserNameOrderByCreatedAtDesc(String userName);
}
