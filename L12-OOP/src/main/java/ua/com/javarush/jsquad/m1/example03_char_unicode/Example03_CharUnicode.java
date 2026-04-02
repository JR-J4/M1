package ua.com.javarush.jsquad.m1.example03_char_unicode;

/**
 * Лекція 12: Типи даних. Ознайомлення з ООП
 * ==========================================
 * Тема: Тип char та Unicode
 *
 * Значення char зберігаються у пам'яті як числа в діапазоні від 0 до 65535,
 * що позначають Юнікод-символи. Кожен символ має свій номер.
 *
 * Синтаксис:
 *   char a = 'A';       // літеральний символ
 *   char a = 65;        // десятковий код
 *   char a = 0x41;      // шістнадцятковий код
 *   char a = '\u0041';  // Unicode-послідовність
 *
 * Аналогія з життя:
 * Уявіть каталог товарів, де кожен товар маркується літерою категорії:
 * 'E' — електроніка, 'F' — їжа, 'C' — одяг. Ці мітки — це char.
 *
 * Реальне застосування:
 * Робота з окремими символами рядка, валідація введення, кодування.
 */
public class Example03_CharUnicode {

    public static void main(String[] args) {

        // === 1. Різні способи задати char ===
        // Сценарій: маркування товарів у магазині

        char categoryLetter = 'E';       // літера — символ в одинарних лапках
        char categoryCode = 69;          // десятковий код літери 'E'
        char categoryHex = 0x45;         // шістнадцятковий код літери 'E'
        char categoryUnicode = '\u0045'; // Unicode-послідовність літери 'E'

        System.out.println("=== Способи задати char ===");
        System.out.println("Літера: " + categoryLetter);
        System.out.println("Десятковий код 69: " + categoryCode);
        System.out.println("Hex 0x45: " + categoryHex);
        System.out.println("Unicode \\u0045: " + categoryUnicode);
        System.out.println("Усі чотири способи дають: " +
                (categoryLetter == categoryCode && categoryCode == categoryHex
                        && categoryHex == categoryUnicode));

        System.out.println();

        // === 2. char — це число! Арифметика з символами ===

        char letterA = 'A';  // код 65
        char letterZ = 'Z';  // код 90

        System.out.println("=== char як число ===");
        System.out.println("'A' = " + (int) letterA);   // 65
        System.out.println("'Z' = " + (int) letterZ);   // 90
        System.out.println("Кількість великих латинських літер: " + (letterZ - letterA + 1)); // 26

        // Перетворення малої літери у велику (різниця між 'a' і 'A' = 32)
        char lowerE = 'e';
        char upperE = (char) (lowerE - 32);
        System.out.println("'" + lowerE + "' у верхній регістр: '" + upperE + "'");

        System.out.println();

        // === 3. Спеціальні та українські символи ===

        char hryvnia = '₴';        // символ гривні
        char ukrainianI = 'І';     // українська літера І
        char ukrainianYi = 'Ї';    // українська літера Ї

        System.out.println("=== Unicode-символи ===");
        System.out.println("Символ гривні: " + hryvnia + " (код: " + (int) hryvnia + ")");
        System.out.println("Українська І: " + ukrainianI + " (код: " + (int) ukrainianI + ")");
        System.out.println("Українська Ї: " + ukrainianYi + " (код: " + (int) ukrainianYi + ")");

        System.out.println();

        // === 4. Перебір символів у циклі ===
        // Виведемо всі великі латинські літери

        System.out.println("=== Перебір символів ===");
        System.out.print("Латинські: ");
        for (char c = 'A'; c <= 'Z'; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        // Цифри від '0' до '9' теж символи!
        System.out.print("Цифри:     ");
        for (char c = '0'; c <= '9'; c++) {
            System.out.print(c + "(" + (int) c + ") ");
        }
        System.out.println();
    }
}
