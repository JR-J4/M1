package ua.com.javarush.jsquad.m1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Лекція 26: Потоки введення-виведення. Частина 3 (NIO).
 * <p>
 * Тема: Читання та запис файлiв через Files — без потокiв i буферiв.
 * <p>
 * Зручнi методи Files для роботи з вмiстом:
 * <ul>
 *   <li>{@code byte[] readAllBytes(Path)} — увесь файл як масив байт</li>
 *   <li>{@code String readString(Path)} — увесь файл як рядок (Java 11+)</li>
 *   <li>{@code List<String> readAllLines(Path)} — увесь файл як список рядкiв</li>
 *   <li>{@code Path write(Path, byte[])} — записати байти</li>
 *   <li>{@code Path writeString(Path, String)} — записати рядок (Java 11+)</li>
 *   <li>{@code boolean exists / isRegularFile / isDirectory}</li>
 *   <li>{@code long size(Path)}</li>
 * </ul>
 * <p>
 * Аналогiя: ранiше потрiбен був цiлий ритуал — вiдкрити потiк, обернути
 * у BufferedReader, читати порядково, закрити. Тепер це один виклик:
 * {@code Files.readString(path)}.
 * <p>
 * Реальне застосування: швидке зчитування конфiгу, шаблону, парсинг
 * невеликих JSON/YAML/CSV. ⚠ Не для гiгабайтних файлiв — все в пам'ять!
 */
public class Example06_FilesRead {

    public static void main(String[] args) throws IOException {
        // === Блок 1: writeString + readString ===
        // Сценарiй: збереження звiту у файл i зчитування його назад.
        System.out.println("=== writeString / readString ===");
        Path report = Files.createTempFile("report-", ".txt");
        Files.writeString(report, "Продажi за квартал: 125 000 грн.\nПрибуток: 32 000 грн.");

        String content = Files.readString(report);
        System.out.println("Вмiст файлу:\n" + content);

        System.out.println();

        // === Блок 2: write byte[] + readAllBytes ===
        // Сценарiй: бiнарнi данi — картинка, серiалiзованi об'єкти.
        System.out.println("=== write(byte[]) / readAllBytes ===");
        Path bin = Files.createTempFile("data-", ".bin");
        byte[] payload = {1, 2, 3, 4, 5, 65, 66, 67};
        Files.write(bin, payload);

        byte[] read = Files.readAllBytes(bin);
        System.out.print("Прочитанi байти: ");
        for (byte b : read) {
            System.out.print(b + " ");
        }
        System.out.println();

        System.out.println();

        // === Блок 3: readAllLines — список рядкiв ===
        // Сценарiй: парсинг CSV або списку завдань — потрiбен лiнiйний доступ.
        System.out.println("=== readAllLines ===");
        Path todo = Files.createTempFile("todo-", ".txt");
        Files.writeString(todo, "Купити молоко\nЗателефонувати мамi\nПомити машину");

        List<String> lines = Files.readAllLines(todo);
        for (int i = 0; i < lines.size(); i++) {
            System.out.println((i + 1) + ". " + lines.get(i));
        }

        System.out.println();

        // === Блок 4: метаiнформацiя про файл ===
        // Сценарiй: дiзнаємось параметри файлу перед обробкою.
        System.out.println("=== exists / size / isRegularFile / isDirectory ===");
        System.out.println("Iснує?           " + Files.exists(todo));
        System.out.println("Це звичайний файл? " + Files.isRegularFile(todo));
        System.out.println("Це директорiя?    " + Files.isDirectory(todo));
        System.out.println("Розмiр:          " + Files.size(todo) + " байт");

        Path tmpDir = todo.getParent();
        System.out.println("\nДля " + tmpDir);
        System.out.println("Це директорiя? " + Files.isDirectory(tmpDir));
        System.out.println("Звичайний файл? " + Files.isRegularFile(tmpDir));

        System.out.println();

        // === Блок 5: коли НЕ використовувати ці методи ===
        // Сценарiй: великий файл — readAllBytes може зжерти всю пам'ять.
        System.out.println("=== ⚠ Обмеження ===");
        System.out.println("readAllBytes / readString / readAllLines — все в пам'ять.");
        System.out.println("Для великих файлiв (>100 МБ) використовуйте:");
        System.out.println("  - Files.newBufferedReader() + readLine()");
        System.out.println("  - Files.lines() — Stream, читає лiниво");
        System.out.println("  - FileChannel + ByteBuffer (див. Example09)");

        // прибираємо
        Files.delete(report);
        Files.delete(bin);
        Files.delete(todo);
    }
}
