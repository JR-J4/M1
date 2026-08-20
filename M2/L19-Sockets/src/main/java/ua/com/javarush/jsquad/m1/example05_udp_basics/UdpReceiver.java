package ua.com.javarush.jsquad.m1.example05_udp_basics;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * ОДЕРЖУВАЧ датаграм (UDP) для прикладу 05.
 *
 * <p>Зверніть увагу: тут немає ніякого {@code accept()} і ніякого «з'єднання».
 * Одержувач просто займає порт і чекає, чи впаде туди якийсь пакет. Хто його
 * надіслав — стає відомо лише з самого пакета.</p>
 */
public class UdpReceiver {

    public static final int PORT = 7005;

    /** Розмір «конверта». Усе, що не вмістилось, буде втрачено — див. приклад 06. */
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        receive();
    }

    /** Приймає пакети, поки не отримає слово "bye". */
    public static void receive() throws IOException {

        // === 1. Займаємо порт. Це весь "сервер" в UDP ===
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("[одержувач] чекаю датаграми на порті " + PORT);

            while (true) {
                // === 2. Готуємо порожній пакет — буфер, куди складати байти ===
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // === 3. receive() блокує, як accept() у TCP ===
                socket.receive(packet);

                // === 4. Витягаємо текст: рівно getLength() байтів, не весь буфер! ===
                String text = new String(packet.getData(), packet.getOffset(), packet.getLength(), UTF_8);

                System.out.println("[одержувач] від " + packet.getAddress().getHostAddress()
                        + ":" + packet.getPort()
                        + " (" + packet.getLength() + " байт) -> " + text);

                if (text.equalsIgnoreCase("bye")) {
                    System.out.println("[одержувач] отримав 'bye', завершую роботу");
                    break;
                }
            }
        }
    }
}
