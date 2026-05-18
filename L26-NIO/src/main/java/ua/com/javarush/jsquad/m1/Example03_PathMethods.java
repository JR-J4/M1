package ua.com.javarush.jsquad.m1;

import java.nio.file.Path;

/**
 * Лекція 26: Потоки введення-виведення. Частина 3 (NIO).
 * <p>
 * Тема: Методи об'єкта Path — отримання частин шляху.
 * <p>
 * Корисні методи Path:
 * <ul>
 *   <li>{@code getParent()} — батькiвська директорiя</li>
 *   <li>{@code getFileName()} — iм'я файлу без директорiї</li>
 *   <li>{@code getRoot()} — коренева директорiя</li>
 *   <li>{@code getNameCount()} — кiлькiсть частин шляху</li>
 *   <li>{@code getName(int)} — частина шляху за iндексом</li>
 *   <li>{@code subpath(begin, end)} — пiдшлях за iнтервалом</li>
 *   <li>{@code startsWith / endsWith} — перевiрка початку/кiнця</li>
 *   <li>{@code toFile() / toUri()} — конвертацiя</li>
 * </ul>
 * <p>
 * Аналогiя: пошта розбирає адресу на частини — країна, мiсто, вулиця, дiм.
 * Path так само вмiє розкласти "/home/student/docs/report.txt" на
 * компоненти.
 * <p>
 * Реальне застосування: показати користувачу лише iм'я файлу без шляху,
 * створити папку для файлу, перевiрити розширення, валiдувати путь.
 */
public class Example03_PathMethods {

    public static void main(String[] args) {
        // === Блок 1: getParent, getFileName, getRoot ===
        // Сценарiй: маємо повний шлях — хочемо окремi частини.
        System.out.println("=== Базовi методи ===");
        Path path = Path.of("/home/student/docs/report.txt");
        System.out.println("Повний шлях: " + path);
        System.out.println("Батьк. директорiя: " + path.getParent());   // /home/student/docs
        System.out.println("Iм'я файлу:        " + path.getFileName()); // report.txt
        System.out.println("Корiнь:            " + path.getRoot());     // /

        System.out.println();

        // === Блок 2: розбиття шляху на частини ===
        // Сценарiй: розкладаємо шлях на iмена директорiй i файлу.
        System.out.println("=== getNameCount / getName / subpath ===");
        Path p = Path.of("/home/student/docs/report.txt");
        System.out.println("Кiлькiсть частин: " + p.getNameCount());  // 4

        for (int i = 0; i < p.getNameCount(); i++) {
            System.out.println("  частина[" + i + "] = " + p.getName(i));
        }

        // subpath повертає шматок шляху
        System.out.println("subpath(1, 3): " + p.subpath(1, 3)); // student/docs

        System.out.println();

        // === Блок 3: startsWith / endsWith ===
        // Сценарiй: перевiряємо, що файл лежить у потрiбнiй директорiї.
        System.out.println("=== startsWith / endsWith ===");
        Path doc = Path.of("/home/student/docs/report.txt");
        System.out.println("Починається з /home/student? " + doc.startsWith("/home/student"));
        System.out.println("Закiнчується на report.txt?   " + doc.endsWith("report.txt"));
        System.out.println("Закiнчується на .txt?         " + doc.endsWith(".txt"));
        // ⚠ endsWith працює на частинах шляху, а не як рядкове .endsWith()
        // тому ".txt" не пройде — треба порiвнювати з iменем файлу як рядком
        String name = doc.getFileName().toString();
        System.out.println("Розширення .txt? (рядком): " + name.endsWith(".txt"));

        System.out.println();

        // === Блок 4: ланцюжки методiв — практичний приклад ===
        // Сценарiй: показати користувачу тiльки iм'я файлу без шляху.
        System.out.println("=== Практика: лише iм'я ===");
        Path photo = Path.of("/Users/anna/photos/2024/holiday.jpg");
        System.out.println("Завантажено: " + photo.getFileName()); // holiday.jpg
        System.out.println("Папка: " + photo.getParent().getParent().getParent().getParent().getParent().getParent().getFileName()); // 2024

        System.out.println();

        // === Блок 5: toFile() i toUri() ===
        // Сценарiй: треба передати старiй бiблiотецi File або зробити URI.
        System.out.println("=== Конвертацiя ===");
        Path cfg = Path.of("/etc/app/config.yaml");
        System.out.println("toFile(): " + cfg.toFile()); // java.io.File
        System.out.println("toUri():  " + cfg.toUri());  // file:///etc/app/config.yaml

        System.out.println();

        // === Блок 6: getParent повертає null для кореня ===
        // Сценарiй: захист вiд NullPointerException у рекурсивних обходах.
        System.out.println("=== Кордоновi випадки ===");
        Path root = Path.of("/");
        System.out.println("getParent() для /: " + root.getParent()); // null
        System.out.println("getFileName() для /: " + root.getFileName()); // null
        System.out.println("⚠ Обережно з null при рекурсивному обходi!");
    }
}
