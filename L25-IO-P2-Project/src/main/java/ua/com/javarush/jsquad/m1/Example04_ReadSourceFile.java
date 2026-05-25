package ua.com.javarush.jsquad.m1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Лекція 25: Підсумковий проєкт. Шифр Цезаря.
 * <p>
 * Тема: Читання вхiдного файлу.
 * <p>
 * За ТЗ програма приймає шлях до текстового файлу. Нам потрiбно
 * прочитати його повнiстю — i зберегти форматування (перенесення
 * рядкiв, пробiли, табуляцiї тощо).
 * <p>
 * Тут демонструємо ТРИ способи прочитати файл:
 * <ul>
 *   <li>BufferedReader + readLine() — класика (треба самим зберiгати \n).</li>
 *   <li>Files.readString(path) — одна стрiчка коду, Java 11+.</li>
 *   <li>Files.readAllLines(path) — список рядкiв.</li>
 * </ul>
 * <p>
 * Аналогія з життя: уявiть, що ви передруковуєте лист. Можна
 * передруковувати слово-за-словом (BufferedReader), а можна
 * зробити ксерокопiю одним натисканням (Files.readString).
 * <p>
 * Реальне застосування: у проєктi-криптоаналiзаторi використовуємо
 * Files.readString — найзручнiше, бо отримуємо текст одним рядком
 * i можемо одразу його зашифрувати.
 * <p>
 * Увага: у прикладi ми НЕ читаємо реальний файл (його може й не бути).
 * Ми симулюємо вхiдний потiк через StringReader, щоб приклад
 * запускався без зовнiшнiх залежностей.
 */
public class Example04_ReadSourceFile {

    public static void main(String[] args) throws IOException {
        // === Блок 1: Симуляцiя вмiсту файлу ===
        // Сценарiй: уявiть, що це вмiст файлу project.txt.
        String fakeFileContent = """
                Перший рядок тексту.
                Другий рядок iз пробiлами   та табами.
                Третiй рядок!
                """;
        System.out.println("=== Що буде у файлi ===");
        System.out.println(fakeFileContent);

        // === Блок 2: BufferedReader + readLine() ===
        // Сценарiй: класичне читання. readLine() з'їдає '\n' — додаємо самi.
        System.out.println("=== Спосiб 1: BufferedReader (рядок за рядком) ===");
        StringBuilder collected = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new StringReader(fakeFileContent))) {
            String line;
            while ((line = br.readLine()) != null) {
                collected.append(line).append('\n');
            }
        }
        System.out.println("Зiбрано в один рядок (довжина " + collected.length() + "):");
        System.out.println(collected);

        // === Блок 3: Files.readString() — Java 11+ ===
        // Сценарiй: у реальному проєктi пишемо так, бо коротко i ясно.
        System.out.println("=== Спосiб 2: Files.readString() (як у проєктi) ===");
        System.out.println("Path path = Path.of(\"folder/textFile1.txt\");");
        System.out.println("String text = Files.readString(path); // одним рядком!");
        System.out.println("// Зберiгає всi '\\n', табуляцiї, пробiли — формат не псується.");

        // === Блок 4: Files.readAllLines() — список рядкiв ===
        // Сценарiй: коли треба обробити кожен рядок окремо.
        System.out.println();
        System.out.println("=== Спосiб 3: Files.readAllLines() (список) ===");
        Path tmp = Files.createTempFile("L25_demo", ".txt");
        Files.writeString(tmp, fakeFileContent);
        List<String> lines = Files.readAllLines(tmp);
        for (int i = 0; i < lines.size(); i++) {
            System.out.println("[" + i + "] " + lines.get(i));
        }
        Files.deleteIfExists(tmp);

        System.out.println();

        // === Блок 5: Що вибрати для проєкту? ===
        System.out.println("=== Що вибрати? ===");
        System.out.println("Малий файл (текст)    -> Files.readString() — мiнiмум коду");
        System.out.println("Великий файл          -> BufferedReader + readLine() — економить пам'ять");
        System.out.println("Треба номер рядка     -> Files.readAllLines() — є iндекси");
    }
}
