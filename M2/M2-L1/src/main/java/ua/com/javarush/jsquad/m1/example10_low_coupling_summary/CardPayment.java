package ua.com.javarush.jsquad.m1.example10_low_coupling_summary;

/**
 * Оплата банківською карткою. Інкапсулює номер картки (private).
 */
public class CardPayment implements PaymentMethod {

    private final String cardNumber; // приховано — інкапсуляція

    public CardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(double amount) {
        // показуємо лише останні 4 цифри — решта прихована
        String masked = "**** " + cardNumber.substring(cardNumber.length() - 4);
        System.out.println("  💳 Оплата карткою " + masked + " на суму " + amount);
        return true;
    }

    @Override
    public String name() {
        return "Банківська картка";
    }
}
