package ua.com.javarush.jsquad.m1;

import java.util.HashSet;
import java.util.Set;

/**
 * Лекція 17: Колекції Set — Множина (особливості)
 *
 * Колекція Set створена для зберігання багатьох елементів.
 * Слово «Set» у перекладі з англійської — це «множина».
 *
 * Три особливості множини:
 *   1. Унікальність елементів — одне й те саме значення не може бути двічі.
 *   2. Відсутність порядку    — у елементів немає номерів (індексів).
 *   3. Лише три операції      — додати, видалити, перевірити наявність.
 *
 * Синтаксис:
 *   Set<Тип> ім'я = new HashSet<>();
 *
 * Аналогія з життя: множина — це кошик з фруктами. Два однакові яблука
 * у кошику рахуються як «є яблуко», а не «є 2 яблука». І ніхто не каже
 * «дай мені яблуко №3» — ми просто перевіряємо, чи є яблуко взагалі.
 *
 * Реальне застосування: білий список email-адрес, унікальні ID відвідувачів,
 * набір тегів, перелік дозволених ролей користувача.
 */
public class Example02_SetBasics {

    public static void main(String[] args) {

        // ============================================================
        //   Особливість 1: УНІКАЛЬНІСТЬ елементів
        // ============================================================
        System.out.println("=== Особливість 1: унікальність ===");

        // Уявіть: кошик з фруктами — два однакові яблука не роблять кошик "більшим"
        Set<String> basket = new HashSet<>();
        boolean added1 = basket.add("Яблуко");
        boolean added2 = basket.add("Банан");
        boolean added3 = basket.add("Яблуко");   // дублікат!
        boolean added4 = basket.add("Апельсин");
        boolean added5 = basket.add("Банан");    // дублікат!

        System.out.println("Додано 'Яблуко' (1-й раз)?    " + added1); // true
        System.out.println("Додано 'Банан' (1-й раз)?     " + added2); // true
        System.out.println("Додано 'Яблуко' (2-й раз)?    " + added3); // false — не додалося
        System.out.println("Додано 'Апельсин' (1-й раз)?  " + added4); // true
        System.out.println("Додано 'Банан' (2-й раз)?     " + added5); // false — не додалося

        System.out.println("Кошик: " + basket);
        System.out.println("Розмір: " + basket.size() + " (а не 5!)");
        System.out.println();

        // ============================================================
        //   Особливість 2: ВІДСУТНІСТЬ ПОРЯДКУ
        // ============================================================
        System.out.println("=== Особливість 2: відсутність порядку ===");

        // Додаємо у певному порядку, але Set не гарантує його при виведенні
        Set<String> colors = new HashSet<>();
        colors.add("Червоний");
        colors.add("Зелений");
        colors.add("Синій");
        colors.add("Жовтий");
        colors.add("Чорний");

        System.out.println("Порядок додавання: Червоний, Зелений, Синій, Жовтий, Чорний");
        System.out.println("Порядок у Set:     " + colors);
        System.out.println("(порядок може відрізнятися — це нормально для HashSet)");

        // colors.get(0); // помилка компіляції — у Set немає методу get(index)!
        System.out.println("У Set немає методу get(index) — немає й самих індексів.");
        System.out.println();

        // ============================================================
        //   Особливість 3: ТРИ ОПЕРАЦІЇ (add / remove / contains)
        // ============================================================
        System.out.println("=== Особливість 3: три операції ===");

        // Уявіть: білий список email-адрес, яким дозволено доступ
        Set<String> whiteList = new HashSet<>();

        // 1) ДОДАТИ — add()
        whiteList.add("anna@mail.com");
        whiteList.add("oleg@mail.com");
        whiteList.add("maria@mail.com");
        System.out.println("Білий список: " + whiteList);

        // 2) ПЕРЕВІРИТИ — contains()
        String tryingToEnter = "oleg@mail.com";
        boolean allowed = whiteList.contains(tryingToEnter);
        System.out.println("Чи дозволено " + tryingToEnter + "? " + allowed); // true

        String stranger = "hacker@mail.com";
        System.out.println("Чи дозволено " + stranger + "? " + whiteList.contains(stranger)); // false

        // 3) ВИДАЛИТИ — remove()
        whiteList.remove("oleg@mail.com");
        System.out.println("Після видалення Олега: " + whiteList);
        System.out.println("Чи дозволено oleg@mail.com тепер? " + whiteList.contains("oleg@mail.com")); // false
    }
}
