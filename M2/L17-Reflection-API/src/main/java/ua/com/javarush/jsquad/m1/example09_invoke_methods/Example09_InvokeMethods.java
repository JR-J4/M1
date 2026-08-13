package ua.com.javarush.jsquad.m1.example09_invoke_methods;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Модуль 2. Рівень 17. Reflection API
 * <hr>
 * <h3>Тема: Виклик методу об'єкта за іменем — Method.invoke()</h3>
 *
 * <p>Одна з можливостей рефлексії — <b>викликати метод об'єкта по імені</b>,
 * навіть якщо на етапі компіляції ми про цей метод нічого не знали.</p>
 *
 * <h4>Синтаксис:</h4>
 * <pre>
 *   Method method = clazz.getMethod("add", int.class, int.class);
 *   Object result = method.invoke(об'єкт, 2, 3);
 *
 *   // для static-методу об'єкт не потрібен:
 *   Object result = method.invoke(null, 5);
 * </pre>
 *
 * <h4>Що варто пам'ятати:</h4>
 * <pre>
 *   • invoke() завжди повертає Object — примітиви автоматично пакуються
 *     (int → Integer). Для void-методу повертається null.
 *   • Якщо метод усередині кинув виняток, invoke() загорне його
 *     в InvocationTargetException — справжня причина лежить у getCause().
 *   • Для private-методу потрібен getDeclaredMethod() + setAccessible(true).
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> звичайний виклик методу — це натиснути конкретну
 * кнопку на пульті, яку ви бачите очима. {@code invoke()} — це сказати вголос
 * "натисни кнопку з написом ГУЧНІСТЬ+", не знаючи заздалегідь, чи є така кнопка
 * взагалі. Якщо кнопки немає — дізнаєтесь про це лише в момент промовляння.</p>
 *
 * <p><b>Реальне застосування:</b> саме так JUnit запускає ваші тести. Він ніколи
 * не бачив класу {@code CalculatorTest}, але знаходить у ньому методи з
 * {@code @Test} і викликає кожен через {@code invoke()}. У кінці прикладу ми
 * напишемо такий міні-JUnit самі.</p>
 */
public class Example09_InvokeMethods {

    public static void main(String[] args) throws Exception {

        Calculator calculator = new Calculator();
        Class<Calculator> calcClass = Calculator.class;

        // === 1. Найпростіший виклик ===
        // Сценарій: імені методу ми "нібито" не знали — воно прийшло рядком.
        Method add = calcClass.getMethod("add", int.class, int.class);
        Object result = add.invoke(calculator, 2, 3);

        System.out.println("1. Виклик методу за іменем:");
        System.out.println("   add.invoke(calculator, 2, 3) -> " + result);
        System.out.println("   Тип результату: " + result.getClass().getSimpleName()
                + " (int автоматично запакувався в Integer)");

        // Щоб працювати з результатом як з числом, його треба привести
        int sum = (int) add.invoke(calculator, 10, 15);
        System.out.println("   Після приведення: " + (sum * 2));

        System.out.println();

        // === 2. void-метод повертає null ===
        Method saveToMemory = calcClass.getMethod("saveToMemory", double.class);
        Object voidResult = saveToMemory.invoke(calculator, 42.5);

        System.out.println("2. Виклик void-методу:");
        System.out.println("   saveToMemory.invoke(...) повернув: " + voidResult);
        System.out.println("   Але стан об'єкта змінився: getMemory() = " + calculator.getMemory());

        System.out.println();

        // === 3. Статичний метод — об'єкт не потрібен ===
        Method square = calcClass.getMethod("square", int.class);

        System.out.println("3. Статичний метод (перший аргумент null):");
        System.out.println("   square.invoke(null, 7) -> " + square.invoke(null, 7));
        System.out.println("   Об'єкт передавати можна, але він ігнорується: "
                + square.invoke(calculator, 5));

        System.out.println();

        // === 4. Приватний метод ===
        // Як і з полями: спершу getDeclaredMethod(), потім setAccessible(true).
        Method formatResult = calcClass.getDeclaredMethod("formatResult", double.class);

        System.out.println("4. Приватний метод:");
        System.out.println("   Метод знайдено: " + formatResult.getName()
                + ", private? " + Modifier.isPrivate(formatResult.getModifiers()));

        try {
            formatResult.invoke(calculator, 3.14159);
        } catch (IllegalAccessException e) {
            System.out.println("   Виклик без setAccessible -> IllegalAccessException");
        }

        formatResult.setAccessible(true);
        System.out.println("   Після setAccessible(true) -> " + formatResult.invoke(calculator, 3.14159));
        formatResult.setAccessible(false);

        System.out.println();

        // === 5. Виняток усередині методу ===
        // Сценарій: divide(10, 0) кидає ArithmeticException.
        Method divide = calcClass.getMethod("divide", double.class, double.class);

        System.out.println("5. Коли метод кидає виняток:");
        System.out.println("   Успішний виклик: divide(10, 4) -> " + divide.invoke(calculator, 10.0, 4.0));

        try {
            divide.invoke(calculator, 10.0, 0.0);
        } catch (InvocationTargetException e) {
            System.out.println("   divide(10, 0) -> " + e.getClass().getSimpleName());
            System.out.println("   getCause() -> " + e.getCause().getClass().getSimpleName()
                    + ": " + e.getCause().getMessage());
            System.out.println("   Тобто invoke() ніколи не кидає виняток методу напряму —");
            System.out.println("   він завжди загорнутий, і діставати його треба через getCause().");
        }

        System.out.println();

        // === 6. Типові помилки при виклику ===
        System.out.println("6. Чого invoke() не пробачає:");

        try {
            add.invoke(calculator, 2);                      // забули другий аргумент
        } catch (IllegalArgumentException e) {
            System.out.println("   мало аргументів      -> IllegalArgumentException");
        }

        try {
            add.invoke(calculator, "два", "три");           // не ті типи
        } catch (IllegalArgumentException e) {
            System.out.println("   не ті типи аргументів -> IllegalArgumentException");
        }

        try {
            add.invoke(null, 2, 3);                         // метод не статичний
        } catch (NullPointerException e) {
            System.out.println("   null замість об'єкта  -> NullPointerException (метод не статичний)");
        }

        try {
            add.invoke("рядок", 2, 3);                      // об'єкт іншого класу
        } catch (IllegalArgumentException e) {
            System.out.println("   об'єкт іншого класу   -> IllegalArgumentException");
        }

        System.out.println();

        // === 7. Динамічний диспетчер: виклик за рядком з "команди" ===
        // Сценарій: користувач ввів команду, а ми знаходимо потрібний метод.
        System.out.println("7. Виконуємо \"команди\", що прийшли рядками:");

        String[][] commands = {
                {"add", "7", "8"},
                {"square", "6"},
                {"divide", "9", "2"},
                {"multiply", "3", "4"}       // такого методу немає
        };

        for (String[] command : commands) {
            execute(calculator, command);
        }

        System.out.println();

        // === 8. Міні-JUnit: знаходимо і запускаємо тести ===
        // Ось заради чого існує invoke(). Фреймворк нічого не знає про CalculatorTest —
        // він просто шукає анотації і викликає методи.
        System.out.println("8. Власний міні-JUnit у 30 рядків:");
        runTests(CalculatorTest.class);
    }

    /**
     * Знаходить метод за іменем з команди і викликає його,
     * підбираючи метод за кількістю аргументів.
     */
    private static void execute(Calculator calculator, String[] command) {
        String methodName = command[0];
        int argCount = command.length - 1;

        // Шукаємо метод з потрібним іменем і кількістю параметрів
        Method target = null;
        for (Method method : Calculator.class.getMethods()) {
            if (method.getName().equals(methodName) && method.getParameterCount() == argCount) {
                target = method;
                break;
            }
        }

        if (target == null) {
            System.out.println("   " + methodName + "(...) -> невідома команда");
            return;
        }

        try {
            // Перетворюємо рядкові аргументи на типи, яких чекає метод
            Class<?>[] paramTypes = target.getParameterTypes();
            Object[] arguments = new Object[argCount];
            for (int i = 0; i < argCount; i++) {
                String raw = command[i + 1];
                // Тут навмисно звичайний if, а не тернарний оператор.
                // Запис (умова ? Integer.valueOf(raw) : Double.valueOf(raw))
                // виглядає коротше, але Java звела б обидві гілки до одного типу
                // double — і в int-параметр полетів би Double з помилкою
                // "argument type mismatch". Класична пастка автопакування.
                if (paramTypes[i] == int.class) {
                    arguments[i] = Integer.valueOf(raw);
                } else {
                    arguments[i] = Double.valueOf(raw);
                }
            }

            Object value = target.invoke(calculator, arguments);
            System.out.println("   " + methodName + Arrays.toString(arguments) + " -> " + value);

        } catch (Exception e) {
            System.out.println("   " + methodName + " -> помилка: " + e.getMessage());
        }
    }

    /**
     * Міні-версія JUnit: створює об'єкт тестового класу, знаходить методи з
     * {@code @Test} і запускає кожен, попередньо викликавши {@code @BeforeEach}.
     *
     * <p>Справжній JUnit влаштований складніше, але його серце — саме ці три дії:
     * створити об'єкт, знайти методи за анотацією, викликати {@code invoke()}.</p>
     */
    private static void runTests(Class<?> testClass) throws Exception {
        int passed = 0;
        int failed = 0;

        // Шукаємо метод підготовки — той, що позначений @BeforeEach
        Method beforeEach = null;
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeEach.class)) {
                beforeEach = method;
            }
        }

        // Сортуємо, щоб порядок тестів був стабільним від запуску до запуску
        Method[] methods = testClass.getDeclaredMethods();
        Arrays.sort(methods, java.util.Comparator.comparing(Method::getName));

        for (Method method : methods) {
            Test test = method.getAnnotation(Test.class);
            if (test == null) {
                continue;                          // не тест — пропускаємо
            }

            // Для кожного тесту — новий об'єкт, як це робить справжній JUnit
            Object instance = testClass.getDeclaredConstructor().newInstance();

            if (beforeEach != null) {
                beforeEach.invoke(instance);
            }

            String name = test.value().isEmpty() ? method.getName() : test.value();
            try {
                method.invoke(instance);
                System.out.println("   [OK]    " + name);
                passed++;
            } catch (InvocationTargetException e) {
                // Справжня причина падіння тесту — усередині getCause()
                Throwable cause = e.getCause();
                System.out.println("   [ПАДАЄ] " + name);
                System.out.println("           " + cause.getClass().getSimpleName()
                        + ": " + cause.getMessage());
                failed++;
            }
        }

        System.out.println("   ─────────────────────────────────────");
        System.out.println("   Усього: " + (passed + failed) + ", успішних: " + passed + ", провалених: " + failed);
        System.out.println("   Метод notATest() не запускався — на ньому немає @Test.");
    }
}
