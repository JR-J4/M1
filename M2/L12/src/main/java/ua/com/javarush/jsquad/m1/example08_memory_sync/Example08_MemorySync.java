package ua.com.javarush.jsquad.m1.example08_memory_sync;

/**
 * Модуль 2. Рівень 12. Знайомство з потоками. Частина 2
 * <hr>
 * <h3>Тема: Умови синхронізації пам'яті (happens-before)</h3>
 *
 * <p>Ми бачили дві біди багатопотоковості: <b>гонки</b> (race) під час спільного
 * запису та <b>невидимість</b> змін через кеші процесора. Java описує правила, за
 * яких зміни ОДНОГО потоку <b>гарантовано видно</b> іншому. Ці правила називають
 * "happens-before" ("відбувається-до") — це і є умови синхронізації пам'яті.</p>
 *
 * <h4>Основні гарантії happens-before:</h4>
 * <pre>
 *   • Запис volatile-змінної видно кожному, хто потім її прочитає.
 *   • Вихід із synchronized-блоку видно тому, хто далі зайде в той самий блок.
 *   • Усе, що зроблено ДО thread.start(), видно всередині нового потоку.
 *   • Усе, що зробив потік, видно тому, хто дочекався його через thread.join().
 * </pre>
 *
 * <p><b>Класичний прийом (публікація через volatile-прапорець):</b> потік спочатку
 * готує дані, а НАОСТАНОК ставить volatile-прапорець {@code ready = true}. Хто
 * побачив {@code ready == true}, той гарантовано бачить і всі підготовлені дані.</p>
 *
 * <p><b>Аналогія з життя:</b> кухар готує страву (дані) й аж потім вмикає лампочку
 * "готово" (volatile). Офіціант, побачивши лампочку, знає: страва точно готова вся.</p>
 */
public class Example08_MemorySync {

    static class Config {
        int port;                          // звичайні поля
        String host;
        volatile boolean ready = false;    // "прапорець публікації"
    }

    public static void main(String[] args) throws InterruptedException {

        // === 1. Публікація через volatile: reader бачить узгоджені дані ===
        System.out.println("=== happens-before: публікація даних через volatile ===");
        Config config = new Config();

        Thread writer = new Thread(() -> {
            config.host = "javarush.com";   // 1) готуємо дані
            config.port = 443;              // 2) ...
            config.ready = true;            // 3) volatile-запис НАОСТАНОК — "опубліковано"
        }, "writer");

        Thread reader = new Thread(() -> {
            while (!config.ready) {          // чекаємо публікації (volatile-читання)
                Thread.yield();
            }
            // Побачили ready == true -> host і port гарантовано готові (happens-before)
            System.out.println("reader бачить готовий конфіг: " + config.host + ":" + config.port);
        }, "reader");

        reader.start();
        writer.start();

        writer.join();
        reader.join();
        System.out.println();

        // === 2. start() і join() теж встановлюють happens-before ===
        System.out.println("=== happens-before через start() та join() ===");
        int[] box = new int[1];
        box[0] = 42;                         // записали ДО start()

        Thread t = new Thread(() -> {
            // усе, що було до start(), тут гарантовано видно:
            System.out.println("потік бачить box[0] = " + box[0]);
            box[0] = 100;                    // змінюємо всередині потоку
        });
        t.start();
        t.join();                            // після join() видно все, що зробив потік

        System.out.println("головний потік після join() бачить box[0] = " + box[0]);
        System.out.println();
        System.out.println("Висновок: synchronized і volatile не лише захищають від гонок,");
        System.out.println("а й ГАРАНТУЮТЬ видимість змін між потоками.");
    }
}
