package ua.com.javarush.jsquad.m1.example02_tcp_echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * СЕРВЕР для прикладу 02 — «луна» (echo): що прислали, те й повертає великими літерами.
 *
 * <p>Можна запускати двома способами:</p>
 * <ul>
 *   <li>окремо — натисніть ▶ біля {@code main()} у цьому файлі, а потім запустіть {@link EchoClient};</li>
 *   <li>разом — запустіть {@link Example02_TcpEcho}, він підніме і сервер, і клієнта.</li>
 * </ul>
 */
public class EchoServer {

    /** Порт, який займе сервер. Числа до 1024 зарезервовані системою — беремо більше. */
    public static final int PORT = 7002;

    public static void main(String[] args) throws IOException {
        start();
    }

    /** Обслуговує ОДНОГО клієнта і завершується. */
    public static void start() throws IOException {

        // === 1. Займаємо порт ===
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[сервер] слухаю порт " + PORT + ", чекаю на клієнта...");

            // === 2. accept() блокує потік, доки хтось не підключиться ===
            // Коли клієнт прийшов — метод створює окремий Socket для розмови саме з ним.
            try (Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                 PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8), true)) {

                System.out.println("[сервер] клієнт підключився: " + socket.getRemoteSocketAddress());

                // === 3. Читаємо рядки, поки клієнт не скаже "bye" ===
                String request;
                while ((request = in.readLine()) != null) {         // readLine() теж чекає на дані
                    System.out.println("[сервер] отримав : " + request);

                    if (request.equalsIgnoreCase("bye")) {
                        out.println("Бувай!");                     // println + autoFlush = дані пішли одразу
                        System.out.println("[сервер] клієнт попрощався");
                        break;
                    }

                    out.println(request.toUpperCase());             // ось і вся "робота" сервера
                }
            }

            System.out.println("[сервер] з'єднання закрито, роботу завершено");
        }
    }
}
