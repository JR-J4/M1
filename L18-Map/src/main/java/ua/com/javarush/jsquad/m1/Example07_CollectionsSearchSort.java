package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Лекція 18: Колекції Map — Клас Collections (пошук, сортування, перетасовка)
 *
 * Методи пошуку та аналітики:
 *   Collections.min(collection)             — мінімальний елемент
 *   Collections.max(collection)             — максимальний елемент
 *   Collections.frequency(collection, obj)  — кількість входжень елемента
 *   Collections.binarySearch(list, key)     — бінарний пошук (список МУСИТЬ бути відсортований!)
 *   Collections.disjoint(col1, col2)        — чи НЕ мають спільних елементів?
 *
 * Методи сортування та перестановки:
 *   Collections.sort(list)      — сортування за зростанням
 *   Collections.reverse(list)   — розвернути список
 *   Collections.rotate(list, n) — циклічний зсув на n позицій
 *   Collections.shuffle(list)   — випадкове перемішування
 *
 * Аналогія з життя: аналітика оцінок у школі — знайти найкращу/найгіршу
 * оцінку, порахувати «двійки», відсортувати рейтинг, перемішати учнів
 * для розподілу по командах.
 *
 * Реальне застосування: рейтинги, таблиці лідерів, випадковий розподіл,
 * статистичний аналіз, пошук у великих відсортованих списках.
 */
public class Example07_CollectionsSearchSort {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: min(), max(), frequency() — аналітика оцінок
        // ============================================================
        System.out.println("=== Блок 1: min / max / frequency ===");

        // Оцінки студентів за іспит (від 1 до 12)
        ArrayList<Integer> grades = new ArrayList<>();
        Collections.addAll(grades, 8, 11, 5, 9, 12, 7, 5, 10, 8, 5, 11, 9);

        System.out.println("Оцінки: " + grades);
        System.out.println("Мінімальна оцінка: " + Collections.min(grades));
        System.out.println("Максимальна оцінка: " + Collections.max(grades));
        System.out.println("Кількість оцінок '5': " + Collections.frequency(grades, 5));
        System.out.println("Кількість оцінок '8': " + Collections.frequency(grades, 8));
        System.out.println("Кількість оцінок '12': " + Collections.frequency(grades, 12));
        System.out.println();

        // ============================================================
        //   Блок 2: sort() і reverse() — рейтинг
        // ============================================================
        System.out.println("=== Блок 2: sort / reverse ===");

        ArrayList<Integer> rating = new ArrayList<>(grades);

        // Сортування за зростанням
        Collections.sort(rating);
        System.out.println("Відсортовано (зростання): " + rating);

        // Розвернути — отримаємо спадання
        Collections.reverse(rating);
        System.out.println("Розвернуто (спадання):    " + rating);
        System.out.println();

        // ============================================================
        //   Блок 3: binarySearch() — швидкий пошук у відсортованому списку
        // ============================================================
        System.out.println("=== Блок 3: binarySearch ===");

        // ВАЖЛИВО: список МУСИТЬ бути відсортований перед binarySearch!
        ArrayList<String> players = new ArrayList<>();
        Collections.addAll(players, "Андрій", "Борис", "Вікторія", "Дмитро", "Олена");
        // Список вже відсортований за алфавітом

        System.out.println("Гравці: " + players);

        int index1 = Collections.binarySearch(players, "Вікторія");
        System.out.println("Індекс 'Вікторія': " + index1); // 2

        int index2 = Collections.binarySearch(players, "Петро");
        System.out.println("Індекс 'Петро': " + index2); // від'ємне число — не знайдено
        System.out.println("(Від'ємне число означає: елемент не знайдено)");
        System.out.println();

        // ============================================================
        //   Блок 4: disjoint() — чи є спільні елементи?
        // ============================================================
        System.out.println("=== Блок 4: disjoint ===");

        // Уявіть: дві команди — перевіряємо, чи немає гравця одразу у двох
        ArrayList<String> teamA = new ArrayList<>();
        Collections.addAll(teamA, "Олена", "Андрій", "Марія");

        ArrayList<String> teamB = new ArrayList<>();
        Collections.addAll(teamB, "Дмитро", "Петро", "Ірина");

        ArrayList<String> teamC = new ArrayList<>();
        Collections.addAll(teamC, "Борис", "Олена", "Тарас");

        System.out.println("Команда A: " + teamA);
        System.out.println("Команда B: " + teamB);
        System.out.println("Команда C: " + teamC);
        System.out.println("A і B не перетинаються? " + Collections.disjoint(teamA, teamB)); // true
        System.out.println("A і C не перетинаються? " + Collections.disjoint(teamA, teamC)); // false (Олена)
        System.out.println();

        // ============================================================
        //   Блок 5: rotate() — циклічний зсув
        // ============================================================
        System.out.println("=== Блок 5: rotate ===");

        // Уявіть: черга чергових — кожен день зсуваємо на 1
        ArrayList<String> duty = new ArrayList<>();
        Collections.addAll(duty, "Понеділок-Олена", "Вівторок-Андрій", "Середа-Марія",
                "Четвер-Дмитро", "П'ятниця-Петро");

        System.out.println("Початковий графік: " + duty);
        Collections.rotate(duty, 1); // зсув вправо на 1
        System.out.println("Після rotate(1):   " + duty);
        Collections.rotate(duty, -2); // зсув вліво на 2
        System.out.println("Після rotate(-2):  " + duty);
        System.out.println();

        // ============================================================
        //   Блок 6: shuffle() — випадкове перемішування
        // ============================================================
        System.out.println("=== Блок 6: shuffle ===");

        // Уявіть: перемішуємо учнів для розподілу на команди
        ArrayList<String> students = new ArrayList<>();
        Collections.addAll(students, "Олена", "Андрій", "Марія", "Дмитро", "Петро", "Ірина");

        System.out.println("До перемішування: " + students);
        Collections.shuffle(students);
        System.out.println("Після shuffle():  " + students);
        Collections.shuffle(students);
        System.out.println("Ще раз shuffle(): " + students);
        System.out.println("(Кожен раз порядок буде різним!)");
    }
}
