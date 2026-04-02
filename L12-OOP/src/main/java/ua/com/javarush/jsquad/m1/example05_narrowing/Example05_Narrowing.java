package ua.com.javarush.jsquad.m1.example05_narrowing;

/**
 * Лекція 12: Типи даних. Ознайомлення з ООП
 * <hr>
 * <h3>Тема: Звуження типів (Narrowing / Casting)</h3>
 *
 * <p>Щоб записати значення більшого типу у менший — потрібне явне приведення.
 * Це може призвести до втрати даних!</p>
 *
 * <p><b>Синтаксис:</b> {@code менший_тип змінна = (менший_тип) більша_змінна;}</p>
 *
 * <h4>Схема звуження (зворотній напрямок):</h4>
 * <pre>
 *  double ──▶ float ──▶ long ──▶ int ──▶ short ──▶ byte
 *                                 │
 *                                 ▼
 *                               char
 *
 *  ──▶  потребує явного (cast)! Можлива втрата даних!
 * </pre>
 *
 *     13
 * 128  64  32  16  8  4  2  1
 * 0    0    0   0  1  1  0  1
 *
 *
 *
 * <h4>Що відбувається при переповненні:</h4>
 * <pre>
 *  int 10_000_000 = 00000000 10011000 10010110 10000000
 *                         ▲▲▲▲▲▲▲▲ ── ці біти відрізаються!
 *  (short)          =                 10010110 10000000
 *                   = -27008 (знаковий біт = 1 → від'ємне!)
 *
 *  ┌────────────────────────┐    ┌──────────────┐
 *  │     int: 10 000 000    │ ──▶│ short: ???   │
 *  │     (4 байти)          │    │ (2 байти)    │
 *  └────────────────────────┘    └──────────────┘
 *  Дані просто обрізаються — як текст, що не влазить у SMS!
 * </pre>
 *
 * <p><b>Аналогія з життя:</b>
 * Як пакування великого товару в маленьку коробку —
 * частина може не влізти, і ми її втратимо.</p>
 *
 * <p><b>Реальне застосування:</b>
 * Перетворення точної ціни (double) у ціле число (int) для відображення,
 * обмеження значень при збереженні у базу даних.</p>
 */
public class Example05_Narrowing {

    public static void main(String[] args) {

        // === 1. Базове звуження — з long у менші типи ===
        // Сценарій: обмеження значень для збереження у базу

        long fullPrice = 1299L;
        int intPrice = (int) fullPrice;        // long -> int (явне приведення)
        short shortPrice = (short) intPrice;   // int -> short
        byte byteValue = (byte) shortPrice;    // short -> byte

        double d = 12d;
        byte b = (byte) d;

        System.out.println("=== Базове звуження типів ===");
        System.out.println("long:  " + fullPrice);
        System.out.println("int:   " + intPrice);
        System.out.println("short: " + shortPrice);
        System.out.println("byte:  " + byteValue);   // 1299 не вміщується в byte!

        System.out.println();

        // === 2. Втрата даних при звуженні ===
        // Сценарій: велике число не вміщується у маленький тип

        int bigNumber = 10_000_000;
        short littleNumber = (short) bigNumber;  // Обрізання! Результат: -27008

        System.out.println("=== Втрата даних ===");
        System.out.println("int bigNumber = " + bigNumber);
        System.out.println("(short) bigNumber = " + littleNumber);
        System.out.println("Дані втрачені! 10 000 000 не вміщується у short (-32768..32767)");

        System.out.println();

        // === 3. double -> int (відкидання дробової частини) ===
        // Сценарій: округлення ціни для відображення на сайті

        double exactPrice = 1499.97;
        int displayPrice = (int) exactPrice;  // дробова частина просто відкидається

        System.out.println("=== double -> int (відкидання дробової частини) ===");
        System.out.println("Точна ціна (double): " + exactPrice + " грн");
        System.out.println("Для відображення (int): " + displayPrice + " грн");
        System.out.println("Увага: дробова частина .97 просто зникла (не округлення!)");

        System.out.println();

        // === 4. Правильне округлення ===
        // Якщо потрібне округлення — використовуємо Math.round()

        double price1 = 199.49;
        double price2 = 199.50;

        System.out.println("=== Правильне округлення з Math.round() ===");
        System.out.println(price1 + " -> (int) = " + (int) price1 + " | Math.round() = " + Math.round(price1));
        System.out.println(price2 + " -> (int) = " + (int) price2 + " | Math.round() = " + Math.round(price2));

        System.out.println();

        // === 5. Звуження char <-> int ===
        // Сценарій: перетворення номера категорії назад у символ

        int code = 66;                // код літери 'B'
        char category = (char) code;  // int -> char (явне звуження)

        System.out.println("=== int -> char ===");
        System.out.println("Код: " + code + " -> Символ: '" + category + "'");

        System.out.println();

        // === 6. Підступні випадки ===

        System.out.println("=== Підступні випадки ===");

        // Переповнення byte
        int value = 130;
        byte result = (byte) value;  // 130 > 127, тому відбувається переповнення
        System.out.println("(byte) 130 = " + result + " (переповнення! 130 - 256 = -126)");

        // Від'ємне число в byte
        int negative = -1;
        byte negByte = (byte) negative;
        System.out.println("(byte) -1 = " + negByte + " (вміщується, бо -128..127)");
    }
}
