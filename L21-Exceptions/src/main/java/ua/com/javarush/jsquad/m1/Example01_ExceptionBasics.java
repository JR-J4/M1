package ua.com.javarush.jsquad.m1;

/**
 * Лекція 21: Винятки — Основи винятків та try-catch
 *
 * Виняток (Exception) — це помилка, що виникає під час виконання програми.
 * Коли виникає помилка, Java створює спеціальний обʼєкт-виняток з інформацією
 * про помилку і «викидає» його. Якщо виняток не перехоплений — програма аварійно
 * завершується.
 *
 * Синтаксис перехоплення:
 *   try {
 *       // код, де може виникнути помилка
 *   }
 *   catch (ТипВинятку імʼя) {
 *       // код обробки помилки
 *   }
 *
 * Аналогія з життя: уяви, що ти несеш стос тарілок. Якщо спіткнешся (виняток),
 * тарілки впадуть і розібʼються (аварійне завершення). Але якщо друг стоїть поруч
 * і ловить тарілки (try-catch) — катастрофи не буде.
 *
 * Реальне застосування: обробка помилок при роботі з файлами, мережею, базою даних,
 * введенням користувача, діленням на нуль, зверненням до null.
 */
public class Example01_ExceptionBasics {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Що відбувається без обробки винятку
        // ============================================================
        System.out.println("=== Блок 1: Виняток без обробки ===");

        // Якщо розкоментувати — програма впаде з ArithmeticException:
        // int result = 10 / 0;

        System.out.println("Ділення на 0 без try-catch зупинить програму!");
        System.out.println("ArithmeticException: / by zero");
        System.out.println();

        // ============================================================
        //   Блок 2: Перехоплення ArithmeticException
        // ============================================================
        System.out.println("=== Блок 2: try-catch — ділення на нуль ===");

        // Сценарій: калькулятор, де користувач ввів 0 як дільник
        int a = 100;
        int b = 0;

        try {
            System.out.println("Спроба поділити " + a + " на " + b + "...");
            int result = a / b;  // ← тут виникне виняток
            System.out.println("Результат: " + result); // ← цей рядок НЕ виконається
        }
        catch (ArithmeticException e) {
            System.out.println("Помилка! Ділення на нуль неможливе.");
            System.out.println("Повідомлення: " + e.getMessage()); // "/ by zero"
        }

        System.out.println("Програма продовжує працювати після catch!");
        System.out.println();

        // ============================================================
        //   Блок 3: Перехоплення ArrayIndexOutOfBoundsException
        // ============================================================
        System.out.println("=== Блок 3: try-catch — вихід за межі масиву ===");

        // Сценарій: список студентів, запит за неіснуючим індексом
        String[] students = {"Олена", "Дмитро", "Софія"};

        try {
            System.out.println("Студент 0: " + students[0]); // OK
            System.out.println("Студент 1: " + students[1]); // OK
            System.out.println("Студент 5: " + students[5]); // ← виняток!
            System.out.println("Цей рядок не виконається");
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Помилка! Такого індексу немає в масиві.");
            System.out.println("Повідомлення: " + e.getMessage()); // "Index 5 out of bounds for length 3"
        }

        System.out.println();

        // ============================================================
        //   Блок 4: Перехоплення NullPointerException
        // ============================================================
        System.out.println("=== Блок 4: try-catch — звернення до null ===");

        // Сценарій: отримали дані від сервера, але обʼєкт порожній (null)
        String serverResponse = null;

        try {
            System.out.println("Довжина відповіді: " + serverResponse.length()); // ← NPE!
        }
        catch (NullPointerException e) {
            System.out.println("Помилка! Відповідь від сервера — null.");
            System.out.println("Не можна викликати метод у null-обʼєкта.");
        }

        System.out.println();

        // ============================================================
        //   Блок 5: Код після try-catch продовжує виконуватись
        // ============================================================
        System.out.println("=== Блок 5: Програма живе далі ===");

        // try-catch — це «страховка». Якщо помилка — обробляємо і йдемо далі
        int[] numbers = {10, 0, 5, 0, 8};

        for (int i = 0; i < numbers.length - 1; i++) {
            try {
                int result = numbers[i] / numbers[i + 1];
                System.out.println(numbers[i] + " / " + numbers[i + 1] + " = " + result);
            }
            catch (ArithmeticException e) {
                System.out.println(numbers[i] + " / " + numbers[i + 1] + " = ПОМИЛКА (ділення на 0)");
            }
        }

        System.out.println();
        System.out.println("Головне: try-catch не зупиняє програму.");
        System.out.println("Виняток перехоплюється → обробляється → програма працює далі.");
    }
}
