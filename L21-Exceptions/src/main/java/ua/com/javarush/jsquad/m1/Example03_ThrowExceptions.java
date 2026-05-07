package ua.com.javarush.jsquad.m1;

/**
 * Лекція 21: Винятки — Викидання винятків (throw)
 *
 * Ключове слово throw дозволяє вручну створити і викинути виняток.
 * Це потрібно, коли програма виявляє некоректну ситуацію і хоче про це
 * повідомити.
 *
 * Синтаксис:
 *   throw new ТипВинятку("повідомлення");
 *
 * Також у блоці catch можна повторно викинути (re-throw) перехоплений виняток,
 * щоб передати обробку вище.
 *
 * Аналогія з життя: охоронець у магазині. Він перевіряє вік покупця. Якщо вік
 * менше 18 — охоронець «викидає» покупця з магазину (throw). Не чекає, поки
 * покупець щось зламає — одразу реагує на порушення.
 *
 * Реальне застосування: валідація параметрів методу (вік, ціна, email),
 * перевірка бізнес-правил, захист від некоректних даних.
 */
public class Example03_ThrowExceptions {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Базове використання throw
        // ============================================================
        System.out.println("=== Блок 1: Ручне викидання винятку ===");

        // Сценарій: реєстрація користувача з перевіркою віку
        int[] ages = {25, 17, 30, -5, 18};

        for (int age : ages) {
            try {
                registerUser("Користувач", age);
                System.out.println("  Вік " + age + " → Реєстрація успішна!");
            }
            catch (IllegalArgumentException e) {
                System.out.println("  Вік " + age + " → Відмова: " + e.getMessage());

                StackTraceElement[] stackTrace = e.getStackTrace();

                for (StackTraceElement stackTraceElement : stackTrace) {
                    System.out.println("Class: " + stackTraceElement.getClassName());
                    System.out.println("Method: " + stackTraceElement.getMethodName());
                    System.out.println("Line: " + stackTraceElement.getLineNumber());
                }
            }
        }

        System.out.println();

        // ============================================================
        //   Блок 2: throw у методі валідації
        // ============================================================
        System.out.println("=== Блок 2: Валідація даних через throw ===");

        // Сценарій: створення товару з перевіркою ціни та назви
        String[][] products = {
                {"Ноутбук", "25000"},
                {"",        "1000"},
                {"Телефон", "-500"},
                {"Навушники", "0"},
                {"Миша",    "350"}
        };

        for (String[] product : products) {
            try {
                createProduct(product[0], Integer.parseInt(product[1]));
                System.out.println("  '" + product[0] + "' за " + product[1] + " грн → Створено!");
            }
            catch (IllegalArgumentException e) {
                System.out.println("  '" + product[0] + "' за " + product[1] + " грн → " + e.getMessage());
            }
        }

        System.out.println();

        // ============================================================
        //   Блок 3: Повторне викидання (re-throw)
        // ============================================================
        System.out.println("=== Блок 3: Re-throw — повторне викидання ===");

        // Сценарій: обробляємо частково, потім передаємо далі
        try {
            processOrder("ORD-001", -3);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Головний обробник зловив: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Re-throw — коли метод хоче залогувати помилку,");
        System.out.println("але не може повністю її обробити, тому передає вище.");
    }

    // --- Допоміжні методи ---

    /**
     * Реєстрація користувача з перевіркою віку.
     * Якщо вік < 18 або від'ємний — кидає виняток.
     */
    static void registerUser(String name, int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Вік не може бути від'ємним: " + age);
        }
        if (age < 18) {
            throw new IllegalArgumentException("Мінімальний вік — 18 років, а передано: " + age);
        }
        // Якщо все OK — реєструємо (тут просто імітація)
    }

    /**
     * Створення товару з валідацією.
     */
    static void createProduct(String name, int price) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Назва товару не може бути порожньою!");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Ціна має бути більше 0, а передано: " + price);
        }
    }

    /**
     * Обробка замовлення з re-throw.
     */
    static void processOrder(String orderId, int quantity) {
        try {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Кількість має бути > 0, а передано: " + quantity);
            }
            System.out.println("Замовлення " + orderId + " оброблено.");
        }
        catch (IllegalArgumentException e) {
            System.out.println("[LOG] Помилка в замовленні " + orderId + ": " + e.getMessage());
            // Перехопили, залогували, але повторно викидаємо
            throw e; // ← re-throw
        }
    }
}
