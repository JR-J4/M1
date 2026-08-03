package ua.com.javarush.jsquad.m1.example01_thread_pool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Модуль 2. Рівень 14. Thread Pool
 * <hr>
 * <h3>Тема: Thread pool і ThreadPoolExecutor — навіщо взагалі пул потоків</h3>
 *
 * <p>Створення потоку в Java — доволі <b>"дорога"</b> операція: JVM просить у
 * операційної системи новий системний потік, виділяє йому стек пам'яті (близько 1 МБ),
 * реєструє його у планувальнику. Якщо створювати новий потік на кожну дрібну задачу —
 * отримаємо великі проблеми з продуктивністю, а згодом і з працездатністю застосунку.</p>
 *
 * <p><b>Thread pool</b> (пул потоків) — набір заздалегідь створених потоків,
 * які <b>повторно використовуються</b>. Розмір пулу може бути фіксованим або змінним.</p>
 *
 * <h4>Головні інтерфейси (з Java 5, Executor framework):</h4>
 * <pre>
 *   Executor        — один метод: void execute(Runnable runnable)
 *                     задача виконається асинхронно колись у майбутньому
 *   ExecutorService — успадковує Executor + вміє завершувати роботу пулу,
 *                     перервати задачу, повертати результат
 * </pre>
 *
 * <p><b>ThreadPoolExecutor</b> реалізує обидва інтерфейси і <b>розділяє створення
 * задачі та її виконання</b>: ми пишемо Runnable і віддаємо його виконавцю, а той
 * сам відповідає за черги, потоки і їх повторне використання.</p>
 *
 * <pre>
 *   Runnable ─┐
 *   Runnable ─┼──►  [ Черга задач ]  ──►  [ Пул потоків ]
 *   Runnable ─┘      Task Task Task        Thread Thread
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> кол-центр. Наївний варіант — наймати нового оператора
 * на кожен дзвінок і звільняти після розмови.
 * Розумний варіант — тримати 10 операторів
 * у штаті (пул), а дзвінки, що не вмістилися, ставити в чергу очікування.</p>
 *
 * <p><b>Реальне застосування:</b> веб-сервер обробляє тисячі запитів пулом із
 * 200 потоків, а не створює 1000 нових потоків на 1000 користувачів.</p>
 */
public class Example01_ThreadPool {

    public static void main(String[] args) throws InterruptedException {

        // === 1. Скільки коштує "новий потік на кожну задачу" ===
        // Сценарій: 2000 однакових дрібних задач. Порівняємо два підходи.
        final int TASKS = 2_000;

        long start = System.currentTimeMillis();
        for (int i = 0; i < TASKS; i++) {
            Thread thread = new Thread(Example01_ThreadPool::smallJob);
            thread.start();
            thread.join();                       // дочекалися і викинули потік
        }
        long newThreadTime = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();

        ExecutorService pool = Executors.newSingleThreadExecutor();

        for (int i = 0; i < TASKS; i++) {
            pool.execute(Example01_ThreadPool::smallJob);   // той самий потік працює далі
        }
        pool.shutdown();                          // більше задач не приймаємо
        pool.awaitTermination(1, TimeUnit.MINUTES);
        long poolTime = System.currentTimeMillis() - start;

        System.out.println("=== 1. Ціна створення потоку (" + TASKS + " задач) ===");
        System.out.println("новий потік на кожну задачу : " + newThreadTime + " мс");
        System.out.println("один потік із пулу          : " + poolTime + " мс");
        System.out.println("Задачі однакові — різниця саме у створенні потоків.");
        System.out.println();

        // === 2. Інтерфейс Executor — лише один метод execute(Runnable) ===
        // Executor не каже, ДЕ виконається задача. Це вирішує реалізація.
        Executor inSameThread = command -> command.run();          // виконати тут і зараз
        Executor inNewThread = command -> new Thread(command).start();  // окремий потік

        System.out.println("=== 2. Executor: одна задача — різні реалізації ===");
        inSameThread.execute(() -> System.out.println("  виконано в потоці: " + name()));
        inNewThread.execute(() -> System.out.println("  виконано в потоці: " + name()));
        Thread.sleep(100);      // хай новий потік встигне надрукувати
        System.out.println();

        // === 3. ExecutorService — це Executor + керування життям пулу ===
        System.out.println("=== 3. ExecutorService = Executor + керування ===");

        ExecutorService service = Executors.newFixedThreadPool(2);

        service.execute(() -> System.out.println("  задача A у потоці " + name() ));
        service.execute(() -> System.out.println("  задача B у потоці " + name() ));
        service.shutdown();                                 // коректне завершення
        System.out.println("  shutdown викликано, isShutdown = " + service.isShutdown());
        service.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("  усі задачі завершені, isTerminated = " + service.isTerminated());
        System.out.println();

        // === 4. Що всередині: ThreadPoolExecutor = черга задач + пул потоків ===
        // Пул усього на 2 потоки, а задач 6 — решта чекатиме у черзі.
        System.out.println("=== 4. Заглядаємо всередину ThreadPoolExecutor ===");

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 6; i++) {
            int number = i;
            executor.execute(() -> {
                sleep(200);                      // імітація довгої роботи
                System.out.println("  задача " + number + " виконана потоком " + name());
            });
        }

        Thread.sleep(50);                        // даємо пулу мить, щоб розібрати задачі
        System.out.println("  потоків створено (poolSize)  : " + executor.getPoolSize());
        System.out.println("  зараз працюють (activeCount) : " + executor.getActiveCount());
        System.out.println("  чекають у черзі              : " + executor.getQueue().size());

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("  виконано всього (completedTaskCount): " + executor.getCompletedTaskCount());
        System.out.println();

        System.out.println("Головне: ми описуємо ЩО зробити (Runnable),");
        System.out.println("а пул вирішує ХТО і КОЛИ це зробить.");
    }

    /** Дрібна задача: така коротка, що майже весь час іде на створення потоку. */
    private static void smallJob() {
        double x = 0;
        for (int i = 0; i < 100; i++) {
            x += Math.sqrt(i);
        }
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
