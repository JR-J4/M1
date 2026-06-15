package ua.com.javarush.jsquad.m1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекцiя 28: Git.
 * <p>
 * Тема: Об'єднання гiлок (merge) i розв'язання конфлiктiв (conflicts).
 * <p>
 * Коли робота у гiлцi закiнчена, її треба ВЛИТИ назад у main. Це робиться
 * командою 'git merge'. Якщо файли в обох гiлках змiнювались у РIЗНИХ
 * мiсцях — Git змерджить автоматично. Якщо в ОДНОМУ й тому ж рядку —
 * виникає конфлiкт, який треба розв'язати руками.
 * <p>
 * Команди:
 * <pre>
 *   git checkout main           — переходимо в гiлку, у яку зливаємо
 *   git merge feature/login     — зливаємо feature/login у main
 *   git merge --abort           — скасувати merge (якщо щось пiшло не так)
 * </pre>
 * <p>
 * Аналогiя: уявiть, що ви i колега одночасно редагували один документ
 * Word. Якщо ви правили першу сторiнку, а колега — третю, об'єднання
 * пройде непомiтно. Але якщо обоє переписали один абзац — треба сiсти i
 * вирiшити, чий варiант лишити.
 */
public class Example08_GitMergeConflicts {

    public static void main(String[] args) throws Exception {
        // === Блок 1: схема merge ===
        // Сценарiй: пояснюємо словами, що вiдбувається при merge.
        System.out.println("=== Як виглядає merge ===");
        System.out.println();
        System.out.println("ДО merge:");
        System.out.println("  main:    A --- B --- C");
        System.out.println("                \\");
        System.out.println("  feature:       D --- E");
        System.out.println();
        System.out.println("ПIСЛЯ merge feature у main:");
        System.out.println("  main:    A --- B --- C --- M    <-- M = 'merge commit'");
        System.out.println("                \\         /");
        System.out.println("  feature:       D --- E");

        System.out.println();

        // === Блок 2: автоматичний merge без конфлiктiв ===
        // Сценарiй: створюємо двi гiлки, якi змiнюють РIЗНI файли.
        Path repo = Files.createTempDirectory("git-merge-");
        runIn(repo, "git", "init");
        runIn(repo, "git", "config", "user.name", "Student");
        runIn(repo, "git", "config", "user.email", "student@example.com");

        Files.writeString(repo.resolve("a.txt"), "файл A v1\n");
        Files.writeString(repo.resolve("b.txt"), "файл B v1\n");
        runIn(repo, "git", "add", "-A");
        runIn(repo, "git", "commit", "-m", "init");

        // у feature змiнюємо a.txt
        runIn(repo, "git", "checkout", "-b", "feature");
        Files.writeString(repo.resolve("a.txt"), "файл A v2 (вiд feature)\n");
        runIn(repo, "git", "add", "-A");
        runIn(repo, "git", "commit", "-m", "feat: update A");

        // у main змiнюємо b.txt
        runIn(repo, "git", "checkout", "-");  // повернутись назад
        Files.writeString(repo.resolve("b.txt"), "файл B v2 (вiд main)\n");
        runIn(repo, "git", "add", "-A");
        runIn(repo, "git", "commit", "-m", "feat: update B");

        System.out.println();
        System.out.println("=== Зливаємо feature у головну гiлку (рiзнi файли — конфлiкту НЕ буде) ===");
        runIn(repo, "git", "merge", "feature", "-m", "merge feature into main");

        System.out.println();
        System.out.println("=== 'git log --oneline --graph' (бачимо M-комiт) ===");
        runIn(repo, "git", "log", "--oneline", "--graph", "--all");

        deleteRecursively(repo);

        System.out.println();

        // === Блок 3: КОНФЛIКТНИЙ merge ===
        // Сценарiй: тепер обидвi гiлки правлять один файл, тi самi рядки.
        Path repo2 = Files.createTempDirectory("git-conflict-");
        runIn(repo2, "git", "init");
        runIn(repo2, "git", "config", "user.name", "Student");
        runIn(repo2, "git", "config", "user.email", "student@example.com");

        Files.writeString(repo2.resolve("greeting.txt"), "Hello!\n");
        runIn(repo2, "git", "add", "-A");
        runIn(repo2, "git", "commit", "-m", "init");

        // у feature змiнюємо greeting
        runIn(repo2, "git", "checkout", "-b", "feature");
        Files.writeString(repo2.resolve("greeting.txt"), "Привiт!\n");
        runIn(repo2, "git", "add", "-A");
        runIn(repo2, "git", "commit", "-m", "feat: ukrainian greeting");

        // у main одночасно змiнюємо той самий рядок
        runIn(repo2, "git", "checkout", "-");
        Files.writeString(repo2.resolve("greeting.txt"), "Hi there!\n");
        runIn(repo2, "git", "add", "-A");
        runIn(repo2, "git", "commit", "-m", "feat: informal greeting");

        System.out.println("=== Зливаємо feature — ОЧIКУЄМО КОНФЛIКТ ===");
        runIn(repo2, "git", "merge", "feature");

        System.out.println();
        System.out.println("=== Як виглядає файл iз конфлiктом ===");
        System.out.println(Files.readString(repo2.resolve("greeting.txt")));

        System.out.println();
        System.out.println("=== Розшифровка маркерiв ===");
        System.out.println("  <<<<<<< HEAD             — початок 'нашого' варiанту (з main)");
        System.out.println("  ...                       — наш текст");
        System.out.println("  =======                   — роздiлювач");
        System.out.println("  ...                       — текст з гiлки, що вливається");
        System.out.println("  >>>>>>> feature           — кiнець варiанту з feature");

        System.out.println();

        // === Блок 4: як розв'язати конфлiкт ===
        // Сценарiй: записуємо фiнальну версiю файлу i доробляємо merge.
        Files.writeString(repo2.resolve("greeting.txt"), "Привiт! Hi there!\n");
        runIn(repo2, "git", "add", "greeting.txt");
        runIn(repo2, "git", "commit", "-m", "merge: resolve greeting conflict");

        System.out.println("=== Конфлiкт вирiшено ===");
        System.out.println("Алгоритм:");
        System.out.println("  1) Вiдкрити файл i прибрати маркери <<<<<<< ======= >>>>>>>");
        System.out.println("  2) Залишити фiнальну версiю (одну, обидвi, або об'єднати)");
        System.out.println("  3) git add <file>");
        System.out.println("  4) git commit (без -m — Git сам запропонує merge-повiдомлення)");

        System.out.println();
        System.out.println("=== Якщо все пiшло не так ===");
        System.out.println("git merge --abort  — скасувати merge i повернутися до стану 'до merge'");

        deleteRecursively(repo2);
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
