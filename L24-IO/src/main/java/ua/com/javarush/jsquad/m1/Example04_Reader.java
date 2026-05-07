package ua.com.javarush.jsquad.m1;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекція 24: Потоки введення-виведення.
 * <p>
 * Тема: Клас Reader — читання символiв (char).
 * <p>
 * Reader — це аналог InputStream, але читає символи, а не байти.
 * Основнi методи:
 * <ul>
 *   <li>{@code int read()} — читає 1 символ (повертає -1 при кiнцi)</li>
 *   <li>{@code int read(char[] buffer)} — читає масив символiв</li>
 *   <li>{@code long skip(long n)} — пропускає n символiв</li>
 *   <li>{@code boolean ready()} — чи є ще данi для читання</li>
 *   <li>{@code void close()} — закриває потiк</li>
 * </ul>
 * <p>
 * Аналогія: InputStream — читає сирi байти (як двiрник збирає смiття у мiшок).
 * Reader — читає символи (як читач сприймає текст книги).
 * Reader розумiє кодування — за нього вже хтось перетворив байти у char.
 * <p>
 * Реальне застосування: читання текстових файлiв, конфiгурацiй, лог-файлiв,
 * CSV/JSON, де нас цiкавлять саме символи, а не байти.
 */
public class Example04_Reader {

    public static void main(String[] args) throws IOException {
        // === Блок 1: StringReader — читання з рядка ===
        // Сценарiй: маємо текст у пам'ятi i хочемо обробити його як потiк.
        System.out.println("=== read() — по одному символу ===");
        Reader reader = new StringReader("Java");
        int charCode;
        while ((charCode = reader.read()) != -1) {
            System.out.println("Символ: '" + (char) charCode + "' (код " + charCode + ")");
        }
        reader.close();

        System.out.println();

        // === Блок 2: read(char[]) — у буфер ===
        // Сценарiй: читаємо порцiями для ефективностi.
        System.out.println("=== read(char[]) — у буфер ===");
        Reader r = new StringReader("Привiт, свiт!");
        char[] buffer = new char[5];
        int charsRead = r.read(buffer);
        System.out.println("Прочитано " + charsRead + " символiв");
        System.out.println("Буфер: '" + new String(buffer, 0, charsRead) + "'");

        // Дочитуємо решту
        charsRead = r.read(buffer);
        System.out.println("Прочитано ще " + charsRead + " символiв");
        System.out.println("Буфер: '" + new String(buffer, 0, charsRead) + "'");
        r.close();

        System.out.println();

        // === Блок 3: ready() — перевiрка наявностi даних ===
        // Сценарiй: хочемо перевiрити чи можна читати без блокування.
        System.out.println("=== ready() ===");
        Reader r2 = new StringReader("ABC");
        System.out.println("Чи готовий потiк? " + r2.ready());
        System.out.println("Читаємо: " + (char) r2.read());
        System.out.println("Чи готовий потiк? " + r2.ready());
        r2.close();

        System.out.println();

        // === Блок 4: FileReader — читання текстового файлу ===
        // Сценарiй: читаємо записку з файлу.
        System.out.println("=== FileReader — текстовий файл ===");
        Path tempFile = Files.createTempFile("jsquad-l23-reader-", ".txt");
        Files.writeString(tempFile, "Привiт, JavaRush!\nЦе текстовий файл з українською мовою.");

        try (FileReader fileReader = new FileReader(tempFile.toFile())) {
            char[] buf = new char[100];
            int total = fileReader.read(buf);
            System.out.println("Прочитано " + total + " символiв з файлу:");
            System.out.println(new String(buf, 0, total));
        }

        System.out.println();

        // === Блок 5: Reader vs InputStream — у чому рiзниця ===
        // Сценарiй: пiдказка коли який клас використовувати.
        System.out.println("=== InputStream чи Reader? ===");
        System.out.println("InputStream: байти. 'Ї' = 2 байти, прочитаєш 2 окремi числа.");
        System.out.println("Reader: символи. 'Ї' = 1 char (код 1031), читаєш як єдине ціле.");
        System.out.println("Для текстiв з рiзними мовами — завжди Reader!");

        Files.deleteIfExists(tempFile);
    }
}
