package ua.com.javarush.jsquad.m1.example10_low_coupling_summary;

public class NewPaymentMethod implements PaymentMethod {
  @Override
  public boolean pay(double amount) {
    return true;
  }

  @Override
  public String name() {
    return "Test";
  }
}
