package ua.com.javarush.jsquad.m1;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Лекція 24: Потоки введення-виведення.
 * <p>
 * Тема: Клас InputStream — читання байтiв.
 * <p>
 * InputStream — абстрактний клас, базовий для всiх вхiдних байтових потокiв.
 * Основнi методи:
 * <ul>
 *   <li>{@code int read()} — читає 1 байт (повертає -1 при кiнцi потоку)</li>
 *   <li>{@code int read(byte[] buffer)} — читає масив байтiв</li>
 *   <li>{@code byte[] readAllBytes()} — читає всi байти одразу</li>
 *   <li>{@code long skip(long n)} — пропускає n байтiв</li>
 *   <li>{@code int available()} — скiльки байтiв ще залишилось</li>
 *   <li>{@code void close()} — закриває потiк</li>
 * </ul>
 * <p>
 * Аналогія: прочитати книжку. Можна по одному символу (read), сторiнками
 * (read у буфер), або одразу всю (readAllBytes).
 * <p>
 * Реальне застосування: завантаження картинок, парсинг бiнарних файлiв,
 * читання конфiгурацiй, обробка мережевого трафiку.
 */
public class Example02_InputStream {

    public static void main(String[] args) throws IOException {
        // === Блок 1: Читаємо по одному байту з масиву пам'яті ===
        // Сценарiй: маємо в пам'ятi послiдовнiсть байтiв (наприклад, "ABC")
        // i хочемо прочитати їх по одному.
        System.out.println("=== read() — по одному байту ===");
        InputStream memoryStream = new ByteArrayInputStream(new byte[]{'A', 'B', 'C'});
        int byteValue;
        while ((byteValue = memoryStream.read()) != -1) {
            System.out.println("Прочитано байт: " + byteValue + " (символ '" + (char) byteValue + "')");
        }
        memoryStream.close();

        System.out.println();

        // === Блок 2: Читання у буфер (цiлий масив за раз) ===
        // Сценарiй: маємо багато даних, хочемо прочитати чанком — швидше нiж по байту.
        System.out.println("=== read(byte[] buffer) — у буфер ===");
        byte[] data = "Hello, JavaRush!".getBytes();
        InputStream stream = new ByteArrayInputStream(data);
        byte[] buffer = new byte[5];
        int bytesRead = stream.read(buffer);
        System.out.println("Прочитано " + bytesRead + " байтiв у буфер");
        System.out.println("Вмiст буфера: " + new String(buffer, 0, bytesRead));
        stream.close();

        System.out.println();

        // === Блок 3: readAllBytes() — все одразу ===
        // Сценарiй: невеликий файл, хочемо отримати весь вмiст одним викликом.
        System.out.println("=== readAllBytes() — весь потiк ===");
        Path tempFile = Files.createTempFile("jsquad-l23-", ".txt");
        Files.writeString(tempFile, "Привiт зi студiї JavaRush!");

        try (  InputStream fileStream = new FileInputStream(tempFile.toFile())  ) {
            byte[] all = fileStream.readAllBytes();
            System.out.println("Розмiр: " + all.length + " байтiв");
            System.out.println("Текст: " + new String(all));
        }

        System.out.println();

        // === Блок 4: skip() та available() ===
        // Сценарiй: пропускаємо заголовок файлу i перевiряємо скiльки лишилось.
        System.out.println("=== skip() та available() ===");
        try (InputStream s = new FileInputStream(tempFile.toFile())) {
            System.out.println("Доступно до читання: " + s.available() + " байтiв");
            long skipped = s.skip(7);
            System.out.println("Пропущено: " + skipped + " байтiв");
            System.out.println("Залишилось: " + s.available() + " байтiв");
            byte[] rest = s.readAllBytes();
            System.out.println("Решта тексту: " + new String(rest));
        }

        // === Блок 5: try-with-resources автоматично закриває потiк ===
        // Сценарiй: правильна робота з ресурсами — не забути close().
        System.out.println("\nПорада: завжди використовуйте try-with-resources!");
        System.out.println("Це гарантує, що close() викличеться навiть при винятку.");

        Files.deleteIfExists(tempFile);
    }
}
