package ua.com.javarush.jsquad.m1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * Лекція 24: Потоки введення-виведення.
 * <p>
 * Тема: Ланцюжки потокiв (stream chains) i клас InputStreamReader.
 * <p>
 * Потiк може читати данi не лише з джерела, а й з iншого потоку!
 * Це називається "ланцюжок потокiв":
 * <pre>
 *   Your code  &lt;-  Reader  &lt;-  InputStream  &lt;-  File/Network/Memory
 * </pre>
 * <p>
 * InputStreamReader — мiст мiж байтами та символами:
 * <ul>
 *   <li>Як джерело приймає InputStream</li>
 *   <li>На виходi дає Reader (читає char)</li>
 *   <li>Перетворює байти у символи з вказаним кодуванням (UTF-8, тощо)</li>
 * </ul>
 * <p>
 * Аналогія: дзвонять по радiозв'язку (байти) — потрiбен перекладач,
 * щоб ви чули українську мову (символи). InputStreamReader — це той перекладач.
 * <p>
 * Реальне застосування: читання HTTP-вiдповiдей, файлiв з рiзними кодуваннями,
 * мережевого трафiку, вмiсту архiвiв.
 */
public class Example06_StreamChains {

    public static void main(String[] args) throws IOException {
        // === Блок 1: InputStreamReader — байти у символи ===
        // Сценарiй: маємо InputStream з UTF-8 байтами, треба читати як текст.
        System.out.println("=== InputStreamReader: byte -> char ===");
        byte[] utf8Bytes = "Привiт!".getBytes(StandardCharsets.UTF_8);
        System.out.println("На входi " + utf8Bytes.length + " байтiв (UTF-8)");

        InputStream byteStream = new ByteArrayInputStream(utf8Bytes);
        Reader charReader = new InputStreamReader(byteStream, StandardCharsets.UTF_8);

        StringBuilder result = new StringBuilder();
        int c;
        while ((c = charReader.read()) != -1) {
            result.append((char) c);
        }
        charReader.close();
        System.out.println("На виходi: '" + result + "' (" + result.length() + " символiв)");

        System.out.println();

        // === Блок 2: Що буде без InputStreamReader ===
        // Сценарiй: спроба читати байти "як символи" — i що з цього вийде.
        System.out.println("=== Без перекладача (читаємо байти напряму) ===");
        InputStream rawBytes = new ByteArrayInputStream("Їжак".getBytes(StandardCharsets.UTF_8));
        System.out.print("Байти 'Їжак' через read(): ");
        int b;
        while ((b = rawBytes.read()) != -1) {
            System.out.print(b + " ");
        }
        rawBytes.close();
        System.out.println("\n(8 чисел замiсть 4 символiв — UTF-8 кодує кирилицю по 2 байти)");

        System.out.println();

        // === Блок 3: OutputStreamWriter — символи у байти ===
        // Сценарiй: пишемо текст, а в потiк iдуть байти певного кодування.
        System.out.println("=== OutputStreamWriter: char -> byte ===");
        ByteArrayOutputStream byteSink = new ByteArrayOutputStream();
        Writer charWriter = new OutputStreamWriter(byteSink, StandardCharsets.UTF_8);
        charWriter.write("Київ");
        charWriter.close();

        byte[] encoded = byteSink.toByteArray();
        System.out.println("Текст 'Київ' зайняв " + encoded.length + " байтiв у UTF-8");
        System.out.print("Байти: ");
        for (byte by : encoded) {
            System.out.print((by & 0xFF) + " ");
        }
        System.out.println();

        System.out.println();

        // === Блок 4: Принцип "обгортання" (декоратор) ===
        // Сценарiй: один потiк "огортає" iнший, додаючи функцiональнiсть.
        System.out.println("=== Декоратор: один потiк всередині iншого ===");
        System.out.println("InputStream stream = new ByteArrayInputStream(...);  // джерело");
        System.out.println("Reader reader = new InputStreamReader(stream);      // перетворювач");
        System.out.println("Reader buffer = new BufferedReader(reader);         // буферизатор");
        System.out.println("// Кожен наступний обгортає попереднiй i додає 'фiчу'.");

        System.out.println();

        // === Блок 5: Кодування — чому це важливо ===
        // Сценарiй: одне i те саме слово, рiзнi кодування — рiзна кiлькiсть байтiв.
        System.out.println("=== Одне слово — рiзнi кодування ===");
        String word = "Java";
        System.out.println("'" + word + "' у UTF-8:    " + word.getBytes(StandardCharsets.UTF_8).length + " байтiв");
        System.out.println("'" + word + "' у UTF-16:   " + word.getBytes(StandardCharsets.UTF_16).length + " байтiв");
        System.out.println();
        String ukWord = "Україна";
        System.out.println("'" + ukWord + "' у UTF-8:  " + ukWord.getBytes(StandardCharsets.UTF_8).length + " байтiв");
        System.out.println("'" + ukWord + "' у UTF-16: " + ukWord.getBytes(StandardCharsets.UTF_16).length + " байтiв");
        System.out.println("Завжди явно вказуйте кодування при роботi з потоками!");
    }
}
