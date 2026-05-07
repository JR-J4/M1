package ua.com.javarush.jsquad.m1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекція 24: Потоки введення-виведення.
 * <p>
 * Тема: Клас BufferedReader — буферизоване читання символiв.
 * <p>
 * BufferedReader — нащадок Reader. Як джерело приймає iнший Reader.
 * Особливостi:
 * <ul>
 *   <li>Не перетворює нiчого — просто буферизує</li>
 *   <li>Читає великими шматками одразу (швидко!)</li>
 *   <li>Має зручний метод {@code String readLine()} — читає цiлий рядок</li>
 * </ul>
 * <p>
 * Аналогія: купуєте воду в магазинi. FileReader — як ходити по 1 склянцi
 * щоразу. BufferedReader — береш одразу пляшку 5 лiтрiв i п'єш потроху
 * вдома. Менше походiв до магазину = швидше.
 * <p>
 * Реальне застосування: читання великих лог-файлiв порядково, парсинг
 * CSV/конфiгiв, обробка текстових файлiв будь-якого розмiру.
 */
public class Example07_BufferedReader {

    public static void main(String[] args) throws IOException {
        // === Блок 1: readLine() — читання порядково ===
        // Сценарiй: читаємо вiршi (кожен рядок — окрема одиниця).
        System.out.println("=== readLine() ===");
        String poem = "Тече вода iз-за гаю\nТа попiд горою.\nХлюпощуться качаточка\nПомiж осокою.";
        BufferedReader br = new BufferedReader(new StringReader(poem));

        String line;
        int lineNum = 1;
        while ((line = br.readLine()) != null) {
            System.out.println(lineNum + ": " + line);
            lineNum++;
        }
        br.close();

        System.out.println();

        // === Блок 2: BufferedReader працює iз файлом ===
        // Сценарiй: читаємо лог-файл порядково.
        System.out.println("=== BufferedReader + FileReader ===");
        Path logFile = Files.createTempFile("jsquad-l23-log-", ".txt");
        Files.writeString(logFile,
                "[INFO] Програма запущена\n" +
                "[INFO] Користувач увiйшов\n" +
                "[ERROR] Помилка з'єднання з БД\n" +
                "[INFO] Спроба перепiдключення\n" +
                "[INFO] З'єднання вiдновлено");

        try (BufferedReader logReader = new BufferedReader(new FileReader(logFile.toFile()))) {
            String entry;
            int errors = 0;
            while ((entry = logReader.readLine()) != null) {
                if (entry.contains("[ERROR]")) {
                    System.out.println("Знайдено помилку: " + entry);
                    errors++;
                }
            }
            System.out.println("Всього помилок у логах: " + errors);
        }

        System.out.println();

        // === Блок 3: Чому BufferedReader швидше ===
        // Сценарiй: пояснення механiзму буферизацiї.
        System.out.println("=== Чому буферизацiя пришвидшує? ===");
        System.out.println("Без буфера: кожен read() = окремий запит до диска.");
        System.out.println("З буфером: один read() = читає 8 КБ за раз у пам'ять.");
        System.out.println("Наступнi read() беруть данi з пам'ятi — у тисячi разiв швидше!");

        System.out.println();

        // === Блок 4: Stream API — lines() ===
        // Сценарiй: сучасний спосiб обробки рядкiв через Stream API.
        System.out.println("=== lines() — Stream API ===");
        try (BufferedReader br2 = new BufferedReader(new FileReader(logFile.toFile()))) {
            long total = br2.lines().count();
            System.out.println("Всього рядкiв у файлi: " + total);
        }

        try (BufferedReader br3 = new BufferedReader(new FileReader(logFile.toFile()))) {
            System.out.println("Тiльки INFO записи:");
            br3.lines()
               .filter(l -> l.contains("[INFO]"))
               .forEach(l -> System.out.println("  -> " + l));
        }

        System.out.println();

        // === Блок 5: Ланцюжок потокiв — фiнальна картина ===
        // Сценарiй: повний ланцюжок до файлу.
        System.out.println("=== Повний ланцюжок ===");
        System.out.println("File -> FileInputStream -> InputStreamReader -> BufferedReader -> код");
        System.out.println("або коротше:");
        System.out.println("File -> FileReader -> BufferedReader -> код");
        System.out.println("(FileReader = FileInputStream + InputStreamReader всередині)");

        Files.deleteIfExists(logFile);
    }
}
