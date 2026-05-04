package ua.com.javarush.jsquad.m1;

/**
 * Лекція 22: Винятки 2 — Зовнішні ресурси та метод close()
 *
 * Зовнішні ресурси — це обʼєкти поза Java-машиною: файли на диску,
 * зʼєднання з базою даних, мережеві сокети. Коли програма відкриває
 * ресурс, ОС виділяє його монопольно. Після завершення роботи ресурс
 * треба звільнити методом close().
 *
 * Проблема: якщо між open() та close() виникне виняток, close() не
 * виконається і ресурс «витече» (resource leak).
 *
 * Рішення (до Java 7): обгорнути код у try-catch-finally і викликати
 * close() у блоці finally — він виконається ЗАВЖДИ.
 *
 * Аналогія з життя: взяв книгу з бібліотеки — повинен повернути.
 * Якщо забув (не викликав close) — книга «зависла» і ніхто інший
 * не може нею скористатися. finally — це нагадування «поверни книгу,
 * що б не сталось».
 *
 * Реальне застосування: робота з файлами, потоками вводу/виводу,
 * зʼєднання з БД, мережеві сокети.
 */
public class Example05_ExternalResources {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Проблема — close() не викликається при помилці
        // ============================================================
        System.out.println("=== Блок 1: Проблема без finally ===");

        // Сценарій: імітація роботи з файлом (без реального файлу)
        MyFileResource file = new MyFileResource("data.txt");
        try {
            file.open();
            file.read();
            // Уявімо, що тут виникла помилка
            throw new RuntimeException("Помилка при обробці даних!");
            // file.close() — НІКОЛИ не виконається!
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
        System.out.println("Ресурс закритий? " + file.isClosed()); // false — витік!

        System.out.println();

        // ============================================================
        //   Блок 2: Рішення — close() у блоці finally
        // ============================================================
        System.out.println("=== Блок 2: Рішення через finally ===");

        // Сценарій: правильна робота з ресурсом через try-catch-finally
        MyFileResource file2 = new MyFileResource("report.txt");
        try {
            file2.open();
            file2.read();
            throw new RuntimeException("Збій при записі!");
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        } finally {
            file2.close(); // ЗАВЖДИ виконається
        }
        System.out.println("Ресурс закритий? " + file2.isClosed()); // true

        System.out.println();

        // ============================================================
        //   Блок 3: Декілька ресурсів — складність зростає
        // ============================================================
        System.out.println("=== Блок 3: Кілька ресурсів з finally ===");

        // Сценарій: копіювання файлу — потрібно відкрити два ресурси
        MyFileResource source = new MyFileResource("source.txt");
        MyFileResource target = new MyFileResource("target.txt");

        try {
            source.open();
            target.open();
            System.out.println("Копіюємо дані з " + source + " в " + target + "...");
            System.out.println("Копіювання завершено!");
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        } finally {
            // Треба закрити ОБА ресурси, навіть якщо перший close() впаде
            try {
                source.close();
            } catch (Exception e) {
                System.out.println("Помилка при закритті source: " + e.getMessage());
            }

            try {
                target.close();
            } catch (Exception e) {
                System.out.println("Помилка при закритті target: " + e.getMessage());
            }
        }

        System.out.println("source закритий? " + source.isClosed());
        System.out.println("target закритий? " + target.isClosed());

        System.out.println();
        System.out.println("Висновок: close() через finally працює, але код стає громіздким.");
        System.out.println("Особливо з кількома ресурсами — багато вкладених try-catch.");
        System.out.println("Java 7 вирішила це через try-with-resources (наступний приклад).");
    }

    // --- Імітація зовнішнього ресурсу ---

    static class MyFileResource {
        private final String name;
        private boolean opened = false;
        private boolean closed = false;

        MyFileResource(String name) {
            this.name = name;
        }

        void open() {
            opened = true;
            System.out.println("[" + name + "] Ресурс відкрито");
        }

        void read() {
            System.out.println("[" + name + "] Читання даних...");
        }

        void close() {
            if (opened && !closed) {
                closed = true;
                System.out.println("[" + name + "] Ресурс закрито");
            }
        }

        boolean isClosed() {
            return closed;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
