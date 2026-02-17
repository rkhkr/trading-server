package in.rkumar.orderservice.models;

import java.time.LocalDateTime;

import in.rkumar.orderservice.enums.OrderStatus;
import in.rkumar.orderservice.enums.OrderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String userName;
	
	private String product; // e.g., "TSLA", "GOOGL"
	private Long price;
	private Integer quantity;

	@Column(nullable = false, length = 1) // Ensures DB column is CHAR(1)
	private OrderType orderType;
	
	@Enumerated(EnumType.STRING) // Stores "PENDING", "FAILED" in DB
    @Column(nullable = false)
    private OrderStatus status;

    private LocalDateTime createdAt;

	// Constructors
	public Order() { }

	public Order(String userName, String product, Long price, Integer quantity, OrderType orderType) {
		this.userName = userName;
		this.product = product;
		this.price = price;
		this.quantity = quantity;
		this.orderType = orderType;
		this.status = OrderStatus.PENDING; // Default status
	}
	
	@PrePersist // Auto-fill the date when saving
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

	// Getters and Setters (Omitted for brevity, generate them in your IDE)
	public Long getId() { return id; }

	public void setStatus(OrderStatus status) { this.status = status; }
	public OrderStatus getStatus() { return status; }

	public OrderType getOrderType() { return orderType; }
	public void setOrderType(OrderType orderType) { this.orderType = orderType; }
	
	public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
