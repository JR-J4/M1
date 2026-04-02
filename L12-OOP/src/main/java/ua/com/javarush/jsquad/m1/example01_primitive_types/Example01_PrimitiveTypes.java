package ua.com.javarush.jsquad.m1.example01_primitive_types;

/**
 * Лекція 12: Типи даних. Ознайомлення з ООП
 * <hr>
 * <h3>Тема: Примітивні типи даних Java</h3>
 *
 * <p>Java має 8 базових примітивних типів. Їх називають примітивними, тому що
 * значення зберігаються прямо всередині змінних (а не за посиланням).</p>
 *
 * <p><b>Синтаксис:</b> {@code тип ім'я = значення;}</p>
 *
 * <h4>Таблиця примітивних типів:</h4>
 * <pre>
 * ┌──────────┬────────┬──────────────────────┬───────────────┐
 * │   Тип    │ Розмір │   Діапазон значень   │ За замовч.    │
 * ├──────────┼────────┼──────────────────────┼───────────────┤
 * │ byte     │ 1 байт │ -128 .. 127          │ 0             │
 * │ short    │ 2 байт │ -32 768 .. 32 767    │ 0             │
 * │ int      │ 4 байт │ -2*10⁹ .. 2*10⁹      │ 0             │
 * │ long     │ 8 байт │ -9*10¹⁸ .. 9*10¹⁸    │ 0L            │
 * │ float    │ 4 байт │ ±3.4 * 10³⁸          │ 0.0f          │
 * │ double   │ 8 байт │ ±1.7 * 10³⁰⁸         │ 0.0d          │
 * │ boolean  │ 1 байт │ true / false         │ false         │
 * │ char     │ 2 байт │ 0 .. 65 535          │ '\u0000'      │
 * └──────────┴────────┴──────────────────────┴───────────────┘
 * </pre>
 *
 * <h4>Розмір у пам'яті (візуалізація):</h4>
 * <pre>
 *  byte    [█]                          1 байт
 *  short   [██]                         2 байти
 *  char    [██]                         2 байти
 *  int     [████]                       4 байти
 *  float   [████]                       4 байти
 *  long    [████████]                   8 байт
 *  double  [████████]                   8 байт
 * </pre>
 *
 * <p><b>Аналогія з життя:</b>
 * Уявіть, що ви заповнюєте картку товару в інтернет-магазині.
 * Для кожного поля потрібен свій тип даних: назва (String), ціна (double),
 * кількість (int), чи є в наявності (boolean).
 * Правильний вибір типу — як вибір правильної коробки для товару:
 * маленький товар не потребує величезної коробки!</p>
 *
 * <p><b>Реальне застосування:</b>
 * Вибір типу впливає на використання пам'яті та допустимий діапазон значень.</p>
 */
public class Example01_PrimitiveTypes {

    public static void main(String[] args) {

        // === 1. Цілочисельні типи — зберігають цілі числа різного розміру ===
        // Сценарій: характеристики товару в інтернет-магазині

        byte rating = 5;              // 1 байт, від -128 до 127 (рейтинг товару 1-5)
        short categoryCode = 1024;    // 2 байти, від -32768 до 32767 (код категорії)
        int quantity = 150_000;       // 4 байти, до ~2 млрд (кількість на складі)
        long barcode = 4_600_000_000L;// 8 байт (штрих-код товару — потрібен long!)

        System.out.println("=== Цілочисельні типи (інтернет-магазин) ===");
        System.out.println("Рейтинг товару (byte): " + rating);
        System.out.println("Код категорії (short): " + categoryCode);
        System.out.println("Кількість на складі (int): " + quantity);
        System.out.println("Штрих-код (long): " + barcode);

        System.out.println();

        // === 2. Дробові типи — зберігають числа з рухомою точкою ===
        // Сценарій: ціни та знижки в магазині

        float discountPercent = 15.5f;    // 4 байти, ~7 значущих цифр
        double price = 29_999.99;         // 8 байт, ~15 значущих цифр (ціна в грн)

        System.out.println("=== Дробові типи (ціни та знижки) ===");
        System.out.println("Знижка (float): " + discountPercent + "%");
        System.out.println("Ціна товару (double): " + price + " грн");

        double priceAfterDiscount = price - (price * discountPercent / 100);
        System.out.println("Ціна зі знижкою: " + priceAfterDiscount + " грн");

        System.out.println();

        // === 3. Логічний тип — true або false ===
        // Сценарій: стан товару

        boolean isAvailable = true;       // чи є в наявності
        boolean isOnSale = false;         // чи є на розпродажі

        System.out.println("=== Логічний тип (стан товару) ===");
        System.out.println("В наявності (boolean): " + isAvailable);
        System.out.println("На розпродажі (boolean): " + isOnSale);

        System.out.println();

        // === 4. Символьний тип — один символ Unicode ===
        // Сценарій: класифікація товару за літерою категорії

        char sizeLabel = 'L';             // 2 байти, зберігає один символ
        char currencySymbol = '₴';        // Unicode-символ гривні

        System.out.println("=== Символьний тип (маркування) ===");
        System.out.println("Розмір (char): " + sizeLabel);
        System.out.println("Валюта (char): " + currencySymbol);

        System.out.println();

        // === 5. Значення за замовчуванням (для полів класу) ===
        // Локальні змінні НЕ мають значень за замовчуванням —
        // їх обов'язково потрібно ініціалізувати!
        // Але поля класу ініціалізуються автоматично:

        System.out.println("=== Значення за замовчуванням (поля класу) ===");
        System.out.println("byte:    0");
        System.out.println("short:   0");
        System.out.println("int:     0");
        System.out.println("long:    0L");
        System.out.println("float:   0.0f");
        System.out.println("double:  0.0d");
        System.out.println("boolean: false");
        System.out.println("char:    '\\u0000' (порожній символ)");

        System.out.println();

        // === 6. Розмір кожного типу в пам'яті ===

        System.out.println("=== Розмір типів у пам'яті ===");
        System.out.println("byte    = 1 байт  | діапазон: -128 .. 127");
        System.out.println("short   = 2 байти | діапазон: -32 768 .. 32 767");
        System.out.println("int     = 4 байти | діапазон: ~-2 млрд .. ~2 млрд");
        System.out.println("long    = 8 байт  | діапазон: ~-9*10^18 .. ~9*10^18");
        System.out.println("float   = 4 байти | діапазон: ~-10^38 .. ~10^38");
        System.out.println("double  = 8 байт  | діапазон: ~-10^308 .. ~10^308");
        System.out.println("boolean = 1 байт  | значення: true, false");
        System.out.println("char    = 2 байти | діапазон: 0 .. 65 535");
    }
}
