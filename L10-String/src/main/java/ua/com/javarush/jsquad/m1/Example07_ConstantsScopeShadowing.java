package ua.com.javarush.jsquad.m1;

/**
 * Лекцiя 10: Константи, область видимостi та затiнення змiнних
 *
 * Константи оголошуються через ключовi слова final, iменуються у SNAKE_CASE.
 * Область видимостi визначає, де можна звертатися до змiнної.
 * Затiнення — коли локальна змiнна має те саме iм'я, що й змiнна класу.
 *
 * Аналогiя: константа — табличка з тарифом, яка не змiнюється; область видимостi —
 * освiтлений лiхтарем простiр; затiнення — коли новий плакат перекриває старий.
 *
 * Реальне застосування: тарифнi плани, розрахунок ПДВ, доступ до змiнних у методах
 * сервiсiв та уникнення помилок через однаковi iмена.
 */
public class Example07_ConstantsScopeShadowing {

    // Константа класу
    private static final double VAT_RATE = 0.2; // 20% ПДВ
    private static final int FREE_DELIVERY_THRESHOLD = 1500;

    // Змінні класу
    private int monthlyLimit = 5000;
    private double totalSpent = 0;

    public static void main(String[] args) {

        Example07_ConstantsScopeShadowing account = new Example07_ConstantsScopeShadowing();

        account.processOrder("Ноутбук", 32000);
        System.out.println();

        account.processOrder("Книга", 400);
        System.out.println();

        account.demoScope(300);
        System.out.println();

        account.demoShadowing(1000);
    }

    // ============================================================
    //   Приклад 1: Робота з константами у платіжному сервісі
    // ============================================================
    private void processOrder(String product, double price) {
        System.out.println("=== Обробка замовлення: " + product + " ===");
        System.out.println("Базова ціна: " + price + " грн");

        double vat = price * VAT_RATE;
        double finalPrice = price + vat;

        System.out.println("ПДВ (" + VAT_RATE * 100 + "%): " + vat + " грн");
        System.out.println("До сплати: " + finalPrice + " грн");

        boolean freeDelivery = finalPrice >= FREE_DELIVERY_THRESHOLD;
        System.out.println("Безкоштовна доставка: " + (freeDelivery ? "так" : "ні"));

        totalSpent += finalPrice;
        System.out.println("Загальні витрати клієнта: " + totalSpent + " грн");
    }

    // ============================================================
    //   Приклад 2: Область видимості — лічильники всередині блоку
    // ============================================================
    private void demoScope(int bonus) {
        System.out.println("=== Область видимості ===");

        if (bonus > 0) {
            int appliedBonus = Math.min(bonus, 500);
            System.out.println("Застосовано бонус: " + appliedBonus);
        }

        // appliedBonus тут недоступний — змінна існує лише всередині блоку if
        // System.out.println(appliedBonus); // помилка компіляції
    }

    // ============================================================
    //   Приклад 3: Затінення змінних
    // ============================================================
    private void demoShadowing(int monthlyLimit) {
        System.out.println("=== Затiнення змiнних ===");
        System.out.println("Вхідний параметр monthlyLimit = " + monthlyLimit);
        System.out.println("Поточний ліміт у полі класу = " + this.monthlyLimit);

        int totalSpent = 2000; // затінює поле totalSpent
        System.out.println("Локальна змінна totalSpent = " + totalSpent);
        System.out.println("Поле класу totalSpent = " + this.totalSpent);

        this.monthlyLimit -= monthlyLimit;
        System.out.println("Новий ліміт у полі класу (після зменшення): " + this.monthlyLimit);
    }
}
