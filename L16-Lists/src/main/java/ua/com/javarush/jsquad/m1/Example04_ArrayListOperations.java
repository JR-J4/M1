package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;

/**
 * Лекція 16: Списки — Операції з ArrayList
 *
 * Основні методи ArrayList:
 *   add(value)           — додає елемент у кінець
 *   add(index, value)    — додає елемент на позицію index
 *   get(index)           — повертає елемент за індексом
 *   set(index, value)    — замінює елемент за індексом
 *   remove(index)        — видаляє за індексом, повертає видалений
 *   remove(value)        — видаляє перший знайдений елемент
 *   clear()              — очищує список
 *   contains(value)      — чи міститься елемент?
 *   isEmpty()            — чи порожній список?
 *   size()               — кількість елементів
 *   toArray(array)       — перетворює список у масив
 *
 * Нумерація елементів починається з нуля, як у масивах.
 *
 * Аналогія з життя: список справ у телефоні — можна додавати нові, видаляти
 * виконані, перевіряти що залишилось, замінювати одну справу іншою.
 *
 * Реальне застосування: управління списком контактів, кошиком покупок,
 * списком задач у проєктному менеджері.
 */
public class Example04_ArrayListOperations {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Вставка за індексом — add(index, value)
        // ============================================================
        System.out.println("=== add(index, value) ===");

        // Уявіть: черга в лікарні — терміновий пацієнт вставляється на початок
        ArrayList<String> queue = new ArrayList<String>();
        queue.add("Пацієнт А");
        queue.add("Пацієнт Б");
        queue.add("Пацієнт В");
        System.out.println("Черга: " + queue);

        // Вставляємо на позицію 0 (початок)
        queue.add(0, "ТЕРМІНОВИЙ");
        System.out.println("Після вставки термінового: " + queue);

        // Вставляємо в середину (позиція 2)
        queue.add(2, "Пацієнт VIP");
        System.out.println("Після вставки VIP: " + queue);
        System.out.println();

        // ============================================================
        //   Приклад 2: get() та set() — отримання та заміна
        // ============================================================
        System.out.println("=== get() та set() ===");

        // Уявіть: плейлист пісень
        ArrayList<String> playlist = new ArrayList<String>();
        playlist.add("Imagine");
        playlist.add("Yesterday");
        playlist.add("Bohemian Rhapsody");
        playlist.add("Hotel California");

        System.out.println("Плейлист: " + playlist);

        // get — дістати пісню за індексом
        String firstSong = playlist.get(0);
        String lastSong = playlist.get(playlist.size() - 1);
        System.out.println("Перша пісня: " + firstSong);       // Imagine
        System.out.println("Остання пісня: " + lastSong);       // Hotel California

        // set — замінити пісню
        playlist.set(1, "Let It Be");
        System.out.println("Після заміни [1]: " + playlist);
        System.out.println();

        // ============================================================
        //   Приклад 3: remove() — видалення елементів
        // ============================================================
        System.out.println("=== remove() ===");

        // Уявіть: список учнів, хтось переводиться
        ArrayList<String> students = new ArrayList<String>();
        students.add("Анна");
        students.add("Богдан");
        students.add("Вікторія");
        students.add("Григорій");
        students.add("Богдан");  // ще один Богдан
        System.out.println("Учні: " + students);

        // Видалення за індексом
        String removed = students.remove(0);
        System.out.println("Видалено [0]: " + removed);  // Анна
        System.out.println("Після видалення: " + students);

        // Видалення за значенням — видаляє ПЕРШИЙ знайдений
        students.remove("Богдан");
        System.out.println("Після remove(\"Богдан\"): " + students);
        // Другий Богдан залишився!
        System.out.println();

        // ============================================================
        //   Приклад 4: remove(int) vs remove(Integer) — пастка!
        // ============================================================
        System.out.println("=== remove(int) vs remove(Integer) ===");

        // Коли ArrayList містить Integer — виникає неоднозначність
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        System.out.println("Числа: " + numbers);

        // remove(1) — видаляє елемент за ІНДЕКСОМ 1 (тобто число 20)
        numbers.remove(1);
        System.out.println("Після remove(1) — видалено за індексом: " + numbers); // [10, 30]

        // Щоб видалити за ЗНАЧЕННЯМ — потрібно передати об'єкт Integer
        numbers.add(10); // тепер [10, 30, 10]
        System.out.println("Додали 10: " + numbers);

        numbers.remove(Integer.valueOf(10)); // видаляє перший елемент зі значенням 10
        System.out.println("Після remove(Integer.valueOf(10)): " + numbers); // [30, 10]
        System.out.println();

        // ============================================================
        //   Приклад 5: contains(), isEmpty(), size()
        // ============================================================
        System.out.println("=== contains(), isEmpty(), size() ===");

        // Уявіть: перевірка наявності товару на складі
        ArrayList<String> warehouse = new ArrayList<String>();
        warehouse.add("Молоко");
        warehouse.add("Хліб");
        warehouse.add("Масло");

        System.out.println("Склад: " + warehouse);
        System.out.println("Є молоко? " + warehouse.contains("Молоко"));    // true
        System.out.println("Є сир? " + warehouse.contains("Сир"));          // false
        System.out.println("Порожній? " + warehouse.isEmpty());              // false
        System.out.println("Кількість: " + warehouse.size());               // 3
        System.out.println();

        // ============================================================
        //   Приклад 6: clear() — очищення списку
        // ============================================================
        System.out.println("=== clear() ===");

        System.out.println("До очищення: " + warehouse);
        warehouse.clear();
        System.out.println("Після clear(): " + warehouse);        // []
        System.out.println("Порожній? " + warehouse.isEmpty());   // true
        System.out.println("Розмір: " + warehouse.size());        // 0
        System.out.println();

        // ============================================================
        //   Приклад 7: toArray() — перетворення у масив
        // ============================================================
        System.out.println("=== toArray() ===");

        // Уявіть: вивантаження списку замовлень у звіт (масив)
        ArrayList<String> orders = new ArrayList<String>();
        orders.add("Замовлення #001");
        orders.add("Замовлення #002");
        orders.add("Замовлення #003");

        // Перетворення ArrayList -> масив
        String[] ordersArray = orders.toArray(new String[0]);

        System.out.println("Масив замовлень:");
        for (String order : ordersArray) {
            System.out.println("  " + order);
        }
        System.out.println();

        // ============================================================
        //   Приклад 8: Ітерація по ArrayList
        // ============================================================
        System.out.println("=== Ітерація по ArrayList ===");

        // Уявіть: підрахунок загальної суми покупок
        ArrayList<Integer> purchasePrices = new ArrayList<Integer>();
        purchasePrices.add(250);
        purchasePrices.add(180);
        purchasePrices.add(520);
        purchasePrices.add(90);

        // Спосіб 1: цикл for з індексом
        int total = 0;
        for (int i = 0; i < purchasePrices.size(); i++) {
            total += purchasePrices.get(i);
        }
        System.out.println("Сума (for): " + total); // 1040

        // Спосіб 2: for-each
        int total2 = 0;
        for (int p : purchasePrices) { // unboxing Integer -> int автоматично
            total2 += p;
        }
        System.out.println("Сума (for-each): " + total2); // 1040
    }
}
