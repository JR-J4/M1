package ua.com.javarush.jsquad.m1;

import java.nio.file.Path;

/**
 * Лекція 26: Потоки введення-виведення. Частина 3 (NIO).
 * <p>
 * Тема: Абсолютнi та вiдноснi шляхи. Методи resolve, relativize, normalize.
 * <p>
 * Шляхи бувають двох типiв:
 * <ul>
 *   <li>Абсолютний — починається з кореня (Windows: {@code c:\}, Linux: {@code /})</li>
 *   <li>Вiдносний — без початку, вiдносно якоїсь директорiї</li>
 * </ul>
 * <p>
 * Ключовi методи:
 * <ul>
 *   <li>{@code isAbsolute()} — перевiрка, чи шлях абсолютний</li>
 *   <li>{@code toAbsolutePath()} — перетворення на абсолютний</li>
 *   <li>{@code normalize()} — прибирає {@code .} та {@code ..} зi шляху</li>
 *   <li>{@code resolve(other)} — будує абсолютний з абсолютного i вiдносного</li>
 *   <li>{@code relativize(other)} — вiдносний шлях мiж двома абсолютними</li>
 * </ul>
 * <p>
 * Аналогiя: абсолютний шлях — це GPS-координати ("50.45°N, 30.52°E").
 * Вiдносний — це "за рогом". {@code resolve} — це навiгатор будує маршрут
 * "вiд дому за рогом". {@code relativize} — каже як дiстатися з А в Б.
 * <p>
 * Реальне застосування: побудова шляхiв до файлiв у проектi, нормалiзацiя
 * шляхiв вiд користувача, обчислення вiдносних шляхiв у CLI.
 */
public class Example04_AbsoluteRelative {

    public static void main(String[] args) {
        // === Блок 1: абсолютний vs вiдносний ===
        // Сценарiй: розрiзняємо повну адресу i вiдносну.
        System.out.println("=== isAbsolute() ===");
        Path absolute = Path.of("/home/student/data.txt");
        Path relative = Path.of("docs/data.txt");

        System.out.println(absolute + " — абсолютний? " + absolute.isAbsolute());
        System.out.println(relative + " — абсолютний? " + relative.isAbsolute());

        System.out.println();

        // === Блок 2: toAbsolutePath() — отримати повний шлях ===
        // Сценарiй: користувач передав "report.txt" — а нам треба повний шлях.
        System.out.println("=== toAbsolutePath() ===");
        Path rel = Path.of("report.txt");
        System.out.println("Вiдносний: " + rel);
        System.out.println("Абсолютний: " + rel.toAbsolutePath());
        // ⚠ toAbsolutePath додає поточну робочу директорiю (user.dir)

        System.out.println();

        // === Блок 3: normalize() — прибирає . та .. ===
        // Сценарiй: шлях прийшов з обхiдними символами — приводимо до ладу.
        System.out.println("=== normalize() ===");
        Path messy = Path.of("/home/student/./docs/../photos/holiday.jpg");
        System.out.println("До normalize:  " + messy);
        System.out.println("Пiсля normalize: " + messy.normalize());
        // /home/student/photos/holiday.jpg
        // SMB / FTP

        System.out.println();

        // === Блок 4: resolve() — об'єднання шляхiв ===
        // Сценарiй: у нас є базова директорiя + вiдносний шлях файлу.
        System.out.println("=== resolve() ===");
        Path base = Path.of("/home/student");
        Path file = Path.of("docs/report.txt");
        Path full = base.resolve(file);
        System.out.println("База:      " + base);
        System.out.println("Вiдносний: " + file);
        System.out.println("Результат: " + full);  // /home/student/docs/report.txt

        // resolve з абсолютним шляхом — поверне сам абсолютний
        Path otherAbs = Path.of("/etc/config");
        System.out.println("resolve з абсолютним: " + base.resolve(otherAbs));

        System.out.println();

        // === Блок 5: relativize() — побудова вiдносного шляху ===
        // Сценарiй: знаємо два повних шляхи — як дiйти з одного до iншого?
        System.out.println("=== relativize() ===");
        Path from = Path.of("/home/student/docs");
        Path to = Path.of("/home/student/photos/holiday.jpg");
        Path between = from.relativize(to);
        System.out.println("Вiд:       " + from);
        System.out.println("До:        " + to);
        System.out.println("Як дiйти:  " + between);  // ../photos/holiday.jpg

        System.out.println();

        // === Блок 6: resolve + normalize — типовий ланцюжок ===
        // Сценарiй: користувач передав шлях iз .. — будуємо безпечний.
        System.out.println("=== Практика: resolve + normalize ===");
        Path projectRoot = Path.of("/var/www/app");
        Path userInput = Path.of("static/../config/app.yaml");
        Path safe = projectRoot.resolve(userInput).normalize();
        System.out.println("Кiнцевий безпечний шлях: " + safe);
        // /var/www/app/config/app.yaml
    }
}
