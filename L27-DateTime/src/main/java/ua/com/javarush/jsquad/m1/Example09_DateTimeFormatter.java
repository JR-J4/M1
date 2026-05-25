package ua.com.javarush.jsquad.m1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Лекція 27: Робота iз часом i датою.
 * <p>
 * Тема: Клас DateTimeFormatter — форматування i парсинг у Date Time API.
 * <p>
 * Це сучасний аналог SimpleDateFormat: легше використовується, потокобезпечний,
 * працює з LocalDate / LocalTime / LocalDateTime / ZonedDateTime.
 * <p>
 * Основнi букви шаблону:
 * <table border="1">
 *   <tr><td>y</td><td>рiк</td></tr>
 *   <tr><td>M</td><td>мiсяць</td></tr>
 *   <tr><td>d</td><td>день</td></tr>
 *   <tr><td>H</td><td>години (0-23)</td></tr>
 *   <tr><td>m</td><td>хвилини</td></tr>
 *   <tr><td>s</td><td>секунди</td></tr>
 *   <tr><td>E</td><td>день тижня</td></tr>
 *   <tr><td>a</td><td>AM/PM</td></tr>
 * </table>
 * <p>
 * Аналогiя: DateTimeFormatter — це двостороннiй перекладач. Скажеш йому
 * "dd.MM.yyyy" — i вiн перекладає дату в "14.08.2024" i назад.
 * <p>
 * Реальне застосування: вiдображення дат користувачу, експорт у CSV/JSON,
 * читання дат з форм, конфiгiв або файлiв логiв.
 */
public class Example09_DateTimeFormatter {

    public static void main(String[] args) {
        // === Блок 1: format — об'єкт у рядок ===
        // Сценарiй: показуємо дату користувачу в звичному форматi.
        System.out.println("=== format() ===");
        LocalDate date = LocalDate.of(2024, 5, 18);
        DateTimeFormatter dmy = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String text = date.format(dmy);
        System.out.println("Дата:    " + date);   // 2024-05-18
        System.out.println("Формат:  " + text);    // 18.05.2024

        System.out.println();

        // === Блок 2: рiзнi шаблони — один LocalDate ===
        // Сценарiй: експорт у рiзнi системи.
        System.out.println("=== Рiзнi формати ===");
        System.out.println("ISO:     " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("USA:     " + date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        System.out.println("Короткий: " + date.format(DateTimeFormatter.ofPattern("d MMM yyyy")));

        System.out.println();

        // === Блок 3: форматування LocalDateTime ===
        // Сценарiй: timestamp для логу.
        System.out.println("=== Формат з часом ===");
        LocalDateTime moment = LocalDateTime.of(2024, 5, 18, 14, 30, 15);
        DateTimeFormatter logFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Лог:     " + moment.format(logFmt));

        DateTimeFormatter chatFmt = DateTimeFormatter.ofPattern("dd MMM, HH:mm");
        System.out.println("Чат:     " + moment.format(chatFmt));

        System.out.println();

        // === Блок 4: локалiзованi формати ===
        // Сценарiй: красивий вiдгук для українця.
        System.out.println("=== З локалiзацiєю ===");
        DateTimeFormatter ukFmt = DateTimeFormatter
                .ofPattern("EEEE, dd MMMM yyyy 'року'", new Locale("uk"));
        System.out.println("Запрошення: " + date.format(ukFmt));

        DateTimeFormatter enFmt = DateTimeFormatter
                .ofPattern("EEEE, MMMM dd yyyy", Locale.ENGLISH);
        System.out.println("Англiйською: " + date.format(enFmt));

        System.out.println();

        // === Блок 5: parse — рядок у об'єкт ===
        // Сценарiй: користувач ввiв дату у формi.
        System.out.println("=== parse() ===");
        DateTimeFormatter input = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate parsed = LocalDate.parse("14.08.2024", input);
        System.out.println("Введено:  '14.08.2024'");
        System.out.println("Розiбрано: " + parsed);
        System.out.println("Рiк:       " + parsed.getYear());

        System.out.println();

        // === Блок 6: parse з помилкою ===
        // Сценарiй: некоректний ввiд — треба обробити.
        System.out.println("=== Помилка парсингу ===");
        try {
            LocalDate.parse("неправильний рядок", input);
        } catch (DateTimeParseException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        System.out.println();

        // === Блок 7: вбудованi форматтери ===
        // Сценарiй: ISO-формат уже готовий, не треба писати шаблон.
        System.out.println("=== Готовi форматтери ===");
        System.out.println("ISO_LOCAL_DATE:      " + date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println("ISO_LOCAL_DATE_TIME: " + moment.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println("BASIC_ISO_DATE:      " + date.format(DateTimeFormatter.BASIC_ISO_DATE)); // 20240518

        System.out.println();

        // === Блок 8: формат LocalTime ===
        // Сценарiй: показуємо час без секунд.
        System.out.println("=== Лише час ===");
        LocalTime time = LocalTime.of(14, 30, 15);
        DateTimeFormatter hm = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter hms = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter h12 = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
        System.out.println("HH:mm:    " + time.format(hm));
        System.out.println("HH:mm:ss: " + time.format(hms));
        System.out.println("12-год:   " + time.format(h12));   // 02:30 PM
    }
}
