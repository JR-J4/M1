package ua.com.javarush.jsquad.m1;

/**
 * Лекція 8: Двовимірні масиви — Перебір елементів
 *
 * Для обходу двовимірного масиву використовують вкладені цикли:
 * - зовнішній цикл — перебирає рядки
 * - внутрішній цикл — перебирає стовпці (елементи рядка)
 *
 * Синтаксис:
 *   for (int i = 0; i < масив.length; i++) {
 *       for (int j = 0; j < масив[i].length; j++) {
 *           // масив[i][j] — поточний елемент
 *       }
 *   }
 *
 * Аналогія з життя: перевірка квитків у літаку — проходите ряд за рядом,
 * у кожному ряді перевіряєте місце за місцем.
 *
 * Реальне застосування: виведення таблиць, підрахунок підсумків,
 * пошук елементів, обробка зображень (пікселі).
 */
public class Example03_TwoDimensionalIteration {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Виведення таблиці оцінок
        // ============================================================
        System.out.println("=== Таблиця оцінок студентів ===");

        // Сценарій: журнал оцінок — 4 студенти, 3 предмети
        String[] students = {"Олена", "Богдан", "Марія", "Тарас"};
        String[] subjects = {"Математика", "Фізика", "Історія"};
        int[][] grades = {
                {10, 8, 11},
                {7, 12, 9},
                {11, 10, 12},
                {9, 7, 8}
        };

        // Заголовок
        System.out.printf("%-10s", "");
        for (String subject : subjects) {
            System.out.printf("%-13s", subject);
        }
        System.out.println();

        // Зовнішній цикл — по рядках (студентах)
        for (int i = 0; i < grades.length; i++) {
            System.out.printf("%-10s", students[i]);
            // Внутрішній цикл — по стовпцях (предметах)
            for (int j = 0; j < grades[i].length; j++) {
                System.out.printf("%-13d", grades[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        // ============================================================
        //   Приклад 2: Підрахунок суми та середнього по рядках
        // ============================================================
        System.out.println("=== Середній бал кожного студента ===");

        for (int i = 0; i < grades.length; i++) {
            int sum = 0;
            for (int j = 0; j < grades[i].length; j++) {
                sum += grades[i][j];
            }
            double average = (double) sum / grades[i].length;
            System.out.printf("%s: середній бал = %.1f%n", students[i], average);
        }
        System.out.println();

        // ============================================================
        //   Приклад 3: Пошук максимального елемента
        // ============================================================
        System.out.println("=== Пошук найвищої оцінки ===");

        int maxGrade = grades[0][0];
        int maxRow = 0;
        int maxCol = 0;

        for (int i = 0; i < grades.length; i++) {
            for (int j = 0; j < grades[i].length; j++) {
                if (grades[i][j] > maxGrade) {
                    maxGrade = grades[i][j];
                    maxRow = i;
                    maxCol = j;
                }
            }
        }

        System.out.println("Найвища оцінка: " + maxGrade);
        System.out.println("Студент: " + students[maxRow] + ", предмет: " + subjects[maxCol]);
        System.out.println();

        // ============================================================
        //   Приклад 4: Перебір за допомогою for-each
        // ============================================================
        System.out.println("=== Перебір через for-each ===");

        // for-each для двовимірного масиву: зовнішній цикл дає рядок (одновимірний масив)
        int totalSum = 0;
        for (int[] row : grades) {
            for (int grade : row) {
                totalSum += grade;
            }
        }
        System.out.println("Загальна сума всіх оцінок: " + totalSum);
        System.out.println("Загальна кількість оцінок: " + (grades.length * grades[0].length));
    }
}
