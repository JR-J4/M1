package ua.com.javarush.jsquad.m1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекцiя 28: Git.
 * <p>
 * Тема: Повний цикл локальної роботи — add, commit, log, diff.
 * <p>
 * Цей приклад робить повноцiнне демо: створює репо, додає файли,
 * робить два комiти, показує iсторiю i рiзницю мiж версiями.
 * Усе це через ProcessBuilder, тож ви бачите справжнiй вивiд Git.
 * <p>
 * Синтаксис команд:
 * <pre>
 *   git add -A              — додати ВСI файли (новi, змiненi, видаленi)
 *   git add .               — додати все з поточної папки
 *   git add file.txt        — додати конкретний файл
 *   git add *.java          — додати за шаблоном (усi .java)
 *   git commit -m "повiд."  — створити комiт iз повiдомленням
 *   git log                 — iсторiя комiтiв
 *   git diff                — змiни в modified-файлах
 * </pre>
 * <p>
 * Аналогiя: git add — це 'покласти речi у валiзу', git commit — 'застiбнути
 * валiзу i поставити її на полицю'. Пiсля комiту валiзу вже не вiдкриєш
 * непомiтно: будь-яка змiна буде окремою новою валiзою.
 */
public class Example05_GitAddCommit {

    public static void main(String[] args) throws Exception {
        // === Блок 0: пiдготовка робочої папки ===
        // Сценарiй: створюємо тимчасове репо, в якому будемо все робити.
        Path repo = Files.createTempDirectory("git-add-commit-");
        System.out.println("=== Робоча папка для демо ===");
        System.out.println(repo);
        System.out.println();

        runIn(repo, "git", "init");
        runIn(repo, "git", "config", "user.name", "Student");
        runIn(repo, "git", "config", "user.email", "student@example.com");
        System.out.println();

        // === Блок 1: створюємо файли i дивимось status ===
        // Сценарiй: два файли — README.md i Main.java.
        Files.writeString(repo.resolve("README.md"), "# Мiй перший проєкт\n");
        Files.writeString(repo.resolve("Main.java"), "public class Main { }\n");

        System.out.println("=== Створили README.md i Main.java. 'git status' ===");
        runIn(repo, "git", "status");

        System.out.println();

        // === Блок 2: git add — додаємо все ===
        // Сценарiй: переводимо файли зi стану untracked у staged.
        System.out.println("=== 'git add -A' (додаємо ВСI файли у staged) ===");
        runIn(repo, "git", "add", "-A");
        runIn(repo, "git", "status");

        System.out.println();

        // === Блок 3: перший комiт ===
        // Сценарiй: закомiчуємо stage у iсторiю.
        System.out.println("=== Перший комiт ===");
        runIn(repo, "git", "commit", "-m", "Initial commit: README + Main");

        System.out.println();

        // === Блок 4: змiнюємо файл i дивимось diff ===
        // Сценарiй: змiнили Main.java, git diff покаже рiзницю.
        Files.writeString(repo.resolve("Main.java"),
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, Git!\");\n" +
                "    }\n" +
                "}\n");

        System.out.println("=== Змiнили Main.java. 'git diff' ===");
        runIn(repo, "git", "diff");

        System.out.println();

        // === Блок 5: другий комiт ===
        // Сценарiй: додаємо тiльки змiнений файл, комiтимо.
        System.out.println("=== Додаємо тiльки Main.java i робимо другий комiт ===");
        runIn(repo, "git", "add", "Main.java");
        runIn(repo, "git", "commit", "-m", "feat: add main method");

        System.out.println();

        // === Блок 6: iсторiя комiтiв ===
        // Сценарiй: git log показує всi комiти у гiлцi.
        System.out.println("=== 'git log' (iсторiя комiтiв) ===");
        runIn(repo, "git", "log");

        System.out.println();

        // === Блок 7: компактний log ===
        // Сценарiй: коротка форма log, корисна на практицi.
        System.out.println("=== 'git log --oneline' (компактна iсторiя) ===");
        runIn(repo, "git", "log", "--oneline");

        System.out.println();

        // === Блок 8: правила хороших commit-повiдомлень ===
        // Сценарiй: ПОРАДА на майбутнє.
        System.out.println("=== Як писати хорошi commit-повiдомлення ===");
        System.out.println("ПОГАНО: 'fix', 'update', 'changes', 'asdf'");
        System.out.println("ДОБРЕ: 'feat: add user login', 'fix: NPE in OrderService.calc()'");
        System.out.println("Префiкси (Conventional Commits):");
        System.out.println("  feat:   нова функцiя");
        System.out.println("  fix:    виправлення бага");
        System.out.println("  docs:   тiльки документацiя");
        System.out.println("  refactor: переписали код без змiни поведiнки");
        System.out.println("  test:   доданi/змiненi тести");

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
