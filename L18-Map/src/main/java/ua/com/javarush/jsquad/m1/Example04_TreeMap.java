package ua.com.javarush.jsquad.m1;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Лекція 18: Колекції Map — TreeMap (сортована мапа)
 *
 * TreeMap — реалізація Map, що зберігає ключі у відсортованому порядку.
 *   - За замовчуванням: природний порядок (alphabetical для String, числовий для Integer)
 *   - З Comparator: довільний порядок сортування
 *
 * Синтаксис:
 *   TreeMap<K, V> map = new TreeMap<>();                    // природний порядок
 *   TreeMap<K, V> map = new TreeMap<>(comparator);          // свій порядок
 *
 * Важливо: TreeMap НЕ допускає null-ключі (на відміну від HashMap).
 *
 * Аналогія з життя: TreeMap — це рейтинг студентів, вивішений на дошці
 * оголошень. Прізвища автоматично розташовані за алфавітом (або за балами),
 * і при додаванні нового студента він одразу стає на своє місце.
 *
 * Реальне застосування: рейтинги, таблиці лідерів, алфавітні довідники,
 * діапазонні запити (всі ключі від A до M).
 */
public class Example04_TreeMap {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: TreeMap з природним порядком (алфавітний)
        // ============================================================
        System.out.println("=== Блок 1: TreeMap — алфавітний порядок ===");

        // Рейтинг студентів: прізвище → середній бал
        TreeMap<String, Double> studentRating = new TreeMap<>();
        studentRating.put("Шевченко", 4.5);
        studentRating.put("Коваленко", 4.8);
        studentRating.put("Андрієнко", 3.9);
        studentRating.put("Мельник", 4.2);
        studentRating.put("Бондаренко", 4.7);

        // Виводимо — ключі автоматично відсортовані за алфавітом
        System.out.println("Рейтинг (алфавітний порядок прізвищ):");
        for (Map.Entry<String, Double> entry : studentRating.entrySet()) {
            System.out.println("  " + entry.getKey() + " — " + entry.getValue());
        }
        System.out.println();

        // ============================================================
        //   Блок 2: TreeMap з Comparator (зворотний порядок)
        // ============================================================
        System.out.println("=== Блок 2: TreeMap зі зворотним порядком ===");


        // Comparator для зворотного алфавітного порядку
        Comparator<String> reverseOrder = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a); // зворотний порядок
            }
        };

        TreeMap<String, Double> reverseRating = new TreeMap<>(reverseOrder);
        reverseRating.put("Шевченко", 4.5);
        reverseRating.put("Коваленко", 4.8);
        reverseRating.put("Андрієнко", 3.9);
        reverseRating.put("Мельник", 4.2);
        reverseRating.put("Бондаренко", 4.7);

        System.out.println("Рейтинг (зворотний алфавітний порядок):");
        for (Map.Entry<String, Double> entry : reverseRating.entrySet()) {
            System.out.println("  " + entry.getKey() + " — " + entry.getValue());
        }
        System.out.println();

        // ============================================================
        //   Блок 3: TreeMap з числовими ключами
        // ============================================================
        System.out.println("=== Блок 3: TreeMap з числовими ключами ===");

        // Місце в рейтингу → прізвище (числа сортуються від меншого до більшого)
        TreeMap<Integer, String> leaderboard = new TreeMap<>();
        leaderboard.put(3, "Мельник");
        leaderboard.put(1, "Коваленко");
        leaderboard.put(5, "Андрієнко");
        leaderboard.put(2, "Бондаренко");
        leaderboard.put(4, "Шевченко");

        System.out.println("Таблиця лідерів:");
        for (Map.Entry<Integer, String> entry : leaderboard.entrySet()) {
            System.out.println("  Місце " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println();

        // ============================================================
        //   Блок 4: Особливість — null-ключ заборонений
        // ============================================================
        System.out.println("=== Блок 4: null-ключ у TreeMap ===");

        System.out.println("HashMap допускає null-ключ, а TreeMap — НІ!");
        System.out.println("Спроба treeMap.put(null, value) призведе до NullPointerException.");
        System.out.println("Причина: TreeMap порівнює ключі (compareTo/compare),");
        System.out.println("а null не можна порівнювати ні з чим.");

        // Порівняння HashMap vs TreeMap
        System.out.println();
        System.out.println("HashMap vs TreeMap:");
        System.out.println("  HashMap — швидший (O(1)), без порядку, допускає null-ключ");
        System.out.println("  TreeMap — повільніший (O(log n)), ключі відсортовані, НЕ допускає null-ключ");
    }
}
