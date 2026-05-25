package ua.com.javarush.jsquad.m1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

/**
 * Лекція 27: Робота iз часом i датою.
 * <p>
 * Тема: Клас LocalDateTime — поєднання LocalDate i LocalTime.
 * <p>
 * LocalDateTime зберiгає i дату, i час, але без часової зони. Об'єкти теж
 * незмiннi, методи аналогiчнi LocalDate i LocalTime разом узятим.
 * <p>
 * Синтаксис:
 * <pre>
 *   LocalDateTime now = LocalDateTime.now();
 *   LocalDateTime dt  = LocalDateTime.of(2024, Month.MAY, 18, 14, 30);
 *   LocalDateTime dt2 = LocalDateTime.of(date, time);  // з частин
 * </pre>
 * <p>
 * Аналогiя: LocalDateTime — це запис у щоденнику: "18 травня 2024, 14:30 —
 * зустрiч з клiєнтом". Знаємо i день, i час, але без уточнення часової зони.
 * <p>
 * Реальне застосування: момент створення замовлення, час публiкацiї поста,
 * розклад зустрiчей, точки на часовiй шкалi подiй.
 */
public class Example06_LocalDateTime {

    public static void main(String[] args) {
        // === Блок 1: поточний момент ===
        // Сценарiй: фiксуємо створення замовлення.
        System.out.println("=== LocalDateTime.now() ===");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Замовлення створено: " + now);

        System.out.println();

        // === Блок 2: конкретний момент ===
        // Сценарiй: зустрiч з клiєнтом — точна дата i час.
        System.out.println("=== LocalDateTime.of(...) ===");
        LocalDateTime meeting1 = LocalDateTime.of(2024, Month.MAY, 18, 14, 30);
        LocalDateTime meeting2 = LocalDateTime.of(2024, 5, 18, 14, 30, 0);
        System.out.println("Зустрiч (Month):  " + meeting1);
        System.out.println("Зустрiч (число):  " + meeting2);

        System.out.println();

        // === Блок 3: складаємо з LocalDate + LocalTime ===
        // Сценарiй: у нас вже є окремо дата i час — об'єднуємо.
        System.out.println("=== Зборка з частин ===");
        LocalDate d = LocalDate.of(2024, 12, 31);
        LocalTime t = LocalTime.of(23, 59);
        LocalDateTime nyEve = LocalDateTime.of(d, t);
        System.out.println("Новорiчний бiй курантiв: " + nyEve);

        System.out.println();

        // === Блок 4: getters — всi фрагменти ===
        // Сценарiй: розкладаємо момент для звiту.
        System.out.println("=== Усi фрагменти ===");
        LocalDateTime dt = LocalDateTime.of(2024, 8, 14, 10, 25, 47);
        System.out.println("Рiк:        " + dt.getYear());
        System.out.println("Мiсяць:     " + dt.getMonth());
        System.out.println("День:       " + dt.getDayOfMonth());
        System.out.println("День тижня: " + dt.getDayOfWeek());
        System.out.println("Години:     " + dt.getHour());
        System.out.println("Хвилини:    " + dt.getMinute());
        System.out.println("Секунди:    " + dt.getSecond());

        System.out.println();

        // === Блок 5: видiлення дати або часу окремо ===
        // Сценарiй: вiд повного моменту хочемо тiльки дату для пошуку у звiтах.
        System.out.println("=== toLocalDate / toLocalTime ===");
        LocalDate onlyDate = dt.toLocalDate();
        LocalTime onlyTime = dt.toLocalTime();
        System.out.println("Лише дата: " + onlyDate);
        System.out.println("Лише час:  " + onlyTime);

        System.out.println();

        // === Блок 6: plus/minus — змiщення в часi ===
        // Сценарiй: нагадування за 30 хвилин до зустрiчi.
        System.out.println("=== plus / minus ===");
        LocalDateTime appointment = LocalDateTime.of(2024, 5, 20, 15, 0);
        LocalDateTime reminder = appointment.minusMinutes(30);
        LocalDateTime followUp = appointment.plusDays(7);
        System.out.println("Прийом:       " + appointment);
        System.out.println("Нагадування:  " + reminder);
        System.out.println("Контроль:     " + followUp);

        System.out.println();

        // === Блок 7: змiшанi змiни одним ланцюжком ===
        // Сценарiй: переносимо зустрiч на 2 днi пiзнiше i на годину ранiше.
        System.out.println("=== Ланцюжок ===");
        LocalDateTime planned = LocalDateTime.of(2024, 5, 18, 14, 0);
        LocalDateTime updated = planned.plusDays(2).minusHours(1);
        System.out.println("Було:  " + planned);
        System.out.println("Стало: " + updated);
        System.out.println("Оригiнал не змiнено: " + planned);

        System.out.println();

        // === Блок 8: порiвняння двох моментiв ===
        // Сценарiй: чи дедлайн вже минув?
        System.out.println("=== Порiвняння ===");
        LocalDateTime deadline = LocalDateTime.of(2024, 12, 31, 23, 59);
        LocalDateTime current = LocalDateTime.of(2025, 1, 1, 0, 0);
        System.out.println("Дедлайн:   " + deadline);
        System.out.println("Зараз:     " + current);
        System.out.println("Минув?     " + current.isAfter(deadline));
        System.out.println("Ще встигаю? " + current.isBefore(deadline));
    }
}
