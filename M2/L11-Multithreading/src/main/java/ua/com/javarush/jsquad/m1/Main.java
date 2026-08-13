package ua.com.javarush.jsquad.m1;

/**
 * Модуль 2. Рівень 11. Знайомство з потоками — ЗМІСТ ПРИКЛАДІВ
 *
 * <p>Лекція охоплює: багатопотоковість, створення та запуск потоків (два способи),
 * метод {@code sleep}, зупинку потоку та метод {@code interrupt}.</p>
 *
 * <p>Кожен приклад — самодостатній клас зі своїм {@code main()}.
 * Запускайте їх по черзі (відкрийте файл і натисніть ▶ біля {@code main}).</p>
 *
 * <pre>
 *  №   Тема                                       Клас для запуску
 *  ────────────────────────────────────────────────────────────────────────────
 *  01  Багатопотоковість (основи)                 example01_multithreading_basics.Example01_MultithreadingBasics
 *  02  Створення потоку через Runnable            example02_runnable.Example02_Runnable
 *  03  Створення потоку через extends Thread      example03_extends_thread.Example03_ExtendsThread
 *  04  Метод sleep                                example04_sleep.Example04_Sleep
 *  05  Кілька потоків одночасно (join)            example05_parallel_threads.Example05_ParallelThreads
 *  06  Зупинка потоку (прапорець volatile)        example06_stopping_thread.Example06_StoppingThread
 *  07  Метод interrupt (Clock із лекції)          example07_interrupt.Example07_Interrupt
 *  08  Підсумок: менеджер завантажень             example08_summary.Example08_Summary
 *  09  Реальний сценарій: по черзі vs паралельно  example09_real_world.Example09_RealWorld
 * </pre>
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Модуль 2. Рівень 11 — Знайомство з потоками");
        System.out.println("9 прикладів у пакетах example01..example09.");
        System.out.println("Теми: багатопотоковість, Runnable/Thread, sleep, зупинка потоку, interrupt.");
        System.out.println("Відкрийте потрібний ExampleNN_*.java і запустіть його main().");
    }
}
