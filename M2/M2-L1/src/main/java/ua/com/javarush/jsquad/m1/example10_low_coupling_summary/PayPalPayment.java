package ua.com.javarush.jsquad.m1.example10_low_coupling_summary;

/**
 * Оплата через PayPal.
 */
public class PayPalPayment implements PaymentMethod {

    private final String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("  🅿 PayPal-переказ з акаунту " + email + " на суму " + amount);
        return true;
    }

    @Override
    public String name() {
        return "PayPal";
    }
}
