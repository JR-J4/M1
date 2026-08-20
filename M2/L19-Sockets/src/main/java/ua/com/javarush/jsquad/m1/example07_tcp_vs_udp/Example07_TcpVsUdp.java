package ua.com.javarush.jsquad.m1.example07_tcp_vs_udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Модуль 2. Рівень 19. Sockets
 * <hr>
 * <h3>Тема: TCP чи UDP?</h3>
 *
 * <p>З лекції: відповісти на це питання складно, бо все залежить від того, яку
 * роботу ми виконуємо і який тип даних надсилаємо. <b>UDP</b> кращий для онлайн-ігор,
 * бо дозволяє працювати без затримок. <b>TCP</b> кращий, коли ми передаємо фотографії,
 * відео, файли — бо гарантує, що дані дійдуть правильними.</p>
 *
 * <p>У цьому прикладі ми не просто читаємо таблицю, а <b>вимірюємо</b> ціну надійності:
 * надсилаємо однакову кількість повідомлень через TCP (з підтвердженням) і через
 * UDP (без підтвердження) — і дивимось на секундомір.</p>
 *
 * <p><b>Аналогія з життя:</b> TCP — рекомендований лист із повідомленням про
 * вручення: надійно, але треба стояти в черзі й чекати на квитанцію.
 * UDP — крикнути в натовп: миттєво, але хтось міг і не почути.</p>
 *
 * <p><b>Реальне застосування:</b> у відеозвінку голос іде по UDP (краще коротке
 * "хрип", ніж півсекундна затримка), а текст чату і файли в тому ж застосунку —
 * по TCP (жодна літера не має зникнути).</p>
 */
public class Example07_TcpVsUdp {

    private static final int PORT = 7007;
    private static final int COUNT = 300;          // скільки повідомлень надсилаємо

    public static void main(String[] args) throws Exception {

        // === 1. Головні відмінності — коротко ===
        System.out.println("1. TCP і UDP поруч:");
        System.out.println("   ┌──────────────────────┬─────────────────────┬────────────────────────┐");
        System.out.println("   │                      │ TCP                 │ UDP                    │");
        System.out.println("   ├──────────────────────┼─────────────────────┼────────────────────────┤");
        System.out.println("   │ з'єднання            │ обов'язкове         │ не потрібне            │");
        System.out.println("   │ підтвердження        │ так, і повторна     │ немає                  │");
        System.out.println("   │                      │ відправка           │                        │");
        System.out.println("   │ порядок даних        │ гарантований        │ не гарантований        │");
        System.out.println("   │ швидкість            │ нижча               │ вища                   │");
        System.out.println("   │ broadcast/multicast  │ немає               │ є                      │");
        System.out.println("   │ класи в Java         │ Socket,             │ DatagramSocket,        │");
        System.out.println("   │                      │ ServerSocket        │ DatagramPacket         │");
        System.out.println("   └──────────────────────┴─────────────────────┴────────────────────────┘");
        System.out.println();

        // === 2. Скільки коштує надійність TCP ===
        // Сценарій: надсилаємо COUNT повідомлень і на кожне ЧЕКАЄМО відповідь сервера.
        System.out.println("2. TCP: " + COUNT + " повідомлень із підтвердженням від сервера");
        long tcpMillis = measureTcp();
        System.out.println("   час: " + tcpMillis + " мс   (усі " + COUNT + " дійшли — інакше TCP перепитав би)");
        System.out.println();

        // === 3. Та сама кількість, але без підтверджень ===
        System.out.println("3. UDP: " + COUNT + " датаграм без жодного підтвердження");
        int[] result = measureUdp();
        System.out.println("   час: " + result[0] + " мс   (дійшло " + result[1] + " з " + COUNT + ")");
        System.out.println();

        System.out.println("   Різниця не в 'швидкості мережі', а в роботі: TCP на кожне");
        System.out.println("   повідомлення чекав відповідь, UDP лише кидав пакети.");
        System.out.println("   І це на одному комп'ютері — у реальній мережі розрив ще більший.");
        System.out.println();

        // === 4. Де що використовують ===
        System.out.println("4. Де застосовують:");
        System.out.println("   TCP -> електронна пошта, передача файлів, перегляд веб-сторінок,");
        System.out.println("          банківські операції, бази даних");
        System.out.println("   UDP -> онлайн-ігри, потокове відео, відеочати, DNS,");
        System.out.println("          телеметрія з датчиків");
        System.out.println();
        System.out.println("   Правило: втрата даних недопустима -> TCP. Затримка недопустима -> UDP.");
    }

    /** TCP: клієнт надсилає рядок і чекає на відповідь сервера. Повертає витрачений час. */
    private static long measureTcp() throws Exception {

        ServerSocket serverSocket = new ServerSocket(PORT);

        // Сервер-луна: що прийшло, те й повертає.
        Thread server = new Thread(() -> {
            try (Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                 PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8), true)) {

                String line;
                while ((line = in.readLine()) != null) {
                    out.println(line);                       // ось воно, "підтвердження"
                }
            } catch (Exception e) {
                System.out.println("   [сервер] " + e.getMessage());
            }
        }, "tcp-echo");
        server.start();

        long start;
        try (Socket client = new Socket("localhost", PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), UTF_8), true)) {

            start = System.currentTimeMillis();
            for (int i = 1; i <= COUNT; i++) {
                out.println("повідомлення №" + i);
                in.readLine();                               // ЧЕКАЄМО — саме тут витрачається час
            }
        }
        long spent = System.currentTimeMillis() - start;

        server.join();
        serverSocket.close();
        return spent;
    }

    /** UDP: відправник шле датаграми і нічого не чекає. Повертає {час, скільки дійшло}. */
    private static int[] measureUdp() throws Exception {

        DatagramSocket receiverSocket = new DatagramSocket(PORT);
        receiverSocket.setSoTimeout(500);                     // 0.5 с тиші = потік завершується
        AtomicInteger received = new AtomicInteger();

        Thread receiver = new Thread(() -> {
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(new byte[512], 512);
                    receiverSocket.receive(packet);
                    received.incrementAndGet();
                } catch (SocketTimeoutException e) {
                    return;                                  // більше нічого не летить
                } catch (Exception e) {
                    return;
                }
            }
        }, "udp-receiver");
        receiver.start();

        long start = System.currentTimeMillis();
        try (DatagramSocket sender = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");
            for (int i = 1; i <= COUNT; i++) {
                byte[] data = ("повідомлення №" + i).getBytes(UTF_8);
                sender.send(new DatagramPacket(data, data.length, address, PORT));   // і все
            }
        }
        long spent = System.currentTimeMillis() - start;

        receiver.join();
        receiverSocket.close();
        return new int[]{(int) spent, received.get()};
    }
}
