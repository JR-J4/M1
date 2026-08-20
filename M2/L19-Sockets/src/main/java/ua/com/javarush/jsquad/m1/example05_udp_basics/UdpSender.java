package ua.com.javarush.jsquad.m1.example05_udp_basics;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * ВІДПРАВНИК датаграм (UDP) для прикладу 05.
 *
 * <p>Відправник не підключається і нічого не чекає: склав пакет — кинув у мережу.
 * Чи дійшов пакет, чи ні — він не дізнається.</p>
 */
public class UdpSender {

    public static void main(String[] args) throws IOException {
        send();
    }

    public static void send() throws IOException {

        // === 1. DatagramSocket без порту — порт для себе видасть операційна система ===
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");

            System.out.println("[відправник] мій порт: " + socket.getLocalPort() + " (видала ОС)");

            String[] messages = {"привіт по UDP", "мене ніхто не підтверджує", "зате швидко", "bye"};

            for (String message : messages) {
                // === 2. Пакет = дані + скільки байтів + КУДИ (адреса і порт) ===
                byte[] data = message.getBytes(UTF_8);
                DatagramPacket packet = new DatagramPacket(data, data.length, address, UdpReceiver.PORT);

                // === 3. send() не блокує і нічого не перевіряє ===
                socket.send(packet);
                System.out.println("[відправник] кинув у мережу: " + message);

                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("[відправник] усе надіслано (чи дійшло — не знаю)");
    }
}
