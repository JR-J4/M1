package ua.com.javarush.jsquad.m1.example05_inner_classes;

/**
 * Лекція 14: Класи та static
 * <hr>
 * <h3>Тема: Внутрішні та статичні (вкладені) класи</h3>
 *
 * <p>У Java можна оголошувати класи усередині класів.
 * Внутрішні класи бувають <b>статичні</b> та <b>нестатичні</b>.</p>
 *
 * <p><b>Статичний вкладений клас (static nested class):</b></p>
 * <pre>
 *   class Зовнішній {
 *       static class Вкладений { }
 *   }
 *   // Створення: new Зовнішній.Вкладений()
 * </pre>
 *
 * <p><b>Внутрішній клас (inner class):</b></p>
 * <pre>
 *   class Зовнішній {
 *       class Внутрішній { }
 *   }
 *   // Створення: зовнішнійОб'єкт.new Внутрішній()
 * </pre>
 *
 * <p><b>Ключова різниця:</b> статичний вкладений клас НЕ має доступу
 * до нестатичних полів зовнішнього класу. Внутрішній клас — МАЄ.</p>
 *
 * <p><b>Аналогія з життя:</b>
 * Комп'ютер: CPU (процесор) — статичний компонент, його можна купити окремо,
 * він не залежить від конкретного комп'ютера. А вентилятор (Fan) — внутрішній,
 * він працює лише коли комп'ютер увімкнений, тобто залежить від стану зовнішнього об'єкта.</p>
 *
 * <p><b>Реальне застосування:</b>
 * Статичні вкладені класи — для допоміжних структур (Builder, Entry, Node).
 * Внутрішні класи — коли потрібен доступ до стану зовнішнього об'єкта (ітератори, обробники подій).</p>
 */
public class Example05_InnerClasses {

    public static void main(String[] args) {

        // === 1. Статичний вкладений клас (static nested class) ===
        // Сценарій: CPU можна створити незалежно від комп'ютера

        System.out.println("=== Статичний вкладений клас — CPU ===");

        // Створення: new ЗовнішнійКлас.ВкладенийКлас()
        // Не потрібен об'єкт Computer!
        Computer.CPU intel = new Computer.CPU("Intel i7-13700K", 16);
        Computer.CPU amd = new Computer.CPU("AMD Ryzen 9 7950X", 16);

        intel.showSpecs();
        amd.showSpecs();

        System.out.println();

        // === 2. Внутрішній клас (inner class) ===
        // Сценарій: вентилятор прив'язаний до конкретного комп'ютера

        System.out.println("=== Внутрішній клас — Fan ===");

        Computer myPC = new Computer("ASUS ROG");

        // Створення: зовнішнійОб'єкт.new ВнутрішнійКлас()
        Computer.Fan fan = myPC.new Fan(1200);

        // Вентилятор "бачить" стан комп'ютера (powerOn, brand)
        fan.spin();  // комп'ютер вимкнено — вентилятор не крутиться

        myPC.turnOn();
        fan.spin();  // комп'ютер увімкнено — вентилятор працює!

        System.out.println();

        // === 3. Різниця у створенні та доступі ===

        System.out.println("=== Порівняння: як створювати ===");
        System.out.println();
        System.out.println("  Статичний вкладений клас (static nested):");
        System.out.println("    Computer.CPU cpu = new Computer.CPU(...)");
        System.out.println("    - Не потребує екземпляра зовнішнього класу");
        System.out.println("    - НЕ має доступу до нестатичних полів зовнішнього класу");
        System.out.println();
        System.out.println("  Внутрішній клас (inner):");
        System.out.println("    Computer pc = new Computer(...)");
        System.out.println("    Computer.Fan fan = pc.new Fan(...)");
        System.out.println("    - ПОТРЕБУЄ екземпляр зовнішнього класу");
        System.out.println("    - МАЄ доступ до всіх полів зовнішнього класу");

        System.out.println();

        // Ще один приклад — інший комп'ютер зі своїм вентилятором
        System.out.println("=== Кожен вентилятор прив'язаний до свого комп'ютера ===");
        Computer laptop = new Computer("MacBook Pro");
        Computer.Fan laptopFan = laptop.new Fan(2000);

        laptop.turnOn();
        laptopFan.spin();  // MacBook Pro: обертається на 2000 RPM

        myPC.turnOff();
        fan.spin();         // ASUS ROG: вимкнено — вентилятор не працює
    }
}
