package ua.com.javarush.jsquad.m1.example03_static_methods;

import ua.com.javarush.jsquad.m1.example04_static_vs_regular.BankAccount;

/**
 * Лекція 14: Класи та static
 * <hr>
 * <h3>Тема: Статичні методи</h3>
 *
 * <p>Статичні методи відрізняються від звичайних тим, що вони прив'язані
 * до <b>класу</b>, а не до об'єкта.</p>
 *
 * <p><b>Синтаксис:</b></p>
 * <pre>
 *   public static тип ім'яМетоду(параметри) {
 *       // тіло методу
 *   }
 * </pre>
 *
 * <p><b>Важлива властивість:</b> статичний метод може звертатися лише
 * до статичних змінних та методів. Він НЕ може використовувати this
 * і нестатичні поля/методи класу.</p>
 *
 * <p><b>Аналогія з життя:</b>
 * Статичний метод — як калькулятор на стіні в магазині.
 * Не потрібно "створювати" калькулятор — він вже є, просто підходиш
 * і рахуєш. Так само MathHelper.max(5, 10) — не потрібен об'єкт.</p>
 *
 * <p><b>Реальне застосування:</b>
 * Утилітні класи (Math, Collections, Arrays), фабричні методи,
 * допоміжні функції для перетворення даних.</p>
 */
public class Example03_StaticMethods {

    public static void main(String[] args) {

        // === 1. Оголошення та виклик статичних методів ===
        // Сценарій: утилітний клас для математичних обчислень


        System.out.println("=== Виклик статичних методів ===");

        // Викликаємо через ім'я класу — об'єкт НЕ потрібен!
        int maximum = MathHelper.max(25, 42);
        int minimum = MathHelper.min(25, 42);

        System.out.println("max(25, 42) = " + maximum);   // 42
        System.out.println("min(25, 42) = " + minimum);   // 25

        System.out.println();

        // === 2. Виклик через Ім'яКласу.метод() ===
        // Не потрібно робити: MathHelper helper = new MathHelper();

        System.out.println("=== Ім'яКласу.метод() — без створення об'єкта ===");

        double roomArea = MathHelper.rectangleArea(5.5, 3.2);
        System.out.println("Площа кімнати 5.5 x 3.2 = " + roomArea + " м²");

        double pizzaArea = MathHelper.circleArea(15);
        System.out.println("Площа піци r=15 см = " + String.format("%.1f", pizzaArea) + " см²");

        System.out.println("Число 7 парне? " + MathHelper.isEven(7));   // false
        System.out.println("Число 10 парне? " + MathHelper.isEven(10)); // true

        System.out.println();

        // === 3. Обмеження статичних методів ===
        // Статичний метод НЕ може звертатися до нестатичних полів/методів

        System.out.println("=== Обмеження статичних методів ===");
        System.out.println();
        System.out.println("  Статичний метод МОЖЕ:");
        System.out.println("    - викликати інші статичні методи");
        System.out.println("    - звертатися до статичних змінних");
        System.out.println("    - створювати об'єкти та працювати з ними");
        System.out.println();
        System.out.println("  Статичний метод НЕ МОЖЕ:");
        System.out.println("    - звертатися до нестатичних змінних класу");
        System.out.println("    - викликати нестатичні методи класу");
        System.out.println("    - використовувати ключове слово this");

        System.out.println();

        // Приклад: статичний метод викликає інші статичні методи
        System.out.println("=== Статичний метод викликає статичний метод ===");
        int clamped = MathHelper.clamp(150, 0, 100);
        System.out.println("clamp(150, 0, 100) = " + clamped);  // 100

        clamped = MathHelper.clamp(-20, 0, 100);
        System.out.println("clamp(-20, 0, 100) = " + clamped);  // 0

        clamped = MathHelper.clamp(50, 0, 100);
        System.out.println("clamp(50, 0, 100) = " + clamped);   // 50
    }
}
