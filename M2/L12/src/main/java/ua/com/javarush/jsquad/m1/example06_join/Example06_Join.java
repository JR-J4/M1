package ua.com.javarush.jsquad.m1.example06_join;

/**
 * Модуль 2. Рівень 12. Знайомство з потоками. Частина 2
 * <hr>
 * <h3>Тема: join() — дочекатися завершення іншого потоку</h3>
 *
 * <p>Один потік може викликати {@code join()} на <b>об'єкті</b> іншого потоку.
 * Тоді потік, який викликав join, <b>призупиняється</b> й чекає, поки другий потік
 * завершить роботу. Лише після цього перший потік продовжує далі.</p>
 *
 * <p><b>Важливо розрізняти два поняття:</b></p>
 * <pre>
 *   • потік         — окремий процес виконання команд (те, що "біжить");
 *   • об'єкт Thread — об'єкт у пам'яті, у якого ми викликаємо методи (start, join...).
 * </pre>
 *
 * <p>Є й версія з тайм-аутом: {@code join(500)} чекає щонайбільше 500 мс —
 * якщо потік не встиг завершитися, ми все одно йдемо далі.</p>
 *
 * <p><b>Аналогія з життя:</b> ви не почнете їсти торт, поки друг не повернеться зі
 * свічками. Ви "join-итесь" — стоїте й чекаєте саме на нього.</p>
 *
 * <p><b>Реальне застосування:</b> головний потік роздає підрахунки робітникам і
 * через join() чекає всі результати, перш ніж вивести підсумок.</p>
 */
public class Example06_Join {

    // Потік-рахувальник: повільно обчислює суму й кладе її в result.
    static class Calculator implements Runnable {
        private long result = 0;
        private final int upTo;

        Calculator(int upTo) {
            this.upTo = upTo;
        }

        long getResult() {
            return result;
        }

        @Override
        public void run() {
            long sum = 0;
            for (int i = 1; i <= upTo; i++) {
                sum += i;
            }
            try {
                Thread.sleep(3000);      // імітуємо, що обчислення тривале
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            result = sum;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== join(): головний потік чекає результат обчислення ===");
        Calculator task = new Calculator(1_000_000);
        Thread worker = new Thread(task, "calc");
        worker.start();

        // Якби ми прочитали результат БЕЗ join() — отримали б 0 (потік ще рахує).
        System.out.println("результат одразу після start() (ще не готовий): " + task.getResult());

        worker.join();      // <-- стоїмо тут, поки calc не завершиться
        System.out.println("результат після join()      (готовий):       " + task.getResult());
        System.out.println();

        // === join(timeout): чекаємо повільний потік щонайбільше 500 мс ===
        System.out.println("=== join(timeout): не чекаємо надто довго ===");
        Thread slow = new Thread(() -> {
            try {
                Thread.sleep(3000);     // "завис" на 3 секунди
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "slow");
        slow.start();

        slow.join(500);     // чекаємо максимум 0.5 с
        System.out.println("минуло 0.5 с. Потік slow ще живий? " + slow.isAlive());
        System.out.println("Ідемо далі, не чекаючи його повного завершення.");

        slow.interrupt();   // приберемо за собою, щоб JVM не чекала зайвих 2.5 с
    }
}
