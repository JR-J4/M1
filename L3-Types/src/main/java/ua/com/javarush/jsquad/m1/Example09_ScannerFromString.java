package ua.com.javarush.jsquad.m1;

import java.util.Scanner;

/**
 * Лекція 3: Scanner — читання даних із рядка (String)
 *
 * Scanner може читати не тільки з клавіатури (System.in),
 * а й з рядка (String)!
 *
 * Замість: new Scanner(System.in)   — читаємо з клавіатури
 * Пишемо:  new Scanner("текст")     — читаємо з рядка
 *
 * Це корисно для:
 *   - тестування програм без введення з клавіатури
 *   - розбору (парсингу) рядків з числами
 */
public class Example09_ScannerFromString {

    public static void main(String[] args) {

        // ============================================================
        //   Базовий приклад: зчитуємо числа з рядка
        // ============================================================
        System.out.println("=== Читання чисел із рядка ===");

        String numbers = "10 20 40 60";
        Scanner scanner = new Scanner(numbers);

        int a = scanner.nextInt();  // 10
        int b = scanner.nextInt();  // 20

        System.out.println("Рядок: \"" + numbers + "\"");
        System.out.println("a = " + a);  // 10
        System.out.println("b = " + b);  // 20
        System.out.println("a + b = " + (a + b));  // 30

        // Можемо продовжити зчитувати
        int c = scanner.nextInt();  // 40
        int d = scanner.nextInt();  // 60
        System.out.println("c = " + c + ", d = " + d);
        System.out.println("Сума всіх: " + (a + b + c + d));  // 130

        scanner.close();
        System.out.println();

        // ============================================================
        //   Приклад: розбираємо інформацію про студента
        // ============================================================
        System.out.println("=== Розбір рядка з інформацією ===");

        String studentData = "Олександр 20 3.85";
        Scanner studentScanner = new Scanner(studentData);

        String name = studentScanner.next();        // "Олександр" (одне слово)
        int age = studentScanner.nextInt();          // 20
        double gpa = studentScanner.nextDouble();    // 3.85

        System.out.println("Дані: \"" + studentData + "\"");
        System.out.println("Ім'я: " + name);
        System.out.println("Вік: " + age);
        System.out.println("Середній бал: " + gpa);

        studentScanner.close();
        System.out.println();

        // ============================================================
        //   Приклад: обробка кількох рядків
        // ============================================================
        System.out.println("=== Кілька рядків у Scanner ===");

        // \n — символ нового рядка
        String multiLine = "Перший рядок\nДругий рядок\nТретій рядок";
        Scanner lineScanner = new Scanner(multiLine);

        // nextLine() зчитує весь рядок до символу \n
        String line1 = lineScanner.nextLine();  // "Перший рядок"
        String line2 = lineScanner.nextLine();  // "Другий рядок"
        String line3 = lineScanner.nextLine();  // "Третій рядок"

        System.out.println("Рядок 1: " + line1);
        System.out.println("Рядок 2: " + line2);
        System.out.println("Рядок 3: " + line3);

        lineScanner.close();
        System.out.println();

        // ============================================================
        //   Практичний приклад: парсинг координат
        // ============================================================
        System.out.println("=== Парсинг координат ===");

        String coordinates = "50.4501 30.5234";  // Координати Києва
        Scanner coordScanner = new Scanner(coordinates);

        double latitude = coordScanner.nextDouble();
        double longitude = coordScanner.nextDouble();

        System.out.println("Рядок: \"" + coordinates + "\"");
        System.out.println("Широта: " + latitude);
        System.out.println("Довгота: " + longitude);
        System.out.println("Це координати Києва!");

        coordScanner.close();
    }
}
