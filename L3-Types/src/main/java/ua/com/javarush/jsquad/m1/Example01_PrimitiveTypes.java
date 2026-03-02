package ua.com.javarush.jsquad.m1;

/**
 * Лекція 3: Примітивні типи в Java
 *
 * Java має 8 примітивних типів даних — це "цеглинки" для зберігання інформації.
 * Кожен тип займає певну кількість байтів у пам'яті комп'ютера.
 *
 * Уявіть пам'ять як таблицю Excel — кожна комірка це 1 байт.
 * Маленькі типи (byte) займають 1 комірку, великі (long, double) — 8 комірок.
 *
 * Цілі числа:   byte (1), short (2), int (4), long (8) — байтів
 * Дійсні числа:  float (4), double (8) — байтів
 * Логічний:      boolean (1 біт) — true або false
 * Символ:        char (2) — один символ Unicode
 */
public class Example01_PrimitiveTypes {

    public static void main(String[] args) {

        System.out.println("=== 8 примітивних типів Java ===");
        System.out.println();

        // --- Цілочисельні типи (для зберігання цілих чисел) ---
        byte myByte = 100;          // 1 байт, від -128 до 127
        short myShort = 30000;      // 2 байти, від -32768 до 32767
        int myInt = 2000000000;     // 4 байти, від -2.1 млрд до 2.1 млрд
        long myLong = 9000000000L;  // 8 байтів, дуже великі числа (додаємо L в кінці!)

        System.out.println("--- Цілочисельні типи ---");
        System.out.println("byte:  " + myByte + "  (1 байт)");
        System.out.println("short: " + myShort + "  (2 байти)");
        System.out.println("int:   " + myInt + "  (4 байти)");
        System.out.println("long:  " + myLong + "  (8 байтів)");
        System.out.println();

        // --- Дійсні типи (для чисел з дробовою частиною) ---
        float myFloat = 3.14f;      // 4 байти (додаємо f в кінці!)
        double myDouble = 3.14159265358979; // 8 байтів, точніший за float

        System.out.println("--- Дійсні типи ---");
        System.out.println("float:  " + myFloat + "   (4 байти)");
        System.out.println("double: " + myDouble + "  (8 байтів)");
        System.out.println();

        // --- Логічний тип ---
        boolean isStudent = true;   // тільки true або false
        boolean isRaining = false;

        // bo

        System.out.println("--- Логічний тип ---");
        System.out.println("boolean isStudent: " + isStudent);
        System.out.println("boolean isRaining: " + isRaining);
        System.out.println();

        // --- Символьний тип ---
        char letter = '<';          // 2 байти, один символ у одинарних лапках ''

        char testChar = (char) (letter * 2);

        char ukrainianChar = 'Я';
        char digit = '7';           // це символ '7', а не число 7!

        System.out.println("--- Символьний тип ---");
        System.out.println("char letter: " + testChar);
        System.out.println("char ukrainianChar: " + ukrainianChar);
        System.out.println("char digit: " + digit);

    }
}
