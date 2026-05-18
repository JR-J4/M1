package ua.com.javarush.jsquad.m1;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекція 26: Потоки введення-виведення. Частина 3 (NIO).
 * <p>
 * Тема: Files.newDirectoryStream — перебiр вмiсту директорiї.
 * <p>
 * {@code newDirectoryStream(Path)} повертає {@code DirectoryStream<Path>}
 * — спецiальний об'єкт-iтератор, через який можна пройтись по файлах
 * i пiддиректорiях.
 * <p>
 * ⚠ DirectoryStream — це ресурс! Завжди використовуйте try-with-resources.
 * <p>
 * Також є перевантажена форма з фiльтром (glob-шаблоном):
 * {@code newDirectoryStream(path, "*.txt")}.
 * <p>
 * Аналогiя: вiдкрити папку i переглянути її вмiст — як заглянути в шухляду
 * i дiстати все по черзi.
 * <p>
 * Реальне застосування: обхiд папки з логами, пошук файлiв за маскою,
 * показ списку файлiв у файловому менеджерi.
 */
public class Example07_DirectoryStream {

    public static void main(String[] args) throws IOException {
        // Готуємо тимчасову директорiю з рiзними файлами
        Path dir = Files.createTempDirectory("jsquad-l26-dir-");
        Files.createFile(dir.resolve("report.txt"));
        Files.createFile(dir.resolve("photo.jpg"));
        Files.createFile(dir.resolve("notes.txt"));
        Files.createFile(dir.resolve("data.csv"));
        Files.createDirectory(dir.resolve("subfolder"));

        // === Блок 1: перебiр усього вмiсту директорiї ===
        // Сценарiй: показати всi файли в папцi (як ls / dir).
        System.out.println("=== Усi файли в " + dir.getFileName() + " ===");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                String type = Files.isDirectory(entry) ? "[DIR] " : "[FILE]";
                System.out.println(type + " " + entry.getFileName());
            }
        }

        System.out.println();

        // === Блок 2: фiльтр за glob-шаблоном ===
        // Сценарiй: показати тiльки .txt файли.
        System.out.println("=== Тiльки *.txt ===");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt")) {
            for (Path entry : stream) {
                System.out.println("  " + entry.getFileName());
            }
        }

        System.out.println();

        // === Блок 3: фiльтр з кiлькох розширень ===
        // Сценарiй: усi медiа-файли (jpg, png) — використовуємо glob.
        System.out.println("=== Усi *.{jpg,png,csv} ===");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{jpg,png,csv}")) {
            for (Path entry : stream) {
                System.out.println("  " + entry.getFileName());
            }
        }

        System.out.println();

        // === Блок 4: фiльтр через лямбду ===
        // Сценарiй: тiльки файли бiльше за певний розмiр (фiльтр на Java-кодi).
        System.out.println("=== Фiльтр через лямбду (тiльки файли, не директорiї) ===");
        DirectoryStream.Filter<Path> onlyFiles = Files::isRegularFile;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, onlyFiles)) {
            for (Path entry : stream) {
                System.out.println("  " + entry.getFileName());
            }
        }

        System.out.println();

        // === Блок 5: ⚠ newDirectoryStream не заходить у пiддиректорiї ===
        // Сценарiй: для рекурсивного обходу — Files.walk() або walkFileTree.
        System.out.println("=== Тiльки 1 рiвень! ===");
        System.out.println("newDirectoryStream не заходить у subfolder.");
        System.out.println("Для рекурсiї використовуйте Files.walk() — стрiм усiх файлiв нижче.");

        // прибираємо
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                Files.delete(entry);
            }
        }
        Files.delete(dir);
    }
}
