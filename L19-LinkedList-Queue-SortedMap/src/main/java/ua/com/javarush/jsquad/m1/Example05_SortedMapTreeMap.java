package ua.com.javarush.jsquad.m1;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Лекція 19: LinkedList, Queue, SortedMap — Інтерфейс SortedMap і TreeMap
 *
 * SortedMap — інтерфейс, що розширює Map і гарантує, що ключі завжди
 * зберігаються у відсортованому порядку. Основна реалізація — TreeMap.
 *
 * Додаткові методи SortedMap (яких немає у звичайній Map):
 *   firstKey()              — найменший (перший) ключ
 *   lastKey()               — найбільший (останній) ключ
 *   headMap(toKey)           — підмапа з ключами < toKey
 *   tailMap(fromKey)         — підмапа з ключами >= fromKey
 *   subMap(fromKey, toKey)   — підмапа з ключами від fromKey (включно) до toKey (виключно)
 *   comparator()             — повертає Comparator або null (якщо природний порядок)
 *
 * Синтаксис:
 *   SortedMap<K, V> map = new TreeMap<>();
 *
 * Аналогія з життя: алфавітний довідник міст. Можна миттєво знайти перше
 * та останнє місто, отримати всі міста від «А» до «К» (headMap), або
 * від «Л» до «Р» (subMap) — як вирвати сторінки з довідника.
 *
 * Реальне застосування: діапазонні запити (всі записи за період), алфавітні
 * індекси, навігація по даних, розбиття даних на сегменти.
 */
public class Example05_SortedMapTreeMap {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Створення SortedMap — довідник міст
        // ============================================================
        System.out.println("=== Блок 1: Створення SortedMap ===");

        // Місто → населення (тисяч)
        SortedMap<String, Integer> cities = new TreeMap<>();
        cities.put("Київ", 2967);
        cities.put("Харків", 1419);
        cities.put("Одеса", 1015);
        cities.put("Дніпро", 968);
        cities.put("Львів", 717);
        cities.put("Запоріжжя", 722);
        cities.put("Миколаїв", 476);
        cities.put("Полтава", 284);
        cities.put("Чернігів", 285);
        cities.put("Вінниця", 370);

        System.out.println("Міста (відсортовані за алфавітом):");
        for (Map.Entry<String, Integer> entry : cities.entrySet()) {
            System.out.println("  " + entry.getKey() + " — " + entry.getValue() + " тис.");
        }
        System.out.println();

        // ============================================================
        //   Блок 2: firstKey() і lastKey()
        // ============================================================
        System.out.println("=== Блок 2: firstKey / lastKey ===");

        System.out.println("Перше місто (за алфавітом): " + cities.firstKey());  // Вінниця
        System.out.println("Останнє місто (за алфавітом): " + cities.lastKey()); // Чернігів
        System.out.println();

        // ============================================================
        //   Блок 3: headMap(toKey) — все ДО вказаного ключа
        // ============================================================
        System.out.println("=== Блок 3: headMap — міста до 'Л' ===");

        // headMap("Л") — всі ключі, що менші за "Л" (не включаючи "Л")
        SortedMap<String, Integer> beforeL = cities.headMap("Л");
        System.out.println("Міста до літери 'Л': " + beforeL.keySet());
        System.out.println();

        // ============================================================
        //   Блок 4: tailMap(fromKey) — все ПОЧИНАЮЧИ з вказаного ключа
        // ============================================================
        System.out.println("=== Блок 4: tailMap — міста від 'М' ===");

        // tailMap("М") — всі ключі >= "М"
        SortedMap<String, Integer> fromM = cities.tailMap("М");
        System.out.println("Міста від літери 'М': " + fromM.keySet());
        System.out.println();

        // ============================================================
        //   Блок 5: subMap(from, to) — діапазон ключів
        // ============================================================
        System.out.println("=== Блок 5: subMap — діапазон ===");

        // subMap("К", "П") — від "К" (включно) до "П" (виключно)
        SortedMap<String, Integer> kToP = cities.subMap("К", "П");
        System.out.println("Міста від 'К' до 'П' (виключно):");
        for (Map.Entry<String, Integer> entry : kToP.entrySet()) {
            System.out.println("  " + entry.getKey() + " — " + entry.getValue() + " тис.");
        }
        System.out.println();

        // ============================================================
        //   Блок 6: comparator() — перевірка порядку сортування
        // ============================================================
        System.out.println("=== Блок 6: comparator ===");

        // Природний порядок — comparator() повертає null
        Comparator<? super String> comp = cities.comparator();
        System.out.println("Comparator для cities: " + comp); // null — природний порядок

        // З власним Comparator
        SortedMap<String, Integer> reversed = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a); // зворотний порядок
            }
        });
        reversed.putAll(cities);

        System.out.println("Comparator для reversed: " + reversed.comparator()); // не null
        System.out.println("Зворотний порядок — перше місто: " + reversed.firstKey()); // Чернігів
        System.out.println("Зворотний порядок — останнє місто: " + reversed.lastKey()); // Вінниця
    }
}
