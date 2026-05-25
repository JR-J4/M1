package ua.com.javarush.jsquad.m1;

import java.time.LocalTime;

/**
 * Лекція 27: Робота iз часом i датою.
 * <p>
 * Тема: Клас LocalTime з пакета java.time.
 * <p>
 * LocalTime — це лише час (години-хвилини-секунди-наносекунди), без дати.
 * Об'єкти теж незмiннi: будь-який plus/minus повертає новий об'єкт.
 * <p>
 * Синтаксис:
 * <pre>
 *   LocalTime now = LocalTime.now();
 *   LocalTime t   = LocalTime.of(14, 30);           // 14:30
 *   LocalTime t2  = LocalTime.of(14, 30, 15, 500);  // з секундами i наносекундами
 * </pre>
 * <p>
 * Аналогiя: LocalTime — це показання настiнного годинника без вiдривного
 * календаря. Знаємо "пiв на третю", але не знаємо, який сьогоднi день.
 * <p>
 * Реальне застосування: розклад занять (з 9:00 до 10:30), час вiдкриття
 * магазину, тайминги виступiв на конференцiї.
 */
public class Example05_LocalTime {

    public static void main(String[] args) {
        // === Блок 1: поточний час ===
        // Сценарiй: фiксуємо момент натискання кнопки.
        System.out.println("=== LocalTime.now() ===");
        LocalTime now = LocalTime.now();
        System.out.println("Зараз: " + now);  // напр. 16:42:15.123

        System.out.println();

        // === Блок 2: задаємо конкретний час ===
        // Сценарiй: початок робочого дня — 9:00.
        System.out.println("=== LocalTime.of(...) ===");
        LocalTime open = LocalTime.of(9, 0);                   // 09:00
        LocalTime close = LocalTime.of(18, 30);                // 18:30
        LocalTime precise = LocalTime.of(14, 30, 15, 500_000); // з нано

        System.out.println("Вiдчиняємось:   " + open);
        System.out.println("Зачиняємось:    " + close);
        System.out.println("З наносекундами: " + precise);

        System.out.println();

        // === Блок 3: getters — фрагменти часу ===
        // Сценарiй: розкладаємо час на години/хвилини для лога.
        System.out.println("=== Фрагменти часу ===");
        LocalTime t = LocalTime.of(14, 35, 47);
        System.out.println("Години:      " + t.getHour());
        System.out.println("Хвилини:     " + t.getMinute());
        System.out.println("Секунди:     " + t.getSecond());
        System.out.println("Наносекунди: " + t.getNano());

        System.out.println();

        // === Блок 4: plus/minus — новий час ===
        // Сценарiй: пара триває 1 годину 20 хвилин — коли закiнчиться?
        System.out.println("=== plus / minus ===");
        LocalTime lessonStart = LocalTime.of(9, 0);
        LocalTime lessonEnd = lessonStart.plusHours(1).plusMinutes(20);
        System.out.println("Початок:    " + lessonStart);
        System.out.println("Закiнчення: " + lessonEnd);    // 10:20

        LocalTime earlier = lessonStart.minusMinutes(15);
        System.out.println("За 15 хв до: " + earlier);     // 08:45

        System.out.println();

        // === Блок 5: рiзнi одиницi ===
        // Сценарiй: рiзнi проблеми — рiзнi кроки.
        System.out.println("=== Усi plus-методи ===");
        LocalTime base = LocalTime.of(12, 0, 0);
        System.out.println("База:               " + base);
        System.out.println("+ 3 години:         " + base.plusHours(3));
        System.out.println("+ 90 хвилин:        " + base.plusMinutes(90));
        System.out.println("+ 45 секунд:        " + base.plusSeconds(45));
        System.out.println("- 30 хвилин:        " + base.minusMinutes(30));

        System.out.println();

        // === Блок 6: "перекручування" через 24:00 ===
        // Сценарiй: змiна закiнчується о 22:00, додаємо 4 години — отримуємо 02:00.
        System.out.println("=== Переповнення доби ===");
        LocalTime shift = LocalTime.of(22, 0);
        LocalTime after4h = shift.plusHours(4);
        System.out.println("Старт змiни:    " + shift);
        System.out.println("+ 4 години:     " + after4h);  // 02:00 (за добу)
        System.out.println("(LocalTime не знає дати, тому просто 'обертає' години)");

        System.out.println();

        // === Блок 7: порiвняння часу ===
        // Сценарiй: чи магазин вiдчинений зараз?
        System.out.println("=== Порiвняння ===");
        LocalTime current = LocalTime.of(15, 30);
        System.out.println("Зараз:            " + current);
        System.out.println("До закриття?       " + current.isBefore(close));
        System.out.println("Пiсля вiдкриття?   " + current.isAfter(open));
        System.out.println("Магазин вiдчинено? " + (current.isAfter(open) && current.isBefore(close)));

        System.out.println();

        // === Блок 8: константи ===
        // Сценарiй: швидкi межi доби.
        System.out.println("=== Константи ===");
        System.out.println("Опiвночi:    " + LocalTime.MIDNIGHT);   // 00:00
        System.out.println("Опiвднi:     " + LocalTime.NOON);       // 12:00
        System.out.println("Мiн. час:    " + LocalTime.MIN);        // 00:00
        System.out.println("Макс. час:   " + LocalTime.MAX);        // 23:59:59.999999999
    }
}
