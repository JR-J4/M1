package ua.com.javarush.jsquad.m1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Лекція 26: Потоки введення-виведення. Частина 3 (NIO).
 * <p>
 * Тема: Buffer i FileChannel — серце NIO.
 * <p>
 * <b>Buffer</b> — контейнер фiксованого розмiру для блокiв даних. Має:
 * <ul>
 *   <li>{@code capacity} — повний розмiр</li>
 *   <li>{@code position} — поточна позицiя</li>
 *   <li>{@code limit} — межа читання/запису</li>
 * </ul>
 * <p>
 * <b>FileChannel</b> — двостороннiй канал зв'язку з файлом. Можна:
 * <ul>
 *   <li>Читати в буфер: {@code channel.read(buffer)}</li>
 *   <li>Писати з буфера: {@code channel.write(buffer)}</li>
 *   <li>Рухатися по файлу: {@code channel.position(N)}</li>
 * </ul>
 * <p>
 * Ключовий метод буфера — {@code flip()}: переключає буфер з режиму
 * запису в режим читання (limit = position, position = 0).
 * <p>
 * Аналогiя: канал — конвеєрна стрiчка. Буфер — ящик на стрiчцi. Стрiчка
 * рухає ящик у обидва боки, ящик мiстить порцiю товару.
 * <p>
 * Реальне застосування: робота з великими файлами, копiювання за допомогою
 * transferTo (швидше за byte-by-byte), random access по файлу.
 */
public class Example09_BufferFileChannel {

    public static void main(String[] args) throws IOException {
        Path file = Files.createTempFile("nio-", ".txt");

        // === Блок 1: запис у файл через FileChannel + ByteBuffer ===
        // Сценарiй: пишемо текст у файл через NIO канал.
        System.out.println("=== Запис через FileChannel ===");
        try (FileChannel channel = FileChannel.open(file, StandardOpenOption.WRITE)) {
            ByteBuffer buffer = ByteBuffer.allocate(64);
            buffer.put("JSquad NIO лекцiя 26\n".getBytes(StandardCharsets.UTF_8));
            buffer.put("Buffer + FileChannel".getBytes(StandardCharsets.UTF_8));

            buffer.flip();  // ⚠ переключаємо з запису на читання!
            int written = channel.write(buffer);
            System.out.println("Записано байт: " + written);
        }

        System.out.println();

        // === Блок 2: читання файлу через FileChannel ===
        // Сценарiй: читаємо файл у буфер i декодуємо в рядок.
        System.out.println("=== Читання через FileChannel ===");
        try (FileChannel channel = FileChannel.open(file, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(128);
            int read = channel.read(buffer);
            System.out.println("Прочитано байт: " + read);

            buffer.flip();  // готуємо буфер до читання
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            System.out.println("Вмiст: " + new String(data, StandardCharsets.UTF_8));
        }

        System.out.println();

        // === Блок 3: position / limit / capacity у буферi ===
        // Сценарiй: розбираємось зi станом буфера крок за кроком.
        System.out.println("=== Стан буфера ===");
        ByteBuffer buf = ByteBuffer.allocate(10);
        System.out.println("Створено: cap=" + buf.capacity() + " pos=" + buf.position() + " lim=" + buf.limit());

        buf.put((byte) 1).put((byte) 2).put((byte) 3);
        System.out.println("Пiсля 3х put: cap=" + buf.capacity() + " pos=" + buf.position() + " lim=" + buf.limit());

        buf.flip();
        System.out.println("Пiсля flip: cap=" + buf.capacity() + " pos=" + buf.position() + " lim=" + buf.limit());

        buf.get(); // прочитали 1 байт
        System.out.println("Пiсля get: cap=" + buf.capacity() + " pos=" + buf.position() + " lim=" + buf.limit());

        System.out.println();

        // === Блок 4: random access — стрибок у середину файлу ===
        // Сценарiй: треба прочитати з певної позицiї (як seek у файлi).
        System.out.println("=== Random access (channel.position) ===");
        try (FileChannel channel = FileChannel.open(file, StandardOpenOption.READ)) {
            channel.position(7);  // стрибаємо на 7-й байт
            ByteBuffer chunk = ByteBuffer.allocate(10);
            channel.read(chunk);
            chunk.flip();
            byte[] piece = new byte[chunk.remaining()];
            chunk.get(piece);
            System.out.println("Шматок з позицiї 7: '" + new String(piece, StandardCharsets.UTF_8) + "'");
        }

        System.out.println();

        // === Блок 5: copy через transferTo — найшвидший спосiб ===
        // Сценарiй: копiюємо файл — NIO робить це на рiвнi ОС (zero-copy).
        System.out.println("=== transferTo (швидке копiювання) ===");
        Path copy = Files.createTempFile("nio-copy-", ".txt");
        try (FileChannel src = FileChannel.open(file, StandardOpenOption.READ);
             FileChannel dst = FileChannel.open(copy, StandardOpenOption.WRITE)) {

            long transferred = src.transferTo(0, src.size(), dst);
            System.out.println("Перенесено байт: " + transferred);
        }
        System.out.println("Копiя:\n" + Files.readString(copy));

        // прибираємо
        Files.delete(file);
        Files.delete(copy);
    }
}
