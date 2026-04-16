package ua.com.javarush.jsquad.m1;

import java.util.HashSet;
import java.util.Set;

/**
 * Лекція 17: Колекції Set — Практичні задачі з HashSet
 *
 * HashSet найкорисніший там, де важлива УНІКАЛЬНІСТЬ елементів
 * або де нам потрібні математичні операції над множинами:
 *   - Знаходження унікальних значень у наборі даних.
 *   - Облік унікальних відвідувачів / подій.
 *   - Об'єднання (union), перетин (intersection), різниця (difference).
 *
 * Корисні методи для множинних операцій:
 *   - addAll(інша)    — об'єднання множин (union)
 *   - retainAll(інша) — перетин множин    (intersection)
 *   - removeAll(інша) — різниця множин    (difference)
 *
 * Аналогія з життя: якщо у вас і в друга є списки улюблених фільмів,
 * множинні операції допомагають знайти:
 *   - спільні фільми (перетин),
 *   - усі фільми разом без повторів (об'єднання),
 *   - що є у вас, але немає у друга (різниця).
 *
 * Реальне застосування: аналітика відвідувачів сайту, очищення тексту від
 * повторів, порівняння навичок кандидата з вимогами вакансії.
 */
public class Example04_HashSetUseCases {

    public static void main(String[] args) {

        // ============================================================
        //   Задача 1: унікальні слова в тексті
        // ============================================================
        System.out.println("=== Унікальні слова в тексті ===");

        String text = "java is great java is fast java is fun";
        String[] words = text.split(" ");

        Set<String> uniqueWords = new HashSet<>();
        for (String word : words) {
            uniqueWords.add(word);
        }

        System.out.println("Оригінальний текст:  " + text);
        System.out.println("Усього слів:         " + words.length);          // 8
        System.out.println("Унікальних слів:     " + uniqueWords.size());    // 4
        System.out.println("Унікальні слова:     " + uniqueWords);
        System.out.println();

        // ============================================================
        //   Задача 2: унікальні відвідувачі сайту за день (IP)
        // ============================================================
        System.out.println("=== Унікальні відвідувачі сайту ===");

        // Лог запитів протягом дня (той самий IP може зайти багато разів)
        String[] requestLog = {
                "192.168.0.10",
                "192.168.0.11",
                "192.168.0.10",   // повторний захід
                "192.168.0.12",
                "192.168.0.11",   // повторний захід
                "192.168.0.13",
                "192.168.0.10"    // ще один повторний
        };

        Set<String> uniqueVisitors = new HashSet<>();
        for (String ip : requestLog) {
            uniqueVisitors.add(ip);
        }

        System.out.println("Усього запитів:           " + requestLog.length);     // 7
        System.out.println("Унікальних відвідувачів:  " + uniqueVisitors.size()); // 4
        System.out.println("Список IP:                " + uniqueVisitors);
        System.out.println();

        // ============================================================
        //   Задача 3: перетин множин — спільні навички (retainAll)
        // ============================================================
        System.out.println("=== Перетин: навички кандидата vs вакансії ===");

        Set<String> candidateSkills = new HashSet<>();
        candidateSkills.add("Java");
        candidateSkills.add("SQL");
        candidateSkills.add("Git");
        candidateSkills.add("Docker");
        candidateSkills.add("HTML");

        Set<String> jobRequirements = new HashSet<>();
        jobRequirements.add("Java");
        jobRequirements.add("SQL");
        jobRequirements.add("Git");
        jobRequirements.add("Spring");
        jobRequirements.add("Kafka");

        // Копіюємо, щоб не змінити оригінальну множину кандидата
        Set<String> matched = new HashSet<>(candidateSkills);
        matched.retainAll(jobRequirements); // залишити лише спільні

        System.out.println("Навички кандидата: " + candidateSkills);
        System.out.println("Вимоги вакансії:   " + jobRequirements);
        System.out.println("Збіг (перетин):    " + matched);
        System.out.println();

        // ============================================================
        //   Задача 4: об'єднання множин — усі теги двох статей (addAll)
        // ============================================================
        System.out.println("=== Об'єднання: теги двох статей ===");

        Set<String> articleATags = new HashSet<>();
        articleATags.add("java");
        articleATags.add("collections");
        articleATags.add("list");

        Set<String> articleBTags = new HashSet<>();
        articleBTags.add("java");
        articleBTags.add("set");
        articleBTags.add("hashset");

        Set<String> allTags = new HashSet<>(articleATags);
        allTags.addAll(articleBTags); // об'єднання (дублікати автоматично зникають)

        System.out.println("Теги статті A:     " + articleATags);
        System.out.println("Теги статті B:     " + articleBTags);
        System.out.println("Усі теги (union):  " + allTags);
        System.out.println();

        // ============================================================
        //   Задача 5: різниця множин — чого не вистачає кандидату
        // ============================================================
        System.out.println("=== Різниця: чого не вистачає кандидату ===");

        Set<String> missing = new HashSet<>(jobRequirements);
        missing.removeAll(candidateSkills); // залишити те, чого немає в кандидата

        System.out.println("Треба довчити: " + missing);
    }
}
