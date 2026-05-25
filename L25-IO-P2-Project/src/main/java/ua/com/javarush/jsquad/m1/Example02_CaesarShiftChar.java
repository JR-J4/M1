package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;
import java.util.List;

/**
 * Лекція 25: Підсумковий проєкт. Шифр Цезаря.
 * <p>
 * Тема: Циклічний зсув одного символу.
 * <p>
 * Шифр Цезаря — це зсув символу на N позицій уперед в алфавіті.
 * Якщо ми "виходимо за край" — повертаємось на початок алфавіту.
 * Це і є циклічний зсув.
 * <p>
 * Формула шифрування:    newIndex = (index + key) % size
 * Формула дешифрування:  newIndex = (index - key % size + size) % size
 * <p>
 * Аналогія з життя: уявіть круглий годинник. Якщо зараз 23:00 і
 * додати 3 години — буде 02:00, а не 26:00. Стрілка повернулась
 * на початок. Так само літера 'Z' після зсуву на 1 стає 'A'.
 * <p>
 * Реальне застосування: будь-який шифр підстановки використовує
 * операцію `% N` для повернення індексу в межі алфавіту.
 */
public class Example02_CaesarShiftChar {

    private static final List<Character> ALPHABET = buildAlphabet();

    public static void main(String[] args) {
        // === Блок 1: Зсув простого символу ===
        // Сценарiй: зашифруємо 'A' з ключем 3 -> має бути 'D'.
        System.out.println("=== Зсув символу ===");
        System.out.println("'A' + 3 = '" + shift('A', 3) + "'");
        System.out.println("'B' + 3 = '" + shift('B', 3) + "'");
        System.out.println("'C' + 3 = '" + shift('C', 3) + "'");

        System.out.println();

        // === Блок 2: Циклiчний перехiд через край ===
        // Сценарiй: 'Z' + 1 має повернутись на 'a', а не вилетiти за алфавiт.
        System.out.println("=== Циклiчний перехiд ===");
        System.out.println("'Z' + 1 = '" + shift('Z', 1) + "' (повернулися на початок)");
        System.out.println("'?' + 1 = '" + shift('?', 1) + "' (останнiй символ алфавiту)");

        System.out.println();

        // === Блок 3: Дешифрування — це зсув у зворотний бiк ===
        // Сценарiй: якщо зашифрували +3, то розшифрувати = -3.
        char encrypted = shift('A', 3);
        char decrypted = unshift(encrypted, 3);
        System.out.println("=== Дешифрування ===");
        System.out.println("Зашифрували 'A' з ключем 3 -> '" + encrypted + "'");
        System.out.println("Розшифрували '" + encrypted + "' з ключем 3 -> '" + decrypted + "'");

        System.out.println();

        // === Блок 4: Великий ключ — % size рятує нас ===
        // Сценарiй: ключ 1000 не повинен ламати програму.
        System.out.println("=== Великий ключ ===");
        System.out.println("'A' + 1000 = '" + shift('A', 1000) + "' (без помилки, бо беремо % size)");

        System.out.println();

        // === Блок 5: Символ поза алфавiтом залишається сам собою ===
        // Сценарiй: цифру '5' ми НЕ шифруємо.
        System.out.println("=== Символ поза алфавiтом ===");
        System.out.println("'5' + 3 = '" + shift('5', 3) + "' (залишився '5')");
        System.out.println("'\\n' + 3 = '" + shift('\n', 3) + "' (перенесення рядка не чiпаємо)");
    }

    private static char shift(char ch, int key) {
        int index = ALPHABET.indexOf(ch);
        if (index == -1) return ch;
        int size = ALPHABET.size();
        int newIndex = (index + key) % size;
        return ALPHABET.get(newIndex);
    }

    private static char unshift(char ch, int key) {
        int index = ALPHABET.indexOf(ch);
        if (index == -1) return ch;
        int size = ALPHABET.size();
        // Додаємо size, щоб уникнути вiд'ємного результату % в Java.
        int newIndex = (index - key % size + size) % size;
        return ALPHABET.get(newIndex);
    }

    private static List<Character> buildAlphabet() {
        List<Character> a = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) a.add(c);
        for (char c = 'a'; c <= 'z'; c++) a.add(c);
        for (char c : new char[]{'.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '}) a.add(c);
        return a;
    }
}
