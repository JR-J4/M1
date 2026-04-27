package ua.com.javarush.jsquad.m1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Лекція 19: LinkedList, Queue, SortedMap — Інтерфейс Queue (Черга)
 *
 * Queue (черга) — інтерфейс, що розширює Collection і працює за принципом
 * FIFO: First In — First Out (хто прийшов першим — той першим вийде).
 *
 * Три пари методів (безпечний / з винятком):
 *   Додати:    offer(e) → false якщо не вдалось  |  add(e)     → виняток
 *   Подивитись: peek()  → null якщо черга порожня |  element()  → виняток
 *   Забрати:   poll()   → null якщо черга порожня |  remove()   → виняток
 *
 * LinkedList реалізує інтерфейс Queue, тому його можна використати як чергу.
 *
 * Синтаксис:
 *   Queue<Тип> queue = new LinkedList<>();
 *
 * Аналогія з життя: черга в касу кав'ярні. Покупці стають у кінець черги
 * (offer/add), касир обслуговує першого (poll/remove), можна подивитись
 * хто наступний без видалення (peek/element).
 *
 * Реальне застосування: черги завдань (Task Queue), обробка повідомлень,
 * BFS-алгоритм (пошук у ширину), системи друку, буфери введення/виведення.
 */
public class Example03_QueueInterface {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Створення черги — покупці стають у чергу
        // ============================================================
        System.out.println("=== Блок 1: Додавання до черги ===");

        Queue<String> cashRegister = new LinkedList<>();

        // offer() — безпечне додавання (повертає true/false)
        cashRegister.offer("Олена");
        cashRegister.offer("Андрій");
        cashRegister.offer("Марія");

        // add() — додавання з винятком (якщо черга обмежена і повна)
        cashRegister.add("Дмитро");
        cashRegister.add("Петро");

        System.out.println("Черга в касу: " + cashRegister);
        System.out.println("Розмір черги: " + cashRegister.size());
        System.out.println();

        // ============================================================
        //   Блок 2: Перегляд голови черги — хто наступний?
        // ============================================================
        System.out.println("=== Блок 2: Перегляд голови черги ===");

        // peek() — подивитись на першого БЕЗ видалення (null якщо порожня)
        System.out.println("peek(): " + cashRegister.peek()); // Олена
        System.out.println("Черга не змінилась: " + cashRegister);

        // element() — те саме, але кидає NoSuchElementException якщо порожня
        System.out.println("element(): " + cashRegister.element()); // Олена
        System.out.println();

        // ============================================================
        //   Блок 3: Обслуговування — забираємо з голови
        // ============================================================
        System.out.println("=== Блок 3: Обслуговування (видалення з голови) ===");

        // poll() — забрати першого (null якщо порожня)
        System.out.println("poll(): " + cashRegister.poll()); // Олена — обслужена
        System.out.println("Черга після poll(): " + cashRegister);

        // remove() — те саме, але кидає NoSuchElementException якщо порожня
        System.out.println("remove(): " + cashRegister.remove()); // Андрій — обслужений
        System.out.println("Черга після remove(): " + cashRegister);
        System.out.println();

        // ============================================================
        //   Блок 4: Безпечні vs небезпечні методи
        // ============================================================
        System.out.println("=== Блок 4: Порожня черга — peek/poll vs element/remove ===");

        Queue<String> emptyQueue = new LinkedList<>();

        // Безпечні методи — повертають null
        System.out.println("peek() на порожній: " + emptyQueue.peek());    // null
        System.out.println("poll() на порожній: " + emptyQueue.poll());    // null

        // Небезпечні — кидають виняток
        // emptyQueue.element(); // NoSuchElementException!
        // emptyQueue.remove();  // NoSuchElementException!
        System.out.println("element()/remove() на порожній → NoSuchElementException!");
        System.out.println();

        System.out.println("Порада: використовуйте offer/peek/poll — вони безпечніші.");
        System.out.println();

        // ============================================================
        //   Блок 5: FIFO в дії — черга завдань на друк
        // ============================================================
        System.out.println("=== Блок 5: Практика — черга друку ===");

        Queue<String> printQueue = new LinkedList<>();
        printQueue.offer("Звіт.pdf");
        printQueue.offer("Фото.jpg");
        printQueue.offer("Резюме.docx");
        printQueue.offer("Таблиця.xlsx");

        System.out.println("Черга друку: " + printQueue);
        System.out.println("Друкуємо по черзі (FIFO):");

        int order = 1;
        while (!printQueue.isEmpty()) {
            String document = printQueue.poll();
            System.out.println("  " + order + ". Друкується: " + document);
            order++;
        }
        System.out.println("Черга порожня: " + printQueue.isEmpty());
    }
}
