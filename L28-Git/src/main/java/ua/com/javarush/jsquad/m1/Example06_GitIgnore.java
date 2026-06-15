package ua.com.javarush.jsquad.m1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекцiя 28: Git.
 * <p>
 * Тема: .gitignore — як сказати Git, якi файли НЕ вiдстежувати.
 * <p>
 * У проєктi завжди є файли, якi не потрiбно зберiгати в репозиторiї:
 * скомпiльованi .class, папка target/ вiд Maven, .idea/ вiд IntelliJ,
 * локальнi паролi у .env. Файл .gitignore у коренi проєкту перелiчує
 * шаблони таких файлiв — i Git їх просто iгнорує.
 * <p>
 * Аналогiя: .gitignore — це список 'не фотографувати'. У вас є фотограф
 * (Git), який знiмає все, що бачить. Ви даєте йому список: 'смiттєвий
 * бак не знiмай, кота сусiдiв не знiмай'. I вiн пропускає цi об'єкти.
 * <p>
 * Реальне застосування: правильний .gitignore економить мегабайти у репо
 * i не дає випадково запушити паролi/токени на GitHub. Для Java-проєкту
 * .gitignore — обов'язковий з першого дня.
 */
public class Example06_GitIgnore {

    public static void main(String[] args) throws Exception {
        // === Блок 1: типовi шаблони ===
        // Сценарiй: показуємо найчастiшi записи у .gitignore Java-проєкту.
        System.out.println("=== Типовий .gitignore для Java-проєкту ===");
        System.out.println("*.class       — усi скомпiльованi класи");
        System.out.println("target/       — папка Maven iз результатами збiрки");
        System.out.println("*.iml         — файли модулiв IntelliJ");
        System.out.println(".idea/        — налаштування IntelliJ для конкретної машини");
        System.out.println(".vscode/      — налаштування VS Code");
        System.out.println("*.log         — лог-файли");
        System.out.println(".env          — змiннi оточення (часто з паролями!)");
        System.out.println("/secrets.txt  — конкретний файл у коренi");

        System.out.println();

        // === Блок 2: правила синтаксису ===
        // Сценарiй: пояснюємо, як писати шаблони.
        System.out.println("=== Синтаксис шаблонiв ===");
        System.out.println("*.ext      — усi файли з розширенням .ext (у всiх папках)");
        System.out.println("name/      — папка name i все, що в нiй");
        System.out.println("/name      — тiльки в коренi (не у пiдпапках)");
        System.out.println("# comment  — рядок iз # — коментар");
        System.out.println("!file.txt  — виняток: НЕ iгнорувати цей файл");

        System.out.println();

        // === Блок 3: демо — створюємо репо з .gitignore ===
        // Сценарiй: робимо тимчасове репо, кладемо туди .gitignore i рiзнi файли.
        Path repo = Files.createTempDirectory("gitignore-demo-");
        runIn(repo, "git", "init");

        // Створюємо .gitignore
        String ignoreContent = """
                # скомпiльованi класи
                *.class
                # папка збiрки
                target/
                # секрети
                .env
                """;
        Files.writeString(repo.resolve(".gitignore"), ignoreContent);

        // Створюємо файли: один потрiбний, три iгноровi
        Files.writeString(repo.resolve("Main.java"), "public class Main {}");
        Files.writeString(repo.resolve("Main.class"), "(скомпiльований байткод)");
        Files.writeString(repo.resolve(".env"), "DB_PASSWORD=super-secret-123");
        Files.createDirectory(repo.resolve("target"));
        Files.writeString(repo.resolve("target/app.jar"), "(зiбраний JAR)");

        System.out.println("=== Створили файли: Main.java, Main.class, .env, target/app.jar ===");
        System.out.println("=== Тепер 'git status' покаже ТIЛЬКИ те, що НЕ iгнорується ===");
        runIn(repo, "git", "status");

        System.out.println();

        // === Блок 4: висновок ===
        // Сценарiй: пiдсумок — на що звернути увагу у виводi.
        System.out.println("=== Зверни увагу ===");
        System.out.println("У статусi є тiльки Main.java i .gitignore.");
        System.out.println("Main.class, .env, target/ — невидимi для Git!");

        System.out.println();

        // === Блок 5: важливе правило ===
        // Сценарiй: типова помилка — закомiтили секрет, потiм додали в .gitignore.
        System.out.println("=== ВАЖЛИВО ===");
        System.out.println(".gitignore працює тiльки для файлiв, якi ЩЕ НЕ доданi в Git.");
        System.out.println("Якщо ви вже закомiтили .env iз паролем — додавання в .gitignore");
        System.out.println("НЕ видалить його з iсторiї. Доведеться:");
        System.out.println("   git rm --cached .env   (прибрати з вiдстежування)");
        System.out.println("   а потiм закомiтити .gitignore i змiнити пароль!");

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
