package ua.com.javarush.jsquad.m1.leetcode;

/**
 * Лекцiя 10: LeetCode 14 — Longest Common Prefix (шаблон).
 *
 * Завдання:
 * - Прийняти масив рядкiв i повернути найдовший спiльний префiкс.
 * - Якщо префiкса немає, повертаємо порожнiй рядок "".
 *
 * Обмеження:
 * 1 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] складається з малих латинських букв.
 *
 * Початковий шаблон: заповнiть метод longestCommonPrefix().
 */
public class LeetCode14_LongestCommonPrefix_Template {

    public static void main(String[] args) {
        String[] sample = {};
        String prefix = longestCommonPrefix(sample);
        System.out.println("Результат (очiкується \"fl\"): " + prefix);
    }

    /**
     * TODO: Реалiзуйте алгоритм пошуку найдовшого спiльного префiкса.
     *
     * @param strs масив рядкiв
     * @return найдовший спiльний префiкс або ""
     *
     * ["flower","flow","flight"]
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0){
            return "";
        }

        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()){
                    return "";
                }
            }
        }

        return prefix;
    }
}
