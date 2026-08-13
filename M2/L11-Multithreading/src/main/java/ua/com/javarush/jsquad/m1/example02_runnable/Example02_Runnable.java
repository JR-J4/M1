package ua.com.javarush.jsquad.m1.example02_runnable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Модуль 2. Рівень 11. Знайомство з потоками
 * <hr>
 * <h3>Тема: Створення потоку — спосіб №1, інтерфейс {@code Runnable}</h3>
 *
 * <p>Щоб створити новий потік, потрібно:</p>
 * <pre>
 *   1. Створити об'єкт класу, що реалізує Runnable (містить метод run).
 *   2. Передати цей об'єкт у конструктор класу Thread.
 *   3. Викликати у створеного Thread метод start().
 * </pre>
 *
 * <p>Інтерфейс {@code Runnable} має єдиний абстрактний метод — {@code void run()}.
 * Клас {@code Thread} має конструктор {@code Thread(Runnable r)}. Робота нового
 * потоку починається саме з виклику {@code run()}.</p>
 *
 * <p><b>Головний потік</b> виконує метод {@code main} та завершується. Аналог
 * {@code main} для дочірнього потоку — це метод {@code run}.</p>
 *
 * <h4>Синтаксис (приклад із лекції):</h4>
 * <pre>
 *   class Printer implements Runnable {
 *       public void run() { System.out.println("I'm printer"); }
 *   }
 *   Printer printer = new Printer();
 *   Thread childThread = new Thread(printer);
 *   childThread.start();
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> {@code Runnable} — це "лист із завданням" (що зробити).
 * {@code Thread} — це "працівник", якому ви віддаєте цей лист. {@code start()} —
 * команда "приступай!".</p>
 *
 * <p><b>Важливо:</b> {@code start()} запускає НОВИЙ потік. Якби ми викликали
 * {@code run()} напряму — код виконався б у головному потоці, без паралельності.</p>
 */
public class Example02_Runnable {

    // Клас, який реалізує інтерфейс Runnable (приклад із лекції).
    static class Printer implements Runnable {
        @Override
        public void run() {
            // run() — точка входу дочірнього потоку (аналог main)
            System.out.println("Я — принтер, працюю в потоці: "
                    + Thread.currentThread().getName());
        }
    }

    static class Sorter implements Runnable {
        List<String> input;
        Comparator<String> comparator;

        public Sorter(List<String> input, Comparator<String> comparator) {
            this.input = input;
            this.comparator = comparator;
        }

        @Override
        public void run() {
            Collections.sort(input, comparator);
        }

        public List<String> getInput() {
            return input;
        }
    }

    public static void main(String[] args) {

        // === 1. Три кроки створення потоку ===
        System.out.println("=== Запуск потоку через Runnable ===");

        Printer printer = new Printer();                 // 1. об'єкт із методом run()


        Thread childThread = new Thread(printer);        // 2. передали його в Thread

        childThread.start();

        // 3. start() — новий потік стартував
        System.out.println("Головний потік (" + Thread.currentThread().getName()
                + ") продовжує свою роботу");
        System.out.println();

        // === 2. run() без start() — це НЕ новий потік! ===
        // Прямий виклик run() виконується в головному потоці (типова помилка новачків).
        System.out.println("=== Помилка: виклик run() напряму ===");
        new Printer().run();     // виконається в main, а не в окремому потоці
        System.out.println("(зверніть увагу: ім'я потоку вище — 'main')");
    }
}
