package ua.com.javarush.jsquad.m1;

import java.time.Instant;

/**
 * Лекція 27: Робота iз часом i датою.
 * <p>
 * Тема: Клас Instant — час для комп'ютерiв, а не людей.
 * <p>
 * Instant зберiгає момент часу у виглядi кiлькостi секунд i наносекунд,
 * що минули з 1 сiчня 1970 року (UTC). Це iдеальний клас для:
 * <ul>
 *   <li>замiру тривалостi виконання операцiї,</li>
 *   <li>збереження мiток часу подiй у логах,</li>
 *   <li>обмiну часом мiж рiзними системами без проблем з часовими поясами.</li>
 * </ul>
 * <p>
 * Синтаксис:
 * <pre>
 *   Instant t = Instant.now();
 *   Instant t = Instant.ofEpochSecond(1_700_000_000);
 *   Instant t = Instant.ofEpochMilli(1_700_000_000_000L);
 * </pre>
 * <p>
 * Аналогiя: Instant — це штампування квитанцiй касовим апаратом.
 * Просто "штамп" з абсолютною кiлькiстю секунд. Не питай "котра година?",
 * питай "скiльки секунд вiд епохи?".
 * <p>
 * Реальне застосування: бенчмаркiнг, логи, JWT-токени (поле exp),
 * timestamp у БД, обмiн часом мiж сервiсами.
 */
public class Example07_Instant {

    public static void main(String[] args) throws InterruptedException {
        // === Блок 1: поточний момент ===
        // Сценарiй: timestamp для логу.
        System.out.println("=== Instant.now() ===");
        Instant now = Instant.now();
        System.out.println("Зараз (UTC): " + now);  // напр. 2026-05-25T13:42:15.123Z

        System.out.println();

        // === Блок 2: getters — секунди i наносекунди ===
        // Сценарiй: дiстаємо "сирi" числа з Instant.
        System.out.println("=== Що зберiгає Instant ===");
        Instant t = Instant.now();
        System.out.println("Секунд з 1970: " + t.getEpochSecond());
        System.out.println("Наносекунд:    " + t.getNano());
        System.out.println("Мiлiсекунд:    " + t.toEpochMilli());

        System.out.println();

        // === Блок 3: створення з epoch ===
        // Сценарiй: у БД timestamp лежить як long — вiдновлюємо Instant.
        System.out.println("=== ofEpochSecond / ofEpochMilli ===");
        Instant fromSec = Instant.ofEpochSecond(1_700_000_000L);
        Instant fromMs = Instant.ofEpochMilli(1_700_000_000_000L);
        Instant withNanos = Instant.ofEpochSecond(1_700_000_000L, 123_456_789);
        System.out.println("З секунд:           " + fromSec);
        System.out.println("З мiлiсекунд:       " + fromMs);
        System.out.println("З секунд + нано:    " + withNanos);

        System.out.println();

        // === Блок 4: plus/minus — змiщення в часi ===
        // Сценарiй: коли токен має закiнчити дiю — через годину вiд зараз.
        System.out.println("=== plus / minus ===");
        Instant created = Instant.now();
        Instant expires = created.plusSeconds(3600);          // +1 година
        Instant fiveMinAgo = created.minusSeconds(5 * 60);
        System.out.println("Створено:     " + created);
        System.out.println("Спливає:      " + expires);
        System.out.println("5 хв тому:    " + fiveMinAgo);

        System.out.println();

        // === Блок 5: мiлi- i нано- ===
        // Сценарiй: точнiше зрушення часу.
        System.out.println("=== plusMillis / plusNanos ===");
        Instant base = Instant.ofEpochSecond(1_700_000_000L);
        System.out.println("База:         " + base);
        System.out.println("+ 500 мс:     " + base.plusMillis(500));
        System.out.println("+ 1_000_000 нс: " + base.plusNanos(1_000_000));  // = +1 мс

        System.out.println();

        // === Блок 6: вимiрювання тривалостi операцiї ===
        // Сценарiй: скiльки часу зайняла важка операцiя.
        System.out.println("=== Бенчмарк операцiї ===");
        Instant t1 = Instant.now();
        Thread.sleep(50);                              // iмiтуємо роботу
        Instant t2 = Instant.now();

        long elapsedMs = t2.toEpochMilli() - t1.toEpochMilli();
        System.out.println("Початок: " + t1);
        System.out.println("Кiнець:  " + t2);
        System.out.println("Тривалiсть: " + elapsedMs + " мс");

        System.out.println();

        // === Блок 7: порiвняння двох моментiв ===
        // Сценарiй: чи токен ще валiдний?
        System.out.println("=== Порiвняння ===");
        Instant tokenIssued = Instant.now();
        Instant tokenExp = tokenIssued.plusSeconds(60);
        Instant check = tokenIssued.plusSeconds(75);  // вже пiсля закiнчення

        System.out.println("Видано:    " + tokenIssued);
        System.out.println("Дiє до:    " + tokenExp);
        System.out.println("Перевiрка: " + check);
        System.out.println("Прострочений? " + check.isAfter(tokenExp));
    }
}
