package in.rkumar.walletservice.events;

public record PortfolioResultEvent(Long orderId, boolean isSuccessful, OrderPlacedEvent orderEvent) { }