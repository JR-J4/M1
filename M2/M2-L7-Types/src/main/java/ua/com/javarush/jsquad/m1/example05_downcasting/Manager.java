package ua.com.javarush.jsquad.m1.example05_downcasting;

/**
 * Менеджер — спадкоємець {@link Employee}. Має власний метод {@code holdMeeting()}.
 */
public class Manager extends Employee {

    public Manager(String name) {
        super(name);
    }

    public void holdMeeting() {
        System.out.println("  " + name + " проводить нараду.");
    }

    @Override
    public void work() {
        holdMeeting();
    }
}
