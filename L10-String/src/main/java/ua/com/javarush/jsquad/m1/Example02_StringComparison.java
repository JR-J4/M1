package ua.com.javarush.jsquad.m1;

/**
 * Лекцiя 10: Порiвняння рядкiв
 *
 * String порiвнюють методами equals(), equalsIgnoreCase(), compareTo(),
 * startsWith() та iншими. Оператор == порiвнює лише посилання.
 *
 * Аналогiя: перевірити ISBN книги — важливі всі цифри, а не те,
 * чи знаходиться книга в одній і тій же полиці (посилання).
 *
 * Реальне застосування: валідація логіну, сортування клієнтів, фільтрація
 * файлів за розширенням.
 */
public class Example02_StringComparison {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Перевірка логіну незалежно від регістру
        // ============================================================
        System.out.println("=== equals() vs equalsIgnoreCase() ===");

        String expectedLogin = "manager";
        String userInput = "Manager";

        boolean strictMatch = expectedLogin.equals(userInput);
        boolean caseInsensitiveMatch = expectedLogin.equalsIgnoreCase(userInput);

        System.out.println("Точний збіг: " + strictMatch);
        System.out.println("Без урахування регістру: " + caseInsensitiveMatch);
        System.out.println();

        // ============================================================
        //   Приклад 2: Сортування назв міст для довідника
        // ============================================================
        System.out.println("=== compareTo() для сортування ===");

        String cityA = "Вінниця";
        String cityB = "Дніпро";
        String cityC = "Варшава";

        System.out.println("Вінниця vs Дніпро: " + cityA.compareTo(cityB));
        System.out.println("Варшава vs Вінниця: " + cityC.compareTo(cityA));
        System.out.println("Вінниця vs Вінниця: " + cityA.compareTo("Вінниця"));
        System.out.println();

        // ============================================================
        //   Приклад 3: Перевірка коду замовлення за шаблоном
        // ============================================================
        System.out.println("=== startsWith(), endsWith(), regionMatches() ===");

        String orderCode = "WEB-2024-00125";

        boolean fromWeb = orderCode.startsWith("WEB-");
        boolean currentYear = orderCode.regionMatches(4, "2024", 0, 4);
        boolean archiveSuffix = orderCode.endsWith("ARCH");

        System.out.println("Зроблено на сайті: " + fromWeb);
        System.out.println("Належить до 2024 року: " + currentYear);
        System.out.println("Архівне замовлення: " + archiveSuffix);
    }
}
