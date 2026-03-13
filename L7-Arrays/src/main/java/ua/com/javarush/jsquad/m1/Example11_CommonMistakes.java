package ua.com.javarush.jsquad.m1;

import java.util.Arrays;

/**
 * Лекція 7: Масиви — Типові помилки початківців
 *
 * При роботі з масивами початківці часто допускають одні й ті ж помилки.
 * Знання цих помилок допоможе уникнути їх і швидше знаходити баги.
 *
 * Найпоширеніші помилки:
 *   1. ArrayIndexOutOfBoundsException — вихід за межі масиву
 *   2. Off-by-one — помилка на одиницю (використання <= замість <)
 *   3. Друк масиву без Arrays.toString()
 *   4. Плутанина між length та останнім індексом
 *
 * Аналогія з життя: ви намагаєтесь відкрити шафку №10,
 * а шафок лише 5 (номери 0-4). Результат — помилка!
 *
 * Реальне застосування: розуміння цих помилок заощадить
 * години відлагодження (debugging) у реальних проектах.
 */
public class Example11_CommonMistakes {

    public static void main(String[] args) {

        // ============================================================
        //   Помилка 1: Вихід за межі масиву
        // ============================================================
        System.out.println("=== Помилка: вихід за межі масиву ===");

        int[] numbers = {10, 20, 30}; // індекси: 0, 1, 2

        System.out.println("Масив: " + Arrays.toString(numbers));
        System.out.println("length = " + numbers.length); // 3
        System.out.println("Останній індекс = " + (numbers.length - 1)); // 2

        // numbers[3] — ПОМИЛКА! ArrayIndexOutOfBoundsException
        // numbers[-1] — теж ПОМИЛКА! Від'ємних індексів немає
        System.out.println("numbers[3] або numbers[-1] викличуть помилку!");
        System.out.println();

        // ============================================================
        //   Помилка 2: Off-by-one (помилка на одиницю)
        // ============================================================
        System.out.println("=== Помилка: off-by-one ===");

        int[] data = {1, 2, 3, 4, 5};

        // НЕПРАВИЛЬНО — i <= data.length спробує звернутися до data[5]
        // for (int i = 0; i <= data.length; i++) { // ПОМИЛКА!
        //     System.out.println(data[i]);
        // }

        // ПРАВИЛЬНО — i < data.length (строго менше!)
        System.out.print("Правильний перебір: ");
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
        System.out.println("Запам'ятай: завжди i < length, а НЕ i <= length!");
        System.out.println();

        // ============================================================
        //   Помилка 3: Друк масиву без Arrays.toString()
        // ============================================================
        System.out.println("=== Помилка: друк масиву ===");

        int[] array = {10, 20, 30};

        // НЕПРАВИЛЬНО — виведе щось на кшталт [I@1b6d3586
        System.out.println("println(array):          " + array);

        // ПРАВИЛЬНО — використовуємо Arrays.toString()
        System.out.println("Arrays.toString(array):  " + Arrays.toString(array));
        System.out.println();

        // ============================================================
        //   Помилка 4: Плутанина — індекс починається з 0
        // ============================================================
        System.out.println("=== Помилка: індексація з 0 ===");

        String[] students = {"Андрій", "Богдан", "Вікторія"};

        // Перший студент — індекс 0, а не 1!
        System.out.println("Перший студент:  students[0] = " + students[0]); // Андрій
        System.out.println("Другий студент:  students[1] = " + students[1]); // Богдан
        System.out.println("Третій студент:  students[2] = " + students[2]); // Вікторія
        // students[3] — ПОМИЛКА! Немає четвертого елемента
        System.out.println();

        // ============================================================
        //   Помилка 5: NullPointerException у String масиві
        // ============================================================
        System.out.println("=== Помилка: null у String масиві ===");

        String[] names = new String[3];
        names[0] = "Олена";
        // names[1] і names[2] = null (не заповнені)

        System.out.println("names[0] = " + names[0]); // Олена
        System.out.println("names[1] = " + names[1]); // null

        // names[1].length() — ПОМИЛКА! NullPointerException
        // Перевіряйте на null перед використанням:
        if (names[1] != null) {
            System.out.println("Довжина: " + names[1].length());
        } else {
            System.out.println("names[1] порожній (null) — не можна викликати методи!");
        }
    }
}
