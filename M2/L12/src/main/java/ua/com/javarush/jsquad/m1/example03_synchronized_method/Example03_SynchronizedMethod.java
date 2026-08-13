package ua.com.javarush.jsquad.m1.example03_synchronized_method;

/**
 * Модуль 2. Рівень 12. Знайомство з потоками. Частина 2
 * <hr>
 * <h3>Тема: synchronized-метод — замок на весь метод</h3>
 *
 * <p>Словом {@code synchronized} можна позначити не лише <b>шматок коду</b> (блок),
 * а й <b>цілий метод</b>. Це просто коротший запис того самого:</p>
 * <pre>
 *   public synchronized void deposit(int sum) { ... }
 *   // те саме, що:
 *   public void deposit(int sum) { synchronized (this) { ... } }
 * </pre>
 *
 * <p><b>Важливо, який об'єкт стає замком:</b></p>
 * <pre>
 *   • звичайний synchronized-метод   -> замок = this (сам об'єкт);
 *   • static synchronized-метод      -> замок = об'єкт КЛАСу (Xxx.class),
 *                                        спільний для всіх екземплярів.
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> synchronized-метод — це кімната з одними дверима на
 * замку: поки хтось усередині щось робить, інші чекають під дверима.</p>
 *
 * <p><b>Реальне застосування:</b> банківський рахунок — {@code deposit}/{@code withdraw}
 * мають виконуватися по одному, інакше баланс "попливе".</p>
 */
public class Example03_SynchronizedMethod {

    static class BankAccount {
        private int balance;

        BankAccount(int balance) {
            this.balance = balance;
        }

        // Замок — this. Поки один потік поповнює, інший чекає.
        public synchronized void deposit(int sum) {
            balance += sum;
        }

        public synchronized int getBalance() {
            return balance;
        }
    }

    // static synchronized -> спільний лічильник усіх операцій (замок = клас).
    static int operations = 0;

    static synchronized void registerOperation() {
        operations++;
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== synchronized-метод: 100 вкладників поповнюють один рахунок ===");
        BankAccount account = new BankAccount(0);

        final int CLIENTS = 100;
        final int PER_CLIENT = 1;   // кожен вносить по 1 грн PER_CLIENT разів

        Runnable client = () -> {
            for (int i = 0; i < PER_CLIENT; i++) {
                account.deposit(1);
                registerOperation();
            }
        };

        Thread[] threads = new Thread[CLIENTS];
        for (int i = 0; i < CLIENTS; i++) {
            threads[i] = new Thread(client);
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();          // чекаємо всіх
        }

        System.out.println("Очікуваний баланс: " + (CLIENTS * PER_CLIENT));
        System.out.println("Фактичний баланс:  " + account.getBalance());
        System.out.println("Усього операцій (static synchronized): " + operations);
        System.out.println();
        System.out.println("Обидва числа збігаються — синхронізовані методи не дали");
        System.out.println("потокам затерти зміни один одного.");
    }
}
