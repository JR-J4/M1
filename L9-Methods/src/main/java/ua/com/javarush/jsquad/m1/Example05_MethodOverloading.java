package ua.com.javarush.jsquad.m1;

/**
 * Лекція 9: Методи — Перевантаження
 *
 * Перевантаження дозволяє мати однакову назву методу з різними наборами параметрів (сигнатурами).
 * Синтаксис: достатньо змінити типи чи кількість параметрів, і компілятор обере відповідний варіант.
 *
 * Аналогія: один номер служби підтримки слухає різні сценарії — ви натискаєте 1, 2 або 3, і система знає, що робити.
 * Реальне застосування: API, де метод `send` може приймати рядок, рядок і вкладення, або список адресатів.
 */
public class Example05_MethodOverloading {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Рахунок із різною деталізацією
        // ============================================================
        System.out.println("=== Різні варіанти printInvoice ===");
        printInvoice("ТОВ \"Сонячна Енергія\"");
        printInvoice("ТОВ \"Сонячна Енергія\"", 12500);
        printInvoice("ТОВ \"Сонячна Енергія\"", 12500, 7);

        System.out.println();

        // ============================================================
        //   Приклад 2: Планування обслуговування без/з годиною
        // ============================================================
        System.out.println("=== scheduleMaintenance з різними параметрами ===");
        scheduleMaintenance(10);
        scheduleMaintenance(10, 21);

        System.out.println();

        // ============================================================
        //   Приклад 3: Одна назва методу повертає значення
        // ============================================================
        System.out.println("=== deliveryCost() обчислює різні сценарії ===");
        System.out.println("Міські замовлення: " + deliveryCost(5));
        System.out.println("Міжміські замовлення: " + deliveryCost(5, 380));

        test(1, 2d);

        test(2d, 3);

    }


    private static void test(int a, double b) {
        System.out.println("INT");
    }

    private static void test(double a, int b) {
        System.out.println("FLOAT");
    }








    private static void printInvoice(String client) {
        System.out.println("Рахунок для клієнта " + client);
    }

    private static void printInvoice(String client, double amount) {
        System.out.println("Рахунок для " + client + " на суму " + amount + " грн");
    }

    private static void printInvoice(String client, double amount, int discountPercent) {
        double discounted = amount - amount * discountPercent / 100;
        System.out.println("Рахунок для " + client + " (знижка " + discountPercent
                + "%): до сплати " + discounted + " грн");
    }

    private static void scheduleMaintenance(int dayOfMonth) {
        System.out.println("Плануємо технічне обслуговування на " + dayOfMonth + " число");
    }

    private static void scheduleMaintenance(int dayOfMonth, int hour) {
        System.out.println("Плануємо технічне обслуговування на " + dayOfMonth + " число о " + hour + ":00");
    }

    private static double deliveryCost(int packages) {
        final double cityRate = 35;
        return packages * cityRate;
    }

    private static double deliveryCost(int packages, double distanceKm) {
        final double base = packages * 30;
        final double distanceFee = distanceKm * 2.5;
        return base + distanceFee;
    }
}
