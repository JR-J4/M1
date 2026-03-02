package ua.com.javarush.jsquad.m1;

/**
 * Лекція 3: Цілочисельні типи — byte, short, int, long
 *
 * Java має 4 типи для цілих чисел. Вони відрізняються розміром у пам'яті
 * та діапазоном значень, які можуть зберігати.
 *
 *   byte  — 1 байт  — від -128 до 127                     (вік людини, оцінка)
 *   short — 2 байти — від -32,768 до 32,767                (рік, кількість днів)
 *   int   — 4 байти — від -2,147,483,648 до 2,147,483,647  (населення міста)
 *   long  — 8 байтів — дуже великі числа                    (населення планети)
 *
 * Походження назв:
 *   byte  — Byte (один байт пам'яті)
 *   short — Short Integer (коротке ціле)
 *   int   — Integer (ціле число)
 *   long  — Long Integer (довге ціле)
 */
public class Example02_IntegerTypes {

    public static void main(String[] args) {

        // === byte: 1 байт (-128 .. 127) ===
        // Ідеально для маленьких значень: вік, оцінка, номер місяця
        System.out.println("=== byte (1 байт, -128..127) ===");
        byte age = 17;
        byte grade = 12;
        byte month = 9;         // вересень
        byte temperature = -15; // мороз!

        System.out.println("Вік: " + age);
        System.out.println("Оцінка: " + grade);
        System.out.println("Місяць: " + month);
        System.out.println("Температура: " + temperature);

        // Максимальне і мінімальне значення byte
        System.out.println("Мін byte: " + Byte.MIN_VALUE);   // -128
        System.out.println("Макс byte: " + Byte.MAX_VALUE);  // 127
        System.out.println();

        // === short: 2 байти (-32768 .. 32767) ===
        // Для середніх значень: рік, кількість студентів, порт
        System.out.println("=== short (2 байти, -32768..32767) ===");
        short year = 2025;
        short studentsInSchool = 1500;
        short altitude = -400; // Мертве море: -400 метрів

        System.out.println("Рік: " + year);
        System.out.println("Учнів у школі: " + studentsInSchool);
        System.out.println("Висота Мертвого моря: " + altitude + " м");
        System.out.println("Мін short: " + Short.MIN_VALUE);
        System.out.println("Макс short: " + Short.MAX_VALUE);
        System.out.println();

        // === int: 4 байти (-2.1 млрд .. 2.1 млрд) ===
        // Найпопулярніший тип! Для більшості задач достатньо int
        System.out.println("=== int (4 байти, ~-2.1 млрд .. ~2.1 млрд) ===");
        int populationKyiv = 2900000;
        int salary = 45000;
        int distanceToMoon = 384400; // км

        System.out.println("Населення Києва: " + populationKyiv);
        System.out.println("Зарплата: " + salary + " грн");
        System.out.println("Відстань до Місяця: " + distanceToMoon + " км");
        System.out.println("Мін int: " + Integer.MIN_VALUE);
        System.out.println("Макс int: " + Integer.MAX_VALUE);
        System.out.println();

        // === long: 8 байтів (дуже великі числа) ===
        // Для чисел, що не влазять в int. ОБОВ'ЯЗКОВО додавати L в кінці!
        System.out.println("=== long (8 байтів, дуже великі числа) ===");
        long worldPopulation = 8000000000L;    // 8 мільярдів — не влізе в int!
        long distanceToSun = 149600000000L;    // 149.6 млн км у метрах
        long nanoseconds = 1000000000L;        // 1 секунда у наносекундах

        System.out.println("Населення Землі: " + worldPopulation);
        System.out.println("Відстань до Сонця: " + distanceToSun + " м");
        System.out.println("Наносекунд в секунді: " + nanoseconds);
        System.out.println("Мін long: " + Long.MIN_VALUE);
        System.out.println("Макс long: " + Long.MAX_VALUE);
    }
}
