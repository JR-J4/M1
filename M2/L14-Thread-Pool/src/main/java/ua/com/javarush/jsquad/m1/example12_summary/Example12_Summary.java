package ua.com.javarush.jsquad.m1.example12_summary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Модуль 2. Рівень 14. Thread Pool
 * <hr>
 * <h3>Підсумок: інтернет-магазин на пулах потоків</h3>
 *
 * <p>Зберемо все з лекції в одному сценарії — служба обробки замовлень:</p>
 *
 * <pre>
 *   ThreadFactory          — свої імена потоків, щоб читати логи
 *   newFixedThreadPool(3)  — важка обробка замовлень (оплата, склад)
 *   newCachedThreadPool()  — легкі сповіщення покупцям
 *   newScheduledThreadPool — періодичний звіт "скільки оброблено"
 *   ThreadLocal            — номер замовлення в логах без передавання параметром
 *   ThreadLocalRandom      — імітація різної тривалості обробки
 *   Future / invokeAll     — зібрати результати
 *   shutdown + awaitTermination — коректно закрити всі пули
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> склад інтернет-магазину. Троє комплектувальників
 * (fixed pool) збирають замовлення, кур'єрська служба на аутсорсі (cached pool)
 * розвозить дрібні посилки, а адміністратор щосекунди дивиться на табло (scheduled pool).</p>
 *
 * <p><b>Головне правило:</b> не створюйте потоки вручну на кожну задачу — опишіть
 * задачу як Runnable/Callable і віддайте її пулу.</p>
 */
public class Example12_Summary {

    // Номер замовлення, "прив'язаний" до потоку, що його обробляє.
    private static final ThreadLocal<String> currentOrder = new ThreadLocal<>();

    private static final AtomicInteger processed = new AtomicInteger();
    private static final AtomicInteger notified = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        String[] orders = {"№1001 ноутбук", "№1002 навушники", "№1003 монітор",
                "№1004 клавіатура", "№1005 миша", "№1006 килимок"};

        // === 1. Готуємо пули під різні типи роботи ===
        System.out.println("=== 1. Три пули під три види задач ===");
        ExecutorService warehouse = Executors.newFixedThreadPool(3, namedFactory("склад"));

        ExecutorService couriers = Executors.newCachedThreadPool(namedFactory("сповіщення"));

        ScheduledExecutorService monitor = Executors.newScheduledThreadPool(1, namedFactory("монітор"));

        System.out.println("  склад       : fixed(3)  — важка обробка, навантаження під контролем");
        System.out.println("  сповіщення  : cached    — багато коротких задач");
        System.out.println("  монітор     : scheduled — періодичний звіт");
        System.out.println();

        // === 2. Табло: звіт кожні 400 мс ===
        System.out.println("=== 2. Запускаємо періодичний звіт ===");
        monitor.scheduleAtFixedRate(
                () -> System.out.println("  [ЗВІТ] оброблено замовлень: " + processed.get()
                        + ", сповіщень надіслано: " + notified.get()
                        + " | " + name()),
                400, 400, TimeUnit.MILLISECONDS);
        System.out.println();

        // === 3. Обробляємо замовлення пулом і збираємо результати ===
        System.out.println("=== 3. Обробка замовлень (3 потоки на 6 замовлень) ===");
        List<Callable<String>> tasks = new ArrayList<>();

        for (String order : orders) {
            tasks.add(() -> {
                currentOrder.set(order);                 // видно в усіх методах цього потоку
                try {
                    return process(couriers);
                } finally {
                    currentOrder.remove();               // прибираємо за собою: потік житиме далі
                }
            });
        }

        long start = System.currentTimeMillis();
        List<Future<String>> results = warehouse.invokeAll(tasks);   // чекає всіх

        System.out.println();
        System.out.println("=== 4. Результати ===");
        for (Future<String> result : results) {
            System.out.println("  " + result.get());
        }
        System.out.println("  усе разом за " + (System.currentTimeMillis() - start) + " мс");
        System.out.println();

        // === 5. Коректне закриття всіх пулів ===
        System.out.println("=== 5. Закриваємо магазин ===");
        warehouse.shutdown();
        couriers.shutdown();
        monitor.shutdown();                              // інакше звіт друкувався б вічно

        boolean warehouseDone = warehouse.awaitTermination(10, TimeUnit.SECONDS);
        boolean couriersDone = couriers.awaitTermination(10, TimeUnit.SECONDS);
        boolean monitorDone = monitor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("  склад завершив роботу      : " + warehouseDone);
        System.out.println("  сповіщення завершили роботу: " + couriersDone);
        System.out.println("  монітор зупинено           : " + monitorDone);
        System.out.println();
        System.out.println("  підсумок: замовлень " + processed.get()
                + ", сповіщень " + notified.get());
        System.out.println();

        System.out.println("=== Шпаргалка: який пул обрати ===");
        System.out.println("  один за одним, важливий порядок -> newSingleThreadExecutor()");
        System.out.println("  важкі задачі, контроль ресурсів -> newFixedThreadPool(n)");
        System.out.println("  багато коротких задач           -> newCachedThreadPool()");
        System.out.println("  нерівномірні паралельні задачі  -> newWorkStealingPool()");
        System.out.println("  за розкладом чи періодично      -> newScheduledThreadPool(n)");
    }

    /** Обробка одного замовлення: оплата -> склад -> сповіщення покупцю. */
    private static String process(ExecutorService couriers) throws InterruptedException {
        log("оплата підтверджена");
        Thread.sleep(ThreadLocalRandom.current().nextInt(150, 350));   // різна тривалість

        log("товар зібрано на складі");
        String order = currentOrder.get();

        // Легке сповіщення віддаємо іншому пулу, щоб не займати "склад".
        couriers.execute(() -> {
            notified.incrementAndGet();
            System.out.println("  [" + order + "] SMS покупцю надіслано | " + name());
        });

        processed.incrementAndGet();
        return order + " -> оброблено потоком " + name();
    }

    /** Лог із номером замовлення, який ніхто не передавав параметром. */
    private static void log(String message) {
        System.out.println("  [" + currentOrder.get() + "] " + message + " | " + name());
    }

    /** Фабрика потоків із людськими іменами: склад-1, склад-2, ... */
    private static ThreadFactory namedFactory(String prefix) {
        AtomicInteger counter = new AtomicInteger();
        return runnable -> {
            Thread thread = new Thread(runnable, prefix + "-" + counter.incrementAndGet());
            thread.setUncaughtExceptionHandler((failed, error) ->
                    System.out.println("  [ALERT] " + failed.getName() + ": " + error.getMessage()));
            return thread;
        };
    }

    private static String name() {
        return Thread.currentThread().getName();
    }
}
