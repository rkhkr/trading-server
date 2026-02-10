package in.rkumar.orderservice.services;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.rkumar.orderservice.enums.OrderType;
import in.rkumar.orderservice.events.OrderPlacedEvent;
import in.rkumar.orderservice.models.Order;
import in.rkumar.orderservice.repositories.OrderRepository;

@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate; // Inject Kafka

    public OrderService(OrderRepository orderRepository, KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional // Ensures the save is atomic
    public Order placeOrder(String userName, String product, Long price, Integer quantity, OrderType orderType) {
    	
        // 1. Save to Database
        Order order = new Order(userName, product, price, quantity, orderType);
        orderRepository.save(order);

        // 2. Create Event
        OrderPlacedEvent event = new OrderPlacedEvent(
                order.getId().toString(), 
                userName, 
                product, 
                price, 
                quantity,
                orderType
        );

        // 3. Send to Kafka Topic "order-placed"
        kafkaTemplate.send("order-placed", event);

        return order;
    }
    
    public List<Order> getOrdersByUsername(String username) {
        return orderRepository.findByUserNameOrderByCreatedAtDesc(username);
    }
}
