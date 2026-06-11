package ua.com.javarush.jsquad.m1.example02_abstraction;

/**
 * Коло — конкретна реалізація абстрактної {@link Figure}.
 * Дає власну реалізацію методу {@code area()}.
 */
public class Circle extends Figure {

    private final double radius;

    public Circle(double radius) {
        super("Коло");
        this.radius = radius;
    }

    // Конкретна формула площі кола: π · r²
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}
