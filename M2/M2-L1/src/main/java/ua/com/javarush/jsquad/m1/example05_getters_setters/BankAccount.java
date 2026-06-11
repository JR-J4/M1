package ua.com.javarush.jsquad.m1.example05_getters_setters;

/**
 * Банківський рахунок — показує, як гетери та сетери захищають
 * <b>валідний внутрішній стан</b> об'єкта.
 *
 * <p>Дві переваги інкапсуляції зі слайду лекції в дії:</p>
 * <ol>
 *   <li><b>Валідний внутрішній стан:</b> баланс ніколи не стане від'ємним —
 *       об'єкт сам стежить за змінами своїх даних.</li>
 *   <li><b>Контроль аргументів:</b> усі дані, що передаються, перевіряються
 *       на відповідність логіці класу (не можна покласти від'ємну суму).</li>
 * </ol>
 *
 * <pre>
 *   deposit(-500)  ─▶  перевірка  ─▶  ✖ відхилено (IllegalArgumentException)
 *   deposit(500)   ─▶  перевірка  ─▶  ✔ balance += 500
 * </pre>
 */
public class BankAccount {

    private final String owner;
    private double balance; // приховано: міняється лише через методи

    public BankAccount(String owner) {
        this.owner = owner;
        this.balance = 0;
    }

    // GETTER — лише читання. Сетера для balance НЕМАЄ навмисно:
    // баланс не можна "просто присвоїти", лише покласти/зняти за правилами.
    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    // Контроль аргументів: від'ємну суму покласти неможливо
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сума поповнення має бути > 0, отримано: " + amount);
        }
        balance += amount;
        System.out.println("  ✔ Поповнення " + amount + " → баланс " + balance);
    }

    // Валідний стан: не дозволяємо піти "в мінус"
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сума зняття має бути > 0, отримано: " + amount);
        }
        if (amount > balance) {
            System.out.println("  ✖ Недостатньо коштів: на рахунку лише " + balance);
            return;
        }
        balance -= amount;
        System.out.println("  ✔ Знято " + amount + " → баланс " + balance);
    }
}
