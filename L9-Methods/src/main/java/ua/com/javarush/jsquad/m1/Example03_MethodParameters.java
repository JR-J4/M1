package ua.com.javarush.jsquad.m1;

/**
 * Лекція 9: Методи — Параметри
 *
 * Параметри — це дані, які метод очікує отримати при виклику: {@code метод(тип1 ім'я1, тип2 ім'я2)}.
 * Вони дозволяють зробити одну реалізацію універсальною для різних вхідних значень.
 *
 * Аналогія: шаблон листа з полями «Ім'я клієнта» та «Сума» — заповнюємо різними даними.
 * Реальне застосування: сервіс сповіщень приймає текст, канал доставки, час затримки й працює з будь-яким проєктом.
 */
public class Example03_MethodParameters {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Повторення повідомлення задану кількість разів
        // ============================================================
        System.out.println("=== Повторювані повідомлення ===");

        repeatMessage("Команда, стендап за 5 хвилин", 2);

        System.out.println();

        repeatMessage("Сьогодні офіс вихідний", 5);



        System.out.println();

        // ============================================================
        //   Приклад 2: Комбінуємо різні типи параметрів
        // ============================================================
        System.out.println("=== Параметри різних типів ===");
        scheduleDelivery("Київ", 12, true);
        scheduleDelivery("Львів", 3, false);

        System.out.println();

        // ============================================================
        //   Приклад 3: Універсальна розсилка нагадувань
        // ============================================================
        System.out.println("=== Гнучкий шаблон нагадувань ===");
        sendReminder("Оксана", "email", 24);
        sendReminder("Ігор", "SMS", 2);
    }

    private static void repeatMessage(String message, int times) {

        for (int i = 0; i < times; i++) {
            System.out.println( (i + 1) + ") " + message);
        }

    }

    private static void scheduleDelivery(String city, int packages, boolean express) {
        String mode = express ? "експрес" : "звичайна";
        System.out.println("Місто: " + city + ", пакунків: " + packages + ", режим: " + mode);
    }

    private static void sendReminder(String clientName, String channel, int hoursBeforeEvent) {
        System.out.println("Нагадування для " + clientName + ": канал " + channel
                + ", за " + hoursBeforeEvent + " год. до зустрічі");
    }
}
