package ua.com.javarush.jsquad.m1.example08_real_world_chat;

import ua.com.javarush.jsquad.m1.chat.ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * КЛІЄНТ чату для прикладу 08.
 *
 * <p>Головна ідея: клієнту потрібні ДВА потоки. Один читає те, що приходить із
 * сервера (бо повідомлення можуть прилетіти будь-коли), інший — надсилає своє.
 * У справжньому чаті другим потоком був би ввід із клавіатури; у нас — готовий
 * масив реплік.</p>
 */
public class ChatClient {

    public static void main(String[] args) throws Exception {
        talk("Гість", new String[]{"привіт усім", "як настрій?"});
    }

    public static void talk(String name, String[] messages) throws IOException {

        try (Socket socket = new Socket("localhost", ChatServer.PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8), true)) {

            // === 1. Представляємось — сервер чекає ім'я першим рядком ===
            out.println(name);

            // === 2. Окремий потік ТІЛЬКИ читає вхідні повідомлення ===
            Thread listener = new Thread(() -> {
                try {
                    String incoming;
                    while ((incoming = in.readLine()) != null) {
                        System.out.println("   [екран " + name + "] " + incoming);
                    }
                } catch (IOException e) {
                    // сокет закрили — це нормальне завершення
                }
            }, "listener-" + name);
            listener.setDaemon(true);
            listener.start();

            // === 3. Головний потік надсилає свої репліки ===
            for (String message : messages) {
                sleep(600);
                out.println(message);
            }

            sleep(600);
            out.println("bye");                    // ввічливо виходимо
            sleep(300);                            // хай останні повідомлення долетять на екран
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
