package ua.com.javarush.jsquad.m1.example02_synchronized;

/**
 * Модуль 2. Рівень 12. Знайомство з потоками. Частина 2
 * <hr>
 * <h3>Тема: synchronized — м'ютекс, вбудований у кожен об'єкт</h3>
 *
 * <p>Щоб потоки не заважали один одному, автори мови придумали <b>м'ютекс</b> —
 * спеціальний "замок" із двома станами: <b>вільний</b> і <b>зайнятий</b>
 * (розблокований / заблокований). Найкраще те, що цей м'ютекс убудований у клас
 * {@code Object}, тож він є <b>в кожного об'єкта</b> — створювати нічого не треба.</p>
 *
 * <h4>Як працює блок synchronized:</h4>
 * <pre>
 *   synchronized (someObject) {
 *       // ... критична секція ...
 *   }
 * </pre>
 * <p>Коли потік заходить у блок, Java-машина <b>блокує</b> м'ютекс об'єкта в дужках.
 * Жоден інший потік не зайде в блок, синхронізований по ТОМУ Ж об'єкту, поки наш
 * потік не вийде. На виході м'ютекс автоматично звільняється для наступного.</p>
 *
 * <p><b>Приклад із лекції:</b> метод {@code swap()} міняє місцями name1 та name2.
 * Без синхронізації два потоки, що викликають swap одночасно, можуть "переплутати"
 * обмін. synchronized (this) робить обмін неподільним.</p>
 *
 * <p><b>Аналогія з життя:</b> кабінка примірочної. Поки ти всередині — двері
 * замкнені, інші чекають. Вийшов — замок відкрився, заходить наступний.</p>
 */
public class Example02_Synchronized {

    // Приклад із лекції: пара імен, які можна міняти місцями.
    static class NamePair {
        private String name1 = "Оля";
        private String name2 = "Лена";

        // Обмін захищено м'ютексом цього ж об'єкта (this).
        public void swap() {
            synchronized (this) {
                String s = name1;
                name1 = name2;
                name2 = s;
            }
        }

        public synchronized String snapshot() {   // читаємо теж під замком
            return name1 + " / " + name2;
        }
    }

    // Той самий лічильник, що "губив" оновлення в Example01 — тепер під замком.
    static class SafeCounter {
        private int count = 0;
        private final Object lock = new Object();   // окремий об'єкт-замок

        void increment() {
            synchronized (lock) {   // тільки один потік за раз
                count++;
            }
        }

        int get() {
            synchronized (lock) {
                return count;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // === 1. swap() із лекції під synchronized (this) ===
//        System.out.println("=== synchronized (this): обмін імен ===");
//        NamePair pair = new NamePair();
//        System.out.println("до обмінів:        " + pair.snapshot());
//        for (int i = 0; i < 1001; i++) {   // непарна кількість -> імена поміняються місцями
//            pair.swap();
//        }
//        System.out.println("після 1001 swap:   " + pair.snapshot());
//        System.out.println();

        // === 2. Лічильник під замком рахує ПРАВИЛЬНО ===
        System.out.println("=== synchronized (lock): лічильник більше не губить оновлення ===");
        final int TIMES = 1_000_000;
        SafeCounter counter = new SafeCounter();

        Runnable job = () -> {
            for (int i = 0; i < TIMES; i++) {
                counter.increment();
            }
        };
        Thread t1 = new Thread(job);
        Thread t2 = new Thread(job);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Очікували: " + (TIMES * 2));
        System.out.println("Отримали:  " + counter.get() + "  (тепер точно збігається!)");


    }
}
