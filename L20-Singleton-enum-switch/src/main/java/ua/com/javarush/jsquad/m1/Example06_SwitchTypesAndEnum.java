package ua.com.javarush.jsquad.m1;

/**
 * Лекція 20: Singleton, enum, switch — Switch: типи даних та enum
 *
 * Не всі типи можна використовувати у switch. Дозволені:
 *   • цілі типи: byte, short, int
 *   • тип char
 *   • тип String
 *   • значення будь-якого enum-типу
 *
 * НЕ дозволені: long, float, double, boolean.
 *
 * Особливість switch з enum: у case пишемо значення БЕЗ імені enum-типу.
 * Тобто: case RED, а НЕ case TrafficLight.RED.
 *
 * Аналогія з життя: автомат з напоями приймає тільки певні монети
 * (визначені типи). Банкноту (непідтримуваний тип) автомат відхилить
 * на етапі компіляції.
 *
 * Реальне застосування: парсинг команд (String switch), визначення
 * оцінки (char switch), автомати станів з enum-станами.
 */
public class Example06_SwitchTypesAndEnum {

    // Enum для світлофора
    enum TrafficLight {
        RED,
        YELLOW,
        GREEN
    }

    // Enum для пори року
    enum Season {
        SPRING,
        SUMMER,
        AUTUMN,
        WINTER
    }

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: switch з char — оцінки студента
        // ============================================================
        System.out.println("=== Блок 1: switch з char ===");

        char[] grades = {'A', 'B', 'C', 'D', 'F'};

        for (char grade : grades) {
            switch (grade) {
                case 'A':
                    System.out.println("  " + grade + " → Відмінно");
                    break;
                case 'B':
                    System.out.println("  " + grade + " → Добре");
                    break;
                case 'C':
                    System.out.println("  " + grade + " → Задовільно");
                    break;
                case 'D':
                    System.out.println("  " + grade + " → Незадовільно");
                    break;
                case 'F':
                    System.out.println("  " + grade + " → Не склав");
                    break;
                default:
                    System.out.println("  " + grade + " → Невідома оцінка");
            }
        }
        System.out.println();

        // ============================================================
        //   Блок 2: switch з String — диспетчер команд
        // ============================================================
        System.out.println("=== Блок 2: switch з String ===");

        String[] commands = {"start", "pause", "stop", "restart", "unknown"};

        for (String command : commands) {
            switch (command) {
                case "start":
                    System.out.println("  \"" + command + "\" → Запускаємо систему...");
                    break;
                case "pause":
                    System.out.println("  \"" + command + "\" → Призупиняємо роботу...");
                    break;
                case "stop":
                    System.out.println("  \"" + command + "\" → Зупиняємо систему...");
                    break;
                case "restart":
                    System.out.println("  \"" + command + "\" → Перезапуск...");
                    break;
                default:
                    System.out.println("  \"" + command + "\" → Невідома команда!");
            }
        }
        System.out.println();

        // ============================================================
        //   Блок 3: switch з enum — світлофор
        // ============================================================
        System.out.println("=== Блок 3: switch з enum ===");

        TrafficLight[] signals = {TrafficLight.RED, TrafficLight.YELLOW, TrafficLight.GREEN};

        for (TrafficLight signal : signals) {
            // УВАГА: в case пишемо просто RED, а не TrafficLight.RED!
            switch (signal) {
                case RED:
                    System.out.println("  " + signal + " → Стій! Рух заборонено.");
                    break;
                case YELLOW:
                    System.out.println("  " + signal + " → Увага! Приготуйся.");
                    break;
                case GREEN:
                    System.out.println("  " + signal + " → Можна їхати!");
                    break;
            }
        }
        System.out.println();

        // Ще приклад — пори року з enum
        Season currentSeason = Season.WINTER;

        switch (currentSeason) {
            case SPRING:
                System.out.println("Весна — час цвітіння!");
                break;
            case SUMMER:
                System.out.println("Літо — час відпусток!");
                break;
            case AUTUMN:
                System.out.println("Осінь — час збирати врожай!");
                break;
            case WINTER:
                System.out.println("Зима — час гарячого какао!");
                break;
        }
        System.out.println();

        // ============================================================
        //   Блок 4: Які типи підтримує switch
        // ============================================================
        System.out.println("=== Блок 4: Підтримувані типи у switch ===");

        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│   Підтримуються у switch:           │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  byte    — цілий тип (8 біт)       │");
        System.out.println("│  short   — цілий тип (16 біт)      │");
        System.out.println("│  int     — цілий тип (32 біт)      │");
        System.out.println("│  char    — символ (16 біт)          │");
        System.out.println("│  String  — рядок                    │");
        System.out.println("│  enum    — будь-який enum-тип       │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│   НЕ підтримуються:                 │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  long    — занадто великий          │");
        System.out.println("│  float   — дробовий тип             │");
        System.out.println("│  double  — дробовий тип             │");
        System.out.println("│  boolean — тільки 2 значення (if!)  │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.println();

        // Демонстрація з byte та short
        byte errorCode = 2;
        switch (errorCode) {
            case 0:
                System.out.println("byte switch: код 0 → OK");
                break;
            case 1:
                System.out.println("byte switch: код 1 → Попередження");
                break;
            case 2:
                System.out.println("byte switch: код 2 → Помилка");
                break;
        }

        // long, float, double, boolean — НЕ працюють у switch:
        // long bigNum = 100L;
        // switch (bigNum) { ... }  // НЕ СКОМПІЛЮЄТЬСЯ!
        //
        // double price = 9.99;
        // switch (price) { ... }   // НЕ СКОМПІЛЮЄТЬСЯ!
        //
        // boolean active = true;
        // switch (active) { ... }  // НЕ СКОМПІЛЮЄТЬСЯ — використовуй if!
    }
}
