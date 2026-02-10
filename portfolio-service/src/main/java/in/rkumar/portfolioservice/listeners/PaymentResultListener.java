package in.rkumar.portfolioservice.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import in.rkumar.portfolioservice.events.PaymentProcessedEvent;
import in.rkumar.portfolioservice.models.PortfolioItem;
import in.rkumar.portfolioservice.repositories.PortfolioRepository;

@Component
public class PaymentResultListener {

    private final PortfolioRepository portfolioRepository;

    public PaymentResultListener(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @KafkaListener(topics = "payment-result", groupId = "portfolio-group")
    @Transactional
    public void handlePaymentSuccess(PaymentProcessedEvent event) {
        if (!event.isSuccessful()) {
            System.out.println("⚠️ Portfolio Service: Payment failed for Order " + event.orderId() + ". Skipping.");
            return;
        }

        System.out.println("📈 Portfolio Service: Payment successful! Adding " + event.orderId() + " " + event.quantity() + " " + event.ticker() + " to User " + event.userName());

        // Check if user already owns this stock
        portfolioRepository.findByUserNameAndTicker(event.userName(), event.ticker())
            .ifPresentOrElse(
                existingItem -> {
                    // Update existing quantity
                    existingItem.setQuantity(existingItem.getQuantity() + event.quantity());
                    portfolioRepository.save(existingItem);
                },
                () -> {
                    // Create new entry
                    PortfolioItem newItem = new PortfolioItem(event.userName(), event.ticker(), event.quantity(), event.orderType());
                    portfolioRepository.save(newItem);
                }
            );
        
        System.out.println("✅ Portfolio updated successfully.");
    }
}