package ua.com.javarush.jsquad.m1.example05_getters_setters;

/**
 * Клас Product — товар у магазині.
 *
 * Всі поля private — доступ тільки через getter і setter.
 * Setter може містити валідацію — захист від некоректних значень.
 */
public class Product {

    // Всі поля — private (інкапсуляція)
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;

        setPrice(price);       // використовуємо setter з валідацією
        setQuantity(quantity); // використовуємо setter з валідацією
    }

    // === GETTER — отримати значення поля ===


    public String getName() {
        return name;
    }


    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    // === SETTER — встановити значення поля (з валідацією!) ===

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        if (price < 0) {
            this.price = 0;
        } else {
            this.price = price;
        }
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            System.out.println("  Помилка: кількість не може бути від'ємною! Встановлено 0.");
            this.quantity = 0;
        } else {
            this.quantity = quantity;
        }
    }

    public void showInfo() {
        System.out.println("  " + name + " | ціна: " + price + " грн | кількість: " + quantity + " шт.");
    }
}
