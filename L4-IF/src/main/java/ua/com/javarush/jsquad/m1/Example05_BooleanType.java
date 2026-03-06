package ua.com.javarush.jsquad.m1;

/**
 * Лекція 4: Логічний тип boolean
 *
 * Тип boolean — найпростіший тип у Java. Він має лише два значення:
 *   true  (істина) — так, правда
 *   false (хиба)   — ні, неправда
 *
 * Аналогія з життя: boolean — це як вимикач світла.
 * Він може бути тільки у двох станах: увімкнений (true) або вимкнений (false).
 * Не буває "трохи увімкнений" — тільки так або ні.
 *
 * Ще аналогія: відповідь на запитання "Чи йде дощ?" — так або ні.
 *
 * Реальне застосування: прапорці (flags) у програмах, стани кнопок,
 * перевірка прав доступу, збереження результатів порівнянь.
 */
public class Example05_BooleanType {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Створення boolean змінних
        // ============================================================
        System.out.println("=== Створення boolean змінних ===");

        boolean isOK = true;         // Все добре? — Так!
        boolean hasError = false;    // Є помилка? — Ні!

        System.out.println("isOK: " + isOK);         // true
        System.out.println("hasError: " + hasError);  // false
        System.out.println();

        // ============================================================
        //   Приклад 2: Результат порівняння — це boolean
        // ============================================================
        System.out.println("=== Результат порівняння ===");

        // Уявіть: система перевіряє чи людина пенсіонер
        int age = 70;
        boolean isSenior = (age > 65);  // 70 > 65? -> true!

        System.out.println("Вік: " + age);
        System.out.println("Пенсіонер? " + isSenior);  // true
        System.out.println();

        // Уявіть: спортсмен побив рекорд
        int record = 612;
        int value = 615;
        boolean hasNewRecord = (value > record);  // 615 > 612? -> true!

        System.out.println("Старий рекорд: " + record);
        System.out.println("Новий результат: " + value);
        System.out.println("Новий рекорд? " + hasNewRecord);  // true
        System.out.println();

        // ============================================================
        //   Приклад 3: boolean в умові if
        // ============================================================
        System.out.println("=== boolean в умові if ===");

        // Два способи написати одне й те саме:

        // Спосіб 1: зберігаємо результат у змінну
        boolean isPensioner = (age > 65);
        if (isPensioner) {
            System.out.println("Спосіб 1: Час на пенсію!");
        }

        // Спосіб 2: порівняння прямо в if
        if (age > 65) {
            System.out.println("Спосіб 2: Час на пенсію!");
        }

        // Обидва способи дають однаковий результат!
        System.out.println();

        // ============================================================
        //   Приклад 4: Прапорці (flags) у програмі
        // ============================================================
        System.out.println("=== Прапорці у програмі ===");

        // Уявіть: налаштування додатку
        boolean isDarkMode = true;      // Темна тема увімкнена?
        boolean isSoundOn = false;      // Звук увімкнений?
        boolean isLoggedIn = true;      // Користувач залогінений?

        if (isDarkMode) {
            System.out.println("Тема: темна");
        } else {
            System.out.println("Тема: світла");
        }

        if (isSoundOn) {
            System.out.println("Звук: увімкнений");
        } else {
            System.out.println("Звук: вимкнений");
        }

        if (isLoggedIn) {
            System.out.println("Статус: авторизований");
        } else {
            System.out.println("Статус: гість");
        }
    }
}
