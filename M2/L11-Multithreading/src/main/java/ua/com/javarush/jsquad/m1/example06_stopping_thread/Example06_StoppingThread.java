package ua.com.javarush.jsquad.m1.example06_stopping_thread;

/**
 * Модуль 2. Рівень 11. Знайомство з потоками
 * <hr>
 * <h3>Тема: Зупинка потоку — потік зупиняє себе САМ</h3>
 *
 * <p>Потік <b>не можна</b> зупинити ззовні примусово. Він може зупинитися лише сам.
 * Все, що ми можемо — <b>дати сигнал</b>, що роботу пора завершувати.</p>
 *
 * <p>Так само, як головний потік завершується разом із виходом із {@code main},
 * дочірній потік завершується, коли закінчує виконання методу {@code run}.</p>
 *
 * <h4>Найпростіший сигнал — прапорець-змінна:</h4>
 * <pre>
 *   volatile boolean running = true;   // volatile — щоб зміну "бачили" всі потоки
 *   // у run():   while (running) { ...робота... }
 *   // ззовні:    running = false;     // сигнал завершитись
 * </pre>
 *
 * <p><b>Навіщо {@code volatile}:</b> без нього дочірній потік може закешувати
 * значення прапорця і не помітити, що головний його змінив.</p>
 *
 * <p><b>Аналогія з життя:</b> ви не можете вимкнути колегу кнопкою. Ви кажете
 * "закінчуй на сьогодні" (сигнал), а він сам доробляє поточну справу і йде.</p>
 */
public class Example06_StoppingThread {

    // Робітник, який працює в циклі, поки прапорець running == true.
    static class Worker implements Runnable {
        // volatile гарантує, що зміну прапорця побачать усі потоки
        private volatile boolean running = true;

        public void stopWork() {          // "сигнал" ззовні
            running = false;
        }

        @Override
        public void run() {
            int step = 0;
            while (running) {             // працюємо, поки не отримали сигнал
                step++;
                System.out.println("робітник виконує крок " + step);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    return;
                }
            }
            // Вихід із циклу -> завершення run() -> завершення потоку
            System.out.println("робітник отримав сигнал і завершив роботу");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Зупинка потоку через прапорець ===");
        Worker worker = new Worker();
        Thread thread = new Thread(worker);
        thread.start();

        // Даємо попрацювати ~0.7 секунди
        Thread.sleep(700);

        // Подаємо сигнал завершитись (НЕ примусова зупинка!)
        System.out.println(">>> головний потік: пора закінчувати");
        worker.stopWork();

        // Чекаємо, поки потік справді завершиться
        thread.join();
        System.out.println("Готово. Потік зупинив себе сам.");
    }
}
