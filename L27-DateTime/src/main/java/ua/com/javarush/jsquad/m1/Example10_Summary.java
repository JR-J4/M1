package ua.com.javarush.jsquad.m1;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Лекція 27: Робота iз часом i датою.
 * <p>
 * Тема: Пiдсумок — мiжнародна онлайн-конференцiя.
 * <p>
 * Сценарiй: ми органiзовуємо вебiнар. Треба:
 * <ol>
 *   <li>Зафiксувати дату i час старту в Києвi;</li>
 *   <li>Згенерувати розклад зустрiчей (раз на тиждень × 4);</li>
 *   <li>Показати час старту в зонах учасникiв (Київ, Нью-Йорк, Токiо);</li>
 *   <li>Сформувати красивi запрошення з локалiзацiєю;</li>
 *   <li>Парсити вiдповiдь учасника з рядка у LocalDate;</li>
 *   <li>Замiряти, скiльки часу зайняла обробка;</li>
 *   <li>Розрахувати, скiльки днiв залишилось до старту.</li>
 * </ol>
 * <p>
 * Використанi теми лекцiї: LocalDate, LocalDateTime, ZonedDateTime, ZoneId,
 * DateTimeFormatter (format i parse), Instant, Duration.
 */
public class Example10_Summary {

    public static void main(String[] args) {
        // === Крок 1: старт вебiнару в Києвi ===
        // Сценарiй: задаємо точну дату i час локально.
        System.out.println("=== Крок 1: створення зустрiчi ===");
        ZoneId kyiv = ZoneId.of("Europe/Kyiv");
        LocalDateTime localStart = LocalDateTime.of(2026, Month.JUNE, 1, 19, 0);
        ZonedDateTime kyivStart = localStart.atZone(kyiv);
        System.out.println("Старт у Києвi: " + kyivStart);

        System.out.println();

        // === Крок 2: генеруємо 4 щотижневих зустрiчi ===
        // Сценарiй: курс iз 4 занять, по одному на тиждень.
        System.out.println("=== Крок 2: розклад занять ===");
        List<ZonedDateTime> sessions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            sessions.add(kyivStart.plusWeeks(i));
        }
        for (int i = 0; i < sessions.size(); i++) {
            System.out.println("Заняття " + (i + 1) + ": " + sessions.get(i).toLocalDate()
                    + " (" + sessions.get(i).getDayOfWeek() + ")");
        }

        System.out.println();

        // === Крок 3: переводимо старт у зони учасникiв ===
        // Сценарiй: учасники з рiзних куточкiв свiту.
        System.out.println("=== Крок 3: коли почнемо у трьох мiстах ===");
        ZoneId nyc = ZoneId.of("America/New_York");
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        System.out.println("Київ:     " + kyivStart);
        System.out.println("Нью-Йорк: " + kyivStart.withZoneSameInstant(nyc));
        System.out.println("Токiо:    " + kyivStart.withZoneSameInstant(tokyo));

        System.out.println();

        // === Крок 4: красивi запрошення ===
        // Сценарiй: пишемо тексти для двох мов.
        System.out.println("=== Крок 4: запрошення ===");
        DateTimeFormatter uk = DateTimeFormatter
                .ofPattern("EEEE, dd MMMM yyyy 'року, о' HH:mm", new Locale("uk"));
        DateTimeFormatter en = DateTimeFormatter
                .ofPattern("EEEE, MMMM dd yyyy 'at' HH:mm", Locale.ENGLISH);

        System.out.println("UA: " + kyivStart.format(uk));
        System.out.println("EN: " + kyivStart.withZoneSameInstant(nyc).format(en));

        System.out.println();

        // === Крок 5: парсимо вiдповiдь учасника ===
        // Сценарiй: учасник пiдтвердив участь i написав свою дату народження.
        System.out.println("=== Крок 5: парсинг дати ===");
        DateTimeFormatter input = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate dob = LocalDate.parse("14.08.1995", input);
        System.out.println("Дата народження: " + dob);
        System.out.println("Вiк (приблизно): " + (LocalDate.now().getYear() - dob.getYear()));

        System.out.println();

        // === Крок 6: замiр часу обробки ===
        // Сценарiй: треба знати, чи робота швидка.
        System.out.println("=== Крок 6: бенчмарк ===");
        Instant t1 = Instant.now();

        long sum = 0;
        for (int i = 0; i < 1_000_000; i++) sum += i;

        Instant t2 = Instant.now();
        Duration elapsed = Duration.between(t1, t2);
        System.out.println("Сума:            " + sum);
        System.out.println("Часу витрачено:  " + elapsed.toMillis() + " мс");

        System.out.println();

        // === Крок 7: скiльки днiв до старту ===
        // Сценарiй: показуємо зворотний вiдлiк на сайтi.
        System.out.println("=== Крок 7: вiдлiк ===");
        LocalDate today = LocalDate.now();
        LocalDate startDate = kyivStart.toLocalDate();
        long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(today, startDate);
        System.out.println("Сьогоднi:       " + today);
        System.out.println("Старт:          " + startDate);
        System.out.println("Залишилось днiв: " + daysLeft);

        System.out.println();

        // === Висновок ===
        System.out.println("=== Висновок ===");
        System.out.println("• Date/Calendar — для сумiсностi зi старим кодом");
        System.out.println("• LocalDate/LocalTime/LocalDateTime — основа сучасної роботи з часом");
        System.out.println("• Instant — точнi timestamp i бенчмаркiнг");
        System.out.println("• ZonedDateTime — мiжнароднi сценарiї");
        System.out.println("• DateTimeFormatter — форматування i парсинг");
    }
}
