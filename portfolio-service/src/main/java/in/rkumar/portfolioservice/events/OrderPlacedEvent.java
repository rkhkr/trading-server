package in.rkumar.portfolioservice.events;

import in.rkumar.portfolioservice.enums.OrderType;

public record OrderPlacedEvent(String orderId, String userName, String product, Long price, Integer quantity, OrderType orderType) { }