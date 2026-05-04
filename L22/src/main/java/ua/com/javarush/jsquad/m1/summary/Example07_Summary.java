package ua.com.javarush.jsquad.m1.summary;

import java.util.Stack;

/**
 * Лекція 22: Винятки 2 — Підсумок: система логування помилок
 *
 * Цей приклад обʼєднує всі теми лекції в одному практичному сценарії:
 * - Власні винятки (custom exceptions)
 * - Stack trace та StackTraceElement
 * - Стек (Stack) для зберігання історії помилок
 * - printStackTrace() та getStackTrace()
 * - try-with-resources для автоматичного закриття ресурсів
 *
 * Сценарій: інтернет-магазин з системою логування помилок.
 * Кожна помилка зберігається у стеку (щоб остання помилка була зверху).
 * Логер реалізує AutoCloseable — після завершення роботи він зберігає
 * звіт і автоматично закривається.
 */
public class Example07_Summary {

    public static void main(String[] args) {

        // Використовуємо try-with-resources для логера
        try (ErrorLogger logger = new ErrorLogger()) {

            // === Операція 1: Успішне замовлення ===
            System.out.println("--- Замовлення #1 ---");
            try {
                placeOrder("ORD-001", "Ноутбук", 1, 25000);
                System.out.println("Замовлення #1 — успішно!");
            } catch (OrderException e) {
                logger.log(e);
            }

            System.out.println();

            // === Операція 2: Замовлення з невалідною кількістю ===
            System.out.println("--- Замовлення #2 ---");
            try {
                placeOrder("ORD-002", "Телефон", -1, 15000);
            } catch (OrderException e) {
                logger.log(e);
                System.out.println("Замовлення #2 — помилка: " + e.getMessage());
            }

            System.out.println();

            // === Операція 3: Замовлення товару, якого немає в наявності ===
            System.out.println("--- Замовлення #3 ---");
            try {
                placeOrder("ORD-003", "PlayStation 6", 1, 30000);
            } catch (OrderException e) {
                logger.log(e);
                System.out.println("Замовлення #3 — помилка: " + e.getMessage());
            }

            System.out.println();

            // === Операція 4: Перевищено ліміт суми ===
            System.out.println("--- Замовлення #4 ---");
            try {
                placeOrder("ORD-004", "Ноутбук", 100, 25000);
            } catch (OrderException e) {
                logger.log(e);
                System.out.println("Замовлення #4 — помилка: " + e.getMessage());
            }

            System.out.println();

            // === Виведення звіту помилок ===
            logger.printReport();

        } // logger.close() — автоматично!

        System.out.println();
        System.out.println("=== Кінець програми ===");
    }

    // --- Бізнес-логіка ---

    static void placeOrder(String orderId, String product, int quantity, double price)
            throws OrderException {
        validateOrder(orderId, product, quantity, price);
        checkInventory(product, quantity);
        checkOrderLimit(quantity, price);
        System.out.println("Замовлення " + orderId + ": " + product + " x" + quantity + " — оформлено!");
    }

    static void validateOrder(String orderId, String product, int quantity, double price)
            throws OrderException {
        if (quantity <= 0) {
            throw new OrderException(orderId, "Кількість має бути > 0, отримано: " + quantity);
        }
        if (price <= 0) {
            throw new OrderException(orderId, "Ціна має бути > 0, отримано: " + price);
        }
    }

    static void checkInventory(String product, int quantity) throws OrderException {
        // Імітація: деякі товари «відсутні»
        if (product.contains("PlayStation")) {
            throw new OrderException("???", "Товар '" + product + "' відсутній на складі!");
        }
    }

    static void checkOrderLimit(int quantity, double price) throws OrderException {
        double total = quantity * price;
        if (total > 1_000_000) {
            throw new OrderException("???",
                    "Сума замовлення " + total + " грн перевищує ліміт 1 000 000 грн!");
        }
    }

}
