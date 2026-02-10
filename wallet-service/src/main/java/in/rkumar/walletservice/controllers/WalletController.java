package in.rkumar.walletservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rkumar.walletservice.repositories.WalletRepository;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletRepository walletRepository;

    public WalletController(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @GetMapping
    public ResponseEntity<Long> getBalance(@RequestHeader("loggedInUser") String userName) {
        return walletRepository.findById(userName)
                .map(wallet -> ResponseEntity.ok(wallet.getBalance()))
                .orElse(ResponseEntity.notFound().build());
    }
}