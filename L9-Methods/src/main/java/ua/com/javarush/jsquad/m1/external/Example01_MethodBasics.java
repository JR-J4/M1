package ua.com.javarush.jsquad.m1.external;

/**
 * Лекція 9: Методи — Навіщо розбивати код на блоки
 *
 * Методи — це іменовані групи команд, які можна викликати необмежену кількість разів.
 * Синтаксис: {@code модифікатор static типНазад ім'я(параметри) { тіло }}.
 *
 * Аналогія: корпоративний шаблон листа — один документ, який менеджер використовує для всіх клієнтів.
 * Реальне застосування: повторювані операції — від надсилання email-підтверджень до оновлення статусів замовлень.
 */
public class Example01_MethodBasics {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Повторення без методу і з методом
        // ============================================================
        System.out.println("=== Ручне повторення статусів ===");
        System.out.println("Статус: нове замовлення");
        System.out.println("Статус: передано в доставку");
        System.out.println("Статус: завершено");

        System.out.println();

        System.out.println("=== Та сама задача через метод ===");
        printStatusFlow();
        printStatusFlow(); // достатньо викликати метод повторно

        System.out.println();

        // ============================================================
        //   Приклад 2: Говорящі назви роблять код самодокументованим
        // ============================================================
        System.out.println("=== Говоряща назва методу ===");
        sendInvoiceEmail();
        archiveInvoice();

        System.out.println();

        // ============================================================
        //   Приклад 3: Великі задачі складаються з малих методів
        // ============================================================
        System.out.println("=== Методи як кроки бізнес-процесу ===");

        processNewCustomer();
    }

    private static void printStatusFlow() {
        System.out.println("Статус: нове замовлення");
        System.out.println("Статус: передано в доставку");
        System.out.println("Статус: завершено");
    }

    private static void sendInvoiceEmail() {
        System.out.println("Готуємо PDF рахунку...");
        System.out.println("Надсилаємо лист клієнту...");
    }

    private static void archiveInvoice() {
        System.out.println("Переміщаємо рахунок в архів за датою");
    }

    private static void processNewCustomer() {
        collectPersonalData();
        createCrmCard();
        sendWelcomePack();
    }

    private static void collectPersonalData() {
        System.out.println("1) Клієнт заповнює коротку форму");
    }

    private static void createCrmCard() {
        System.out.println("2) CRM створює картку з даними");
    }

    private static void sendWelcomePack() {
        System.out.println("3) Сервіс надсилає вітальний лист і бонусний купон");
    }
}
