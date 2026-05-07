package ua.com.javarush.jsquad.m1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекція 24: Потоки введення-виведення.
 * <p>
 * Тема: Пiдсумок — мiнi-проєкт "Облiк замовлень iнтернет-магазину".
 * <p>
 * Об'єднуємо все вивчене:
 * <ul>
 *   <li>FileWriter + BufferedWriter — створюємо файл замовлень</li>
 *   <li>FileReader + BufferedReader — читаємо i обробляємо порядково</li>
 *   <li>FileInputStream/FileOutputStream — копiюємо файл як байти</li>
 *   <li>readAllBytes() — бекап у пам'ять</li>
 *   <li>try-with-resources — безпечне закриття</li>
 * </ul>
 * <p>
 * Сценарiй: маємо iнтернет-магазин. Замовлення приходять — пишемо їх у
 * текстовий файл. Потiм читаємо файл, рахуємо суму, шукаємо найбiльше
 * замовлення i створюємо бекап.
 */
public class Example10_Summary {

    public static void main(String[] args) throws IOException {
        Path ordersFile = Files.createTempFile("jsquad-l23-orders-", ".txt");
        Path backupFile = Files.createTempFile("jsquad-l23-backup-", ".txt");

        // === Крок 1: Запис замовлень у файл ===
        // Сценарiй: новi замовлення додаються в журнал.
        System.out.println("=== Крок 1: Запис замовлень ===");
        String[][] orders = {
                {"#001", "Iвано Iваненко",      "Ноутбук Lenovo",     "27500"},
                {"#002", "Петро Петренко",      "Мишка Logitech",     "650"},
                {"#003", "Олена Сидоренко",     "Монiтор Samsung",    "8900"},
                {"#004", "Марiя Шевченко",      "Клавiатура Razer",   "2100"},
                {"#005", "Андрiй Коваленко",    "Веб-камера Logitech", "1850"}
        };

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ordersFile.toFile()))) {
            writer.write("# Замовлення iнтернет-магазину");
            writer.newLine();
            for (String[] order : orders) {
                writer.write(String.join(";", order));
                writer.newLine();
            }
        }
        System.out.println("Записано " + orders.length + " замовлень у файл.");

        System.out.println();

        // === Крок 2: Читання i аналiз ===
        // Сценарiй: бухгалтер пiдраховує суму та шукає найбiльше замовлення.
        System.out.println("=== Крок 2: Аналiз замовлень ===");
        long totalSum = 0;
        int count = 0;
        String biggestClient = "";
        long biggestSum = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ordersFile.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") && !line.contains(";")) continue; // коментар
                String[] parts = line.split(";");
                if (parts.length < 4) continue;

                long price = Long.parseLong(parts[3]);
                totalSum += price;
                count++;

                if (price > biggestSum) {
                    biggestSum = price;
                    biggestClient = parts[1];
                }

                System.out.println("  " + parts[0] + " | " + parts[1] + " | " + parts[2] + " | " + parts[3] + " грн");
            }
        }

        System.out.println();
        System.out.println("Всього замовлень: " + count);
        System.out.println("Загальна сума:    " + totalSum + " грн");
        System.out.println("Найбiльше замовлення: " + biggestClient + " на суму " + biggestSum + " грн");

        System.out.println();

        // === Крок 3: Бекап файлу як байти ===
        // Сценарiй: робимо копiю файлу через байтовi потоки (як копiювали б картинку).
        System.out.println("=== Крок 3: Бекап файлу (байтовi потоки) ===");
        try (InputStream in = new FileInputStream(ordersFile.toFile());
             OutputStream out = new FileOutputStream(backupFile.toFile())) {
            byte[] buf = new byte[1024];
            int read;
            int totalBytes = 0;
            while ((read = in.read(buf)) != -1) {
                out.write(buf, 0, read);
                totalBytes += read;
            }
            System.out.println("Скопiйовано " + totalBytes + " байтiв у бекап.");
        }

        // Перевiрка: оригiнал i бекап однаковi
        byte[] original = Files.readAllBytes(ordersFile);
        byte[] backup = Files.readAllBytes(backupFile);
        System.out.println("Розмiр оригiналу: " + original.length + " байтiв");
        System.out.println("Розмiр бекапу:    " + backup.length + " байтiв");
        System.out.println("Файли iдентичнi:  " + java.util.Arrays.equals(original, backup));

        System.out.println();

        // === Крок 4: Шпаргалка ===
        // Сценарiй: пiдсумкова таблиця для запам'ятовування.
        System.out.println("=== Шпаргалка з потокiв ===");
        System.out.println("Текстовий файл -> читаємо: BufferedReader(new FileReader(file))");
        System.out.println("Текстовий файл -> пишемо:  BufferedWriter(new FileWriter(file))");
        System.out.println("Бiнарний файл  -> читаємо: new FileInputStream(file)");
        System.out.println("Бiнарний файл  -> пишемо:  new FileOutputStream(file)");
        System.out.println("Консоль        -> читаємо: BufferedReader(new InputStreamReader(System.in))");
        System.out.println("Кодування      -> завжди явно вказуємо StandardCharsets.UTF_8");
        System.out.println("Закриття       -> завжди try-with-resources");

        Files.deleteIfExists(ordersFile);
        Files.deleteIfExists(backupFile);
    }
}
