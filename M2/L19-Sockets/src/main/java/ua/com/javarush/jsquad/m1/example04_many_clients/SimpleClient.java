package ua.com.javarush.jsquad.m1.example04_many_clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * КЛІЄНТ для прикладу 04. Називає своє ім'я і читає дві відповіді сервера.
 */
public class SimpleClient {

    public static void main(String[] args) throws IOException {
        talk("Одиночний клієнт");
    }

    public static void talk(String name) throws IOException {

        try (Socket socket = new Socket("localhost", MultiClientServer.PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8), true)) {

            out.println(name);                        // представляємось

            System.out.println("[" + name + "] сервер: " + in.readLine());   // привітання
            System.out.println("[" + name + "] сервер: " + in.readLine());   // результат
        }
    }
}
