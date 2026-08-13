package ua.com.javarush.jsquad.m1.example09_real_world;

/**
 * Модуль 2. Рівень 11. Знайомство з потоками
 * <hr>
 * <h3>Тема: Реальний сценарій — завантаження екрану застосунку</h3>
 *
 * <p>Коли ви відкриваєте застосунок, екран часто збирається з кількох
 * <b>незалежних</b> частин: аватар, стрічка новин, сповіщення. Кожна тягне свої
 * дані з сервера ~1 секунду.</p>
 *
 * <pre>
 *   • По черзі (один потік):   1 + 1 + 1 = ~3 секунди
 *   • Паралельно (три потоки): усі три разом = ~1 секунда
 * </pre>
 *
 * <p>Оскільки задачі не залежать одна від одної, немає сенсу чекати завершення
 * однієї, щоб почати наступну — запускаємо їх одночасно.</p>
 *
 * <p><b>Аналогія з життя:</b> варите каву, смажите тост і чавите сік. Робити це
 * по черзі — довго. Робити одночасно — сніданок готовий утричі швидше.</p>
 */
public class Example09_RealWorld {

    // Імітація завантаження однієї частини екрана (~1 секунда).
    static void load(String part) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("завантажено: " + part);
    }

    public static void main(String[] args) throws InterruptedException {

        // === 1. По черзі — повільно ===
        System.out.println("=== По черзі (один потік) ===");
        long start = System.currentTimeMillis();
        load("аватар");
        load("стрічка новин");
        load("сповіщення");
        System.out.println("Разом: " + (System.currentTimeMillis() - start) + " мс");
        System.out.println();

        // === 2. Паралельно — швидко ===
        System.out.println("=== Паралельно (три потоки) ===");
        start = System.currentTimeMillis();

        Thread t1 = new Thread(() -> load("аватар"));
        Thread t2 = new Thread(() -> load("стрічка новин"));
        Thread t3 = new Thread(() -> load("сповіщення"));

        t1.start();
        t2.start();
        t3.start();

        // Чекаємо, поки всі три завершаться
        t1.join();
        t2.join();
        t3.join();

        System.out.println("Разом: " + (System.currentTimeMillis() - start) + " мс");
    }
}
