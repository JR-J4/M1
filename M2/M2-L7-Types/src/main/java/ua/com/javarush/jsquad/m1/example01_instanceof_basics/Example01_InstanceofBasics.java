package ua.com.javarush.jsquad.m1.example01_instanceof_basics;

/**
 * Модуль 2. Рівень 7. Приведення типів
 * <hr>
 * <h3>Тема: Оператор {@code instanceof} — перевірка типу об'єкта</h3>
 *
 * <p>Оператор {@code instanceof} перевіряє, чи є об'єкт об'єктом певного класу.
 * Результат — {@code boolean} ({@code true} або {@code false}).</p>
 *
 * <h4>Синтаксис:</h4>
 * <pre>
 *   об'єкт instanceof Клас
 *
 *   Object o = 3;                       // насправді Integer
 *   boolean isInt = o instanceof Integer;   // true
 *   boolean isStr = o instanceof String;    // false
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> охоронець на вході дивиться на ваш документ і питає:
 * "Це паспорт?". Відповідь — так/ні. Так само {@code instanceof} питає в об'єкта:
 * "Ти належиш до цього типу?".</p>
 *
 * <p><b>Реальне застосування:</b> коли в одній колекції лежать різнорідні об'єкти
 * (числа, рядки, дати) — перед обробкою треба з'ясувати, з чим саме ми маємо справу.</p>
 */
public class Example01_InstanceofBasics {

    public static void main(String[] args) {

        // === 1. Найпростіша перевірка (приклад із лекції) ===
        System.out.println("=== Базова перевірка instanceof ===");
        Object o1 = 3;                          // у змінну Object поклали Integer


        boolean isInt = o1 instanceof Integer;  // об'єкт справді Integer
        boolean isStr = o1 instanceof String;   // але точно не String
        System.out.println("o1 = 3");
        System.out.println("o1 instanceof Integer -> " + isInt); // true
        System.out.println("o1 instanceof String  -> " + isStr); // false
        System.out.println();

        // === 2. Той самий об'єкт, інший вміст ===
        System.out.println("=== Тепер у змінній рядок ===");
        Object o2 = "Мама";                     // тепер це String
        System.out.println("o2 = \"Мама\"");
        System.out.println("o2 instanceof Integer -> " + (o2 instanceof Integer)); // false
        System.out.println("o2 instanceof String  -> " + (o2 instanceof String));  // true
        System.out.println();

        // === 3. Розбираємо "мішок" різнорідних об'єктів ===
        System.out.println("=== Визначаємо тип кожного елемента ===");
        Object[] items = {42, "текст", 3.14, true, 'A'};
        for (Object item : items) {
            String type;
            if (item instanceof Integer i) {
                type = "ціле число (Integer)";
                i.byteValue();

            } else if (item instanceof String) {
                type = "рядок (String)";
            } else if (item instanceof Double) {
                type = "дробове число (Double)";
            } else if (item instanceof Boolean) {
                type = "логічне значення (Boolean)";
            } else {
                type = "щось інше";
            }
            System.out.println("  " + item + "  ->  " + type);
        }
        System.out.println();

        // === 4. Важлива пастка: null instanceof завжди false ===
        System.out.println("=== Перевірка null ===");
        String empty = null;
        // null не посилається на жоден об'єкт, тому перевірка завжди false
        System.out.println("null instanceof String -> " + (empty instanceof String)); // false
        System.out.println("Тому instanceof — це ще й безпечна перевірка на null:");
        System.out.println("вона не кине NullPointerException.");
        System.out.println();

        System.out.println("Головне: instanceof відповідає на питання \"це об'єкт такого типу?\".");
    }
}
