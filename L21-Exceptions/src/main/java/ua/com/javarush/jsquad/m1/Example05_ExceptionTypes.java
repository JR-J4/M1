package ua.com.javarush.jsquad.m1;

/**
 * Лекція 21: Винятки — Види винятків (ієрархія)
 *
 * Усі винятки в Java утворюють ієрархію класів:
 *
 *   Throwable (базовий клас)
 *   ├── Error — серйозні помилки JVM (OutOfMemoryError, StackOverflowError)
 *   │          Програміст НЕ повинен їх перехоплювати!
 *   └── Exception — звичайні винятки під час роботи методів
 *       └── RuntimeException — підтип Exception
 *
 * Поділ на Checked і Unchecked:
 *   - Unchecked (НЕ перевіряються компілятором):
 *       Error + RuntimeException та їх нащадки
 *       Не потрібно оголошувати в throws або обовʼязково ловити
 *   - Checked (перевіряються компілятором):
 *       Усі інші Exception (IOException, SQLException тощо)
 *       ОБОВ'ЯЗКОВО або ловити (catch), або оголошувати (throws)
 *
 * Аналогія з життя: помилки бувають двох видів.
 *   - Unchecked — як спіткнутися на рівній дорозі. Може трапитись будь-коли,
 *     неможливо передбачити кожен крок. Компілятор не змушує тебе ходити в шоломі.
 *   - Checked — як перехід дороги. Небезпека очевидна, тому закон ЗОБОВ'ЯЗУЄ
 *     дивитися на світлофор (обробити виняток).
 *
 * Реальне застосування: розуміння ієрархії допомагає правильно обирати,
 * які винятки ловити, а які пропускати.
 */
public class Example05_ExceptionTypes {

    public static void main(String[] args) {


        // ============================================================
        //   Блок 1: Ієрархія — хто від кого наслідується
        // ============================================================
        System.out.println("=== Блок 1: Ієрархія винятків ===");

        System.out.println("Throwable");
        System.out.println("├── Error (серйозні, НЕ ловимо)");
        System.out.println("│   ├── OutOfMemoryError");
        System.out.println("│   ├── StackOverflowError");
        System.out.println("│   └── ...");
        System.out.println("└── Exception (звичайні, ловимо)");
        System.out.println("    ├── IOException          [CHECKED]");
        System.out.println("    ├── SQLException          [CHECKED]");
        System.out.println("    ├── FileNotFoundException [CHECKED]");
        System.out.println("    └── RuntimeException      [UNCHECKED]");
        System.out.println("        ├── NullPointerException");
        System.out.println("        ├── ArithmeticException");
        System.out.println("        ├── ArrayIndexOutOfBoundsException");
        System.out.println("        ├── NumberFormatException");
        System.out.println("        ├── IllegalArgumentException");
        System.out.println("        └── ...");
        System.out.println();

        // ============================================================
        //   Блок 2: Unchecked — RuntimeException (не обовʼязково ловити)
        // ============================================================
        System.out.println("=== Блок 2: Unchecked-винятки (RuntimeException) ===");

        // Компілятор НЕ змушує нас ловити ці винятки.
        // Але якщо не зловимо — програма впаде під час виконання.

        // NullPointerException

        try {
            String text = null;
            text.toUpperCase();
        }
        catch (NullPointerException e) {
            System.out.println("NullPointerException — звернення до null");
        }

        // NumberFormatException
        try {
            int num = Integer.parseInt("abc");
        }
        catch (NumberFormatException e) {
            System.out.println("NumberFormatException — некоректний формат числа");
        }

        // ClassCastException
        try {
            Object obj = "Рядок";
            Integer num = (Integer) obj; // String → Integer — неможливо
        }
        catch (ClassCastException e) {
            System.out.println("ClassCastException — неможливе приведення типу");
        }

        // IllegalArgumentException
        try {
            int[] arr = new int[-5]; // відʼємний розмір масиву
        }
        catch (NegativeArraySizeException e) {
            System.out.println("NegativeArraySizeException — відʼємний розмір масиву");
        }

        System.out.println("Усі ці винятки — unchecked (наслідують RuntimeException).");
        System.out.println("Компілятор дозволяє НЕ ловити їх, але програма впаде.");
        System.out.println();

        // ============================================================
        //   Блок 3: Error — критичні помилки JVM
        // ============================================================
        System.out.println("=== Блок 3: Error — помилки JVM ===");

        // StackOverflowError — переповнення стеку (наприклад, нескінченна рекурсія)
        try {
            infiniteRecursion(1);
        }
        catch (StackOverflowError e) {
            System.out.println("StackOverflowError — стек переповнений (нескінченна рекурсія)");
        }

        System.out.println("Error — це НЕ Exception. Їх не варто ловити у продакшн-коді!");
        System.out.println("Тут ловимо лише для демонстрації.");
        System.out.println();

        // ============================================================
        //   Блок 4: Перевірка типу винятку через instanceof
        // ============================================================
        System.out.println("=== Блок 4: Перевірка типу винятку ===");

        Exception[] exceptions = {
                new RuntimeException("Runtime"),
                new NullPointerException("NPE"),
                new IllegalArgumentException("Illegal"),
                new Exception("Checked")
        };

        for (Exception e : exceptions) {
            String type;
            if (e instanceof RuntimeException) {
                type = "UNCHECKED";
            } else {
                type = "CHECKED";
            }
            System.out.println("  " + e.getClass().getSimpleName() + " → " + type);
        }

        System.out.println();
        System.out.println("Правило: RuntimeException та його нащадки — unchecked.");
        System.out.println("Усі інші Exception — checked (компілятор вимагає обробки).");
    }

    /**
     * Метод викликає сам себе нескінченно → StackOverflowError.
     */
    static void infiniteRecursion(int n) {
        infiniteRecursion(n + 1);
    }
}
