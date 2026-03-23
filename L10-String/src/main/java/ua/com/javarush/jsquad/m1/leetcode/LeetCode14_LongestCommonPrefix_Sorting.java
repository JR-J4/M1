package ua.com.javarush.jsquad.m1.leetcode;

import java.util.Arrays;

/**
 * Рiшення 2: Сортування масиву + порiвняння першого та останнього рядкiв.
 *
 * Iдея:
 * - Пiсля сортування лише найменший i найбiльший рядок визначають максимальний
 *   спiльний префiкс, що буде спiльним для всiх.
 * - Порiвнюємо символи першого та останнього рядка.
 *
 * Час: O(n log n + m), пам'ять: O(1) додатково (або O(log n) для сортування).
 */
public class LeetCode14_LongestCommonPrefix_Sorting {

    public static void main(String[] args) {
        String[][] tests = {
                {"flower", "flow", "flight"},
                {"dog", "racecar", "car"},
                {"inbox", "input", "inside"},
                {"a"}
        };

        for (String[] test : tests) {
            System.out.println("Вхiд: " + Arrays.toString(test));
            System.out.println("Префiкс: " + longestCommonPrefix(test));
            System.out.println();
        }
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        Arrays.sort(strs);
        String first = strs[0];
        String last = strs[strs.length - 1];

        int index = 0;
        while (index < first.length()
                && index < last.length()
                && first.charAt(index) == last.charAt(index)) {
            index++;
        }
        return first.substring(0, index);
    }
}
