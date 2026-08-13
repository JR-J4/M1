package ua.com.javarush.jsquad.m1.example02_instanceof_inheritance;

/**
 * Качка — спадкоємець {@link Animal}, який ЩЕ Й реалізує інтерфейс {@link Swimmer}.
 * Тому качка одночасно є і Animal, і Duck, і Swimmer.
 */
public class Duck extends Animal implements Swimmer {
    // Duck IS an Animal and IS a Swimmer

    public Duck(String name) {
        super(name);
    }

    @Override
    public void move() {
        swim();
    }

    @Override
    public void accept(AnimalVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void swim() {
        System.out.println("  " + name + " пливе по озеру.");
    }
}
