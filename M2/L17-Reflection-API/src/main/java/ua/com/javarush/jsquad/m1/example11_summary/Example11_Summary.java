package ua.com.javarush.jsquad.m1.example11_summary;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Модуль 2. Рівень 17. Reflection API
 * <hr>
 * <h3>Підсумок: власний міні-Spring на рефлексії</h3>
 *
 * <p>Цей приклад збирає докупи всю лекцію. Ми напишемо крихітний контейнер
 * залежностей, який робить те саме, що й Spring, — і побачимо, що всередині
 * немає жодної магії, лише рефлексія.</p>
 *
 * <h4>Що використано з лекції:</h4>
 * <pre>
 *   Class.forName()                     — приклад 01: клас відомий лише рядком
 *   getAnnotation() / isAnnotationPresent() — приклади 03, 04: читання анотацій
 *   getDeclaredConstructor().newInstance() — приклад 06: створення об'єкта
 *   getDeclaredFields() + setAccessible()  — приклади 07, 08: заповнення приватних полів
 *   getDeclaredMethods() + invoke()      — приклад 09: виклик методу за анотацією
 *   Proxy.newProxyInstance()             — приклад 10: перехоплення викликів
 * </pre>
 *
 * <p><b>Сценарій:</b> інтернет-магазин. Є сервіс замовлень, сховище та відправник
 * листів. Класи нічого не знають один про одного і не містять жодного {@code new} —
 * контейнер збирає з них робочу програму під час виконання.</p>
 *
 * <p><b>Аналогія з життя:</b> ви не збираєте автомобіль самі. Ви пишете заявку:
 * "потрібен двигун, коробка передач і магнітола". Завод (контейнер) читає заявку,
 * знаходить потрібні деталі на складі та встановлює їх на місця. Ваша справа —
 * лише правильно оголосити потреби.</p>
 */
public class Example11_Summary {

    public static void main(String[] args) throws Exception {

        // === 1. "Файл налаштувань" ===
        // Уявіть, що це прочитано з application.properties
        Map<String, String> properties = new LinkedHashMap<>();
        properties.put("mail.from", "noreply@shop.ua");
        properties.put("db.url", "jdbc:postgresql://localhost:5432/shop");
        properties.put("shop.maxItems", "5");

        System.out.println("1. Налаштування застосунку:");
        properties.forEach((key, val) -> System.out.println("   " + key + " = " + val));

        System.out.println();

        // === 2. "Конфігурація": імена класів рядками ===
        // Компілятор про ці класи тут нічого не знає — для нього це просто текст.
        List<String> classNames = List.of(
                "ua.com.javarush.jsquad.m1.example11_summary.EmailSender",
                "ua.com.javarush.jsquad.m1.example11_summary.OrderRepository",
                "ua.com.javarush.jsquad.m1.example11_summary.OrderService",
                "java.util.ArrayList");     // клас без @Component — контейнер його проігнорує

        System.out.println("2. Запускаємо контейнер:");
        MiniContainer container = new MiniContainer(properties);
        container.start(classNames);

        System.out.println();

        // === 3. Що вийшло ===
        System.out.println("3. Компоненти в контейнері:");
        for (String description : container.describeBeans()) {
            System.out.println("   " + description);
        }

        System.out.println();

        // === 4. Працюємо із застосунком ===
        // Об'єкт зібрано контейнером: поля заповнені, залежності підставлені.
        OrderService orderService = container.getBean(OrderService.class);

        System.out.println("4. Оформлюємо замовлення:");
        orderService.placeOrder("olena@example.com", "Ноутбук", 1);

        System.out.println();
        orderService.placeOrder("andriy@example.com", "Навушники", 2);

        System.out.println();
        System.out.println("   Усього замовлень: " + orderService.totalOrders());

        System.out.println();

        // === 5. Налаштування з конфігу справді працює ===
        // maxItems = 5 приїхало з properties і потрапило в приватне поле через рефлексію.
        System.out.println("5. Перевіряємо ліміт із налаштувань (shop.maxItems = 5):");
        try {
            orderService.placeOrder("test@example.com", "Ручка", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("   " + e.getMessage());
        }

        System.out.println();

        // === 6. Компонент з інтерфейсом приїхав загорнутим у проксі ===
        NotificationSender sender = container.getBean(NotificationSender.class);

        System.out.println("6. Що саме підставив контейнер у поле sender:");
        System.out.println("   клас об'єкта: " + sender.getClass().getName());
        System.out.println("   це проксі?    " + java.lang.reflect.Proxy.isProxyClass(sender.getClass()));
        System.out.println("   Саме тому у Spring у стектрейсі трапляються дивні $Proxy-класи:");
        System.out.println("   ваш об'єкт загорнутий у проксі заради транзакцій, кешу чи безпеки.");

        System.out.println();

        // === 7. Головний висновок ===
        System.out.println("7. Що варто запам'ятати:");
        System.out.println("   • Рефлексія — це доступ до класів, полів і методів під час виконання.");
        System.out.println("   • Точка входу завжди одна — об'єкт Class.");
        System.out.println("   • getXxx() бачить лише public (зате й успадковане),");
        System.out.println("     getDeclaredXxx() — усе своє, включно з private.");
        System.out.println("   • Constructor.newInstance() кращий за Class.newInstance():");
        System.out.println("     працює з будь-якими конструкторами і чесно загортає винятки.");
        System.out.println("   • setAccessible(true) відкриває private, але це знімає гарантії класу.");
        System.out.println("   • Dynamic Proxy додає поведінку, не змінюючи код класу.");
        System.out.println("   • Ціна: втрата швидкості, перевірок компілятора та читабельності.");
        System.out.println();
        System.out.println("   Головне правило: рефлексія — інструмент фреймворків.");
        System.out.println("   Якщо задачу можна розв'язати звичайним викликом методу — так і робіть.");
    }
}
