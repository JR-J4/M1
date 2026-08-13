package ua.com.javarush.jsquad.m1.example03_instanceof_pattern;

/**
 * Модуль 2. Рівень 7. Приведення типів
 * <hr>
 * <h3>Тема: {@code instanceof} із зіставленням зі зразком (Java 14+)</h3>
 *
 * <p>Раніше після перевірки {@code instanceof} доводилося ще й окремо звужувати тип:</p>
 * <pre>
 *   if (o instanceof String) {
 *       ((String) o).toLowerCase();   // громіздке явне приведення
 *   }
 * </pre>
 *
 * <p>Починаючи з Java 14, перевірку й звуження можна об'єднати в одному рядку —
 * прямо у {@code instanceof} оголошується вже готова змінна потрібного типу:</p>
 * <pre>
 *   if (o instanceof String s) {   // якщо o — String, s вже його "звужена" копія
 *       s.toLowerCase();
 *   }
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> замість "перевір, чи це водій" + "тепер дай мені його
 * посвідчення водія" — одна дія: "якщо це водій, ось одразу його посвідчення".</p>
 *
 * <p><b>Реальне застосування:</b> обробка різнорідних даних без зайвого приведення —
 * код коротший і безпечніший (не можна забути перевірку перед звуженням).</p>
 */
public class Example03_InstanceofPattern {

    public static void main(String[] args) {

        Object value = "Привіт, JavaRush";

        // === 1. СТАРИЙ спосіб: перевірка + окреме явне звуження ===
        System.out.println("=== Старий спосіб (до Java 14) ===");
        if (value instanceof String) {
            String s = (String) value;          // доводиться звужувати вручну
            System.out.println("  довжина рядка: " + s.length());
        }
        System.out.println();

        // === 2. НОВИЙ спосіб: змінна оголошується прямо в instanceof ===
        System.out.println("=== Новий спосіб (Java 14+) ===");
        if (value instanceof String s) {         // перевірка + звуження в одному рядку
            System.out.println("  у верхньому регістрі: " + s.toUpperCase());
        }
        System.out.println();

        // === 3. Змінну зі зразка можна одразу використати в умові ===
        System.out.println("=== Зразок + додаткова умова ===");
        Object input = "root";
        if (input instanceof String s && s.length() <= 4) {
            System.out.println("  короткий логін прийнято: " + s);
        }
        System.out.println();

        // === 4. Обробка "мішка" різнорідних даних ===
        System.out.println("=== Форматуємо різнорідні значення ===");
        Object[] data = {100, "hello", 4.5, true};
        for (Object item : data) {
            String result;
            if (item instanceof Integer i) {
                result = "ціле, подвоєне = " + (i * 2);
            } else if (item instanceof String str) {
                result = "рядок довжиною " + str.length();
            } else if (item instanceof Double d) {
                result = "дробове, округлене = " + Math.round(d);
            } else {
                result = "тип без спец-обробки: " + item;
            }
            System.out.println("  " + item + "  ->  " + result);
        }
        System.out.println();

        System.out.println("Головне: instanceof зі зразком = перевірка та звуження типу в один рух.");
    }
}
