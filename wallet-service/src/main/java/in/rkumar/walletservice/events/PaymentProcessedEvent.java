package in.rkumar.walletservice.events;

import in.rkumar.walletservice.enums.OrderType;

public record PaymentProcessedEvent(String orderId, String userName, String ticker, Integer quantity, OrderType orderType, boolean isSuccessful) { }