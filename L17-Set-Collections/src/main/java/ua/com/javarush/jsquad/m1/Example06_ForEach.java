package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Лекція 17: Колекції Set — Цикл for-each
 *
 * Цикл for-each використовується для обходу ВСІХ елементів колекції.
 *
 * Синтаксис:
 *   for (Тип ім'я : колекція) {
 *       // ...
 *   }
 *
 * Де:
 *   - колекція — змінна, яку обходимо (List, Set, масив тощо)
 *   - Тип      — тип елементів колекції
 *   - ім'я     — змінна, що на кожному витку набуває чергового значення
 *
 * Важливо: під капотом for-each використовує ПРИХОВАНИЙ ітератор
 * (той самий, що ми вивчили в Example05). Тобто for-each — це красивий
 * скорочений запис для while(it.hasNext()) { it.next(); ... }.
 *
 * Особливо зручний для Set, бо в множини немає індексів і звичайний
 * for(int i = 0; i < size; i++) тут не підходить.
 *
 * Аналогія з життя: for-each — це як «кожному пасажиру в автобусі роздати квиток».
 * Не треба рахувати місця і ходити рядами — просто йдеш і віддаєш кожному.
 *
 * Реальне застосування: підрахунок суми, фільтрація, друк усіх елементів,
 * виклик однакової дії над кожним об'єктом колекції.
 */
public class Example06_ForEach {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: for-each з ArrayList (відомо з L16)
        // ============================================================
        System.out.println("=== for-each з ArrayList ===");

        ArrayList<String> cities = new ArrayList<>();
        cities.add("Київ");
        cities.add("Львів");
        cities.add("Одеса");
        cities.add("Харків");

        for (String city : cities) {
            System.out.println("Місто: " + city);
        }
        System.out.println();

        // ============================================================
        //   Приклад 2: for-each з HashSet — те саме, без змін
        // ============================================================
        System.out.println("=== for-each з HashSet ===");

        // Для Set це єдиний зручний спосіб обходу (немає індексів!)
        Set<String> tags = new HashSet<>();
        tags.add("java");
        tags.add("collections");
        tags.add("hashset");
        tags.add("iterator");

        for (String tag : tags) {
            System.out.println("Тег: #" + tag);
        }
        System.out.println();

        // ============================================================
        //   Приклад 3: Прихований ітератор (зв'язок з Example05)
        // ============================================================
        System.out.println("=== for-each == прихований ітератор ===");

        Set<String> letters = new HashSet<>();
        letters.add("A");
        letters.add("B");
        letters.add("C");

        System.out.println("Через for-each:");
        for (String l : letters) {
            System.out.println("  " + l);
        }

        System.out.println("Те саме через явний ітератор:");
        java.util.Iterator<String> it = letters.iterator();
        while (it.hasNext()) {
            String l = it.next();
            System.out.println("  " + l);
        }
        System.out.println("(обидва варіанти роблять одне й те саме)");
        System.out.println();

        // ============================================================
        //   Приклад 4: Сценарій — сума балів студентів з HashSet
        // ============================================================
        System.out.println("=== Сума балів студентів ===");

        // Набір студентських балів (унікальні ідентифікатори → унікальні бали)
        Set<Integer> scores = new HashSet<>();
        scores.add(85);
        scores.add(72);
        scores.add(91);
        scores.add(64);
        scores.add(88);

        int total = 0;
        int max = Integer.MIN_VALUE;
        for (int score : scores) {
            total += score;
            if (score > max) {
                max = score;
            }
        }

        double average = (double) total / scores.size();

        System.out.println("Бали:       " + scores);
        System.out.println("Сума:       " + total);
        System.out.println("Максимум:   " + max);
        System.out.println("Середній:   " + average);
    }
}
