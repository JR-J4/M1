package ua.com.javarush.jsquad.m1.example03_single_thread_executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Модуль 2. Рівень 14. Thread Pool
 * <hr>
 * <h3>Тема: Single Thread Executor — пул із єдиного потоку</h3>
 *
 * <p>Метод {@code Executors.newSingleThreadExecutor()} створює ExecutorService
 * із пулом, що містить <b>один</b> потік.</p>
 *
 * <h4>Логіка роботи цього пулу:</h4>
 * <ul>
 *   <li>сервіс виконує за раз лише <b>одну</b> задачу;</li>
 *   <li>якщо надіслати N задач — усі N виконає один потік, <b>одна за одною</b>,
 *       у порядку надходження;</li>
 *   <li>якщо потік перервано (наприклад, задача кинула виняток) — створюється
 *       <b>новий</b> потік для виконання решти задач.</li>
 * </ul>
 *
 * <pre>
 *   ExecutorService executor = Executors.newSingleThreadExecutor();
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> єдине віконце в поліклініці. Скільки б людей не
 * прийшло — приймають строго по одному, у порядку черги. Якщо медсестра захворіла,
 * на її місце виходить інша, і черга рухається далі.</p>
 *
 * <p><b>Реальне застосування:</b> запис у файл логу, послідовне збереження в БД,
 * обробка подій UI — усе, де важливий <b>порядок</b> і неприпустима одночасність.</p>
 */
public class Example03_SingleThreadExecutor {

    public static void main(String[] args) throws Exception {

        // === 1. N задач — один потік, суворо по черзі ===
        // Сценарій: журнал операцій. Записи не повинні перемішатися.
        System.out.println("=== 1. Порядок виконання гарантовано ===");
        ExecutorService logger = Executors.newSingleThreadExecutor();

        for (int i = 1; i <= 5; i++) {
            int number = i;
            logger.execute(() -> {
                sleep(100);
                System.out.println("  запис #" + number + " зроблено потоком " + name());
            });
        }

        logger.shutdown();
        logger.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("  усі 5 записів зробив ОДИН і той самий потік, по порядку.");
        System.out.println();

        // === 2. Порівняння: той самий код у пулі з 3 потоків ===
        // Порядок уже не гарантований — задачі йдуть паралельно.
        System.out.println("=== 2. А що було б у пулі з 3 потоків ===");
        ExecutorService multi = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 5; i++) {
            int number = i;
            multi.execute(() -> {
                sleep(100);
                System.out.println("  запис #" + number + " зроблено потоком " + name());
            });
        }

        multi.shutdown();
        multi.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("  порядок міг порушитися — записи перемішалися.");
        System.out.println();

        // === 3. Задача впала з винятком — пул піднімає новий потік ===
        // execute(): виняток "вбиває" потік, і сервіс мовчки створює заміну.
        System.out.println("=== 3. Потік зламався -> створюється новий ===");
        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(() -> System.out.println("  задача 1 (ок)      | потік " + name()));
        service.execute(() -> {
            System.out.println("  задача 2 (впаде)   | потік " + name());
            throw new IllegalStateException("сервер недоступний");
        });
        sleep(200);                                  // хай стек-трейс встигне надрукуватися
        service.execute(() -> System.out.println("  задача 3 (ок)      | потік " + name()));

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("  зверніть увагу: у задачі 3 інше ім'я потоку — це вже НОВИЙ потік,");
        System.out.println("  але сервіс продовжив працювати і решту задач виконав.");
        System.out.println();

        // === 4. submit() ловить виняток у Future — потік не гине ===
        // Це найчастіша причина "мовчазних" помилок: без future.get() ви їх не побачите.
        System.out.println("=== 4. submit(): виняток чекає всередині Future ===");
        ExecutorService safe = Executors.newSingleThreadExecutor();

        Future<?> failed = safe.submit(() -> {
            throw new IllegalArgumentException("невірний код товару");
        });

        sleep(100);
        System.out.println("  задача завершилась? " + failed.isDone() + " — а помилки не видно");
        try {
            failed.get();                            // ось тут виняток і "вистрілить"
        } catch (Exception e) {
            System.out.println("  future.get() кинув: " + e.getCause().getMessage());
        }

        safe.shutdown();
        safe.awaitTermination(1, TimeUnit.MINUTES);
    }

    private static String name() {
        return Thread.currentThread().getName();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
