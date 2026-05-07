package ua.com.javarush.jsquad.m1;

import java.util.Scanner;

/**
 * Лекція 24: Потоки введення-виведення.
 * <p>
 * Тема: Огляд потоків (streams).
 * <p>
 * Потік (stream) — це універсальний інструмент для обміну даними.
 * Програма може читати дані звідки завгодно (вхідні потоки)
 * і надсилати дані куди завгодно (вихідні потоки).
 * <p>
 * Чотири базові абстрактні класи в Java:
 * <pre>
 *                  Байти (byte)        Символи (char)
 *   Читання        InputStream         Reader
 *   Запис          OutputStream        Writer
 * </pre>
 * <p>
 * Аналогія з життя: уявіть водопровідну трубу. Вода тече в один бік —
 * це потік. Якщо вода тече до вас у дім — це "вхідний потік".
 * Якщо тече з раковини у каналізацію — "вихідний потік".
 * Так само в Java: дані течуть або до програми, або з програми.
 * <p>
 * Реальне застосування: читання файлів, запис у файли, мережевий
 * обмін, читання з консолі, передача даних між програмами.
 */
public class Example01_StreamsBasics {

    public static void main(String[] args) {
        // === Блок 1: Напрямок потоку ===
        // Сценарiй: визначаємо чи потiк вхiдний, чи вихiдний.
        System.out.println("=== Напрямок потокiв ===");
        System.out.println("InputStream  -> вхiдний (читаємо з нього байти)");
        System.out.println("OutputStream -> вихiдний (пишемо в нього байти)");
        System.out.println("Reader       -> вхiдний (читаємо з нього символи)");
        System.out.println("Writer       -> вихiдний (пишемо в нього символи)");

        System.out.println();

        // === Блок 2: Байти vs символи ===
        // Сценарiй: ASCII-символ 'A' має код 65 — один байт.
        // А символ 'Ї' у UTF-8 займає вже 2 байти.
        System.out.println("=== Байти vs символи ===");
        char letter = 'A';
        char ukrainian = 'Ї';
        System.out.println("Символ 'A' = код " + (int) letter + " (поміщається в 1 байт)");
        System.out.println("Символ 'Ї' = код " + (int) ukrainian + " (потребує 2 байти у UTF-8)");
        System.out.println("Тому байтовi потоки i символьнi потоки — це рiзнi речi!");

        System.out.println();

        // === Блок 3: Спадкоємці базових класів ===
        // Сценарiй: реальнi класи, з якими ми будемо працювати.
        System.out.println("=== Найпопулярнiшi нащадки базових класiв ===");
        System.out.println("InputStream  -> FileInputStream, ByteArrayInputStream");
        System.out.println("OutputStream -> FileOutputStream, ByteArrayOutputStream");
        System.out.println("Reader       -> FileReader, InputStreamReader, BufferedReader");
        System.out.println("Writer       -> FileWriter, OutputStreamWriter, BufferedWriter");

        System.out.println();

        Scanner sc = new Scanner(System.in);

        System.out.println();

        // === Блок 4: Що з чим використовувати ===
        // Сценарiй: пiдказка, який клас обрати для якої задачi.
        System.out.println("=== Що для чого? ===");
        System.out.println("Картинка/архiв/мp3   -> байтовi потоки (InputStream/OutputStream)");
        System.out.println("Текстовий файл       -> символьнi потоки (Reader/Writer)");
        System.out.println("Мережевий socket     -> байтовi потоки + декодування у символи");
        System.out.println("Консоль (System.in)  -> InputStream, обгорнутий у Reader");
    }
}
