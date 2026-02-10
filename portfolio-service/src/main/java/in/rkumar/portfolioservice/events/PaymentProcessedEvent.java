package in.rkumar.portfolioservice.events;

import in.rkumar.portfolioservice.enums.OrderType;

public record PaymentProcessedEvent(String orderId, String userName, String ticker, Integer quantity, OrderType orderType, boolean isSuccessful) { }