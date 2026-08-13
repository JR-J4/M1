package ua.com.javarush.jsquad.m1.example09_summary;

/**
 * Абстрактний базовий клас {@code Figure} (Фігура) — спільний "предок" усіх фігур.
 * Площу кожна фігура рахує по-своєму (поліморфізм), тому метод {@code area()} — абстрактний.
 */
public abstract class Figure {

    protected String name;
    protected FigureType type;

    protected Figure(String name, FigureType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public FigureType getType() {
        return type;
    }

    // Кожен спадкоємець зобов'язаний порахувати свою площу
    public abstract double area();
}
