package ua.com.javarush.jsquad.m1.example04_many_clients;

/**
 * Модуль 2. Рівень 19. Sockets
 * <hr>
 * <h3>Тема: Один сервер — багато клієнтів</h3>
 *
 * <p>Приклад 02 обслуговував рівно одного клієнта і завершувався. Справжній сервер
 * так не працює. Робочий рецепт складається з двох рядків:</p>
 *
 * <pre>
 *   while (true) {
 *       Socket socket = serverSocket.accept();          // 1) прийняли з'єднання
 *       new Thread(() -&gt; serve(socket)).start();        // 2) віддали потоку і знову на accept()
 *   }
 * </pre>
 *
 * <p>Якби ми обслуговували клієнта в тому ж потоці, де стоїть {@code accept()},
 * то другий клієнт чекав би, поки перший договорить. Тут кожен обслуговується
 * паралельно — зверніть увагу на імена потоків у виводі.</p>
 *
 * <p><b>Аналогія з життя:</b> адміністратор на вході в поліклініку. Він не лікує
 * сам — лише зустрічає пацієнта і направляє до вільного лікаря, а сам одразу
 * повертається до дверей. Якби адміністратор лікував кожного особисто, черга
 * на вулиці не рухалась би.</p>
 *
 * <p><b>Реальне застосування:</b> так влаштовані всі веб-сервери. У бойовому коді
 * замість {@code new Thread(...)} беруть пул потоків (Рівень 14:
 * {@code Executors.newFixedThreadPool(50)}) — щоб 10 000 клієнтів не створили
 * 10 000 потоків і не поклали сервер.</p>
 */
public class Example04_ManyClients {

    public static void main(String[] args) throws Exception {

        System.out.println("=== Приклад 04: три клієнти одночасно ===");
        System.out.println();

        // === 1. Підіймаємо сервер. Daemon — щоб програма змогла завершитись ===
        // Сервер працює в нескінченному циклі; звичайний потік не дав би JVM вийти.
        Thread server = new Thread(() -> {
            try {
                MultiClientServer.start();
            } catch (Exception e) {
                System.out.println("[сервер] помилка: " + e.getMessage());
            }
        }, "server");
        server.setDaemon(true);
        server.start();

        Thread.sleep(300);                            // хай сервер займе порт

        // === 2. Три клієнти стукають у двері ОДНОЧАСНО ===
        String[] names = {"Олена", "Петро", "Марія"};
        Thread[] clients = new Thread[names.length];

        long start = System.currentTimeMillis();

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            clients[i] = new Thread(() -> {
                try {
                    SimpleClient.talk(name);
                } catch (Exception e) {
                    System.out.println("[" + name + "] помилка: " + e.getMessage());
                }
            }, name);
            clients[i].start();
        }

        // === 3. Чекаємо на всіх і дивимось на час ===
        for (Thread client : clients) {
            client.join();
        }

        long total = System.currentTimeMillis() - start;

        System.out.println();
        System.out.println("Усі три обслужені за " + total + " мс.");
        System.out.println("Кожен запит займав 500 мс на сервері. Якби сервер працював");
        System.out.println("по черзі — вийшло б близько 1500 мс. Потоки зробили це паралельно.");
    }
}
