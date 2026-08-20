package ua.com.javarush.jsquad.m1.example06_udp_no_guarantees;

import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Модуль 2. Рівень 19. Sockets
 * <hr>
 * <h3>Тема: Недоліки UDP на власні очі</h3>
 *
 * <p>З лекції: «Ми не можемо жодним чином підтвердити успішне передавання даних.
 * UDP не може мати механізм відстеження послідовності даних. UDP не має з'єднання,
 * тому передавати дані ненадійно. У разі зіткнення пакети UDP відкидаються
 * маршрутизаторами. UDP може відкидати пакети у разі виявлення помилок.»</p>
 *
 * <p>Цей приклад показує три наслідки цих слів у коді:</p>
 * <pre>
 *   1) відправка "в порожнечу" не викликає жодної помилки;
 *   2) пакет, що не влазить у буфер одержувача, ОБРІЗАЄТЬСЯ;
 *   3) якщо пакетів багато — частина просто зникає.
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> ви кидаєте листівки у скриньку. Пошта не скаже вам,
 * що адресат виїхав (1). Якщо текст довший за листівку — решта не поміститься (2).
 * А якщо кинути сотню листівок одночасно — кілька загубляться в сортуванні (3).</p>
 *
 * <p><b>Реальне застосування:</b> саме тому в іграх поверх UDP пишуть власну
 * нумерацію пакетів, а важливі дані (гроші, файли, листи) ніколи не передають
 * по UDP — для них є TCP.</p>
 */
public class Example06_UdpNoGuarantees {

    private static final int PORT = 7006;

    public static void main(String[] args) throws Exception {

        // === 1. Відправка в порожнечу: UDP мовчить, TCP кричить ===
        // Сценарій: одержувача НЕ ЗАПУЩЕНО. Обидва протоколи стукають у порожній порт.
        System.out.println("1. Надсилаємо дані туди, де нікого немає:");

        try (DatagramSocket udp = new DatagramSocket()) {
            byte[] data = "чи є тут хтось?".getBytes(UTF_8);
            DatagramPacket packet = new DatagramPacket(
                    data, data.length, InetAddress.getByName("localhost"), PORT);

            udp.send(packet);
            System.out.println("   UDP: send() виконався успішно... і пакет зник назавжди");
        }

        try (Socket tcp = new Socket("localhost", PORT)) {
            System.out.println("   TCP: підключився? " + tcp.isConnected());
        } catch (ConnectException e) {
            System.out.println("   TCP: ConnectException — одразу видно, що сервера немає");
        }

        System.out.println("   Висновок: UDP не має способу сказати вам, що дані не дійшли.");
        System.out.println();

        // === 2. Малий буфер обрізає пакет ===
        // Сценарій: одержувач приготував конверт на 20 байтів, а йому надіслали довгий текст.
        // У TCP такого не буває: там потік байтів, читай скільки хочеш і коли хочеш.
        System.out.println("2. Пакет не влазить у буфер одержувача:");

        String longText = "Це дуже довге повідомлення, яке точно не влізе у маленький буфер";
        byte[] longData = longText.getBytes(UTF_8);

        try (DatagramSocket receiver = new DatagramSocket(PORT);
             DatagramSocket sender = new DatagramSocket()) {

            sender.send(new DatagramPacket(
                    longData, longData.length, InetAddress.getByName("localhost"), PORT));

            DatagramPacket small = new DatagramPacket(new byte[20], 20);   // конверт на 20 байтів
            receiver.receive(small);

            System.out.println("   надіслано (" + longData.length + " байт): " + longText);
            System.out.println("   отримано  (" + small.getLength() + " байт): "
                    + new String(small.getData(), 0, small.getLength(), UTF_8));
            System.out.println("   Решта байтів не 'дочекається' наступного читання — вона втрачена.");
        }

        System.out.println();

        // === 3. Багато пакетів одразу — частина зникає ===
        // Сценарій: відправник шле 1000 пакетів залпом, одержувач читає їх ПІСЛЯ цього.
        // Операційна система складає непрочитані пакети у свій буфер, а коли той
        // переповнюється — просто викидає нові пакети. Без жодного попередження.
        System.out.println("3. Залп із 1000 пакетів (одержувач читає їх уже після відправки):");

        int total = 1000;
        List<Integer> receivedNumbers = new ArrayList<>();

        try (DatagramSocket receiver = new DatagramSocket(PORT);
             DatagramSocket sender = new DatagramSocket()) {

            receiver.setReceiveBufferSize(8 * 1024);          // навмисно маленький буфер
            receiver.setSoTimeout(500);                       // читаємо, поки не настане тиша
            InetAddress address = InetAddress.getByName("localhost");

            System.out.println("   буфер одержувача : " + receiver.getReceiveBufferSize() + " байт");

            for (int i = 1; i <= total; i++) {
                byte[] data = ("пакет №" + i + " ".repeat(200)).getBytes(UTF_8);
                sender.send(new DatagramPacket(data, data.length, address, PORT));
            }
            System.out.println("   надіслано пакетів: " + total);

            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(new byte[2048], 2048);
                    receiver.receive(packet);
                    String text = new String(packet.getData(), 0, packet.getLength(), UTF_8).trim();
                    receivedNumbers.add(Integer.parseInt(text.substring("пакет №".length())));
                } catch (SocketTimeoutException e) {
                    break;                                    // 500 мс тиші — значить, більше нічого не буде
                }
            }
        }

        System.out.println("   отримано пакетів : " + receivedNumbers.size());

        int lost = total - receivedNumbers.size();
        if (lost > 0) {
            System.out.println("   ВТРАЧЕНО: " + lost + " пакетів — і ніхто про це не повідомив");
            System.out.println("   номери перших отриманих: "
                    + receivedNumbers.subList(0, Math.min(5, receivedNumbers.size())));
            System.out.println("   номер останнього       : " + receivedNumbers.get(receivedNumbers.size() - 1)
                    + "  <- далі буфер переповнився, і решта пакетів просто зникла");
        } else {
            System.out.println("   Цього разу локально дійшли всі — на одному комп'ютері мережі майже немає.");
            System.out.println("   У справжній мережі частина пакетів зникає завжди.");
        }

        System.out.println();
        System.out.println("Порівняйте: у TCP жоден байт не зникне і порядок не зміниться —");
        System.out.println("протокол сам перепитає і перешле те, що не дійшло.");
    }
}
