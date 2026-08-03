package ua.com.javarush.jsquad.m1.example06_cached_thread_pool;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Модуль 2. Рівень 14. Thread Pool
 * <hr>
 * <h3>Тема: Cached Thread Executor — кешований пул потоків</h3>
 *
 * <p>Ще один тип пулу — <b>кешований</b>. Такі пули так само поширені у використанні,
 * як і фіксовані.</p>
 *
 * <p>Пул потоків <b>кешує</b> потоки — звідси й назва. Він тримає активними
 * (але такими, що не використовуються) потоки протягом обмеженого часу
 * (за замовчуванням <b>60 секунд</b>), щоб використати ці потоки для виконання
 * нових задач. Якщо вільного потоку немає — створює новий, кількість не обмежена.</p>
 *
 * <pre>
 *   ExecutorService executor = Executors.newCachedThreadPool();
 * </pre>
 *
 * <p>Такий пул краще використовувати, коли в нас є до виконання деяка кількість
 * <b>легких</b> задач. Для важких і довгих задач він небезпечний: на 10 000 задач
 * він спробує створити 10 000 потоків.</p>
 *
 * <p><b>Аналогія з життя:</b> служба таксі без фіксованого штату. Є замовлення —
 * викликають водія; водій, що звільнився, чекає годину на стоянці й може взяти
 * наступного пасажира; не дочекався — їде додому.</p>
 *
 * <p><b>Реальне застосування:</b> обробка коротких асинхронних подій: надіслати
 * пуш-сповіщення, записати метрику, зробити швидкий HTTP-запит.</p>
 */
public class Example06_CachedThreadPool {

    public static void main(String[] args) throws InterruptedException {

        // === 1. Задачі йдуть по черзі -> потік перевикористовується ===
        // Сценарій: сповіщення приходять рідко. Пул обходиться ОДНИМ потоком.
        System.out.println("=== 1. Повторне використання вільного потоку ===");
        ThreadPoolExecutor calm = (ThreadPoolExecutor) Executors.newCachedThreadPool();


        for (int i = 1; i <= 5; i++) {
            int event = i;
            calm.execute(() -> System.out.println("  подія " + event + " | " + name()));
            Thread.sleep(120);                    // задачі не перетинаються в часі
        }
        System.out.println("  >>> створено потоків усього: " + calm.getPoolSize());
        calm.shutdown();
        System.out.println();

        // === 2. Навала задач -> пул створює стільки потоків, скільки треба ===
        // Ті самі 10 задач, але всі одночасно: вільних потоків немає — створюються нові.
        System.out.println("=== 2. Навала одночасних задач ===");
        ThreadPoolExecutor burst = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        for (int i = 1; i <= 10; i++) {
            int event = i;
            burst.execute(() -> {
                sleep(200);                        // усі зайняті одночасно
                System.out.println("  подія " + event + " | " + name());
            });
        }

        Thread.sleep(50);
        System.out.println("  >>> потоків створено: " + burst.getPoolSize()
                + ", у черзі: " + burst.getQueue().size() + " (черга завжди порожня!)");
        burst.shutdown();
        burst.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println();

        // === 3. Cached проти Fixed на тих самих 10 задачах ===
        // Fixed(2) виконає їх у 5 "хвиль", Cached — усі разом.
        System.out.println("=== 3. Cached vs Fixed: час на 10 задачах по 200 мс ===");


        ThreadPoolExecutor smartCached = new ThreadPoolExecutor(0, 4,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());

        long cachedTime = measure(smartCached);
        long fixedTime = measure(Executors.newFixedThreadPool(2));

        System.out.println("  newCachedThreadPool()   : " + cachedTime + " мс");
        System.out.println("  newFixedThreadPool(2)   : " + fixedTime + " мс");
        System.out.println("  Cached швидший — але й потоків з'їв набагато більше.");
        System.out.println();

        // === 4. Коли кешований пул небезпечний ===
        System.out.println("=== 4. Обережно з важкими задачами ===");
        System.out.println("  кешований пул НЕ обмежує кількість потоків;");
        System.out.println("  1000 довгих задач -> спроба створити 1000 потоків -> OutOfMemoryError;");
        System.out.println("  тож: короткі задачі — cached, довгі/важкі — fixed.");
    }

    /** Виконує 10 однакових задач по 200 мс і повертає витрачений час. */
    private static long measure(java.util.concurrent.ExecutorService pool) throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            pool.execute(() -> sleep(200));
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
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
