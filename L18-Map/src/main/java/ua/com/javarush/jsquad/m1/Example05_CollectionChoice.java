package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Лекція 18: Колекції Map — Вибір колекції (ArrayList vs HashSet vs HashMap)
 *
 * Три основні колекції та їхня складність (Big-O):
 *
 *   Операція        | ArrayList | HashSet | HashMap
 *   ────────────────┼───────────┼─────────┼────────
 *   Додавання       |   O(1)*  |  O(1)   |  O(1)
 *   Пошук за індекс.|   O(1)  |   —     |   —
 *   Пошук за знач.  |   O(n)  |  O(1)   |  O(1) за ключем
 *   Видалення       |   O(n)  |  O(1)   |  O(1)
 *
 *   * — амортизовано (при збільшенні масиву буде O(n))
 *
 * Коли що використовувати:
 *   - ArrayList: потрібен порядок, доступ за індексом, допускаються дублікати
 *   - HashSet: потрібна унікальність, швидка перевірка «чи є елемент?»
 *   - HashMap: потрібен зв'язок «ключ → значення», швидкий пошук за ключем
 *
 * Аналогія з життя: облік відвідувачів музею.
 *   - ArrayList: журнал усіх візитів (хронологічний, з повторами)
 *   - HashSet: список унікальних відвідувачів (хто був хоча б раз)
 *   - HashMap: кількість візитів кожного відвідувача (хто → скільки разів)
 *
 * Реальне застосування: вибір структури даних — одне з найважливіших рішень
 * під час проектування програми.
 */
public class Example05_CollectionChoice {

    public static void main(String[] args) {

        // Дані: журнал входу відвідувачів музею за тиждень
        String[] visitLog = {
                "Олена", "Андрій", "Марія", "Олена", "Дмитро",
                "Андрій", "Олена", "Марія", "Андрій", "Петро"
        };

        // ============================================================
        //   Підхід 1: ArrayList — зберігаємо ВСІ візити (з повторами)
        // ============================================================
        System.out.println("=== ArrayList: журнал усіх візитів ===");

        ArrayList<String> allVisits = new ArrayList<>();
        for (String visitor : visitLog) {
            allVisits.add(visitor);
        }

        System.out.println("Усі візити: " + allVisits);
        System.out.println("Загальна кількість візитів: " + allVisits.size()); // 10
        System.out.println("Хто прийшов 3-м: " + allVisits.get(2)); // доступ за індексом
        System.out.println();

        // ============================================================
        //   Підхід 2: HashSet — зберігаємо УНІКАЛЬНИХ відвідувачів
        // ============================================================
        System.out.println("=== HashSet: унікальні відвідувачі ===");

        HashSet<String> uniqueVisitors = new HashSet<>();
        for (String visitor : visitLog) {
            uniqueVisitors.add(visitor); // дублікати ігноруються автоматично
        }

        System.out.println("Унікальні відвідувачі: " + uniqueVisitors);
        System.out.println("Кількість унікальних: " + uniqueVisitors.size()); // 5
        System.out.println("Чи був Петро? " + uniqueVisitors.contains("Петро")); // true — O(1)!
        System.out.println("Чи був Іван? " + uniqueVisitors.contains("Іван")); // false — O(1)!
        System.out.println();

        // ============================================================
        //   Підхід 3: HashMap — підрахунок візитів кожного
        // ============================================================
        System.out.println("=== HashMap: кількість візитів кожного ===");

        HashMap<String, Integer> visitCount = new HashMap<>();
        for (String visitor : visitLog) {
            if (visitCount.containsKey(visitor)) {
                visitCount.put(visitor, visitCount.get(visitor) + 1);
            } else {
                visitCount.put(visitor, 1);
            }
        }

        System.out.println("Статистика відвідувань:");
        for (String visitor : visitCount.keySet()) {
            System.out.println("  " + visitor + " — " + visitCount.get(visitor) + " раз(ів)");
        }
        System.out.println("Скільки разів була Олена? " + visitCount.get("Олена")); // 3
        System.out.println();

        // ============================================================
        //   Блок 4: Порада — як обрати колекцію
        // ============================================================
        System.out.println("=== Порада: як обрати колекцію ===");
        System.out.println("Запитайте себе:");
        System.out.println("  1. Потрібен порядок/індекс?    → ArrayList");
        System.out.println("  2. Потрібна унікальність?      → HashSet");
        System.out.println("  3. Потрібен зв'язок ключ→знач? → HashMap");
        System.out.println();
        System.out.println("Пам'ятайте: неправильний вибір колекції може");
        System.out.println("зробити програму у 1000 разів повільнішою!");
    }
}
