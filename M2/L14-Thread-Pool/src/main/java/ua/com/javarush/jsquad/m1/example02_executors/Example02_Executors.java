package ua.com.javarush.jsquad.m1.example02_executors;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Модуль 2. Рівень 14. Thread Pool
 * <hr>
 * <h3>Тема: клас Executors — фабрика пулів, і життєвий цикл ExecutorService</h3>
 *
 * <p>Створювати {@code ThreadPoolExecutor} руками (а в нього 5-7 параметрів конструктора)
 * незручно. Тому є утилітарний клас <b>Executors</b> — набір статичних методів,
 * що готують типовий пул однією стрічкою.</p>
 *
 * <pre>
 *   newFixedThreadPool(n)      — фіксована кількість потоків для будь-якої кількості задач
 *
 *   newWorkStealingPool(n)     — потоків = ядер процесора; вільний "краде" роботу в зайнятого
 *
 *   newSingleThreadExecutor()  — рівно один потік на всі задачі
 *
 *   newCachedThreadPool()      — створює потоки за потреби, повторно використовує вільні
 *
 *   newScheduledThreadPool(n)  — виконання із затримкою або періодично
 * </pre>
 *
 * <h4>Як віддати задачу і як завершити пул:</h4>
 * <pre>
 *   execute(Runnable)          — просто виконати, результату немає
 *   submit(Runnable/Callable)  — виконати і повернути Future (результат у майбутньому)
 *   invokeAll(колекція)        — виконати всі й дочекатися всіх
 *
 *   shutdown()                 — нових задач не приймати, вже прийняті — доробити
 *   shutdownNow()              — спробувати перервати все і повернути невиконане
 *   awaitTermination(t, unit)  — почекати завершення не довше вказаного часу
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> Executors — це кадрове агентство. Ви не проводите
 * співбесіди самі, а кажете: "потрібна бригада з 4 людей" — і отримуєте готову команду.</p>
 *
 * <p><b>Реальне застосування:</b> у 95% коду пул створюють саме через Executors,
 * а конструктор ThreadPoolExecutor чіпають лише коли треба своя черга чи політика відмов.</p>
 */
public class Example02_Executors {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // === 1. П'ять фабричних методів класу Executors ===
        System.out.println("=== 1. Що вміє Executors ===");

        ExecutorService fixed = Executors.newFixedThreadPool(10);

        ExecutorService work = Executors.newWorkStealingPool(4);

        ExecutorService single = Executors.newSingleThreadExecutor();

        ExecutorService cached = Executors.newCachedThreadPool();

        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(2);


        System.out.println("  newFixedThreadPool(10)   -> " + fixed.getClass().getSimpleName());
        System.out.println("  newWorkStealingPool(4)   -> " + work.getClass().getSimpleName());
        System.out.println("  newSingleThreadExecutor  -> " + single.getClass().getSimpleName());
        System.out.println("  newCachedThreadPool()    -> " + cached.getClass().getSimpleName());
        System.out.println("  newScheduledThreadPool(2)-> " + scheduled.getClass().getSimpleName());

        // Пули, які більше не потрібні, треба закривати — інакше їхні потоки живуть далі.
        for (ExecutorService unused : List.of(fixed, work, cached, scheduled)) {
            unused.shutdown();
        }
        System.out.println();

        // === 2. execute() — віддали і забули ===
        // Сценарій: записати подію в лог. Результат нам не потрібен.
        System.out.println("=== 2. execute(): результат не цікавить ===");
        single.execute(() -> System.out.println("  [LOG] користувач увійшов | потік " + name()));
        Thread.sleep(100);                           // щоб побачити результат саме тут
        System.out.println();


        // === 3. submit() + Future — коли потрібен результат ===
        // Callable, на відміну від Runnable, ПОВЕРТАЄ значення і може кинути виняток.
        System.out.println("=== 3. submit() + Future: результат у майбутньому ===");
        Callable<Integer> countWords = () -> {
            Thread.sleep(200);                       // імітація довгого підрахунку
            return "пул потоків економить ресурси застосунку".split(" ").length;
        };

        Future<Integer> future = single.submit(countWords);

        System.out.println("  задачу віддано, головний потік вільний. Готово? " + future.isDone());

        Integer words = future.get();                // блокує, доки не буде результату

        System.out.println("  future.get() = " + words + " слів. Готово? " + future.isDone());
        System.out.println();

        // === 4. invokeAll() — виконати пачку задач і дочекатися всіх ===
        // Сценарій: перевірити наявність трьох товарів на складі паралельно.
        System.out.println("=== 4. invokeAll(): пачка задач ===");
        ExecutorService warehouse = Executors.newFixedThreadPool(3);
        List<Callable<String>> checks = List.of(
                () -> check("ноутбук"),
                () -> check("мишка"),
                () -> check("монітор")
        );

        List<Future<String>> results = warehouse.invokeAll(checks);   // чекає всіх
        for (Future<String> result : results) {
            System.out.println("  " + result.get());
        }
        warehouse.shutdown();
        System.out.println();

        // === 5. Завершення роботи пулу ===
        System.out.println("=== 5. shutdown / awaitTermination ===");
        single.shutdown();
        System.out.println("  isShutdown   = " + single.isShutdown());
        System.out.println("  isTerminated = " + single.isTerminated() + " (ще доробляє прийняте)");
        boolean finished = single.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("  дочекалися за 5 с? " + finished + ", isTerminated = " + single.isTerminated());

        // Після shutdown() пул більше не приймає задачі — буде RejectedExecutionException.
        try {
            single.execute(() -> System.out.println("  цього не станеться"));
        } catch (RejectedExecutionException e) {
            System.out.println("  нова задача після shutdown -> " + e.getClass().getSimpleName());
        }
        System.out.println();

        System.out.println("Пам'ятайте: якщо не викликати shutdown(), потоки пулу");
        System.out.println("залишаються живими і програма може не завершитися.");
    }

    private static String check(String item) throws InterruptedException {
        Thread.sleep(150);
        return "товар '" + item + "' є на складі | перевірив " + name();
    }

    private static String name() {
        return Thread.currentThread().getName();
    }
}
