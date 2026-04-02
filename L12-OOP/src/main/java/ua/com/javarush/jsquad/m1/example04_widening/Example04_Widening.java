package ua.com.javarush.jsquad.m1.example04_widening;

/**
 * Лекція 12: Типи даних. Ознайомлення з ООП
 * <hr>
 * <h3>Тема: Розширення типів (Widening)</h3>
 *
 * <p>Змінній більшого розміру завжди можна присвоювати змінні меншого розміру.
 * Це відбувається автоматично, без втрати даних.</p>
 *
 * <h4>Схема дозволених перетворень (розширення):</h4>
 * <pre>
 *                     char
 *                      │
 *                      ▼
 *  byte ──▶ short ──▶ int ──▶ long
 *                      │
 *                      ▼
 *                    float ──▶ double
 *
 *  ──▶  суцільна лінія = без втрати інформації
 * </pre>
 *
 * <h4>Матрьошки (аналогія розширення):</h4>
 * <pre>
 *  ┌─────────────────────────────────┐
 *  │ long                            │
 *  │  ┌──────────────────────────┐   │
 *  │  │ int                      │   │
 *  │  │  ┌───────────────────┐   │   │
 *  │  │  │ short             │   │   │
 *  │  │  │  ┌────────────┐   │   │   │
 *  │  │  │  │ byte       │   │   │   │
 *  │  │  │  │   [5]      │   │   │   │
 *  │  │  │  └────────────┘   │   │   │
 *  │  │  └───────────────────┘   │   │
 *  │  └──────────────────────────┘   │
 *  └─────────────────────────────────┘
 *  Маленька матрьошка (byte) легко
 *  поміщається у більшу (long).
 * </pre>
 *
 * <p><b>Реальне застосування:</b>
 * У магазині: кількість товару (short) потрібно додати до загальної суми (int),
 * а потім помножити на ціну (double) — розширення відбувається автоматично.</p>
 */
public class Example04_Widening {

    public static void main(String[] args) {

        // === 1. Базове розширення — ланцюжок цілих типів ===
        // Сценарій: обчислення вартості замовлення в магазині

        byte itemCount = 5;              // кількість одного товару
        short totalItems = itemCount;    // загальна кількість товарів
        int warehouse = totalItems;      // кількість на складі
        long globalStock = warehouse;    // глобальний облік (великі числа)

        System.out.println("=== Розширення цілих типів ===");
        System.out.println("byte  itemCount = " + itemCount);
        System.out.println("short totalItems = " + totalItems);
        System.out.println("int   warehouse = " + warehouse);
        System.out.println("long  globalStock = " + globalStock);
        System.out.println("Жодних явних перетворень — компілятор робить це сам!");

        System.out.println();

        // === 2. Розширення у виразах ===
        // Сценарій: підрахунок суми замовлення

        byte apples = 3;
        short pricePerApple = 45;        // ціна за штуку (short)
        int totalCost = apples * pricePerApple;  // byte * short -> int (автоматично)

        System.out.println("=== Розширення у виразах ===");
        System.out.println("Яблук: " + apples + " шт.");
        System.out.println("Ціна за штуку: " + pricePerApple + " грн");
        System.out.println("Загальна вартість (byte * short -> int): " + totalCost + " грн");

        System.out.println();

        // === 3. Розширення з цілого в дробове ===
        // Сценарій: розрахунок ціни зі знижкою

        int originalPrice = 1500;          // ціна товару (int)
        double discountRate = 0.15;        // знижка 15% (double)
        double finalPrice = originalPrice * (1 - discountRate); // int -> double автоматично

        System.out.println("=== Ціле -> дробове ===");
        System.out.println("Початкова ціна (int): " + originalPrice + " грн");
        System.out.println("Знижка (double): " + (discountRate * 100) + "%");
        System.out.println("Кінцева ціна (double): " + finalPrice + " грн");

        System.out.println();

        // === 4. char -> int (розширення) ===
        // Сценарій: код категорії товару

        char category = 'A';
        int categoryNumber = category;  // char -> int автоматично (65)

        System.out.println("=== char -> int ===");
        System.out.println("Категорія (char): '" + category + "'");
        System.out.println("Код категорії (int): " + categoryNumber);

        System.out.println();

        // === 5. Ланцюжок обчислень у реальному сценарії ===

        byte boxes = 10;                       // кількість коробок
        short itemsPerBox = 250;               // товарів у коробці
        int totalUnits = boxes * itemsPerBox;  // всього одиниць
        long totalRevenue = (long) totalUnits * 299; // загальний дохід

        System.out.println("=== Повний ланцюжок (склад магазину) ===");
        System.out.println("Коробок: " + boxes);
        System.out.println("Товарів у коробці: " + itemsPerBox);
        System.out.println("Всього одиниць: " + totalUnits);
        System.out.println("Дохід (по 299 грн): " + totalRevenue + " грн");
    }
}
