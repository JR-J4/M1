package ua.com.javarush.jsquad.m1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекція 25: Підсумковий проєкт. Шифр Цезаря.
 * <p>
 * Тема: Запис результату у файл з потрiбним iм'ям.
 * <p>
 * За ТЗ результат шифрування/дешифрування — це новий файл у тiй
 * самiй папцi, що й вхiдний. Iм'я будується за правилом:
 * <pre>
 *   textFile1.txt        + ENCRYPT  -> textFile1[ENCRYPTED].txt
 *   textFile1[ENCRYPTED] + DECRYPT  -> textFile1[ENCRYPTED][DECRYPTED].txt
 * </pre>
 * <p>
 * Аналогiя з життя: коли ви архiвуєте папку — отримуєте файл
 * <i>NameOfFolder.zip</i> поруч iз оригiналом. Так само ми кладемо
 * результат поруч iз вихiдним файлом.
 * <p>
 * Реальне застосування: у проєктi цю логiку зазвичай оформлюють
 * у класi FileService (методи read, write, buildResultPath).
 */
public class Example05_WriteResultFile {

    public static void main(String[] args) throws IOException {
        // === Блок 1: Path — це не String ===
        // Сценарiй: уся робота з файлами у сучаснiй Java — через Path.
        Path original = Path.of("folder/textFile1.txt");
        System.out.println("=== Path API ===");
        System.out.println("getFileName():    " + original.getFileName());
        System.out.println("getParent():      " + original.getParent());
        System.out.println("toAbsolutePath():  " + original.toAbsolutePath());

        System.out.println();

        // === Блок 2: Розбиваємо iм'я файлу на iм'я + розширення ===
        // Сценарiй: щоб вставити "[ENCRYPTED]" перед ".txt".
        String fileName = original.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        String base = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
        String ext = (dotIndex == -1) ? "" : fileName.substring(dotIndex);
        System.out.println("=== Розбиваємо iм'я ===");
        System.out.println("base = " + base);
        System.out.println("ext  = " + ext);

        System.out.println();

        // === Блок 3: Будуємо iм'я результату ===
        // Сценарiй: для ENCRYPT додаємо [ENCRYPTED], для DECRYPT — [DECRYPTED].
        Path encrypted = buildResultPath(original, "ENCRYPTED");
        Path decrypted = buildResultPath(original, "DECRYPTED");
        System.out.println("=== Iм'я файлу-результату ===");
        System.out.println("ENCRYPT -> " + encrypted);
        System.out.println("DECRYPT -> " + decrypted);

        System.out.println();

        // === Блок 4: Реально пишемо у тимчасовий файл ===
        // Сценарiй: створимо файл i запишемо текст одним рядком.
        Path tmpDir = Files.createTempDirectory("L25_out");
        Path source = tmpDir.resolve("demo.txt");
        Files.writeString(source, "Hello, World!");
        Path result = buildResultPath(source, "ENCRYPTED");
        Files.writeString(result, "Khoor/2Zruog$");
        System.out.println("=== Запис у файл ===");
        System.out.println("Створили: " + result);
        System.out.println("Розмiр:   " + Files.size(result) + " байтiв");
        System.out.println("Вмiст:    " + Files.readString(result));

        // Прибираємо за собою
        Files.deleteIfExists(source);
        Files.deleteIfExists(result);
        Files.deleteIfExists(tmpDir);
    }

    /**
     * Будує шлях до файлу-результату поруч iз вхiдним.
     * folder/textFile1.txt + ENCRYPTED -> folder/textFile1[ENCRYPTED].txt
     */
    private static Path buildResultPath(Path source, String tag) {
        String name = source.getFileName().toString();
        int dot = name.lastIndexOf('.');
        String base = (dot == -1) ? name : name.substring(0, dot);
        String ext = (dot == -1) ? "" : name.substring(dot);
        String newName = base + "[" + tag + "]" + ext;
        Path parent = source.getParent();
        return (parent == null) ? Path.of(newName) : parent.resolve(newName);
    }
}
