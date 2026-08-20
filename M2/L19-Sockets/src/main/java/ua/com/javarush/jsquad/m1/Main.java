package ua.com.javarush.jsquad.m1;

/**
 * Модуль 2. Рівень 19. Sockets — ЗМІСТ ПРИКЛАДІВ
 *
 * <p>Лекція охоплює: протокол TCP, класи {@code Socket} і {@code ServerSocket},
 * протокол UDP разом із {@code DatagramSocket} та {@code DatagramPacket},
 * а також вибір між TCP і UDP.</p>
 *
 * <p>Кожен приклад — самодостатній клас зі своїм {@code main()}.
 * Запускайте їх по черзі (відкрийте файл і натисніть ▶ біля {@code main}).</p>
 *
 * <pre>
 *  №   Тема                                   Клас для запуску
 *  ──────────────────────────────────────────────────────────────────────────────────────
 *  01  TCP і що таке сокет (IP + порт)        example01_tcp_and_socket.Example01_TcpAndSocket
 *  02  Перший сервер і клієнт (echo)          example02_tcp_echo.Example02_TcpEcho
 *  03  accept() чекає, таймаути               example03_accept_blocking.Example03_AcceptBlocking
 *  04  Один сервер — багато клієнтів          example04_many_clients.Example04_ManyClients
 *  05  UDP: DatagramSocket і DatagramPacket   example05_udp_basics.Example05_UdpBasics
 *  06  Недоліки UDP на власні очі             example06_udp_no_guarantees.Example06_UdpNoGuarantees
 *  07  TCP чи UDP: порівняння і заміри        example07_tcp_vs_udp.Example07_TcpVsUdp
 *  08  Підсумок: груповий чат на сокетах      example08_real_world_chat.Example08_RealWorldChat
 * </pre>
 *
 * <p>У прикладах 02, 04, 05 і 08 сервер та клієнт лежать в ОКРЕМИХ файлах
 * ({@code EchoServer}/{@code EchoClient}, {@code ChatServer}/{@code ChatClient} тощо).
 * Клас {@code ExampleNN_*} запускає їх разом в одному процесі — це зручно для
 * швидкого перегляду. Але спробуйте обов'язково і «справжній» спосіб: запустити
 * сервер окремо, а клієнта — окремо, у двох різних вікнах запуску. Саме так вони
 * працюють у житті: два незалежні процеси, які знайшли одне одного за адресою і портом.</p>
 *
 * <p>Порти прикладів: 7001–7008. Якщо побачите {@code BindException} — значить,
 * попередній приклад ще не зупинився; закрийте його і запустіть знову.</p>
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Модуль 2. Рівень 19 — Sockets");
        System.out.println("8 прикладів у пакетах example01..example08.");
        System.out.println("Теми: TCP, Socket, ServerSocket, UDP, DatagramSocket, DatagramPacket, TCP чи UDP.");
        System.out.println("Відкрийте потрібний ExampleNN_*.java і запустіть його main().");
    }
}
