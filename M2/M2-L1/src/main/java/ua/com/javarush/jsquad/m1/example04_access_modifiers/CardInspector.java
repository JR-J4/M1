package ua.com.javarush.jsquad.m1.example04_access_modifiers;

/**
 * Клас із ТОГО Ж пакета, що й {@link BankCard}.
 *
 * <p>Показує, до чого можна дотягтися "сусіду по пакету":
 * до {@code public}, {@code protected} та package-default — так.
 * До {@code private} — ні.</p>
 */
public class CardInspector {

    public void inspect(BankCard card) {
        // ✔ public — доступно
        System.out.println("  Номер картки (public):   " + card.cardNumber);

        // ✔ package-default — доступно, бо ми в тому ж пакеті
        System.out.println("  Власник (package):       " + card.holderName);

        // ✔ protected — доступно для сусіда по пакету
        System.out.println("  Баланс (protected):      " + card.balance);

        // ✖ private — НЕ доступно навіть із того ж пакета:
        // System.out.println(card.pinCode); // ПОМИЛКА КОМПІЛЯЦІЇ!
        System.out.println("  PIN (private):           ✖ недоступний навіть тут!");
    }
}
