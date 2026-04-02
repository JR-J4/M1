package ua.com.javarush.jsquad.m1.example02_constructors;

/**
 * Лекція 13: Об'єкти
 * <hr>
 * <h3>Тема: Конструктори — створення об'єктів</h3>
 *
 * <p>Конструктор — спеціальний метод, який відповідає за ініціалізацію
 * об'єкта під час його створення. Його ім'я збігається з ім'ям класу,
 * і він не має типу повернення.</p>
 *
 * <p><b>Синтаксис:</b></p>
 * <pre>
 *   модифікатори Клас(параметри) {
 *       // код ініціалізації
 *   }
 * </pre>
 *
 * <h4>Що відбувається при виклику new:</h4>
 * <pre>
 *   Smartphone phone = new Smartphone("Apple", "iPhone", 128);
 *                        │
 *                        ▼
 *   1. Java-машина створює об'єкт типу Smartphone
 *   2. Java-машина викликає конструктор
 *   3. Конструктор ініціалізує поля: brand="Apple", model="iPhone", storage=128
 *   4. Посилання на об'єкт зберігається у змінній phone
 * </pre>
 *
 * <p><b>Аналогія з життя:</b>
 * Конструктор — як процес збирання смартфона на заводі.
 * Ви вказуєте параметри (бренд, модель, пам'ять), і з конвеєра
 * виходить готовий телефон із заданими характеристиками.</p>
 *
 * <p><b>Реальне застосування:</b>
 * Конструктори гарантують, що об'єкт створюється у коректному стані.
 * Наприклад, телефон завжди має бренд і модель — не буває «порожнього» телефону.</p>
 */
public class Example02_Constructors {

    public static void main(String[] args) {

        // === 1. Створення об'єкта через конструктор ===
        // Сценарій: магазин електроніки — створюємо телефони

        System.out.println("=== Створення об'єктів через конструктор ===");

        // При виклику new — автоматично викликається конструктор
        Smartphone iphone = new Smartphone("Apple", "iPhone 15", 256);
        Smartphone samsung = new Smartphone("Samsung", "Galaxy S24", 128);

        System.out.println();

        // === 2. Об'єкти одразу готові до використання ===
        // Конструктор гарантує, що всі поля ініціалізовані

        System.out.println("=== Об'єкти готові до використання ===");
        iphone.showInfo();   // brand, model, storage вже заповнені!
        samsung.showInfo();

        System.out.println();

        // === 3. Конструктор виконується один раз — при створенні ===
        // Після створення об'єкт живе своїм життям

        System.out.println("=== Використання об'єктів після створення ===");
        iphone.use(5);    // використовували 5 годин
        iphone.use(3);    // ще 3 години
        iphone.showInfo();

        samsung.use(8);   // Samsung використовували довше
        samsung.showInfo();

        System.out.println();

        // === 4. Ключове слово this у конструкторі ===
        // this — посилання на поточний об'єкт

        System.out.println("=== Ключове слово this ===");
        System.out.println();
        System.out.println("  public Smartphone(String brand, ...) {");
        System.out.println("      this.brand = brand;");
        System.out.println("      ─────┬─────   ──┬──");
        System.out.println("           │           │");
        System.out.println("     поле об'єкта   параметр конструктора");
        System.out.println("  }");
        System.out.println();
        System.out.println("this потрібен, коли ім'я параметра збігається з ім'ям поля.");

        System.out.println();

        // === 5. Зарядка та повторне використання ===

        System.out.println("=== Зарядка телефону ===");
        iphone.charge();
        iphone.showInfo();
    }
}
