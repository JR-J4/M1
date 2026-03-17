package ua.com.javarush.jsquad.m1;

/**
 * Лекція 8: Двовимірні масиви — Практичні задачі
 *
 * Двовимірні масиви широко використовуються для моделювання
 * табличних даних та ігрових полів.
 *
 * Аналогія з життя: шахівниця — це двовимірний масив 8x8,
 * де кожна клітинка може містити фігуру або бути порожньою.
 *
 * Реальне застосування: хрестики-нулики, морський бій,
 * карта лабіринту, розклад занять.
 */
public class Example04_TwoDimensionalPractice {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Ігрове поле «Хрестики-нулики»
        // ============================================================
        System.out.println("=== Хрестики-нулики ===");

        // ' ' — порожньо, 'X' — хрестик, 'O' — нулик
        char[][] board = {
                {'X', 'O', 'X'},
                {' ', 'X', 'O'},
                {'O', ' ', 'X'}
        };

        // Виведення поля
        System.out.println("  0   1   2");
        for (int i = 0; i < board.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < board[i].length - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < board.length - 1) {
                System.out.println("  ---------");
            }
        }
        System.out.println();

        // ============================================================
        //   Приклад 2: Транспонування матриці
        // ============================================================
        System.out.println("=== Транспонування матриці ===");

        // Сценарій: повернути таблицю — рядки стають стовпцями
        int[][] original = {
                {1, 2, 3},
                {4, 5, 6}
        };

        // Оригінал: 2 рядки x 3 стовпці -> Транспонована: 3 рядки x 2 стовпці
        int[][] transposed = new int[original[0].length][original.length];

        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                transposed[j][i] = original[i][j];
            }
        }

        System.out.println("Оригінальна матриця:");
        printMatrix(original);

        System.out.println("Транспонована матриця:");
        printMatrix(transposed);

        // ============================================================
        //   Приклад 3: Карта кімнати (лабіринт)
        // ============================================================
        System.out.println("=== Карта лабіринту ===");

        // 0 — прохід, 1 — стіна, 2 — вхід, 3 — вихід
        int[][] maze = {
                {2, 0, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0},
                {1, 1, 1, 1, 3}
        };

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                switch (maze[i][j]) {
                    case 0 -> System.out.print(". "); // прохід
                    case 1 -> System.out.print("# "); // стіна
                    case 2 -> System.out.print("S "); // старт
                    case 3 -> System.out.print("E "); // вихід
                }
            }
            System.out.println();
        }
        System.out.println("S = старт, E = вихід, # = стіна, . = прохід");
    }

    /**
     * Допоміжний метод для виведення матриці
     */
    static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.printf("%4d", val);
            }
            System.out.println();
        }
        System.out.println();
    }
}
