package ua.com.javarush.jsquad.m1;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Лекція 27: Робота iз часом i датою.
 * <p>
 * Тема: Калас Calendar — наступник Date з бiльшими можливостями.
 * <p>
 * Calendar — це абстрактний клас, найпоширенiший нащадок — {@link GregorianCalendar}
 * (григорiанський календар, той самий, яким ми всi користуємось). Є ще:
 * <ul>
 *   <li>BuddhistCalendar — буддистський</li>
 *   <li>JapaneseImperialCalendar — японський iмператорський</li>
 * </ul>
 * <p>
 * Синтаксис:
 * <pre>
 *   Calendar c = Calendar.getInstance();         // поточний момент
 *   Calendar c = new GregorianCalendar(2024, Calendar.MAY, 18);
 *   int year   = c.get(Calendar.YEAR);
 *   c.set(Calendar.MONTH, Calendar.DECEMBER);
 * </pre>
 * <p>
 * Аналогiя: Calendar — це уже не просто секундомiр, а повноцiнний
 * настiльний календар: можна гортати сторiнки (мiсяцi), вiдмiчати
 * днi, питати "а який це день тижня?".
 * <p>
 * Реальне застосування: ще трапляється в кодi, але для нових проєктiв
 * рекомендується LocalDate/LocalDateTime з пакета java.time.
 */
public class Example03_Calendar {

    public static void main(String[] args) {
        // === Блок 1: поточна дата через getInstance() ===
        // Сценарiй: фабричний метод повертає календар для нашої локалi.
        System.out.println("=== Calendar.getInstance() ===");
        Calendar today = Calendar.getInstance();
        System.out.println("Сьогоднi: " + today.getTime());  // toString виглядає погано — беремо Date

        System.out.println();

        // === Блок 2: створення конкретної дати ===
        // Сценарiй: дата заснування компанiї.
        // Мiсяцi нумеруються з нуля — для зручностi є константи.
        System.out.println("=== GregorianCalendar(рiк, мiсяць, день) ===");
        Calendar founded = new GregorianCalendar(2010, Calendar.MAY, 18);
        System.out.println("Компанiя заснована: " + founded.getTime());

        Calendar exact = new GregorianCalendar(2024, Calendar.NOVEMBER, 25, 14, 30, 0);
        System.out.println("Точний момент:      " + exact.getTime());

        System.out.println();

        // === Блок 3: метод get() — отримання фрагментiв ===
        // Сценарiй: показуємо рiк, мiсяць, день тижня у звiтi.
        System.out.println("=== calendar.get(...) ===");
        Calendar c = new GregorianCalendar(2024, Calendar.AUGUST, 14, 10, 25, 0);
        System.out.println("Рiк:        " + c.get(Calendar.YEAR));
        System.out.println("Мiсяць:     " + c.get(Calendar.MONTH));        // 7 (серпень нумерується з 0)
        System.out.println("День мiс.:  " + c.get(Calendar.DAY_OF_MONTH));
        System.out.println("День тижня: " + c.get(Calendar.DAY_OF_WEEK));  // 1=недiля, 2=пн, ..., 7=сб
        System.out.println("Години:     " + c.get(Calendar.HOUR_OF_DAY));  // 0-23
        System.out.println("Хвилини:    " + c.get(Calendar.MINUTE));
        System.out.println("День року:  " + c.get(Calendar.DAY_OF_YEAR));  // 1-366

        System.out.println();

        // === Блок 4: метод set() — змiна фрагментiв ===
        // Сценарiй: переносимо дедлайн з 14 серпня на 20 вересня.
        System.out.println("=== calendar.set(...) ===");
        Calendar deadline = new GregorianCalendar(2024, Calendar.AUGUST, 14);
        System.out.println("Було:  " + deadline.getTime());

        deadline.set(Calendar.MONTH, Calendar.SEPTEMBER);
        deadline.set(Calendar.DAY_OF_MONTH, 20);
        System.out.println("Стало: " + deadline.getTime());

        System.out.println();

        // === Блок 5: add() — додавання рiзних одиниць ===
        // Сценарiй: розрахунок дати повернення книги (14 днiв з сьогоднi).
        System.out.println("=== calendar.add(...) ===");
        Calendar borrow = new GregorianCalendar(2024, Calendar.MAY, 18);
        System.out.println("Книгу взято: " + borrow.getTime());

        borrow.add(Calendar.DAY_OF_MONTH, 14);  // +14 днiв
        System.out.println("Повернути:    " + borrow.getTime());

        borrow.add(Calendar.MONTH, -1);          // -1 мiсяць
        System.out.println("Якщо мiнус мiсяць: " + borrow.getTime());

        System.out.println();

        // === Блок 6: переведення Calendar -> Date i назад ===
        // Сценарiй: стара бiблiотека хоче Date, наш код тримає Calendar.
        System.out.println("=== Calendar <-> Date ===");
        Calendar cal = new GregorianCalendar(2024, Calendar.JULY, 4, 12, 0, 0);
        java.util.Date asDate = cal.getTime();             // Calendar -> Date
        System.out.println("Як Date:     " + asDate);

        Calendar back = Calendar.getInstance();
        back.setTime(asDate);                              // Date -> Calendar
        System.out.println("Назад рiк:   " + back.get(Calendar.YEAR));

        System.out.println();

        // === Блок 7: автокорекцiя дати ===
        // Сценарiй: задаємо "32 сiчня" — Calendar сам перетворить на 1 лютого.
        System.out.println("=== Автокорекцiя ===");
        Calendar weird = new GregorianCalendar(2024, Calendar.JANUARY, 32);
        System.out.println("Заданий 32 сiчня -> " + weird.getTime());

        Calendar wrap = new GregorianCalendar(2024, Calendar.DECEMBER, 31);
        wrap.add(Calendar.DAY_OF_MONTH, 1);
        System.out.println("31.12 + 1 день -> " + wrap.getTime());  // переходить у новий рiк
    }
}
