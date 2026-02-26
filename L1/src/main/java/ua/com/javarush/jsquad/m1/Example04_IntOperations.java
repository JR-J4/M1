package ua.com.javarush.jsquad.m1;

/**
 * Лекція 1-2: Арифметичні операції з цілими числами (int)
 *
 * Java вміє рахувати як калькулятор:
 *   +  додавання
 *   -  віднімання
 *   *  множення
 *   /  ділення (тільки ціла частина! дробова відкидається)
 *   %  залишок від ділення (модуль)
 *
 * Важливо: int / int = завжди int (без дробової частини!)
 * Це як ділити пиріг порівну: 7 шматків на 2 людини = по 3 шматки, 1 залишається.
 */
public class Example04_IntOperations {

    public static void main(String[] args) {

        // === Базові операції ===
        System.out.println("=== Базові операції ===");
        int a = 10;
        int b = 3;

        System.out.println(a + " + " + b + " = " + (a + b));   // 13
        System.out.println(a + " - " + b + " = " + (a - b));   // 7
        System.out.println(a + " * " + b + " = " + (a * b));   // 30
        System.out.println(a + " / " + b + " = " + (a / b));   // 3 (не 3.33!)
        System.out.println(a + " % " + b + " = " + (a % b));   // 1 (залишок)

        System.out.println();

        // === Ділення цілих чисел - дробова частина ВІДКИДАЄТЬСЯ ===
        // Як розділити 5 цукерок на 2 дітей? Кожному по 2, одна залишилась.
        System.out.println("=== Ділення цілих чисел (дроби відкидаються!) ===");
        System.out.println("5 / 2 = " + (5 / 2));     // 2 (не 2.5!)
        System.out.println("7 / 3 = " + (7 / 3));     // 2 (не 2.33!)
        System.out.println("1 / 2 = " + (1 / 2));     // 0 (не 0.5!)
        System.out.println("20 / 3 = " + (20 / 3));   // 6 (не 6.66!)

        System.out.println();

        // === Залишок від ділення (%) - дуже корисна операція ===
        // Показує, скільки "залишилось" після ділення
        System.out.println("=== Залишок від ділення (%) ===");
        System.out.println("5 % 2 = " + (5 % 2));     // 1 (5 = 2*2 + 1)
        System.out.println("10 % 3 = " + (10 % 3));   // 1 (10 = 3*3 + 1)
        System.out.println("20 % 4 = " + (20 % 4));   // 0 (ділиться без залишку)
        System.out.println("7 % 2 = " + (7 % 2));     // 1 (непарне число!)

        // Лайфхак: якщо число % 2 == 0, то число парне!
        int number = 42;
        System.out.println(number + " % 2 = " + (number % 2) + " -> число парне!");
        number = 17;
        System.out.println(number + " % 2 = " + (number % 2) + " -> число непарне!");

        System.out.println();

        // === Вирази зі змінними (Java - це калькулятор!) ===
        System.out.println("=== Вирази ===");
        int price = 100;
        int quantity = 3;
        int total = price * quantity;
        System.out.println("Ціна: " + price + ", кількість: " + quantity + ", всього: " + total);

        // Дужки працюють як у математиці
        int result = (2 + 3) * 4;   // 20, а не 14
        System.out.println("(2 + 3) * 4 = " + result);
    }
}
