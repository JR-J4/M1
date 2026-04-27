package ua.com.javarush.jsquad.m1;

import java.util.LinkedList;
import java.util.List;

/**
 * Лекція 19: LinkedList, Queue, SortedMap — Основи LinkedList
 *
 * LinkedList — клас-колекція, що дістався Java у спадок від C++.
 * Зовні LinkedList — такий самий список, як ArrayList (ті ж методи),
 * але всередині він влаштований зовсім інакше: це двозв'язний список,
 * де кожен елемент зберігає посилання на попередній і наступний.
 *
 * Додаткові методи (яких немає в ArrayList):
 *   addFirst(e), addLast(e)       — додати на початок / кінець
 *   getFirst(), getLast()         — отримати перший / останній
 *   removeFirst(), removeLast()   — видалити перший / останній
 *
 * Синтаксис:
 *   LinkedList<Тип> list = new LinkedList<>();
 *   List<Тип> list = new LinkedList<>();  // через інтерфейс (поліморфізм)
 *
 * Аналогія з життя: потяг з вагонами. Кожен вагон «знає» тільки свого
 * сусіда зліва та справа. Можна легко причепити новий вагон на початок
 * чи кінець, або від'єднати будь-який вагон (перезв'язавши сусідів).
 * Але щоб дістатися до вагону №7, потрібно йти від локомотива по черзі.
 *
 * Реальне застосування: плейлисти (наступний/попередній трек), історія
 * браузера (назад/вперед), undo/redo, черги повідомлень.
 */
public class Example01_LinkedListBasics {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Створення LinkedList — будуємо потяг
        // ============================================================
        System.out.println("=== Блок 1: Створення та додавання ===");

        LinkedList<String> train = new LinkedList<>();

        // Звичайний add() — додає в кінець (як ArrayList)
        train.add("Вагон-1");
        train.add("Вагон-2");
        train.add("Вагон-3");
        System.out.println("Потяг: " + train);

        // addFirst — причепити вагон на початок
        train.addFirst("Локомотив");
        System.out.println("Після addFirst: " + train);

        // addLast — причепити вагон у кінець
        train.addLast("Хвостовий");
        System.out.println("Після addLast:  " + train);

        // add(index, element) — вставити в середину
        train.add(2, "Вагон-Ресторан");
        System.out.println("Після add(2):   " + train);
        System.out.println();

        // ============================================================
        //   Блок 2: Читання елементів — інспектуємо вагони
        // ============================================================
        System.out.println("=== Блок 2: Отримання елементів ===");

        System.out.println("getFirst(): " + train.getFirst());     // Локомотив
        System.out.println("getLast():  " + train.getLast());       // Хвостовий
        System.out.println("get(2):     " + train.get(2));          // Вагон-Ресторан
        System.out.println("size():     " + train.size());          // 6
        System.out.println("contains(\"Вагон-2\"): " + train.contains("Вагон-2")); // true
        System.out.println();

        // ============================================================
        //   Блок 3: Видалення елементів — від'єднуємо вагони
        // ============================================================
        System.out.println("=== Блок 3: Видалення елементів ===");

        System.out.println("До видалення: " + train);

        train.removeFirst();
        System.out.println("removeFirst(): " + train);

        train.removeLast();
        System.out.println("removeLast():  " + train);

        train.remove("Вагон-Ресторан");
        System.out.println("remove(\"Вагон-Ресторан\"): " + train);

        train.remove(1); // видалення за індексом
        System.out.println("remove(1):     " + train);
        System.out.println();

        // ============================================================
        //   Блок 4: Поліморфізм — LinkedList як List
        // ============================================================
        System.out.println("=== Блок 4: Поліморфізм ===");

        // LinkedList реалізує інтерфейс List — можна використати як List
        List<String> playlist = new LinkedList<>();
        playlist.add("Трек 1 — Океан Ельзи");
        playlist.add("Трек 2 — ДахаБраха");
        playlist.add("Трек 3 — Kalush");

        System.out.println("Плейлист (List<String>): " + playlist);
        System.out.println("Тип: " + playlist.getClass().getSimpleName()); // LinkedList

        // Через List інтерфейс доступні лише стандартні методи
        // playlist.addFirst("..."); // НЕ скомпілюється через тип List!
        System.out.println("Через інтерфейс List — немає addFirst/addLast,");
        System.out.println("але є всі стандартні методи: add, get, remove, size...");
    }
}
