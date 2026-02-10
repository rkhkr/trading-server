package in.rkumar.portfolioservice.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rkumar.portfolioservice.models.PortfolioItem;
import in.rkumar.portfolioservice.repositories.PortfolioRepository;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioRepository repository;

    public PortfolioController(PortfolioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<PortfolioItem>> getPortfolio(@RequestHeader("loggedInUser") String userName) {
        
        System.out.println("Fetching portfolio for: " + userName);

        List<PortfolioItem> items = repository.findByUserName(userName);
        
        return items.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(items);
    }
}
