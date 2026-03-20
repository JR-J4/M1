package ua.com.javarush.jsquad.m1;

/**
 * Лекція 9: Методи — Ключове слово static
 *
 * {@code static} прив'язує метод або поле до класу, а не до конкретного об'єкта.
 * Викликаємо такі методи через назву класу: {@code ClassName.methodName()}.
 *
 * Аналогія: корпоративний чат із загальним оголошенням — неважливо, хто відкриє чат, повідомлення одне для всіх.
 * Реальне застосування: утилітарні методи (конвертери, валідатори) та глобальні лічильники/конфігурації.
 */
public class Example08_StaticKeyword {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Статичний утилітарний метод
        // ============================================================
        System.out.println("=== CurrencyConverter ===");
        double usd = CurrencyConverter.uahToUsd(25_000, 38.2);
        System.out.println("25 000 грн = " + usd + " $");

        System.out.println();

        // ============================================================
        //   Приклад 2: Статичне поле як глобальний лічильник
        // ============================================================
        System.out.println("=== Генерація ID ===");
        System.out.println("Новий ідентифікатор: " + UserIdGenerator.next());
        System.out.println("Ще один ідентифікатор: " + UserIdGenerator.next());

        System.out.println();

        // ============================================================
        //   Приклад 3: Статичний метод не бачить нестатичні поля напряму
        // ============================================================
        System.out.println("=== ProductionMonitor: статичний метод працює з інстансом ===");
        ProductionMonitor monitor = new ProductionMonitor("Лінія #3");
        ProductionMonitor.printState(monitor);
    }

    static class CurrencyConverter {
        static double uahToUsd(double amountUah, double rate) {
            return Math.round(amountUah / rate * 100) / 100.0;
        }
    }

    static class UserIdGenerator {
        private static int current = 1000;

        static int next() {
            current += 1;
            return current;
        }
    }

    static class ProductionMonitor {
        private final String lineName;
        private int processedBatches = 42;

        ProductionMonitor(String lineName) {
            this.lineName = lineName;
        }

        static void printState(ProductionMonitor monitor) {
            // Статичний метод отримує дані через параметри, а не безпосередньо
            System.out.println(monitor.lineName + ": оброблено " + monitor.processedBatches + " партій");
        }
    }
}
