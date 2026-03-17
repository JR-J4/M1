package ua.com.javarush.jsquad.m1;

import java.util.Arrays;

/**
 * Лекція 8: Багатовимірні масиви
 *
 * У Java можна створювати масиви з будь-якою кількістю вимірів:
 * - тривимірний (масив двовимірних масивів)
 * - чотиривимірний і т.д.
 *
 * Синтаксис:
 *   тип[][][] ім'я = new тип[x][y][z];        // 3D
 *   тип[][][][] ім'я = new тип[a][b][c][d];   // 4D
 *
 * Аналогія з життя:
 * - 1D масив = шеренга людей (лише номер позиції)
 * - 2D масив = кінозал (ряд + місце)
 * - 3D масив = багатоповерхова парковка (поверх + ряд + місце)
 *
 * Реальне застосування: 3D-графіка (координати x,y,z),
 * дані за рік (місяць/день/година), кольорові зображення (RGB).
 *
 *
 *                     TOP
 *           Left <- CENTER -> RIGHT
 *                   BOTTOM
 *                    Back
 *
 *
 *      r r r
 *      r r r
 *      r r r
 *
 */
public class Example06_MultiDimensionalArrays {

    public static void main(String[] args) {

        char[][][] cube = new char[6][3][3];

        cube[0][0][0] = 'r';
        cube[0][0][1] = 'r';
        cube[0][0][2] = 'r';

        cube[0][1][0] = 'r';
        cube[0][1][1] = 'r';
        cube[0][1][2] = 'r';

        cube[0][2][0] = 'r';
        cube[0][2][1] = 'r';
        cube[0][2][2] = 'r';









        int[][][] test = new int[2][3][4];

        test[0][0][0] = 15;

        test[1][0][0] = 20;

        test[0][2][3] = 8;


        for (int[][] ints : test) {
            System.out.println("----------");
            for (int[] anInt : ints) {
                System.out.println(Arrays.toString(anInt));
            }
        }




        int[][][] quickInit = {
                {
                    {1, 2, 3, 5},
                    {1, 2, 3, 5},
                    {1, 2, 3, 5},
                },
                {
                    {1, 2, 3, 5},
                    {1, 2, 3, 5},
                    {1, 2, 3, 5},
                }
        };





        // ============================================================
        //   Приклад 1: Тривимірний масив — багатоповерхова парковка
        // ============================================================
        System.out.println("=== Тривимірний масив: Парковка ===");

        // Парковка: 3 поверхи, 2 ряди на поверсі, 4 місця в ряді
        // 0 = вільно, 1 = зайнято
        int[][][] parking = new int[3][2][4];

        // Паркуємо авто
        parking[0][0][1] = 1; // поверх 0, ряд 0, місце 1
        parking[0][1][3] = 1; // поверх 0, ряд 1, місце 3
        parking[1][0][0] = 1; // поверх 1, ряд 0, місце 0
        parking[2][1][2] = 1; // поверх 2, ряд 1, місце 2

        for (int floor = 0; floor < parking.length; floor++) {
            System.out.println("--- Поверх " + floor + " ---");
            for (int row = 0; row < parking[floor].length; row++) {
                System.out.print("  Ряд " + row + ": ");
                for (int spot = 0; spot < parking[floor][row].length; spot++) {
                    System.out.print(parking[floor][row][spot] == 1 ? "[X]" : "[ ]");
                }
                System.out.println();
            }
        }
        System.out.println();

        // ============================================================
        //   Приклад 2: Підрахунок вільних місць
        // ============================================================
        System.out.println("=== Підрахунок вільних місць ===");

        int totalSpots = 0;
        int occupiedSpots = 0;

        for (int[][] floor : parking) {
            for (int[] row : floor) {
                for (int spot : row) {
                    totalSpots++;
                    if (spot == 1) {
                        occupiedSpots++;
                    }
                }
            }
        }

        System.out.println("Всього місць:   " + totalSpots);
        System.out.println("Зайнятих:       " + occupiedSpots);
        System.out.println("Вільних:        " + (totalSpots - occupiedSpots));
        System.out.println();

        // ============================================================
        //   Приклад 3: Розмірності багатовимірного масиву
        // ============================================================
        System.out.println("=== Розмірності масиву ===");

        // Створюємо 4-вимірний масив (як у лекції)
        int[][][][] matrix4D = new int[2][3][4][5];

        System.out.println("4D масив: int[2][3][4][5]");
        System.out.println("Вимір 1 (matrix4D.length):          " + matrix4D.length);          // 2
        System.out.println("Вимір 2 (matrix4D[0].length):       " + matrix4D[0].length);       // 3
        System.out.println("Вимір 3 (matrix4D[0][0].length):    " + matrix4D[0][0].length);    // 4
        System.out.println("Вимір 4 (matrix4D[0][0][0].length): " + matrix4D[0][0][0].length); // 5
        System.out.println("Загальна кількість елементів: " + (2 * 3 * 4 * 5)); // 120

        System.out.println();
        System.out.println("На практиці масиви з 3+ вимірами використовують рідко.");
        System.out.println("Найчастіше достатньо 2D масивів.");
    }
}
