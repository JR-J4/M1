package ua.com.javarush.jsquad.m1;

import java.util.Scanner;

/**
 * Лекція 3: Клас Scanner — введення даних з клавіатури
 *
 * System.out — для ВИВЕДЕННЯ даних на екран (друкуємо)
 * System.in  — для ВВЕДЕННЯ даних з клавіатури (читаємо)
 *
 * System.in читає дані по одному символу — це незручно.
 * Тому використовують клас Scanner (java.util.Scanner),
 * який вміє зчитувати цілі рядки та числа.
 *
 * Створення Scanner:
 *   Scanner console = new Scanner(System.in);
 *
 * Основні методи:
 *   console.nextLine()   — зчитати рядок тексту
 *   console.nextInt()    — зчитати ціле число
 *   console.nextDouble() — зчитати дробове число
 */
public class Example07_ScannerBasics {

    public static void main(String[] args) {

        // Крок 1: Створюємо об'єкт Scanner для читання з клавіатури
        // Scanner — це як "мікрофон", який слухає що друкує користувач
        Scanner console = new Scanner(System.in);

        // === Зчитуємо ім'я (рядок тексту) ===
        System.out.print("Як тебе звати? ");
        String name = console.nextLine();  // чекає поки користувач натисне Enter

        System.out.println("Привіт, " + name + "!");
        System.out.println();

        // === Зчитуємо вік (ціле число) ===
        System.out.print("Скільки тобі років? ");
        int age = console.nextInt();  // зчитує ціле число

        System.out.println("Тобі " + age + " років.");
        System.out.println("Через 5 років тобі буде " + (age + 5) + "!");
        System.out.println();

        // === Зчитуємо зріст (дробове число) ===
        System.out.print("Який у тебе зріст (у метрах, наприклад 1.75)? ");
        double height = console.nextDouble();  // зчитує дробове число

        System.out.println("Твій зріст: " + height + " м");
        System.out.println();

        // === Підсумок ===
        System.out.println("=== Твоя картка ===");
        System.out.println("Ім'я: " + name);
        System.out.println("Вік: " + age);
        System.out.println("Зріст: " + height + " м");

        // Закриваємо Scanner (гарна практика)
        console.close();
    }
}
