package ua.com.javarush.jsquad.m1.example07_polymorphism_overriding;

/**
 * Модуль 2. Рівень 1. ООП: інкапсуляція, поліморфізм
 * <hr>
 * <h3>Тема: Поліморфізм — перевизначення методу (overriding)</h3>
 *
 * <p>Якщо ми успадкували метод, який робить не зовсім те, що нам потрібно, ми можемо
 * <b>замінити</b> цей метод на інший. Головне не те, в якому класі написано метод,
 * а яким є тип (клас) об'єкта, в якому цей метод викликається.</p>
 *
 * <p><b>Важливо (зі слайду):</b> успадковувати та перевизначити можна лише
 * <b>нестатичні методи</b>. Статичні методи не успадковуються і, відповідно,
 * не перевизначаються.</p>
 *
 * <p><b>Аналогія з життя:</b> «надіслати сповіщення» можна різними каналами.
 * Email, SMS, Push — однакова команда {@code send()}, але кожен робить це по-своєму.</p>
 *
 * <pre>
 *   send()
 *     ├── Notification : "загальне повідомлення"
 *     ├── Email        : "📧 лист"          ← повна заміна
 *     ├── Sms          : "📱 SMS"           ← повна заміна
 *     └── Push         : вібрація + super.send()  ← ДОПОВНЕННЯ через super
 * </pre>
 *
 * <p><b>Реальне застосування:</b> сервіс розсилок зберігає список {@code Notification}
 * і просто кличе {@code send()} для кожного — не знаючи й не питаючи, який це канал.</p>
 */
public class Example07_PolymorphismOverriding {

    public static void main(String[] args) {

        // === 1. Той самий виклик send() — різна поведінка ===
        System.out.println("=== Один метод send() — різні реалізації ===");

        EmailNotification email = new EmailNotification("Ваше замовлення відправлено", "user@mail.com");
        NotificationBase baseNotification = email;
        Notification notification = new PushNotification("У вас нове повідомлення");



        EmailNotification newEn = (EmailNotification) notification;



        Notification sms = new SmsNotification("Код: 4815", "+380501234567");
        Notification push = new PushNotification("У вас нове повідомлення");




        email.send();  // викликається версія EmailNotification
        sms.send();    // викликається версія SmsNotification
        push.send();   // спершу свій код, потім super.send()
        System.out.println();


        // === 2. Поліморфізм у дії: один цикл — різні канали ===
        // Змінна типу Notification, а виконується метод КОНКРЕТНОГО об'єкта.
        System.out.println("=== Розсилка списком (масив батьківського типу) ===");

        Notification[] outbox = { email, sms, push };


        for (Notification n : outbox) {
            n.send(); // компілятор не знає тип — потрібну версію обере JVM під час виконання
        }
        System.out.println();

        // === 3. @Override та super.method() ===
        System.out.println("=== Два способи перевизначення ===");
        System.out.println("• Email/SMS — ПОВНА заміна методу (@Override).");
        System.out.println("• Push      — ДОПОВНЕННЯ: свій код + super.send().");
        System.out.println();
        System.out.println("Поліморфізм дозволяє звертатись до об'єктів різних класів однаково.");
    }
}
