package ua.com.javarush.jsquad.m1.example07_deadlock;

/**
 * Модуль 2. Рівень 12. Знайомство з потоками. Частина 2
 * <hr>
 * <h3>Тема: DeadLock — взаємне блокування двох потоків</h3>
 *
 * <p><b>Дедлок</b> (deadlock) — взаємне блокування, коли потоки навічно застрягли,
 * чекаючи один на одного. Виникає, коли складаються ТРИ умови:</p>
 * <pre>
 *   A. Кожному потоку в роботі потрібно захопити ОБИДВА м'ютекси.
 *   B. Перший потік захопив перший м'ютекс і чекає на звільнення другого.
 *   C. Другий потік захопив другий м'ютекс і чекає на звільнення першого.
 * </pre>
 * <p>Ніхто не поступиться — обидва стоять назавжди.</p>
 *
 * <p><b>Як уникнути:</b> завжди захоплювати м'ютекси в <b>однаковому порядку</b>
 * (спершу перший, потім другий — в усіх потоках). Тоді умови B+C не складуться.</p>
 *
 * <p><b>Аналогія з життя:</b> двоє за столом; кожному для їжі потрібні дві виделки.
 * Один схопив ліву й чекає праву, другий — праву й чекає ліву. Обидва голодні назавжди.</p>
 *
 * <p><b>Технічна деталь:</b> потоки-"застрягайли" зроблено daemon — щоб вони не
 * заважали JVM завершитися; а сам дедлок ми виявляємо через {@code join(timeout)}.</p>
 */
public class Example07_Deadlock {

    public static void main(String[] args) throws InterruptedException {

//        // === 1. Класичний дедлок: потоки беруть замки у РІЗНОМУ порядку ===
//        System.out.println("=== Демонстрація дедлоку (потоки застрягнуть) ===");
//
//        final Object forkLeft = new Object();
//        final Object forkRight = new Object();
//
//        Thread a = new Thread(() -> {
//            synchronized (forkLeft) {
//                System.out.println("A: захопив forkLeft, чекаю forkRight...");
//                sleep(200);
//                synchronized (forkRight) {                 // ніколи не дочекається
//                    System.out.println("A: захопив forkRight (сюди не дійдемо)");
//                }
//            }
//        }, "A");
//
//        Thread b = new Thread(() -> {
//            synchronized (forkRight) {
//                System.out.println("B: захопив forkRight, чекаю forkLeft...");
//                sleep(200);
//                synchronized (forkLeft) {                  // ніколи не дочекається
//                    System.out.println("B: захопив forkLeft (сюди не дійдемо)");
//                }
//            }
//        }, "B");
//
//        // daemon: щоб застряглі потоки НЕ заважали JVM завершитися.
//        a.setDaemon(true);
//        b.setDaemon(true);
//        a.start();
//        b.start();
//
//        // Даємо їм час зайти в дедлок і перевіряємо через join із тайм-аутом.
//        a.join(1500);
//        b.join(1500);
//        if (a.isAlive() && b.isAlive()) {
//            System.out.println(">>> Обидва потоки досі живі й нічого не роблять — це ДЕДЛОК.");
//        }
//        System.out.println();

        // === 2. Виправлення: ОБИДВА беруть замки в однаковому порядку ===
        System.out.println("=== Виправлено: єдиний порядок захоплення (спершу перший, потім другий) ===");

        final Object lock1 = new Object();   // нові замки, не ті, що "застрягли" вище
        final Object lock2 = new Object();

        Runnable safeJob = () -> {
            String name = Thread.currentThread().getName();
            synchronized (lock1) {           // усі беруть СПОЧАТКУ lock1...
                synchronized (lock2) {       // ...а ПОТІМ lock2
                    System.out.println(name + ": захопив обидва замки і спокійно поїв :)");
                }
            }
        };
        Thread c = new Thread(safeJob, "C");
        Thread d = new Thread(safeJob, "D");
        c.start();
        d.start();
        c.join();
        d.join();

        System.out.println();
        System.out.println("Однаковий порядок захоплення замків — і дедлок неможливий.");
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
