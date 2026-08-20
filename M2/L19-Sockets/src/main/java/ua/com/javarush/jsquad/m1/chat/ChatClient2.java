package ua.com.javarush.jsquad.m1.chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * КЛІЄНТ чату для прикладу 08.
 *
 * <p>Головна ідея: клієнту потрібні ДВА потоки. Один читає те, що приходить із
 * сервера (бо повідомлення можуть прилетіти будь-коли), інший — надсилає своє.
 * У справжньому чаті другим потоком був би ввід із клавіатури; у нас — готовий
 * масив реплік.</p>
 */
public class ChatClient2 {

    public static void main(String[] args) throws Exception {
        talk();
    }

    public static void talk() throws IOException {

        try (Socket socket = new Socket("localhost", ChatServer.PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8), true)) {

            Scanner clientInput = new Scanner(System.in);

            System.out.println("What is your name?");

            String name = clientInput.nextLine();

            out.println(name);

            // === 2. Окремий потік ТІЛЬКИ читає вхідні повідомлення ===
            Thread listener = new Thread(() -> {
                try {
                    String incoming;
                    while ((incoming = in.readLine()) != null) {
                        System.out.println("   [екран " + name + "] " + incoming);
                    }
                } catch (IOException e) {
                    // сокет закрили — це нормальне завершення
                }
            }, "listener-" + name);

            listener.setDaemon(true);
            listener.start();


            // Scan input messages from the client

            while (true) {
                if (clientInput.hasNextLine()) {
                    String s = clientInput.nextLine();
                    out.println(s);
                }
            }

        }
    }

}
