package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;

/**
 * Лекція 16: Списки — Улаштування ArrayList
 *
 * Всередині ArrayList — звичайний масив та змінна size.
 *
 * Як це працює:
 *   - Спочатку внутрішній масив має довжину 10, а size = 0
 *   - При додаванні елемента — він записується в масив, size збільшується на 1
 *   - Коли масив заповнений і додається ще елемент:
 *     1) Створюється новий масив у 1.5 рази довший
 *     2) Копіюються всі елементи з існуючого масиву
 *     3) Посилання замінюється на новий масив
 *     4) Новий елемент записується в нову комірку
 *     5) size збільшується на 1
 *
 * Аналогія з життя: уявіть блокнот на 10 сторінок. Коли всі сторінки заповнені,
 * ви купуєте новий блокнот на 15 сторінок, переписуєте туди все, і продовжуєте.
 *
 * Реальне застосування: розуміння внутрішньої роботи допомагає писати
 * ефективніший код — наприклад, задавати початковий розмір, якщо відомо
 * приблизну кількість елементів.
 */
public class Example05_ArrayListInternals {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Демонстрація динамічного зростання
        // ============================================================
        System.out.println("=== Динамічне зростання ArrayList ===");

        // Уявіть: журнал відвідувань — спочатку мало учнів, потім все більше
        ArrayList<String> log = new ArrayList<String>(); // внутрішній масив = 10

        System.out.println("Початковий розмір (size): " + log.size()); // 0

        // Додаємо 12 елементів — перевищуємо початкову ємність (10)
        for (int i = 1; i <= 12; i++) {
            log.add("Запис #" + i);
            System.out.println("Додано запис #" + i + ", size = " + log.size());
        }
        // Десь між 10 та 11 елементом ArrayList створив новий масив розміром 15

        System.out.println("Фінальний список: " + log);
        System.out.println();

        // ============================================================
        //   Приклад 2: Задання початкової ємності
        // ============================================================
        System.out.println("=== Початкова ємність (capacity) ===");

        // Якщо знаємо приблизну кількість — задаємо наперед для оптимізації
        // Уявіть: організовуємо конференцію на 500 осіб
        ArrayList<String> participants = new ArrayList<String>(500);
        participants.add("Учасник 1");
        participants.add("Учасник 2");

        System.out.println("Розмір (size): " + participants.size()); // 2
        // Але внутрішній масив вже на 500 елементів — не буде зайвих розширень
        System.out.println("Ємність задана заздалегідь: 500");
        System.out.println("Це запобігає зайвим копіюванням масивів");
        System.out.println();

        // ============================================================
        //   Приклад 3: Різниця між size та capacity
        // ============================================================
        System.out.println("=== size vs capacity ===");

        // size — скільки елементів реально є у списку
        // capacity — скільки місць виділено у внутрішньому масиві

        ArrayList<Integer> data = new ArrayList<Integer>(); // capacity = 10
        System.out.println("Порожній список:");
        System.out.println("  size = " + data.size());        // 0
        System.out.println("  capacity = 10 (за замовчуванням)");

        data.add(100);
        data.add(200);
        data.add(300);
        System.out.println("Після додавання 3 елементів:");
        System.out.println("  size = " + data.size());        // 3
        System.out.println("  capacity = 10 (ще достатньо місця)");
        System.out.println();

        // ============================================================
        //   Приклад 4: Як працює розширення (симуляція)
        // ============================================================
        System.out.println("=== Симуляція розширення масиву ===");

        // Імітуємо те, що робить ArrayList всередині
        int capacity = 4; // початкова ємність (для наочності — менша)
        int size = 0;
        int[] internalArray = new int[capacity];

        int[] elementsToAdd = {10, 20, 30, 40, 50, 60};

        for (int element : elementsToAdd) {
            // Перевіряємо, чи є місце
            if (size >= capacity) {
                // Створюємо новий масив у 1.5 рази більший
                int newCapacity = capacity + capacity / 2;
                System.out.println("  Розширення: " + capacity + " -> " + newCapacity);
                int[] newArray = new int[newCapacity];

                // Копіюємо старі елементи
                for (int j = 0; j < size; j++) {
                    newArray[j] = internalArray[j];
                }

                internalArray = newArray;
                capacity = newCapacity;
            }

            // Додаємо елемент
            internalArray[size] = element;
            size++;
            System.out.println("Додано " + element + " -> size=" + size + ", capacity=" + capacity);
        }
    }
}
