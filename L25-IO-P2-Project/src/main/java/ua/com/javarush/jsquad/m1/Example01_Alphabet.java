package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;
import java.util.List;

/**
 * Лекція 25: Підсумковий проєкт. Шифр Цезаря.
 * <p>
 * Тема: Алфавіт шифру.
 * <p>
 * Алфавіт у криптографії — це скінченна множина символів,
 * які бере участь у шифруванні. Усе, що не входить до алфавіту,
 * залишається в тексті без змін.
 * <p>
 * За технічним завданням наш алфавіт це:
 * <ul>
 *   <li>Великі англійські літери: 'A'..'Z'</li>
 *   <li>Малі англійські літери:   'a'..'z'</li>
 *   <li>Дозволені знаки:          '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '</li>
 * </ul>
 * <p>
 * Аналогія з життя: уявіть колоду карт. Можна тасувати лише ті карти,
 * що лежать на столі. Карти в коробці — не зачіпаємо. Алфавіт це і є
 * "карти на столі" для нашого шифру.
 * <p>
 * Реальне застосування: будь-який шифр підстановки спирається на
 * заздалегідь визначений алфавіт. Якщо алфавіти у відправника та
 * отримувача різні — розшифрування не спрацює.
 */
public class Example01_Alphabet {

    public static void main(String[] args) {
        // === Блок 1: Будуємо алфавіт як List<Character> ===
        // Сценарiй: збираємо ВСI допустимi символи в один список.
        List<Character> alphabet = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) alphabet.add(c);
        for (char c = 'a'; c <= 'z'; c++) alphabet.add(c);
        for (char c : new char[]{'.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '}) {
            alphabet.add(c);
        }

        System.out.println("=== Алфавiт шифру ===");
        System.out.println("Розмiр алфавiту: " + alphabet.size());
        System.out.println("Алфавiт: " + alphabet);

        System.out.println();

        // === Блок 2: Пошук позицiї символу в алфавiтi ===
        // Сценарiй: щоб зашифрувати символ — треба знати його iндекс.
        char letter = 'C';
        int index = alphabet.indexOf(letter);
        System.out.println("=== Позицiя символу ===");
        System.out.println("Символ '" + letter + "' стоiть на позицii " + index);
        System.out.println("Символ '!' стоiть на позицii " + alphabet.indexOf('!'));

        System.out.println();

        // === Блок 3: Символи поза алфавiтом ===
        // Сценарiй: цифру '5' ми не шифруємо — повертаємо як є.
        char digit = '5';
        int unknown = alphabet.indexOf(digit);
        System.out.println("=== Символи поза алфавiтом ===");
        System.out.println("Цифра '5' у алфавiтi? iндекс = " + unknown + " (-1 означає 'немає')");
        System.out.println("Висновок: '5', цифри та iншi символи залишаємо без змiн.");

        System.out.println();

        // === Блок 4: Великi i малi — це РIЗНI символи ===
        // Сценарiй: 'A' та 'a' мають рiзнi iндекси, бо вони обидва в алфавiтi.
        System.out.println("=== Регiстр має значення ===");
        System.out.println("'A' -> iндекс " + alphabet.indexOf('A'));
        System.out.println("'a' -> iндекс " + alphabet.indexOf('a'));
        System.out.println("Тому пiсля розшифрування великi лiтери залишаються великими,");
        System.out.println("а малi — малими. Це вимога з ТЗ.");
    }
}
