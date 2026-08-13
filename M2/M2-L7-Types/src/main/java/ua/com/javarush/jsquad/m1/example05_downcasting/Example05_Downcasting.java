package ua.com.javarush.jsquad.m1.example05_downcasting;

/**
 * Модуль 2. Рівень 7. Приведення типів
 * <hr>
 * <h3>Тема: Звуження типу (downcasting) — рух ланцюжком успадкування ВНИЗ</h3>
 *
 * <p>Рух ВНИЗ (від загального типу до конкретного типу об'єкта) — це <b>звуження типу</b>
 * (воно ж — низхідне перетворення, або <b>downcasting</b>). На відміну від розширення,
 * звуження треба писати <b>явно</b> — оператором приведення {@code (Тип)}.</p>
 *
 * <pre>
 *   Employee e = new Developer("Аня");  // розширення (автоматично)
 *   Developer d = (Developer) e;        // звуження (явно!) — знову бачимо writeCode()
 * </pre>
 *
 * <p><b>Небезпека:</b> якщо у змінній лежить об'єкт НЕ того класу, до якого звужуємо —
 * буде виняток {@code ClassCastException}. Тому перед звуженням тип перевіряють через
 * {@code instanceof}.</p>
 *
 * <p><b>Аналогія з життя:</b> у списку "співробітники" всі однакові. Але щоб дати
 * завдання "напиши код", треба спершу впевнитися, що перед тобою саме розробник,
 * і лише тоді звертатися до нього як до розробника.</p>
 *
 * <p><b>Реальне застосування:</b> дістати з узагальненого списку конкретний підтип,
 * щоб скористатися його спеціальними можливостями.</p>
 */
public class Example05_Downcasting {

    public static void main(String[] args) {

        // === 1. Звуження повертає доступ до спеціальних методів ===
        System.out.println("=== Downcasting: Employee -> Developer ===");
        Employee employee = new Developer("Аня"); // upcasting (автоматично)
        // employee.writeCode();                   // ✖ через тип Employee метод не видно
        Developer developer = (Developer) employee; // явне звуження
        developer.writeCode();                       // ✔ тепер метод доступний
        System.out.println();

        // === 2. Безпечне звуження: спершу перевірка instanceof ===
        System.out.println("=== Перевірка перед звуженням ===");
        Employee[] staff = {
                new Developer("Богдан"),
                new Manager("Олена"),
                new Developer("Дмитро")
        };
        for (Employee e : staff) {
            e.work();                                // спільний метод — для всіх
        }
        System.out.println();

        // === 3. Що буде БЕЗ перевірки: ClassCastException ===
        System.out.println("=== Небезпечне звуження до НЕ того типу ===");
        Employee someone = new Manager("Ігор"); // насправді це Manager!
        try {
            Developer wrong = (Developer) someone; // намагаємось видати менеджера за розробника
            wrong.writeCode();
        } catch (ClassCastException e) {
            System.out.println("  ✖ Спіймано ClassCastException!");
            System.out.println("  Manager не можна звузити до Developer: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Правило: звуження — явне; перед ним завжди перевіряй тип через instanceof.");
    }
}
