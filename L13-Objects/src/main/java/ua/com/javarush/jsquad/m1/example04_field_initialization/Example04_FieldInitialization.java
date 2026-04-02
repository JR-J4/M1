package ua.com.javarush.jsquad.m1.example04_field_initialization;

/**
 * Лекція 13: Об'єкти
 * <hr>
 * <h3>Тема: Ініціалізація змінних класу</h3>
 *
 * <p>Змінні класу ініціалізуються у чіткому порядку:</p>
 * <ol>
 *   <li>Створюється об'єкт типу Клас</li>
 *   <li>Ініціалізуються всі поля класу своїми стартовими значеннями (у порядку оголошення)</li>
 *   <li>Викликається конструктор і виконується його код</li>
 * </ol>
 *
 * <h4>Порядок ініціалізації:</h4>
 * <pre>
 *   new Order("Laptop", 3)
 *        │
 *        ▼
 *   1. Створюється об'єкт Order у пам'яті
 *        │
 *        ▼
 *   2. Ініціалізуються поля (у порядку оголошення):
 *        product = null  →  (чекає на конструктор)
 *        quantity = 0    →  (чекає на конструктор)
 *        pricePerUnit = 999.99  ← стартове значення!
 *        discount = 0.05        ← стартове значення!
 *        totalPrice = pricePerUnit * quantity * (1 - discount)
 *        │
 *        ▼
 *   3. Виконується код конструктора
 * </pre>
 *
 * <h4>Значення за замовчуванням для полів:</h4>
 * <pre>
 *   int, long, short, byte → 0
 *   double, float          → 0.0
 *   boolean                → false
 *   char                   → '\0' (нульовий символ)
 *   String (та всі об'єкти)→ null
 * </pre>
 *
 * <p><b>Аналогія з життя:</b>
 * Це як конвеєр на заводі: спочатку створюється заготовка,
 * потім встановлюються стандартні деталі (значення за замовчуванням),
 * і лише потім майстер (конструктор) робить фінальне налаштування.</p>
 *
 * <p><b>Реальне застосування:</b>
 * Знання порядку ініціалізації запобігає багам, коли одне поле
 * залежить від іншого і може отримати неочікуване значення.</p>
 */
public class Example04_FieldInitialization {

    public static void main(String[] args) {

        // === 1. Значення за замовчуванням для полів класу ===
        // Сценарій: створюємо об'єкт без ініціалізації — бачимо дефолти

        System.out.println("=== Значення за замовчуванням ===");

        DefaultValues defaults = new DefaultValues();
//        defaults.showAll();

        System.out.println();

        // === 2. Ініціалізація полів стартовими значеннями ===
        // Сценарій: інтернет-магазин — замовлення з попередньо заданими полями

        System.out.println("=== Ініціалізація полів стартовими значеннями ===");

        Order order = new Order("Ноутбук", 2);
        order.showInfo();

        System.out.println();

        // === 3. Порядок ініціалізації: поля → конструктор ===
        // Сценарій: демонстрація що поля ініціалізуються ДО конструктора

        System.out.println("=== Порядок: поля → конструктор ===");
        System.out.println();
        System.out.println("  new Order(\"Ноутбук\", 2):");
        System.out.println("  ┌──────────────────────────────────────────┐");
        System.out.println("  │ 1. Створюється об'єкт Order             │");
        System.out.println("  │                                          │");
        System.out.println("  │ 2. Поля ініціалізуються (у порядку):     │");
        System.out.println("  │    product = null                        │");
        System.out.println("  │    quantity = 0                          │");
        System.out.println("  │    pricePerUnit = 999.99  ← стартове    │");
        System.out.println("  │    discount = 0.05        ← стартове    │");
        System.out.println("  │                                          │");
        System.out.println("  │ 3. Конструктор:                          │");
        System.out.println("  │    product = \"Ноутбук\"                   │");
        System.out.println("  │    quantity = 2                          │");
        System.out.println("  └──────────────────────────────────────────┘");

        System.out.println();

        // === 4. Ініціалізація поля через інше поле ===
        // Поля ініціалізуються в порядку оголошення — це важливо!

        System.out.println("=== Ініціалізація поля через інше поле ===");

        Payment payment = new Payment();
        payment.showInfo();

        System.out.println();
        System.out.println("totalPrice обчислюється під час ініціалізації полів,");
        System.out.println("тому що price і tax вже ініціалізовані (вони оголошені раніше).");

        System.out.println();

        // === 5. Конструктор базового класу ===
        // Змінні базового класу ініціалізуються до класу-спадкоємця

        System.out.println("=== Конструктор базового класу ===");
        System.out.println();
        System.out.println("  Порядок для класу-спадкоємця:");
        System.out.println("  1. Ініціалізація полів базового класу");
        System.out.println("  2. Конструктор базового класу");
        System.out.println("  3. Ініціалізація полів класу-спадкоємця");
        System.out.println("  4. Конструктор класу-спадкоємця");
        System.out.println();
        System.out.println("  Вся ініціалізація батька відбувається ДО ініціалізації нащадка!");
    }
}
