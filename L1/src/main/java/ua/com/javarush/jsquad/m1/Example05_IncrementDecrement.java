package ua.com.javarush.jsquad.m1;

/**
 * Лекція 1-2: Інкремент (++) та декремент (--)
 *
 * Інкремент (++) - збільшує змінну на 1. Як додати 1 яблуко в кошик.
 * Декремент (--) - зменшує змінну на 1. Як з'їсти 1 яблуко з кошика.
 *
 * Це просто скорочений запис:
 *   x++  це те саме що  x = x + 1
 *   x--  це те саме що  x = x - 1
 */
public class Example05_IncrementDecrement {

    public static void main(String[] args) {




        // === Інкремент ++ (збільшення на 1) ===
        System.out.println("=== Інкремент ++ ===");
        int apples = 3;
        System.out.println("Яблук у кошику: " + apples);

        apples++;  // додаємо 1 яблуко
        System.out.println("Додали одне: " + apples);    // 4

        apples++;  // ще одне
        System.out.println("Ще одне: " + apples);        // 5

        apples++;  // і ще одне
        System.out.println("І ще: " + apples);            // 6

        System.out.println();

        // === Декремент -- (зменшення на 1) ===
        System.out.println("=== Декремент -- ===");
        int lives = 3;
        System.out.println("Життів у грі: " + lives);

        lives--;  // втратили одне життя
        System.out.println("Програли раунд: " + lives);   // 2

        lives--;
        System.out.println("Ще раз програли: " + lives);  // 1

        lives--;
        System.out.println("Game over: " + lives);        // 0

        System.out.println();

        // === Реальний приклад: лічильник відвідувачів ===
        System.out.println("=== Лічильник відвідувачів кафе ===");
        int visitors = 0;
        System.out.println("Кафе відкрилось. Відвідувачів: " + visitors);

        visitors++;
        System.out.println("Зайшов клієнт. Відвідувачів: " + visitors);  // 1

        visitors++;
        visitors++;
        System.out.println("Зайшли ще двоє. Відвідувачів: " + visitors); // 3

        visitors--;

        System.out.println("Один пішов. Відвідувачів: " + visitors);     // 2
    }
}
