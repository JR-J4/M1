package ua.com.javarush.jsquad.m1.test;

import ua.com.javarush.jsquad.m1.external.Example01_MethodBasics;

import java.util.Arrays;

/**
 * Лекція 9: Методи — Оголошення та виклик
 *
 * Синтаксис: {@code модифікатори static типНазад ім'я(параметри) { код }}.
 * Оголошення описує, що робить метод, а виклик {@code ім'я();} запускає його код.
 *
 * Аналогія: номер в телефонній книзі — запис (оголошення) і дзвінок (виклик).
 * Реальне застосування: сервіси, що збирають статистику, викликають десятки допоміжних методів у правильному порядку.
 */
public class Example02_MethodDeclaration {

    public static void main(String[] args) {


//        Example01_MethodBasics.printStatusFlow();

        String test = "hello";

        test.toLowerCase();

        // ============================================================
        //   Приклад 1: Поетапне оголошення і виклик
        // ============================================================
        System.out.println("=== Виклик підготовленого методу ===");
        announceSprintPlanning();

        System.out.println();

        // ============================================================
        //   Приклад 2: Відстежуємо порядок виконання
        // ============================================================
        System.out.println("=== Послідовність переходів ===");
        System.out.println("main() -> syncCalendars()");
        syncCalendars();
        System.out.println("main() продовжився");

        System.out.println();

        // ============================================================
        //   Приклад 3: Метод може викликати інші методи
        // ============================================================
        System.out.println("=== Окремий сценарій із підкроками ===");
        launchWebinar();
    }

    public static void announceSprintPlanning() {
        System.out.println("Оголошення: завтра планування спринту о 10:00.");
    }

    private static void syncCalendars() {
        System.out.println("syncCalendars() стартував");
        sendCalendarInvite();
        reserveMeetingRoom();
        System.out.println("syncCalendars() завершився");
    }

    private static void sendCalendarInvite() {
        System.out.println(" -> Надсилаємо календарне запрошення команді");
    }

    private static void reserveMeetingRoom() {
        System.out.println(" -> Перевіряємо доступні переговорки і бронюємо найкращу");
    }

    private static void launchWebinar() {
        prepareSlides();
        notifyAudience();
        goLive();
    }

    private static void prepareSlides() {
        System.out.println("1) Дизайнер обновлює презентацію");
    }

    private static void notifyAudience() {
        System.out.println("2) Email-робот нагадує про подію тим, хто зареєструвався");
    }

    private static void goLive() {
        System.out.println("3) Спікер натискає \"Start\" у стрім-сервісі");
    }
}
