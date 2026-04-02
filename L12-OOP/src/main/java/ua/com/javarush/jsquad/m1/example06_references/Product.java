package ua.com.javarush.jsquad.m1.example06_references;

/**
 * Клас Product — модель товару в інтернет-магазині.
 * Демонструє різницю між примітивними полями та посилальними.
 */
public class Product {
    String name;       // посилальний тип — зберігає посилання на об'єкт String
    double price;      // примітивний тип — зберігає значення напряму
    int quantity;      // примітивний тип
    boolean available; // примітивний тип
}
