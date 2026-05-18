package ua.com.javarush.jsquad.m1;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Лекція 26: Потоки введення-виведення. Частина 3 (NIO).
 * <p>
 * Тема: Клас Path — сучасна замiна File.
 * <p>
 * Path — це iнтерфейс, який представляє шлях до файлу або директорiї.
 * Прийшов на замiну застарiлому класу File. Працювати з ним безпечнiше
 * та ефективнiше.
 * <p>
 * Створити Path можна трьома способами:
 * <ul>
 *   <li>{@code Path.of("шлях")} — рекомендовано (з Java 11)</li>
 *   <li>{@code Path.of("частина1", "частина2", ...)} — збирає шлях iз частин</li>
 *   <li>{@code Paths.get("шлях")} — старiший варiант, теж працює</li>
 * </ul>
 * <p>
 * Аналогiя: Path — це адреса на конвертi. Можна написати її одним рядком
 * "м. Київ, вул. Хрещатик, 1" або зiбрати з частин: мiсто + вулиця + дiм.
 * <p>
 * Реальне застосування: будь-яка робота з файлами — Path є першим, що
 * створюється для подальших операцiй (читання, запис, копiювання).
 */
public class Example02_Path {

    public static void main(String[] args) {
        // === Блок 1: створення Path через Path.of() ===
        // Сценарiй: вказуємо шлях до файлу одним рядком.
        System.out.println("=== Path.of(одним рядком) ===");
        Path p1 = Path.of("/Users/student/docs/report.txt");
        System.out.println("Шлях: " + p1);

        // Для Windows використовуйте \\ (екранованi слешi)
        Path p2 = Path.of("C:\\Users\\student\\report.txt");
        System.out.println("Windows-шлях: " + p2);

        System.out.println();

        // === Блок 2: створення Path iз частин ===
        // Сценарiй: збираємо шлях iз окремих сегментiв (як шлях iз кубикiв).
        System.out.println("=== Path.of(з частин) ===");
        Path p3 = Path.of("Users", "student", "docs", "report.txt");
        System.out.println("Зiбрано: " + p3);

        // Зручно, коли частини шляху приходять iз рiзних мiсць
        String user = "student";
        String fileName = "diploma.pdf";
        Path userFile = Path.of("home", user, "documents", fileName);
        System.out.println("Динамiчно: " + userFile);

        System.out.println();

        // === Блок 3: створення через Paths.get() — старий варiант ===
        // Сценарiй: легасi-код, у якому ще не перейшли на Path.of().
        System.out.println("=== Paths.get() (legacy) ===");
        Path p4 = Paths.get("home", "student", "data.csv");
        System.out.println("Paths.get(): " + p4);
        System.out.println("(працює так само, але краще писати Path.of)");

        System.out.println();

        // === Блок 4: створення з URI === URL
        // Сценарiй: маємо посилання у форматi file:// — конвертуємо в Path.
        System.out.println("=== Path iз URI ===");
        URI uri = URI.create("file:///tmp/data.txt");
        Path fromUri = Path.of(uri);
        System.out.println("URI: " + uri);
        System.out.println("Path: " + fromUri);

        System.out.println();

        // === Блок 5: Path не створює файл — це просто рядок-адреса ===
        // Сценарiй: можна створити Path до неiснуючого файлу — це нормально.
        System.out.println("=== Path — це лише адреса ===");
        Path ghost = Path.of("/tmp/немає-такого-файлу.txt");
        System.out.println("Створили Path: " + ghost);
        System.out.println("Файл iснує? — Path не перевiряє. Тiльки зберiгає адресу.");
    }
}
