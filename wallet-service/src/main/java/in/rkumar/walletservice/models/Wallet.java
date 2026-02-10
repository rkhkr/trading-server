package in.rkumar.walletservice.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private Long balance;

    // Constructors
    public Wallet() {}

    public Wallet(String userName, Long balance) {
        this.userName = userName;
        this.balance = balance;
    }

    // Getters and Setters
    public String getUserName() { return userName; }
    public Long getBalance() { return balance; }
    public void setBalance(Long balance) { this.balance = balance; }
}
