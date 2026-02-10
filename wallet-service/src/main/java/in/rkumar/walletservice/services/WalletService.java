package in.rkumar.walletservice.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.rkumar.walletservice.models.Wallet;
import in.rkumar.walletservice.repositories.WalletRepository;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    // Deduct Money (Buy)
    @Transactional
    public boolean processPayment(String userName, Long amountInPaisa) {
        Wallet wallet = walletRepository.findById(userName)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user: " + userName));

        // Much cleaner check!
        if (wallet.getBalance() >= amountInPaisa) {
            wallet.setBalance(wallet.getBalance() - amountInPaisa); // Simple subtraction
            walletRepository.save(wallet);
            return true;
        }
        return false;
    }

    // Add Money (Sell)
    @Transactional
    public void addFunds(String userName, Long amountInPaisa) {
        Wallet wallet = walletRepository.findById(userName)
                .orElseGet(() -> {
                    Wallet newWallet = new Wallet(userName, 0L);
                    return walletRepository.save(newWallet);
                });
        
//        Wallet wallet = walletRepository.findByUserId(userId)
//                .orElseThrow(() -> new RuntimeException("User Wallet not found"));

        // Update Balance
        wallet.setBalance(wallet.getBalance() + amountInPaisa); // Simple addition
        walletRepository.save(wallet);
        System.out.println("✅ Wallet Updated: Added " + amountInPaisa + " to User " + userName);
    }
}