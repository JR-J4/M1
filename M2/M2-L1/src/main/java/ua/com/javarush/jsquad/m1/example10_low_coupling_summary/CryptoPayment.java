package ua.com.javarush.jsquad.m1.example10_low_coupling_summary;

/**
 * Оплата криптовалютою. ДОДАНА пізніше — і магазин (OnlineStore)
 * не довелось змінювати ані на рядок. Це і є перевага слабкої зв'язності.
 */
public class CryptoPayment implements PaymentMethod {

    private final String wallet;

    public CryptoPayment(String wallet) {
        this.wallet = wallet;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("  ₿ Криптопереказ з гаманця " + wallet + " на суму " + amount);
        return true;
    }

    @Override
    public String name() {
        return "Криптовалюта";
    }
}
