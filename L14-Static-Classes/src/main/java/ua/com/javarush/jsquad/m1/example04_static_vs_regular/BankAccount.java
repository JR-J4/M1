package ua.com.javarush.jsquad.m1.example04_static_vs_regular;

/**
 * Клас BankAccount — демонстрація статичних vs звичайних методів.
 *
 * Статичний метод getExchangeRate() — спільний для всіх, не потребує об'єкта.
 * Звичайні методи deposit(), withdraw(), getBalance() — працюють з конкретним рахунком.
 */
public class BankAccount {

    // Статична змінна — курс долара (один для всіх рахунків)
    private static double exchangeRateUSD = 41.50;
    private static double exchangeRateEUR = 50.50;

    // Нестатичні змінні — у кожного рахунку свої
    private String owner;
    private double balance;

    public BankAccount(String owner, double initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
    }

    // --- Статичні методи (прив'язані до класу) ---

    // Отримати поточний курс — не потрібен об'єкт
    public static double getExchangeRate() {
        return exchangeRateUSD;
    }

    // Змінити курс — впливає на всі рахунки
    public static void setExchangeRate(double newRate) {
        exchangeRateUSD = newRate;
    }

    // Конвертувати гривні в долари — утилітний метод
    public static double convertToUSD(double uah) {
        return convert(uah, exchangeRateUSD);
    }

    // Конвертувати гривні в долари — утилітний метод
    public static double convertToEUR(double uah) {
        return convert(uah, exchangeRateEUR);
    }

    public static double convertToEUR(double uah, double rate) {
        return convert(uah, rate);
    }

    private static double convert(double input, double rate) {
        return input / rate;
    }

    // --- Звичайні (нестатичні) методи (прив'язані до об'єкта) ---

    // Поповнити рахунок — потрібен конкретний рахунок
    public void deposit(double amount) {
        balance += amount;
        System.out.println("  " + owner + ": поповнення +" + amount + " грн. Баланс: " + balance + " грн");
    }

    // Зняти кошти — потрібен конкретний рахунок
    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("  " + owner + ": недостатньо коштів!");
        } else {
            balance -= amount;
            System.out.println("  " + owner + ": зняття -" + amount + " грн. Баланс: " + balance + " грн");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    // Звичайний метод може звертатися і до статичних змінних!
    public void showBalanceInUSD() {
        double usd = balance / exchangeRateUSD;
        System.out.println("  " + owner + ": " + balance + " грн = "
                + String.format("%.2f", usd) + " USD (курс: " + exchangeRateUSD + ")");
    }
}
