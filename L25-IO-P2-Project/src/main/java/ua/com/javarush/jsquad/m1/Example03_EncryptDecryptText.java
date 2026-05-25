package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;
import java.util.List;

/**
 * Лекція 25: Підсумковий проєкт. Шифр Цезаря.
 * <p>
 * Тема: Шифрування i дешифрування цiлого рядка.
 * <p>
 * Якщо ми вмiємо зсувати один символ — рядок шифрується тривiально:
 * проходимо char-за-char i зсуваємо кожен. Iмпорт CaesarCipher
 * у реальному проєктi виглядатиме як окремий клас iз методами
 * encrypt(text, key) i decrypt(text, key).
 * <p>
 * Аналогія з життя: уявiть, що ви перекладаєте речення з англiйської
 * слово за словом. Загальна логiка — однакова для кожного слова,
 * просто застосовується багато разiв.
 * <p>
 * Реальне застосування: пiсля цього кроку готовий "движок" шифру.
 * Залишається тiльки прикрутити до нього читання/запис файлiв.
 */
public class Example03_EncryptDecryptText {

    private static final List<Character> ALPHABET = buildAlphabet();

    public static void main(String[] args) {
        // === Блок 1: Шифруємо коротку фразу ===
        // Сценарiй: класичний приклад з лекцiї — ключ 3.
        String message = "Hello, World!";
        String encrypted = encrypt(message, 3);
        System.out.println("=== Шифрування ===");
        System.out.println("Оригiнал:    \"" + message + "\"");
        System.out.println("Зашифровано: \"" + encrypted + "\"");

        System.out.println();

        // === Блок 2: Дешифрування поверне оригiнал 1-в-1 ===
        // Сценарiй: ТЗ вимагає, щоб пiсля decrypt текст не вiдрiзнявся вiд вихiдного.
        String decrypted = decrypt(encrypted, 3);
        System.out.println("=== Дешифрування ===");
        System.out.println("Зашифровано: \"" + encrypted + "\"");
        System.out.println("Розшифр.:    \"" + decrypted + "\"");
        System.out.println("Однаково?    " + message.equals(decrypted));

        System.out.println();

        // === Блок 3: Великi/малi лiтери зберiгаються ===
        // Сценарiй: 'H' лишається великою, 'e' — малою.
        String mixed = "Java Is Cool!";
        System.out.println("=== Регiстр зберiгається ===");
        System.out.println("Оригiнал:    \"" + mixed + "\"");
        System.out.println("Зашифровано: \"" + encrypt(mixed, 5) + "\"");

        System.out.println();

        // === Блок 4: Символи поза алфавiтом — без змiн ===
        // Сценарiй: цифри й перенесення рядка не торкаємось.
        String withDigits = "Code 2024\nNew line!";
        System.out.println("=== Цифри й перенесення рядка ===");
        System.out.println("Оригiнал:    \"" + withDigits.replace("\n", "\\n") + "\"");
        System.out.println("Зашифровано: \"" + encrypt(withDigits, 10).replace("\n", "\\n") + "\"");

        System.out.println();

        // === Блок 5: Великий ключ — без помилок ===
        // Сценарiй: ключ 100 завдяки % size працює як ключ (100 % size).
        System.out.println("=== Великий ключ ===");
        System.out.println("Зашифровано з ключем 100: \"" + encrypt(message, 100) + "\"");
        System.out.println("Розшифровано з ключем 100: \"" + decrypt(encrypt(message, 100), 100) + "\"");
    }

    private static String encrypt(String text, int key) {
        StringBuilder out = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            out.append(shift(text.charAt(i), key));
        }
        return out.toString();
    }

    private static String decrypt(String text, int key) {
        return encrypt(text, -key); // -key зi знаком "-" — це той самий зсув у зворотний бiк
    }

    private static char shift(char ch, int key) {
        int index = ALPHABET.indexOf(ch);
        if (index == -1) return ch;
        int size = ALPHABET.size();
        int newIndex = ((index + key) % size + size) % size; // подвiйний % size — захист вiд вiд'ємних
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
