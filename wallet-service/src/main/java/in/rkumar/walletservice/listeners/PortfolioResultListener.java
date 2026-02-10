package in.rkumar.walletservice.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import in.rkumar.walletservice.events.OrderPlacedEvent;
import in.rkumar.walletservice.events.PortfolioResultEvent;
import in.rkumar.walletservice.services.WalletService;

@Component
public class PortfolioResultListener {

    private final WalletService walletService;

    public PortfolioResultListener(WalletService walletService) {
        this.walletService = walletService;
    }

    @KafkaListener(topics = "portfolio-result", groupId = "wallet-sell-group")
    public void handlePortfolioResult(PortfolioResultEvent event) {
        // If portfolio deduction was successful, add money to the wallet
        if (event.isSuccessful()) {
            OrderPlacedEvent order = event.orderEvent();
            Long amountToAdd = order.price() * order.quantity();
            
            System.out.println("💰 SELL SUCCESS: Adding " + amountToAdd + " paisa to User " + order.userName());
            walletService.addFunds(order.userName(), amountToAdd);
        } else {
            System.err.println("❌ SELL FAILED: Portfolio could not deduct stocks for Order " + event.orderId());
        }
    }
}