package ua.com.javarush.jsquad.m1;

/**
 * Лекція 22: Винятки 2 — Виведення стек-трейсу під час обробки помилок
 *
 * Коли у програмі виникає виняток, Java записує в нього поточний stack trace.
 * Цей стек-трейс зберігається всередині обʼєкта-винятку і може бути вилучений:
 *   - exception.printStackTrace()  — виводить у консоль повну інформацію
 *   - exception.getStackTrace()    — повертає масив StackTraceElement[]
 *
 * Метод printStackTrace() належить класу Throwable і доступний у будь-якого
 * винятку. Він виводить: тип винятку, повідомлення, та повний ланцюжок викликів.
 *
 * Аналогія з життя: після ДТП складається протокол, де описано ланцюжок подій:
 * хто їхав, звідки повернув, де зіткнулися. printStackTrace() — це такий «протокол»
 * для помилки в програмі.
 *
 * Реальне застосування: логування помилок на сервері, відправка звітів
 * про помилки (crash reports), дебагінг у розробці.
 */
public class Example04_PrintStackTrace {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Метод printStackTrace()
        // ============================================================
        System.out.println("=== Блок 1: printStackTrace() ===");

        // Сценарій: помилка при обробці платежу — виводимо повну інформацію
        try {
            processPayment("PAY-001", -100);
        } catch (Exception e) {
            System.out.println("Перехопили помилку. Повний стек-трейс:");
            e.printStackTrace(); // виводить у System.err
            System.out.println("(стек-трейс виведено вище)");
        }

        System.out.println();

        // ============================================================
        //   Блок 2: getStackTrace() — програмний аналіз стеку
        // ============================================================
        System.out.println("=== Блок 2: getStackTrace() для аналізу ===");

        // Сценарій: логер збирає інформацію про помилку для відправки на сервер
        try {
            orderService();
        } catch (Exception e) {
            System.out.println("Виняток: " + e.getMessage());
            System.out.println();

            StackTraceElement[] trace = e.getStackTrace();
            System.out.println("Аналіз стек-трейсу (" + trace.length + " елементів):");

            for (int i = 0; i < trace.length; i++) {
                StackTraceElement el = trace[i];
                String marker = (i == 0) ? "ПОМИЛКА ТУТ →" : "               ";
                System.out.println("  " + marker + " " + el.getMethodName()
                        + "() [" + el.getFileName() + ":" + el.getLineNumber() + "]");
            }
        }

        System.out.println();

        // ============================================================
        //   Блок 3: getMessage() vs toString() vs printStackTrace()
        // ============================================================
        System.out.println("=== Блок 3: Три способи отримати інформацію ===");

        try {
            String text = null;
            text.length();
        } catch (NullPointerException e) {
            System.out.println("getMessage():      " + e.getMessage());
            System.out.println("toString():        " + e.toString());
            System.out.println("printStackTrace(): (виводить повний стек у System.err)");
        }

        System.out.println();
        System.out.println("getMessage()      — тільки повідомлення");
        System.out.println("toString()        — тип + повідомлення");
        System.out.println("printStackTrace() — тип + повідомлення + весь ланцюжок викликів");
    }

    // --- Ланцюжок методів для демонстрації ---

    static void processPayment(String paymentId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сума платежу має бути > 0: " + amount);
        }
    }

    static void orderService() {
        inventoryCheck();
    }

    static void inventoryCheck() {
        databaseQuery();
    }

    static void databaseQuery() {
        throw new RuntimeException("Зʼєднання з базою даних втрачено!");
    }
}
