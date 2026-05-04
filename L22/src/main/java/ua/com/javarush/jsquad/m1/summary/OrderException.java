package ua.com.javarush.jsquad.m1.summary;

public class OrderException extends Exception {
  private final String orderId;

  OrderException(String orderId, String message) {
    super(message);
    this.orderId = orderId;
  }

  String getOrderId() {
    return orderId;
  }
}