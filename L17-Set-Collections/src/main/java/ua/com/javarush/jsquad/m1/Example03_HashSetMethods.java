package ua.com.javarush.jsquad.m1;

import java.util.HashSet;

/**
 * Лекція 17: Колекції Set — Методи класу HashSet
 *
 * Клас HashSet — типовий представник колекцій типу «множина».
 * Створити HashSet можна за допомогою команди:
 *   HashSet<Тип> ім'я = new HashSet<>();
 *
 * Основні методи HashSet (з таблиці лекції):
 *   - boolean add(Тип value)       — додає елемент value до колекції
 *   - boolean remove(Тип value)    — видаляє елемент, повертає true, якщо він був
 *   - boolean contains(Тип value)  — перевіряє, чи є в колекції такий елемент
 *   - void    clear()              — очищує колекцію повністю
 *   - int     size()               — повертає кількість елементів
 *
 * Аналогія з життя: уявіть дошку з магнітами-тегами на холодильнику.
 * Ви можете приліпити тег (add), зірвати (remove), подивитися, чи тег там є (contains),
 * змести всі одразу (clear) або порахувати, скільки їх зараз (size).
 *
 * Реальне застосування: набір тегів для статті в блозі — теги унікальні,
 * можна додавати, видаляти, перевіряти і рахувати їх кількість.
 */
public class Example03_HashSetMethods {

    public static void main(String[] args) {

        // ============================================================
        //   Сценарій: набір тегів для статті в блозі
        // ============================================================
        System.out.println("=== Теги для статті в блозі ===");

        HashSet<String> tags = new HashSet<>();
        System.out.println("Початковий набір тегів: " + tags);  // []
        System.out.println("Розмір: " + tags.size());           // 0
        System.out.println();

        // ============================================================
        //   Метод add() — повертає true, якщо елемент реально додано
        // ============================================================
        System.out.println("=== add() — додавання тегів ===");

        boolean r1 = tags.add("java");
        boolean r2 = tags.add("tutorial");
        boolean r3 = tags.add("collections");
        boolean r4 = tags.add("java");   // дублікат — не додасться
        boolean r5 = tags.add("hashset");

        System.out.println("add('java'):        " + r1); // true
        System.out.println("add('tutorial'):    " + r2); // true
        System.out.println("add('collections'): " + r3); // true
        System.out.println("add('java') знову:  " + r4); // false — вже був
        System.out.println("add('hashset'):     " + r5); // true

        System.out.println("Поточні теги: " + tags);
        System.out.println();

        // ============================================================
        //   Метод size() — кількість елементів
        // ============================================================
        System.out.println("=== size() — кількість тегів ===");
        System.out.println("Кількість тегів: " + tags.size()); // 4
        System.out.println();

        // ============================================================
        //   Метод contains() — чи є такий тег
        // ============================================================
        System.out.println("=== contains() — перевірка наявності ===");

        System.out.println("Чи є тег 'java'?       " + tags.contains("java"));       // true
        System.out.println("Чи є тег 'python'?     " + tags.contains("python"));     // false
        System.out.println("Чи є тег 'hashset'?    " + tags.contains("hashset"));    // true
        System.out.println("Чи є тег 'arraylist'?  " + tags.contains("arraylist"));  // false
        System.out.println();

        // ============================================================
        //   Метод remove() — видалити тег
        // ============================================================
        System.out.println("=== remove() — видалення тегу ===");

        boolean rm1 = tags.remove("tutorial");   // був → true
        boolean rm2 = tags.remove("python");     // не було → false

        System.out.println("remove('tutorial'): " + rm1); // true
        System.out.println("remove('python'):   " + rm2); // false — такого й не було

        System.out.println("Теги після видалення: " + tags);
        System.out.println("Розмір: " + tags.size()); // 3
        System.out.println();

        // ============================================================
        //   Метод clear() — очистити повністю
        // ============================================================
        System.out.println("=== clear() — очищення всіх тегів ===");

        tags.clear();
        System.out.println("Теги після clear(): " + tags);      // []
        System.out.println("Розмір: " + tags.size());            // 0
        System.out.println("Порожньо? " + tags.isEmpty());       // true
    }
}
