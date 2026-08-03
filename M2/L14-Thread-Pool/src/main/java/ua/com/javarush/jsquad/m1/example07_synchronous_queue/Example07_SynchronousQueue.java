package ua.com.javarush.jsquad.m1.example07_synchronous_queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Модуль 2. Рівень 14. Thread Pool
 * <hr>
 * <h3>Тема: SynchronousQueue — черга, у якій нічого не зберігається</h3>
 *
 * <p>Основна ідея синхронного передавання доволі проста: <b>поставити елемент до черги
 * можна лише тоді, коли інший потік приймає цей елемент одночасно</b>.</p>
 *
 * <p>Іншими словами, синхронна черга <b>не може містити задач</b>: у момент, коли
 * надходить нова задача, потік, що виконує її, вже забирає цю задачу. Її місткість
 * дорівнює нулю, тому {@code size()} завжди 0, а {@code isEmpty()} завжди {@code true}.</p>
 *
 * <p>Саме таку чергу всередині використовує {@code Executors.newCachedThreadPool()}:
 * задачі нікуди не складаються — або її одразу підхоплює вільний потік, або
 * створюється новий потік.</p>
 *
 * <p><b>Аналогія з життя:</b> передача естафетної палички. Немає столика, куди її можна
 * покласти й піти — бігун стоїть із простягнутою рукою, доки наступний її не візьме.</p>
 *
 * <p><b>Реальне застосування:</b> прямий обмін між виробником і споживачем без буфера,
 * а також основа кешованих пулів потоків.</p>
 */
public class Example07_SynchronousQueue {

    public static void main(String[] args) throws InterruptedException {

        // === 1. put() чекає, доки хтось не забере ===
        // Сценарій: кухар віддає страву офіціантові з рук у руки, підвіконня немає.
        System.out.println("=== 1. Передача з рук у руки ===");

        BlockingQueue<String> window = new SynchronousQueue<>();

        Thread cook = new Thread(() -> {

            try {
                for (String dish : new String[]{"борщ", "вареники"}) {
                    System.out.println("  кухар: страва '" + dish + "' готова, чекаю офіціанта...");
                    window.put(dish);                      // блокує, доки не заберуть
                    System.out.println("  кухар: '" + dish + "' передано");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "cook");

        cook.start();
        Thread.sleep(300);                                  // кухар уже чекає
        System.out.println("  розмір черги під час очікування: " + window.size() + " (завжди 0!)");

        for (int i = 0; i < 2; i++) {
            String dish = window.take();                    // забираємо
            System.out.println("  офіціант: забрав '" + dish + "'");
            Thread.sleep(200);
        }
        cook.join();
        System.out.println();

        // === 2. offer() без споживача одразу повертає false ===
        System.out.println("=== 2. Нікому передати — значить, не вийде ===");
        BlockingQueue<String> empty = new SynchronousQueue<>();
        System.out.println("  offer(\"замовлення\") = " + empty.offer("замовлення") + " (ніхто не чекає)");
        System.out.println("  isEmpty() = " + empty.isEmpty() + ", size() = " + empty.size());
        System.out.println();

        // === 3. Пул із SynchronousQueue: або є вільний потік, або відмова ===
        // Максимум 2 потоки, черга нульова -> третя задача не має де почекати.
        System.out.println("=== 3. ThreadPoolExecutor + SynchronousQueue ===");
        ThreadPoolExecutor strict = new ThreadPoolExecutor(
                2, 2,                                       // core = max = 2 потоки
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>());                  // місця для очікування немає

        strict.execute(() -> hold("задача 1"));
        strict.execute(() -> hold("задача 2"));
        Thread.sleep(50);
        try {
            strict.execute(() -> hold("задача 3"));
        } catch (RejectedExecutionException e) {
            System.out.println("  задача 3 відхилена: " + e.getClass().getSimpleName());
        }
        strict.shutdown();
        strict.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println();

        // === 4. Та сама ситуація з чергою, що ВМІЄ зберігати ===
        // LinkedBlockingQueue приймає задачу — вона просто чекатиме вільного потоку.
        System.out.println("=== 4. Для порівняння: LinkedBlockingQueue ===");
        ThreadPoolExecutor patient = new ThreadPoolExecutor(
                2, 2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());               // черга без обмежень

        for (int i = 1; i <= 3; i++) {
            patient.execute(hold("задача " + i, patient));
        }
        Thread.sleep(50);
        System.out.println("  у черзі чекає: " + patient.getQueue().size() + " задача — відмови немає");
        patient.shutdown();
        patient.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println();

        // === 5. Три типові черги пулів ===
        System.out.println("=== 5. Яку чергу обирає який пул ===");
        System.out.println("  SynchronousQueue        -> newCachedThreadPool()  : не зберігає нічого");
        System.out.println("  LinkedBlockingQueue     -> newFixedThreadPool(n)  : зберігає скільки завгодно");
        System.out.println("  ArrayBlockingQueue(k)   -> свій ThreadPoolExecutor: зберігає не більше k");
        BlockingQueue<Runnable> limited = new ArrayBlockingQueue<>(100);
        System.out.println("  приклад обмеженої черги на " + limited.remainingCapacity() + " задач");
    }

    private static void hold(String task) {
        System.out.println("  " + task + " виконується | " + Thread.currentThread().getName());
        sleep(300);
    }

    private static Runnable hold(String task, ThreadPoolExecutor owner) {
        return () -> {
            System.out.println("  " + task + " виконується | " + Thread.currentThread().getName()
                    + " | активних потоків: " + owner.getActiveCount());
            sleep(300);
        };
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
