package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;
import java.util.List;

/**
 * Лекція 20: Singleton, enum, switch — Підсумок
 *
 * Обʼєднуємо всі три теми лекції в одному реальному сценарії:
 * система замовлень кавʼярні "JavaCup".
 *
 *   • enum Coffee — меню з фіксованим набором напоїв та цінами
 *   • Singleton OrderManager — єдиний менеджер замовлень
 *   • switch — обробка кожного напою (час приготування, інструкції)
 *
 * Аналогія з життя: справжня кавʼярня. Меню — фіксоване (enum),
 * каса — одна на всю кавʼярню (Singleton), і бариста обирає рецепт
 * залежно від напою (switch).
 *
 * Реальне застосування: будь-яка система з фіксованим набором опцій
 * (enum), централізованим менеджером (Singleton) і розгалуженою
 * логікою обробки (switch).
 */
public class Example07_Summary {

    // ================================================================
    // Enum — меню кавʼярні з цінами
    // ================================================================
    enum Coffee {
        ESPRESSO("Еспресо", 45),
        LATTE("Лате", 65),
        CAPPUCCINO("Капучіно", 60),
        AMERICANO("Американо", 40),
        MOCHA("Мока", 75);

        private final String nameUa;
        private final int priceUah;

        Coffee(String nameUa, int priceUah) {
            this.nameUa = nameUa;
            this.priceUah = priceUah;
        }

        public String getNameUa() {
            return nameUa;
        }

        public int getPriceUah() {
            return priceUah;
        }
    }

    // ================================================================
    // Singleton — менеджер замовлень (один на всю кавʼярню)
    // ================================================================
    static class OrderManager {
        private static final OrderManager INSTANCE = new OrderManager();

        private final List<String> orders = new ArrayList<>();
        private int totalRevenue = 0;

        private OrderManager() {
        }

        public static OrderManager getInstance() {
            return INSTANCE;
        }

        public void addOrder(Coffee coffee, String customerName) {
            orders.add(customerName + " → " + coffee.getNameUa()
                    + " (" + coffee.getPriceUah() + " грн)");
            totalRevenue += coffee.getPriceUah();
        }

        public void printReport() {
            System.out.println("┌──────────────────────────────────────┐");
            System.out.println("│        Звіт кавʼярні JavaCup        │");
            System.out.println("├──────────────────────────────────────┤");
            for (int i = 0; i < orders.size(); i++) {
                System.out.printf("│  %d. %-33s│%n", i + 1, orders.get(i));
            }
            System.out.println("├──────────────────────────────────────┤");
            System.out.printf("│  Загалом замовлень: %-17d│%n", orders.size());
            System.out.printf("│  Загальна виручка:  %-14s грн│%n", totalRevenue);
            System.out.println("└──────────────────────────────────────┘");
        }

        public int getOrderCount() {
            return orders.size();
        }
    }

    // ================================================================
    // Метод з switch — обробка замовлення (час і інструкції)
    // ================================================================
    static void processOrder(Coffee coffee, String customer) {
        System.out.print("  Готуємо " + coffee.getNameUa() + " для " + customer + ": ");

        // switch на enum — визначаємо рецепт
        switch (coffee) {
            case ESPRESSO:
                System.out.println("30 сек — подвійний шот, без молока");
                break;
            case LATTE:
                System.out.println("2 хв — еспресо + спінене молоко");
                break;
            case CAPPUCCINO:
                System.out.println("2 хв — еспресо + молочна пінка");
                break;
            case AMERICANO:
                System.out.println("1 хв — еспресо + гаряча вода");
                break;
            case MOCHA:
                System.out.println("3 хв — еспресо + шоколад + молоко");
                break;
        }
    }

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Enum — переглядаємо меню
        // ============================================================
        System.out.println("=== Блок 1: Меню кавʼярні (enum) ===");

        System.out.println("Меню кавʼярні JavaCup:");
        for (Coffee coffee : Coffee.values()) {
            System.out.println("  • " + coffee.getNameUa()
                    + " — " + coffee.getPriceUah() + " грн");
        }
        System.out.println("Кількість позицій: " + Coffee.values().length);
        System.out.println();

        // ============================================================
        //   Блок 2: Singleton — єдиний менеджер замовлень
        // ============================================================
        System.out.println("=== Блок 2: Менеджер замовлень (Singleton) ===");

        // Усі замовлення йдуть через один і той самий менеджер
        OrderManager manager1 = OrderManager.getInstance();
        OrderManager manager2 = OrderManager.getInstance();
        System.out.println("manager1 == manager2? " + (manager1 == manager2)); // true
        System.out.println("Одна каса на всю кавʼярню — Singleton працює!");
        System.out.println();

        // ============================================================
        //   Блок 3: Switch — готуємо замовлення
        // ============================================================
        System.out.println("=== Блок 3: Обробка замовлень (switch) ===");

        // Імітуємо замовлення від різних клієнтів
        Coffee[] orders = {
                Coffee.LATTE,
                Coffee.ESPRESSO,
                Coffee.MOCHA,
                Coffee.AMERICANO,
                Coffee.CAPPUCCINO
        };
        String[] customers = {"Олена", "Андрій", "Марія", "Дмитро", "Петро"};

        for (int i = 0; i < orders.length; i++) {
            processOrder(orders[i], customers[i]);
        }
        System.out.println();

        // ============================================================
        //   Блок 4: Все разом — повний цикл
        // ============================================================
        System.out.println("=== Блок 4: Повний цикл роботи ===");

        // Додаємо замовлення через Singleton менеджер
        for (int i = 0; i < orders.length; i++) {
            OrderManager.getInstance().addOrder(orders[i], customers[i]);
        }

        // Друкуємо звіт — усі замовлення в одному місці
        OrderManager.getInstance().printReport();
        System.out.println();

        // ============================================================
        //   Блок 5: Довідник — коли що використовувати
        // ============================================================
        System.out.println("=== Блок 5: Коли що використовувати ===");

        System.out.println("┌──────────┬──────────────────────────┬────────────────────────────┐");
        System.out.println("│ Концепція│ Коли використовувати     │ Приклад                    │");
        System.out.println("├──────────┼──────────────────────────┼────────────────────────────┤");
        System.out.println("│ enum     │ Фіксований набір значень │ Дні тижня, статуси, ролі   │");
        System.out.println("│          │ відомий на етапі компіл. │ Coffee, Season, OrderStatus│");
        System.out.println("├──────────┼──────────────────────────┼────────────────────────────┤");
        System.out.println("│ Singleton│ Потрібен рівно один      │ Logger, Config, Cache,     │");
        System.out.println("│          │ екземпляр на всю програму│ OrderManager, DB Connection│");
        System.out.println("├──────────┼──────────────────────────┼────────────────────────────┤");
        System.out.println("│ switch   │ Розгалуження по значенню │ Обробка команд, меню,      │");
        System.out.println("│          │ (int/char/String/enum)   │ рецепти, маршрутизація      │");
        System.out.println("└──────────┴──────────────────────────┴────────────────────────────┘");
    }
}
