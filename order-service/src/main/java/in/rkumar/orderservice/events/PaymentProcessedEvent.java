package in.rkumar.orderservice.events;

public record PaymentProcessedEvent(String orderId, boolean isSuccessful) { }