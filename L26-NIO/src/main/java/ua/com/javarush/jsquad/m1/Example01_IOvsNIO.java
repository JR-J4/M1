package ua.com.javarush.jsquad.m1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Лекція 26: Потоки введення-виведення. Частина 3 (NIO).
 * <p>
 * Тема: Java IO vs Java NIO — порiвняння двох пiдходiв.
 * <p>
 * Основнi вiдмiнностi:
 * <ul>
 *   <li>IO — потокоорiєнтований (читаємо/пишемо побайтово по черзi)</li>
 *   <li>NIO — буфер-орiєнтований (читаємо одразу блок у буфер)</li>
 *   <li>IO — блокувальний (read() чекає, поки данi не зчитаються)</li>
 *   <li>NIO — неблокувальний (бере те, що є зараз, або нiчого)</li>
 *   <li>NIO має Селектори — один потiк монiторить багато каналiв</li>
 * </ul>
 * <p>
 * Аналогiя: IO — це пити воду iз соломинки (один ковток за раз, по порядку).
 * NIO — це налити склянку (буфер) i пити як хочеш: робити паузи, ковтати
 * бiльше або менше, повернутися i перечитати.
 * <p>
 * Реальне застосування: IO зручний для простих сценарiїв (читання конфiгу,
 * лог-файлу). NIO потрiбен у високонавантажених серверах (один потiк
 * обробляє тисячi з'єднань), при роботi з великими файлами.
 */
public class Example01_IOvsNIO {

    public static void main(String[] args) throws IOException {
        // === Блок 1: IO — потокоорiєнтований пiдхiд ===
        // Сценарiй: читаємо текст символ за символом — як крапля з крана.
        System.out.println("=== IO: потiк символiв ===");
        String text = "JSquad";
        BufferedReader reader = new BufferedReader(new StringReader(text));

        int ch;
        System.out.print("Читаємо: ");
        while ((ch = reader.read()) != -1) {
            System.out.print((char) ch + " ");  // символ за символом
        }
        reader.close();
        System.out.println();

        System.out.println();

        // === Блок 2: NIO — буфер-орiєнтований пiдхiд ===
        // Сценарiй: завантажуємо одразу всi байти в буфер i працюємо з ним.
        System.out.println("=== NIO: буфер ===");
        ByteBuffer buffer = ByteBuffer.wrap("JSquad".getBytes(StandardCharsets.UTF_8));
        System.out.println("Розмiр буфера: " + buffer.capacity());
        System.out.println("Позицiя: " + buffer.position());
        System.out.println("Лiмiт: " + buffer.limit());

        System.out.print("Читаємо вмiст: ");
        while (buffer.hasRemaining()) {
            System.out.print((char) buffer.get() + " ");
        }
        System.out.println();

        System.out.println();

        // === Блок 3: гнучкiсть буфера — можна повернутися назад ===
        // Сценарiй: у потоцi назад не повернешся, а в буферi — легко.
        System.out.println("=== Гнучкiсть буфера ===");
        buffer.rewind();  // скидаємо позицiю на початок
        System.out.println("Перший символ: " + (char) buffer.get());
        System.out.println("Другий символ: " + (char) buffer.get());

        buffer.position(0);  // повертаємось на початок
        System.out.println("Знову перший: " + (char) buffer.get());

        System.out.println();

        // === Блок 4: коли який пiдхiд обирати ===
        System.out.println("=== Коли що ===");
        System.out.println("IO  — невеликi файли, простi сценарiї, послiдовне читання");
        System.out.println("NIO — великi файли, високе навантаження, асинхроннiсть");
        System.out.println("NIO — коли потрiбно рухатися по даних вперед-назад");
    }
}
