package ua.com.javarush.jsquad.m1.example06_inheritance;

/**
 * Розробник — нащадок {@link Employee}.
 *
 * <p>Бере від батька name, baseSalary, introduce() і додає СВОЄ поле — premium
 * (надбавка за кількість закритих задач).</p>
 */
public class Developer extends Employee {

    private final int closedTasks; // власне поле нащадка

    public Developer(String name, double baseSalary, int closedTasks) {
        super(name, baseSalary); // обов'язково ініціалізуємо частину від батька
        this.closedTasks = closedTasks;
    }

    // Власний метод, якого немає в батька
    public void writeCode() {
        System.out.println("  " + name + " пише код... 💻 (" + closedTasks + " задач закрито)");
        super.test();
        this.test();

    }

    // Доповнюємо розрахунок: база + бонус за задачі
    @Override
    public double calculateSalary() {
        return 0d;
    }
}
