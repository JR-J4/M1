package ua.com.javarush.jsquad.m1.example05_constructors;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * Модуль 2. Рівень 17. Reflection API
 * <hr>
 * <h3>Тема: Конструктори — getConstructors, getDeclaredConstructors, клас Constructor</h3>
 *
 * <h4>Два методи отримання конструкторів:</h4>
 * <pre>
 *   getDeclaredConstructors() — УСІ конструктори, оголошені класом:
 *                               public, protected, default (package) та private.
 *   getConstructors()         — лише PUBLIC конструктори класу.
 * </pre>
 *
 * <h4>Основні методи класу Constructor:</h4>
 * <pre>
 *   getName()                  — ім'я конструктора (це завжди ПОВНЕ ім'я класу!)
 *   getModifiers()             — модифікатори конструктора
 *   getParameterTypes()        — типи параметрів у порядку оголошення
 *   getParameters()            — масив об'єктів Parameter
 *   getGenericParameterTypes() — типи параметрів з урахуванням узагальнень
 *   getExceptionTypes()        — винятки з throws (порожній масив, якщо їх немає)
 * </pre>
 *
 * <p><b>Важлива деталь із лекції:</b> конструктори деяких внутрішніх класів мають
 * <i>неявно оголошений</i> параметр на додачу до явно оголошених — посилання на
 * об'єкт зовнішнього класу. Нижче це видно на власні очі.</p>
 *
 * <p><b>Аналогія з життя:</b> конструктор — це бланк заявки на виготовлення речі.
 * {@code getConstructors()} показує бланки, що лежать у відкритому доступі в холі,
 * а {@code getDeclaredConstructors()} — усі бланки, включно з тими, що зберігаються
 * у сейфі "тільки для співробітників".</p>
 *
 * <p><b>Реальне застосування:</b> Spring перебирає конструктори класу, обирає
 * потрібний (наприклад, єдиний або позначений {@code @Autowired}), дивиться типи
 * його параметрів — і сам підставляє в них інші біни.</p>
 */
public class Example05_Constructors {

