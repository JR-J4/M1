package ua.com.javarush.jsquad.m1.example09_summary;

/**
 * Коло — спадкоємець {@link Figure}. Має власне поле {@code radius}
 * та власний метод {@code getRadius()}, якого немає в базовому типі.
 */
public class Circle extends Figure {

    private final double radius;

    public Circle(double radius) {
        super("Коло", FigureType.CIRCLE);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}
