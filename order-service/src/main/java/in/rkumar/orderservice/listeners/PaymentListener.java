package in.rkumar.orderservice.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import in.rkumar.orderservice.enums.OrderStatus;
import in.rkumar.orderservice.events.PaymentProcessedEvent;
import in.rkumar.orderservice.repositories.OrderRepository;

@Component
public class PaymentListener {

    private final OrderRepository orderRepository;

    public PaymentListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "payment-result", groupId = "order-group")
    @Transactional
    public void handlePaymentResult(PaymentProcessedEvent event) {
        System.out.println("<<< RECEIVED PAYMENT RESULT for Order " + event.orderId() + " Success: " + event.isSuccessful());

        Long orderId = Long.valueOf(event.orderId());
        
        orderRepository.findById(orderId).ifPresent(order -> {
            if (event.isSuccessful()) {
                order.setStatus(OrderStatus.PENDING);
            } else {
                order.setStatus(OrderStatus.FAILED);
            }
            orderRepository.save(order);
            System.out.println("Order " + orderId + " updated to " + order.getStatus());
        });
    }
}
