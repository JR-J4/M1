package ua.com.javarush.jsquad.m1.example02_pros_cons;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Модуль 2. Рівень 17. Reflection API
 * <hr>
 * <h3>Тема: Можливості та мінуси рефлексії</h3>
 *
 * <h4>Що дозволяє рефлексія:</h4>
 * <pre>
 *   • дізнатися/визначити клас об'єкта;
 *   • отримати інформацію про модифікатори класу, поля, методи,
 *     константи, конструктори та суперкласи;
 *   • дізнатися, які методи належать до інтерфейсів, що реалізуються;
 *   • створити екземпляр класу, коли ім'я класу невідоме до моменту виконання;
 *   • отримати і встановити значення поля об'єкта по імені;
 *   • викликати метод об'єкта по імені.
 * </pre>
 *
 * <h4>Мінуси рефлексії:</h4>
 * <pre>
 *   • Порушення безпеки застосунку — доступ до коду, до якого його не мали б мати
 *     (порушення інкапсуляції).
 *   • Обмеження системи безпеки — рефлексія вимагає дозволів часу виконання,
 *     недоступних під управлінням менеджера безпеки / у закритих модулях.
 *   • Низька продуктивність — типи визначаються динамічно, JVM не може
 *     оптимізувати виклик так само добре, як звичайний.
 *   • Складність підтримки — такий код важко читати й налагоджувати, помилки
 *     "переїжджають" з етапу компіляції на етап виконання.
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> рефлексія — це майстер-ключ від усіх дверей у
 * будинку. Дуже зручно, коли ви комендант і мусите потрапити всюди. Але сам факт
 * існування такого ключа означає, що жоден замок у будинку більше нічого не
 * гарантує.</p>
 *
 * <p><b>Реальне застосування:</b> висновок простий — рефлексія це інструмент
 * для <i>фреймворків</i> та інфраструктурного коду, а не для щоденної бізнес-логіки.
 * Якщо задачу можна розв'язати звичайним викликом методу — розв'язуйте звичайним.</p>
 */
public class Example02_ProsAndCons {

