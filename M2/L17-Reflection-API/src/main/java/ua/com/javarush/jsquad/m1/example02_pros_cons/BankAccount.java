package ua.com.javarush.jsquad.m1.example02_pros_cons;

/**
 * Клас, який дуже старанно захищає свій стан:
 * поле {@code balance} приватне, а покласти гроші можна лише через {@code deposit()},
 * який перевіряє суму.
 *
 * <p>У прикладі 02 ми побачимо, що рефлексія обходить увесь цей захист.</p>
 */
public class BankAccount {

    private final String owner;
    private double balance;

    public BankAccount(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    /** Єдиний "легальний" спосіб змінити баланс — з перевіркою. */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сума має бути додатною!");
        }
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }
}
