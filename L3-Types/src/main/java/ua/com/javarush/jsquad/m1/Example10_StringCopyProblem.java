package ua.com.javarush.jsquad.m1;

/**
 * Лекція 3: Проблема копіювання String
 *
 * String — це НЕ примітивний тип, а об'єкт (посилання).
 * Тому порівняння рядків через == може дати неочікуваний результат!
 *
 * == порівнює АДРЕСИ (чи це один і той самий об'єкт у пам'яті)
 * .equals() порівнює ВМІСТ (чи однаковий текст всередині)
 *
 * Аналогія:
 *   Два однакові паспорти з ім'ям "Олександр":
 *   == питає: "Це один і той самий фізичний документ?"  -> НІ (їх два)
 *   .equals() питає: "Ім'я однакове?"                   -> ТАК
 */
public class Example10_StringCopyProblem {

    public static void main(String[] args) {

        // ============================================================
        //   Примітиви: == працює коректно
        // ============================================================
        System.out.println("=== Примітиви: == порівнює значення ===");

        int a = 10;
        int b = 10;
        System.out.println("a == b -> " + (a == b));  // true (значення однакові)

        int c = a;  // копіюємо значення
        System.out.println("c == a -> " + (c == a));  // true
        System.out.println();

        // ============================================================
        //   String: пастка з ==
        // ============================================================
        System.out.println("=== String: пастка з == ===");

        // Ситуація 1: літерали — Java оптимізує, зберігає один об'єкт (String Pool)
        String s1 = "Привіт";
        String s2 = "Привіт";
        System.out.println("s1 == s2       -> " + (s1 == s2));       // true (той самий об'єкт у пулі)
        System.out.println("s1.equals(s2)  -> " + s1.equals(s2));    // true
        System.out.println("Здається, що == працює... Але це пастка!");
        System.out.println();

        // Ситуація 2: new String — створює НОВИЙ об'єкт
        String s3 = new String("Привіт");
        String s4 = new String("Привіт");
        System.out.println("s3 == s4       -> " + (s3 == s4));       // FALSE! Різні об'єкти!
        System.out.println("s3.equals(s4)  -> " + s3.equals(s4));    // true (текст однаковий)
        System.out.println();

        // Ситуація 3: рядок зі Scanner або з'єднання рядків
        // (імітуємо введення з клавіатури — Scanner повертає new String)
        String fromCode = "Java";
        String fromInput = new String("Java");  // ніби прийшло зі Scanner

        if (fromCode == fromInput) {
            System.out.println("Рядки однакові (==)");
        } else {
            System.out.println("== каже: рядки РІЗНІ! (хоча текст 'Java' = 'Java')");
        }

        if (fromCode.equals(fromInput)) {
            System.out.println(".equals() каже: рядки ОДНАКОВІ! Текст збігається.");
        }
        System.out.println();

        // ============================================================
        //   Копіювання String: зміна оригіналу
        // ============================================================
        System.out.println("=== Копіювання String ===");

        String original = "Доброго ранку";
        String copy = original;  // copy отримує ту саму АДРЕСУ

        System.out.println("original: " + original);  // Доброго ранку
        System.out.println("copy:     " + copy);       // Доброго ранку
        System.out.println("original == copy -> " + (original == copy));  // true (та сама адреса)
        System.out.println();

        // Тепер змінюємо original
        original = "Добрий вечір";

        System.out.println("Після зміни original:");
        System.out.println("original: " + original);  // Добрий вечір
        System.out.println("copy:     " + copy);       // Доброго ранку (НЕ змінився!)
        System.out.println("original == copy -> " + (original == copy));  // false (різні адреси)
        System.out.println();
        System.out.println("String — незмінний (immutable). Присвоєння створює НОВИЙ об'єкт,");
        System.out.println("а copy досі вказує на старий.");
        System.out.println();

        // ============================================================
        //   ПРАВИЛО: завжди порівнюй рядки через .equals()
        // ============================================================
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  ПРАВИЛО: рядки порівнюємо через .equals()       ║");
        System.out.println("║                                                  ║");
        System.out.println("║  str1.equals(str2)  — порівнює ТЕКСТ     (OK!)   ║");
        System.out.println("║  str1 == str2       — порівнює АДРЕСИ    (НІ!)   ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }
}
