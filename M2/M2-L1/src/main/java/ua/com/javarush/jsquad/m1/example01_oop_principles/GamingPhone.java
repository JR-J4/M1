package ua.com.javarush.jsquad.m1.example01_oop_principles;

/**
 * Ігровий смартфон — нащадок {@link Smartphone}.
 *
 * <p>Демонструє <b>УСПАДКУВАННЯ</b> (бере всі поля та методи батька)
 * та <b>ПОЛІМОРФІЗМ</b> (перевизначає метод {@code powerOn()}).</p>
 */
public class GamingPhone extends Smartphone {

    public GamingPhone(String model, String owner) {
        super(model, owner); // викликаємо конструктор батька
    }

    // Поліморфізм: той самий метод powerOn() поводиться інакше
    @Override
    public void powerOn() {
        System.out.println("  " + getModel() + " вмикається... 🎮 ІГРОВИЙ режим, RGB-підсвітка!");
    }
}
