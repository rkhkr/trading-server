package in.rkumar.walletservice.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import in.rkumar.walletservice.events.OrderPlacedEvent;
import in.rkumar.walletservice.events.PaymentProcessedEvent;
import in.rkumar.walletservice.services.WalletService;

@Component
public class OrderEventListener {

	private final WalletService walletService;
    private final KafkaTemplate<String, PaymentProcessedEvent> kafkaTemplate; // 1. Inject Kafka

    public OrderEventListener(WalletService walletService, KafkaTemplate<String, PaymentProcessedEvent> kafkaTemplate) {
        this.walletService = walletService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order-placed", groupId = "wallet-group")
    public void handleOrderPlaced(OrderPlacedEvent event) {
        System.out.println("--- RECEIVED ORDER EVENT: " + event.orderId() + " ---");
        
        Long totalCost = event.price() * event.quantity();
        
        // 1. Process Payment
        boolean success = walletService.processPayment(event.userName(), totalCost);

        // 2. Create Result Event with EXTRA DATA, We map 'product' from Order to 'ticker' for Portfolio
        PaymentProcessedEvent resultEvent = new PaymentProcessedEvent(event.orderId(), event.userName(), event.product(), event.quantity(), event.orderType(), success);

        // 3. Send
        kafkaTemplate.send("payment-result", resultEvent);

        if (success) {
            System.out.println("✅ PAYMENT SUCCESSFUL. Sent confirmation for Order " + event.orderId());
        } else {
            System.err.println("❌ PAYMENT FAILED. Sent failure notice for Order " + event.orderId());
        }
    }
}