    public static void main(String[] args) throws Exception {

        Class<Employee> employeeClass = Employee.class;

        // === 1. getConstructors() — лише public ===
        // Сценарій: дізнаємось, як цим класом дозволено користуватися ззовні.
        Constructor<?>[] publicConstructors = employeeClass.getConstructors();

        System.out.println("1. getConstructors() — лише public: знайдено " + publicConstructors.length);
        for (Constructor<?> constructor : publicConstructors) {
            System.out.println("   " + describe(constructor));
        }

        System.out.println();

        // === 2. getDeclaredConstructors() — усі, включно з private ===
        Constructor<?>[] allConstructors = employeeClass.getDeclaredConstructors();

        System.out.println("2. getDeclaredConstructors() — усі: знайдено " + allConstructors.length);
        // Сортуємо за кількістю параметрів, щоб вивід був стабільним
        Arrays.sort(allConstructors, java.util.Comparator.comparingInt(Constructor::getParameterCount));
        for (Constructor<?> constructor : allConstructors) {
            System.out.println("   " + describe(constructor));
        }
        System.out.println("   Рефлексія бачить навіть private-конструктор, який ви ховали від усіх.");

        System.out.println();

        // === 3. getName() у конструктора — це ім'я КЛАСУ ===
        // Часта плутанина: у конструктора немає власного імені.
        Constructor<Employee> main = employeeClass.getConstructor(String.class, String.class, double.class);

        System.out.println("3. Ім'я конструктора:");
        System.out.println("   getName() -> " + main.getName());
        System.out.println("   Це повне ім'я класу, а не \"Employee(...)\" — у конструктора свого імені немає.");

        System.out.println();

        // === 4. Параметри конструктора ===
        System.out.println("4. Параметри конструктора Employee(String, String, double):");
        System.out.println("   getParameterCount(): " + main.getParameterCount());
        System.out.println("   getParameterTypes(): " + Arrays.toString(main.getParameterTypes()));
        System.out.println("   getGenericParameterTypes(): "
                + Arrays.toString(main.getGenericParameterTypes()));

        for (Parameter parameter : main.getParameters()) {
            System.out.println("      " + parameter.getType().getSimpleName() + " " + parameter.getName());
        }

        System.out.println();

        // === 5. Винятки конструктора ===
        Constructor<Employee> protectedOne = employeeClass.getDeclaredConstructor(String.class, double.class);

        System.out.println("5. Винятки, оголошені конструктором:");
        System.out.println("   Employee(String, double) throws "
                + Arrays.toString(protectedOne.getExceptionTypes()));
        System.out.println("   Employee(String, String, double) — винятків: "
                + main.getExceptionTypes().length + " (порожній масив)");

        System.out.println();

        // === 6. Модифікатори конструкторів ===
        // Сценарій: аудит класу — чи можна його взагалі створити ззовні.
        System.out.println("6. Модифікатори кожного конструктора:");
        for (Constructor<?> constructor : allConstructors) {
            int mods = constructor.getModifiers();
            String access;
            if (Modifier.isPublic(mods)) {
                access = "public";
            } else if (Modifier.isProtected(mods)) {
                access = "protected";
            } else if (Modifier.isPrivate(mods)) {
                access = "private";
            } else {
                access = "package-private";
            }
            System.out.println("   " + String.format("%-16s", access)
                    + " параметрів: " + constructor.getParameterCount());
        }

        System.out.println();

        // === 7. Пошук конкретного конструктора ===
        System.out.println("7. Пошук конструктора за типами параметрів:");
        System.out.println("   getConstructor() (без аргументів):        "
                + describe(employeeClass.getConstructor()));
        System.out.println("   getDeclaredConstructor(long.class):       "
                + describe(employeeClass.getDeclaredConstructor(long.class)));

        try {
            employeeClass.getConstructor(long.class);      // він private → getConstructor не бачить
        } catch (NoSuchMethodException e) {
            System.out.println("   getConstructor(long.class) -> NoSuchMethodException (конструктор private)");
        }

        try {
            employeeClass.getConstructor(int.class);       // такого немає взагалі
        } catch (NoSuchMethodException e) {
            System.out.println("   getConstructor(int.class)  -> NoSuchMethodException (такого немає)");
        }
        System.out.println("   Увага: типи мають збігатися точно. int.class і Integer.class — різні речі.");

        System.out.println();

        // === 8. Прихований параметр у конструктора внутрішнього класу ===
        // WorkBadge оголошений як inner (без static), тому в байт-коді його
        // конструктор приймає ще й посилання на зовнішній об'єкт Employee.
        Class<Employee.WorkBadge> badgeClass = Employee.WorkBadge.class;
        Constructor<?> badgeConstructor = badgeClass.getDeclaredConstructors()[0];

        System.out.println("8. Конструктор внутрішнього класу WorkBadge:");
        System.out.println("   У коді написано:   public WorkBadge(String badgeNumber)");
        System.out.println("   getParameterTypes(): " + Arrays.toString(badgeConstructor.getParameterTypes()));
        System.out.println("   Параметрів насправді: " + badgeConstructor.getParameterCount()
                + " — компілятор додав прихований Employee.");

        // Тому й створювати такий об'єкт треба, передаючи зовнішній об'єкт першим
        Employee employee = new Employee("Андрій Ткаченко", "Аналітик", 55_000);
        Object badge = badgeConstructor.newInstance(employee, "A-117");
        System.out.println("   Створили через рефлексію: " + badge);
    }

    /**
     * Складає текстовий опис конструктора з його метаданих.
     */
    private static String describe(Constructor<?> constructor) {
        StringBuilder sb = new StringBuilder();

        String modifiers = Modifier.toString(constructor.getModifiers());
        sb.append(modifiers.isEmpty() ? "(package-private)" : modifiers).append(' ');

        // Беремо коротке ім'я класу — getName() повернув би повне з пакетом
        sb.append(constructor.getDeclaringClass().getSimpleName()).append('(');

        Class<?>[] params = constructor.getParameterTypes();
        for (int i = 0; i < params.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(params[i].getSimpleName());
        }
        sb.append(')');

        Class<?>[] exceptions = constructor.getExceptionTypes();
        if (exceptions.length > 0) {
            sb.append(" throws ");
            for (int i = 0; i < exceptions.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(exceptions[i].getSimpleName());
            }
        }
        return sb.toString();
    }
}
