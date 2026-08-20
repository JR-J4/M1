package ua.com.javarush.jsquad.m1.example04_many_clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * СЕРВЕР для прикладу 04 — обслуговує БАГАТО клієнтів одночасно.
 *
 * <p>Ключова ідея: {@code accept()} у циклі приймає з'єднання одне за одним, але
 * саму «розмову» з клієнтом віддає окремому потоку. Тому поки один клієнт думає,
 * інші не стоять у черзі.</p>
 *
 * <p>Запущений окремо, цей сервер працює нескінченно (як і справжній) — зупиніть
 * його кнопкою Stop. Разом з клієнтами його підіймає {@link Example04_ManyClients}.</p>
 */
public class MultiClientServer {

    public static final int PORT = 7004;

    /** Скільком клієнтам ми вже відкрили двері. Atomic — бо звертаються різні потоки. */
    private static final AtomicInteger clientCounter = new AtomicInteger();

    public static void main(String[] args) throws IOException {
        start();
    }

    public static void start() throws IOException {

        // === 1. Один серверний сокет — на всіх клієнтів ===
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[сервер] слухаю порт " + PORT);

            // === 2. Нескінченний цикл прийому з'єднань ===
            while (true) {
                Socket socket = serverSocket.accept();          // чекаємо на наступного
                int number = clientCounter.incrementAndGet();

                // === 3. Розмову віддаємо окремому потоку і одразу вертаємось до accept() ===
                Thread worker = new Thread(() -> serve(socket, number), "клієнт-" + number);
                worker.setDaemon(true);
                worker.start();
            }
        }
    }

    /** Уся робота з одним клієнтом. Виконується у власному потоці. */
    private static void serve(Socket socket, int number) {
        try (socket;                                            // try-with-resources закриє сокет
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8), true)) {

            String name = in.readLine();
            System.out.println("[сервер] клієнт #" + number + " представився: " + name);

            out.println("Вітаю, " + name + "! Ти клієнт номер " + number + ".");

            // Імітуємо роботу, яка займає час (запит у базу, обробка файлу тощо).
            Thread.sleep(500);

            out.println("Твоє замовлення виконано за 500 мс. Обслуговував потік: "
                    + Thread.currentThread().getName());

            System.out.println("[сервер] клієнт #" + number + " обслужений");

        } catch (Exception e) {
            System.out.println("[сервер] клієнт #" + number + " відпав: " + e.getMessage());
        }
    }
}
