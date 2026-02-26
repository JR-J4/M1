package ua.com.javarush.jsquad.m1;

/**
 * Лекція 1-2: Методи роботи з рядками (String)
 *
 * String має вбудовані "суперсили" (методи):
 *   length()      - дізнатись довжину (скільки символів)
 *   toLowerCase() - перетворити всі літери на маленькі
 *   toUpperCase() - перетворити всі літери на ВЕЛИКІ
 *
 * Метод викликається через крапку: змінна.метод()
 * Як попросити когось щось зробити: "Рядок, скажи свою довжину!" -> name.length()
 */
public class Example08_StringMethods {

    public static void main(String[] args) {

        // === length() - довжина рядка ===
        // Рахує ВСІ символи, включно з пробілами
        System.out.println("=== length() - довжина рядка ===");

        String name = "Java";
        int len = name.length();
        System.out.println("\"" + name + "\" - довжина: " + len);  // 4

        String greeting = "Привіт, світе!";
        System.out.println("\"" + greeting + "\" - довжина: " + greeting.length());  // 14

        // Пробіли теж рахуються!
        String withSpaces = "A B C";
        System.out.println("\"" + withSpaces + "\" - довжина: " + withSpaces.length());  // 5

        System.out.println();

        // === toLowerCase() - все маленькими ===
        // Корисно для порівняння: "Java" і "java" - це одне й те саме?
        System.out.println("=== toLowerCase() - маленькі літери ===");

        String city = "КИЇВ";
        String cityLower = city.toLowerCase();
        System.out.println(city + " -> " + cityLower);  // київ

        String mixed = "PrOgRaMuVaNnYa";
        System.out.println(mixed + " -> " + mixed.toLowerCase());  // programuvannya

        System.out.println();

        // === toUpperCase() - все ВЕЛИКИМИ ===
        // Як "кричати" текстом
        System.out.println("=== toUpperCase() - ВЕЛИКІ літери ===");

        String whisper = "тихо";
        String shout = whisper.toUpperCase();
        System.out.println(whisper + " -> " + shout);  // ТИХО

        String language = "java";
        System.out.println(language + " -> " + language.toUpperCase());  // JAVA

        System.out.println();

        // === Реальний приклад: картка студента ===
        System.out.println("=== Картка студента ===");
        String studentName = "Оксана Петренко";
        String group = "КП-21";
        int course = 2;

        System.out.println("========================");
        System.out.println("  " + studentName.toUpperCase());
        System.out.println("  Група: " + group);
        System.out.println("  Курс: " + course);
        System.out.println("  Символів в імені: " + studentName.length());
        System.out.println("========================");
    }
}
