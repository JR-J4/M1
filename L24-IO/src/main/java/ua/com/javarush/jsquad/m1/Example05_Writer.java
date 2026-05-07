package ua.com.javarush.jsquad.m1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекція 24: Потоки введення-виведення.
 * <p>
 * Тема: Клас Writer — запис символiв (char).
 * <p>
 * Writer — це аналог OutputStream, але працює iз символами.
 * Основнi методи:
 * <ul>
 *   <li>{@code void write(int b)} — пише один символ</li>
 *   <li>{@code void write(char[] buffer)} — пише масив символiв</li>
 *   <li>{@code void write(char[] buffer, int off, int len)} — частину масиву</li>
 *   <li>{@code void write(String str)} — пише рядок</li>
 *   <li>{@code void write(String str, int off, int len)} — частину рядка</li>
 *   <li>{@code void flush()} — скидає буфер</li>
 *   <li>{@code void close()} — закриває потiк</li>
 * </ul>
 * <p>
 * Аналогія: Writer — це автор, який пише статтю українською. Він не думає
 * про байти — лише про слова та символи.
 * <p>
 * Реальне застосування: запис лог-файлiв, генерацiя HTML/CSV/JSON, експорт
 * звiтiв, збереження користувацьких даних.
 */
public class Example05_Writer {

    public static void main(String[] args) throws IOException {
        // === Блок 1: StringWriter — запис у пам'ять ===
        // Сценарiй: формуємо текст у пам'ятi перед вiдправкою.
        System.out.println("=== StringWriter — у пам'ять ===");
        StringWriter sw = new StringWriter();
        sw.write('H');
        sw.write('i');
        sw.write('!');
        System.out.println("Зiбрано: " + sw);
        sw.close();

        System.out.println();

        // === Блок 2: write(String) — пишемо рядок ===
        // Сценарiй: формуємо листа.
        System.out.println("=== write(String) ===");
        StringWriter letter = new StringWriter();
        letter.write("Привiт, ");
        letter.write("JavaRush");
        letter.write("!\n");
        letter.write("Сьогоднi ми вивчаємо потоки.");
        System.out.println("Лист:");
        System.out.println(letter);

        System.out.println();

        // === Блок 3: write(char[]) — масив символiв ===
        // Сценарiй: маємо готовий буфер символiв.
        System.out.println("=== write(char[]) ===");
        StringWriter cw = new StringWriter();
        char[] arr = {'J', 'a', 'v', 'a'};
        cw.write(arr);
        System.out.println("Результат: " + cw);

        System.out.println();

        // === Блок 4: write(String, off, len) — частина рядка ===
        // Сценарiй: записуємо лише iм'я з повного рядка.
        System.out.println("=== write(String, off, len) ===");
        StringWriter partWriter = new StringWriter();
        String full = "FirstName=Олександр;LastName=Петренко";
        partWriter.write(full, 10, 9); // "Олександр"
        System.out.println("Витягли тiльки iм'я: '" + partWriter + "'");

        System.out.println();

        // === Блок 5: FileWriter — запис у файл ===
        // Сценарiй: зберiгаємо щоденник у файл.
        System.out.println("=== FileWriter — у файл ===");
        Path tempFile = Files.createTempFile("jsquad-l23-writer-", ".txt");

        try (Writer fw = new FileWriter(tempFile.toFile())) {
            fw.write("Щоденник, " + java.time.LocalDate.now() + "\n");
            fw.write("Сьогоднi я вивчив потоки введення-виведення.\n");
            fw.write("Тепер можу читати та писати файли!");
        }

        System.out.println("Файл створено. Вмiст:");
        System.out.println(Files.readString(tempFile));

        System.out.println();

        // === Блок 6: Чому close() важливий для Writer ===
        // Сценарiй: дані без flush() можуть не доїхати до файлу.
        System.out.println("=== Чому close() обов'язковий ===");
        System.out.println("Writer часто буферизує данi для швидкостi.");
        System.out.println("Без close() (або flush()) частина тексту може не записатися!");
        System.out.println("Завжди використовуйте try-with-resources.");

        Files.deleteIfExists(tempFile);
    }
}
