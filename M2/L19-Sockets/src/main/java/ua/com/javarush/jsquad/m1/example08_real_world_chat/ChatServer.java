package ua.com.javarush.jsquad.m1.example08_real_world_chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * СЕРВЕР чату для прикладу 08.
 *
 * <p>Відрізняється від сервера з прикладу 04 однією важливою деталлю: він <b>пам'ятає</b>
 * усіх підключених клієнтів. Тому повідомлення від одного можна переслати всім іншим.</p>
 *
 * <p>Запущений окремо, працює нескінченно — зупиніть кнопкою Stop.</p>
 */
public class ChatServer {

    public static final int PORT = 7008;

    /**
     * «Труби» до всіх підключених клієнтів.
     * CopyOnWriteArrayList — бо список читають і змінюють різні потоки одночасно.
     */
    private static final List<PrintWriter> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {
        start();
    }

    public static void start() throws IOException {

        // === 1. Звичайний цикл accept + потік на клієнта ===
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[сервер] чат працює на порті " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                Thread worker = new Thread(() -> handle(socket), "chat-worker");
                worker.setDaemon(true);
                worker.start();
            }
        }
    }

    /** Обслуговує одного учасника чату. */
    private static void handle(Socket socket) {

        PrintWriter out = null;
        String name = "невідомий";

        try (socket;
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8))) {

            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8), true);

            // === 2. Перший рядок — це ім'я учасника (найпростіший "протокол") ===
            name = in.readLine();
            clients.add(out);

            System.out.println("[сервер] нове підключення: " + name + ". Усього в чаті: " + clients.size());
            broadcast("*** до чату приєднується " + name + " ***", out);

            // === 3. Кожен прочитаний рядок пересилаємо всім іншим ===
            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("bye")) {
                    break;
                }
                System.out.println("[сервер] " + name + ": " + message);
                broadcast(name + ": " + message, out);
            }

        } catch (IOException e) {
            System.out.println("[сервер] зв'язок із " + name + " обірвався: " + e.getMessage());
        } finally {
            // === 4. Учасник пішов — прибираємо його зі списку ===
            clients.remove(out);
            broadcast("*** " + name + " покидає чат ***", out);
            System.out.println("[сервер] відключення: " + name + ". Залишилось у чаті: " + clients.size());
        }
    }

    /** Надсилає текст усім, крім автора. */
    private static void broadcast(String text, PrintWriter author) {
        for (PrintWriter client : clients) {
            if (client != author) {
                client.println(text);
            }
        }
    }
}
