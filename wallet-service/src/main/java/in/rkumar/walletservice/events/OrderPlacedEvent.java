package in.rkumar.walletservice.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import in.rkumar.walletservice.enums.OrderType;

//This must match the fields sent by Order Service exactly
@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderPlacedEvent(String orderId, String userName, String product, Long price, Integer quantity, OrderType orderType) { }