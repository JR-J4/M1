package ua.com.javarush.jsquad.m1.example06_inheritance;

/**
 * Базовий клас Employee (Працівник).
 *
 * <p>Містить 80–90% того, що потрібно будь-якому працівнику: ім'я, базову
 * зарплату й метод представлення. Нащадки візьмуть це "безкоштовно" і додадуть своє.</p>
 */
public class Employee {

    protected String name;       // protected — щоб нащадки могли користуватись напряму
    protected double baseSalary;

    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }


    protected void test() {

    }

    // Метод, спільний для всіх працівників
    public void introduce() {
        System.out.println("  Я " + name + ", базова зарплата: " + baseSalary);
    }

    // Метод розрахунку зарплати — нащадки зможуть його доповнити
    public double calculateSalary() {
        return baseSalary;
    }

    public void asd(){
        
    }
}
