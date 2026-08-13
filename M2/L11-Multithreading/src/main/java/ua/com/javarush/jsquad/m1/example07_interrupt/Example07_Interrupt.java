package ua.com.javarush.jsquad.m1.example07_interrupt;

import java.util.ArrayList;
import java.util.List;

/**
 * Модуль 2. Рівень 11. Знайомство з потоками
 * <hr>
 * <h3>Тема: Метод {@code interrupt} — цивілізований сигнал зупинки</h3>
 *
 * <p>У класі {@code Thread} є вбудований прапорець та методи для нього:</p>
 * <pre>
 *   • interrupt()        — виставляє прапорець "тебе перервали" у true
 *   • isInterrupted()    — перевіряє цей прапорець (true/false)
 * </pre>
 *
 * <p>Це "штатний" аналог власного {@code volatile boolean} з попереднього прикладу —
 * тільки прапорець уже є всередині кожного потоку.</p>
 *
 * <h4>Приклад із лекції — годинник (Clock):</h4>
 * <pre>
 *   class Clock implements Runnable {
 *       public void run() {
 *           Thread current = Thread.currentThread();     // "нинішній потік"
 *           while (!current.isInterrupted()) {           // поки прапорець == false
 *               try { Thread.sleep(1000); }
 *               catch (InterruptedException e) { current.interrupt(); }
 *               System.out.println("Tik");
 *           }
 *       }
 *   }
 *   // main: start() -> Thread.sleep(10000) -> clockThread.interrupt();
 * </pre>
 *
 * <p><b>Важлива деталь про sleep:</b> метод {@code sleep} сам перевіряє прапорець.
 * Якщо потік перервали під час сну — {@code sleep} не спить, а <b>кидає</b>
 * {@code InterruptedException}. При цьому прапорець скидається в false, тому в
 * {@code catch} його часто виставляють назад через {@code current.interrupt()}.</p>
 *
 * <p><b>Аналогія з життя:</b> годинник цокав би вічно, але ви натиснули кнопку
 * "стоп" (interrupt). Годинник помічає натискання і сам зупиняється.</p>
 *
 * <p><i>Таймінги зменшено (у лекції — 1000 та 10000 мс), щоб приклад виконувався швидко.</i></p>
 */
public class Example07_Interrupt {

    // Годинник, що цокає, поки його не перервуть (приклад із лекції).
    static class Clock implements Runnable {
        @Override
        public void run() {
            Thread current = Thread.currentThread();     // потік, який нас виконує
            int counter = 0;
            while (!current.isInterrupted()) {           // цокаємо, поки не перервали
                try {
                    Thread.sleep(300);                   // у лекції — 1000 мс
                } catch (InterruptedException e) {
                    // sleep перервали: прапорець скинуто -> виставляємо назад і виходимо
                    current.interrupt();
                }
                System.out.println("Tik : " + ++counter);
            }
            System.out.println("Годинник зупинився (isInterrupted == true).");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Годинник цокає, поки його не перервуть ===");


        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(new Clock()));
        }


        for (Thread thread : threads) {
            thread.start();
        }

        Thread.currentThread().sleep(1100);


        for (Thread thread : threads) {
//            thread.join();
            thread.interrupt();
        }


    }
}
