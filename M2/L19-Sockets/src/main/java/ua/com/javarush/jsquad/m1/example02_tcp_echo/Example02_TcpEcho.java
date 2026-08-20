package ua.com.javarush.jsquad.m1.example02_tcp_echo;

/**
 * Модуль 2. Рівень 19. Sockets
 * <hr>
 * <h3>Тема: Найпростіший TCP-сервер і TCP-клієнт («луна»)</h3>
 *
 * <p>Мінімальна пара «сервер + клієнт». Сервер приймає рядок і повертає його
 * ВЕЛИКИМИ літерами. Це класичний echo-сервер — з нього починають усі, хто вчить сокети.</p>
 *
 * <h4>Скелет, який варто запам'ятати:</h4>
 * <pre>
 *   // СЕРВЕР
 *   ServerSocket serverSocket = new ServerSocket(7002);
 *   Socket socket = serverSocket.accept();      // чекає на клієнта
 *
 *   // КЛІЄНТ
 *   Socket socket = new Socket("localhost", 7002);
 *
 *   // ОБИДВА однаково читають і пишуть:
 *   BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 *   PrintWriter    out = new PrintWriter(socket.getOutputStream(), true);   // true = autoFlush
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> телефонна розмова. Сервер — це людина, яка сидить біля
 * телефону і чекає на дзвінок. Клієнт набирає номер (адреса + порт). Далі говорять
 * по черзі: сказав — послухав. Хто перший поклав слухавку, той і завершив розмову.</p>
 *
 * <p><b>Реальне застосування:</b> так само влаштований будь-який текстовий протокол —
 * HTTP, SMTP, Redis. Браузер надсилає {@code GET /index.html} рядком, а сервер
 * рядками відповідає. Різниця лише в тому, ЩО написано в цих рядках.</p>
 *
 * <h4>Як запускати:</h4>
 * <p>Цей клас підіймає сервер в окремому потоці й одразу запускає клієнта — зручно
 * для одного кліку. Щоб відчути "справжню" мережу, запустіть окремо {@link EchoServer},
 * а потім окремо {@link EchoClient} — це будуть два різні процеси.</p>
 */
public class Example02_TcpEcho {

    public static void main(String[] args) throws Exception {

        System.out.println("=== Приклад 02: TCP echo (сервер повертає текст ВЕЛИКИМИ літерами) ===");
        System.out.println();

        // === 1. Сервер живе в окремому потоці ===
        // Чому в потоці? Бо accept() блокує: у одному потоці клієнт ніколи б не дочекався черги.
        Thread server = new Thread(() -> {
            try {
                EchoServer.start();
            } catch (Exception e) {
                System.out.println("[сервер] помилка: " + e.getMessage());
            }
        }, "echo-server");
        server.start();

        // === 2. Даємо серверу мить, щоб він встиг зайняти порт ===
        Thread.sleep(300);

        // === 3. Запускаємо клієнта в головному потоці ===
        EchoClient.talk();

        // === 4. Чекаємо, поки сервер акуратно завершиться ===
        server.join();

        System.out.println();
        System.out.println("Головне: сервер і клієнт — це просто два сокети, які читають");
        System.out.println("і пишуть у звичайні потоки введення/виведення (як у файл).");
    }
}
