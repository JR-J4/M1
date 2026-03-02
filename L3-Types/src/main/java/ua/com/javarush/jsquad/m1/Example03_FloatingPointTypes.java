package ua.com.javarush.jsquad.m1;

/**
 * Лекція 3: Дійсні (дробові) типи — float і double
 *
 * Для чисел з дробовою частиною (3.14, 0.5, -2.7) Java має 2 типи:
 *
 *   float  — 4 байти — ~7 знаків після коми    (Floating Point Number)
 *   double — 8 байтів — ~15 знаків після коми   (Double Float — подвійна точність)
 *
 * За замовчуванням дробове число в Java — це double.
 * Щоб зробити float, треба додати f або F в кінці: 3.14f
 *
 * double точніший за float, тому його використовують частіше.
 */
public class Example03_FloatingPointTypes {

    public static void main(String[] args) {

        // === float: 4 байти, ~7 знаків точності ===
        // Завжди додаємо f в кінці числа!
        System.out.println("=== float (4 байти) ===");
        float pi = 3.14159f;
        float price = 29.99f;
        float temperatureF = 36.6f;

        System.out.println("Число пі: " + pi);
        System.out.println("Ціна: " + price + " грн");
        System.out.println("Температура тіла: " + temperatureF + "°C");
        System.out.println();

        // === double: 8 байтів, ~15 знаків точності ===
        // Не потрібно додавати нічого — double це тип за замовчуванням
        System.out.println("=== double (8 байтів) ===");
        double piPrecise = 3.141592653589793;
        double gravity = 9.80665;
        double salaryWithCoins = 45000.75;

        System.out.println("Число пі (точне): " + piPrecise);
        System.out.println("Прискорення вільного падіння: " + gravity + " м/с²");
        System.out.println("Зарплата: " + salaryWithCoins + " грн");
        System.out.println();

        // === Різниця в точності: float vs double ===
        System.out.println("=== Точність: float vs double ===");
        float floatPi = 3.141592653589793f;   // float обріже до ~7 знаків
        double doublePi = 3.141592653589793;   // double збереже ~15 знаків

        System.out.println("float pi:  " + floatPi);    // 3.1415927 (неточно!)
        System.out.println("double pi: " + doublePi);   // 3.141592653589793
        System.out.println();

        // === Ділення дійсних чисел (на відміну від int, зберігає дроби) ===
        System.out.println("=== Ділення дійсних чисел ===");

        // Пригадаємо: int / int = int (дробова частина зникає!)
        int intResult = 7 / 2;
        System.out.println("int:    7 / 2 = " + intResult);     // 3

        // double / double = double (дробова частина зберігається!)
        double doubleResult = 7.0 / 2.0;
        System.out.println("double: 7.0 / 2.0 = " + doubleResult); // 3.5

        // Якщо хоча б одне число double — результат буде double
        double mixedResult = 7.0 / 2;
        System.out.println("mixed:  7.0 / 2 = " + mixedResult);    // 3.5
        System.out.println();

        // === Практичний приклад: розрахунок середнього балу ===
        System.out.println("=== Середній бал ===");
        int math = 10;
        int physics = 8;
        int english = 11;

        // Увага: якщо ділити int на int — дріб зникне!
        int wrongAverage = (math + physics + english) / 3;
        System.out.println("Неправильно (int): " + wrongAverage);  // 9

        // Робимо хоча б одне число double — і все працює
        double correctAverage = (math + physics + english) / 3.0;
        System.out.println("Правильно (double): " + correctAverage); // 9.666...
    }
}
