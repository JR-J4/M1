package ua.com.javarush.jsquad.m1.example03_accept_blocking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Модуль 2. Рівень 19. Sockets
 * <hr>
 * <h3>Тема: Метод accept() чекає — і чому це важливо</h3>
 *
 * <p>З лекції (Момент 2): у класі {@code ServerSocket} є метод {@code accept()}, який,
 * якщо його викликати, <b>чекатиме на вхідне з'єднання</b>. Тобто метод буде
 * виконуватися нескінченно довго, доки якийсь клієнтський сокет не спробує до нього
 * звернутися. Тоді {@code accept()} прийме з'єднання, створить об'єкт-сокет для
 * комунікації і поверне цей об'єкт.</p>
 *
 * <h4>Які методи «блокують» потік:</h4>
 * <pre>
 *   serverSocket.accept()   // чекає на КЛІЄНТА
 *   in.readLine()           // чекає на ДАНІ від співрозмовника
 *   packet -> socket.receive(packet)   // те саме в UDP (приклад 05)
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> ви сидите в приймальні й чекаєте на відвідувача.
 * Поки ніхто не зайшов — ви нічого іншого не робите. Саме тому серверу потрібні
 * окремі потоки: один чекає на вхідні дзвінки, інші працюють.</p>
 *
 * <p><b>Реальне застосування:</b> таймаут ({@code setSoTimeout}) обов'язковий у
 * бойовому коді. Без нього програма може «зависнути» назавжди, чекаючи на партнера,
 * який уже впав або якого відрізало мережею.</p>
 */
public class Example03_AcceptBlocking {

    private static final int PORT = 7003;
    private static long start;

    public static void main(String[] args) throws Exception {

        start = System.currentTimeMillis();

        // === 1. accept() чекає стільки, скільки треба ===
        // Сценарій: сервер готовий одразу, а клієнт "запізнюється" на 2 секунди.
        System.out.println("1. Сервер чекає на клієнта, який прийде не одразу:");

        ServerSocket serverSocket = new ServerSocket(PORT);

        Thread serverThread = new Thread(() -> {
            log("[сервер] викликаю accept() — і зупиняюсь тут");
            try (Socket socket = serverSocket.accept()) {              // <-- потік стоїть саме тут
                log("[сервер] accept() ПОВЕРНУВ сокет " + socket.getRemoteSocketAddress());
            } catch (Exception e) {
                log("[сервер] помилка: " + e.getMessage());
            }
        }, "server");
        serverThread.start();

        Thread.sleep(2000);                                            // імітуємо "довго не було клієнтів"
        log("[клієнт] нарешті підключаюсь");
        try (Socket client = new Socket("localhost", PORT)) {
            log("[клієнт] з'єднання встановлено: " + client.isConnected());
        }

        serverThread.join();
        serverSocket.close();

        System.out.println("   Дві секунди сервер не робив НІЧОГО — просто чекав.");
        System.out.println();

        // === 2. Таймаут: скільки чекати — вирішуємо ми ===
        // Сценарій: серверу дозволено чекати лише 1 секунду, а клієнт так і не прийшов.
        System.out.println("2. Те саме, але з обмеженням часу (setSoTimeout):");

        try (ServerSocket withTimeout = new ServerSocket(PORT)) {
            withTimeout.setSoTimeout(1000);                            // мілісекунди
            log("[сервер] чекаю на клієнта, але не довше 1 секунди");
            try {
                withTimeout.accept();
            } catch (SocketTimeoutException e) {
                log("[сервер] SocketTimeoutException — час вийшов, клієнта немає");
            }
        }

        System.out.println("   Без таймауту програма чекала б вічно.");
        System.out.println();

        // === 3. readLine() блокує так само, як accept() ===
        // Сценарій: з'єднання є, але співрозмовник ще думає, що написати.
        System.out.println("3. Читання даних теж чекає:");

        try (ServerSocket serverForRead = new ServerSocket(PORT)) {

            Thread reader = new Thread(() -> {
                try (Socket socket = serverForRead.accept();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream(), UTF_8))) {

                    log("[сервер] клієнт є, викликаю readLine()");
                    String line = in.readLine();                       // <-- знову чекаємо
                    log("[сервер] readLine() повернув: \"" + line + "\"");
                } catch (Exception e) {
                    log("[сервер] помилка: " + e.getMessage());
                }
            }, "reader");
            reader.start();

            try (Socket client = new Socket("localhost", PORT);
                 PrintWriter out = new PrintWriter(
                         new OutputStreamWriter(client.getOutputStream(), UTF_8), true)) {

                log("[клієнт] підключився, але друкую повідомлення 1.5 секунди");
                Thread.sleep(1500);
                out.println("готово, ось мої дані");
                log("[клієнт] надіслав повідомлення");
                reader.join();
            }
        }

        System.out.println();
        System.out.println("Висновок: сокети — це очікування. Тому реальний сервер завжди");
        System.out.println("тримає окремий потік на кожного клієнта (див. приклад 04).");
    }

    /** Друкує повідомлення разом із часом від старту програми — так видно, хто скільки чекав. */
    private static void log(String message) {
        long millis = System.currentTimeMillis() - start;
        System.out.printf("   %5d мс | %s%n", millis, message);
    }
}
