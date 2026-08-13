package ua.com.javarush.jsquad.m1.example06_creating_objects;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Модуль 2. Рівень 17. Reflection API
 * <hr>
 * <h3>Тема: Створення об'єктів за допомогою рефлексії</h3>
 *
 * <p>Зазвичай ми створюємо об'єкти словом {@code new}. Але в Reflection API є два
 * методи, якими теж можна створювати об'єкти:</p>
 * <pre>
 *   1. Class.newInstance()        — з пакета java.lang
 *   2. Constructor.newInstance()  — з пакета java.lang.reflect
 * </pre>
 *
 * <h4>Як користуватися:</h4>
 * <pre>
 *   // 1) через Class — потрібен лише об'єкт Class
 *   Employee employee = Employee.class.newInstance();
 *
 *   // 2) через Constructor — спершу беремо конструктор, потім викликаємо newInstance()
 *   Constructor&lt;Employee&gt; constructor = Employee.class.getConstructor();
 *   Employee employee = constructor.newInstance();
 * </pre>
 *
 * <h4>Різниця між ними:</h4>
 * <pre>
 *   1. Class.newInstance() може викликати ЛИШЕ конструктор без аргументів.
 *      Constructor.newInstance() — будь-який конструктор, з будь-якою кількістю параметрів.
 *
 *   2. Class.newInstance() вимагає, щоб конструктор був видимим.
 *      Constructor.newInstance() може викликати й приватні конструктори
 *      (після setAccessible(true)).
 *
 *   3. Class.newInstance() кидає будь-який виняток конструктора як є —
 *      навіть перевірюваний, який ви ніде не оголошували.
 *      Constructor.newInstance() завжди загортає виняток в InvocationTargetException.
 *
 *   Висновок: використання Constructor.newInstance() є кращим — саме його
 *   застосовують Spring, Guava, Zookeeper, Jackson, Servlet та інші.
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> {@code new} — це купити готову модель у магазині.
 * {@code Constructor.newInstance()} — прийти на завод із креслеником і сказати
 * "зберіть мені за цією специфікацією". Дорожче й довше, зате можна замовити те,
 * про що продавець у магазині навіть не знає.</p>
 *
 * <p><b>Реальне застосування:</b> коли Spring читає конфігурацію і бачить ім'я
 * класу рядком, він не може написати {@code new}. Він бере конструктор і викликає
 * {@code newInstance()}. Так само працюють JDBC-драйвери та плагінні системи.</p>
 */
public class Example06_CreatingObjects {

