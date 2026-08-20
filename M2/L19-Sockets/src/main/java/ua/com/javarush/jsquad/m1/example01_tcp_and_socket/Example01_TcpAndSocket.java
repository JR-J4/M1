package ua.com.javarush.jsquad.m1.example01_tcp_and_socket;

import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Модуль 2. Рівень 19. Sockets
 * <hr>
 * <h3>Тема: TCP і що таке сокет</h3>
 *
 * <p><b>TCP (Transmission Control Protocol)</b> — мережевий протокол, який не втрачає
 * дані під час передачі: він запитує підтвердження отримання у приймаючої сторони
 * і, за потреби, надсилає дані повторно. Перш ніж почати обмін, TCP обов'язково
 * <b>встановлює з'єднання</b>. Ціна надійності — швидкість: TCP повільніший за UDP.</p>
 *
 * <p><b>Socket</b> в перекладі з англійської — це <i>розетка</i>. IP-адреса — це
 * унікальний номер комп'ютера, а IP-адреса + порт — унікальний номер конкретної
 * «квартири» в цьому комп'ютері, яку може зайняти програма. Саме така пара і
 * називається сокетом.</p>
 *
 * <h4>Два класи в Java:</h4>
 * <pre>
 *   ServerSocket server = new ServerSocket(7001);          // СЕРВЕР: лише порт
 *   Socket client = new Socket("localhost", 7001);         // КЛІЄНТ: адреса + порт
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> IP-адреса — це номер будинку, порт — номер квартири.
 * Щоб надіслати листа, потрібні обидва числа. І в одній квартирі може мешкати лише
 * один мешканець: якщо порт вже зайнятий однією програмою, друга туди не заселиться.</p>
 *
 * <p><b>Реальне застосування:</b> усе, що ви робите в інтернеті, — це сокети.
 * Браузер відкриває сокет на порт 80/443 сайту, пошта — на 25/587, база даних
 * PostgreSQL слухає порт 5432.</p>
 */
public class Example01_TcpAndSocket {

    private static final int PORT = 7001;

    public static void main(String[] args) throws Exception {

        // === 1. IP-адреса — унікальний номер комп'ютера ===
        // Сценарій: перш ніж кудись підключатися, з'ясуємо власні "координати".
        InetAddress localhost = InetAddress.getByName("localhost");
        InetAddress loopback = InetAddress.getLoopbackAddress();

        System.out.println("1. Адреси, з якими ми будемо працювати:");
        System.out.println("   localhost        -> " + localhost.getHostAddress());
        System.out.println("   loopback         -> " + loopback.getHostAddress());
        System.out.println("   ім'я комп'ютера  -> " + InetAddress.getLocalHost().getHostName());
        System.out.println("   (127.0.0.1 — це 'сам себе'. Ідеально, щоб тренуватися без мережі.)");

        System.out.println();

        // === 2. Сокет = IP + порт, і порт може зайняти лише ОДНА програма ===
        // Сценарій: сервер "заселяється в квартиру" номер 7001.
        try (ServerSocket server = new ServerSocket(PORT)) {

            System.out.println("2. Серверний сокет зайняв порт:");
            System.out.println("   порт          -> " + server.getLocalPort());
            System.out.println("   повна адреса  -> " + server.getLocalSocketAddress());

            // Спробуємо зайняти той самий порт ще раз — квартира вже зайнята.
            try (ServerSocket duplicate = new ServerSocket(PORT)) {
                System.out.println("   ...цього рядка не буде: " + duplicate);
            } catch (BindException e) {
                System.out.println("   друга спроба зайняти 7001 -> BindException (порт вже зайнятий)");
            }

            System.out.println();

            // === 3. З'єднання — це ДВА сокети: клієнтський і серверний ===
            // Сценарій: сервер чекає на дзвінок, клієнт дзвонить.
            // Сервер працює в окремому потоці, бо accept() блокує виконання.
            System.out.println("3. Встановлюємо з'єднання:");

            Thread serverThread = new Thread(() -> {
                try (Socket accepted = server.accept()) {          // чекає на клієнта
                    System.out.println("   [сервер] прийняв з'єднання");
                    System.out.println("   [сервер] мій сокет      : " + accepted.getLocalSocketAddress());
                    System.out.println("   [сервер] сокет клієнта  : " + accepted.getRemoteSocketAddress());
                } catch (Exception e) {
                    System.out.println("   [сервер] помилка: " + e.getMessage());
                }
            });
            serverThread.start();

            // Клієнтський сокет: тут потрібні І адреса, І порт.
            try (Socket client = new Socket("localhost", PORT)) {
                serverThread.join();                                // хай сервер додрукує свої рядки
                System.out.println("   [клієнт] мій сокет      : " + client.getLocalSocketAddress());
                System.out.println("   [клієнт] сокет сервера  : " + client.getRemoteSocketAddress());
                System.out.println("   [клієнт] з'єднано?      : " + client.isConnected());
            }

            System.out.println();
            System.out.println("   Зверніть увагу: порт сервера — 7001 (ми його задали),");
            System.out.println("   а порт клієнта операційна система видала випадковий.");
        }

        System.out.println();

        // === 4. Життєвий цикл TCP-з'єднання ===
        System.out.println("4. Порядок дій у TCP завжди однаковий:");
        System.out.println("   СЕРВЕР: new ServerSocket(порт) -> accept() -> обмін даними -> close()");
        System.out.println("   КЛІЄНТ: new Socket(адреса, порт)          -> обмін даними -> close()");
        System.out.println();
        System.out.println("   Головне з лекції: сервер не 'шукає' клієнтів — він ЧЕКАЄ на них.");
    }
}
