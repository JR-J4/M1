package ua.com.javarush.jsquad.m1.example07_switch_expression;

import java.util.HashMap;
import java.util.Map;

/**
 * Модуль 2. Рівень 7. Приведення типів
 * <hr>
 * <h3>Тема: Switch expression — сучасний switch зі стрілкою (Java 14+)</h3>
 *
 * <p>Switch expression офіційно з'явився в Java 14. Синтаксис зі стрілкою ({@code ->})
 * прийшов із функціонального програмування й дає дві переваги:</p>
 * <ul>
 *   <li>не потрібен {@code break} — виконується лише одна гілка, "провалу" немає;</li>
 *   <li>switch тепер <b>повертає значення</b>, яке можна зберегти у змінну або
 *       повернути через {@code return}.</li>
 * </ul>
 *
 * <h4>Синтаксис:</h4>
 * <pre>
 *   String type = switch (product) {
 *       case "Apple", "Peach" -> "Fruit";   // кілька міток через кому
 *       case "Raspberry"      -> "Berry";
 *       default               -> "other";
 *   };
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> замість довгого бланка, де на кожному пункті треба
 * ставити "стоп" (break), — одне коротке питання й одразу готова відповідь.</p>
 *
 * <p><b>Реальне застосування:</b> перетворення "значення -&gt; результат" (категорія,
 * підпис, ціна) в один компактний і безпечний вираз.</p>
 */
public class Example07_SwitchExpression {

    public static void main(String[] args) {

        // === 1. Стрілка + кілька міток -> результат одразу в змінну ===
        System.out.println("=== switch expression зі стрілкою ===");
        String product = "Peach";
        String productType = switch (product) {
            case "Apple", "Peach" -> "Фрукт";   // одна гілка на дві мітки
            case "Raspberry"      -> "Ягода";
            default               -> "інше";
        };
        System.out.println("  " + product + " -> " + productType);
        System.out.println();

        // === 2. Немає break — і немає "провалу" в наступні case ===
        System.out.println("=== Число -> назва дня тижня ===");
        int day = 3;
        String dayName = switch (day) {
            case 1 -> "Понеділок";
            case 2 -> "Вівторок";
            case 3 -> "Середа";
            case 4 -> "Четвер";
            case 5 -> "П'ятниця";
            case 6, 7 -> "Вихідний";
            default -> "невідомий день";
        };
        System.out.println("  день " + day + " -> " + dayName);
        System.out.println();

        Map<String, Integer> discountValueByClientStatus = new HashMap<>();

        discountValueByClientStatus.put("GOLD", 20);
        discountValueByClientStatus.put("SILVER", 10);



        // === 3. switch expression як результат методу ===
        System.out.println("=== Обчислюємо знижку за статусом клієнта ===");
        System.out.println("  GOLD    -> " + discountFor("GOLD") + "%");
        System.out.println("  SILVER  -> " + discountFor("SILVER") + "%");
        System.out.println("  guest   -> " + discountFor("guest") + "%");
        System.out.println();



        String status = "ASDASD";

        System.out.println("  GOLD    -> " + discountValueByClientStatus.getOrDefault(status, 0) + "%");


        System.out.println("Головне: switch expression повертає значення й не потребує break.");
    }

    // switch expression можна одразу повернути через return
    private static int discountFor(String status) {
        if ("GOLD".equals(status)){
            return 20;
        } else if ("SILVER".equals(status)) {
            return 10;
        } else {
            return 0;
        }
    }
}
