package ua.com.javarush.jsquad.m1.example08_work_stealing_pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * Модуль 2. Рівень 14. Thread Pool
 * <hr>
 * <h3>Тема: Work Stealing Thread Executor — пул, що "краде" роботу</h3>
 *
 * <p>Цей пул потоків особливий — концепція його роботи полягає в
 * <b>"крадіжці" роботи</b>.</p>
 *
 * <p>Задачі збираються до черги й розподіляються по процесорах. Але якщо процесор
 * зайнятий, інший <b>вільний</b> процесор може вкрасти в нього задачу та виконати її.
 * Такий формат ввели до Java, щоб зменшити суперечності (простої) у багатопотокових
 * застосунках. В основі лежить фреймворк <b>fork/join</b>.</p>
 *
 * <pre>
 *   ExecutorService executor = Executors.newWorkStealingPool(4);
 *   // без параметра: потоків = кількість ядер, доступних JVM
 * </pre>
 *
 * <p><b>Важливо:</b> потоки такого пулу є daemon-потоками, тому результат треба
 * дочекатися явно — через {@code invokeAll}, {@code Future.get()} або
 * {@code awaitTermination()}.</p>
 *
 * <p><b>Аналогія з життя:</b> бригада вантажників. У кожного своя купа коробок.
 * Хто закінчив раніше — не курить осторонь, а бере коробки з купи сусіда.
 * У результаті вся робота закінчується раніше.</p>
 *
 * <p><b>Реальне застосування:</b> паралельна обробка великих масивів даних;
 * саме на ForkJoinPool працюють {@code parallelStream()} і {@code Arrays.parallelSort()}.</p>
 */
public class Example08_WorkStealingPool {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // === 1. Пул за замовчуванням = кількість ядер ===
        System.out.println("=== 1. Скільки потоків у work stealing pool ===");
        System.out.println("  ядер доступно JVM: " + Runtime.getRuntime().availableProcessors());
        ExecutorService byCores = Executors.newWorkStealingPool();      // рівень паралелізму = ядра
        ExecutorService byNumber = Executors.newWorkStealingPool(2);    // або задаємо самі
        System.out.println("  реалізація пулу  : " + byNumber.getClass().getSimpleName());
        System.out.println("  паралелізм       : " + ((ForkJoinPool) byNumber).getParallelism());
        byCores.shutdown();
        System.out.println();

        // === 2. Нерівномірні задачі: вільні потоки "крадуть" чужу роботу ===
        // Сценарій: 8 замовлень різної складності. Хто звільнився — бере наступне.
        System.out.println("=== 2. Нерівномірна робота розбирається повністю ===");
        int[] weights = {500, 50, 50, 50, 400, 50, 50, 50};   // мс на замовлення

        List<Callable<String>> orders = new ArrayList<>();
        for (int i = 0; i < weights.length; i++) {
            int number = i + 1;
            int duration = weights[i];
            orders.add(() -> {
                Thread.sleep(duration);
                return "замовлення " + number + " (" + duration + " мс) | " + name();
            });
        }

        long start = System.currentTimeMillis();
        List< Future<String> > done = byNumber.invokeAll(orders);   // чекає всіх
        for (Future<String> result : done) {
            System.out.println("  " + result.get());
        }
        System.out.println("  усе виконано за " + (System.currentTimeMillis() - start) + " мс"
                + " (послідовно було б ~1200 мс)");
        byNumber.shutdown();
        byNumber.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println();

        // === 3. Fork/Join: розділяй задачу — і володарюй ===
        // Сума великого масиву: ділимо навпіл, доки шматок не стане дрібним.
        System.out.println("=== 3. Основа пулу — fork/join ===");
        long[] numbers = new long[1_000_000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        long sum = forkJoinPool.invoke(new SumTask(numbers, 0, numbers.length));
        System.out.println("  сума 1..1 000 000 = " + sum);
        System.out.println("  задач-шматків створено: " + SumTask.parts());
        System.out.println("  потоків задіяно: " + forkJoinPool.getPoolSize());
        forkJoinPool.shutdown();
        System.out.println();

        // === 4. Спільний пул: він уже працює у вашій програмі ===
        System.out.println("=== 4. ForkJoinPool.commonPool() ===");
        System.out.println("  паралелізм спільного пулу: " + ForkJoinPool.commonPool().getParallelism());
        long parallelSum = java.util.stream.LongStream.rangeClosed(1, 1_000_000)
                .parallel()                          // працює саме на commonPool
                .sum();
        System.out.println("  parallelStream дав ту саму суму: " + parallelSum);
    }

    /** Рекурсивна задача: сума частини масиву. Великий шматок ділиться навпіл. */
    static class SumTask extends RecursiveTask<Long> {

        private static final int THRESHOLD = 100_000;     // менший шматок рахуємо самі
        private static final java.util.concurrent.atomic.AtomicInteger PARTS =
                new java.util.concurrent.atomic.AtomicInteger();

        private final long[] data;
        private final int from;
        private final int to;

        SumTask(long[] data, int from, int to) {
            this.data = data;
            this.from = from;
            this.to = to;
            PARTS.incrementAndGet();
        }

        @Override
        protected Long compute() {
            if (to - from <= THRESHOLD) {                 // достатньо дрібно — рахуємо
                long result = 0;
                for (int i = from; i < to; i++) {
                    result += data[i];
                }
                return result;
            }

            int middle = (from + to) / 2;
            SumTask left = new SumTask(data, from, middle);
            SumTask right = new SumTask(data, middle, to);

            left.fork();                                  // ліву половину — у пул
            long rightResult = right.compute();           // праву рахуємо тут же
            long leftResult = left.join();                // забираємо результат лівої

            return leftResult + rightResult;
        }

        static int parts() {
            return PARTS.get();
        }
    }

    private static String name() {
        return Thread.currentThread().getName();
    }
}
