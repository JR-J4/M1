package ua.com.javarush.jsquad.m1.example01_reflection_intro;

/**
 * Батьківський клас — потрібен, щоб показати метод {@code getSuperclass()}.
 */
public abstract class Gadget {

    protected String brand;

    protected Gadget(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }
}
