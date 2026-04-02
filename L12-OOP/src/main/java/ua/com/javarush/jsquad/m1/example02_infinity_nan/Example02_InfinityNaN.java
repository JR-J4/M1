package ua.com.javarush.jsquad.m1.example02_infinity_nan;

/**
 * Лекція 12: Типи даних. Ознайомлення з ООП
 * ==========================================
 * Тема: Нескінченність (Infinity) та NaN (Not a Number)
 *
 * Числа з рухомою точкою (float, double) мають цікаву особливість:
 * вони дозволяють зберігати спеціальні значення — нескінченність та NaN.
 *
 * Аналогія з життя:
 * Уявіть, що в інтернет-магазині потрібно порахувати середню ціну товарів
 * у категорії. Якщо в категорії 0 товарів — ділення на нуль!
 * Для цілих чисел це крах програми, а для double — спеціальне значення.
 *
 * Реальне застосування:
 * Обробка крайніх випадків у розрахунках (ділення на нуль, невалідні дані).
 */
public class Example02_InfinityNaN {

    public static void main(String[] args) {

        // === 1. Позитивна та негативна нескінченність ===
        // Сценарій: розрахунок середньої ціни при порожній категорії

        double totalPrice = 5000.0;
        double numberOfProducts = 0.0;

        double averagePrice = totalPrice / numberOfProducts;
        System.out.println("=== Нескінченність (Infinity) ===");
        System.out.println("Сума цін: " + totalPrice);
        System.out.println("Кількість товарів: " + numberOfProducts);
        System.out.println("Середня ціна (5000.0 / 0.0): " + averagePrice); // Infinity

        double negativeAverage = -totalPrice / numberOfProducts;
        System.out.println("Від'ємна середня (-5000.0 / 0.0): " + negativeAverage); // -Infinity

        System.out.println();

        // === 2. Операції з нескінченністю ===
        // Будь-які арифметичні дії з Infinity дають Infinity

        double a = 1d / 0d;        // Infinity
        double b = a * 10;         // Infinity
        double c = b - 100;        // Infinity
        double d = a + 1_000_000;  // Infinity

        System.out.println("=== Операції з Infinity ===");
        System.out.println("1d / 0d = " + a);
        System.out.println("Infinity * 10 = " + b);
        System.out.println("Infinity - 100 = " + c);
        System.out.println("Infinity + 1000000 = " + d);

        System.out.println();

        // === 3. NaN — Not a Number (не число) ===
        // Сценарій: коли результат математично невизначений

        double zeroByZero = 0.0 / 0.0;  // NaN — невизначеність
        System.out.println("=== NaN (Not a Number) ===");
        System.out.println("0.0 / 0.0 = " + zeroByZero);

        // Infinity / Infinity = NaN (нескінченність на нескінченність)
        double infinity = 1d / 0d;
        double infByInf = infinity / infinity;
        System.out.println("Infinity / Infinity = " + infByInf);

        System.out.println();

        // === 4. Будь-яка операція з NaN дає NaN ===

        double nanValue = 0.0 / 0.0;
        double nanPlus = nanValue * 10;
        double nanMinus = nanValue - 100;
        double nanWithInf = nanValue + infinity;

        System.out.println("=== Операції з NaN ===");
        System.out.println("NaN * 10 = " + nanPlus);
        System.out.println("NaN - 100 = " + nanMinus);
        System.out.println("NaN + Infinity = " + nanWithInf);

        System.out.println();

        // === 5. Перевірка на NaN та Infinity ===
        // Важливо: NaN != NaN (навіть сам собі не дорівнює!)

        System.out.println("=== Перевірка спеціальних значень ===");
        System.out.println("NaN == NaN: " + (nanValue == nanValue));            // false!
        System.out.println("Double.isNaN(NaN): " + Double.isNaN(nanValue));     // true
        System.out.println("Double.isInfinite(Inf): " + Double.isInfinite(a));  // true

        // Порада: для цілих типів (int, long) ділення на 0 — це помилка ArithmeticException!
        // Тільки float та double дають Infinity/NaN
    }
}
