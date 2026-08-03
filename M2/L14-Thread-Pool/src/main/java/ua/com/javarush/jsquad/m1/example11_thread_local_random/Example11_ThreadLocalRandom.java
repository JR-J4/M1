package ua.com.javarush.jsquad.m1.example11_thread_local_random;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Модуль 2. Рівень 14. Thread Pool
 * <hr>
 * <h3>Тема: ThreadLocalRandom — випадкові числа для багатопотокового коду</h3>
 *
 * <p>{@code java.util.concurrent.ThreadLocalRandom} — це службовий клас, який ввели
 * з <b>JDK 1.7</b>. Він більш корисний, коли для генерації випадкових чисел потрібно
 * кілька потоків чи задач ForkJoinTasks.</p>
 *
 * <p>ThreadLocalRandom — це комбінація класів <b>ThreadLocal</b> та <b>Random</b>,
 * ізольована для нинішнього потоку. Таким чином, він досягає кращої продуктивності
 * в багатопотоковому середовищі, уникаючи будь-якого паралельного доступу до
 * екземплярів Random.</p>
 *
 * <p>Випадкове число, яке отримує один потік, не залежить від іншого потоку, тоді як
 * {@code java.util.Random} надає випадкові числа <b>глобально</b>: усі потоки б'ються
 * за одне поле seed через CAS, і чим більше потоків — тим більше простоїв.</p>
 *
 * <pre>
 *   int dice = ThreadLocalRandom.current().nextInt(1, 7);   // 1..6
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> одна колода карт на весь стіл (Random) — гравці штовхаються
 * і чекають одне одного. Своя колода в кожного (ThreadLocalRandom) — тасуй коли хочеш.</p>
 *
 * <p><b>Реальне застосування:</b> навантажувальні тести, ігрова логіка, випадкові
 * затримки повторних спроб (retry backoff), генерація тестових даних у пулі потоків.</p>
 */
public class Example11_ThreadLocalRandom {

    public static void main(String[] args) throws InterruptedException {

        // === 1. Базове використання: current() замість new ===
        // Об'єкт не створюють — його беруть у поточного потоку.
        System.out.println("=== 1. ThreadLocalRandom.current() ===");
        ThreadLocalRandom random = ThreadLocalRandom.current();
        System.out.println("  кубик (1..6)        : " + random.nextInt(1, 7));
        System.out.println("  число 0..99         : " + random.nextInt(100));
        System.out.println("  дробове 0.0..1.0    : " + random.nextDouble());
        System.out.println("  ціна 10.0..99.99    : " + String.format("%.2f", random.nextDouble(10, 100)));
        System.out.println("  так/ні              : " + random.nextBoolean());
        System.out.println("  Random так не вміє: у нього немає nextInt(origin, bound)");
        System.out.println();

        // === 2. У кожного потоку свій стан генератора ===
        System.out.println("=== 2. Свій генератор у кожного потоку ===");
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 3; i++) {
            int player = i;
            pool.execute(() -> System.out.println("  гравець " + player + " кинув кубик: "
                    + ThreadLocalRandom.current().nextInt(1, 7)
                    + " | " + name()));
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("  current() повертає один спільний об'єкт для всіх потоків,");
        System.out.println("  але стан генератора (seed) зберігається в полях самого потоку —");
        System.out.println("  тож потоки не заважають одне одному й синхронізація не потрібна.");

        // Саме тому seed задати вручну не можна — він належить потоку, а не об'єкту.
        try {
            ThreadLocalRandom.current().setSeed(42);
        } catch (UnsupportedOperationException e) {
            System.out.println("  setSeed(42) -> " + e.getClass().getSimpleName()
                    + " (потрібні відтворювані числа? беріть new Random(42))");
        }
        System.out.println();

        // === 3. Швидкість під навантаженням: Random проти ThreadLocalRandom ===
        // 4 потоки по 2 000 000 чисел. Спільний Random змушує потоки конкурувати.
        System.out.println("=== 3. Спільний Random vs ThreadLocalRandom (4 потоки) ===");
        final int THREADS = 4;
        final int NUMBERS = 2_000_000;

        Random shared = new Random();
        long sharedTime = race(THREADS, NUMBERS, () -> shared.nextInt(100));
        long localTime = race(THREADS, NUMBERS, () -> ThreadLocalRandom.current().nextInt(100));

        System.out.println("  один спільний Random : " + sharedTime + " мс");
        System.out.println("  ThreadLocalRandom    : " + localTime + " мс");
        System.out.println("  Різниця саме через конкуренцію потоків за спільний seed.");
        System.out.println();

        // === 4. Потоки генерації чисел ===
        System.out.println("=== 4. Потоки (streams) випадкових чисел ===");
        System.out.print("  5 чисел 1..49: ");
        ThreadLocalRandom.current().ints(5, 1, 50).forEach(number -> System.out.print(number + " "));
        System.out.println();
        System.out.println("  сума 1000 випадкових чисел 1..10: "
                + ThreadLocalRandom.current().ints(1000, 1, 11).sum());
        System.out.println();

        // === 5. Практика: випадкові паузи в задачах пулу ===
        // Класичний прийом — імітувати "нерівну" тривалість роботи.
        System.out.println("=== 5. Імітація нерівномірних задач ===");
        ExecutorService workers = Executors.newFixedThreadPool(2);
        AtomicLong totalMillis = new AtomicLong();

        for (int i = 1; i <= 4; i++) {
            int task = i;
            workers.execute(() -> {
                int duration = ThreadLocalRandom.current().nextInt(100, 400);
                totalMillis.addAndGet(duration);
                sleep(duration);
                System.out.println("  задача " + task + " тривала " + duration + " мс | " + name());
            });
        }
        workers.shutdown();
        workers.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("  сумарна 'робота' задач: " + totalMillis.get() + " мс,");
        System.out.println("  але виконали її двоє паралельно — приблизно вдвічі швидше.");
    }

    /** Запускає THREADS потоків, кожен генерує NUMBERS чисел. Повертає час у мс. */
    private static long race(int threads, int numbers, java.util.function.IntSupplier generator)
            throws InterruptedException {

        Thread[] pack = new Thread[threads];
        long start = System.currentTimeMillis();

        for (int i = 0; i < threads; i++) {
            pack[i] = new Thread(() -> {
                int sum = 0;
                for (int j = 0; j < numbers; j++) {
                    sum += generator.getAsInt();
                }
                if (sum < 0) {                    // щоб JIT не викинув цикл як непотрібний
                    System.out.print("");
                }
            });
            pack[i].start();
        }
        for (Thread thread : pack) {
            thread.join();
        }
        return System.currentTimeMillis() - start;
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
