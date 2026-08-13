package ua.com.javarush.jsquad.m1.example05_constructors;

/**
 * Працівник із конструкторами всіх рівнів доступу —
 * саме на ньому видно різницю між {@code getConstructors()}
 * та {@code getDeclaredConstructors()}.
 */
public class Employee {

    private String name;
    private String position;
    private double salary;

    /** public — конструктор без аргументів. */
    public Employee() {
        this("Новий працівник", "Стажер", 0);
    }

    /** public — основний конструктор. */
    public Employee(String name, String position, double salary) {
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    /** protected — конструктор, що оголошує виняток. */
    protected Employee(String name, double salary) throws IllegalArgumentException {
        if (salary < 0) {
            throw new IllegalArgumentException("Зарплата не може бути від'ємною");
        }
        this.name = name;
        this.position = "Не вказано";
        this.salary = salary;
    }

    /** package-private — без модифікатора доступу. */
    Employee(String name) {
        this(name, "Не вказано", 0);
    }

    /** private — "закритий" конструктор для внутрішніх потреб. */
    private Employee(long internalId) {
        this.name = "Службовий запис #" + internalId;
        this.position = "SYSTEM";
        this.salary = 0;
    }

    /** Внутрішній (inner) клас — його конструктор має прихований параметр. */
    public class WorkBadge {

        private final String badgeNumber;

        public WorkBadge(String badgeNumber) {
            this.badgeNumber = badgeNumber;
        }

        @Override
        public String toString() {
            return "Перепустка " + badgeNumber + " (" + name + ")";
        }
    }

    @Override
    public String toString() {
        return name + " / " + position + " / " + salary;
    }
}
