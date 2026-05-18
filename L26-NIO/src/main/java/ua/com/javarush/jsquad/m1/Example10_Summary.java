package ua.com.javarush.jsquad.m1;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Лекція 26: Потоки введення-виведення. Частина 3 (NIO).
 * <p>
 * Тема: Пiдсумок — система архiвiв звiтiв на базi NIO.
 * <p>
 * Сценарiй: у бухгалтерiї треба автоматизувати роботу зi звiтами:
 * <ol>
 *   <li>Створити структуру папок: {@code archive/2026/05/}</li>
 *   <li>Записати щоденнi звiти у потрiбну папку</li>
 *   <li>Прочитати всi звiти за день i вивести зведення</li>
 *   <li>Перемiстити старий звiт в {@code archive-old/}</li>
 *   <li>Видалити всi тимчасовi файли</li>
 * </ol>
 * <p>
 * Використовуються майже всi теми лекцiї: Path, Path.of, resolve,
 * Files.createDirectories, Files.writeString, newDirectoryStream,
 * Files.move, Files.delete, метаiнформацiя.
 */
public class Example10_Summary {

    public static void main(String[] args) throws IOException {
        // === Крок 1: створюємо коренi i структуру архiву ===
        // Сценарiй: розгортаємо структуру папок для архiву звiтiв.
        System.out.println("=== Крок 1: створення структури ===");
        Path root = Files.createTempDirectory("accountant-");
        LocalDate today = LocalDate.now();

        Path archive = root.resolve("archive")
                .resolve(String.valueOf(today.getYear()))
                .resolve(String.format("%02d", today.getMonthValue()));
        Files.createDirectories(archive);
        System.out.println("Папка архiву: " + archive);

        Path archiveOld = root.resolve("archive-old");
        Files.createDirectory(archiveOld);

        System.out.println();

        // === Крок 2: записуємо щоденнi звiти ===
        // Сценарiй: 3 звiти за рiзнi години того ж дня.
        System.out.println("=== Крок 2: запис звiтiв ===");
        String[] reports = {
                "08:00 — Каса: 1250 грн",
                "14:00 — Каса: 4870 грн",
                "20:00 — Каса: 7320 грн"
        };
        for (int i = 0; i < reports.length; i++) {
            Path r = archive.resolve("report-" + (i + 1) + ".txt");
            Files.writeString(r, reports[i]);
            System.out.println("Збережено: " + r.getFileName() + "  (" + Files.size(r) + " байт)");
        }

        System.out.println();

        // === Крок 3: збираємо всi звiти за день ===
        // Сценарiй: пiд кiнець дня — зведення.
        System.out.println("=== Крок 3: зведення за день ===");
        List<String> allLines = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(archive, "report-*.txt")) {
            for (Path file : stream) {
                allLines.add(Files.readString(file));
            }
        }
        System.out.println("Звiти за " + today + ":");
        for (String line : allLines) {
            System.out.println("  • " + line);
        }

        System.out.println();

        // === Крок 4: архiвуємо найстарiший звiт ===
        // Сценарiй: переносимо перший звiт у архiв старих.
        System.out.println("=== Крок 4: move до archive-old ===");
        Path first = archive.resolve("report-1.txt");
        Path moved = archiveOld.resolve("report-old.txt");
        Files.move(first, moved);
        System.out.println("Перемiщено: " + first.getFileName() + " -> " + moved);
        System.out.println("Старий iснує? " + Files.exists(first));
        System.out.println("Новий iснує? " + Files.exists(moved));

        System.out.println();

        // === Крок 5: пiдрахунок i звiт ===
        // Сценарiй: дивимось, що залишилось в активному архiвi.
        System.out.println("=== Крок 5: пiсля архiвацiї ===");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(archive)) {
            int count = 0;
            long total = 0;
            for (Path file : stream) {
                count++;
                total += Files.size(file);
            }
            System.out.println("В активному архiвi файлiв: " + count + ", всього байт: " + total);
        }

        System.out.println();

        // === Крок 6: прибираємо за собою ===
        // Сценарiй: рекурсивне видалення (через walk не використовуємо —
        // показуємо ручне видалення з вкладеної структури).
        System.out.println("=== Крок 6: cleanup ===");
        deleteRecursive(root);
        System.out.println("Все прибрано: " + !Files.exists(root));
    }

    /**
     * Рекурсивне видалення директорiї.
     * Спочатку видаляє всi файли всерединi, потiм саму папку.
     */
    private static void deleteRecursive(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                for (Path child : stream) {
                    deleteRecursive(child);
                }
            }
        }
        Files.delete(path);
    }
}
