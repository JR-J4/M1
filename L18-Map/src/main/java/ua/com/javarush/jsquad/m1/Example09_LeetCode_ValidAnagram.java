package ua.com.javarush.jsquad.m1;

import java.util.HashMap;
import java.util.Map;

/**
 * Лекція 18: Колекції Map — LeetCode #242 "Valid Anagram" (Easy)
 *
 * Задача: дано два рядки s і t. Повернути true, якщо t є анаграмою s.
 * Анаграма — слово, утворене перестановкою літер іншого слова
 * (використовуючи всі оригінальні літери рівно один раз).
 *
 * Приклад:
 *   s = "anagram", t = "nagaram"  →  true
 *   s = "rat",     t = "car"      →  false
 *
 * Ключова ідея з HashMap:
 *   Підрахувати частоту кожної літери в обох рядках за допомогою HashMap.
 *   Якщо частоти збігаються — це анаграма.
 *
 * Складність:
 *   Сортування обох рядків і порівняння: O(n log n)
 *   З HashMap (підрахунок частот):      O(n)
 *
 * Аналогія з життя: уявіть дві коробки з літерами Scrabble. Щоб перевірити,
 * чи однакові набори, не треба складати слова — просто порахуйте кількість
 * кожної літери в обох коробках. Якщо кількості збігаються — набори однакові.
 *
 * Реальне застосування: перевірка анаграм у словниках, пошук перестановок,
 * виявлення плагіату, криптографічні перевірки.
 */
public class Example09_LeetCode_ValidAnagram {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Базові приклади
        // ============================================================
        System.out.println("=== Блок 1: Базові приклади ===");

        System.out.println("\"anagram\" і \"nagaram\": " + isAnagram("anagram", "nagaram")); // true
        System.out.println("\"rat\" і \"car\":         " + isAnagram("rat", "car"));         // false
        System.out.println("\"listen\" і \"silent\":   " + isAnagram("listen", "silent"));   // true
        System.out.println("\"hello\" і \"world\":     " + isAnagram("hello", "world"));     // false
        System.out.println("\"кіт\" і \"тік\":         " + isAnagram("кіт", "тік"));         // true
        System.out.println();

        // ============================================================
        //   Блок 2: Покрокова візуалізація
        // ============================================================
        System.out.println("=== Блок 2: Покрокова візуалізація ===");

        isAnagramVisualized("listen", "silent");
        System.out.println();

        isAnagramVisualized("rat", "car");
        System.out.println();

        // ============================================================
        //   Блок 3: Крайові випадки
        // ============================================================
        System.out.println("=== Блок 3: Крайові випадки ===");

        System.out.println("Різна довжина \"ab\" і \"abc\": " + isAnagram("ab", "abc")); // false
        System.out.println("Порожні рядки \"\" і \"\":       " + isAnagram("", ""));     // true
        System.out.println("Однакові \"java\" і \"java\":    " + isAnagram("java", "java")); // true
        System.out.println("Повтори \"aab\" і \"aba\":       " + isAnagram("aab", "aba")); // true
        System.out.println("Повтори \"aab\" і \"aaa\":       " + isAnagram("aab", "aaa")); // false
    }

    /**
     * Рішення з HashMap: підрахунок частот — O(n)
     *
     * Алгоритм:
     * 1. Якщо довжини різні — одразу false
     * 2. Рахуємо частоту кожного символу в s (інкремент)
     * 3. Для кожного символу в t декрементуємо лічильник
     * 4. Якщо будь-який лічильник < 0 — не анаграма
     */
    static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        HashMap<Character, Integer> charCount = new HashMap<>();

        // Рахуємо частоту символів у першому рядку
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        // Зменшуємо лічильники для символів другого рядка
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            int count = charCount.getOrDefault(c, 0);
            if (count == 0) {
                return false; // символ з t відсутній в s або вже вичерпаний
            }
            charCount.put(c, count - 1);
        }

        return true;
    }

    /**
     * Візуалізація роботи алгоритму
     */
    static void isAnagramVisualized(String s, String t) {
        System.out.println("Перевіряємо: \"" + s + "\" і \"" + t + "\"");

        if (s.length() != t.length()) {
            System.out.println("  Різна довжина → false");
            return;
        }

        HashMap<Character, Integer> charCount = new HashMap<>();

        // Крок 1: підрахунок частот s
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        System.out.println("  Частоти літер у \"" + s + "\": " + charCount);

        // Крок 2: віднімання частот t
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            int count = charCount.getOrDefault(c, 0);
            if (count == 0) {
                System.out.println("  Літера '" + c + "' — зайва! → false");
                return;
            }
            charCount.put(c, count - 1);
        }

        // Перевірка фінального стану
        System.out.println("  Частоти після віднімання: " + charCount);

        boolean allZero = true;
        for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
            if (entry.getValue() != 0) {
                allZero = false;
                break;
            }
        }
        System.out.println("  Всі лічильники = 0? " + allZero + " → " + (allZero ? "АНАГРАМА!" : "НЕ анаграма"));
    }
}
