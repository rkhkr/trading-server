package in.rkumar.portfolioservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rkumar.portfolioservice.models.PortfolioItem;

public interface PortfolioRepository extends JpaRepository<PortfolioItem, Long> {
	
    Optional<PortfolioItem> findByUserNameAndTicker(String userName, String ticker);
    
    List<PortfolioItem> findByUserName(String userName);
}