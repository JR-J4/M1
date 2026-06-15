package ua.com.javarush.jsquad.m1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Лекцiя 28: Git.
 * <p>
 * Тема: Встановлення Git i початкове налаштування.
 * <p>
 * Перед роботою з Git його треба встановити (на Linux/macOS часто вже є,
 * на Windows — завантажити з git-scm.com). Пiсля встановлення обов'язково
 * налаштувати iм'я i email — цi данi Git пiдставлятиме у поле Author
 * кожного комiту, щоб було видно, хто його зробив.
 * <p>
 * Аналогiя: налаштування user.name/user.email — це як пiдписати ручку
 * перед контрольною. Кожен ваш запис у зошитi буде з вашим пiдписом,
 * i вчитель завжди знатиме, хто це написав.
 * <p>
 * Реальне застосування: без user.name/email Git вiдмовиться робити
 * комiт. У командi емейл має збiгатися з тим, що зареєстровано на
 * GitHub/GitLab — iнакше комiти не будуть прив'язанi до вашого профiлю.
 */
public class Example02_GitInstallAndConfig {

    public static void main(String[] args) {
        // === Блок 1: встановлення на рiзних ОС ===
        // Сценарiй: студент тiльки-но сiв за комп'ютер i ще не має Git.
        System.out.println("=== Встановлення Git ===");
        System.out.println("Windows: завантажити з https://git-scm.com/downloads i запустити .exe");
        System.out.println("Linux:   sudo apt-get install git   (для Ubuntu/Debian)");
        System.out.println("macOS:   зазвичай уже є (з XCode), або з https://git-scm.com/downloads");

        System.out.println();

        // === Блок 2: перевiрка, чи встановлено ===
        // Сценарiй: запускаємо git --version прямо з Java через ProcessBuilder,
        // щоб студент побачив реальну вiдповiдь термiналу.
        System.out.println("=== Перевiрка версiї (git --version) ===");
        runGitCommand("git", "--version");

        System.out.println();

        // === Блок 3: налаштування користувача ===
        // Сценарiй: показуємо команди, якi потрiбно виконати один раз.
        System.out.println("=== Налаштування Git (виконати ОДИН раз пiсля встановлення) ===");
        System.out.println("git config --global user.name \"Ivan Petrenko\"");
        System.out.println("git config --global user.email \"ivan@gmail.com\"");
        System.out.println();
        System.out.println("Прапорець --global означає 'для всiх проєктiв на цьому комп'ютерi'.");
        System.out.println("Без нього налаштування буде тiльки для поточного репозиторiю.");

        System.out.println();

        // === Блок 4: перегляд поточних налаштувань ===
        // Сценарiй: подивитись, якi user.name/email Git зараз використовує.
        System.out.println("=== Поточнi налаштування (git config --global --list) ===");
        runGitCommand("git", "config", "--global", "--list");

        System.out.println();

        // === Блок 5: корисний tip ===
        // Сценарiй: пiдказка для виправлення типової помилки.
        System.out.println("=== Якщо забули налаштувати email ===");
        System.out.println("Git видасть помилку при першому комiтi:");
        System.out.println("  'Please tell me who you are. Run git config --global user.email ...'");
        System.out.println("Не лякаємось — просто виконуємо двi команди з блоку 3.");
    }

    /**
     * Виконує git-команду i друкує її вивiд у консоль.
     * Якщо Git не встановлено — спокiйно повiдомить про це.
     */
    private static void runGitCommand(String... command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
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
            System.out.println("  (не вдалося запустити Git: " + e.getMessage() + ")");
        }
    }
}
