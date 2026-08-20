package ua.com.javarush.jsquad.m1.example02_tcp_echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * КЛІЄНТ для прикладу 02. Надсилає серверу кілька фраз і друкує відповіді.
 *
 * <p>Замість вводу з клавіатури беремо готовий масив фраз — приклад має працювати
 * без участі користувача.</p>
 */
public class EchoClient {

    public static void main(String[] args) throws IOException {
        talk();
    }

    public static void talk() throws IOException {

        // === 1. Підключаємось: адреса + порт ===
        // Тут же відбувається "рукостискання" TCP — з'єднання встановлено ще до першого байта.
        try (Socket socket = new Socket("localhost", EchoServer.PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8), true)) {

            System.out.println("[клієнт] підключився до " + socket.getRemoteSocketAddress());

            // === 2. Обмін: надіслали рядок — прочитали відповідь ===
            String[] messages = {"привіт, сервере", "як справи?", "сокети — це просто", "bye"};

            for (String message : messages) {
                out.println(message);                        // надсилаємо
                System.out.println("[клієнт] надіслав : " + message);

                String answer = in.readLine();               // ЧЕКАЄМО на відповідь
                System.out.println("[клієнт] відповідь: " + answer);
            }
        }

        // === 3. Вихід із try-with-resources закриває сокет ===
        System.out.println("[клієнт] з'єднання закрито");
    }
}
