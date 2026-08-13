package ua.com.javarush.jsquad.m1.example04_methods;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * Модуль 2. Рівень 17. Reflection API
 * <hr>
 * <h3>Тема: Отримання даних про методи — клас java.lang.reflect.Method</h3>
 *
 * <p>Об'єкт {@code Method} — це опис методу: як називається, що повертає, які
 * приймає параметри, які винятки оголошує та які має анотації.</p>
 *
 * <h4>Як отримати методи:</h4>
 * <pre>
 *   clazz.getMethods()                       // лише public, ЗАТЕ разом з успадкованими (в т.ч. від Object)
 *   clazz.getDeclaredMethods()               // ВСІ методи цього класу (private теж), без успадкованих
 *   clazz.getMethod("findAll")               // один public-метод за іменем і типами параметрів
 *   clazz.getDeclaredMethod("secret", int.class)
 * </pre>
 *
 * <h4>Основні методи класу Method:</h4>
 * <pre>
 *   getName()                  — ім'я методу
 *   getModifiers()             — модифікатори (розшифровує Modifier)
 *   getReturnType()            — тип, що повертається
 *   getGenericReturnType()     — тип з узагальненнями: List&lt;String&gt;
 *   getParameterTypes()        — масив типів параметрів (порожній, якщо параметрів немає)
 *   getGenericParameterTypes() — те саме, але з узагальненнями
 *   getParameters()            — масив об'єктів Parameter (тип + ім'я + модифікатори)
 *   getExceptionTypes()        — винятки з throws (порожній масив, якщо їх немає)
 *   getGenericExceptionTypes() — те саме з урахуванням узагальнень
 *   getAnnotations()           — усі анотації методу
 *   getDeclaredAnnotations()   — анотації, оголошені безпосередньо на цьому методі
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> {@code Method} — це не сама послуга, а рядок у
 * прайс-листі: назва послуги, що потрібно принести (параметри), що отримаєте на
 * виході (тип повернення) і дрібний шрифт про можливі проблеми (throws).</p>
 *
 * <p><b>Реальне застосування:</b> JUnit шукає методи з {@code @Test}, Spring MVC —
 * методи з {@code @GetMapping}, щоб зіставити їх з URL. Обидва роблять це саме так:
 * обходять {@code getDeclaredMethods()} і питають анотації.</p>
 */
public class Example04_Methods {

