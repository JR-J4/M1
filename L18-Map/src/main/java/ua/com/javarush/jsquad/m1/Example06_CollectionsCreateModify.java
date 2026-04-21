package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Лекція 18: Колекції Map — Клас Collections (створення та модифікація)
 *
 * Collections — утилітний клас із статичними методами для роботи з колекціями.
 * (Не плутати з інтерфейсом Collection!)
 *
 * Методи створення та модифікації:
 *   Collections.addAll(collection, elements...)  — додати кілька елементів одразу
 *   Collections.fill(list, value)                — заповнити весь список одним значенням
 *   Collections.nCopies(n, value)                — створити незмінний список із n копій
 *   Collections.replaceAll(list, old, new)       — замінити всі входження значення
 *   Collections.copy(dest, src)                  — скопіювати елементи з одного списку в інший
 *
 * Аналогія з життя: менеджер замовлень у ресторані. Він може:
 * швидко заповнити бланк однаковими стравами (fill), створити шаблон
 * замовлення (nCopies), замінити страву у всіх замовленнях (replaceAll).
 *
 * Реальне застосування: ініціалізація списків, масове оновлення даних,
 * створення шаблонів, тестові дані.
 */
public class Example06_CollectionsCreateModify {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Collections.addAll() — додати кілька елементів
        // ============================================================
        System.out.println("=== Блок 1: Collections.addAll() ===");

        // Уявіть: формуєте меню ресторану, додаючи страви одним рядком
        ArrayList<String> menu = new ArrayList<>();
        Collections.addAll(menu, "Борщ", "Вареники", "Голубці", "Деруни", "Салат");

        System.out.println("Меню: " + menu);
        System.out.println("Кількість страв: " + menu.size());
        System.out.println();

        // ============================================================
        //   Блок 2: Collections.fill() — заповнити одним значенням
        // ============================================================
        System.out.println("=== Блок 2: Collections.fill() ===");

        // Уявіть: всі столики позначаємо як "вільний"
        ArrayList<String> tables = new ArrayList<>();
        Collections.addAll(tables, "зайнятий", "зайнятий", "зайнятий", "зайнятий", "зайнятий");
        System.out.println("До закриття:  " + tables);

        Collections.fill(tables, "вільний");
        System.out.println("Після закриття: " + tables);
        System.out.println();

        // ============================================================
        //   Блок 3: Collections.nCopies() — список із n однакових елементів
        // ============================================================
        System.out.println("=== Блок 3: Collections.nCopies() ===");

        // Уявіть: шаблон замовлення — 5 порцій «Кава»
        List<String> coffeeOrder = Collections.nCopies(5, "Кава");
        System.out.println("Шаблон замовлення: " + coffeeOrder);
        System.out.println("Кількість: " + coffeeOrder.size());

        // nCopies повертає незмінний список — зміни заборонені!
        // coffeeOrder.add("Чай"); // UnsupportedOperationException!
        System.out.println("(Список nCopies — незмінний, додати елемент не можна)");

        // Якщо потрібен змінний список — обгортаємо у ArrayList
        ArrayList<String> mutableOrder = new ArrayList<>( Collections.nCopies(3, "Чай") );
        mutableOrder.add("Сік");
        System.out.println("Змінний список: " + mutableOrder);
        System.out.println();

        // ============================================================
        //   Блок 4: Collections.replaceAll() — замінити всі входження
        // ============================================================
        System.out.println("=== Блок 4: Collections.replaceAll() ===");

        // Уявіть: у меню «Вареники» замінюємо на «Вареники з вишнею»
        ArrayList<String> dishes = new ArrayList<>();
        Collections.addAll(dishes, "Борщ", "Вареники", "Салат", "Вареники", "Деруни");
        System.out.println("До заміни:    " + dishes);

        Collections.replaceAll(dishes, "Вареники", "Вареники з вишнею");
        System.out.println("Після заміни: " + dishes);
        System.out.println();

        // ============================================================
        //   Блок 5: Collections.copy() — копіювання списку
        // ============================================================
        System.out.println("=== Блок 5: Collections.copy() ===");

        // Уявіть: копіюємо замовлення одного столика для іншого
        ArrayList<String> original = new ArrayList<>();
        Collections.addAll(original, "Піца", "Сік", "Десерт");

        // Список-приймач повинен мати розмір >= розмір джерела!
        ArrayList<String> copy = new ArrayList<>(Collections.nCopies(original.size(), ""));
        Collections.copy(copy, original);

        System.out.println("Оригінал: " + original);
        System.out.println("Копія:    " + copy);

        // Зміна копії не впливає на оригінал
        copy.set(0, "Паста");
        System.out.println("Після зміни копії:");
        System.out.println("  Оригінал: " + original);
        System.out.println("  Копія:    " + copy);
    }
}
