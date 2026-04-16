package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Лекція 17: Колекції Set — Контейнери та колекції
 *
 * Контейнерами або колекціями називають класи, які дозволяють зберігати
 * та опрацьовувати багато об'єктів одночасно.
 *
 * У Java є чотири основні сімейства колекцій:
 *   - List    — список (ArrayList, LinkedList, Vector, Stack)
 *   - Set     — множина (HashSet, TreeSet, LinkedHashSet)
 *   - Queue   — черга (PriorityQueue, ArrayQueue)
 *   - Map     — карта/словник (HashMap, TreeMap, HashTable)
 *
 * Усі вони мають спільний «корінь» — інтерфейс Collection (крім Map).
 *
 * Аналогія з життя: контейнери — це різні види меблів для зберігання.
 *   - List — книжкова полиця (елементи в порядку, з номерами-індексами)
 *   - Set  — кошик з фруктами (одне яблуко — значить одне, без порядку)
 *   - Queue — черга в касу (хто перший прийшов, той перший обслужений)
 *   - Map  — словник (кожному слову відповідає переклад)
 *
 * Реальне застосування: залежно від задачі ми обираємо найкращий тип колекції.
 * Список користувачів — ArrayList; унікальні теги статті — HashSet;
 * черга повідомлень на сервері — Queue; телефонна книга — Map.
 */
public class Example01_Collections {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Коротка довідка про сімейства колекцій
        // ============================================================
        System.out.println("=== Сімейства колекцій у Java ===");

        System.out.println("List  -> ArrayList, LinkedList, Vector, Stack   (Список)");
        System.out.println("Set   -> HashSet, TreeSet, LinkedHashSet         (Множина)");
        System.out.println("Queue -> PriorityQueue, ArrayQueue               (Черга)");
        System.out.println("Map   -> HashMap, TreeMap, HashTable             (Карта/Словник)");
        System.out.println();

        // ============================================================
        //   Приклад 2: ArrayList — те, що ми знаємо з попередньої лекції
        // ============================================================
        System.out.println("=== ArrayList (відомо з L16) ===");

        // Уявіть: історія переглянутих товарів в інтернет-магазині
        ArrayList<String> history = new ArrayList<>();
        history.add("Ноутбук");
        history.add("Мишка");
        history.add("Ноутбук");   // дублікат — у List дозволений
        history.add("Клавіатура");
        history.add("Мишка");     // дублікат — у List дозволений

        System.out.println("Історія переглядів: " + history);
        System.out.println("Кількість переглядів: " + history.size()); // 5
        System.out.println("Товар під індексом 0: " + history.get(0)); // за індексом!
        System.out.println();

        // ============================================================
        //   Приклад 3: HashSet — нова колекція з цієї лекції
        // ============================================================
        System.out.println("=== HashSet (нове у L17) ===");

        // Уявіть: перелік унікальних товарів, якими користувач коли-небудь цікавився
        HashSet<String> viewed = new HashSet<>();
        viewed.add("Ноутбук");
        viewed.add("Мишка");
        viewed.add("Ноутбук");     // дублікат — Set НЕ додасть
        viewed.add("Клавіатура");
        viewed.add("Мишка");       // дублікат — Set НЕ додасть

        System.out.println("Унікальні перегляди: " + viewed);
        System.out.println("Кількість унікальних: " + viewed.size()); // 3
        // viewed.get(0); // помилка компіляції — у Set немає індексів!
        System.out.println();

        // ============================================================
        //   Приклад 4: Коли яку колекцію обирати
        // ============================================================
        System.out.println("=== Коли що обирати ===");

        System.out.println("Потрібен порядок і доступ за індексом?         -> List (ArrayList)");
        System.out.println("Потрібна лише унікальність, порядок неважливий? -> Set  (HashSet)");
        System.out.println("Потрібно обробляти в порядку надходження?       -> Queue");
        System.out.println("Потрібна пара ключ -> значення?                 -> Map  (HashMap)");
    }
}
