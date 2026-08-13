package ua.com.javarush.jsquad.m1.example01_reflection_intro;

import java.lang.reflect.Modifier;

/**
 * Модуль 2. Рівень 17. Reflection API
 * <hr>
 * <h3>Тема: Що таке рефлексія і як отримати об'єкт Class</h3>
 *
 * <p><b>Рефлексія в Java</b> — це механізм, який дозволяє розробнику вносити зміни
 * та отримувати інформацію про класи, інтерфейси, поля та методи <b>під час
 * виконання</b>, при цьому без знання їх імен на етапі компіляції.</p>
 *
 * <p>Точка входу в рефлексію завжди одна — об'єкт {@code Class}. Це "паспорт"
 * класу: JVM створює його автоматично для кожного завантаженого класу, і саме
 * через нього ми питаємо "які в тебе поля? які методи? хто твій батько?".</p>
 *
 * <h4>Три способи отримати об'єкт Class:</h4>
 * <pre>
 *   Class&lt;Smartphone&gt; c1 = Smartphone.class;                   // 1) якщо клас відомий на етапі компіляції
 *   Class&lt;?&gt; c2 = phone.getClass();                            // 2) якщо є готовий об'єкт
 *   Class&lt;?&gt; c3 = Class.forName("ua.com....Smartphone");       // 3) якщо є лише РЯДОК з іменем
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> уявіть закриту коробку з технікою. Звичайний код —
 * це інструкція користувача: ви можете лише натискати кнопки, які виробник вивів
 * назовні. Рефлексія — це рентген: ви бачите всю начинку всередині, навіть ті
 * деталі, які виробник ховав, і можете дотягнутися до них викруткою.</p>
 *
 * <p><b>Реальне застосування:</b> рефлексія лежить в основі більшості сучасних
 * Java-фреймворків. <b>Spring</b> створює ваші об'єкти-біни, хоча ніколи не бачив
 * ваших класів. <b>JUnit</b> знаходить і запускає методи з {@code @Test}.
 * <b>Jackson/Gson</b> перетворюють JSON на об'єкти, розкладаючи значення по полях.
 * <b>Hibernate</b> зіставляє поля класу з колонками таблиці.</p>
 */
public class Example01_ReflectionIntro {

