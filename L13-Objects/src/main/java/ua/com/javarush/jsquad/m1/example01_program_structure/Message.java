package ua.com.javarush.jsquad.m1.example01_program_structure;

/**
 * Клас Message — шаблон для створення повідомлень у месенджері.
 *
 * Поля — дані повідомлення (відправник, текст).
 * Методи — дії, які можна виконати з повідомленням.
 */
public class Message {

    // Поля класу — дані повідомлення
    String sender;  // хто відправив
    String text;    // текст повідомлення

    // Метод — показати інформацію про повідомлення
    void showInfo() {
        System.out.println("[" + sender + "]: " + text);
    }

    // Метод — імітація відправки повідомлення
    void send() {
        System.out.println("Повідомлення від " + sender + " відправлено!");
    }
}
