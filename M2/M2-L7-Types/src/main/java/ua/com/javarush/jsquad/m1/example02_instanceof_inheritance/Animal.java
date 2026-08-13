package ua.com.javarush.jsquad.m1.example02_instanceof_inheritance;

/**
 * Базовий клас {@code Animal} (Тварина) — спільний "предок" для всіх тварин.
 */
public abstract class Animal {

    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    abstract public void move();

    /**
     * Точка подвійної диспетчеризації. Викликається віртуально (за РЕАЛЬНИМ типом
     * об'єкта), а всередині кожен спадкоємець передає {@code this} у відвідувача —
     * і тоді компілятор обирає правильне перевантаження {@code visit(...)}.
     */
    abstract public void accept(AnimalVisitor visitor);

    public void eat() {
        System.out.println("  " + name + " їсть.");
    }
}
