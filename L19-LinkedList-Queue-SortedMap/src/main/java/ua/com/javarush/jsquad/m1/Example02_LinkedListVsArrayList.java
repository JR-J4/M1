package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Лекція 19: LinkedList, Queue, SortedMap — LinkedList vs ArrayList
 *
 * LinkedList і ArrayList — два способи зберігати список елементів.
 * Зовні вони однакові (обидва реалізують List), але всередині працюють по-різному:
 *
 *   Операція                  | ArrayList  | LinkedList
 *   ─────────────────────────┼────────────┼────────────
 *   Доступ за індексом get(i) |   O(1)     |   O(n)
 *   Додати в кінець add(e)   |   O(1)*    |   O(1)
 *   Додати на початок        |   O(n)     |   O(1)
 *   Вставка в середину       |   O(n)     |   O(1)**
 *   Видалення з початку      |   O(n)     |   O(1)
 *
 *   *  — амортизовано (при розширенні масиву — O(n))
 *   ** — сама операція O(1), але пошук позиції O(n)
 *
 * Аналогія з життя:
 *   ArrayList — нумерований зал кінотеатру. Знайти місце №50 — миттєво,
 *   але щоб посадити VIP на місце №1, треба пересадити всіх.
 *   LinkedList — жива черга. Пропустити людину вперед — легко (перезв'язати
 *   сусідів), але знайти 50-ту людину — потрібно рахувати від початку.
 *
 * Реальне застосування: ArrayList — для читання, LinkedList — для частих
 * вставок/видалень на початку або при ітерації.
 */
public class Example02_LinkedListVsArrayList {

    public static void main(String[] args) {

        int testSize = 100_000;

        // ============================================================
        //   Блок 1: Вставка на початок — LinkedList виграє
        // ============================================================
        System.out.println("=== Блок 1: Вставка на початок (" + testSize + " елементів) ===");

        // ArrayList — кожна вставка зсуває всі елементи вправо
        ArrayList<Integer> arrayList = new ArrayList<>();
        long startAL = System.currentTimeMillis();
        for (int i = 0; i < testSize; i++) {
            arrayList.add(0, i); // вставка на початок — O(n) кожен раз
        }
        long timeAL = System.currentTimeMillis() - startAL;

        // LinkedList — просто оновлюємо 2 посилання
        LinkedList<Integer> linkedList = new LinkedList<>();
        long startLL = System.currentTimeMillis();
        for (int i = 0; i < testSize; i++) {
            linkedList.addFirst(i); // вставка на початок — O(1)
        }
        long timeLL = System.currentTimeMillis() - startLL;

        System.out.println("  ArrayList:  " + timeAL + " мс");
        System.out.println("  LinkedList: " + timeLL + " мс");
        System.out.println("  → LinkedList ШВИДШИЙ для вставки на початок");
        System.out.println();

        // ============================================================
        //   Блок 2: Доступ за індексом — ArrayList виграє
        // ============================================================
        System.out.println("=== Блок 2: Доступ за індексом (1000 випадкових get) ===");

        // ArrayList — прямий доступ до масиву за індексом
        long startGetAL = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            arrayList.get(testSize / 2); // доступ до середини — O(1)
        }
        long timeGetAL = System.currentTimeMillis() - startGetAL;

        // LinkedList — прохід від голови/хвоста до потрібного індексу
        long startGetLL = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            linkedList.get(testSize / 2); // доступ до середини — O(n)
        }
        long timeGetLL = System.currentTimeMillis() - startGetLL;

        System.out.println("  ArrayList:  " + timeGetAL + " мс");
        System.out.println("  LinkedList: " + timeGetLL + " мс");
        System.out.println("  → ArrayList ШВИДШИЙ для доступу за індексом");
        System.out.println();

        // ============================================================
        //   Блок 3: Видалення з початку — LinkedList виграє
        // ============================================================
        System.out.println("=== Блок 3: Видалення з початку (10 000 елементів) ===");

        // Підготовка даних
        ArrayList<Integer> al = new ArrayList<>();
        LinkedList<Integer> ll = new LinkedList<>();
        for (int i = 0; i < 10_000; i++) {
            al.add(i);
            ll.add(i);
        }

        long startRemAL = System.currentTimeMillis();
        while (!al.isEmpty()) {
            al.remove(0); // O(n) — зсув всіх елементів
        }
        long timeRemAL = System.currentTimeMillis() - startRemAL;

        long startRemLL = System.currentTimeMillis();
        while (!ll.isEmpty()) {
            ll.removeFirst(); // O(1) — просто перекидаємо посилання
        }
        long timeRemLL = System.currentTimeMillis() - startRemLL;

        System.out.println("  ArrayList:  " + timeRemAL + " мс");
        System.out.println("  LinkedList: " + timeRemLL + " мс");
        System.out.println("  → LinkedList ШВИДШИЙ для видалення з початку");
        System.out.println();

        // ============================================================
        //   Блок 4: Підсумкова порада
        // ============================================================
        System.out.println("=== Блок 4: Коли що використовувати ===");
        System.out.println("┌─────────────────────────────┬────────────┬────────────┐");
        System.out.println("│ Операція                    │ ArrayList  │ LinkedList │");
        System.out.println("├─────────────────────────────┼────────────┼────────────┤");
        System.out.println("│ Доступ за індексом get(i)   │  O(1) ✓    │  O(n)      │");
        System.out.println("│ Додати в кінець             │  O(1) ✓    │  O(1) ✓    │");
        System.out.println("│ Додати на початок           │  O(n)      │  O(1) ✓    │");
        System.out.println("│ Видалити з початку          │  O(n)      │  O(1) ✓    │");
        System.out.println("│ Пам'ять на елемент          │  менше ✓   │  більше    │");
        System.out.println("└─────────────────────────────┴────────────┴────────────┘");
        System.out.println();
        System.out.println("Порада: у 95% випадків ArrayList — кращий вибір.");
        System.out.println("LinkedList — лише коли багато вставок/видалень на початку.");
    }
}
