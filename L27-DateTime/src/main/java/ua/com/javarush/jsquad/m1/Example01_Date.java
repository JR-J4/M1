package ua.com.javarush.jsquad.m1;

import java.util.Date;

/**
 * Лекція 27: Робота iз часом i датою.
 * <p>
 * Тема: Клас Date — найстарший спосiб роботи з датою i часом у Java.
 * <p>
 * Date зберiгає момент часу як кiлькiсть мiлiсекунд, що минули з
 * 1 сiчня 1970 року (так звана "епоха Unix"). Щоб помiстити таку
 * велику кiлькiсть, всерединi використовується тип {@code long}.
 * <p>
 * Синтаксис:
 * <pre>
 *   Date now = new Date();                   // поточний момент
 *   Date d   = new Date(year, month, day);   // конкретна дата (deprecated)
 * </pre>
 * <p>
 * Аналогiя: Date — це фотографiя секундомiра, який почав вiдлiк
 * 1 сiчня 1970. У нього один великий лiчильник тикiв, i все.
 * <p>
 * Реальне застосування: Date досi трапляється в старому кодi,
 * у бiблiотеках, у БД. У новому кодi краще обирати LocalDateTime
 * або Instant — про них поговоримо в наступних прикладах.
 */
public class Example01_Date {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        // === Блок 1: поточна дата i час ===
        // Сценарiй: фiксуємо момент входу користувача в систему.
        System.out.println("=== Поточний момент ===");
        Date current = new Date();
        System.out.println("Логiн зафiксовано: " + current);

        System.out.println();

        // === Блок 2: всерединi — мiлiсекунди з 1970 року ===
        // Сценарiй: показуємо, що насправдi зберiгає Date.
        System.out.println("=== Мiлiсекунди з 01.01.1970 ===");
        long millis = current.getTime();
        System.out.println("Кiлькiсть мiлiсекунд: " + millis);
        System.out.println("Це приблизно " + (millis / 1000L / 60 / 60 / 24 / 365) + " рокiв");

        System.out.println();

        // === Блок 3: задаємо конкретну дату ===
        // Сценарiй: записуємо дату народження клiєнта.
        // УВАГА: рiк задається вiд 1900, мiсяцi нумеруються з нуля!
        // 2005 рiк -> 2005 - 1900 = 105;  серпень (8-й мiсяць) -> 7.
        System.out.println("=== Конкретна дата (deprecated конструктор) ===");
        Date birthday = new Date(105, 7, 14);  // 14 серпня 2005
        System.out.println("День народження: " + birthday);

        System.out.println();

        // === Блок 4: дата з часом ===
        // Сценарiй: точний момент пiдписання договору.
        // Конструктор: рiк, мiсяць, день, години, хвилини, секунди.
        System.out.println("=== Дата i час ===");
        Date contract = new Date(124, 2, 15, 10, 30, 0);  // 15.03.2024 10:30:00
        System.out.println("Договiр пiдписано: " + contract);

        System.out.println();

        // === Блок 5: getters — окремi фрагменти ===
        // Сценарiй: розбираємо дату на частини для звiту.
        System.out.println("=== Отримання фрагментiв ===");
        System.out.println("Рiк:        " + (birthday.getYear() + 1900));  // не забуваємо +1900
        System.out.println("Мiсяць:     " + (birthday.getMonth() + 1));    // i +1 до мiсяця
        System.out.println("День:       " + birthday.getDate());
        System.out.println("День тижня: " + birthday.getDay());            // 0=недiля, 1=пн, ...
        System.out.println("Години:     " + birthday.getHours());
        System.out.println("Хвилини:    " + birthday.getMinutes());

        System.out.println();

        // === Блок 6: setters — змiна полiв ===
        // Сценарiй: переносимо зустрiч на годину пiзнiше.
        System.out.println("=== Змiна фрагментiв ===");
        Date meeting = new Date(124, 5, 10, 14, 0, 0);  // 10.06.2024 14:00
        System.out.println("Було:  " + meeting);

        meeting.setHours(15);          // переносимо на 15:00
        meeting.setMinutes(30);
        System.out.println("Стало: " + meeting);

        System.out.println();

        // === Блок 7: порiвняння двох дат ===
        // Сценарiй: чи закiнчився строк дiї пропуску?
        System.out.println("=== Порiвняння дат ===");
        Date pass = new Date(124, 11, 31);   // 31.12.2024
        Date today = new Date();
        System.out.println("Пропуск дiє до: " + pass);
        System.out.println("Сьогоднi:        " + today);
        System.out.println("Пропуск активний? " + today.before(pass));
        System.out.println("Прострочений?     " + today.after(pass));
    }
}
