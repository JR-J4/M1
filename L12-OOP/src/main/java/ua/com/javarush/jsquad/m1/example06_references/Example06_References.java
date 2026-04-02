package ua.com.javarush.jsquad.m1.example06_references;

import java.util.Scanner;

/**
 * Лекція 12: Типи даних. Ознайомлення з ООП
 * <hr>
 * <h3>Тема: Змінні-посилання (Reference variables)</h3>
 *
 * <p>Змінні-посилання — це змінні всіх типів, крім примітивних.
 * Такі змінні містять лише адресу об'єкта (посилання на об'єкт), а не сам об'єкт.</p>
 *
 * <h4>Примітив vs Посилання у пам'яті:</h4>
 * <pre>
 *         СТЕК (Stack)                  КУПА (Heap)
 *  ┌─────────────────────┐
 *  │ int price = 500     │
 *  │ ┌─────────────────┐ │
 *  │ │      500        │ │     Значення ПРЯМО у змінній
 *  │ └─────────────────┘ │
 *  │                     │
 *  │ Product laptop      │      ┌─────────────────────────┐
 *  │ ┌─────────────────┐ │      │  Об'єкт Product         │
 *  │ │    @A7F2 ───────┼─┼────▶ │  name = "Ноутбук"       │
 *  │ └─────────────────┘ │      │  price = 25000.0        │
 *  │                     │      │  quantity = 10          │
 *  │ Product same        │      └─────────────────────────┘
 *  │ ┌─────────────────┐ │               ▲
 *  │ │    @A7F2  ──────┼─┼───────────────┘
 *  │ └─────────────────┘ │      Обидві змінні вказують
 *  │                     │      на ОДИН об'єкт!
 *  │ Product pending     │
 *  │ ┌─────────────────┐ │
 *  │ │     null        │ │     Нікуди не вказує
 *  │ └─────────────────┘ │
 *  └─────────────────────┘
 * </pre>
 *
 * <h4>Порівняння == для посилань:</h4>
 * <pre>
 *  phone1 ──▶ { name="iPhone", price=45000 }    phone2 ──▶ { name="iPhone", price=45000 }
 *
 *  phone1 == phone2  →  false  (різні адреси, хоча вміст однаковий!)
 *
 *  phone3 = phone1
 *  phone1 == phone3  →  true   (та сама адреса!)
 * </pre>
 *
 * <p><b>Аналогія з життя:</b>
 * Примітив — це як записати ціну на етикетці товару (число прямо на етикетці).
 * Посилання — це як QR-код на етикетці: сам код не є товаром,
 * але він вказує, де знайти повну інформацію.</p>
 *
 * <p><b>Реальне застосування:</b>
 * Коли ми передаємо об'єкт товару в метод — передається не копія товару,
 * а посилання на нього. Зміни через посилання змінюють оригінальний об'єкт.</p>
 */
public class Example06_References {

    public static void main(String[] args) {

        // === 1. Примітиви vs Посилання — як зберігаються ===

        // Примітив: значення зберігається прямо у змінній
        int price = 500;
        int priceCopy = price;   // копія значення
        priceCopy = 999;         // зміна копії НЕ впливає на оригінал

        System.out.println("=== Примітиви — копіювання значення ===");
        System.out.println("price = " + price);           // 500 (не змінився!)
        System.out.println("priceCopy = " + priceCopy);   // 999

        System.out.println();

        // === 2. Посилання: дві змінні вказують на один об'єкт ===
        // Сценарій: два менеджери працюють з одним товаром

        Product laptop = new Product();
        laptop.name = "Ноутбук ASUS";
        laptop.price = 25_000.0;
        laptop.quantity = 10;
        laptop.available = true;

        Product sameProduct = laptop;  // НЕ копія! Обидві змінні вказують на ОДИН об'єкт!
        sameProduct.price = 23_000.0;  // знижка через іншу змінну

        System.out.println("=== Посилання — спільний об'єкт ===");
        System.out.println("laptop.price = " + laptop.price);         // 23000.0 — змінилася!
        System.out.println("sameProduct.price = " + sameProduct.price); // 23000.0
        System.out.println("Обидві змінні вказують на один і той самий об'єкт!");

        System.out.println();

        // === 3. null — відсутність посилання ===
        // Сценарій: товар ще не завантажений з бази

        Product pendingProduct = null;  // змінна оголошена, але не вказує на жодний об'єкт

        System.out.println("=== null — відсутність об'єкта ===");
        System.out.println("pendingProduct = " + pendingProduct);  // null
        // pendingProduct.name = "Тест";  // NullPointerException! Не можна працювати з null!
        System.out.println("Спроба звернутися до null призведе до NullPointerException!");

        System.out.println();

        // === 4. Порівняння посилань ===
        // Сценарій: чи це один і той самий товар?

        Product phone1 = new Product();
        phone1.name = "iPhone 15";
        phone1.price = 45_000.0;

        Product phone2 = new Product();
        phone2.name = "iPhone 15";
        phone2.price = 45_000.0;

        Product phone3 = phone1;  // те саме посилання

        System.out.println("=== Порівняння посилань (==) ===");
        System.out.println("phone1 == phone2: " + (phone1 == phone2)); // false — різні об'єкти!
        System.out.println("phone1 == phone3: " + (phone1 == phone3)); // true — одне посилання
        System.out.println("== порівнює адреси, а не вміст об'єктів!");

        System.out.println();

        // === 5. Різниця у пам'яті (схема) ===

        System.out.println("=== Як це виглядає в пам'яті ===");
        System.out.println();
        System.out.println("         СТЕК (Stack)                     КУПА (Heap)");
        System.out.println("  ┌─────────────────────────┐");
        System.out.println("  │ int price               │");
        System.out.println("  │ ┌─────────────────────┐ │");
        System.out.println("  │ │        500          │ │     Значення ПРЯМО у змінній");
        System.out.println("  │ └─────────────────────┘ │");
        System.out.println("  │                         │");
        System.out.println("  │ Product laptop          │      ┌───────────────────────┐");
        System.out.println("  │ ┌─────────────────────┐ │      │  Об'єкт Product       │");
        System.out.println("  │ │    @A7F2  ──────────┼─┼─────>│  name = \"Ноутбук\"   │");
        System.out.println("  │ └─────────────────────┘ │      │  price = 23000.0      │");
        System.out.println("  │                         │      └───────────────────────┘");
        System.out.println("  │ Product sameProduct     │               ^");
        System.out.println("  │ ┌─────────────────────┐ │               │");
        System.out.println("  │ │    @A7F2  ──────────┼─┼───────────────┘");
        System.out.println("  │ └─────────────────────┘ │    Обидві -> один об'єкт!");
        System.out.println("  │                         │");
        System.out.println("  │ Product pending         │");
        System.out.println("  │ ┌─────────────────────┐ │");
        System.out.println("  │ │       null          │ │     Нікуди не вказує");
        System.out.println("  │ └─────────────────────┘ │");
        System.out.println("  └─────────────────────────┘");
    }
}
