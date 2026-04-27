package ua.com.javarush.jsquad.m1;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Лекція 19: LinkedList, Queue, SortedMap — SortedMap з Comparator
 *
 * TreeMap дозволяє задати свій Comparator для нестандартного порядку
 * сортування ключів. При цьому всі SortedMap-методи (headMap, tailMap,
 * subMap) працюють відповідно до цього порядку.
 *
 * Важливо розуміти: headMap/tailMap/subMap орієнтуються на порядок,
 * заданий Comparator, а не на «логічну» послідовність ключів.
 *
 * Аналогія з життя: табло результатів змагань з програмування.
 * Результати відсортовані за балами від найбільшого до найменшого.
 * Можна вивести «Топ-3» (headMap) або «аутсайдерів» (tailMap).
 *
 * Реальне застосування: рейтинги, таблиці лідерів, системи скорінгу,
 * розбиття даних на квартилі.
 */
public class Example06_SortedMapCustomComparator {

    public static void main(String[] args) {



        // ============================================================
        //   Блок 1: Табло змагань — сортування за балами (спадання)
        // ============================================================
        System.out.println("=== Блок 1: Табло змагань ===");

        // Comparator: від більшого до меншого балу
        Comparator<Integer> descending = new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a; // спадання
            }
        };

        // Бали → ім'я учасника
        TreeMap<Integer, String> scoreboard = new TreeMap<>(descending);
        scoreboard.put(95, "Олена");
        scoreboard.put(82, "Андрій");
        scoreboard.put(97, "Марія");
        scoreboard.put(88, "Дмитро");
        scoreboard.put(76, "Петро");
        scoreboard.put(91, "Ірина");

        System.out.println("Повне табло (за спаданням балів):");
        int place = 1;
        for (Map.Entry<Integer, String> entry : scoreboard.entrySet()) {
            System.out.println("  " + place + ". " + entry.getValue()
                    + " — " + entry.getKey() + " балів");
            place++;
        }
        System.out.println();

        // ============================================================
        //   Блок 2: headMap — Топ учасники
        // ============================================================
        System.out.println("=== Блок 2: headMap — Топ учасники ===");

        // При спадному порядку headMap(88) дає ключі «до 88» в цьому порядку,
        // тобто ключі > 88 (бо вони йдуть першими при спаданні)
        SortedMap<Integer, String> topScorers = scoreboard.headMap(88);
        System.out.println("Учасники з балами > 88 (headMap(88)):");
        for (Map.Entry<Integer, String> entry : topScorers.entrySet()) {
            System.out.println("  " + entry.getValue() + " — " + entry.getKey() + " балів");
        }
        System.out.println();

        // ============================================================
        //   Блок 3: tailMap — аутсайдери
        // ============================================================
        System.out.println("=== Блок 3: tailMap — аутсайдери ===");

        // tailMap(88) — від 88 і далі (в порядку спадання: 88, 82, 76)
        SortedMap<Integer, String> lowerScorers = scoreboard.tailMap(88);
        System.out.println("Учасники з балами <= 88 (tailMap(88)):");
        for (Map.Entry<Integer, String> entry : lowerScorers.entrySet()) {
            System.out.println("  " + entry.getValue() + " — " + entry.getKey() + " балів");
        }
        System.out.println();

        // ============================================================
        //   Блок 4: subMap — середня група
        // ============================================================
        System.out.println("=== Блок 4: subMap — середня група ===");

        // При спадному порядку: subMap(95, 82) дає елементи від 95 (вкл.) до 82 (викл.)
        // тобто: 95, 91, 88 (в порядку спадання)
        SortedMap<Integer, String> middleGroup = scoreboard.subMap(95, 82);
        System.out.println("Середня група (від 95 до 82, виключно):");
        for (Map.Entry<Integer, String> entry : middleGroup.entrySet()) {
            System.out.println("  " + entry.getValue() + " — " + entry.getKey() + " балів");
        }
        System.out.println();

        // ============================================================
        //   Блок 5: Підсумок — SortedMap vs HashMap
        // ============================================================
        System.out.println("=== Блок 5: SortedMap vs Map ===");
        System.out.println("Map (HashMap):");
        System.out.println("  + Швидкий пошук O(1)");
        System.out.println("  - Немає порядку");
        System.out.println("  - Немає headMap/tailMap/subMap");
        System.out.println();
        System.out.println("SortedMap (TreeMap):");
        System.out.println("  + Ключі завжди відсортовані");
        System.out.println("  + headMap/tailMap/subMap для діапазонних запитів");
        System.out.println("  + firstKey/lastKey");
        System.out.println("  - Повільніший O(log n)");
    }
}
