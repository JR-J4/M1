package ua.com.javarush.jsquad.m1;

/**
 * Модуль 2. Рівень 14. Thread Pool — ЗМІСТ ПРИКЛАДІВ
 *
 * <p>Лекція охоплює: Thread pool і ThreadPoolExecutor, утилітарний клас Executors,
 * Single Thread Executor, ThreadFactory, Fixed Thread Executor, Cached Thread Executor,
 * SynchronousQueue, Work Stealing Thread Executor, Scheduled Thread Executor,
 * ThreadLocal та ThreadLocalRandom.</p>
 *
 * <p>Кожен приклад — самодостатній клас зі своїм {@code main()}.
 * Запускайте їх по черзі (відкрийте файл і натисніть ▶ біля {@code main}).</p>
 *
 * <pre>
 *  №   Тема                                        Клас для запуску
 *  ─────────────────────────────────────────────────────────────────────────────────────────
 *  01  Thread pool і ThreadPoolExecutor            example01_thread_pool.Example01_ThreadPool
 *  02  Executors + життєвий цикл ExecutorService   example02_executors.Example02_Executors
 *  03  Single Thread Executor (один потік)         example03_single_thread_executor.Example03_SingleThreadExecutor
 *  04  ThreadFactory (своя фабрика потоків)        example04_thread_factory.Example04_ThreadFactory
 *  05  Fixed Thread Executor (n потоків + черга)   example05_fixed_thread_pool.Example05_FixedThreadPool
 *  06  Cached Thread Executor (кешовані потоки)    example06_cached_thread_pool.Example06_CachedThreadPool
 *  07  SynchronousQueue (черга без місткості)      example07_synchronous_queue.Example07_SynchronousQueue
 *  08  Work Stealing Executor (fork/join)          example08_work_stealing_pool.Example08_WorkStealingPool
 *  09  Scheduled Thread Executor (за розкладом)    example09_scheduled_thread_pool.Example09_ScheduledThreadPool
 *  10  ThreadLocal (своє значення в кожного потоку) example10_thread_local.Example10_ThreadLocal
 *  11  ThreadLocalRandom (випадкові числа в пулі)  example11_thread_local_random.Example11_ThreadLocalRandom
 *  12  Підсумок: інтернет-магазин на пулах         example12_summary.Example12_Summary
 * </pre>
 *
 * <h4>Коротка шпаргалка:</h4>
 * <pre>
 *   newSingleThreadExecutor()  — один потік, суворий порядок
 *   newFixedThreadPool(n)      — рівно n потоків, решта задач у черзі
 *   newCachedThreadPool()      — потоків скільки треба, вільні живуть 60 с
 *   newWorkStealingPool(n)     — вільний потік "краде" роботу в зайнятого
 *   newScheduledThreadPool(n)  — із затримкою або періодично
 * </pre>
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Модуль 2. Рівень 14 — Thread Pool");
        System.out.println("12 прикладів у пакетах example01..example12.");
        System.out.println("Теми: Thread pool, ThreadPoolExecutor, Executors, Single/Fixed/Cached/WorkStealing/Scheduled,");
        System.out.println("      ThreadFactory, SynchronousQueue, ThreadLocal, ThreadLocalRandom.");
        System.out.println("Відкрийте потрібний ExampleNN_*.java і запустіть його main().");
    }
}
