package ua.com.javarush.jsquad.m1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекція 24: Потоки введення-виведення.
 * <p>
 * Тема: Клас BufferedWriter — буферизований запис символiв.
 * <p>
 * BufferedWriter записує текст у потiк виведення символiв,
 * буферизуючи символи для ефективного запису.
 * <p>
 * Конструктори:
 * <pre>
 *   BufferedWriter(Writer out)
 *   BufferedWriter(Writer out, int size)
 * </pre>
 * <p>
 * Корисний метод: {@code newLine()} — пише символ переходу на новий рядок,
 * що залежить вiд ОС (на Windows це "\r\n", на Linux/Mac — "\n").
 * <p>
 * Аналогія: листоноша. Без буфера — несе кожен лист окремо
 * (швидко втомлюється). Buffered — складає у сумку 100 листiв,
 * один похiд = доставлено все. Менше походiв = ефективнiше.
 * <p>
 * Реальне застосування: запис лог-файлiв, генерацiя великих звiтiв,
 * експорт CSV з тисячами рядкiв.
 */
public class Example09_BufferedWriter {

    public static void main(String[] args) throws IOException {
        // === Блок 1: BufferedWriter — простий приклад ===
        // Сценарiй: записуємо список покупок у пам'ять.
        System.out.println("=== BufferedWriter (StringWriter як sink) ===");
        StringWriter sink = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sink);

        bw.write("Список покупок:");
        bw.newLine();
        bw.write("- Хлiб");
        bw.newLine();
        bw.write("- Молоко");
        bw.newLine();
        bw.write("- Яйця");
        bw.flush();

        System.out.println("Записано:");
        System.out.println(sink);
        bw.close();

        System.out.println();

        // === Блок 2: Запис у файл ===
        // Сценарiй: формуємо щоденний звiт у файл.
        System.out.println("=== BufferedWriter + FileWriter ===");
        Path reportFile = Files.createTempFile("jsquad-l23-report-", ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile.toFile()))) {
            writer.write("=== Звiт за день ===");
            writer.newLine();
            writer.write("Дата: " + java.time.LocalDate.now());
            writer.newLine();
            writer.newLine();
            writer.write("Продажi: 12 одиниць");
            writer.newLine();
            writer.write("Виручка: 3450 грн");
            writer.newLine();
            writer.write("Прибуток: 890 грн");
        }

        System.out.println("Файл створено. Вмiст:");
        System.out.println(Files.readString(reportFile));

        System.out.println();

        // === Блок 3: Чому buffer пришвидшує запис ===
        // Сценарiй: пишемо 10 000 рядкiв i вимiрюємо рiзницю.
        System.out.println("=== Замiр швидкостi ===");

        Path fastFile = Files.createTempFile("jsquad-l23-fast-", ".txt");
        long start = System.nanoTime();
        try (BufferedWriter b = new BufferedWriter(new FileWriter(fastFile.toFile()))) {
            for (int i = 0; i < 10_000; i++) {
                b.write("Рядок номер " + i);
                b.newLine();
            }
        }
        long bufferedTime = System.nanoTime() - start;

        Path slowFile = Files.createTempFile("jsquad-l23-slow-", ".txt");
        start = System.nanoTime();
        try (Writer w = new FileWriter(slowFile.toFile())) {
            for (int i = 0; i < 10_000; i++) {
                w.write("Рядок номер " + i + System.lineSeparator());
            }
        }
        long unbufferedTime = System.nanoTime() - start;

        System.out.println("BufferedWriter: " + bufferedTime / 1_000_000 + " мс");
        System.out.println("FileWriter:     " + unbufferedTime / 1_000_000 + " мс");
        System.out.println("(точне число залежить вiд диска, але buffer стабiльно швидший)");

        System.out.println();

        // === Блок 4: flush() — коли це важливо ===
        // Сценарiй: записали данi, але вони ще в буферi.
        System.out.println("=== flush() — важливо! ===");
        System.out.println("write() кладе данi в буфер у пам'ятi.");
        System.out.println("Якщо програма впаде до flush()/close() — частина даних загубиться!");
        System.out.println("close() автоматично робить flush(). try-with-resources викликає close().");

        System.out.println();

        // === Блок 5: newLine() — кросплатформнiсть ===
        // Сценарiй: чому не "\n", а саме newLine().
        System.out.println("=== newLine() vs '\\n' ===");
        System.out.println("Windows: новий рядок = \\r\\n");
        System.out.println("Linux/Mac: новий рядок = \\n");
        System.out.println("newLine() використовує правильний символ для поточної ОС.");
        System.out.println("Поточний роздiлювач: " + System.lineSeparator()
                .replace("\r", "\\r").replace("\n", "\\n"));

        Files.deleteIfExists(reportFile);
        Files.deleteIfExists(fastFile);
        Files.deleteIfExists(slowFile);
    }
}
