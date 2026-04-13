package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;

/**
 * Лекція 16: Списки — Колекції в Java: ArrayList
 *
 * ArrayList — це клас-колекція, який поєднує переваги масиву та динамічного розміру.
 * Назва складається з Array (масив) + List (список).
 *
 * Переваги ArrayList над звичайним масивом:
 *   - Зберігає елементи певного типу
 *   - Динамічно змінює розмір
 *   - Додає елементи в кінець списку
 *   - Вставляє елементи на початок і в середину
 *   - Видаляє елементи з будь-якого місця
 *
 * Синтаксис:
 *   ArrayList<Тип> ім'я = new ArrayList<Тип>();
 *
 * Аналогія з життя: масив — це полиця з фіксованою кількістю місць.
 * ArrayList — це розтягувана полиця, яка сама збільшується, коли додаєш книги.
 *
 * Реальне застосування: список товарів у кошику, список повідомлень у чаті,
 * історія замовлень клієнта.
 */
public class Example03_ArrayListBasics {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Створення ArrayList різних типів
        // ============================================================
        System.out.println("=== Створення ArrayList ===");

        // Колекції зберігають тільки посилальні типи (обгортки, а не примітиви)
        ArrayList<Integer> numbers = new ArrayList<Integer>();    // список цілих чисел
        ArrayList<String> names = new ArrayList<String>();         // список рядків
        ArrayList<Double> prices = new ArrayList<Double>();        // список дійсних чисел
        ArrayList<Boolean> flags = new ArrayList<Boolean>();       // список boolean

        System.out.println("Створено 4 порожні списки");
        System.out.println("numbers: " + numbers);  // []
        System.out.println("names: " + names);       // []
        System.out.println();

        // ============================================================
        //   Приклад 2: Додавання елементів — add()
        // ============================================================
        System.out.println("=== Додавання елементів ===");

        // Уявіть: кошик в інтернет-магазині — додаємо товари
        ArrayList<String> cart = new ArrayList<String>();
        cart.add("Ноутбук");
        cart.add("Мишка");
        cart.add("Клавіатура");
        cart.add("Монітор");

        System.out.println("Кошик: " + cart);
        // [Ноутбук, Мишка, Клавіатура, Монітор]
        System.out.println("Кількість товарів: " + cart.size()); // 4
        System.out.println();

        // ============================================================
        //   Приклад 3: Порівняння масиву та ArrayList
        // ============================================================
        System.out.println("=== Масив vs ArrayList ===");

        // Масив: фіксований розмір, треба знати кількість наперед
        String[] arrayFriends = new String[3];
        arrayFriends[0] = "Олег";
        arrayFriends[1] = "Марія";
        arrayFriends[2] = "Тарас";
        // arrayFriends[3] = "Анна"; // ArrayIndexOutOfBoundsException!

        System.out.println("Масив (3 місця): не можна додати 4-го друга");

        // ArrayList: динамічний розмір, додавай скільки хочеш
        ArrayList<String> listFriends = new ArrayList<String>();
        listFriends.add("Олег");
        listFriends.add("Марія");
        listFriends.add("Тарас");
        listFriends.add("Анна");       // без проблем!
        listFriends.add("Дмитро");     // і ще!

        System.out.println("ArrayList: " + listFriends);
        System.out.println("Розмір динамічно зріс до: " + listFriends.size());
    }
}
