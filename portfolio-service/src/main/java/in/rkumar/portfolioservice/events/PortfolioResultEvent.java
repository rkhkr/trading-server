package in.rkumar.portfolioservice.events;

public record PortfolioResultEvent(String orderId, boolean isSuccessful, OrderPlacedEvent originalOrder) { }