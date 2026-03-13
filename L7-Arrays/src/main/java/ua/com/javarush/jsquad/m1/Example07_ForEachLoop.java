package ua.com.javarush.jsquad.m1;

/**
 * Лекція 7: Масиви — Цикл for-each
 *
 * Java має спеціальний цикл для перебору масивів — for-each.
 * Він автоматично бере кожен елемент масиву по черзі,
 * без необхідності працювати з індексами.
 *
 * Синтаксис:
 *   for (тип змінна : масив) {
 *       // змінна містить поточний елемент
 *   }
 *
 * Аналогія з життя: ви перебираєте яблука в кошику —
 * берете кожне яблуко по черзі, не рахуючи їх номери.
 *
 * Реальне застосування: виведення списку, підрахунок суми,
 * коли НЕ потрібен індекс елемента.
 */
public class Example07_ForEachLoop {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Простий for-each
        // ============================================================
        System.out.println("=== Простий for-each ===");

        // Уявіть: ви читаєте меню ресторану — просто перелічуєте страви
        String[] menu = {"Борщ", "Вареники", "Котлета по-київськи", "Сирники", "Компот"};

//        for (int i = 0; i < menu.length; i++) {
//            System.out.println("- " + menu[i]);
//        }

        for (String dish : menu) {
            System.out.println("- " + dish);
        }

        System.out.println();

        // ============================================================
        //   Приклад 2: Порівняння for і for-each
        // ============================================================
        System.out.println("=== for vs for-each ===");

        int[] numbers = {10, 20, 30, 40, 50};

        // Звичайний for — є доступ до індексу
        System.out.print("for:      ");

        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }

        System.out.println();

        // for-each — коротше, але без індексу
        System.out.print("for-each: ");

        for (int num : numbers) {
            System.out.print(num + " ");
        }

        System.out.println();
        System.out.println();

        // ============================================================
        //   Приклад 3: Підрахунок середнього бала
        // ============================================================
        System.out.println("=== Середній бал ===");

        // Реальне застосування: підрахунок середньої оцінки студента
        double[] grades = {8.5, 9.0, 7.5, 10.0, 8.0, 9.5};

        double sum = 0;

        for (double grade : grades) {
            sum = sum + grade;
        }

        double average = sum / grades.length;

        System.out.println("Оцінки: 8.5, 9.0, 7.5, 10.0, 8.0, 9.5");
        System.out.println("Середній бал: " + average); // 8.75
        System.out.println();

        // ============================================================
        //   Приклад 4: Коли for-each НЕ підходить
        // ============================================================
        System.out.println("=== Коли for-each НЕ підходить ===");

        // for-each не дає змінювати елементи масиву!
        int[] values = {1, 2, 3, 4, 5};

        // Спроба подвоїти — НЕ ПРАЦЮЄ (змінюється лише копія)

        for (int val : values) {
            val = val * 2; // це змінює локальну змінну, а не масив!
        }


        System.out.print("Після for-each (не змінилось): ");
        for (int val : values) {
            System.out.print(val + " "); // 1 2 3 4 5
        }
        System.out.println();

        // Правильний спосіб — звичайний for
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] * 2; // змінюємо сам масив
        }
        System.out.print("Після for (змінилось):         ");
        for (int val : values) {
            System.out.print(val + " "); // 2 4 6 8 10
        }
        System.out.println();
    }
}
