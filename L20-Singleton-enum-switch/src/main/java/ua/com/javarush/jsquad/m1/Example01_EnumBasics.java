package ua.com.javarush.jsquad.m1;

/**
 * Лекція 20: Singleton, enum, switch — Основи enum (перелічування)
 *
 * enum (від слова Enumeration — перелічування) — тип даних, що складається
 * з кінцевого набору іменованих констант. Кожне значення enum існує
 * в єдиному екземплярі.
 *
 * Синтаксис оголошення:
 *   enum ІмʼяТипу {
 *       ЗНАЧЕННЯ1,
 *       ЗНАЧЕННЯ2,
 *       ЗНАЧЕННЯ3
 *   }
 *
 * Присвоєння:
 *   Day day = Day.MONDAY;
 *
 * Аналогія з життя: світлофор — він може бути тільки ЧЕРВОНИЙ, ЖОВТИЙ
 * або ЗЕЛЕНИЙ. Неможливо вигадати новий колір. Кожен колір є унікальним
 * і існує рівно в одному екземплярі.
 *
 * Реальне застосування: дні тижня, місяці, статуси замовлень, ролі
 * користувачів, HTTP-коди відповідей, стани гри.
 */
public class Example01_EnumBasics {

    // Оголошення enum — тип "День тижня" з 7 можливими значеннями

    // Ще один enum — місяці року
    enum Month {
        JANUARY, FEBRUARY, MARCH,
        APRIL, MAY, JUNE,
        JULY, AUGUST, SEPTEMBER,
        OCTOBER, NOVEMBER, DECEMBER
    }

    // Enum для статусу замовлення
    enum OrderStatus {
        NEW,
        PROCESSING,
        SHIPPED,
        DELIVERED
    }

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Оголошення enum — дні тижня
        // ============================================================
        System.out.println("=== Блок 1: Оголошення та присвоєння ===");

        // Створюємо змінну типу Day і присвоюємо значення
        Day today = Day.MONDAY;
        System.out.println("Сьогодні: " + today);  // WEDNESDAY

        // Можна присвоїти будь-яке значення з enum
        Day weekend = Day.SATURDAY;
        System.out.println("Вихідний: " + weekend); // SATURDAY

        // Day invalid = "MONDAY";  // НЕ скомпілюється — Day це не String!
        // Day invalid = 1;         // НЕ скомпілюється — Day це не int!
        System.out.println("enum — це окремий тип, не String і не int");
        System.out.println();

        // ============================================================
        //   Блок 2: Порівняння з == — кожне значення єдине
        // ============================================================
        System.out.println("=== Блок 2: Порівняння enum з == ===");

        Day day1 = Day.MONDAY;
        Day day2 = Day.MONDAY;
        Day day3 = Day.FRIDAY;

        // Порівняння через == працює, бо кожне значення існує в одному екземплярі
        System.out.println("day1 == day2: " + (day1 == day2));        // true
        System.out.println("day1 == day3: " + (day1 == day3));        // false
        System.out.println("day1.equals(day2): " + day1.equals(day2)); // true — теж працює

        // Чому == працює? Бо не може існувати двох різних обʼєктів MONDAY
        // з різними посиланнями. Кожен обʼєкт-значення типу enum існує
        // лише в єдиному екземплярі. Тому == завжди працюватиме.
        System.out.println();

        // ============================================================
        //   Блок 3: Enum у циклі — перебір усіх значень
        // ============================================================
        System.out.println("=== Блок 3: Перебір усіх значень enum ===");

        // Метод values() повертає масив УСІХ значень enum
        System.out.println("Усі місяці року:");
        for (Month month : Month.values()) {
            System.out.println("  " + month);
        }

        System.out.println("Усього місяців: " + Month.values().length); // 12
        System.out.println();

        // ============================================================
        //   Блок 4: Enum замість магічних чисел
        // ============================================================
        System.out.println("=== Блок 4: Enum замість магічних чисел ===");

        // ❌ ПОГАНО — магічні числа (без enum)
        // int status = 0;  // Що таке 0? NEW? ERROR? Хтось памʼятає?
        // if (status == 0) { ... }
        // if (status == 5) { ... }  // А 5 — це що?
        System.out.println("Без enum: status = 0 — незрозуміло, що означає число");

        // ✅ ДОБРЕ — enum
        OrderStatus status = OrderStatus.NEW;
        System.out.println("З enum:   status = " + status + " — одразу зрозуміло!");

        // Перебираємо статуси замовлення
        System.out.println("Можливі статуси замовлення:");
        for (OrderStatus s : OrderStatus.values()) {
            System.out.println("  → " + s);
        }

        System.out.println();
        System.out.println("Переваги enum:");
        System.out.println("  1. Типобезпека — компілятор перевіряє значення");
        System.out.println("  2. Читабельність — код зрозумілий без коментарів");
        System.out.println("  3. Кінцевий набір — неможливо присвоїти хибне значення");
    }
}
