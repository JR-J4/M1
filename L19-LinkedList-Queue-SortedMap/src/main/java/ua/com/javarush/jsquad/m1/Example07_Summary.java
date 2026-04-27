package ua.com.javarush.jsquad.m1;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Лекція 19: LinkedList, Queue, SortedMap — Підсумок
 *
 * У цій лекції ми розглянули три теми:
 *   1. LinkedList — двозв'язний список (швидка вставка/видалення на початку)
 *   2. Queue      — інтерфейс черги (FIFO: offer/poll/peek)
 *   3. SortedMap  — відсортована мапа (firstKey/lastKey/headMap/tailMap/subMap)
 *
 * У цьому прикладі об'єднуємо всі три теми в одному реальному сценарії.
 *
 * Аналогія з життя: система доставки їжі.
 *   - Queue — черга замовлень, що надходять від клієнтів (FIFO)
 *   - LinkedList — список страв у замовленні (додаємо/видаляємо до підтвердження)
 *   - SortedMap (TreeMap) — зони доставки з часом, відсортовані за відстанню
 *
 * Реальне застосування: будь-яка система обробки замовлень, логістика,
 * маршрутизація, диспетчеризація.
 */
public class Example07_Summary {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Queue — черга замовлень від клієнтів
        // ============================================================
        System.out.println("=== Блок 1: Queue — черга замовлень ===");

        Queue<String> orderQueue = new LinkedList<>();
        orderQueue.offer("Замовлення #101 — Олена (вул. Хрещатик)");
        orderQueue.offer("Замовлення #102 — Андрій (пр. Шевченка)");
        orderQueue.offer("Замовлення #103 — Марія (вул. Франка)");
        orderQueue.offer("Замовлення #104 — Дмитро (пл. Ринок)");

        System.out.println("Нові замовлення у черзі: " + orderQueue.size());
        System.out.println();

        // ============================================================
        //   Блок 2: LinkedList — формування замовлення
        // ============================================================
        System.out.println("=== Блок 2: LinkedList — страви у замовленні ===");

        // Обробляємо перше замовлення з черги
        String currentOrder = orderQueue.poll();
        System.out.println("Обробляємо: " + currentOrder);

        // Клієнт формує список страв (може додавати/видаляти)
        LinkedList<String> dishes = new LinkedList<>();
        dishes.add("Піца Маргарита");
        dishes.add("Цезар з куркою");
        dishes.add("Картопля фрі");
        System.out.println("  Початкове замовлення: " + dishes);

        // Клієнт передумав — додає десерт і прибирає картоплю
        dishes.addLast("Тірамісу");
        dishes.remove("Картопля фрі");
        System.out.println("  Після змін: " + dishes);

        // Додаємо напій на перше місце (для швидкого приготування)
        dishes.addFirst("Лимонад");
        System.out.println("  Фінальне замовлення: " + dishes);
        System.out.println();

        // ============================================================
        //   Блок 3: SortedMap — зони доставки
        // ============================================================
        System.out.println("=== Блок 3: SortedMap — зони доставки ===");

        // Відстань (км) → назва зони (TreeMap сортує за ключем — відстанню)
        SortedMap<Integer, String> deliveryZones = new TreeMap<>();
        deliveryZones.put(2, "Центр");
        deliveryZones.put(5, "Середнє місто");
        deliveryZones.put(8, "Спальний район");
        deliveryZones.put(12, "Передмістя");
        deliveryZones.put(20, "Приміська зона");

        System.out.println("Зони доставки (за відстанню):");
        for (Map.Entry<Integer, String> zone : deliveryZones.entrySet()) {
            System.out.println("  " + zone.getKey() + " км — " + zone.getValue());
        }

        // Зони до 10 км — безкоштовна доставка
        SortedMap<Integer, String> freeDelivery = deliveryZones.headMap(10);
        System.out.println("Безкоштовна доставка (до 10 км): " + freeDelivery.values());

        // Зони від 10 км — платна доставка
        SortedMap<Integer, String> paidDelivery = deliveryZones.tailMap(10);
        System.out.println("Платна доставка (від 10 км): " + paidDelivery.values());
        System.out.println();

        // ============================================================
        //   Блок 4: Обробка всієї черги замовлень
        // ============================================================
        System.out.println("=== Блок 4: Обробка черги ===");

        int orderNum = 1;
        System.out.println("Продовжуємо обробку решти замовлень:");
        while (!orderQueue.isEmpty()) {
            String order = orderQueue.poll();
            System.out.println("  " + orderNum + ". " + order);
            orderNum++;
        }
        System.out.println("Усі замовлення оброблені!");
        System.out.println();

        // ============================================================
        //   Блок 5: Довідник — коли що використовувати
        // ============================================================
        System.out.println("=== Блок 5: Довідник з вибору колекції ===");
        System.out.println("┌─────────────────────┬──────────────────────────────────────┐");
        System.out.println("│ Потреба             │ Колекція                             │");
        System.out.println("├─────────────────────┼──────────────────────────────────────┤");
        System.out.println("│ Список з індексами  │ ArrayList                            │");
        System.out.println("│ Часті вставки/видал. │ LinkedList                          │");
        System.out.println("│ Черга FIFO          │ Queue (LinkedList)                   │");
        System.out.println("│ Черга з пріоритетом │ PriorityQueue                        │");
        System.out.println("│ Унікальні елементи  │ HashSet                              │");
        System.out.println("│ Ключ → значення     │ HashMap                              │");
        System.out.println("│ Відсорт. ключі     │ TreeMap (SortedMap)                   │");
        System.out.println("│ Діапазонні запити   │ TreeMap (headMap/tailMap/subMap)       │");
        System.out.println("└─────────────────────┴──────────────────────────────────────┘");
    }
}
