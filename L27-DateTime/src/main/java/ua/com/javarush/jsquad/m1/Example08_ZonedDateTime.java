package ua.com.javarush.jsquad.m1;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.TreeSet;

/**
 * Лекція 27: Робота iз часом i датою.
 * <p>
 * Тема: Клас ZonedDateTime — час iз врахуванням часового поясу.
 * <p>
 * Свiт великий. Зараз офiцiйно вiдомо <b>599 часових зон</b> (не 24!).
 * Якщо твiй застосунок працює з користувачами з рiзних країн, треба
 * вмiти переводити час мiж зонами — для цього є ZonedDateTime.
 * <p>
 * Синтаксис:
 * <pre>
 *   ZoneId zone = ZoneId.of("Europe/Kyiv");
 *   ZonedDateTime here = ZonedDateTime.now(zone);
 *   ZonedDateTime there = here.withZoneSameInstant(ZoneId.of("America/New_York"));
 * </pre>
 * <p>
 * Аналогiя: ZonedDateTime — це фото з геолокацiєю. Знаємо не лише
 * "коли", а й "де". Той самий момент в Нью-Йорку i Києвi виглядає
 * на годинниках по-рiзному.
 * <p>
 * Реальне застосування: дзвiнки/зустрiчi в мiжнароднiй командi,
 * розклад рейсiв, бронювання готелiв у рiзних країнах.
 */
public class Example08_ZonedDateTime {

    public static void main(String[] args) {
        // === Блок 1: ZoneId i зона за замовчуванням ===
        // Сценарiй: дiзнаємось зону, в якiй працює наш сервер.
        System.out.println("=== ZoneId.systemDefault() ===");
        ZoneId defaultZone = ZoneId.systemDefault();
        System.out.println("Зона цього комп'ютера: " + defaultZone);

        System.out.println();

        // === Блок 2: створюємо ZoneId за iменем ===
        // Сценарiй: пишемо чiткi зони, не покладаючись на замовчування.
        System.out.println("=== ZoneId.of(...) ===");
        ZoneId kyiv = ZoneId.of("Europe/Kyiv");
        ZoneId newYork = ZoneId.of("America/New_York");
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        System.out.println("Київ:     " + kyiv);
        System.out.println("Нью-Йорк: " + newYork);
        System.out.println("Токiо:    " + tokyo);

        System.out.println();

        // === Блок 3: ZonedDateTime.now(zone) ===
        // Сценарiй: що зараз на годинниках у трьох мiстах?
        System.out.println("=== Котра година зараз ===");
        ZonedDateTime nowKyiv = ZonedDateTime.now(kyiv);
        ZonedDateTime nowNyc = ZonedDateTime.now(newYork);
        ZonedDateTime nowTokyo = ZonedDateTime.now(tokyo);
        System.out.println("Київ:     " + nowKyiv);
        System.out.println("Нью-Йорк: " + nowNyc);
        System.out.println("Токiо:    " + nowTokyo);

        System.out.println();

        // === Блок 4: один момент — рiзнi зони ===
        // Сценарiй: 14:00 в Києвi — котра година в Нью-Йорку?
        System.out.println("=== withZoneSameInstant ===");
        ZonedDateTime kyivMeeting = LocalDateTime.of(2024, 5, 18, 14, 0)
                .atZone(kyiv);
        ZonedDateTime nycMeeting = kyivMeeting.withZoneSameInstant(newYork);
        ZonedDateTime tokyoMeeting = kyivMeeting.withZoneSameInstant(tokyo);

        System.out.println("Зустрiч у Києвi:     " + kyivMeeting);
        System.out.println("Те ж саме в Нью-Йорку: " + nycMeeting);
        System.out.println("Те ж саме в Токiо:     " + tokyoMeeting);

        System.out.println();

        // === Блок 5: рiзниця withZoneSameLocal vs withZoneSameInstant ===
        // Сценарiй: важливо розумiти рiзницю.
        //  - SameInstant: "той самий момент" (час змiнюється, зона мiняється)
        //  - SameLocal:   "те саме показання годинника" (момент змiнюється, зона мiняється)
        System.out.println("=== SameInstant vs SameLocal ===");
        ZonedDateTime original = LocalDateTime.of(2024, 6, 1, 12, 0).atZone(kyiv);
        ZonedDateTime sameInstant = original.withZoneSameInstant(tokyo);
        ZonedDateTime sameLocal = original.withZoneSameLocal(tokyo);

        System.out.println("Оригiнал (Київ):     " + original);
        System.out.println("Same Instant (Tokyo): " + sameInstant);  // той самий момент
        System.out.println("Same Local   (Tokyo): " + sameLocal);    // тi ж 12:00, але в Токiо

        System.out.println();

        // === Блок 6: getAvailableZoneIds — скiльки зон у свiтi ===
        // Сценарiй: подивимось перших кiлька зон зi списку.
        System.out.println("=== Скiльки зон вiдомо Java ===");
        Set<String> all = ZoneId.getAvailableZoneIds();
        System.out.println("Всього зон: " + all.size());

        TreeSet<String> sorted = new TreeSet<>(all);
        System.out.println("Першi 5 зон зi списку:");
        int shown = 0;
        for (String id : sorted) {
            System.out.println("  " + id);
            if (++shown == 5) break;
        }

        System.out.println();

        // === Блок 7: plus/minus у ZonedDateTime ===
        // Сценарiй: рейс вiдправлення + 11 годин польоту.
        System.out.println("=== plusHours враховує зону ===");
        ZonedDateTime departure = LocalDateTime.of(2024, 7, 15, 22, 0).atZone(kyiv);
        ZonedDateTime arrival = departure.plusHours(11).withZoneSameInstant(tokyo);
        System.out.println("Вилiт з Києва:   " + departure);
        System.out.println("Посадка в Токiо: " + arrival);
    }
}
