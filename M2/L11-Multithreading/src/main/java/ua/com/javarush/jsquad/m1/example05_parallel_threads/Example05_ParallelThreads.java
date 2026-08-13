package ua.com.javarush.jsquad.m1.example05_parallel_threads;

/**
 * Модуль 2. Рівень 11. Знайомство з потоками
 * <hr>
 * <h3>Тема: Кілька потоків одночасно — паралельне виконання</h3>
 *
 * <p>Коли запущено кілька потоків, процесор перемикається між ними сотні разів
 * на секунду. Тому їхній вивід <b>перемішується</b>, а порядок рядків
 * <b>не гарантований</b> — його визначає планувальник ОС, а не наш код.</p>
 *
 * <h4>Корисне:</h4>
 * <pre>
 *   • thread.join() — головний потік ЧЕКАЄ, поки дочірній завершиться.
 *   • Thread.currentThread().getName() — ім'я потоку, зручно для логів.
 *   • можна дати потоку своє ім'я: new Thread(task, "Кур'єр-1").
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> двоє кур'єрів розвозять замовлення одночасно. Хто
 * доставить першим — залежить від пробок, а не від того, кого відправили раніше.</p>
 *
 * <p><b>Реальне застосування:</b> завантаження кількох файлів паралельно —
 * разом швидше, ніж по черзі.</p>
 */
public class Example05_ParallelThreads {

    public static void main(String[] args) throws InterruptedException {

        // === 1. Завдання для кур'єра: доставити 3 замовлення ===
        // Один і той самий Runnable віддамо двом різним потокам.
        Runnable courierJob = () -> {
            String name = Thread.currentThread().getName();
            for (int order = 1; order <= 10; order++) {
                System.out.println(name + " доставив замовлення #" + order);
                try {
                    Thread.sleep(100);       // імітація дороги
                } catch (InterruptedException e) {
                    return;
                }
            }
        };

        // === 2. Запускаємо два потоки одночасно ===
        System.out.println("=== Два кур'єри працюють паралельно ===");
        Thread courier1 = new Thread(courierJob, "Кур'єр-1");
        Thread courier2 = new Thread(courierJob, "Кур'єр-2");
        courier1.start();
        courier2.start();

        // === 3. Чекаємо завершення обох через join() ===
        courier1.join();     // головний потік стоїть тут, поки Кур'єр-1 не закінчить
        courier2.join();
        System.out.println();
        System.out.println("Усі замовлення доставлено. Рядки вище — перемішані, "
                + "бо потоки працювали одночасно.");
    }
}
