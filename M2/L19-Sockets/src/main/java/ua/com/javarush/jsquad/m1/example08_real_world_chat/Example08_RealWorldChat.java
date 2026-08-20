package ua.com.javarush.jsquad.m1.example08_real_world_chat;

import ua.com.javarush.jsquad.m1.chat.ChatServer;

/**
 * Модуль 2. Рівень 19. Sockets
 * <hr>
 * <h3>Тема: Підсумок — груповий чат на сокетах</h3>
 *
 * <p>Фінальний приклад збирає все, що було в лекції, у справжню програму:</p>
 * <pre>
 *   ServerSocket + accept() у циклі   — приклад 01, 03
 *   потік на кожного клієнта          — приклад 04
 *   читання/запис рядків              — приклад 02
 *   TCP, бо повідомлення губити не можна — приклад 07
 * </pre>
 *
 * <p>Новий елемент лише один: сервер <b>зберігає список</b> підключених клієнтів,
 * тому може переслати повідомлення від одного всім іншим (broadcast).
 * Клієнт же тримає окремий потік-слухач, бо повідомлення від сусідів прилітають
 * у непередбачуваний момент.</p>
 *
 * <p><b>Аналогія з життя:</b> радіодиспетчер. Кожен таксист має свою рацію
 * (сокет), диспетчер тримає список усіх увімкнених рацій і, почувши одного,
 * повторює повідомлення в усі інші.</p>
 *
 * <p><b>Реальне застосування:</b> це спрощений скелет Telegram, Slack чи чату
 * в грі. У бойових системах поверх сокетів беруть WebSocket і пул потоків, але
 * принцип — «список підключених + розсилка» — залишається той самий.</p>
 *
 * <h4>Як запускати:</h4>
 * <p>Цей клас підіймає сервер і двох учасників одразу. Щоб побачити «живий» чат,
 * запустіть окремо {@link ChatServer}, а потім двічі {@link ChatClient} у різних
 * вікнах запуску.</p>
 */
public class Example08_RealWorldChat {

    public static void main(String[] args) throws Exception {

        System.out.println("=== Приклад 08: чат на сокетах (сервер + два учасники) ===");
        System.out.println();

        // === 1. Сервер чату ===
        Thread server = new Thread(() -> {
            try {
                ChatServer.start();
            } catch (Exception e) {
                System.out.println("[сервер] помилка: " + e.getMessage());
            }
        }, "chat-server");
        server.setDaemon(true);
        server.start();

        Thread.sleep(300);

        // === 2. Перший учасник заходить у чат ===
        Thread olena = client("Олена", new String[]{"привіт, є хто живий?", "у мене працює!"});
        olena.start();

        Thread.sleep(400);                        // Петро заходить трохи пізніше

        // === 3. Другий учасник бачить повідомлення першого ===
        Thread petro = client("Петро", new String[]{"привіт, Олено", "сокети — це просто рядки в трубі"});
        petro.start();

        olena.join();
        petro.join();

        System.out.println();
        System.out.println("Кожен рядок '[екран ...]' — це повідомлення, яке сервер переслав");
        System.out.println("іншому учаснику. Один сокет на клієнта, один список на сервері — і чат готовий.");
    }

    private static Thread client(String name, String[] messages) {
        return new Thread(() -> {
            try {
                ChatClient.talk(name, messages);
            } catch (Exception e) {
                System.out.println("[" + name + "] помилка: " + e.getMessage());
            }
        }, name);
    }
}
