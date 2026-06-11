package ua.com.javarush.jsquad.m1.example06_inheritance;

import java.util.ArrayList;

/**
 * Модуль 2. Рівень 1. ООП: інкапсуляція, поліморфізм
 * <hr>
 * <h3>Тема: Успадкування</h3>
 *
 * <p>У програмуванні є можливість створювати один клас на основі іншого. Новий клас стає
 * <b>нащадком</b> того класу, який існує. Це дуже вигідно, коли є клас, що містить
 * 80–90% потрібних нам даних та методів.</p>
 *
 * <p>Ми просто оголошуємо клас, який нам підходить, <b>батьком</b> нашого нового класу
 * (ключове слово {@code extends}), і тоді в новому класі автоматично з'являються
 * всі дані та методи батьківського класу.</p>
 *
 * <pre>
 *                  ┌──────────────────┐
 *                  │     Employee     │  батько (80–90% спільного)
 *                  │  name            │
 *                  │  baseSalary      │
 *                  │  introduce()     │
 *                  │  calculateSalary │
 *                  └────────┬─────────┘
 *                  extends  │
 *              ┌────────────┴────────────┐
 *      ┌───────┴────────┐        ┌───────┴────────┐
 *      │   Developer    │        │    Manager     │
 *      │ + closedTasks  │        │ + teamSize     │
 *      │ + writeCode()  │        │ + holdMeeting()│
 *      └────────────────┘        └────────────────┘
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> штатний розпис компанії. "Працівник" — загальна посада.
 * "Розробник" і "Менеджер" — це працівники з додатковими обов'язками. Усе спільне
 * (ім'я, зарплата) описано один раз у "Працівнику".</p>
 *
 * <p><b>super(...)</b> у конструкторі нащадка викликає конструктор батька, щоб
 * ініціалізувати успадковану частину.</p>
 */
public class Example06_Inheritance {

    public static void main(String[] args) {

        Developer dev = new Developer("Олег", 1000, 12);
        Manager manager = new Manager("Світлана", 1500, 5);


        Employee test = new Employee("Test", 1000);
        Employee testDev = new Developer("Test", 1000, 20);


        ArrayList<Employee> employees = new ArrayList<>();

        employees.add(test);
        employees.add(testDev);

        for (Employee employee : employees) {
            System.out.println(employee.calculateSalary());
        }



        System.out.println(dev.name);
        System.out.println(dev.baseSalary);

        // === 1. Нащадки УСПАДКУВАЛИ метод introduce() від Employee ===
        System.out.println("=== Успадкований метод introduce() ===");
        dev.introduce();     // код методу написано лише в Employee!
        manager.introduce(); // і тут — той самий метод батька
        System.out.println();

        // === 2. У кожного нащадка є СВОЇ методи ===
        System.out.println("=== Власні методи нащадків ===");
        dev.writeCode();      // є тільки в Developer
        manager.holdMeeting(); // є тільки в Manager
        System.out.println();

        // === 3. Нащадки доповнили розрахунок зарплати ===
        System.out.println("=== Розрахунок зарплати (кожен по-своєму) ===");
        System.out.println("  Розробник: " + dev.calculateSalary() + " (база + бонус за задачі)");
        System.out.println("  Менеджер:  " + manager.calculateSalary() + " (база + бонус за команду)");
        System.out.println();

        System.out.println("Успадкування = взяли готові 80–90% від батька,");
        System.out.println("дописали лише те, що відрізняє нащадка.");
    }
}
