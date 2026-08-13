package ua.com.javarush.jsquad.m1;

/**
 * Модуль 2. Рівень 17. Reflection API — ЗМІСТ ПРИКЛАДІВ
 *
 * <p>Лекція охоплює: Reflection API, отримання даних за допомогою рефлексії,
 * створення об'єктів за допомогою рефлексії, зміну внутрішнього стану об'єкта
 * та Dynamic Proxy.</p>
 *
 * <p>Кожен приклад — самодостатній клас зі своїм {@code main()}.
 * Запускайте їх по черзі (відкрийте файл і натисніть ▶ біля {@code main}).</p>
 *
 * <pre>
 *  №   Тема                                          Клас для запуску
 *  ────────────────────────────────────────────────────────────────────────────────────────────
 *  01  Що таке рефлексія, об'єкт Class               example01_reflection_intro.Example01_ReflectionIntro
 *  02  Можливості та мінуси рефлексії                example02_pros_cons.Example02_ProsAndCons
 *  03  Поля: клас Field                              example03_fields.Example03_Fields
 *  04  Методи: клас Method                           example04_methods.Example04_Methods
 *  05  Конструктори: клас Constructor                example05_constructors.Example05_Constructors
 *  06  Створення об'єктів (два newInstance)          example06_creating_objects.Example06_CreatingObjects
 *  07  Зміна внутрішнього стану об'єкта              example07_field_state.Example07_FieldState
 *  08  private та final: setAccessible()             example08_private_and_final.Example08_PrivateAndFinal
 *  09  Виклик методів: Method.invoke() + міні-JUnit  example09_invoke_methods.Example09_InvokeMethods
 *  10  Dynamic Proxy та InvocationHandler            example10_dynamic_proxy.Example10_DynamicProxy
 *  11  Підсумок: власний міні-Spring                 example11_summary.Example11_Summary
 * </pre>
 *
 * <h4>Коротка шпаргалка:</h4>
 * <pre>
 *   ОТРИМАТИ Class:
 *     Cat.class                  клас відомий на етапі компіляції
 *     cat.getClass()             є готовий об'єкт
 *     Class.forName("ua...Cat")  є лише рядок з іменем
 *
 *   ДВА СІМЕЙСТВА МЕТОДІВ:
 *     getFields() / getMethods() / getConstructors()
 *                                → лише public, ЗАТЕ разом з успадкованими
 *     getDeclaredFields() / getDeclaredMethods() / getDeclaredConstructors()
 *                                → УСЕ своє, включно з private, без успадкованого
 *
 *   СТВОРИТИ ОБ'ЄКТ:
 *     Cat.class.newInstance()                          застарів з Java 9
 *     Cat.class.getDeclaredConstructor().newInstance() сучасний спосіб
 *     constructor.newInstance(арг1, арг2)              будь-який конструктор
 *
 *   ПОЛЕ:
 *     field.get(об'єкт)             прочитати      (для static → get(null))
 *     field.set(об'єкт, значення)   записати       (ДВА аргументи, не один!)
 *     field.setInt/setBoolean/...   типізовано
 *     field.setAccessible(true)     відкрити private; потім поверніть false
 *
 *   МЕТОД:
 *     method.invoke(об'єкт, арг1, арг2)   виклик    (для static → invoke(null, ...))
 *     виняток методу → InvocationTargetException, причина в getCause()
 *
 *   DYNAMIC PROXY:
 *     Proxy.newProxyInstance(loader, new Class[]{Інтерфейс.class}, handler)
 *     InvocationHandler.invoke(proxy, method, args) — сюди йдуть УСІ виклики
 *     працює лише з інтерфейсами
 *
 *   ЩО ЗМІНИТИ НЕ ВИЙДЕ:
 *     static final поле            → IllegalAccessException
 *     поля класів JDK              → InaccessibleObjectException (закриті модулі)
 *     final з константним виразом  → поле зміниться, а поведінка коду ні
 *
 *   Правило: рефлексія — інструмент фреймворків (Spring, JUnit, Jackson,
 *            Hibernate). У звичайному коді спершу шукайте рішення без неї.
 * </pre>
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Модуль 2. Рівень 17 — Reflection API");
        System.out.println("11 прикладів у пакетах example01..example11.");
        System.out.println("Теми: об'єкт Class, класи Field/Method/Constructor, створення об'єктів,");
        System.out.println("      зміна стану об'єкта, setAccessible(), Method.invoke(), Dynamic Proxy.");
        System.out.println("Відкрийте потрібний ExampleNN_*.java і запустіть його main().");

        //  0x00000001 = 1
        //  0x00000011 = 16 + 1 = 17

        // 0000 0011 = 3
    }
}