    public static void main(String[] args) throws Exception {

        // === 1. МІНУС: порушення інкапсуляції ===
        // Сценарій: клас BankAccount ретельно захищає баланс — змінити його можна
        // лише через deposit() з перевіркою. Спробуємо "легально" зіпсувати баланс.
        BankAccount account = new BankAccount("Олена Кравчук", 5_000.0);

        System.out.println("1. Порушення інкапсуляції:");
        System.out.println("   Баланс на старті: " + account.getBalance());

        try {
            account.deposit(-100_000);           // клас захищається
        } catch (IllegalArgumentException e) {
            System.out.println("   deposit(-100000) -> " + e.getMessage());
        }

        // А тепер те саме, але через рефлексію — жодних перевірок не буде викликано
        Field balanceField = BankAccount.class.getDeclaredField("balance");
        balanceField.setAccessible(true);        // "вимикаємо" модифікатор private
        balanceField.setDouble(account, -100_000);

        System.out.println("   Через рефлексію -> баланс: " + account.getBalance());
        System.out.println("   Захист класу не спрацював: рефлексія обійшла і private, і перевірку.");

        System.out.println();

        // === 2. МІНУС: обмеження системи безпеки (модулі JDK закриті) ===
        // Сценарій: спробуємо залізти всередину String. Свої класи відкриті,
        // а от нутрощі JDK з часів Java 9 (система модулів) закриті.
        System.out.println("2. Обмеження системи безпеки:");
        try {
            Field valueField = String.class.getDeclaredField("value");
            valueField.setAccessible(true);
            System.out.println("   Доступ до String.value дозволено");
        } catch (Exception e) {
            System.out.println("   " + e.getClass().getSimpleName() + " — модуль java.base не відкриває java.lang");
            System.out.println("   Тобто рефлексія всесильна не всюди: її обмежують модулі та політики безпеки.");
        }

        System.out.println();

        // === 3. МІНУС: низька продуктивність ===
        // Сценарій: 5 мільйонів викликів одного й того самого методу.
        System.out.println("3. Продуктивність (5 000 000 викликів getBalance()):");

        int iterations = 5_000_000;
        BankAccount probe = new BankAccount("Тест", 100.0);

        // 3.1 Звичайний виклик
        long start = System.nanoTime();
        double sum1 = 0;
        for (int i = 0; i < iterations; i++) {
            sum1 += probe.getBalance();
        }
        long directTime = System.nanoTime() - start;

        // 3.2 Виклик через рефлексію
        Method getBalance = BankAccount.class.getMethod("getBalance");
        start = System.nanoTime();
        double sum2 = 0;
        for (int i = 0; i < iterations; i++) {
            sum2 += (double) getBalance.invoke(probe);
        }
        long reflectionTime = System.nanoTime() - start;

        System.out.println("   Звичайний виклик:   " + (directTime / 1_000_000) + " мс (сума " + sum1 + ")");
        System.out.println("   Через рефлексію:    " + (reflectionTime / 1_000_000) + " мс (сума " + sum2 + ")");
        System.out.println("   Різниця приблизно у " + Math.max(1, reflectionTime / Math.max(1, directTime)) + " разів.");
        System.out.println("   Висновок: рефлексію не ставлять у гарячі цикли, які виконуються мільйони разів.");

        System.out.println();

        // === 4. МІНУС: помилки переїжджають з компіляції на виконання ===
        // Сценарій: хтось перейменував поле "balance" на "amount".
        // Звичайний код одразу б не скомпілювався. Рефлексивний — впаде у клієнта.
        System.out.println("4. Складність підтримки:");
        try {
            BankAccount.class.getDeclaredField("amount");   // такого поля немає
        } catch (NoSuchFieldException e) {
            System.out.println("   getDeclaredField(\"amount\") -> NoSuchFieldException");
            System.out.println("   Компілятор цього не спіймав: рядок \"amount\" для нього просто текст.");
            System.out.println("   IDE теж не перейменує таке поле під час рефакторингу.");
        }

        System.out.println();

        // === 5. ПЛЮС: універсальний код, який працює з будь-яким класом ===
        // Сценарій: логер, що вміє надрукувати вміст БУДЬ-ЯКОГО об'єкта.
        // Написати таке без рефлексії неможливо — ми ж не знаємо класів наперед.
        System.out.println("5. Заради чого все це — універсальність:");
        dump(account);
        dump(new Employee("Ігор Мельник", 48_000, "Розробка"));
        dump(new Point(12, -4));
        System.out.println("   Один метод — три різні класи, про які він нічого не знав.");
    }

    /** Допоміжний клас для демонстрації універсального dump(). */
    private static class Employee {
        private final String name;
        private final int salary;
        private final String department;

        Employee(String name, int salary, String department) {
            this.name = name;
            this.salary = salary;
            this.department = department;
        }
    }

    /** Ще один клас із зовсім іншим набором полів. */
    private static class Point {
        private final int x;
        private final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Друкує всі поля будь-якого об'єкта. Метод нічого не знає про класи,
     * які йому передадуть, — саме в цьому цінність рефлексії.
     *
     * <p>Так само влаштовані Jackson (об'єкт → JSON), Hibernate (об'єкт → рядок
     * таблиці) і метод {@code toString()} у бібліотеках на кшталт Apache Commons.</p>
     */
    private static void dump(Object obj) {
        Class<?> type = obj.getClass();
        StringBuilder sb = new StringBuilder("   " + type.getSimpleName() + " { ");

        Field[] fields = type.getDeclaredFields();
        boolean first = true;
        for (Field field : fields) {
            if (field.isSynthetic()) {          // пропускаємо службові поля компілятора
                continue;
            }
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(field.getName()).append('=');
            try {
                field.setAccessible(true);
                sb.append(field.get(obj));
            } catch (Exception e) {
                // Наприклад, поля класів JDK закриті модулями — чесно про це пишемо
                sb.append("<немає доступу>");
            }
        }
        System.out.println(sb.append(" }"));
    }
}
