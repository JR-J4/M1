package ua.com.javarush.jsquad.m1.example08_oop_principles;

/**
 * Лекція 12: Типи даних. Ознайомлення з ООП
 * <hr>
 * <h3>Тема: 4 принципи ООП — Абстракція, Інкапсуляція, Успадкування, Поліморфізм</h3>
 *
 * <p>ООП (Об'єктно-Орієнтоване Програмування) — парадигма, де програма
 * складається з об'єктів, що взаємодіють між собою.</p>
 *
 * <h4>Ієрархія класів у цьому прикладі:</h4>
 * <pre>
 *                  ┌──────────────────┐
 *                  │     Vehicle      │
 *                  │──────────────────│
 *                  │ - brand: String  │
 *                  │ - model: String  │
 *                  │ - maxSpeed: int  │
 *                  │ - engineOn: bool │
 *                  │──────────────────│
 *                  │ + startEngine()  │
 *                  │ + stopEngine()   │
 *                  │ + showInfo()     │
 *                  └────────┬─────────┘
 *                           │ extends
 *              ┌────────────┴────────────┐
 *              │                         │
 *   ┌──────────┴─────────┐    ┌──────────┴─────────┐
 *   │    SportsCar        │    │      Truck          │
 *   │────────────────────│    │────────────────────│
 *   │ hasTurbo: boolean  │    │ cargoCapacity: dbl │
 *   │────────────────────│    │────────────────────│
 *   │ + startEngine() ⟲  │    │ + startEngine() ⟲  │
 *   │ + activateTurbo()  │    │ + loadCargo()      │
 *   └────────────────────┘    └────────────────────┘
 *         ⟲ = перевизначений метод (поліморфізм)
 * </pre>
 *
 * <h4>4 принципи ООП:</h4>
 * <pre>
 *  ┌────────────────────────────────────────────────────────┐
 *  │                      О О П                             │
 *  │                                                        │
 *  │  ┌─────────────┐  ┌──────────────┐                     │
 *  │  │ АБСТРАКЦІЯ  │  │ ІНКАПСУЛЯЦІЯ │                     │
 *  │  │ Виділяємо   │  │ Ховаємо дані │                     │
 *  │  │ важливе     │  │ за методами  │                     │
 *  │  └─────────────┘  └──────────────┘                     │
 *  │                                                        │
 *  │  ┌──────────────┐  ┌──────────────┐                    │
 *  │  │УСПАДКУВАННЯ  │  │ ПОЛІМОРФІЗМ  │                    │
 *  │  │ Нащадок бере │  │ Один метод — │                    │
 *  │  │ від батька   │  │ різна логіка │                    │
 *  │  └──────────────┘  └──────────────┘                    │
 *  └────────────────────────────────────────────────────────┘
 * </pre>
 *
 * <h4>Поліморфізм — startEngine():</h4>
 * <pre>
 *  Vehicle:   "двигун запущено"
 *  SportsCar: "натиснуто кнопку START. VROOOOM!"  ← інша реалізація!
 *  Truck:     "повернуто ключ запалювання"         ← інша реалізація!
 * </pre>
 *
 * <p><b>Аналогія з життя:</b>
 * Автосалон продає різні типи авто: спортивні та вантажні.
 * Всі вони — транспортні засоби (Vehicle), мають спільні характеристики
 * (марка, модель, швидкість), але заводяться по-різному.</p>
 */
public class Example08_OopPrinciples {

