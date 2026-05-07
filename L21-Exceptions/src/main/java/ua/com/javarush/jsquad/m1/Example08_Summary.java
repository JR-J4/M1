package ua.com.javarush.jsquad.m1;

/**
 * Лекція 21: Винятки — Підсумковий приклад (інтернет-магазин)
 *
 * Збираємо всі концепції з лекції в одному реалістичному сценарії:
 *   - try-catch для перехоплення
 *   - throw для валідації
 *   - throws для checked-винятків
 *   - finally для очищення
 *   - multi-catch для спільної обробки
 *   - ієрархія винятків (checked / unchecked)
 *
 * Сценарій: інтернет-магазин обробляє замовлення.
 * Перевіряє товар, кількість, знижку, оплату. На кожному етапі
 * можуть виникнути різні помилки.
 *
 * Аналогія з життя: касир у магазині. Перевіряє товар (чи є в наявності),
 * перевіряє оплату (чи достатньо грошей), пакує товар. Якщо щось не так —
 * зупиняє процес і повідомляє покупця. Але каса (ресурс) закривається ЗАВЖДИ.
 *
 * Реальне застосування: обробка запитів у веб-додатках, валідація
 * бізнес-логіки, обробка платежів, управління транзакціями.
 */
public class Example08_Summary {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Обробка кількох замовлень
        // ============================================================
        System.out.println("=== Інтернет-магазин: обробка замовлень ===");
        System.out.println();

        // Тестові замовлення: {товар, кількість, знижка%, сума оплати}
        String[][] orders = {
                {"Ноутбук",   "2",  "10", "45000"},  // OK
                {"",          "1",  "0",  "1000"},    // порожня назва
                {"Телефон",   "-1", "0",  "15000"},   // відʼємна кількість
                {"Навушники", "3",  "110","5000"},     // знижка > 100%
                {"Планшет",   "1",  "5",  "500"},     // недостатньо коштів
                {"Миша",      "5",  "0",  "2500"},    // OK
        };

        int successCount = 0;
        int failCount = 0;

        for (int i = 0; i < orders.length; i++) {
            String orderId = "ORD-" + (i + 1);
            System.out.println("--- Замовлення " + orderId + " ---");

            boolean sessionOpen = false;

            try {
                // Імітація відкриття сесії обробки
                sessionOpen = true;
                System.out.println("  [Сесія відкрита]");

                // 1. Валідація товару (може кинути unchecked)
                String product = orders[i][0];
                validateProduct(product);

                // 2. Валідація кількості (може кинути unchecked)
                int quantity = Integer.parseInt(orders[i][1]);
                validateQuantity(quantity);

                // 3. Валідація знижки (може кинути unchecked)
                int discount = Integer.parseInt(orders[i][2]);
                validateDiscount(discount);

                // 4. Обробка оплати (може кинути checked)
                int payment = Integer.parseInt(orders[i][3]);
                int price = calculatePrice(product, quantity, discount);
                processPayment(payment, price);

                System.out.println("  Замовлення оформлено: " + quantity + "x "
                        + product + " = " + price + " грн");
                successCount++;

            }
            catch (NullPointerException | IllegalArgumentException e) {
                // Multi-catch: помилки валідації (unchecked)
                System.out.println("  ПОМИЛКА валідації: " + e.getMessage());
                failCount++;
            }
            catch (Exception e) {
                // Checked-виняток від processPayment
                System.out.println("  ПОМИЛКА оплати: " + e.getMessage());
                failCount++;
            }
            finally {
                // Закриваємо сесію ЗАВЖДИ
                if (sessionOpen) {
                    System.out.println("  [Сесія закрита]");
                }
            }

            System.out.println();
        }

        // ============================================================
        //   Блок 2: Підсумок
        // ============================================================
        System.out.println("=== Підсумок ===");
        System.out.println("Успішних замовлень: " + successCount);
        System.out.println("Невдалих замовлень: " + failCount);
        System.out.println();

        System.out.println("=== Що ми використали ===");
        System.out.println("1. try-catch     — перехоплення помилок при обробці замовлення");
        System.out.println("2. throw         — ручне викидання при невалідних даних");
        System.out.println("3. throws        — оголошення checked-винятку у processPayment");
        System.out.println("4. finally       — закриття сесії незалежно від результату");
        System.out.println("5. multi-catch   — спільна обробка NPE і IllegalArgumentException");
        System.out.println("6. ієрархія      — unchecked для валідації, checked для оплати");
    }

    // --- Методи валідації (кидають unchecked-винятки) ---

    static void validateProduct(String product) {
        if (product == null || product.isEmpty()) {
            throw new IllegalArgumentException("Назва товару не може бути порожньою");
        }
    }

    static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість має бути > 0, передано: " + quantity);
        }
    }

    static void validateDiscount(int discount) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Знижка має бути 0-100%, передано: " + discount + "%");
        }
    }

    // --- Бізнес-логіка ---

    static int calculatePrice(String product, int quantity, int discount) {
        // Імітація цін
        int unitPrice;
        switch (product) {
            case "Ноутбук":   unitPrice = 25000; break;
            case "Телефон":   unitPrice = 15000; break;
            case "Навушники": unitPrice = 2000;  break;
            case "Планшет":   unitPrice = 12000; break;
            case "Миша":      unitPrice = 500;   break;
            default:          unitPrice = 1000;
        }

        int total = unitPrice * quantity;
        int discountAmount = total * discount / 100;
        return total - discountAmount;
    }

    /**
     * Обробка оплати — checked-виняток (throws Exception).
     * Будь-хто, хто викликає цей метод, ЗОБОВ'ЯЗАНИЙ обробити виняток.
     */
    static void processPayment(int payment, int price) throws Exception {
        if (payment < price) {
            throw new Exception("Недостатньо коштів: потрібно "
                    + price + " грн, а є " + payment + " грн");
        }
        int change = payment - price;
        if (change > 0) {
            System.out.println("  Решта: " + change + " грн");
        }
    }
}
