package ua.com.javarush.jsquad.m1.leetcode;

import java.util.Arrays;

/**
 * Рiшення 1: Вертикальне сканування (зменшення базового префiкса).
 *
 * Iдея:
 * - Вважати, що весь перший рядок — потенцiйний префiкс.
 * - Для кожного наступного рядка скорочувати prefix, поки рядок не починається з prefix.
 * - Якщо префiкс спорожнiв — спiльного префiкса немає.
 *
 * Часова складнiсть: O(n * m), де n — кiлькiсть рядкiв, m — довжина префiкса.
 * Пам'ять: O(1) додатково.
 */
public class LeetCode14_LongestCommonPrefix_Vertical {

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

        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        return prefix;
    }
}
