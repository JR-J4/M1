package ua.com.javarush.jsquad.m1.example02_abstraction;

/**
 * Прямокутник — ще одна конкретна реалізація абстрактної {@link Figure}.
 */
public class Rectangle extends Figure {

    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        super("Прямокутник");
        this.width = width;
        this.height = height;
    }

    // Власна формула площі прямокутника: ширина · висота
    @Override
    public double area() {
        return width * height;
    }
}
