package in.rkumar.orderservice.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rkumar.orderservice.enums.OrderType;
import in.rkumar.orderservice.models.Order;
import in.rkumar.orderservice.services.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order placeOrder(@RequestBody OrderRequest request) {
        return orderService.placeOrder(
            request.userName(), 
            request.product(), 
            request.price(), 
            request.quantity(),
            request.orderType()
        );
    }
    
    // GET: http://localhost:8080/order-service/api/orders/history
    @GetMapping("/history")
    public ResponseEntity<List<Order>> getOrders(@RequestHeader("loggedInUser") String username) {
        System.out.println("Fetching order history for: " + username);
        List<Order> orders = orderService.getOrdersByUsername(username);
        return orders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(orders);
    }

    // Java Record for the Input JSON
    public record OrderRequest(String userName, String product, Long price, Integer quantity, OrderType orderType) {}
    
}
