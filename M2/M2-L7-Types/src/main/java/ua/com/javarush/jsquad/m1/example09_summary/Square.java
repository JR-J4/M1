package ua.com.javarush.jsquad.m1.example09_summary;

/**
 * Квадрат — окремий випадок прямокутника, тому успадкований від {@link Rectangle}.
 * Демонструє, що ланцюжок успадкування може бути довшим за один рівень:
 * {@code Square -> Rectangle -> Figure}.
 */
public class Square extends Rectangle {

    public Square(double side) {
        super("Квадрат", side, side, FigureType.Square);
    }

    public double getSide() {
        return getWidth();
    }
}
