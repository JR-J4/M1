package ua.com.javarush.jsquad.m1;

/**
 * Лекція 20: Singleton, enum, switch — Методи enum
 *
 * Кожен enum автоматично отримує кілька корисних методів:
 *   values()       — статичний метод, повертає масив усіх значень enum
 *   ordinal()      — повертає порядковий номер значення (з 0)
 *   toString()     — повертає імʼя значення як String
 *   valueOf(String) — статичний метод, перетворює рядок на значення enum
 *
 * 4 типи конвертації:
 *   enum → String:  day.toString()        або  "" + day
 *   String → enum:  Day.valueOf("MONDAY")
 *   enum → int:     day.ordinal()
 *   int → enum:     Day.values()[index]
 *
 * Аналогія з життя: пронумерований список працівників компанії.
 * values() — це весь список, ordinal() — номер у списку,
 * toString() — бейджик з іменем, valueOf() — пошук за іменем.
 *
 * Реальне застосування: серіалізація (зберігання enum у БД як String),
 * випадаючі списки в UI, маппінг enum на індекси масивів.
 */
public class Example02_EnumMethods {

    enum Season {
        SPRING,   // ordinal = 0
        SUMMER,   // ordinal = 1
        AUTUMN,   // ordinal = 2
        WINTER    // ordinal = 3
    }

    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: values() — отримати всі значення
        // ============================================================
        System.out.println("=== Блок 1: values() — масив усіх значень ===");

        // values() повертає масив Season[] зі всіма значеннями
        Season[] allSeasons = Season.values();

        System.out.println("Усі пори року:");
        for (Season season : allSeasons) {
            System.out.println("  " + season);
        }

        // Можна звертатись за індексом
        System.out.println("Третя пора року: " + allSeasons[2]); // AUTUMN
        System.out.println("Кількість: " + allSeasons.length);    // 4
        System.out.println();

        // ============================================================
        //   Блок 2: ordinal() — порядковий номер
        // ============================================================
        System.out.println("=== Блок 2: ordinal() — порядковий номер ===");

        // ordinal() повертає індекс значення (починається з 0!)
        // Викликати потрібно не у класу enum, а у значення enum
        for (Season season : Season.values()) {
            System.out.println("  " + season + " → ordinal = " + season.ordinal());
        }

        System.out.println();
        System.out.println("Дні тижня:");
        System.out.println("  MONDAY.ordinal()  = " + Day.MONDAY.ordinal());   // 0
        System.out.println("  FRIDAY.ordinal()  = " + Day.FRIDAY.ordinal());   // 4
        System.out.println("  SUNDAY.ordinal()  = " + Day.SUNDAY.ordinal());   // 6
        System.out.println();

        // ============================================================
        //   Блок 3: toString() — перетворення в рядок
        // ============================================================
        System.out.println("=== Блок 3: toString() — enum у String ===");

        Season winter = Season.WINTER;

        // Явний виклик toString()
        String str1 = winter.toString();
        System.out.println("toString(): " + str1);  // WINTER

        // Неявний виклик — конкатенація з рядком
        String str2 = "Зараз " + winter;
        System.out.println(str2);  // Зараз WINTER

        // println() теж викликає toString() неявно
        System.out.println("println: " + winter);  // WINTER
        System.out.println();

        // ============================================================
        //   Блок 4: valueOf() — рядок у enum
        // ============================================================
        System.out.println("=== Блок 4: valueOf() — String у enum ===");

        // valueOf() перетворює рядок назад у значення enum
        Season fromString = Season.valueOf("SUMMER");
        System.out.println("valueOf(\"SUMMER\"): " + fromString);  // SUMMER
        System.out.println("Це SUMMER? " + (fromString == Season.SUMMER)); // true

        // УВАГА: valueOf() чутливий до регістру!
        try {
            Season invalid = Season.valueOf("summer"); // маленькі літери
        } catch (IllegalArgumentException e) {
            System.out.println("valueOf(\"summer\") → помилка: " + e.getMessage());
        }

        // УВАГА: неіснуюче значення теж викличе помилку
        try {
            Season noSuch = Season.valueOf("RAIN");
        } catch (IllegalArgumentException e) {
            System.out.println("valueOf(\"RAIN\") → помилка: " + e.getMessage());
        }
        System.out.println();

        // ============================================================
        //   Блок 5: Конвертація enum ↔ int та enum ↔ String
        // ============================================================
        System.out.println("=== Блок 5: 4 типи конвертації ===");

        Season original = Season.AUTUMN;

        // 1. enum → String
        String asString = original.toString();
        System.out.println("1. enum → String:  " + original + " → \"" + asString + "\"");

        // 2. String → enum
        Season fromStr = Season.valueOf(asString);
        System.out.println("2. String → enum:  \"" + asString + "\" → " + fromStr);

        // 3. enum → int
        int asInt = original.ordinal();
        System.out.println("3. enum → int:     " + original + " → " + asInt);

        // 4. int → enum
        Season fromInt = Season.values()[asInt];
        System.out.println("4. int → enum:     " + asInt + " → " + fromInt);

        System.out.println();
        System.out.println("Усі 4 конвертації повернули AUTUMN — коло замкнулось!");
    }
}
