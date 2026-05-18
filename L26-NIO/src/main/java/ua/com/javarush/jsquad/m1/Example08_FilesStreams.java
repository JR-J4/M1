package ua.com.javarush.jsquad.m1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекція 26: Потоки введення-виведення. Частина 3 (NIO).
 * <p>
 * Тема: Files.newInputStream / newOutputStream / newBufferedReader.
 * <p>
 * Починаючи з Java 5, класи {@code FileInputStream} i {@code FileOutputStream}
 * вважаються застарiлими. Їх мiнус: створення об'єкта одразу вiдкриває файл
 * на диску, можливi помилки створення.
 * <p>
 * Рекомендований шлях — фабричнi методи класу Files:
 * <ul>
 *   <li>{@code Files.newInputStream(path)} — замiсть {@code new FileInputStream}</li>
 *   <li>{@code Files.newOutputStream(path)} — замiсть {@code new FileOutputStream}</li>
 *   <li>{@code Files.newBufferedReader(path)} — вже з буферизацiєю та UTF-8</li>
 *   <li>{@code Files.newBufferedWriter(path)} — те саме для запису</li>
 * </ul>
 * <p>
 * Аналогiя: FileInputStream — самозбiрна меблева полиця з 1990-х. Файли
 * newInputStream — IKEA з докладною iнструкцiєю. Те саме, але безпечнiше
 * i зручнiше.
 * <p>
 * Реальне застосування: будь-яке стрiмове читання/запис, де ранiше
 * використовували FileInputStream/FileOutputStream.
 */
public class Example08_FilesStreams {

    public static void main(String[] args) throws IOException {
        // === Блок 1: Files.newOutputStream — запис байтiв ===
        // Сценарiй: записуємо файл побайтово (наприклад, картинку з мережi).
        System.out.println("=== newOutputStream ===");
        Path bin = Files.createTempFile("img-", ".dat");
        try (OutputStream out = Files.newOutputStream(bin)) {
            out.write(new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF}); // JPEG-magic
            out.write("Привiт".getBytes(StandardCharsets.UTF_8));
        }
        System.out.println("Записано " + Files.size(bin) + " байт у " + bin.getFileName());

        System.out.println();

        // === Блок 2: Files.newInputStream — читання байтiв ===
        // Сценарiй: читаємо файл порцiями (наприклад, для розрахунку хешу).
        System.out.println("=== newInputStream ===");
        try (InputStream in = Files.newInputStream(bin)) {
            byte[] buf = new byte[16];
            int read = in.read(buf);
            System.out.print("Прочитано " + read + " байт: ");
            for (int i = 0; i < read; i++) {
                System.out.print(String.format("%02X ", buf[i]));
            }
            System.out.println();
        }

        System.out.println();

        // === Блок 3: Files.newBufferedReader — читання рядкiв ===
        // Сценарiй: великий лог-файл — читаємо порядково, без завантаження в RAM.
        System.out.println("=== newBufferedReader ===");
        Path log = Files.createTempFile("server-", ".log");
        Files.writeString(log,
                "[INFO] Сервер стартував\n" +
                "[INFO] Пiдключено клiєнт #1\n" +
                "[ERROR] Розрив зв'язку\n" +
                "[INFO] Клiєнт перепiдключився");

        try (BufferedReader reader = Files.newBufferedReader(log)) {
            String line;
            int n = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println(n + ": " + line);
                n++;
            }
        }

        System.out.println();

        // === Блок 4: чому Files.newXxxStream замiсть new FileXxxStream ===
        System.out.println("=== Чому новий API кращий ===");
        System.out.println("1. Працює з Path (сучасний API), а не File.");
        System.out.println("2. Бiльше опцiй (StandardOpenOption: APPEND, CREATE, TRUNCATE...).");
        System.out.println("3. newBufferedReader одразу буферизує — менше коду.");
        System.out.println("4. За замовчуванням UTF-8, без сюрпризiв iз кодуванням.");

        System.out.println();

        // === Блок 5: StandardOpenOption — дозапис ===
        // Сценарiй: додаємо запис у кiнець лог-файлу (замiсть перезапису).
        System.out.println("=== APPEND через newOutputStream ===");
        try (OutputStream append = Files.newOutputStream(log, java.nio.file.StandardOpenOption.APPEND)) {
            append.write("\n[INFO] Завершення роботи".getBytes(StandardCharsets.UTF_8));
        }
        System.out.println("Файл пiсля APPEND:");
        System.out.println(Files.readString(log));

        // прибираємо
        Files.delete(bin);
        Files.delete(log);
    }
}
