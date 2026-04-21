package ua.com.javarush.jsquad.m1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Collection;

/**
 * Лекція 18: Колекції Map — Методи HashMap
 *
 * Повний набір основних методів HashMap:
 *   put(key, value)    — додати/оновити пару
 *   get(key)           — отримати значення за ключем (або null)
 *   containsKey(key)   — чи є такий ключ?
 *   containsValue(val) — чи є таке значення?
 *   remove(key)        — видалити пару за ключем
 *   clear()            — очистити всю мапу
 *   size()             — кількість пар
 *   keySet()           — множина всіх ключів
 *   values()           — колекція всіх значень
 *   entrySet()         — множина всіх пар (Map.Entry)
 *
 * Аналогія з життя: каталог товарів інтернет-магазину — артикул товару (ключ)
 * відповідає ціні (значення). Менеджер може шукати товар за артикулом,
 * перевіряти наявність, видаляти з продажу, бачити всі артикули чи всі ціни.
 *
 * Реальне застосування: каталоги продуктів, кеш цін, словники перекладу,
 * реєстри користувачів.
 */
public class Example02_HashMapMethods {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Створення каталогу товарів (артикул → ціна)
        // ============================================================
        System.out.println("=== Блок 1: Створення каталогу ===");

        HashMap<String, Double> catalog = new HashMap<>();
        catalog.put("ART-001", 1299.99);  // Ноутбук
        catalog.put("ART-002", 549.50);   // Планшет
        catalog.put("ART-003", 89.90);    // Навушники
        catalog.put("ART-004", 2199.00);  // Смартфон
        catalog.put("ART-005", 349.99);   // Монітор

        System.out.println("Каталог: " + catalog);
        System.out.println();

        // ============================================================
        //   Блок 2: get() і containsKey() / containsValue()
        // ============================================================
        System.out.println("=== Блок 2: Пошук у каталозі ===");

        // get — отримати ціну за артикулом
        Double price = catalog.get("ART-003");
        System.out.println("Ціна ART-003 (навушники): " + price + " грн");

        // containsKey — чи є товар з таким артикулом?
        System.out.println("Є ART-002? " + catalog.containsKey("ART-002")); // true
        System.out.println("Є ART-099? " + catalog.containsKey("ART-099")); // false

        // containsValue — чи є товар з такою ціною?
        System.out.println("Є товар за 549.50? " + catalog.containsValue(549.50)); // true
        System.out.println("Є товар за 100.00? " + catalog.containsValue(100.00)); // false
        System.out.println();

        // ============================================================
        //   Блок 3: remove() і size()
        // ============================================================
        System.out.println("=== Блок 3: Видалення та розмір ===");

        System.out.println("Розмір до видалення: " + catalog.size()); // 5
        catalog.remove("ART-002"); // планшет знято з продажу
        System.out.println("Після видалення ART-002: " + catalog.size()); // 4
        System.out.println("get(ART-002) = " + catalog.get("ART-002")); // null
        System.out.println();

        // ============================================================
        //   Блок 4: keySet(), values(), entrySet()
        // ============================================================
        System.out.println("=== Блок 4: Перегляд вмісту ===");

        // keySet — всі артикули
        Set<String> articles = catalog.keySet();
        System.out.println("Артикули: " + articles);

        // values — всі ціни
        Collection<Double> prices = catalog.values();
        System.out.println("Ціни: " + prices);

        // entrySet — всі пари
        Set < Map.Entry<String, Double> > entries = catalog.entrySet();
        System.out.println("Пари (артикул=ціна):");
        for (Map.Entry<String, Double> entry : entries) {
            System.out.println("  " + entry.getKey() + " → " + entry.getValue() + " грн");
        }
        System.out.println();

        catalog.forEach( (k, v) -> {
            System.out.println(k + " " + v);
        });


        // ============================================================
        //   Блок 5: clear() — повне очищення
        // ============================================================
        System.out.println("=== Блок 5: Очищення каталогу ===");

        System.out.println("Розмір до clear(): " + catalog.size());
        catalog.clear();
        System.out.println("Розмір після clear(): " + catalog.size()); // 0
        System.out.println("Каталог порожній: " + catalog);
    }
}
