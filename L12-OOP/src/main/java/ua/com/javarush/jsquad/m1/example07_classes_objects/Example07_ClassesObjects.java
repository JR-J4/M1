package ua.com.javarush.jsquad.m1.example07_classes_objects;

import java.util.Scanner;

/**
 * Лекція 12: Типи даних. Ознайомлення з ООП
 * <hr>
 * <h3>Тема: Класи та об'єкти. Оператор new</h3>
 *
 * <p>Клас — це шаблон (креслення) для створення об'єктів.
 * Він визначає, як об'єкт виглядатиме і які функції матиме.
 * Кожен об'єкт є екземпляром якогось класу.</p>
 *
 * <p><b>Синтаксис:</b> {@code Клас змінна = new Клас(параметри);}</p>
 *
 * <h4>Клас vs Об'єкт:</h4>
 * <pre>
 *       КЛАС (шаблон/креслення)          ОБ'ЄКТИ (екземпляри)
 *  ┌───────────────────────┐
 *  │        Car            │       ┌─────────────────────┐
 *  │───────────────────────│       │ sedan               │
 *  │ brand: String         │──new──▶ brand = "Toyota"    │
 *  │ model: String         │  │    │ model = "Camry"     │
 *  │ year: int             │  │    │ year = 2023         │
 *  │ fuelLevel: double     │  │    └─────────────────────┘
 *  │ isRunning: boolean    │  │
 *  │───────────────────────│  │    ┌─────────────────────┐
 *  │ start()               │  │    │ suv                 │
 *  │ stop()                │──new──▶ brand = "Honda"     │
 *  │ drive(km)             │       │ model = "CR-V"      │
 *  │ showInfo()            │       │ year = 2024         │
 *  └───────────────────────┘       └─────────────────────┘
 *
 *  Один клас — багато об'єктів!
 *  Кожен об'єкт має власні значення полів.
 * </pre>
 *
 * <p><b>Аналогія з життя:</b>
 * Клас — як технічна документація на автомобіль.
 * Об'єкт — конкретний автомобіль, зібраний за цією документацією.
 * З одного креслення можна зібрати багато різних авто.</p>
 *
 * <p><b>Реальне застосування:</b>
 * Все в Java — це об'єкти. String, масив, Scanner — все створюється за шаблоном класу.</p>
 */
public class Example07_ClassesObjects {

    public static void main(String[] args) {

        // === 1. Створення об'єктів за допомогою new ===
        // Сценарій: автосалон — створюємо різні авто з одного класу


//        Scanner sc = new Scanner(System.in);

        Car sedan = new Car("Toyota", "CR-V", 2023);       // створюємо перший об'єкт

        Car test = new Car();



        Car svu = new Car("A", "B", 2021);



        sedan.getModel();
        svu.getModel();





        System.out.println("=== Створення об'єктів (автосалон) ===");
        sedan.showInfo();





        System.out.println("Два різних об'єкти, створені з одного класу Car!");

        System.out.println();

        // === 2. Виклик методів об'єкта ===
        // Сценарій: тест-драйв автомобіля

        System.out.println("=== Тест-драйв Toyota Camry ===");
        sedan.start();
        sedan.drive(100);     // проїхати 100 км
        sedan.drive(50);      // ще 50 км
        sedan.showInfo();
        sedan.stop();

        System.out.println();

        // === 3. Об'єкти незалежні один від одного ===

        System.out.println("=== Об'єкти незалежні ===");
        System.out.println("Toyota Camry:");
        sedan.showInfo();
        System.out.println("Honda CR-V:");
//        suv.showInfo();  // Honda CR-V не змінилася, хоча Toyota їздила

        System.out.println();

        // === 4. Об'єкт без палива ===
        // Сценарій: авто зі складу без палива

        Car emptyCar = new Car();
//        emptyCar.brand = "Nissan";
//        emptyCar.model = "Leaf";
//        emptyCar.year = 2022;
//        emptyCar.fuelLevel = 0;

        System.out.println("=== Авто без палива ===");
        emptyCar.start();  // не заведеться!

        System.out.println();

        // === 5. Стандартні об'єкти Java — теж створюються через new ===

        System.out.println("=== Стандартні об'єкти Java ===");
        String greeting = new String("Привіт!");   // String — це теж клас!
        int[] numbers = new int[5];                 // масив — теж об'єкт!

        System.out.println("String: " + greeting);
        System.out.println("int[5]: довжина масиву = " + numbers.length);
        System.out.println("Все в Java — це об'єкти класів!");
    }
}
