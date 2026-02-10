package in.rkumar.walletservice;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import in.rkumar.walletservice.models.Wallet;
import in.rkumar.walletservice.repositories.WalletRepository;

@SpringBootApplication
public class WalletServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletServiceApplication.class, args);
    }

    // This runs automatically on startup
    @Bean
    public CommandLineRunner loadData(WalletRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(
                		Arrays.asList(
	                        new Wallet("101", 1000000L),
	                        new Wallet("102", 10000000L),
	                        new Wallet("103", 5000L),
	                        new Wallet("104", 1000L)
                ));
            }
        };
    }
}