    public static void main(String[] args) throws Exception {

        Class<OrderService> serviceClass = OrderService.class;

        // === 1. getDeclaredMethods() vs getMethods() ===
        // Сценарій: інспектуємо сервіс — які методи він взагалі має.
        Method[] declared = serviceClass.getDeclaredMethods();
        Method[] publicMethods = serviceClass.getMethods();

        System.out.println("1. Два способи отримати методи:");
        System.out.println("   getDeclaredMethods(): " + declared.length + " (усі методи класу, навіть private)");
        System.out.println("   getMethods():         " + publicMethods.length
                + " (лише public, але разом з успадкованими від Object)");

        System.out.println("   Методи, оголошені в самому OrderService:");
        // Порядок методів не гарантований — сортуємо, щоб вивід був стабільним
        Arrays.sort(declared, java.util.Comparator.comparing(Method::getName));
        for (Method method : declared) {
            System.out.println("      " + Modifier.toString(method.getModifiers())
                    + " " + method.getName());
        }

        System.out.println("   А ось що додає getMethods() зверху (успадковане від Object):");
        for (Method method : publicMethods) {
            if (method.getDeclaringClass() != OrderService.class) {
                System.out.println("      " + method.getName()
                        + " — з класу " + method.getDeclaringClass().getSimpleName());
            }
        }

        System.out.println();

        // === 2. Повний "підпис" методу ===
        // Сценарій: генератор документації відновлює сигнатуру методу з метаданих.
        System.out.println("2. Відновлюємо сигнатури методів з метаданих:");
        for (Method method : declared) {
            System.out.println("   " + buildSignature(method));
        }

        System.out.println();

        // === 3. Тип повернення: звичайний і узагальнений ===
        Method findAll = serviceClass.getMethod("findAll");

        System.out.println("3. Тип, що повертається:");
        System.out.println("   getReturnType():        " + findAll.getReturnType().getName()
                + "   (стирання типів — просто List)");
        System.out.println("   getGenericReturnType(): " + findAll.getGenericReturnType().getTypeName()
                + "   (повне оголошення)");

        Method cancelOrder = serviceClass.getMethod("cancelOrder", int.class);
        System.out.println("   У void-методу cancelOrder тип повернення: "
                + cancelOrder.getReturnType().getName());

        System.out.println();

        // === 4. Параметри методу ===
        Method createOrder = serviceClass.getMethod("createOrder", String.class, int.class);

        System.out.println("4. Параметри методу createOrder(String, int):");
        System.out.println("   getParameterCount(): " + createOrder.getParameterCount());
        System.out.println("   getParameterTypes(): " + Arrays.toString(createOrder.getParameterTypes()));

        for (Parameter parameter : createOrder.getParameters()) {
            System.out.println("      " + parameter.getType().getSimpleName()
                    + " " + parameter.getName()
                    + "   (справжнє ім'я збережено? " + parameter.isNamePresent() + ")");
        }
        System.out.println("   Імена arg0/arg1 — це нормально: щоб бачити справжні,");
        System.out.println("   клас треба скомпілювати з прапорцем javac -parameters.");

        // Varargs — під капотом це звичайний масив
        Method totalQuantity = serviceClass.getMethod("totalQuantity", int[].class);
        System.out.println("   totalQuantity(int...): isVarArgs()=" + totalQuantity.isVarArgs()
                + ", тип параметра " + totalQuantity.getParameterTypes()[0].getSimpleName());
        System.out.println("   Тому шукати його треба саме як getMethod(\"totalQuantity\", int[].class).");

        // Цікавий нюанс, який видно у виводі пункту 2:
        // Modifier.toString() надрукував для цього методу слово "transient".
        // Це не помилка: у байт-коді прапорець ACC_VARARGS має те саме числове
        // значення (0x0080), що й ACC_TRANSIENT для полів. Клас Modifier не знає,
        // кого саме він описує, тому й друкує "transient".
        // Правильна перевірка для методу — саме isVarArgs(), а не текст модифікаторів.
        System.out.println("   А слово \"transient\" у його модифікаторах — це збіг прапорців у байт-коді:");
        System.out.println("   ACC_VARARGS і ACC_TRANSIENT — те саме число 0x0080, довіряйте isVarArgs().");

        System.out.println();

        // === 5. Оголошені винятки (throws) ===
        Method exportToFile = serviceClass.getMethod("exportToFile", String.class);

        System.out.println("5. Винятки методу exportToFile:");
        for (Class<?> exceptionType : exportToFile.getExceptionTypes()) {
            System.out.println("      " + exceptionType.getSimpleName());
        }
        System.out.println("   У методу findAll винятків: " + findAll.getExceptionTypes().length
                + " (порожній масив, а не null)");

        System.out.println();

        // === 6. Анотації методів ===
        // Сценарій: фреймворк шукає, які методи треба "обгорнути" логуванням.
        System.out.println("6. Шукаємо методи, позначені @Loggable:");
        for (Method method : declared) {
            Loggable loggable = method.getAnnotation(Loggable.class);
            if (loggable != null) {
                System.out.println("   " + method.getName() + " -> логувати на рівні " + loggable.level());
            }
        }
        System.out.println("   getAnnotations() у createOrder: "
                + Arrays.toString(createOrder.getAnnotations()));
        System.out.println("   getDeclaredAnnotations() у createOrder: "
                + Arrays.toString(createOrder.getDeclaredAnnotations()));
        System.out.println("   Для методів ці два методи майже завжди однакові:");
        System.out.println("   анотації методів не успадковуються (@Inherited працює лише для класів).");

        System.out.println();

        // === 7. Пошук конкретного методу і перевантаження ===
        // Ім'я методу не унікальне — тому шукати треба разом з типами параметрів.
        System.out.println("7. Перевантажені методи шукаємо за іменем + типами параметрів:");
        System.out.println("   getMethod(\"createOrder\", String.class):            "
                + buildSignature(serviceClass.getMethod("createOrder", String.class)));
        System.out.println("   getMethod(\"createOrder\", String.class, int.class): "
                + buildSignature(serviceClass.getMethod("createOrder", String.class, int.class)));

        try {
            serviceClass.getMethod("createOrder", int.class);   // такої комбінації немає
        } catch (NoSuchMethodException e) {
            System.out.println("   getMethod(\"createOrder\", int.class) -> NoSuchMethodException");
        }

        try {
            serviceClass.getMethod("buildSecretCode", String.class);   // private
        } catch (NoSuchMethodException e) {
            System.out.println("   getMethod(\"buildSecretCode\") -> NoSuchMethodException (метод private)");
        }
        System.out.println("   getDeclaredMethod(\"buildSecretCode\") знаходить його: "
                + serviceClass.getDeclaredMethod("buildSecretCode", String.class).getName());

        System.out.println();

        // === 8. Найцікавіше: метод можна не тільки описати, а й викликати ===
        // Детально виклики розберемо в прикладі 09, тут — короткий анонс.
        OrderService service = new OrderService();

        String created = (String) createOrder.invoke(service, "Марія Шевченко", 3);

        System.out.println("8. Виклик методу через рефлексію (детально — у прикладі 09):");
        System.out.println("   createOrder.invoke(service, \"Марія Шевченко\", 3) -> " + created);
        System.out.println("   findAll.invoke(service) -> " + findAll.invoke(service));

        // Статичний метод викликається без об'єкта — перший аргумент null
        Method calculateTax = serviceClass.getMethod("calculateTax", double.class);
        System.out.println("   calculateTax.invoke(null, 1000.0) -> " + calculateTax.invoke(null, 1000.0));
    }

    /**
     * Складає текстову сигнатуру методу лише з метаданих рефлексії —
     * приблизно так, як її пише IDE або генератор документації.
     */
    private static String buildSignature(Method method) {
        StringBuilder sb = new StringBuilder();

        String modifiers = Modifier.toString(method.getModifiers());
        if (!modifiers.isEmpty()) {
            sb.append(modifiers).append(' ');
        }

        sb.append(method.getGenericReturnType().getTypeName().replace("java.lang.", ""))
                .append(' ')
                .append(method.getName())
                .append('(');

        Class<?>[] params = method.getParameterTypes();
        for (int i = 0; i < params.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(params[i].getSimpleName());
        }
        sb.append(')');

        Class<?>[] exceptions = method.getExceptionTypes();
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
