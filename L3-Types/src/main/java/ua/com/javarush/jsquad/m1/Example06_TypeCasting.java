package ua.com.javarush.jsquad.m1;

/**
 * Лекція 3: Перетворення (кастинг) типів
 *
 * Іноді потрібно перетворити один тип на інший.
 * Є два види перетворень:
 *
 * 1. Автоматичне (розширення) — з меншого в більший тип.
 *    Як пересипати цукор з маленької чашки у велику — нічого не втрачається.
 *    byte -> short -> int -> long -> float -> double
 *
 * 2. Явне (звуження) — з більшого в менший тип.
 *    Як налити воду з відра в склянку — частина може вилитись!
 *    Потрібно вказати тип у дужках: (byte), (int) тощо.
 */
public class Example06_TypeCasting {

    public static void main(String[] args) {

        // ============================================================
        //   Автоматичне розширення (маленький -> великий)
        // ============================================================
        System.out.println("=== Автоматичне розширення (без втрат) ===");

        byte myByte = 42;
        short myShort = myByte;    // byte -> short (автоматично)
        int myInt = myShort;       // short -> int (автоматично)
        long myLong = myInt;       // int -> long (автоматично)
        float myFloat = myLong;    // long -> float (автоматично)
        double myDouble = myFloat; // float -> double (автоматично)

        System.out.println("byte:   " + myByte);
        System.out.println("short:  " + myShort);
        System.out.println("int:    " + myInt);
        System.out.println("long:   " + myLong);
        System.out.println("float:  " + myFloat);
        System.out.println("double: " + myDouble);
        System.out.println("Усі значення = 42 — нічого не втрачено!");
        System.out.println();

        // ============================================================
        //   Явне звуження (великий -> маленький)
        // ============================================================
        System.out.println("=== Явне звуження (може втратити дані!) ===");

        // Потрібно писати (тип) перед значенням
        int bigNumber = 130;
        byte smallByte = (byte) bigNumber; // int -> byte (явно!)

        System.out.println("int:  " + bigNumber);
        System.out.println("byte: " + smallByte);  // -126! (бо 130 > 127, відбулось переповнення)
        System.out.println("Увага! 130 не влізло в byte (-128..127), тому результат: " + smallByte);
        System.out.println();

        // Ще приклад: double -> int (дробова частина зникає)
        double price = 29.99;
        int roundedPrice = (int) price;  // просто відкидає дробову частину (НЕ округлює!)

        System.out.println("double: " + price);
        System.out.println("int:    " + roundedPrice);  // 29 (не 30!)
        System.out.println("Дробова частина просто зникає, НЕ округлюється!");
        System.out.println();

        // ============================================================
        //   Практичні приклади
        // ============================================================
        System.out.println("=== Практичні приклади ===");

        // Ділення цілих чисел з отриманням дробового результату
        int apples = 7;
        int people = 2;

        // Неправильно: int / int = int
        System.out.println("7 / 2 = " + (apples / people));         // 3

        // Правильно: кастимо один з операндів до double
        double fair = (double) apples / people;
        System.out.println("(double)7 / 2 = " + fair);              // 3.5

        System.out.println();

        // char і int — взаємне перетворення
        System.out.println("=== char <-> int ===");
        char letter = 'A';
        int code = letter;           // char -> int (автоматично)
        System.out.println("'" + letter + "' -> код " + code);  // 65

        int anotherCode = 90;
        char anotherLetter = (char) anotherCode;  // int -> char (явно)
        System.out.println("код " + anotherCode + " -> '" + anotherLetter + "'");  // Z
    }
}
