package in.rkumar.orderservice.events;

import in.rkumar.orderservice.enums.OrderType;

public record OrderPlacedEvent(String orderId, String userName, String product, Long price, Integer quantity, OrderType orderType) { }