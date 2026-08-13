package ua.com.javarush.jsquad.m1;

/**
 * Модуль 2. Рівень 12. Знайомство з потоками. Частина 2 — ЗМІСТ ПРИКЛАДІВ
 *
 * <p>Лекція охоплює: мінуси багатопотоковості, {@code synchronized} (блок і метод),
 * {@code volatile}, {@code yield}, {@code join}, {@code DeadLock} та умови
 * синхронізації пам'яті (happens-before).</p>
 *
 * <p>Кожен приклад — самодостатній клас зі своїм {@code main()}.
 * Запускайте їх по черзі (відкрийте файл і натисніть ▶ біля {@code main}).</p>
 *
 * <pre>
 *  №   Тема                                       Клас для запуску
 *  ────────────────────────────────────────────────────────────────────────────
 *  01  Мінуси багатопотоковості (гонка потоків)   example01_multithreading_downsides.Example01_MultithreadingDownsides
 *  02  synchronized-блок (м'ютекс, this)          example02_synchronized.Example02_Synchronized
 *  03  synchronized-метод (this / клас)           example03_synchronized_method.Example03_SynchronizedMethod
 *  04  volatile (видимість між потоками)          example04_volatile.Example04_Volatile
 *  05  Thread.yield() (поступитися квантом)       example05_yield.Example05_Yield
 *  06  join() (дочекатися іншого потоку)          example06_join.Example06_Join
 *  07  DeadLock (взаємне блокування)              example07_deadlock.Example07_Deadlock
 *  08  Умови синхронізації пам'яті (happens-before) example08_memory_sync.Example08_MemorySync
 *  09  Підсумок: продаж квитків онлайн            example09_summary.Example09_Summary
 * </pre>
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Модуль 2. Рівень 12 — Знайомство з потоками. Частина 2");
        System.out.println("9 прикладів у пакетах example01..example09.");
        System.out.println("Теми: мінуси багатопотоковості, synchronized, volatile, yield, join, DeadLock, happens-before.");
        System.out.println("Відкрийте потрібний ExampleNN_*.java і запустіть його main().");
    }
}
