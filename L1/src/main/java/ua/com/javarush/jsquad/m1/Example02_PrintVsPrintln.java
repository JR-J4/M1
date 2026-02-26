package ua.com.javarush.jsquad.m1;

/**
 * Лекція 1-2: Різниця між println() і print()
 *
 * println() - виводить текст і переходить на НОВИЙ рядок (як натиснути Enter)
 * print()   - виводить текст і залишається на ТОМУ Ж рядку
 *
 * Аналогія: println - це як писати кожне слово на окремому рядку зошита,
 *           print   - це як писати всі слова на одному рядку підряд.
 *
 *
 *
 *           TEXT
 *
 *
 */
public class Example02_PrintVsPrintln {

    public static void main(String[] args) {

        // --- Приклад 1: println - кожне слово на новому рядку ---
        System.out.println("=== println (новий рядок) ===");
        System.out.println("Яблуко");
        System.out.println("Банан");
        System.out.println("Вишня");
        // Результат:
        // Яблуко
        // Банан
        // Вишня

        System.out.println(); // просто порожній рядок (як Enter)

        // --- Приклад 2: print - все на одному рядку ---
        System.out.println("=== print (той самий рядок) ===");
        System.out.print("Яблуко");
        System.out.print("Банан");
        System.out.print("Вишня");
        System.out.println(); // перехід на новий рядок
        // Результат: ЯблукоБананВишня

        System.out.println(); // порожній рядок

        // --- Приклад 3: комбінуємо print і println ---
        System.out.println("=== комбінуємо ===");
        System.out.print("Я ");
        System.out.print("вивчаю ");
        System.out.println("Java!"); // println в кінці - перехід на новий рядок
        System.out.println("І мені подобається!");





        // Результат:
        // Я вивчаю Java!
        // І мені подобається!
    }
}
