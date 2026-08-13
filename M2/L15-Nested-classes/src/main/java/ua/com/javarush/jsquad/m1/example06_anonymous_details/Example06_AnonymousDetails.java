package ua.com.javarush.jsquad.m1.example06_anonymous_details;

/**
 * Модуль 2. Рівень 15. Внутрішні та вкладені класи
 * <hr>
 * <h3>Тема: Анонімні класи зсередини — захоплення змінних, this, локальні класи, лямбди</h3>
 *
 * <p>Анонімний клас — це різновид внутрішнього класу, оголошений просто в коді методу.
 * Звідси кілька важливих деталей, про які варто знати:</p>
 *
 * <pre>
 *   • анонімний клас БАЧИТЬ локальні змінні методу, але лише
 *     effectively final — ті, які після ініціалізації не змінюються;
 *   • this всередині анонімного класу — це САМ анонімний об'єкт,
 *     до зовнішнього треба звертатися Outer.this;
 *   • конструктора в анонімного класу бути не може (немає імені!),
 *     замість нього — блок ініціалізації { ... };
 *   • у лямбди все навпаки: this = зовнішній об'єкт, свого this у неї немає.
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> записка на холодильнику. Вона написана в конкретній
 * кухні (метод), бачить усе, що є на кухні, але діє від власного імені. Якщо
 * потрібно сказати "від господаря квартири" — доводиться підписатися повністю
 * ({@code Outer.this}).</p>
 *
 * <p><b>Реальне застосування:</b> обробники подій у GUI та Android, слухачі
 * у бібліотеках, разові стратегії. Знання про {@code this} рятує в момент,
 * коли анонімному слухачеві треба змінити поле свого екрана/вікна.</p>
 */
public class Example06_AnonymousDetails {

    private final String appName = "МійЗастосунок";     // поле зовнішнього класу
    private int handledClicks = 0;                      // його ж змінюватимуть слухачі

    public static void main(String[] args) {
        new Example06_AnonymousDetails().run();         // працюємо з об'єкта: потрібен Outer.this
    }

    private void run() {

        // === 1. Анонімний клас бачить локальні змінні методу ===
        // Сценарій: слухач підставляє в текст ім'я користувача з локальної змінної.
        String user = "Олег";                            // effectively final: більше не змінюємо

        Button save = new Button("Зберегти");
        save.setListener(buttonTitle -> System.out.println("   " + user + " натиснув «" + buttonTitle + "»"));

        System.out.println("1. Захоплення локальної змінної:");
        save.click();

        // user = "Інший";   // якби ми розкоментували — попередній код перестав би компілюватися:
        //                   // "local variables referenced from an inner class must be final
        //                   //  or effectively final"

        System.out.println();

        // === 2. Змінювати стан можна через поле зовнішнього об'єкта ===
        // Локальну змінну-лічильник збільшити не можна, а поле класу — будь ласка.
        Button like = new Button("Подобається");
        like.setListener(new ClickListener() {
            @Override
            public void onClick(String buttonTitle) {
                handledClicks++;                         // поле зовнішнього об'єкта — можна
                System.out.println("   Клік №" + handledClicks + " по «" + buttonTitle + "»");
            }
        });

        System.out.println("2. Зміна стану зовнішнього об'єкта зі слухача:");
        like.click();
        like.click();

        System.out.println();

        // === 3. Два this: свій і зовнішній ===
        // Сценарій: слухачеві треба і власне поле, і назву застосунку із зовнішнього об'єкта.
        Button about = new Button("Про програму");

        about.setListener(new ClickListener() {

            private int shows;                           // власне поле анонімного класу

            {                                            // блок ініціалізації замість конструктора
                shows = 0;
            }


            @Override
            public void onClick(String buttonTitle) {
                shows++;
                System.out.println("   Показ №" + shows + ": застосунок «"
                        + Example06_AnonymousDetails.this.appName + "»");
                System.out.println("   this всередині слухача  → " + this.getClass().getName());
                System.out.println("   Outer.this              → "
                        + Example06_AnonymousDetails.this.getClass().getName());
            }
        });

        System.out.println("3. this анонімного класу vs Outer.this:");
        about.click();

        System.out.println();

        // === 4. Локальний клас: як анонімний, але з іменем ===
        // Сценарій: слухач потрібен двічі й з різними налаштуваннями → потрібен конструктор.
        class LoggingListener implements ClickListener {

            private final String prefix;                 // локальний клас МОЖЕ мати конструктор

            LoggingListener(String prefix) {
                this.prefix = prefix;
            }

            @Override
            public void onClick(String buttonTitle) {
                System.out.println("   " + prefix + " натиснуто «" + buttonTitle + "» у " + appName);
            }
        }

        Button delete = new Button("Видалити");
        delete.setListener(new LoggingListener("[WARN]"));

        Button exit = new Button("Вихід");
        exit.setListener(new LoggingListener("[INFO]"));

        System.out.println("4. Локальний клас (можна перевикористати в межах методу):");
        delete.click();
        exit.click();
        System.out.println("   Ім'я локального класу: " + LoggingListener.class.getName());

        System.out.println();

        // === 5. Лямбда: коротший запис, але ІНШИЙ this ===
        // Лямбда підходить лише для функціонального інтерфейсу (рівно один абстрактний метод).
        Button share = new Button("Поділитися");
        share.setListener(title -> {
            System.out.println("   Лямбда обробила «" + title + "»");
            System.out.println("   this всередині лямбди → " + this.getClass().getName());
        });

        System.out.println("5. Лямбда замість анонімного класу:");
        share.click();
        System.out.println("   Бачите різницю? У лямбди this — це зовнішній об'єкт,");
        System.out.println("   вона не створює власного класу-нащадка.");

        System.out.println();

        // === 6. Коли лямбда НЕ підходить ===
        System.out.println("6. Анонімний клас обов'язковий, якщо:");
        System.out.println("   • треба успадкуватися від класу (лямбда вміє лише інтерфейси);");
        System.out.println("   • в інтерфейсі більше одного абстрактного методу;");
        System.out.println("   • об'єкту потрібні власні поля й свій this;");
        System.out.println("   • треба перевизначити ще й equals/hashCode/toString.");
    }
}
