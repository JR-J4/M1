package ua.com.javarush.jsquad.m1.example05_anonymous_class;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Модуль 2. Рівень 15. Внутрішні та вкладені класи
 * <hr>
 * <h3>Тема: Внутрішні анонімні класи</h3>
 *
 * <p>Анонімний клас дозволяє об'єднати в одному місці <b>чотири речі</b>:</p>
 * <pre>
 *   1. Оголошення класу-нащадка.
 *   2. Перевизначення методу.
 *   3. Оголошення змінної.
 *   4. Створення об'єкта класу-нащадка.
 * </pre>
 *
 * <p>Але фактично ми об'єднуємо дві операції — оголошення класу-нащадка
 * та створення його об'єкта:</p>
 *
 * <pre>
 *   Thread thread = new Thread() {
 *       public void run() {
 *           // код методу
 *       }
 *   };
 * </pre>
 *
 * <p>Читається так: "створи об'єкт класу, який успадковується від Thread
 * і має ось такий run()". Імені в цього класу немає — компілятор дасть йому
 * технічне ім'я {@code Зовнішній$1}, {@code Зовнішній$2} тощо.</p>
 *
 * <p><b>Аналогія з життя:</b> разова довіреність. Замість того щоб оформлювати
 * окрему фірму-посередника (окремий клас) заради одного доручення, ви пишете
 * папірець прямо на місці: "цей чоловік робить ось це". Один раз, тут і зараз.</p>
 *
 * <p><b>Реальне застосування:</b> обробники подій (кнопка натиснута), задачі для
 * потоків, компаратори для сортування, заглушки в тестах — усе, що потрібне
 * в одному-єдиному місці й не заслуговує на окремий файл.</p>
 */
public class Example05_AnonymousClass {

    public static void main(String[] args) throws InterruptedException {

        // === 1. Довгий шлях: окремий іменований клас ===
        // Сценарій: треба потік, який щось друкує. Класичний спосіб — окремий клас.
        System.out.println("1. Окремий іменований клас-нащадок Thread:");

        class NamedTask extends Thread {          // локальний клас (усередині методу)
            @Override
            public void run() {
                System.out.println("   Іменований клас: працює потік " + getName());
            }
        }

        Thread named = new NamedTask();
        named.start();
        named.join();

        System.out.println();

        // === 2. Той самий результат анонімним класом ===
        // Оголошення класу-нащадка + перевизначення run() + створення об'єкта — одразу.
        System.out.println("2. Той самий потік анонімним класом:");

        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("   Анонімний клас: працює потік " + getName());
            }
        };
        thread.start();
        thread.join();

        System.out.println("   Ім'я класу об'єкта thread: " + thread.getClass().getName());
        System.out.println("   Батьківський клас:         " + thread.getClass().getSuperclass().getSimpleName());

        System.out.println();

        // === 3. Анонімна реалізація інтерфейсу ===
        // Сценарій: сортуємо список міст за довжиною назви.
        List<String> cities = new ArrayList<>(List.of("Київ", "Дніпро", "Тернопіль", "Ніжин", "Львів"));

        cities.sort(new Comparator<String>() {     // анонімний клас, що реалізує Comparator
            @Override
            public int compare(String a, String b) {
                return Integer.compare(a.length(), b.length());
            }
        });

        System.out.println("3. Міста за довжиною назви: " + cities);

        System.out.println();

        // === 4. Анонімний нащадок абстрактного класу ===
        // Сценарій: два різні канали сповіщень, кожен потрібен лише тут.
        // Зверніть увагу: у дужках після new передаємо аргументи конструктора предка.
        Notifier email = new Notifier("email") {
            @Override
            public void send(String text) {
                System.out.println("   ✉ Лист: " + text);
            }
        };

        Notifier sms = new Notifier("sms") {
            @Override
            public void send(String text) {
                System.out.println("   ☎ SMS: " + text);
            }
        };

        System.out.println("4. Два анонімні нащадки одного абстрактного класу:");
        email.send("Ваше замовлення відправлено");
        email.report();
        sms.send("Код підтвердження: 4821");
        sms.report();

        System.out.println();

        // === 5. Анонімний клас може мати ВЛАСНІ поля й методи ===
        // Але дістатися до них можна лише зсередини — ззовні тип змінної обмежує нас.
        Runnable counter = new Runnable() {
            private int runs = 0;                  // власне поле анонімного класу

            @Override
            public void run() {
                runs++;
                System.out.println("   Запуск №" + runs);
            }
        };

        System.out.println("5. Анонімний клас зі своїм станом:");
        counter.run();
        counter.run();
        counter.run();
        // counter.runs — недоступне: тип змінної Runnable нічого не знає про поле runs

        System.out.println();

        // === 6. Нумерація анонімних класів ===
        // Кожен анонімний клас у файлі отримує свій номер: $1, $2, $3...
        System.out.println("6. Технічні імена анонімних класів у цьому файлі:");
        System.out.println("   thread:  " + thread.getClass().getName());
        System.out.println("   email:   " + email.getClass().getName());
        System.out.println("   sms:     " + sms.getClass().getName());
        System.out.println("   counter: " + counter.getClass().getName());
        System.out.println("   Пропущений $2 — це анонімний Comparator із блоку 3,");
        System.out.println("   ми просто не зберігали його в змінну.");
        System.out.println("   А іменований клас із блоку 1 отримав ім'я "
                + NamedTask.class.getName() + " (локальний клас).");
    }
}
