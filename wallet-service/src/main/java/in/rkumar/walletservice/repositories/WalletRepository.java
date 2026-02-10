package in.rkumar.walletservice.repositories;

import in.rkumar.walletservice.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, String> {
	// Optional<Wallet> findByUserName(String userName); // byDefault available
}