    public static void main(String[] args) {

        // === 1. АБСТРАКЦІЯ ===
        // Ми створили клас Vehicle з полями brand, model, maxSpeed.
        // Ми НЕ додали колір, вагу, кількість дверей — абстрагувалися від деталей.

        System.out.println("=== 1. АБСТРАКЦІЯ ===");
        System.out.println("Реальний автомобіль має сотні характеристик.");
        System.out.println("Ми обрали лише важливі для нашої задачі:");
        System.out.println();
        System.out.println("  Реальний автомобіль           Клас Vehicle");
        System.out.println("  ┌───────────────────┐         ┌──────────────────┐");
        System.out.println("  │ марка             │ ──────> │ brand    ✔       │");
        System.out.println("  │ модель            │ ──────> │ model    ✔       │");
        System.out.println("  │ макс. швидкість   │ ──────> │ maxSpeed ✔       │");
        System.out.println("  │ колір             │ ──X     │                  │");
        System.out.println("  │ вага              │ ──X     │ Зайве відкинуто! │");
        System.out.println("  │ кількість дверей  │ ──X     │                  │");
        System.out.println("  │ матеріал сидінь   │ ──X     │                  │");
        System.out.println("  └───────────────────┘         └──────────────────┘");

        System.out.println();

        // === 2. ІНКАПСУЛЯЦІЯ ===
        // Поля Vehicle оголошені як private — доступ тільки через getter/setter.
        // Не можна встановити від'ємну швидкість!

        System.out.println("=== 2. ІНКАПСУЛЯЦІЯ ===");

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Volkswagen");
        vehicle.setModel("Golf");
        vehicle.setMaxSpeed(220);

        // vehicle.maxSpeed = -100;  // ПОМИЛКА КОМПІЛЯЦІЇ! Поле private!
//        vehicle.setMaxSpeed(-100);   // Метод перевіряє значення і відхиляє від'ємне
        vehicle.showInfo();          // maxSpeed залишився 220

        System.out.println("Інкапсуляція захищає дані від некоректних змін!");

        System.out.println();

        // === 3. УСПАДКУВАННЯ ===
        // SportsCar та Truck успадковують (extends) Vehicle.
        // Вони отримують всі поля та методи батька + додають свої.

        System.out.println("=== 3. УСПАДКУВАННЯ ===");
        System.out.println();
        System.out.println("              ┌──────────────┐");
        System.out.println("              │   Vehicle    │  <- батьківський клас");
        System.out.println("              │  brand       │");
        System.out.println("              │  model       │");
        System.out.println("              │  maxSpeed    │");
        System.out.println("              └──────┬───────┘");
        System.out.println("                     │ extends");
        System.out.println("            ┌────────┴────────┐");
        System.out.println("            │                 │");
        System.out.println("   ┌────────┴───────┐  ┌──────┴─────────┐");
        System.out.println("   │  SportsCar     │  │     Truck      │");
        System.out.println("   │  + hasTurbo    │  │ + cargoCapacity│");
        System.out.println("   │  + activateT() │  │ + loadCargo()  │");
        System.out.println("   └────────────────┘  └────────────────┘");
        System.out.println();

        SportsCar sports = new SportsCar();
        sports.setBrand("Ferrari");
        sports.setModel("F40");
        sports.setMaxSpeed(324);
        sports.hasTurbo = false;   // власне поле SportsCar

        Truck truck = new Truck();
        truck.setBrand("Volvo");
        truck.setModel("FH16");
        truck.setMaxSpeed(120);
        truck.cargoCapacity = 20.0; // власне поле Truck

        System.out.println("SportsCar успадкував від Vehicle:");
        sports.showInfo();  // метод showInfo() від Vehicle працює!

        System.out.println("Truck успадкував від Vehicle:");
        truck.showInfo();   // той самий метод працює і для вантажівки!

        System.out.println();


        // === 4. ПОЛІ МОРФІЗМ ===
        // Один і той самий метод startEngine() працює по-різному
        // залежно від типу об'єкта.

        System.out.println("=== 4. ПОЛІМОРФІЗМ ===");
        System.out.println("Один метод startEngine() — різна поведінка:");
        System.out.println();
        System.out.println("  startEngine()");
        System.out.println("       │");
        System.out.println("       ├── Vehicle:   \"двигун запущено\"");
        System.out.println("       ├── SportsCar: \"натиснуто кнопку START. VROOOOM!\"");
        System.out.println("       └── Truck:     \"повернуто ключ запалювання\"");
        System.out.println();
        System.out.println("Запускаємо:");

        vehicle.startEngine();   // Звичайне: "двигун запущено"
        sports.startEngine();    // Спортивне: "натиснуто кнопку START. VROOOOM!"
        truck.startEngine();     // Вантажне: "повернуто ключ запалювання"

        System.out.println();
        System.out.println("Один метод — три різні поведінки! Це і є поліморфізм.");

        System.out.println();

        // === 5. Все разом — реальний сценарій ===

        System.out.println("=== Автопарк компанії ===");

        // Масив батьківського типу може зберігати об'єкти нащадків!
        Vehicle[] fleet = new Vehicle[3];
        fleet[0] = vehicle;   // звичайний автомобіль
        fleet[1] = sports;    // спортивний
        fleet[2] = truck;     // вантажний

        // Поліморфізм у дії: один цикл, різна поведінка
        for (Vehicle v : fleet) {
            v.startEngine();  // кожен заводиться по-своєму!
        }

        System.out.println();

        // Додаткові можливості нащадків
        sports.activateTurbo();
        truck.loadCargo(15.0);
        truck.loadCargo(25.0);  // перевантаження!
    }
}
