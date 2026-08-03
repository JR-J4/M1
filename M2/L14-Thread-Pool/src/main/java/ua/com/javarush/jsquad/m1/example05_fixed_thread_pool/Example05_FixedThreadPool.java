package ua.com.javarush.jsquad.m1.example05_fixed_thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Модуль 2. Рівень 14. Thread Pool
 * <hr>
 * <h3>Тема: Fixed Thread Executor — пул із фіксованою кількістю потоків</h3>
 *
 * <p>Метод {@code Executors.newFixedThreadPool(n)} створює ExecutorService
 * із фіксованою кількістю потоків. На відміну від {@code newSingleThreadExecutor},
 * ми самі вказуємо, скільки потоків хочемо бачити в пулі.</p>
 *
 * <h4>Логіка роботи:</h4>
 * <ul>
 *   <li>максимум <b>n</b> потоків будуть активними для обробки задач;</li>
 *   <li>якщо передали більше ніж n задач — вони <b>чекатимуть у черзі</b>,
 *       доки потоки не звільняться;</li>
 *   <li>якщо в роботі одного з потоків станеться збій і він завершиться —
 *       буде створено <b>новий потік</b> на місці зламаного;</li>
 *   <li>будь-який потік із пулу активний доти, доки пул не закрито.</li>
 * </ul>
 *
 * <pre>
 *   ExecutorService executor = Executors.newFixedThreadPool(10);
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> супермаркет із 3 відкритими касами. Скільки б покупців
 * не прийшло, одночасно обслуговують трьох, решта стоїть у черзі. Каси не додаються
 * і не зникають — вони працюють до закриття магазину.</p>
 *
 * <p><b>Реальне застосування:</b> базовий вибір для важких задач — обробка зображень,
 * запити до БД, розрахунки. Кількість потоків беруть близькою до кількості ядер
 * процесора, щоб не перевантажити систему.</p>
 */
public class Example05_FixedThreadPool {

    public static void main(String[] args) throws InterruptedException {

        // === 1. 3 потоки на 9 задач: решта чекає у черзі ===
        // Сценарій: 9 фотографій на обробку, "процесор" тягне лише 3 одночасно.
        System.out.println("=== 1. Три каси — дев'ять покупців ===");
        ThreadPoolExecutor photoPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 9; i++) {
            int photo = i;
            photoPool.execute(() -> {
                System.out.println("  почав фото " + photo + " | " + name());
                sleep(300);
                System.out.println("  готово фото " + photo + " | " + name());
            });
        }

        Thread.sleep(50);
        System.out.println("  >>> потоків у пулі: " + photoPool.getPoolSize()
                + ", у черзі чекають: " + photoPool.getQueue().size());

        photoPool.shutdown();
        photoPool.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("  оброблено задач: " + photoPool.getCompletedTaskCount()
                + " — трьома потоками, трьома 'хвилями'");
        System.out.println();

        // === 2. Кількість потоків = кількість ядер ===
        // Більше потоків, ніж ядер, для обчислювальних задач зазвичай НЕ прискорює.
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("=== 2. Скільки потоків брати ===");
        System.out.println("  ядер доступно JVM: " + cores);
        System.out.println("  обчислення (CPU)  -> ~" + cores + " потоків");
        System.out.println("  мережа/файли (IO) -> можна більше: потоки все одно чекають");
        System.out.println();

        // === 3. Потік зламався — пул створює новий на його місці ===
        // Пул із 2 потоків: одна задача падає, але пул лишається робочим.
        System.out.println("=== 3. Заміна зламаного потоку ===");
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        pool.execute(() -> System.out.println("  задача 1 ок     | " + name()));
        pool.execute(() -> {
            System.out.println("  задача 2 падає  | " + name());
            throw new RuntimeException("збій диска");
        });
        sleep(200);
        pool.execute(() -> System.out.println("  задача 3 ок     | " + name()));
        pool.execute(() -> System.out.println("  задача 4 ок     | " + name()));

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("  пул вижив: виконано " + pool.getCompletedTaskCount() + " задач");
        System.out.println();

        // === 4. Потоки живуть, доки пул не закрито ===
        // Навіть коли роботи немає, потоки нікуди не зникають — вони чекають нових задач.
        System.out.println("=== 4. Потоки не зникають між задачами ===");
        ThreadPoolExecutor idle = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        idle.execute(() -> System.out.println("  разова задача | " + name()));
        Thread.sleep(300);                       // роботи давно немає
        System.out.println("  роботи немає, а потоків у пулі досі: " + idle.getPoolSize());
        System.out.println("  саме тому пул треба закривати вручну — shutdown()");
        idle.shutdown();

        ExecutorService reminder = idle;
        System.out.println("  після shutdown: isShutdown = " + reminder.isShutdown());
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
