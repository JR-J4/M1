package ua.com.javarush.jsquad.m1.example03_extends_thread;

/**
 * Модуль 2. Рівень 11. Знайомство з потоками
 * <hr>
 * <h3>Тема: Створення потоку — спосіб №2, успадкування від {@code Thread}</h3>
 *
 * <p>Клас {@code Thread} сам реалізує інтерфейс {@code Runnable}. Тому можна
 * не створювати окремий Runnable-клас, а просто успадкуватися від {@code Thread}
 * і перевизначити його метод {@code run()}.</p>
 *
 * <h4>Синтаксис (приклад із лекції):</h4>
 * <pre>
 *   class Printer extends Thread {
 *       private String name;
 *       public Printer(String name) { this.name = name; }
 *       public void run() { System.out.println("I'm " + this.name); }
 *   }
 *
 *   Printer p1 = new Printer("Вася");  p1.start();
 *   Printer p2 = new Printer("Коля");  p2.start();
 * </pre>
 *
 * <p><b>Два способи — коли який обирати:</b></p>
 * <pre>
 *   • implements Runnable — гнучкіше: клас може успадкувати щось ІНШЕ,
 *     а один Runnable можна віддати кільком потокам.
 *   • extends Thread — коротше, але "витрачає" єдиний дозвіл на успадкування.
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> тут працівник (Thread) сам знає свою роботу —
 * не потрібно давати йому окремий "лист із завданням".</p>
 */
public class Example03_ExtendsThread {

    // Успадковуємось від Thread і перевизначаємо run() (приклад із лекції).
    static class Printer extends Thread {
        private final String name;

        public Printer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("Я — " + this.name
                    + " (системний потік: " + Thread.currentThread().getName() + ")");
        }
    }

    public static void main(String[] args) {

        // === 1. Два потоки, кожен на своєму об'єкті Printer ===
        System.out.println("=== Два дочірні потоки ===");
        Printer printer = new Printer("Вася");
        printer.start();                       // start(), а не run()!

        Printer printer2 = new Printer("Коля");
        printer2.start();

        System.out.println("Головний потік роздав завдання і йде далі");
        // Порядок появи "Вася"/"Коля" НЕ гарантований — вирішує планувальник.
    }
}
