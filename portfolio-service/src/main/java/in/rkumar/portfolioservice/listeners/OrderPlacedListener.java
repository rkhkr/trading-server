package in.rkumar.portfolioservice.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import in.rkumar.portfolioservice.enums.OrderType;
import in.rkumar.portfolioservice.events.OrderPlacedEvent;
import in.rkumar.portfolioservice.events.PaymentProcessedEvent;
import in.rkumar.portfolioservice.events.PortfolioResultEvent;
import in.rkumar.portfolioservice.models.PortfolioItem;
import in.rkumar.portfolioservice.repositories.PortfolioRepository;

@Component
public class OrderPlacedListener {

    private final PortfolioRepository portfolioRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderPlacedListener(PortfolioRepository portfolioRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.portfolioRepository = portfolioRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    // ----------------------------------------------------------------
    // SELL LOGIC (Listens to "order-placed")
    // ----------------------------------------------------------------
    @KafkaListener(topics = "order-placed", groupId = "portfolio-sell-group")
    @Transactional
    public void handleSellOrder(OrderPlacedEvent event) {
        
        if(!"SELL".equals(event.orderType())) return; 

        System.out.println("📉 Processing SELL order for " + event.product());

        portfolioRepository.findByUserNameAndTicker(event.userName(), event.product())
            .filter(item -> item.getQuantity() >= event.quantity())
            .ifPresentOrElse(
                item -> {
                    // 1. Success: Deduct Stocks
                    item.setQuantity(item.getQuantity() - event.quantity());
                    portfolioRepository.save(item);

                    // 2. Notify Wallet to ADD MONEY
                    // ✅ CORRECT TOPIC: Send to 'portfolio-result' so Wallet can listen
                    kafkaTemplate.send("portfolio-result", new PortfolioResultEvent(event.orderId(), true, event));
                    System.out.println("✅ Stocks deducted. Notifying Wallet Service...");
                },
                () -> {
                    // 3. Failure: Notify System
                    System.err.println("❌ SELL FAILED: Insufficient shares.");
                    
                    // Optional: Send failure event so Order Service can mark as FAILED
                    kafkaTemplate.send("portfolio-result", new PortfolioResultEvent(event.orderId(), false, event));
                }
            );
    }

    // ----------------------------------------------------------------
    // 2. BUY LOGIC (Listens to "payment-result")
    // ----------------------------------------------------------------
    @KafkaListener(topics = "payment-result", groupId = "portfolio-buy-group")
    @Transactional
    public void handleBuyOrder(PaymentProcessedEvent event) {
        
        // We only care about BUY orders that were PAID successfully
        if(!"BUY".equals(event.orderType()) || !event.isSuccessful()) return;

        System.out.println("📈 Payment successful! Adding " + event.quantity() + " " + event.ticker() + " to User " + event.userName());

        // Check if user already owns this stock
        PortfolioItem item = portfolioRepository.findByUserNameAndTicker(event.userName(), event.ticker())
                .orElse(new PortfolioItem(event.userName(), event.ticker(), 0, event.orderType())); // Create new if not found

        // Add Quantity
        item.setQuantity(item.getQuantity() + event.quantity());
        portfolioRepository.save(item);

        System.out.println("✅ Portfolio updated successfully.");
    }
}
