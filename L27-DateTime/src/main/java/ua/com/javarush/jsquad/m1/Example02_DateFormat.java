package ua.com.javarush.jsquad.m1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Лекція 27: Робота iз часом i датою.
 * <p>
 * Тема: SimpleDateFormat — форматування об'єкта Date.
 * <p>
 * За замовчуванням Date виводиться як {@code Thu Feb 21 14:01:34 EET 2019}.
 * Для програмiста це нормально, але для користувача хочеться щось на кшталт
 * "21 лютого 2019 року, 14:01". Саме для цього є клас {@code SimpleDateFormat}:
 * вiн перетворює Date в рядок i навпаки за заданим шаблоном.
 * <p>
 * Основнi букви шаблону:
 * <ul>
 *   <li>{@code y} — рiк, {@code M} — мiсяць, {@code d} — день</li>
 *   <li>{@code H} — години 0-23, {@code m} — хвилини, {@code s} — секунди</li>
 *   <li>{@code E} — день тижня</li>
 * </ul>
 * <p>
 * Аналогiя: SimpleDateFormat — це перекладач, який знає шаблон.
 * Скажеш йому "dd.MM.yyyy" — поверне "14.08.2005". Скажеш "yyyy-MM-dd" — "2005-08-14".
 * <p>
 * Реальне застосування: вiдображення дат на сторiнцi, у звiтах, лог-файлах;
 * читання дат з текстових файлiв або з форм користувача.
 */
public class Example02_DateFormat {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws ParseException {
        // === Блок 1: проблема — за замовчуванням некрасиво ===
        // Сценарiй: показуємо клiєнту, коли його замовлення оформлено.
        System.out.println("=== Що видає Date за замовчуванням ===");
        Date order = new Date(124, 4, 18, 9, 45, 0);  // 18.05.2024 09:45
        System.out.println("Замовлення: " + order);
        System.out.println("(виглядає нерозбiрливо для звичайного користувача)");

        System.out.println();

        // === Блок 2: простий формат "день.мiсяць.рiк" ===
        // Сценарiй: європейський формат дати — найзвичнiший для нас.
        System.out.println("=== Формат dd.MM.yyyy ===");
        SimpleDateFormat dmy = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println("Замовлення: " + dmy.format(order));   // 18.05.2024

        System.out.println();

        // === Блок 3: американський i ISO формати ===
        // Сценарiй: експорт у мiжнароднi системи.
        System.out.println("=== Рiзнi формати дати ===");
        SimpleDateFormat usa = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("USA:   " + usa.format(order));        // 05/18/2024
        System.out.println("ISO:   " + iso.format(order));        // 2024-05-18

        System.out.println();

        // === Блок 4: формат з часом ===
        // Сценарiй: розклад автобусiв — потрiбен день i час.
        System.out.println("=== Дата з часом ===");
        SimpleDateFormat full = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        System.out.println("Вiдправлення: " + full.format(order));  // 18.05.2024 09:45:00

        System.out.println();

        // === Блок 5: день тижня + назва мiсяця ===
        // Сценарiй: красиве запрошення на захiд.
        System.out.println("=== З днем тижня i назвою мiсяця ===");
        SimpleDateFormat pretty = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("uk"));
        System.out.println("Захiд: " + pretty.format(order));       // субота, 18 травня 2024

        System.out.println();

        // === Блок 6: парсинг — рядок -> Date ===
        // Сценарiй: користувач ввiв "25.12.2024" — треба отримати Date.
        System.out.println("=== Парсинг рядка в Date ===");
        SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy");
        Date holiday = parser.parse("25.12.2024");
        System.out.println("Розiбрано: " + holiday);
        System.out.println("Рiк:   " + (holiday.getYear() + 1900));
        System.out.println("Мiсяць: " + (holiday.getMonth() + 1));
        System.out.println("День:   " + holiday.getDate());

        System.out.println();

        // === Блок 7: рiзнi шаблони — однiй i тiй же датi ===
        // Сценарiй: одне замовлення треба показати у логу, у листi i на чеку.
        System.out.println("=== Один Date, три формати ===");
        System.out.println("Лог:  " + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(order));
        System.out.println("Лист: " + new SimpleDateFormat("dd MMMM yyyy 'року о' HH:mm", new Locale("uk")).format(order));
        System.out.println("Чек:  " + new SimpleDateFormat("dd/MM/yy HH:mm").format(order));
    }
}
