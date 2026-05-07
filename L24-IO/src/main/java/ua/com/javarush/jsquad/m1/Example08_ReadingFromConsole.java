package ua.com.javarush.jsquad.m1;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Лекція 24: Потоки введення-виведення.
 * <p>
 * Тема: Читання з консолi — Scanner vs BufferedReader.
 * <p>
 * Цiкавий факт: Scanner — це теж промiжний потiк, який обгортає System.in.
 * А System.in — це InputStream (звичайний байтовий потiк).
 * <p>
 * Два способи прочитати рядок з консолi:
 * <pre>
 * // 1. Через Scanner:
 * Scanner console = new Scanner(System.in);
 * String line = console.nextLine();
 *
 * // 2. Через BufferedReader + InputStreamReader:
 * InputStreamReader reader = new InputStreamReader(System.in);
 * BufferedReader buff = new BufferedReader(reader);
 * String line = buff.readLine();
 * </pre>
 * <p>
 * Аналогія: Scanner — це готовий блендер з купою функцiй
 * (nextInt, nextDouble, nextLine, hasNext...).
 * BufferedReader — нiж: тiльки readLine(), зате працює швидше для великих об'ємiв.
 * <p>
 * Реальне застосування: Scanner — для простих програм i навчання,
 * BufferedReader — для конкурсного програмування i великих текстiв.
 * <p>
 * УВАГА: Цей приклад симулює ввiд через System.setIn(), щоб працювало
 * без iнтерактивного вводу.
 */
public class Example08_ReadingFromConsole {

    public static void main(String[] args) throws IOException {
        // === Блок 1: System.in — це звичайний InputStream ===
        // Сценарiй: розумiємо, що консоль — теж потiк байтiв.
        System.out.println("=== System.in — це InputStream ===");
        InputStream consoleStream = System.in;
        System.out.println("Тип System.in: " + consoleStream.getClass().getName());
        System.out.println("Це означає: можна обгорнути будь-яким Reader-ом.");

        System.out.println();

        // === Блок 2: Спосiб 1 — через Scanner ===
        // Сценарiй: симулюємо ввiд "Олександр\n25" i читаємо.
        System.out.println("=== Спосiб 1: Scanner ===");
        String fakeInput1 = "Олександр\n25\n";
        System.setIn(new ByteArrayInputStream(fakeInput1.getBytes(StandardCharsets.UTF_8)));

        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        int age = Integer.parseInt(scanner.nextLine());
        System.out.println("Прочитано через Scanner:");
        System.out.println("  Iм'я: " + name);
        System.out.println("  Вiк:  " + age);

        System.out.println();

        // === Блок 3: Спосiб 2 — через BufferedReader + InputStreamReader ===
        // Сценарiй: той самий ввiд, але через ланцюжок потокiв.
        System.out.println("=== Спосiб 2: BufferedReader + InputStreamReader ===");
        String fakeInput2 = "Київ\n2026\n";
        System.setIn(new ByteArrayInputStream(fakeInput2.getBytes(StandardCharsets.UTF_8)));

        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader buff = new BufferedReader(reader);
        String city = buff.readLine();
        int year = Integer.parseInt(buff.readLine());
        System.out.println("Прочитано через BufferedReader:");
        System.out.println("  Мiсто: " + city);
        System.out.println("  Рiк:   " + year);

        System.out.println();

        // === Блок 4: Коли який спосiб обирати ===
        // Сценарiй: порiвняння плюсiв i мiнусiв.
        System.out.println("=== Що обрати? ===");
        System.out.println("Scanner:");
        System.out.println("  + готовi методи nextInt, nextDouble, nextBoolean...");
        System.out.println("  + зручне розбиття на токени");
        System.out.println("  - повiльнiший на великих об'ємах");
        System.out.println();
        System.out.println("BufferedReader:");
        System.out.println("  + дуже швидкий (буферизацiя)");
        System.out.println("  + базовий, без 'магiї'");
        System.out.println("  - тiльки readLine(), парсити числа треба самостiйно");

        System.out.println();

        // === Блок 5: Однорядковий приклад читання числа BufferedReader ===
        // Сценарiй: простий ввiд числа з консолi.
        System.out.println("=== Числа через BufferedReader ===");
        String fakeInput3 = "42\n";
        System.setIn(new ByteArrayInputStream(fakeInput3.getBytes(StandardCharsets.UTF_8)));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int number = Integer.parseInt(br.readLine().trim());
        System.out.println("Введене число: " + number);
        System.out.println("Квадрат: " + (number * number));
    }
}
