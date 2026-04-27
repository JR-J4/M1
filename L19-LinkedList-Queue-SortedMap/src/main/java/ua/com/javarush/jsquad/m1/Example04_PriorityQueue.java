package ua.com.javarush.jsquad.m1;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Лекція 19: LinkedList, Queue, SortedMap — PriorityQueue (Черга з пріоритетом)
 *
 * PriorityQueue — реалізація Queue, де елементи видаються НЕ за порядком
 * додавання (FIFO), а за пріоритетом (найменший елемент — першим).
 *
 * Внутрішня структура — бінарна купа (binary heap).
 *
 * Порядок визначається:
 *   - природним порядком (Comparable): числа за зростанням, рядки за алфавітом
 *   - або Comparator, переданим у конструктор
 *
 * Синтаксис:
 *   PriorityQueue<Тип> pq = new PriorityQueue<>();             // природний порядок
 *   PriorityQueue<Тип> pq = new PriorityQueue<>(comparator);   // свій порядок
 *
 * Аналогія з життя: приймальне відділення лікарні (ER). Пацієнти НЕ
 * обслуговуються за часом прибуття — першим лікують найважчий випадок,
 * незалежно від того, коли пацієнт прийшов.
 *
 * Реальне застосування: планувальники ОС (процеси з різним пріоритетом),
 * алгоритм Дейкстри, завдання з дедлайнами, системи тикетів.
 */
public class Example04_PriorityQueue {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: PriorityQueue з числами — ER-відділення
        // ============================================================
        System.out.println("=== Блок 1: PriorityQueue з числами ===");

        // Рівень тяжкості: 1 = критичний, 5 = легкий
        PriorityQueue<Integer> emergencyRoom = new PriorityQueue<>();
        emergencyRoom.offer(3); // середня тяжкість
        emergencyRoom.offer(1); // критичний
        emergencyRoom.offer(5); // легкий
        emergencyRoom.offer(2); // важкий
        emergencyRoom.offer(4); // помірний

        System.out.println("Черга (внутрішній порядок): " + emergencyRoom);
        System.out.println("Увага: toString() НЕ показує елементи в порядку пріоритету!");
        System.out.println();

        // poll() завжди видає елемент з найвищим пріоритетом (найменше число)
        System.out.println("Обслуговуємо за пріоритетом (poll):");
        while (!emergencyRoom.isEmpty()) {
            System.out.println("  Пацієнт з рівнем тяжкості: " + emergencyRoom.poll());
        }
        System.out.println();

        // ============================================================
        //   Блок 2: PriorityQueue з рядками — за алфавітом
        // ============================================================
        System.out.println("=== Блок 2: PriorityQueue з рядками ===");

        PriorityQueue<String> patients = new PriorityQueue<>();
        patients.offer("Петро");
        patients.offer("Андрій");
        patients.offer("Марія");
        patients.offer("Ірина");
        patients.offer("Борис");

        System.out.println("Видача за алфавітом (природний порядок String):");
        while (!patients.isEmpty()) {
            System.out.println("  " + patients.poll());
        }
        System.out.println();

        // ============================================================
        //   Блок 3: PriorityQueue з Comparator — свій порядок
        // ============================================================
        System.out.println("=== Блок 3: PriorityQueue з Comparator ===");

        // Зворотний порядок: найбільше число (найлегший випадок) — першим
        // Це як черга у звичайній поліклініці — хто менш хворий, того швидше відпускають
        Comparator<Integer> reverseOrder = new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a; // зворотний порядок
            }
        };

        PriorityQueue<Integer> reverseQueue = new PriorityQueue<>(reverseOrder);
        reverseQueue.offer(3);
        reverseQueue.offer(1);
        reverseQueue.offer(5);
        reverseQueue.offer(2);
        reverseQueue.offer(4);

        System.out.println("Зворотний порядок (найбільше → першим):");
        while (!reverseQueue.isEmpty()) {
            System.out.println("  " + reverseQueue.poll());
        }
        System.out.println();

        // ============================================================
        //   Блок 4: Практичний приклад — тикети техпідтримки
        // ============================================================
        System.out.println("=== Блок 4: Тикети техпідтримки ===");

        // Порівнюємо за пріоритетом (менший номер = вищий пріоритет)
        Comparator<String> byPriority = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                // Формат: "P1-Опис", "P3-Опис" — порівнюємо за першим символом після P
                return a.charAt(1) - b.charAt(1);
            }
        };

        PriorityQueue<String> tickets = new PriorityQueue<>(byPriority);
        tickets.offer("P3-Оновити документацію");
        tickets.offer("P1-Сервер не відповідає");
        tickets.offer("P2-Кнопка не працює");
        tickets.offer("P1-Втрата даних клієнта");
        tickets.offer("P3-Змінити колір кнопки");

        System.out.println("Обробка тикетів за пріоритетом:");
        while (!tickets.isEmpty()) {
            System.out.println("  → " + tickets.poll());
        }
    }
}
