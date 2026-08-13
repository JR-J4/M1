package ua.com.javarush.jsquad.m1.example04_sleep;

/**
 * Модуль 2. Рівень 11. Знайомство з потоками
 * <hr>
 * <h3>Тема: Метод {@code sleep} — "заснути" на деякий час</h3>
 *
 * <p>{@code Thread.sleep(millis)} — <b>статичний</b> метод класу {@code Thread}
 * (не прив'язаний до жодного об'єкта). Він присипляє <b>той потік</b>, який його
 * викликав, на вказану кількість мілісекунд.</p>
 *
 * <h4>Синтаксис (приклад із лекції):</h4>
 * <pre>
 *   public static void main(String[] args) throws InterruptedException {
 *       Thread.sleep(2000);   // програма застигне на 2 секунди
 *   }
 * </pre>
 *
 * <p>{@code sleep} оголошено з {@code throws InterruptedException}, тому виклик
 * треба обгорнути в {@code try/catch} або додати {@code throws} до методу.</p>
 *
 * <p><b>Аналогія з життя:</b> будильник з паузою. Ви сказали "розбуди через
 * 2 секунди" — і на цей час нічого не робите.</p>
 *
 * <p><b>Реальне застосування:</b> часто використовується в дочірніх потоках, коли
 * дію треба повторювати, але <b>не надто часто</b> (напр. перевіряти пошту раз на 5с).</p>
 */
public class Example04_Sleep {

    public static void main(String[] args) throws InterruptedException {

        // === 1. Найпростіший sleep у головному потоці ===
        System.out.println("=== Пауза 1 секунда ===");
        System.out.println("Програма запустилася...");
        Thread.sleep(1000);                  // 1000 мс = 1 секунда
        System.out.println("...минула секунда, продовжуємо");
        System.out.println();

        // === 2. Періодична дія в дочірньому потоці ===
        // Годинник, який "цокає" раз на 300 мс — але не частіше завдяки sleep.
        System.out.println("=== Дочірній потік цокає раз на 300 мс ===");
        Thread ticker = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("тік " + i);
                try {
                    Thread.sleep(3000);       // пауза між "цоканнями"
                } catch (InterruptedException e) {
                    return;                  // якщо потік перервали — виходимо
                }
            }
        });

        ticker.start();

        // Головний потік чекає, поки дочірній закінчить (щоб побачити всі "тіки").
        ticker.join();
        System.out.println("Дочірній потік завершився.");
    }
}