    // Class.newInstance() позначений як @Deprecated з Java 9 —
    // ми свідомо його викликаємо, щоб показати різницю, тому глушимо попередження.
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {

        // === 1. Спосіб 1: Class.newInstance() ===
        // Сценарій із лекції — найкоротший запис, але з великою кількістю обмежень.
        Employee employee1 = Employee.class.newInstance();

        System.out.println("1. Class.newInstance():");
        System.out.println("   " + employee1);
        System.out.println("   Викликано конструктор без аргументів — інших цей спосіб не вміє.");

        System.out.println();

        // === 2. Спосіб 2: Constructor.newInstance() ===
        Constructor<Employee> noArgs = Employee.class.getConstructor();
        Employee employee2 = noArgs.newInstance();

        System.out.println("2. Constructor.newInstance():");
        System.out.println("   без аргументів: " + employee2);

        // А тепер те, чого перший спосіб не може взагалі — конструктор з параметрами
        Constructor<Employee> withArgs = Employee.class.getConstructor(String.class, String.class);
        Employee employee3 = withArgs.newInstance("Оксана Данилюк", "Тімлід");

        System.out.println("   з аргументами:  " + employee3);

        System.out.println();

        // === 3. Відмінність №1: кількість аргументів ===
        System.out.println("3. Відмінність №1 — аргументи конструктора:");
        System.out.println("   Class.newInstance()       -> тільки конструктор БЕЗ аргументів");
        System.out.println("   Constructor.newInstance() -> будь-який конструктор");

        // Якщо конструктора без аргументів немає, Class.newInstance() падає
        try {
            NoDefaultConstructor.class.newInstance();
        } catch (InstantiationException e) {
            System.out.println("   Клас без конструктора без аргументів -> InstantiationException");
        }
        // А через Constructor усе працює
        Object created = NoDefaultConstructor.class.getConstructor(String.class).newInstance("Київ");
        System.out.println("   Через Constructor той самий клас створюється: " + created);

        System.out.println();

        // === 4. Відмінність №2: видимість конструктора ===
        // Сценарій: у Singleton конструктор private. "Ззовні мене не створити" — так вважав автор.
        System.out.println("4. Відмінність №2 — приватний конструктор:");

        DatabaseConnection legal = DatabaseConnection.getInstance();
        System.out.println("   Легальний спосіб: " + legal.getUrl());

        try {
            DatabaseConnection.class.newInstance();
        } catch (IllegalAccessException e) {
            System.out.println("   Class.newInstance() -> IllegalAccessException (конструктор private)");
        }

        // А Constructor.newInstance() після setAccessible(true) створює другий об'єкт
        Constructor<DatabaseConnection> privateConstructor =
                DatabaseConnection.class.getDeclaredConstructor();
        privateConstructor.setAccessible(true);                  // знімаємо захист
        DatabaseConnection hacked = privateConstructor.newInstance();

        System.out.println("   Constructor.newInstance() + setAccessible(true) -> об'єкт створено!");
        System.out.println("   Це той самий об'єкт, що й INSTANCE? " + (legal == hacked));
        System.out.println("   Тобто рефлексія ламає патерн Singleton. Захиститися можна");
        System.out.println("   через enum-Singleton — його рефлексія створити не дозволяє.");

        System.out.println();

        // === 5. Відмінність №3: обробка винятків з конструктора ===
        // Конструктор ReportGenerator завжди кидає IOException (перевірюваний виняток).
        System.out.println("5. Відмінність №3 — винятки з конструктора:");

        try {
            ReportGenerator.class.newInstance();
        } catch (Exception e) {
            System.out.println("   Class.newInstance()       -> " + e.getClass().getSimpleName()
                    + ": " + e.getMessage());
            System.out.println("      Виняток прилетів \"як є\", хоча метод його ніде не оголошував.");
        }

        try {
            ReportGenerator.class.getConstructor().newInstance();
        } catch (InvocationTargetException e) {
            System.out.println("   Constructor.newInstance() -> " + e.getClass().getSimpleName());
            System.out.println("      Справжня причина всередині: "
                    + e.getCause().getClass().getSimpleName() + ": " + e.getCause().getMessage());
            System.out.println("      Тому після InvocationTargetException завжди дивіться getCause().");
        }

        System.out.println();

        // === 6. Сучасний правильний запис ===
        // Class.newInstance() застарів з Java 9. Офіційна заміна — саме такий рядок:
        Employee modern = Employee.class.getDeclaredConstructor().newInstance();

        System.out.println("6. Як писати сьогодні:");
        System.out.println("   Employee.class.getDeclaredConstructor().newInstance() -> " + modern);
        System.out.println("   Class.newInstance() позначено @Deprecated з Java 9 саме через");
        System.out.println("   проблему з непередбачуваними перевірюваними винятками.");

        System.out.println();

        // === 7. Практика: створення об'єкта за іменем класу з конфігу ===
        // Ось заради чого все це існує: клас невідомий на етапі компіляції.
        System.out.println("7. Створюємо об'єкти за іменами класів із \"конфігу\":");

        List<String> classNamesFromConfig = List.of(
                "java.util.ArrayList",
                "java.util.HashMap",
                "java.lang.StringBuilder");

        for (String className : classNamesFromConfig) {
            Class<?> type = Class.forName(className);
            Object instance = type.getDeclaredConstructor().newInstance();
            System.out.println("   " + className + " -> створено " + instance.getClass().getSimpleName()
                    + ", вміст: " + instance);
        }

        System.out.println();

        // === 8. Створення масиву через рефлексію ===
        // Для масивів є окремий клас-помічник java.lang.reflect.Array.
        Object array = java.lang.reflect.Array.newInstance(String.class, 3);
        java.lang.reflect.Array.set(array, 0, "Київ");
        java.lang.reflect.Array.set(array, 1, "Львів");
        java.lang.reflect.Array.set(array, 2, "Одеса");

        System.out.println("8. Масив через java.lang.reflect.Array:");
        System.out.println("   тип: " + array.getClass().getSimpleName()
                + ", довжина: " + java.lang.reflect.Array.getLength(array));
        System.out.println("   вміст: " + java.util.Arrays.toString((String[]) array));
    }

    /**
     * Клас БЕЗ конструктора без аргументів —
     * на ньому видно обмеження {@code Class.newInstance()}.
     */
    public static class NoDefaultConstructor {

        private final String city;

        public NoDefaultConstructor(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "NoDefaultConstructor{" + city + "}";
        }
    }
}
