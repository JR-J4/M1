package ua.com.javarush.jsquad.m1.example04_access_modifiers;

/**
 * Банківська картка — демонструє <b>4 рівні доступу</b> в Java.
 *
 * <p>Із приховуванням даних нам допомагають <b>модифікатори доступу</b>.
 * Від найсуворішого до найвідкритішого:</p>
 *
 * <pre>
 *  ┌───────────────┬──────────┬───────────────┬───────────┬─────────┐
 *  │ модифікатор   │ той клас │ той же пакет  │ нащадки   │ усюди   │
 *  ├───────────────┼──────────┼───────────────┼───────────┼─────────┤
 *  │ private       │    ✔     │      ✖        │    ✖     │   ✖    │
 *  │ (package)     │    ✔     │      ✔        │    ✖*    │   ✖    │
 *  │ protected     │    ✔     │      ✔        │    ✔     │   ✖    │
 *  │ public        │    ✔     │      ✔        │    ✔     │   ✔    │
 *  └───────────────┴──────────┴───────────────┴───────────┴─────────┘
 *   * нащадок з ІНШОГО пакета package-default поле не бачить
 * </pre>
 */
public class BankCard {

    // public — видно звідусіль (номер картки можна показувати)
    public String cardNumber;

    // protected — видно в цьому класі, у пакеті та нащадкам
    protected double balance;

    // package-default (без слова) — видно лише в межах цього пакета
    String holderName;

    // private — видно ЛИШЕ всередині цього класу (найсекретніше — CVV/PIN)
    private int pinCode;

    public BankCard(String cardNumber, String holderName, int pinCode) {
        this.cardNumber = cardNumber;
        this.holderName = holderName;
        this.pinCode = pinCode;
        this.balance = 0;
    }

    // private-метод використовується лише всередині класу
    private boolean checkPin(int input) {
        return input == pinCode;
    }

    // public-метод дає КОНТРОЛЬОВАНИЙ доступ до приватного pinCode
    public void withdraw(int pin, double amount) {
        if (checkPin(pin)) {
            balance -= amount;
            System.out.println("  ✔ Знято " + amount + ". Залишок: " + balance);
        } else {
            System.out.println("  ✖ Невірний PIN! Доступ заборонено.");
        }
    }
}
