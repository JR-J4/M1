package ua.com.javarush.jsquad.m1;

import java.util.HashMap;
import java.util.Map;

/**
 * Лекція 18: Колекції Map — Ітерація по HashMap
 *
 * Три способи обходу (ітерації) HashMap:
 *   1. keySet() + get(key)   — отримуємо ключі, потім за ключем — значення
 *   2. entrySet()            — отримуємо пари Map.Entry (ключ + значення разом)
 *   3. values()              — якщо потрібні тільки значення (без ключів)
 *
 * Синтаксис entrySet():
 *   for (Map.Entry<K, V> entry : map.entrySet()) {
 *       entry.getKey();
 *       entry.getValue();
 *   }
 *
 * Аналогія з життя: журнал оцінок у школі — прізвище учня (ключ) → оцінка
 * (значення). Вчитель може переглянути: лише прізвища, лише оцінки, або
 * повний список «прізвище — оцінка».
 *
 * Реальне застосування: звіти, формування таблиць, експорт даних у CSV,
 * логування вмісту конфігурацій.
 */
public class Example03_HashMapIteration {

    public static void main(String[] args) {

        // Журнал оцінок: прізвище → оцінка за контрольну
        HashMap<String, Integer> grades = new HashMap<>();
        grades.put("Коваленко", 92);
        grades.put("Шевченко", 85);
        grades.put("Бондаренко", 97);
        grades.put("Мельник", 78);
        grades.put("Ткаченко", 88);

        // ============================================================
        //   Спосіб 1: keySet() + get() — через ключі
        // ============================================================
        System.out.println("=== Спосіб 1: keySet() + get() ===");

        // Уявіть: вчитель дивиться список прізвищ і для кожного шукає оцінку
        for (String student : grades.keySet()) {
            int grade = grades.get(student);
            System.out.println("  " + student + " — " + grade + " балів");
        }
        System.out.println();

        // ============================================================
        //   Спосіб 2: entrySet() — через пари Map.Entry
        // ============================================================
        System.out.println("=== Спосіб 2: entrySet() (рекомендований) ===");

        // Ефективніший спосіб — отримуємо ключ і значення одразу, без повторного пошуку
        for (Map.Entry<String, Integer> entry : grades.entrySet()) {
            String student = entry.getKey();
            int grade = entry.getValue();
            System.out.println("  " + student + " → " + grade + " балів");
        }
        System.out.println();

        // ============================================================
        //   Спосіб 3: values() — тільки значення
        // ============================================================
        System.out.println("=== Спосіб 3: values() — тільки оцінки ===");

        // Уявіть: директору потрібна лише статистика оцінок (без прізвищ)
        int sum = 0;
        int count = 0;
        for (int grade : grades.values()) {
            System.out.println("  Оцінка: " + grade);
            sum += grade;
            count++;
        }
        double average = (double) sum / count;
        System.out.println("Середній бал групи: " + average);
        System.out.println();

        // ============================================================
        //   Блок 4: Практичний приклад — підрахунок частоти слів
        // ============================================================
        System.out.println("=== Блок 4: Підрахунок частоти слів ===");

        String[] words = {"java", "map", "java", "set", "list", "map", "java", "set"};
        HashMap<String, Integer> frequency = new HashMap<>();

        for (String word : words) {
            if (frequency.containsKey(word)) {
                frequency.put(word, frequency.get(word) + 1);
            } else {
                frequency.put(word, 1);
            }
        }

        System.out.println("Частота слів:");
        for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
            System.out.println("  \"" + entry.getKey() + "\" — " + entry.getValue() + " раз(ів)");
        }
    }
}
