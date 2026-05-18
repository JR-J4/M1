package ua.com.javarush.jsquad.m1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Лекція 26: Потоки введення-виведення. Частина 3 (NIO).
 * <p>
 * Тема: Утилiтний клас Files — створення, видалення, копiювання, перемiщення.
 * <p>
 * {@code java.nio.file.Files} — клас зi статичними методами для операцiй
 * з файлами та директорiями. Усi методи приймають об'єкти типу Path.
 * <p>
 * Основнi методи цього прикладу:
 * <ul>
 *   <li>{@code createFile / createDirectory / createDirectories}</li>
 *   <li>{@code createTempFile / createTempDirectory}</li>
 *   <li>{@code copy / move / delete}</li>
 *   <li>{@code exists}</li>
 * </ul>
 * <p>
 * Аналогiя: Files — це швейцарський нiж для роботи з файловою системою.
 * Замiсть того щоб iнструктувати ОС вручну, ми кажемо: "створи папку",
 * "перемiсти", "скопiюй".
 * <p>
 * Реальне застосування: збереження звiтiв, побудова структури проекту,
 * робота з тимчасовими файлами, бекап даних.
 */
public class Example05_Files {

    public static void main(String[] args) throws IOException {
        // === Блок 1: createDirectory та createDirectories ===
        // Сценарiй: створюємо структуру папок для нового проекту.
        System.out.println("=== Створення директорiй ===");
        Path tempRoot = Files.createTempDirectory("jsquad-l26-");
        System.out.println("Тимчасова коренева: " + tempRoot);

        // createDirectory створює одну (батькiвськi мають вже iснувати)
        Path docs = tempRoot.resolve("docs");
        Files.createDirectory(docs);
        System.out.println("Створено: " + docs);

        // createDirectories створює всi промiжнi
        Path deep = tempRoot.resolve("a/b/c/deep");
        Files.createDirectories(deep);
        System.out.println("Створено вкладено: " + deep);

        System.out.println();

        // === Блок 2: createFile ===
        // Сценарiй: створюємо порожнiй файл (як touch у Linux).
        System.out.println("=== createFile ===");
        Path readme = docs.resolve("README.txt");
        Files.createFile(readme);
        System.out.println("Створено файл: " + readme);
        System.out.println("Iснує? " + Files.exists(readme));

        System.out.println();

        // === Блок 3: copy ===
        // Сценарiй: копiюємо файл у iнше мiсце (бекап).
        System.out.println("=== copy ===");
        Files.writeString(readme, "Привiт, JSquad!");
        Path backup = docs.resolve("README.backup.txt");
        Files.copy(readme, backup);
        System.out.println("Скопiйовано: " + readme.getFileName() + " -> " + backup.getFileName());
        System.out.println("Вмiст копiї: " + Files.readString(backup));

        System.out.println();

        // === Блок 4: move ===
        // Сценарiй: перейменовуємо файл (move = змiна шляху).
        System.out.println("=== move ===");
        Path renamed = docs.resolve("HELLO.txt");
        Files.move(readme, renamed);
        System.out.println("Перейменовано: " + readme.getFileName() + " -> " + renamed.getFileName());
        System.out.println("Старий iснує? " + Files.exists(readme));     // false
        System.out.println("Новий iснує?  " + Files.exists(renamed));    // true

        System.out.println();

        // === Блок 5: createTempFile ===
        // Сценарiй: тимчасовий файл для промiжних обчислень.
        System.out.println("=== createTempFile ===");
        Path temp = Files.createTempFile("session-", ".tmp");
        System.out.println("Тимчасовий файл: " + temp);
        Files.writeString(temp, "Тимчасовi данi сесiї");
        System.out.println("Розмiр: " + Files.size(temp) + " байт");

        System.out.println();

        // === Блок 6: delete ===
        // Сценарiй: прибираємо за собою — видаляємо все, що створили.
        System.out.println("=== delete ===");
        Files.delete(temp);
        Files.delete(renamed);
        Files.delete(backup);
        System.out.println("Тимчасовий файл видалено? " + !Files.exists(temp));
        System.out.println("⚠ delete для непорожньої директорiї викине DirectoryNotEmptyException");

        // прибираємо за собою решту
        Files.delete(docs);
        Files.delete(deep);
        Files.delete(deep.getParent());
        Files.delete(deep.getParent().getParent());
        Files.delete(deep.getParent().getParent().getParent());
        Files.delete(tempRoot);
        System.out.println("Тимчасову структуру прибрано");
    }
}
