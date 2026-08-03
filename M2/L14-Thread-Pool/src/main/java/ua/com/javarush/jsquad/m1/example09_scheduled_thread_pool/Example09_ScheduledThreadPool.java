package ua.com.javarush.jsquad.m1.example09_scheduled_thread_pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Модуль 2. Рівень 14. Thread Pool
 * <hr>
 * <h3>Тема: Scheduled Thread Executor — пул запланованих задач</h3>
 *
 * <p>Ще один тип пулу потоків — <b>пул запланованих задач</b>. Судячи з назви можна
 * допустити, що ми використовуємо цей тип для планування запуску певної задачі,
 * розподіленої в часі.</p>
 *
 * <p>Цей вид сервісу корисний, коли є задача запуску певної активності з умовою
 * добігання деякого часу чи <b>періодичності</b> виконання задачі.</p>
 *
 * <pre>
 *   ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
 *
 *   schedule(task, delay, unit)                        — один раз через delay
 *   scheduleAtFixedRate(task, initial, period, unit)   — старт кожні period
 *   scheduleWithFixedDelay(task, initial, delay, unit) — пауза delay МІЖ запусками
 * </pre>
 *
 * <p>Цей пул можемо використовувати для виконання <b>"сервісних"</b> періодичних задач.
 * Сервісними ми називаємо ті задачі, які потрібно виконати незалежно від роботи
 * основного функціоналу програми.</p>
 *
 * <p><b>Аналогія з життя:</b> будильник і графік прибирання. "Розбудити о 7:00" —
 * це schedule. "Виносити сміття щодня" — scheduleAtFixedRate.</p>
 *
 * <p><b>Реальне застосування:</b> оновлення курсів валют кожні 5 хвилин, очищення
 * застарілого кешу, надсилання звітів, перевірка "живий чи ні" зовнішнього сервісу.</p>
 */
public class Example09_ScheduledThreadPool {

    private static final long START = System.currentTimeMillis();

    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // === 1. schedule() — виконати один раз із затримкою ===
        // Сценарій: нагадування "кошик чекає на вас" через деякий час після візиту.
        System.out.println("=== 1. Одноразовий запуск із затримкою ===");
        log("задачу заплановано на +600 мс");
        scheduler.schedule(() -> log("нагадування надіслано"), 600, TimeUnit.MILLISECONDS);

        Thread.sleep(800);
        System.out.println();

        // === 2. scheduleAtFixedRate() — старт кожні N мс ===
        // Період рахується від ПОЧАТКУ попереднього запуску.
        // Сценарій: перевірка нових повідомлень кожні 300 мс.
        System.out.println("=== 2. Періодично: scheduleAtFixedRate (кожні 300 мс) ===");
        ScheduledFuture<?> polling = scheduler.scheduleAtFixedRate(
                () -> log("перевіряю нові повідомлення"),
                0, 300, TimeUnit.MILLISECONDS);

        Thread.sleep(1000);                       // хай спрацює кілька разів
        polling.cancel(false);                    // періодичну задачу треба скасовувати
        log("перевірку зупинено, cancelled = " + polling.isCancelled());
        System.out.println();

        // === 3. scheduleWithFixedDelay() — пауза МІЖ запусками ===
        // Затримка рахується від ЗАВЕРШЕННЯ попереднього запуску,
        // тому довга задача не "накладається" сама на себе.
        System.out.println("=== 3. Періодично: scheduleWithFixedDelay (пауза 200 мс) ===");
        ScheduledFuture<?> backup = scheduler.scheduleWithFixedDelay(() -> {
            log("резервна копія почалася");
            sleep(300);                            // сама задача триває 300 мс
            log("резервна копія завершена");
        }, 0, 200, TimeUnit.MILLISECONDS);

        Thread.sleep(1200);
        backup.cancel(false);                     // false = поточний запуск дороблює до кінця
        Thread.sleep(350);                        // дочекаємося цього останнього запуску
        System.out.println();

        // === 4. Різниця між двома режимами ===
        System.out.println("=== 4. AtFixedRate vs WithFixedDelay ===");
        System.out.println("  atFixedRate    : старт-старт-старт кожні period,");
        System.out.println("                   якщо задача довша за period — запуски йдуть підряд без пауз;");
        System.out.println("  withFixedDelay : завершив -> почекав delay -> почав знову,");
        System.out.println("                   пауза між запусками стабільна.");
        System.out.println();

        // === 5. Коректне завершення планувальника ===
        System.out.println("=== 5. Завершення ===");
        scheduler.shutdown();
        boolean stopped = scheduler.awaitTermination(5, TimeUnit.SECONDS);
        log("планувальник зупинено: " + stopped);
        System.out.println("  без shutdown() програма висітиме вічно — сервіс чекає нових задач.");
    }

    /** Друкує повідомлення з відміткою часу від старту програми. */
    private static void log(String message) {
        long elapsed = System.currentTimeMillis() - START;
        System.out.printf("  [%4d мс] %-38s | %s%n", elapsed, message, Thread.currentThread().getName());
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
