package ua.com.javarush.jsquad.m1;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекція 24: Потоки введення-виведення.
 * <p>
 * Тема: Клас OutputStream — запис байтiв.
 * <p>
 * OutputStream — абстрактний клас для всiх вихiдних байтових потокiв.
 * Основнi методи:
 * <ul>
 *   <li>{@code void write(int b)} — пише один байт</li>
 *   <li>{@code void write(byte[] buffer)} — пише масив байтiв</li>
 *   <li>{@code void write(byte[] buffer, int off, int len)} — частину масиву</li>
 *   <li>{@code void flush()} — скидає буфер у потiк</li>
 *   <li>{@code void close()} — закриває потiк</li>
 * </ul>
 * <p>
 * Аналогія: писар записує лист. Можна писати по літері, фразами або
 * взагалі копіювати готовий шматок (масив).
 * <p>
 * Реальне застосування: запис файлiв, збереження картинок, експорт даних,
 * вiдправка повiдомлень у мережу.
 */
public class Example03_OutputStream {

    public static void main(String[] args) throws IOException {
        // === Блок 1: Запис у пам'ять (ByteArrayOutputStream) ===
        // Сценарiй: збираємо данi у пам'ятi перед вiдправкою (наприклад, формуємо JSON).
        System.out.println("=== write(int) — по одному байту ===");
        ByteArrayOutputStream memory = new ByteArrayOutputStream();
        memory.write('J');
        memory.write('A');
        memory.write('V');
        memory.write('A');
        System.out.println("Накопичено в пам'ятi: " + memory.toString());

        System.out.println();

        // === Блок 2: write(byte[]) — масив одразу ===
        // Сценарiй: маємо готовий рядок, хочемо записати його повнiстю.
        System.out.println("=== write(byte[]) — масив ===");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] greeting = "Привiт, JavaRush!".getBytes();
        buffer.write(greeting);
        System.out.println("Записано " + greeting.length + " байтiв");
        System.out.println("Результат: " + buffer);

        System.out.println();

        // === Блок 3: write(byte[], off, len) — частина масиву ===
        // Сценарiй: маємо великий буфер, але хочемо записати лише його шматок.
        System.out.println("=== write(byte[], off, len) — частина масиву ===");
        ByteArrayOutputStream part = new ByteArrayOutputStream();
        byte[] data = "[HEADER]MainContent[FOOTER]".getBytes();
        // Беремо лише "MainContent" (з 8-го байту, 11 байтiв)
        part.write(data, 8, 11);
        System.out.println("Записано лише частину: " + part);

        System.out.println();

        // === Блок 4: Запис у файл через FileOutputStream ===
        // Сценарiй: зберiгаємо звiт у файл на диску.
        System.out.println("=== FileOutputStream — запис у файл ===");
        Path tempFile = Files.createTempFile("jsquad-l23-out-", ".txt");

        try ( OutputStream fileOut = new FileOutputStream(tempFile.toFile()) ) {
            fileOut.write("Звiт за день:\n".getBytes());
            fileOut.write("Продано: 10 одиниць\n".getBytes());
            fileOut.write("Виручка: 2500 грн".getBytes());


        }

        System.out.println("Файл створено: " + tempFile);
        System.out.println("Вмiст файлу:");
        System.out.println(Files.readString(tempFile));

        System.out.println();

        // === Блок 5: Що робить flush() ===
        // Сценарiй: данi можуть тимчасово зберiгатися в буферi.
        // flush() гарантує, що вони доїдуть до призначення.
        System.out.println("=== flush() — скидаємо буфер ===");
        System.out.println("write() може зберiгати данi в буферi для оптимiзацiї.");
        System.out.println("flush() форсує запис: 'все, що в буферi — пиши негайно!'");
        System.out.println("close() автоматично викликає flush() перед закриттям.");

        Files.deleteIfExists(tempFile);
    }
}
