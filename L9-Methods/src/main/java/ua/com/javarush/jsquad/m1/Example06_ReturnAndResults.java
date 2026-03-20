package ua.com.javarush.jsquad.m1;

/**
 * Лекція 9: Методи — Оператор return та значення
 *
 * {@code return;} миттєво завершує метод. {@code return значення;} ще й повертає дані викликачу.
 * Тип значення у виразі має збігатися з типом, що оголошено для методу.
 *
 * Аналогія: менеджер може завершити зустріч раніше (return) або ж повернутися з підписаним документом (return value).
 * Реальне застосування: валідації завершують метод, якщо дані некоректні, а сервіси розрахунку повертають суму, тариф або категорію клієнта.
 */
public class Example06_ReturnAndResults {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Ранній вихід із void-методу
        // ============================================================
        System.out.println("=== Перевірка бюджету ===");
        checkBudget(20000, 15000);
        checkBudget(20000, 35000); // вихід одразу після return

        System.out.println();

        // ============================================================
        //   Приклад 2: Метод обчислює значення і повертає результат
        // ============================================================
        System.out.println("=== Розрахунок бонусу ===");
        int bonus = calculateMonthlyBonus(40, 120_000);
        System.out.println("Щомісячний бонус: " + bonus + " грн");

        System.out.println();

        // ============================================================
        //   Приклад 3: Метод повертає категорію клієнта
        // ============================================================
        System.out.println("=== Категорія клієнта ===");
        System.out.println("Оборот 5 тис.: " + detectPriority(5000));
        System.out.println("Оборот 210 тис.: " + detectPriority(210_000));
    }

    private static void checkBudget(double monthlyBudget, double plannedCost) {
        if (plannedCost > monthlyBudget) {
            System.out.println("Витрата " + plannedCost + " грн не поміщається у бюджет. Зупиняємося.");
            return;
        }
        System.out.println("Закупівля на " + plannedCost + " грн схвалена.");
    }

    private static int calculateMonthlyBonus(int closedTasks, int revenue) {
        int bonusFromTasks = closedTasks * 50;
        int bonusFromRevenue = revenue > 100_000 ? 2000 : 0;
        return bonusFromTasks + bonusFromRevenue;
    }

    private static String detectPriority(int yearlyTurnover) {
        if (yearlyTurnover >= 200_000) {
            return "VIP";
        }
        if (yearlyTurnover >= 50_000) {
            return "Стандарт";
        }
        return "Базовий";
    }
}
