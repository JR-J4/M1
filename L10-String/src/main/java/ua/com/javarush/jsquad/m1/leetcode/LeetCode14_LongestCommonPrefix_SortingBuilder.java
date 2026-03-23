package ua.com.javarush.jsquad.m1.leetcode;

import java.util.Arrays;

/**
 * Рiшення 3: Сортування масиву + поступове нарощування префiкса через StringBuilder.
 *
 * Iдея та сама, що й у рiшеннi з сортуванням, але результат зручно формувати
 * у StringBuilder, зупиняючись, щойно символи перестають збiгатися.
 */
public class LeetCode14_LongestCommonPrefix_SortingBuilder {

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

        StringBuilder ans = new StringBuilder();
        int limit = Math.min(first.length(), last.length());

        for (int i = 0; i < limit; i++) {
            if (first.charAt(i) != last.charAt(i)) {
                return ans.toString();
            }
            ans.append(first.charAt(i));
        }
        return ans.toString();
    }
}
