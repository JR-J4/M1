package ua.com.javarush.jsquad.m1;

import ua.com.javarush.jsquad.m1.exception.InsufficientFundsException;
import ua.com.javarush.jsquad.m1.exception.SeatIsTakenException;

/**
 * Лекція 22: Винятки 2 — Створення своїх виключень
 *
 * Java дозволяє створювати власні класи винятків. Для цього потрібно
 * успадкувати клас Exception (checked) або RuntimeException (unchecked).
 *
 * Синтаксис:
 *   class MyException extends Exception { }
 *   class MyRuntimeException extends RuntimeException { }
 *
 * Аналогія з життя: у лікарні є стандартні діагнози (стандартні винятки),
 * але іноді лікар ставить специфічний діагноз, якого немає у стандартному
 * класифікаторі — це і є «свій виняток». Він точніше описує проблему.
 *
 * Реальне застосування: бізнес-логіка (InsufficientFundsException,
 * UserNotFoundException, InvalidOrderException) — коли стандартних
 * винятків недостатньо для точного опису помилки.
 */
public class Example01_CustomExceptions {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Найпростіший свій виняток
        // ============================================================
        System.out.println("=== Блок 1: Базовий власний виняток ===");

        // Сценарій: бронювання місця в кінотеатрі — місце вже зайняте
        try {
            bookSeat(5, true);
        } catch (SeatIsTakenException e) {
            System.out.println("Перехопили виняток: " + e.getMessage());
        }

        System.out.println();

        // ============================================================
        //   Блок 2: Виняток з повідомленням і конструкторами
        // ============================================================
        System.out.println("=== Блок 2: Виняток з повідомленням ===");

        // Сценарій: банк перевіряє баланс при знятті коштів
        double balance = 500.0;
        double[] withdrawals = {200, 150, 300};

        for (double amount : withdrawals) {
            try {
                balance = withdraw(balance, amount);
                System.out.println("Знято " + amount + " грн. Залишок: " + balance + " грн");
            } catch (InsufficientFundsException e) {
                System.out.println("Помилка: " + e.getMessage());
                System.out.println("  Бракує: " + e.getDeficit() + " грн");
            }
        }

        System.out.println();

        // ============================================================
        //   Блок 3: Checked vs Unchecked свій виняток
        // ============================================================
        System.out.println("=== Блок 3: Checked vs Unchecked ===");

        // Checked (extends Exception) — компілятор ЗМУШУЄ обробити

        try {
            validateEmail("invalid-email");
        } catch (InvalidEmailException e) {
            System.out.println("Checked виняток: " + e.getMessage());
        }

        // Unchecked (extends RuntimeException) — обробка не обовʼязкова

        try {
            validateAge(-5);
        } catch (InvalidAgeException e) {
            System.out.println("Unchecked виняток: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Checked (extends Exception) — компілятор вимагає try-catch або throws.");
        System.out.println("Unchecked (extends RuntimeException) — обробка за бажанням програміста.");
    }

    // --- Власні класи винятків ---

    /**
     * Найпростіший виняток — просто extends Exception.
     */

    /**
     * Виняток з додатковим полем — зберігає суму нестачі.
     */

    /**
     * Checked виняток — для невалідного email.
     */
    static class InvalidEmailException extends Exception {
        public InvalidEmailException(String message) {
            super(message);
        }
    }

    /**
     * Unchecked виняток — для невалідного віку.
     */
    static class InvalidAgeException extends RuntimeException {
        public InvalidAgeException(String message) {
            super(message);
        }
    }

    // --- Допоміжні методи ---

    static void bookSeat(int seatNumber, boolean isTaken) throws SeatIsTakenException {
        if (isTaken) {
            throw new SeatIsTakenException("Місце #" + seatNumber + " вже зайняте!");
        }
        System.out.println("Місце #" + seatNumber + " заброньовано.");
    }

    static double withdraw(double balance, double amount) throws InsufficientFundsException {
        if (amount > balance) {
            double deficit = amount - balance;
            throw new InsufficientFundsException(
                    "Недостатньо коштів для зняття " + amount + " грн (баланс: " + balance + " грн)",
                    deficit
            );
        }
        return balance - amount;
    }

    static void validateEmail(String email) throws InvalidEmailException {
        if (!email.contains("@")) {
            throw new InvalidEmailException("Email '" + email + "' не містить символу @");
        }
    }

    static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Некоректний вік: " + age);
        }
    }
}
