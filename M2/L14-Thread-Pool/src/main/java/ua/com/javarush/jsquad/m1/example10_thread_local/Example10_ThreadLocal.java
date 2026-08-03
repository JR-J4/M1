package ua.com.javarush.jsquad.m1.example10_thread_local;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Модуль 2. Рівень 14. Thread Pool
 * <hr>
 * <h3>Тема: ThreadLocal — змінна, у якої в кожного потоку своє значення</h3>
 *
 * <p>Клас <b>ThreadLocal</b> використовується для зберігання змінних, які мають бути
 * доступними для всього потоку. Фактично це щось на кшталт ще однієї
 * <b>області видимості</b> змінних: не поле об'єкта і не локальна змінна методу,
 * а "змінна потоку".</p>
 *
 * <p>Клас має методи <b>get</b> і <b>set</b>, які дозволяють отримати поточне значення
 * та встановити нове. Зазвичай екземпляри ThreadLocal оголошують як
 * <b>private static</b> змінні класу. Кожний потік отримує з методу get своє значення
 * та встановлює через set теж своє, <b>ізольоване</b> від інших потоків.</p>
 *
 * <pre>
 *   private static final ThreadLocal&lt;Integer&gt; counter = ThreadLocal.withInitial(() -&gt; 0);
 *   counter.set(counter.get() + 1);   // впливає лише на поточний потік
 *   counter.remove();                 // прибрати значення цього потоку
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> шафка в роздягальні спортзалу. Шафки спільні для всіх
 * (одна змінна), але кожен відвідувач бачить у "своїй" лише свої речі.</p>
 *
 * <p><b>Реальне застосування:</b> ідентифікатор запиту в логах, поточний користувач,
 * транзакція БД, а також несинхронізовані об'єкти на кшталт SimpleDateFormat.</p>
 */
public class Example10_ThreadLocal {

    // Кожен потік матиме власний лічильник, який стартує з нуля.
    private static final ThreadLocal<Integer> counter = ThreadLocal.withInitial(() -> 0);

    // SimpleDateFormat НЕ потокобезпечний — даємо кожному потоку власний екземпляр.
    private static final ThreadLocal<SimpleDateFormat> formatter =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("HH:mm:ss.SSS"));

    // Ідентифікатор запиту: зручно підставляти в логи будь-де без передавання параметром.
    private static final ThreadLocal<String> requestId = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {

        // === 1. Одна змінна — у кожного потоку своє значення ===
        System.out.println("=== 1. Ізольовані лічильники ===");
        Runnable job = () -> {
            for (int i = 0; i < 3; i++) {
                counter.set(counter.get() + 1);          // тільки МІЙ лічильник
            }
            System.out.println("  " + name() + ": мій лічильник = " + counter.get());
        };

        Thread first = new Thread(job, "потік-A");
        Thread second = new Thread(job, "потік-B");
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println("  головний потік: мій лічильник = " + counter.get() + " (свій, недоторканий)");
        System.out.println();

        // === 2. Навіщо це в пулі: несинхронізований SimpleDateFormat ===
        // Один спільний SimpleDateFormat на кілька потоків дає биті дати або виняток.
        // ThreadLocal вирішує це без synchronized і без створення об'єкта на кожен виклик.
        System.out.println("=== 2. Свій SimpleDateFormat для кожного потоку ===");
        ExecutorService pool = Executors.newFixedThreadPool(3);
        Date now = new Date();

        for (int i = 1; i <= 3; i++) {
            int task = i;
            pool.execute(() -> System.out.println("  задача " + task + " | " + name()
                    + " | час: " + formatter.get().format(now)
                    + " | екземпляр форматера: " + hash(formatter.get())));
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("  хеші різні -> у кожного потоку свій об'єкт, гонки немає");
        System.out.println();

        // === 3. Наскрізний ідентифікатор запиту ===
        // requestId не передається параметром, але доступний у будь-якому методі потоку.
        System.out.println("=== 3. requestId, який не треба тягнути параметром ===");
        ExecutorService server = Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 4; i++) {
            int number = i;
            server.execute(() -> {
                requestId.set("REQ-" + number);
                try {
                    handleRequest();                     // requestId доступний глибоко всередині
                } finally {
                    requestId.remove();                  // ОБОВ'ЯЗКОВО прибрати за собою
                }
            });
        }
        server.shutdown();
        server.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println();

        // === 4. Пастка пулів: значення "переїжджає" до наступної задачі ===
        // Потік у пулі не вмирає, тож і його ThreadLocal-значення живе далі.
        System.out.println("=== 4. Чому remove() критично важливий у пулі ===");
        ExecutorService single = Executors.newSingleThreadExecutor();

        single.execute(() -> {
            requestId.set("REQ-100");
            System.out.println("  задача 1 встановила " + requestId.get() + " і НЕ прибрала");
        });
        single.execute(() -> System.out.println("  задача 2 бачить чуже значення: " + requestId.get()));

        single.shutdown();
        single.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("  саме тому: set() -> try/finally -> remove()");
    }

    /** Метод "десь глибоко в коді": бере requestId прямо з потоку. */
    private static void handleRequest() {
        System.out.println("  [" + requestId.get() + "] обробляю запит | " + name());
        saveToDatabase();
    }

    private static void saveToDatabase() {
        System.out.println("  [" + requestId.get() + "] зберігаю в БД     | " + name());
    }

    private static String hash(Object object) {
        return Integer.toHexString(System.identityHashCode(object));
    }

    private static String name() {
        return Thread.currentThread().getName();
    }
}
