package ua.com.javarush.jsquad.m1;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Лекція 18: Колекції Map — LeetCode #1 "Two Sum" (Easy)
 *
 * Задача: дано масив цілих чисел nums і цільове число target.
 * Повернути індекси двох елементів, сума яких дорівнює target.
 * Кожен вхід має рівно одне рішення. Не можна використати один елемент двічі.
 *
 * Приклад:
 *   nums = [2, 7, 11, 15], target = 9  →  [0, 1]  (бо 2 + 7 = 9)
 *
 * Ключова ідея з HashMap:
 *   Для кожного елемента рахуємо «доповнення» (complement = target - nums[i]).
 *   Якщо доповнення вже є в HashMap — знайшли пару!
 *   Якщо ні — зберігаємо nums[i] → i у HashMap для майбутніх перевірок.
 *
 * Складність:
 *   Брутфорс (два цикли): O(n²)
 *   З HashMap:             O(n) — один прохід!
 *
 * Аналогія з життя: шукаєте пару шкарпеток у купі. Замість порівнювати кожну
 * з кожною (O(n²)), ви кладете кожну шкарпетку на полицю за кольором.
 * Коли беретеш нову — одразу дивишся: чи є вже така на полиці? Якщо є — пара!
 *
 * Реальне застосування: пошук комплементарних значень, фінансові транзакції
 * (знайти дві операції, що в сумі дають конкретну суму).
 */
public class Example08_LeetCode_TwoSum {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Брутфорс рішення — O(n²)
        // ============================================================
        System.out.println("=== Блок 1: Брутфорс — O(n²) ===");

        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;

        int[] resultBrute = twoSumBruteForce(nums1, target1);
        System.out.println("nums = " + Arrays.toString(nums1) + ", target = " + target1);
        System.out.println("Результат: " + Arrays.toString(resultBrute));
        System.out.println("Пояснення: nums[" + resultBrute[0] + "] + nums["
                + resultBrute[1] + "] = " + nums1[resultBrute[0]] + " + "
                + nums1[resultBrute[1]] + " = " + target1);
        System.out.println();

        // ============================================================
        //   Блок 2: Рішення з HashMap — O(n)
        // ============================================================
        System.out.println("=== Блок 2: HashMap рішення — O(n) ===");

        int[] nums2 = {3, 2, 4};
        int target2 = 6;

        int[] resultMap = twoSumHashMap(nums2, target2);
        System.out.println("nums = " + Arrays.toString(nums2) + ", target = " + target2);
        System.out.println("Результат: " + Arrays.toString(resultMap));
        System.out.println("Пояснення: nums[" + resultMap[0] + "] + nums["
                + resultMap[1] + "] = " + nums2[resultMap[0]] + " + "
                + nums2[resultMap[1]] + " = " + target2);
        System.out.println();

        // ============================================================
        //   Блок 3: Ще один приклад — більший масив
        // ============================================================
        System.out.println("=== Блок 3: Більший масив ===");

        int[] nums3 = {1, 5, 3, 8, 12, 4, 7, 2};
        int target3 = 11;

        int[] result3 = twoSumHashMap(nums3, target3);
        System.out.println("nums = " + Arrays.toString(nums3) + ", target = " + target3);
        System.out.println("Результат: " + Arrays.toString(result3));
        System.out.println("Пояснення: nums[" + result3[0] + "] + nums["
                + result3[1] + "] = " + nums3[result3[0]] + " + "
                + nums3[result3[1]] + " = " + target3);
        System.out.println();

        // ============================================================
        //   Блок 4: Покрокова візуалізація роботи HashMap
        // ============================================================
        System.out.println("=== Блок 4: Покрокова візуалізація ===");

        int[] demo = {2, 7, 11, 15};
        int demoTarget = 9;
        twoSumVisualized(demo, demoTarget);
    }

    /**
     * Брутфорс: перебираємо всі пари — O(n²)
     */
    static int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * HashMap рішення: один прохід — O(n)
     * Зберігаємо значення → індекс. Для кожного елемента перевіряємо,
     * чи є доповнення (target - nums[i]) вже у мапі.
     */
    static int[] twoSumHashMap(int[] nums, int target) {
        HashMap<Integer, Integer> seen = new HashMap<>(); // значення → індекс

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (seen.containsKey(complement)) {
                return new int[]{seen.get(complement), i};
            }

            seen.put(nums[i], i);
        }

        return new int[]{-1, -1};
    }

    /**
     * Візуалізація роботи алгоритму крок за кроком
     */
    static void twoSumVisualized(int[] nums, int target) {
        HashMap<Integer, Integer> seen = new HashMap<>();
        System.out.println("Шукаємо пару з сумою " + target + " у " + Arrays.toString(nums));
        System.out.println();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            System.out.println("Крок " + (i + 1) + ": nums[" + i + "] = " + nums[i]
                    + ", доповнення = " + target + " - " + nums[i] + " = " + complement);

            if (seen.containsKey(complement)) {
                System.out.println("  ✓ Доповнення " + complement + " є в мапі з індексом "
                        + seen.get(complement) + "!");
                System.out.println("  → Відповідь: [" + seen.get(complement) + ", " + i + "]");
                return;
            } else {
                seen.put(nums[i], i);
                System.out.println("  ✗ Доповнення не знайдено. Додаємо " + nums[i]
                        + " → " + i + " у мапу.");
                System.out.println("  Мапа зараз: " + seen);
            }
            System.out.println();
        }
    }
}
