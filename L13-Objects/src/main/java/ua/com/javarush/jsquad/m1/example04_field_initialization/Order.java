package ua.com.javarush.jsquad.m1.example04_field_initialization;

/**
 * Клас Order — замовлення в інтернет-магазині.
 *
 * Демонструє ініціалізацію полів стартовими значеннями
 * та подальше перевизначення в конструкторі.
 */
public class Order {

    // Поля з явною ініціалізацією (стартові значення)
    private String product;
    private int quantity;
    private double pricePerUnit = 999.99;  // стартове значення
    private double discount = 0.05;        // 5% знижка за замовчуванням

    public Order(String product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        // pricePerUnit і discount вже ініціалізовані стартовими значеннями!
    }

    public void showInfo() {
        double total = pricePerUnit * quantity * (1 - discount);
        System.out.println("Замовлення: " + product);
        System.out.println("  Кількість: " + quantity);
        System.out.println("  Ціна за одиницю: " + pricePerUnit + " грн");
        System.out.println("  Знижка: " + (discount * 100) + "%");
        System.out.println("  Загальна сума: " + total + " грн");
    }
}
