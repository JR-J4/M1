package ua.com.javarush.jsquad.m1;

/**
 * Лекція 16: Списки — Типи-обгортки (Wrapper Types)
 *
 * Починаючи з п'ятої версії Java, у примітивних типів з'явилися класи-близнюки.
 * Кожен такий клас зберігає всередині одне поле зі значенням певного типу.
 * Їх називають типами-обгортками, бо вони обертають примітивні значення в класи.
 *
 * Таблиця відповідності:
 *   byte    -> Byte
 *   short   -> Short
 *   int     -> Integer   (!)
 *   long    -> Long
 *   float   -> Float
 *   double  -> Double
 *   char    -> Character (!)
 *   boolean -> Boolean
 *
 * Аналогія з життя: примітив — це цукерка без обгортки. Wrapper — це та сама
 * цукерка, загорнута у фантик із написом (назва, склад, методи).
 * Без обгортки цукерку не покладеш на полицю магазину (колекції).
 *
 * Реальне застосування: колекції (ArrayList тощо) не можуть зберігати примітиви,
 * тому потрібні типи-обгортки.
 */
public class Example01_WrapperTypes {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Клас Integer — поля та методи
        // ============================================================
        System.out.println("=== Клас Integer ===");

        // Максимальне та мінімальне значення типу int
        System.out.println("Integer.MAX_VALUE: " + Integer.MAX_VALUE);   // 2147483647
        System.out.println("Integer.MIN_VALUE: " + Integer.MIN_VALUE);   // -2147483648

        // Корисні методи Integer
        int number = 255;
        System.out.println("Hex: " + Integer.toHexString(number));       // ff
        System.out.println("Binary: " + Integer.toBinaryString(number)); // 11111111
        System.out.println("Octal: " + Integer.toOctalString(number));   // 377

        // parseInt — отримати число з рядка (наприклад, з конфігурації сервера)
        String portString = "8080";
        int port = Integer.parseInt(portString);
        System.out.println("Порт сервера: " + port); // 8080

        // valueOf — обернути int в Integer
        Integer wrapped = Integer.valueOf(42);
        System.out.println("Обгорнуте число: " + wrapped); // 42
        System.out.println();

        // ============================================================
        //   Приклад 2: Клас Double — поля та методи
        // ============================================================
        System.out.println("=== Клас Double ===");

        // Спеціальні значення
        System.out.println("POSITIVE_INFINITY: " + Double.POSITIVE_INFINITY);
        System.out.println("NEGATIVE_INFINITY: " + Double.NEGATIVE_INFINITY);
        System.out.println("MAX_VALUE: " + Double.MAX_VALUE);
        System.out.println("MIN_VALUE: " + Double.MIN_VALUE);
        System.out.println("MIN_EXPONENT: " + Double.MIN_EXPONENT); // мінімальне значення експоненти
        System.out.println("MAX_EXPONENT: " + Double.MAX_EXPONENT); // максимальне значення експоненти

        // toHexString — шістнадцяткове представлення числа
        System.out.println("toHexString(3.14): " + Double.toHexString(3.14));

        // Перевірка на нескінченність та NaN
        double result = 1.0 / 0;
        System.out.println("1.0 / 0 = " + result);                          // Infinity
        System.out.println("Це нескінченність? " + Double.isInfinite(result)); // true

        double nan = 0.0 / 0;
        System.out.println("0.0 / 0 = " + nan);                    // NaN
        System.out.println("Це NaN? " + Double.isNaN(nan));         // true

        // parseDouble — зчитати дробове число з рядка
        String priceString = "199.99";
        double price = Double.parseDouble(priceString);
        System.out.println("Ціна товару: " + price); // 199.99
        System.out.println();

        // ============================================================
        //   Приклад 3: Клас Character — методи перевірки символів
        // ============================================================
        System.out.println("=== Клас Character ===");

        // Уявіть: перевірка коректності введення — валідація пароля
        char letter = 'A';
        char digit = '7';
        char space = ' ';
        char ukr = 'Я';

        System.out.println("'" + letter + "' — буква? " + Character.isLetter(letter));       // true
        System.out.println("'" + digit + "' — цифра? " + Character.isDigit(digit));           // true
        System.out.println("'" + space + "' — пробіл (whitespace)? " + Character.isWhitespace(space)); // true
        System.out.println("'" + space + "' — пробіл (spaceChar)? " + Character.isSpaceChar(space)); // true
        System.out.println("'" + ukr + "' — буква алфавіту? " + Character.isAlphabetic(ukr)); // true

        // isSpaceChar vs isWhitespace:
        // isSpaceChar — тільки Unicode-пробіли (категорія Zs)
        // isWhitespace — пробіли + табуляція, перенос рядка тощо
        char tab = '\t';
        System.out.println("'\\t' — whitespace? " + Character.isWhitespace(tab));  // true
        System.out.println("'\\t' — spaceChar? " + Character.isSpaceChar(tab));    // false

        // Перетворення регістру
        System.out.println("toLowerCase('A'): " + Character.toLowerCase('A')); // a
        System.out.println("toUpperCase('z'): " + Character.toUpperCase('z')); // Z

        // Перевірка регістру
        System.out.println("isUpperCase('A'): " + Character.isUpperCase('A')); // true
        System.out.println("isLowerCase('a'): " + Character.isLowerCase('a')); // true
        System.out.println();

        // ============================================================
        //   Приклад 4: Клас Boolean — константи
        // ============================================================
        System.out.println("=== Клас Boolean ===");

        // Boolean має дві константи
        Boolean trueValue = Boolean.TRUE;
        Boolean falseValue = Boolean.FALSE;

        System.out.println("Boolean.TRUE:  " + trueValue);   // true
        System.out.println("Boolean.FALSE: " + falseValue);  // false

        // parseBoolean — отримати boolean з рядка (наприклад, з конфігурації)
        String debugMode = "true";
        boolean isDebug = Boolean.parseBoolean(debugMode);
        System.out.println("Режим налагодження: " + isDebug); // true
    }
}
