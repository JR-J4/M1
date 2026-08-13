package ua.com.javarush.jsquad.m1.example01_reflection_intro;

/**
 * Звичайний клас, який ми будемо "розглядати під мікроскопом" рефлексії.
 *
 * <p>Зверніть увагу: у самому класі немає нічого особливого. Рефлексія працює
 * з будь-яким класом — його не треба якось спеціально готувати.</p>
 */
public final class Smartphone extends Gadget implements Device, Comparable<Smartphone> {

    public static final String CATEGORY = "Електроніка";

    private String model;
    private double price;
    private boolean poweredOn;

    public Smartphone(String brand, String model, double price) {
        super(brand);
        this.model = model;
        this.price = price;
    }

    @Override
    public void turnOn() {
        poweredOn = true;
    }

    @Override
    public void turnOff() {
        poweredOn = false;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public boolean isPoweredOn() {
        return poweredOn;
    }

    private void secretDiagnostics() {
        System.out.println("   [приховану діагностику запущено]");
    }

    @Override
    public int compareTo(Smartphone other) {
        return Double.compare(this.price, other.price);
    }
}
