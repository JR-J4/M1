package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Лекція 20: Singleton, enum, switch — Власні методи в enum
 *
 * Оскільки enum під час компіляції перетворюється у звичайний клас,
 * у ньому можна оголошувати поля, конструктори та методи.
 * Після останньої константи потрібно поставити крапку з комою (;),
 * а далі писати поля і методи як у звичайному класі.
 *
 * Синтаксис:
 *   enum Planet {
 *       EARTH(5.97e24, 6.37e6);   // ← крапка з комою після останньої константи
 *
 *       private double mass;
 *       Planet(double mass, double radius) { ... }
 *       public double getMass() { return mass; }
 *   }
 *
 * Аналогія з життя: меню в кафе. Кожна страва (константа enum) має
 * свою назву, ціну і час приготування (поля). Можна запитати у будь-якої
 * страви її ціну (виклик методу).
 *
 * Реальне застосування: валюти з курсами обміну, HTTP-коди з описами,
 * розміри піци з цінами, ролі з правами доступу.
 */
public class Example03_EnumCustomMethods {

    // Enum з полями та конструктором — розміри піци
    enum PizzaSize {
        SMALL(25, 149),
        MEDIUM(30, 199),
        LARGE(35, 269);   // ← крапка з комою обовʼязкова!

        private final int diameterCm;
        private final int priceUah;

        // Конструктор enum — завжди private (навіть без ключового слова)
        PizzaSize(int diameterCm, int priceUah) {
            this.diameterCm = diameterCm;
            this.priceUah = priceUah;
        }

        public int getDiameterCm() {
            return diameterCm;
        }

        public int getPriceUah() {
            return priceUah;
        }

        // Власний метод — опис розміру
        public String getDescription() {
            return name() + " (Ø" + diameterCm + " см) — " + priceUah + " грн";
        }
    }

    // Enum з методом asList() — приклад з лекції
    enum Color {
        RED,
        GREEN,
        BLUE;

        // Можна додавати свої статичні методи
        public static List<Color> asList() {
            ArrayList<Color> list = new ArrayList<>();
            Collections.addAll(list, values());
            return list;
        }
    }

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Enum з полями — розміри піци
        // ============================================================
        System.out.println("=== Блок 1: Enum з полями — меню піцерії ===");

        // Кожна константа має свої значення полів
        System.out.println("Меню піцерії:");
        for (PizzaSize size : PizzaSize.values()) {
            System.out.println("  " + size.name()
                    + ": діаметр " + size.getDiameterCm() + " см"
                    + ", ціна " + size.getPriceUah() + " грн");
        }

        // Вибираємо розмір
        PizzaSize myPizza = PizzaSize.LARGE;
        System.out.println("Мій вибір: " + myPizza.name() + " за " + myPizza.getPriceUah() + " грн");
        System.out.println();

        // ============================================================
        //   Блок 2: Enum з методом — getDescription()
        // ============================================================
        System.out.println("=== Блок 2: Власний метод у enum ===");

        // Викликаємо власний метод getDescription() на кожному значенні
        for (PizzaSize size : PizzaSize.values()) {
            System.out.println("  " + size.getDescription());
        }
        System.out.println();

        // ============================================================
        //   Блок 3: Enum як клас — внутрішня структура
        // ============================================================
        System.out.println("=== Блок 3: Як працює enum всередині ===");

        // Компілятор перетворює enum Day { MONDAY, TUESDAY, ... } у клас:
        //
        // public class Day {
        //     public static final Day MONDAY    = new Day(0);
        //     public static final Day TUESDAY   = new Day(1);
        //     public static final Day WEDNESDAY = new Day(2);
        //     ...
        //
        //     private static final Day[] array = {MONDAY, TUESDAY, ...};
        //     private final int value;
        //
        //     private Day(int value) {       // private конструктор!
        //         this.value = value;
        //     }
        //
        //     public int ordinal() { return this.value; }
        //     public static Day[] values() { return array; }
        // }

        System.out.println("Кожне значення enum — це:");
        System.out.println("  • public static final обʼєкт цього ж класу");
        System.out.println("  • Конструктор — private (не можна створити new Day())");
        System.out.println("  • ordinal() — повертає внутрішній номер");
        System.out.println("  • values() — повертає статичний масив усіх значень");
        System.out.println();

        // Доказ — кожне значення є обʼєктом класу
        System.out.println("PizzaSize.SMALL — це обʼєкт класу: "
                + PizzaSize.SMALL.getClass().getSimpleName());
        System.out.println("PizzaSize.SMALL — ordinal: " + PizzaSize.SMALL.ordinal());
        System.out.println("PizzaSize.LARGE — ordinal: " + PizzaSize.LARGE.ordinal());
        System.out.println();

        // ============================================================
        //   Блок 4: Метод asList() — приклад з лекції
        // ============================================================
        System.out.println("=== Блок 4: Статичний метод asList() ===");

        // Метод asList() повертає ArrayList замість масиву
        List<Color> colors = Color.asList();
        System.out.println("Color.asList(): " + colors);
        System.out.println("Тип: " + colors.getClass().getSimpleName()); // ArrayList

        // ArrayList зручніший за масив — можна використовувати contains, indexOf, тощо
        System.out.println("Містить RED? " + colors.contains(Color.RED));     // true
        System.out.println("Індекс BLUE: " + colors.indexOf(Color.BLUE));     // 2
        System.out.println("Розмір: " + colors.size());                        // 3
    }
}
