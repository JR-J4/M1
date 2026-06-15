package ua.com.javarush.jsquad.m1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекцiя 28: Git.
 * <p>
 * Тема: Гiлки (branches) — як працювати над фiчами паралельно.
 * <p>
 * Гiлка — це просто рухомий вказiвник на якийсь комiт. Коли ви робите
 * новий комiт, вказiвник поточної гiлки автоматично "переїжджає" на нього.
 * Гiлки потрiбнi, щоб одночасно вести кiлька паралельних напрямкiв роботи:
 * у main — стабiльний код, у feature/login — нова фiча, у hotfix/bug-42 — баг.
 * <p>
 * Команди:
 * <pre>
 *   git branch                  — список локальних гiлок (зiрочка * = поточна)
 *   git branch -a               — усi гiлки, включно з вiддаленими
 *   git branch feature/login    — створити нову гiлку
 *   git checkout feature/login  — переключитися на гiлку
 *   git checkout -b feature/login — створити i одразу переключитися
 *   git switch feature/login    — новiший варiант checkout (з Git 2.23)
 * </pre>
 * <p>
 * Аналогiя: гiлка — це паралельний всесвiт проєкту. У основному всесвiтi
 * (main) живе робочий додаток, а в гiлцi feature/dark-mode ви тихенько
 * пишете темну тему. Якщо вийде — об'єднаєте всесвiти (merge). Якщо нi —
 * просто видалите цей всесвiт, головний навiть не помiтить.
 */
public class Example07_GitBranches {

    public static void main(String[] args) throws Exception {
        // === Блок 1: концепцiя ===
        // Сценарiй: вiзуалiзуємо, як виглядає дерево комiтiв iз гiлками.
        System.out.println("=== Гiлка — це вказiвник на комiт ===");
        System.out.println();
        System.out.println("  main:    A --- B --- C --- D");
        System.out.println("                       \\");
        System.out.println("  feature:              E --- F   <-- HEAD (тут ми зараз)");
        System.out.println();
        System.out.println("'feature' указує на F, 'main' указує на D.");
        System.out.println("Гiлцi належать ВСI попереднi комiти (F -> E -> C -> B -> A).");

        System.out.println();

        // === Блок 2: створюємо репо з парою гiлок ===
        // Сценарiй: робимо реальне демо, щоб побачити команди в дiї.
        Path repo = Files.createTempDirectory("git-branches-");
        runIn(repo, "git", "init");
        runIn(repo, "git", "config", "user.name", "Student");
        runIn(repo, "git", "config", "user.email", "student@example.com");

        Files.writeString(repo.resolve("app.txt"), "версiя 1\n");
        runIn(repo, "git", "add", "-A");
        runIn(repo, "git", "commit", "-m", "v1");
        System.out.println();

        // === Блок 3: дивимось гiлки ===
        // Сценарiй: тiльки одна гiлка main (або master у старих Git).
        System.out.println("=== 'git branch' пiсля першого комiту ===");
        runIn(repo, "git", "branch");

        System.out.println();

        // === Блок 4: створюємо нову гiлку ===
        // Сценарiй: робимо feature/login на основi поточного стану.
        System.out.println("=== 'git checkout -b feature/login' (створити + переключитись) ===");
        runIn(repo, "git", "checkout", "-b", "feature/login");

        System.out.println();
        System.out.println("=== 'git branch' пiсля створення гiлки ===");
        runIn(repo, "git", "branch");
        System.out.println("Зiрочка * показує, на якiй гiлцi ми ЗАРАЗ.");

        System.out.println();

        // === Блок 5: комiтимо у нову гiлку ===
        // Сценарiй: змiни в feature/login не зачiпають main.
        Files.writeString(repo.resolve("login.txt"), "форма логiну\n");
        runIn(repo, "git", "add", "-A");
        runIn(repo, "git", "commit", "-m", "feat: add login form");

        System.out.println();
        System.out.println("=== 'git log --oneline --all' (iсторiя всiх гiлок) ===");
        runIn(repo, "git", "log", "--oneline", "--all", "--decorate");

        System.out.println();

        // === Блок 6: переключаємось на main ===
        // Сценарiй: повертаємось у main — там НЕМАЄ файлу login.txt.
        // Git намагається створити main, але якщо у старих версiях гiлка master —
        // спробуємо обидвi (для гнучкостi прикладу).
        System.out.println("=== Перемикаємось назад на головну гiлку ===");
        runIn(repo, "git", "checkout", "main");
        // Якщо немає main (старi версiї) — пробуємо master.
        if (!Files.exists(repo.resolve(".git/refs/heads/main"))) {
            runIn(repo, "git", "checkout", "master");
        }

        System.out.println();
        System.out.println("=== У головнiй гiлцi немає login.txt — перевiримо ===");
        System.out.println("Файл login.txt iснує: " + Files.exists(repo.resolve("login.txt")));
        System.out.println("(у feature/login вiн є, у main — нi. Гiлки iзольованi!)");

        System.out.println();

        // === Блок 7: коротка пам'ятка ===
        // Сценарiй: основнi команди в одному мiсцi.
        System.out.println("=== Пам'ятка по гiлках ===");
        System.out.println("git branch                       — список гiлок");
        System.out.println("git branch new-branch            — створити (НЕ переключаючись)");
        System.out.println("git checkout new-branch          — переключитися");
        System.out.println("git checkout -b new-branch       — створити + переключитися");
        System.out.println("git switch new-branch            — те саме, новiший синтаксис");
        System.out.println("git branch -d old-branch         — видалити (якщо змерджена)");
        System.out.println("git branch -D old-branch         — видалити силою");

        deleteRecursively(repo);
    }

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

    private static void deleteRecursively(Path path) throws Exception {
        if (!Files.exists(path)) return;
        Files.walk(path)
                .sorted((a, b) -> b.getNameCount() - a.getNameCount())
                .forEach(p -> {
                    try { Files.deleteIfExists(p); } catch (Exception ignored) {}
                });
    }
}
