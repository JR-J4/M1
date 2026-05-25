package ua.com.javarush.jsquad.m1;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/**
 * Лекція 27: Робота iз часом i датою.
 * <p>
 * Тема: Клас LocalDate з пакета java.time (Date Time API, Java 8+).
 * <p>
 * LocalDate — це лише дата (рiк-мiсяць-день), без часу i без часової зони.
 * Об'єкти LocalDate <b>незмiннi</b> (immutable): методи на кшталт plusDays
 * не змiнюють поточний об'єкт, а повертають новий — як у класi String.
 * <p>
 * Синтаксис:
 * <pre>
 *   LocalDate today = LocalDate.now();
 *   LocalDate d     = LocalDate.of(2024, Month.MAY, 18);   // мiсяцi з 1!
 * </pre>
 * <p>
 * Аналогiя: LocalDate — це листочок календаря на стiнi: лише число, мiсяць i рiк.
 * Часу там немає. I якщо ти хочеш iншу дату — береш iнший листочок, а не
 * виправляєш цей.
 * <p>
 * Реальне застосування: дата народження, дата завершення курсу, дедлайни,
 * розклад занять — все, де потрiбна лише дата.
 */
public class Example04_LocalDate {

    public static void main(String[] args) {
        // === Блок 1: поточна дата ===
        // Сценарiй: ставимо штамп "видано" на документi.
        System.out.println("=== LocalDate.now() ===");
        LocalDate today = LocalDate.now();
        System.out.println("Сьогоднi: " + today);  // 2026-05-25 — формат ISO

        System.out.println();

        // === Блок 2: конкретна дата через of() ===
        // Сценарiй: дата народження клiєнта.
        // ВАЖЛИВО: тут мiсяцi нумеруються з 1 (на вiдмiну вiд Date/Calendar)!
        System.out.println("=== LocalDate.of(...) ===");
        LocalDate birthday1 = LocalDate.of(1995, Month.AUGUST, 14);  // через константу
        LocalDate birthday2 = LocalDate.of(1995, 8, 14);              // через число
        System.out.println("Через Month:  " + birthday1);
        System.out.println("Через число:  " + birthday2);
        System.out.println("Однаковi?     " + birthday1.equals(birthday2));

        System.out.println();

        // === Блок 3: getters — окремi фрагменти ===
        // Сценарiй: розкладаємо дату народження на частини для анкети.
        System.out.println("=== Фрагменти дати ===");
        LocalDate d = LocalDate.of(2024, Month.MAY, 18);
        System.out.println("Рiк:           " + d.getYear());
        System.out.println("Мiсяць:        " + d.getMonth());       // MAY
        System.out.println("Номер мiсяця:  " + d.getMonthValue());   // 5
        System.out.println("День мiсяця:   " + d.getDayOfMonth());
        System.out.println("День тижня:    " + d.getDayOfWeek());    // SATURDAY
        System.out.println("День року:     " + d.getDayOfYear());    // 139
        System.out.println("Високосний?    " + d.isLeapYear());

        System.out.println();

        // === Блок 4: plus/minus — нова дата, поточна не мiняється ===
        // Сценарiй: розрахунок дати повернення книги.
        System.out.println("=== plusDays / minusDays ===");
        LocalDate borrow = LocalDate.of(2024, 5, 18);
        LocalDate due = borrow.plusDays(14);  // повернути через 14 днiв
        System.out.println("Взято:    " + borrow);
        System.out.println("Повернути: " + due);
        System.out.println("borrow не змiнилось: " + borrow);  // важлива деталь!

        System.out.println();

        // === Блок 5: рiзнi одиницi — днi, тижнi, мiсяцi, роки ===
        // Сценарiй: плануємо контрольнi точки проєкту.
        System.out.println("=== Рiзнi одиницi ===");
        LocalDate start = LocalDate.of(2024, 1, 10);
        System.out.println("Старт:           " + start);
        System.out.println("+ 3 днi:         " + start.plusDays(3));
        System.out.println("+ 2 тижнi:       " + start.plusWeeks(2));
        System.out.println("+ 1 мiсяць:      " + start.plusMonths(1));
        System.out.println("+ 1 рiк:         " + start.plusYears(1));
        System.out.println("- 5 днiв:        " + start.minusDays(5));

        System.out.println();

        // === Блок 6: ланцюжок викликiв ===
        // Сценарiй: рiк i мiсяць пiсля старту проєкту.
        System.out.println("=== Ланцюжок (chaining) ===");
        LocalDate later = start.plusYears(1).plusMonths(2).plusDays(10);
        System.out.println("Старт + 1р + 2м + 10д = " + later);

        System.out.println();

        // === Блок 7: порiвняння дат ===
        // Сценарiй: чи прострочений дедлайн?
        System.out.println("=== Порiвняння ===");
        LocalDate deadline = LocalDate.of(2024, 12, 31);
        LocalDate now = LocalDate.of(2025, 1, 15);
        System.out.println("Дедлайн:  " + deadline);
        System.out.println("Зараз:    " + now);
        System.out.println("Зараз пiсля? " + now.isAfter(deadline));
        System.out.println("Зараз до?    " + now.isBefore(deadline));
        System.out.println("Однаковi?    " + now.isEqual(deadline));

        System.out.println();

        // === Блок 8: практика — наступний понедiлок ===
        // Сценарiй: запис на курс щовiвторка — знайти найближчий.
        System.out.println("=== Найближчий вiвторок ===");
        LocalDate cursor = today;
        while (cursor.getDayOfWeek() != DayOfWeek.TUESDAY) {
            cursor = cursor.plusDays(1);
        }
        System.out.println("Сьогоднi:           " + today + " (" + today.getDayOfWeek() + ")");
        System.out.println("Найближчий вiвторок: " + cursor);
    }
}
