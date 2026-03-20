package ua.com.javarush.jsquad.m1;

/**
 * Лекція 9: Методи — Константи
 *
 * Константа — це змінна, яку не можна змінити після ініціалізації. В Java використовується ключове слово {@code final}.
 * Прийнято іменувати константи великими літерами з підкресленнями.
 *
 * Аналогія: офіційний курс НБУ на день звіту — поки звіт формується, число незмінне.
 * Реальне застосування: податкові ставки, максимальні розміри буфера, назви станів робочого процесу.
 */
public class Example10_Constants {

    private static final double VAT_RATE = 0.2; // 20%
    private static final int MAX_BATCH_SIZE = 500;

    public static void main(String[] args) {

        String userName = "Test";

        // ============================================================
        //   Приклад 1: Глобальні константи класу
        // ============================================================
        System.out.println("=== Глобальні константи ===");
        double priceWithVat = applyVat(10_000);
        System.out.println("Ціна з ПДВ: " + priceWithVat + " грн (ставка " + VAT_RATE + ")");

        System.out.println();

        // ============================================================
        //   Приклад 2: Константи сценарію використання
        // ============================================================
        System.out.println("=== MAX_BATCH_SIZE ===");
        splitBatch(1200);

        System.out.println();

        // ============================================================
        //   Приклад 3: Локальна константа-налаштування
        // ============================================================
        System.out.println("=== Локальні final змінні ===");
        final int WORKING_DAYS = 21;
        System.out.println("Плановий фонд робочого часу: " + WORKING_DAYS + " днів");
    }

    private static double applyVat(double basePrice) {
        return basePrice + basePrice * VAT_RATE;
    }

    private static void splitBatch(int items) {
        int batches = (int) Math.ceil(items / (double) MAX_BATCH_SIZE);
        System.out.println("Партій потрібно: " + batches);
    }
}
