package ua.com.javarush.jsquad.m1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекцiя 28: Git.
 * <p>
 * Тема: Створення локального репозиторiю (git init) i перегляд стану (git status).
 * <p>
 * Цей приклад НЕ пише код для студента — вiн ЗАПУСКАЄ справжнi git-команди
 * у тимчасовiй папцi, щоб ви побачили реальний вивiд Git своїми очима.
 * <p>
 * Аналогiя: git init — це як вiдкрити нову порожню записну книжку для проєкту.
 * Поки що там немає жодного запису, але книжка вже готова приймати записи.
 * git status — це швидкий погляд на стiл: 'що в мене зараз вiдкрито?'.
 * <p>
 * Реальне застосування: git init виконується ОДИН раз — на самому початку
 * нового проєкту. git status навпаки — найчастiше використовувана команда:
 * розробники запускають її буквально кожну хвилину.
 */
public class Example04_GitInitAndStatus {

    public static void main(String[] args) throws Exception {
        // === Блок 1: теорiя про git init ===
        // Сценарiй: що робить ця команда i коли її виконувати.
        System.out.println("=== git init ===");
        System.out.println("Створює прихований каталог .git у поточнiй папцi.");
        System.out.println("Саме в .git Git зберiгає всю iсторiю: комiти, гiлки, налаштування.");
        System.out.println("Виконується ОДИН раз для нового проєкту.");

        System.out.println();

        // === Блок 2: створюємо тимчасову папку i робимо init ===
        // Сценарiй: щоб не псувати реальний проєкт, працюємо у тимчасовiй папцi.
        Path tempDir = Files.createTempDirectory("git-demo-");
        System.out.println("=== Створюємо тимчасову папку для демо ===");
        System.out.println("Папка: " + tempDir);

        System.out.println();

        System.out.println("=== Виконуємо 'git init' у цiй папцi ===");
        runIn(tempDir, "git", "init");

        System.out.println();

        // === Блок 3: перевiряємо, що .git створено ===
        // Сценарiй: пiсля init у папцi з'являється прихована .git.
        Path gitDir = tempDir.resolve(".git");
        System.out.println("=== Чи з'явилася папка .git? ===");
        System.out.println("Iснує .git: " + Files.exists(gitDir));
        System.out.println("(саме тут Git зберiгає всю iсторiю проєкту)");

        System.out.println();

        // === Блок 4: git status у порожньому репо ===
        // Сценарiй: щойно створили репо, ще нiчого не додавали — що скаже git?
        System.out.println("=== 'git status' у щойно створеному репо ===");
        runIn(tempDir, "git", "status");

        System.out.println();

        // === Блок 5: створюємо файл i знов дивимось status ===
        // Сценарiй: додаємо файл — побачимо його у списку untracked.
        Files.writeString(tempDir.resolve("hello.txt"), "Привiт, Git!");
        System.out.println("=== Створили файл hello.txt. Тепер 'git status' ===");
        runIn(tempDir, "git", "status");

        System.out.println();

        // === Блок 6: висновки ===
        // Сценарiй: пiдсумовуємо, на що звернути увагу.
        System.out.println("=== Зверни увагу ===");
        System.out.println("1) hello.txt у списку 'Untracked files' — Git його ще НЕ вiдстежує.");
        System.out.println("2) Git одразу пiдказує наступний крок: 'use git add ...'");
        System.out.println("3) Гiлка за замовчуванням — main (у старих версiях Git: master).");

        // Прибираємо за собою (необов'язково, але чисто)
        deleteRecursively(tempDir);
    }

    /** Запускає команду в зазначенiй папцi i друкує її вивiд. */
    private static void runIn(Path dir, String... command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(dir.toFile());
            pb.redirectErrorStream(true);
            Process process = pb.start();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("  " + line);
                }
            }
            process.waitFor();
        } catch (Exception e) {
            System.out.println("  (помилка: " + e.getMessage() + ")");
        }
    }

    /** Прибирає тимчасову папку. */
    private static void deleteRecursively(Path path) throws Exception {
        if (!Files.exists(path)) return;
        Files.walk(path)
                .sorted((a, b) -> b.getNameCount() - a.getNameCount())
                .forEach(p -> {
                    try { Files.deleteIfExists(p); } catch (Exception ignored) {}
                });
    }
}
