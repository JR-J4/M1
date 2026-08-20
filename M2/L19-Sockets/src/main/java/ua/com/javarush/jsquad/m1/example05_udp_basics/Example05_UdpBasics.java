package ua.com.javarush.jsquad.m1.example05_udp_basics;

/**
 * Модуль 2. Рівень 19. Sockets
 * <hr>
 * <h3>Тема: UDP — DatagramSocket і DatagramPacket</h3>
 *
 * <p>При використанні протоколу <b>UDP (User Datagram Protocol)</b> відповідальність
 * за обробку помилок і повторну передачу даних покладено на протокол рівнем вище.
 * Але попри недоліки, UDP ефективний для серверів, які надсилають невеликі відповіді
 * великій кількості клієнтів.</p>
 *
 * <p>У Java для роботи з датаграмами використовують два класи:</p>
 * <pre>
 *   DatagramSocket — «розетка» для датаграм. І у відправника, і в одержувача.
 *   DatagramPacket — сам пакет: масив байтів + адреса та порт отримувача.
 *
 *   // ВІДПРАВНИК
 *   DatagramSocket socket = new DatagramSocket();                    // порт видасть ОС
 *   byte[] data = "привіт".getBytes(UTF_8);
 *   socket.send(new DatagramPacket(data, data.length, address, 7005));
 *
 *   // ОДЕРЖУВАЧ
 *   DatagramSocket socket = new DatagramSocket(7005);                // займаємо порт
 *   DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
 *   socket.receive(packet);                                          // чекає на пакет
 * </pre>
 *
 * <h4>Чим UDP відрізняється від TCP (приклад 02):</h4>
 * <pre>
 *   немає accept()          — немає з'єднання взагалі
 *   немає клієнта/сервера   — є лише відправник і одержувач
 *   немає потоків (Stream)  — працюємо напряму з масивом байтів
 *   немає гарантій          — пакет може не дійти або прийти не в тому порядку
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> TCP — це телефонна розмова (спершу додзвонились,
 * потім говоримо, чуємо одне одного). UDP — це поштова листівка: написали адресу,
 * вкинули в скриньку і пішли. Ніхто не підтвердить, що листівка дійшла.</p>
 *
 * <p><b>Реальне застосування:</b> онлайн-ігри, потокове відео, відеочати, DNS.
 * Там краще втратити один кадр, ніж чекати на його повторну доставку.</p>
 */
public class Example05_UdpBasics {

    public static void main(String[] args) throws Exception {

        System.out.println("=== Приклад 05: обмін датаграмами по UDP ===");
        System.out.println();

        // === 1. Одержувач в окремому потоці — receive() блокує так само, як accept() ===
        Thread receiver = new Thread(() -> {
            try {
                UdpReceiver.receive();
            } catch (Exception e) {
                System.out.println("[одержувач] помилка: " + e.getMessage());
            }
        }, "udp-receiver");
        receiver.start();

        Thread.sleep(300);                       // хай одержувач займе порт

        // === 2. Відправник просто кидає пакети ===
        UdpSender.send();

        receiver.join();

        System.out.println();
        System.out.println("Порівняйте з прикладом 02: тут ніхто не встановлював з'єднання.");
        System.out.println("Відправник навіть не знає, чи хтось його слухає.");
    }
}
