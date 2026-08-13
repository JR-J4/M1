package ua.com.javarush.jsquad.m1.example09_summary;

/**
 * Прямокутник — спадкоємець {@link Figure}. Має власні поля {@code width}/{@code height}.
 */
public class Rectangle extends Figure {

    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        super("Прямокутник", FigureType.Rectangle);
        this.width = width;
        this.height = height;
    }

    // Конструктор для спадкоємців (напр. Square), які задають власну назву
    protected Rectangle(String name, double width, double height, FigureType type) {
        super(name, type);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double area() {
        return width * height;
    }
}
