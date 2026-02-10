package in.rkumar.portfolioservice.models;

import in.rkumar.portfolioservice.enums.OrderType;
import jakarta.persistence.*;

@Entity
@Table(name = "portfolio_item", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"userId", "ticker"}) // One row per user+stock combo
})
public class PortfolioItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String ticker;

    @Column(nullable = false)
    private Integer quantity;
    
	@Column(nullable = false, length = 1) // Ensures DB column is CHAR(1)
	private OrderType orderType;

    // Constructors
    public PortfolioItem() {}

    public PortfolioItem(String userName, String ticker, Integer quantity, OrderType orderType) {
        this.userName = userName;
        this.ticker = ticker;
        this.quantity = quantity;
        this.orderType = orderType;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getUserId() { return userName; }
    public String getTicker() { return ticker; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setOrderType(OrderType orderType) { this.orderType = orderType; }
    public OrderType getOrderType() { return orderType; }
}