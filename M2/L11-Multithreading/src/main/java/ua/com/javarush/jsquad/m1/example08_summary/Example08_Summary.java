package ua.com.javarush.jsquad.m1.example08_summary;

/**
 * Модуль 2. Рівень 11. Знайомство з потоками
 * <hr>
 * <h3>Тема: Підсумок — усі можливості разом (менеджер завантажень)</h3>
 *
 * <p>Збираємо всі теми лекції в одному сценарії:</p>
 * <pre>
 *   • Runnable + Thread + start()  — запуск паралельних завантажень
 *   • Thread.sleep()               — імітація "часу на завантаження"
 *   • join()                       — головний потік чекає результатів
 *   • interrupt() / isInterrupted()— скасування занадто довгого завантаження
 * </pre>
 *
 * <p><b>Сценарій:</b> менеджер завантажень качає кілька файлів <b>одночасно</b>.
 * Маленькі файли завантажуються повністю; величезний файл користувач скасовує
 * кнопкою "Скасувати" (interrupt).</p>
 *
 * <p><b>Реальне застосування:</b> так працюють браузери, торент-клієнти,
 * оновлювачі застосунків — кожне завантаження в окремому потоці, будь-яке можна
 * скасувати, не заморожуючи інтерфейс.</p>
 */
public class Example08_Summary {

    // Одне завантаження = один потік. sizeMb — умовний "розмір" файлу.
    static class Download implements Runnable {
        private final String fileName;
        private final int sizeMb;

        Download(String fileName, int sizeMb) {
            this.fileName = fileName;
            this.sizeMb = sizeMb;
        }

        @Override
        public void run() {
            Thread current = Thread.currentThread();
            for (int mb = 1; mb <= sizeMb; mb++) {
                if (current.isInterrupted()) {                    // нас попросили скасувати?
                    System.out.println("✗ " + fileName + " — скасовано на "
                            + (mb - 1) + "/" + sizeMb + " МБ");
                    return;                                       // завершуємо run() достроково
                }
                System.out.println("  " + fileName + ": " + mb + "/" + sizeMb + " МБ");
                try {
                    Thread.sleep(120);                            // імітація завантаження 1 МБ
                } catch (InterruptedException e) {
                    current.interrupt();                          // повертаємо прапорець
                }
            }
            if (!current.isInterrupted()) {
                System.out.println("✓ " + fileName + " — завантажено повністю");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Менеджер завантажень ===");

        // Два маленькі файли та один величезний
        Thread photo = new Thread(new Download("photo.jpg", 12), "dl-photo");
        Thread song  = new Thread(new Download("song.mp3", 10), "dl-song");
        Thread movie = new Thread(new Download("movie.mkv", 2500), "dl-movie");

        // Запускаємо всі завантаження паралельно
        photo.start();
        song.start();
        movie.start();

        // Даємо маленьким файлам догойтатися
        photo.join();
        song.join();

        // Величезний фільм користувач вирішив скасувати
        System.out.println(">>> користувач натиснув 'Скасувати' для movie.mkv");
        movie.interrupt();
        movie.join();

        System.out.println();
        System.out.println("Менеджер завершив роботу. Головний потік виходить.");
    }
}