    public static void main(String[] args) throws Exception {

        // === 1. Три способи отримати об'єкт Class ===
        // Сценарій: у нас є смартфон, і ми хочемо дізнатися про його клас усе.
        Smartphone phone = new Smartphone("Samsung", "Galaxy S24", 32999.0);

        Class<Smartphone> fromLiteral = Smartphone.class;      // клас відомий заздалегідь
        Class<?> fromObject = phone.getClass();                // клас беремо з готового об'єкта
        Class<?> fromName = Class.forName(
                "ua.com.javarush.jsquad.m1.example01_reflection_intro.Smartphone"); // клас із рядка

        System.out.println("1. Три способи отримати Class:");
        System.out.println("   Smartphone.class    -> " + fromLiteral.getSimpleName());
        System.out.println("   phone.getClass()    -> " + fromObject.getSimpleName());
        System.out.println("   Class.forName(...)  -> " + fromName.getSimpleName());

        // Найважливіше: об'єкт Class для класу в JVM ОДИН. Тому це той самий об'єкт.
        System.out.println("   Це один і той самий об'єкт? " + (fromLiteral == fromObject));
        System.out.println("   А з forName()?             " + (fromLiteral == fromName));

        System.out.println();

        // === 2. Класу навіть не треба знати наперед — достатньо рядка ===
        // Саме так працюють конфіги: у файлі написано ім'я класу, програма його завантажує.
        // Це і є "без знання їх імен" з визначення рефлексії.
        String classNameFromConfig = "java.util.ArrayList";    // уявімо, це прочитано з config.properties
        Class<?> configured = Class.forName(classNameFromConfig);

        System.out.println("2. Клас, ім'я якого прийшло рядком із конфігу:");
        System.out.println("   рядок у конфізі: " + classNameFromConfig);
        System.out.println("   завантажений клас: " + configured.getSimpleName());

        // Якщо такого класу немає — дізнаємось про це лише під час ВИКОНАННЯ:
        try {
            Class.forName("com.example.NoSuchClass");
        } catch (ClassNotFoundException e) {
            System.out.println("   помилковий рядок -> ClassNotFoundException (а не помилка компіляції!)");
        }

        System.out.println();

        // === 3. Основна інформація про клас ===
        System.out.println("3. Паспорт класу Smartphone:");
        System.out.println("   Повне ім'я (getName):          " + fromLiteral.getName());
        System.out.println("   Коротке ім'я (getSimpleName):  " + fromLiteral.getSimpleName());
        System.out.println("   Пакет (getPackageName):        " + fromLiteral.getPackageName());
        System.out.println("   Батьківський клас:             " + fromLiteral.getSuperclass().getSimpleName());

        // Модифікатори повертаються цілим числом — розшифровує їх клас Modifier
        int modifiers = fromLiteral.getModifiers();
        System.out.println("   Модифікатори (число):          " + modifiers);
        System.out.println("   Модифікатори (текст):          " + Modifier.toString(modifiers));
        System.out.println("   public?                        " + Modifier.isPublic(modifiers));
        System.out.println("   abstract?                      " + Modifier.isAbstract(modifiers));

        System.out.println();

        // === 4. Які інтерфейси реалізує клас ===
        // Один із пунктів "можливостей рефлексії": дізнатися, що саме реалізує клас.
        System.out.println("4. Інтерфейси класу Smartphone:");
        Class<?>[] interfaces = fromLiteral.getInterfaces();
        for (Class<?> iface : interfaces) {
            System.out.println("   - " + iface.getSimpleName());
        }

        // Перевірка "чи є цей клас нащадком/реалізацією" — аналог instanceof, але динамічний
        System.out.println("   Device можна присвоїти Smartphone? " + Device.class.isAssignableFrom(fromLiteral));
        System.out.println("   phone є екземпляром Device?        " + Device.class.isInstance(phone));

        System.out.println();

        // === 5. Скільки всього в класі (детально розберемо далі) ===
        // getDeclaredXxx — усе, що оголошено в САМОМУ класі (навіть private).
        // getXxx         — лише public, але разом з успадкованими.
        System.out.println("5. Побіжний огляд вмісту класу:");
        System.out.println("   getDeclaredFields():       " + fromLiteral.getDeclaredFields().length + " полів (усі, навіть private)");
        System.out.println("   getFields():               " + fromLiteral.getFields().length + " полів (лише public)");
        System.out.println("   getDeclaredMethods():      " + fromLiteral.getDeclaredMethods().length + " методів свого класу");
        System.out.println("   getMethods():              " + fromLiteral.getMethods().length + " методів разом з успадкованими від Object");
        System.out.println("   getDeclaredConstructors(): " + fromLiteral.getDeclaredConstructors().length + " конструктор(ів)");

        System.out.println();

        // === 6. Class є не тільки в класів ===
        // Примітиви, масиви, інтерфейси, enum — у всіх є свій об'єкт Class.
        System.out.println("6. Class буває різний:");
        printKind(Smartphone.class);
        printKind(Device.class);
        printKind(int.class);
        printKind(String[].class);
        printKind(java.time.DayOfWeek.class);
    }

    /**
     * Друкує, до якої "категорії" належить переданий клас.
     * Цей метод не знає наперед, що йому передадуть, — типова рефлексивна логіка.
     */
    private static void printKind(Class<?> type) {
        String kind;
        if (type.isPrimitive()) {
            kind = "примітивний тип";
        } else if (type.isArray()) {
            kind = "масив, тип елемента: " + type.getComponentType().getSimpleName();
        } else if (type.isEnum()) {
            kind = "enum";
        } else if (type.isInterface()) {
            kind = "інтерфейс";
        } else {
            kind = "звичайний клас";
        }
        System.out.println("   " + String.format("%-12s", type.getSimpleName()) + " -> " + kind);
    }
}
