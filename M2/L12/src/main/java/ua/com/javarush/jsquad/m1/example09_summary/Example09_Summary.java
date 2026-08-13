package ua.com.javarush.jsquad.m1.example09_summary;

/**
 * Модуль 2. Рівень 12. Знайомство з потоками. Частина 2
 * <hr>
 * <h3>Тема: Підсумок — продаж квитків онлайн (усі інструменти разом)</h3>
 *
 * <p>Збираємо всю лекцію в одному сценарії:</p>
 * <pre>
 *   • Мінуси багатопотоковості — без захисту квиток продався б двічі (overselling).
 *   • synchronized             — метод buy() продає рівно по одному квитку за раз.
 *   • volatile                 — прапорець salesOpen "гасне" й видно всім касам одразу.
 *   • join                     — головний потік чекає всі каси, тоді підбиває підсумок.
 * </pre>
 *
 * <p><b>Сценарій:</b> у наявності обмежена кількість квитків. Кілька "кас" (потоків)
 * одночасно продають їх покупцям. Треба продати рівно стільки, скільки є —
 * ні квитком більше.</p>
 *
 * <p><b>Реальне застосування:</b> бронювання місць у літаку/кіно, продаж лімітованих
 * товарів на розпродажі, списання зі складу — усюди, де кілька потоків ділять
 * спільний обмежений ресурс.</p>
 */
public class Example09_Summary {

    static class TicketOffice {
        private int ticketsLeft;
        private int sold = 0;
        private volatile boolean salesOpen = true;   // видно всім касам одразу

        TicketOffice(int ticketsLeft) {
            this.ticketsLeft = ticketsLeft;
        }

        // synchronized: лише одна каса за раз перевіряє й зменшує залишок.
        synchronized boolean buy(String cashier) {
            if (!salesOpen || ticketsLeft == 0) {
                return false;                        // продаж закрито або квитки скінчились
            }
            ticketsLeft--;
            sold++;
            System.out.println(cashier + " продав квиток. Залишилось: " + ticketsLeft);
            return true;
        }

        void closeSales() {
            salesOpen = false;                       // volatile-запис
        }

        int getSold() {
            return sold;
        }

        int getTicketsLeft() {
            return ticketsLeft;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Продаж квитків: 20 квитків, 4 каси одночасно ===");
        TicketOffice office = new TicketOffice(20);

        // Каса намагається продавати, поки продаж відкрито і є квитки.
        Runnable cashierJob = () -> {
            String name = Thread.currentThread().getName();
            while (office.buy(name)) {
                sleep(30);                            // імітація оформлення покупки
            }
        };

        Thread[] cashiers = new Thread[4];
        for (int i = 0; i < cashiers.length; i++) {
            cashiers[i] = new Thread(cashierJob, "Каса-" + (i + 1));
            cashiers[i].start();
        }

        // Через 0.2 с адміністратор закриває продаж (навіть якщо квитки ще є).
        sleep(200);
        System.out.println(">>> адміністратор закриває продаж (salesOpen = false)");
        office.closeSales();

        for (Thread c : cashiers) {
            c.join();                                 // чекаємо, поки всі каси завершаться
        }

        System.out.println();
        System.out.println("Продано квитків:   " + office.getSold());
        System.out.println("Залишок квитків:   " + office.getTicketsLeft());
        System.out.println("Продано + залишок = " + (office.getSold() + office.getTicketsLeft())
                + " (завжди 20 — жоден квиток не продано двічі й не зник).");
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
