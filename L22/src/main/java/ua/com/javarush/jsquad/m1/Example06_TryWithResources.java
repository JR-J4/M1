package ua.com.javarush.jsquad.m1;

/**
 * Лекція 22: Винятки 2 — try-with-resources та AutoCloseable
 *
 * Починаючи з Java 7, зʼявився оператор try-with-resources — синтаксичний
 * цукор для автоматичного закриття ресурсів.
 *
 * Синтаксис:
 *   try (Клас імʼя = new Клас()) {
 *       // код, який працює зі змінною імʼя
 *   }
 *   // компілятор сам додасть finally { імʼя.close(); }
 *
 * Кілька ресурсів (розділяються крапкою з комою):
 *   try (Клас1 a = new Клас1(); Клас2 b = new Клас2()) {
 *       // код
 *   }
 *
 * Інтерфейс AutoCloseable — має лише один метод close().
 * У try-with-resources можна передавати ТІЛЬКИ обʼєкти, що реалізують
 * AutoCloseable (або його батьківський Closeable).
 *
 * Аналогія з життя: автоматичні двері в супермаркеті. Зайшов — двері
 * відчинились, вийшов — двері САМІ зачинились. Не потрібно памʼятати
 * про закриття — система зробить це за тебе.
 *
 * Реальне застосування: робота з файлами (FileReader, BufferedReader),
 * зʼєднання з БД (Connection), потоки (InputStream, OutputStream).
 */
public class Example06_TryWithResources {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Базовий try-with-resources
        // ============================================================
        System.out.println("=== Блок 1: Один ресурс ===");

        // Сценарій: відкриваємо зʼєднання з базою, виконуємо запит
        try ( DatabaseConnection db = new DatabaseConnection("users_db") ) {
            db.query("SELECT * FROM users");
            db.query("SELECT * FROM orders");
        } // close() викличеться автоматично!

        System.out.println("Після try — зʼєднання вже закрите.");
        System.out.println();

        // ============================================================
        //   Блок 2: Кілька ресурсів одночасно
        // ============================================================
        System.out.println("=== Блок 2: Декілька ресурсів ===");

        // Сценарій: копіювання даних з одного джерела в інше
        try ( DataReader reader = new DataReader("input.csv");
             DataWriter writer = new DataWriter("output.csv") ) {

            String data = reader.readAll();
            writer.write(data);
            System.out.println("Копіювання завершено!");
        }
        // Обидва ресурси закриються автоматично (у зворотному порядку!)

        System.out.println();

        // ============================================================
        //   Блок 3: try-with-resources + catch
        // ============================================================
        System.out.println("=== Блок 3: try-with-resources + catch ===");

        // Сценарій: ресурс відкрився, але при роботі виникла помилка
        try (DatabaseConnection db = new DatabaseConnection("products_db")) {
            db.query("SELECT * FROM products");
            throw new RuntimeException("Таблиця не знайдена!");
        } catch (RuntimeException e) {
            System.out.println("Помилка: " + e.getMessage());
            System.out.println("Але ресурс УЖЕ закритий (close() викликано ДО catch)!");
        }

        System.out.println();

        // ============================================================
        //   Блок 4: Інтерфейс AutoCloseable — свій клас
        // ============================================================
        System.out.println("=== Блок 4: Свій AutoCloseable ===");

        // Сценарій: таймер, що вимірює час виконання блоку коду
        try (ExecutionTimer timer = new ExecutionTimer("Обчислення")) {
            // Імітація роботи
            long sum = 0;
            for (int i = 0; i < 1_000_000; i++) {
                sum += i;
            }
            System.out.println("Результат: " + sum);
        }

        System.out.println();

        // ============================================================
        //   Блок 5: Порівняння — до і після try-with-resources
        // ============================================================
        System.out.println("=== Блок 5: Порівняння підходів ===");

        System.out.println("ДО Java 7 (try-catch-finally):");
        System.out.println("  Resource r = new Resource();");
        System.out.println("  try {");
        System.out.println("      r.use();");
        System.out.println("  } finally {");
        System.out.println("      r.close();");
        System.out.println("  }");
        System.out.println();
        System.out.println("ПІСЛЯ Java 7 (try-with-resources):");
        System.out.println("  try (Resource r = new Resource()) {");
        System.out.println("      r.use();");
        System.out.println("  }");
        System.out.println();
        System.out.println("Менше коду, менше помилок, автоматичне закриття!");
    }

    // --- Класи, що реалізують AutoCloseable ---

    /**
     * Імітація зʼєднання з базою даних.
     */
    static class DatabaseConnection implements AutoCloseable {
        private final String dbName;

        DatabaseConnection(String dbName) {
            this.dbName = dbName;
            System.out.println("[DB:" + dbName + "] Зʼєднання відкрито");
        }

        void query(String sql) {
            System.out.println("[DB:" + dbName + "] Виконую: " + sql);
        }

        @Override
        public void close() {
            System.out.println("[DB:" + dbName + "] Зʼєднання закрито (AutoCloseable)");
        }
    }

    /**
     * Імітація зчитувача даних.
     */
    static class DataReader implements AutoCloseable {
        private final String fileName;

        DataReader(String fileName) {
            this.fileName = fileName;
            System.out.println("[Reader:" + fileName + "] Відкрито для читання");
        }

        String readAll() {
            System.out.println("[Reader:" + fileName + "] Зчитую дані...");
            return "дані з " + fileName;
        }

        @Override
        public void close() {
            System.out.println("[Reader:" + fileName + "] Закрито");
        }
    }

    /**
     * Імітація записувача даних.
     */
    static class DataWriter implements AutoCloseable {
        private final String fileName;

        DataWriter(String fileName) {
            this.fileName = fileName;
            System.out.println("[Writer:" + fileName + "] Відкрито для запису");
        }

        void write(String data) {
            System.out.println("[Writer:" + fileName + "] Записую: " + data);
        }

        @Override
        public void close() {
            System.out.println("[Writer:" + fileName + "] Закрито");
        }
    }

    /**
     * Практичний AutoCloseable — таймер виконання.
     * Автоматично вимірює час від створення до close().
     */
    static class ExecutionTimer implements AutoCloseable {
        private final String taskName;
        private final long startTime;

        ExecutionTimer(String taskName) {
            this.taskName = taskName;
            this.startTime = System.nanoTime();
            System.out.println("[Timer:" + taskName + "] Старт");
        }

        @Override
        public void close() {
            long elapsed = System.nanoTime() - startTime;
            System.out.println("[Timer:" + taskName + "] Завершено за "
                    + (elapsed / 1_000_000.0) + " мс");
        }
    }
}
