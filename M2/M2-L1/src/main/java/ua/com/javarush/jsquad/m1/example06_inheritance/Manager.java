package ua.com.javarush.jsquad.m1.example06_inheritance;

/**
 * Менеджер — ще один нащадок {@link Employee}.
 * Додає розмір команди та бонус за керівництво.
 */
public class Manager extends Employee {

    private final int teamSize;

    public Manager(String name, double baseSalary, int teamSize) {
        super(name, baseSalary);
        this.teamSize = teamSize;
    }

    public void holdMeeting() {
        System.out.println("  " + name + " проводить нараду з командою з " + teamSize + " осіб 🗣");
    }

    @Override
    public double calculateSalary() {
        return baseSalary + teamSize * 100; // бонус за розмір команди
    }
}
