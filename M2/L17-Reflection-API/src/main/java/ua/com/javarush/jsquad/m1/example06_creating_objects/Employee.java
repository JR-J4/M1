package ua.com.javarush.jsquad.m1.example06_creating_objects;

/**
 * Клас із лекції: саме на ньому показано обидва способи створення об'єкта —
 * {@code Class.newInstance()} та {@code Constructor.newInstance()}.
 */
public class Employee {

    private String name;
    private String position;

    /** Конструктор без аргументів — потрібен для Class.newInstance(). */
    public Employee() {
        this.name = "Без імені";
        this.position = "Стажер";
    }

    public Employee(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Employee{" + name + ", " + position + "}";
    }
}
