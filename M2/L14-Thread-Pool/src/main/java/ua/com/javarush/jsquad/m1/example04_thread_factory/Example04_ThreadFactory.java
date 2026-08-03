package ua.com.javarush.jsquad.m1.example04_thread_factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Модуль 2. Рівень 14. Thread Pool
 * <hr>
 * <h3>Тема: ThreadFactory — своя фабрика потоків для пулу</h3>
 *
 * <p><b>ThreadFactory</b> — це об'єкт, що створює нові потоки за потребою.
 * У ньому лише один метод:</p>
 *
 * <pre>
 *   Thread newThread(Runnable r);
 * </pre>
 *
 * <p>Свою фабрику можна передати в будь-який метод Executors:</p>
 *
 * <pre>
 *   Executors.newSingleThreadExecutor(ThreadFactory threadFactory);
 *   Executors.newFixedThreadPool(int nThreads, ThreadFactory threadFactory);
 * </pre>
 *
 * <p>За допомогою фабрики можна налаштувати потоки, що створюються: задати
 * <b>зрозумілі імена</b>, встановити <b>пріоритети</b>, зробити потоки
 * <b>daemon</b>, використати підкласи Thread, додати
 * <b>UncaughtExceptionHandler</b> та інше.</p>
 *
 * <p><b>Аналогія з життя:</b> відділ кадрів із власними правилами. Кожному новому
 * працівнику одразу видають бейдж з іменем ("payment-worker-1"), визначають
 * пріоритет завдань і призначають керівника, який розбирає його аварії.</p>
 *
 * <p><b>Реальне застосування:</b> у логах продакшену "pool-1-thread-3" не каже нічого,
 * а "email-sender-3" одразу показує, який саме підсистемі належить потік.</p>
 */
public class Example04_ThreadFactory {

    public static void main(String[] args) throws InterruptedException {

        // === 1. Фабрика за замовчуванням дає безликі імена ===
        System.out.println("=== 1. Стандартна фабрика ===");
        ExecutorService defaultPool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            defaultPool.execute(() -> System.out.println("  потік: " + name()));
        }
        defaultPool.shutdown();
        defaultPool.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println();

        // === 2. Своя фабрика: зрозумілі імена + пріоритет ===
        // Сценарій: підсистема відправки листів. Хочемо бачити її потоки в логах.
        System.out.println("=== 2. Своя фабрика: імена та пріоритет ===");
        ThreadFactory emailFactory = new NamedThreadFactory("email-sender", Thread.NORM_PRIORITY + 2);

        ExecutorService mailPool = Executors.newFixedThreadPool(3, emailFactory);
        for (int i = 1; i <= 3; i++) {
            int letter = i;
            mailPool.execute(() -> {
                Thread current = Thread.currentThread();
                System.out.println("  лист #" + letter + " відправив " + current.getName()
                        + " (пріоритет " + current.getPriority()
                        + ", daemon=" + current.isDaemon() + ")");
            });
        }
        mailPool.shutdown();
        mailPool.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("  усього створено потоків фабрикою: " + NamedThreadFactory.created());
        System.out.println();

        // === 3. UncaughtExceptionHandler — єдине місце для аварій ===
        // Задача падає, а обробник з фабрики фіксує це замість "мовчазного" стек-трейсу.
        System.out.println("=== 3. Обробник неперехоплених винятків ===");

        ThreadFactory guardedFactory = runnable -> {
            Thread thread = new Thread(runnable, "guarded-worker");
            thread.setUncaughtExceptionHandler((failedThread, error) ->
                    System.out.println("  [ALERT] потік " + failedThread.getName()
                            + " впав: " + error.getMessage()));
            return thread;
        };

        ExecutorService guarded = Executors.newSingleThreadExecutor(guardedFactory);
        guarded.execute(() -> {
            System.out.println("  обробляю платіж у потоці " + name());
            throw new IllegalStateException("банк не відповідає");
        });
        guarded.shutdown();
        guarded.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println();

        // === 4. Daemon-потоки: не тримають JVM живою ===
        // Звичайний потік пулу не дасть програмі завершитися, поки його не зупинити.
        // Daemon-потік JVM просто "покине", коли завершиться main.
        System.out.println("=== 4. Daemon-фабрика для фонових задач ===");

        ThreadFactory daemonFactory = runnable -> {
            Thread thread = new Thread(runnable, "background-cleaner");
            thread.setDaemon(true);                     // ключовий рядок
            return thread;
        };

        ExecutorService cleaner = Executors.newSingleThreadExecutor(daemonFactory);
        cleaner.execute(() -> {
            Thread current = Thread.currentThread();
            System.out.println("  прибирання кешу | " + current.getName()
                    + " | daemon=" + current.isDaemon());
        });

        Thread.sleep(100);
        System.out.println("  такий пул можна навіть не закривати — JVM не чекатиме на нього.");
        cleaner.shutdown();
    }

    /** Фабрика з ім'ям, нумерацією потоків і заданим пріоритетом. */
    static class NamedThreadFactory implements ThreadFactory {

        private static final AtomicInteger TOTAL = new AtomicInteger();

        private final String prefix;
        private final int priority;
        private final AtomicInteger counter = new AtomicInteger();

        NamedThreadFactory(String prefix, int priority) {
            this.prefix = prefix;
            this.priority = priority;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            TOTAL.incrementAndGet();
            Thread thread = new Thread(runnable, prefix + "-" + counter.incrementAndGet());
            thread.setPriority(priority);
            thread.setDaemon(false);
            return thread;
        }

        static int created() {
            return TOTAL.get();
        }
    }

    private static String name() {
        return Thread.currentThread().getName();
    }
}
