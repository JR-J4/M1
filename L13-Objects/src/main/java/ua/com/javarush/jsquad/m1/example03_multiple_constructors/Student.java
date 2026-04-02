package ua.com.javarush.jsquad.m1.example03_multiple_constructors;

/**
 * Клас Student — демонстрація декількох конструкторів.
 *
 * Клас може мати кілька конструкторів з різними параметрами.
 * Компілятор сам обирає потрібний конструктор за типом та кількістю аргументів.
 */
public class Student {

    private String name;
    private int age;
    private String group;
    private double gpa;   // середній бал

    // Конструктор 1: повний — всі параметри
    public Student(String name, int age, String group, double gpa) {
        this.name = name;
        this.age = age;
        this.group = group;
        this.gpa = gpa;
        System.out.println("  [Конструктор 1 — повний] Створено студента: " + name);
    }

    // Конструктор 2: базовий — тільки ім'я та група
    public Student(String name, String group) {
        this.name = name;
        this.age = 18;        // вік за замовчуванням
        this.group = group;
        this.gpa = 0.0;       // середній бал ще не відомий
        System.out.println("  [Конструктор 2 — базовий] Створено студента: " + name);
    }

    // Конструктор 3: за замовчуванням — без параметрів
    public Student() {
        this.name = "Невідомий";
        this.age = 0;
        this.group = "Не призначено";
        this.gpa = 0.0;
        System.out.println("  [Конструктор 3 — за замовчуванням] Створено порожнього студента");
    }

    public void showInfo() {
        System.out.println("  " + name + ", вік: " + age + ", група: " + group + ", бал: " + gpa);
    }
}
