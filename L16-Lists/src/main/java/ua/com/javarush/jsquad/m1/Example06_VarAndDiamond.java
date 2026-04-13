package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;

/**
 * Лекція 16: Списки — Виведення типів Java-компілятором
 *
 * Java має "синтаксичний цукор" — хитрі особливості, що скорочують код.
 *
 * 1) var (Java 10+) — компілятор сам визначає тип змінної за значенням:
 *    var list = new ArrayList<Integer>();
 *    // еквівалентно: ArrayList<Integer> list = new ArrayList<Integer>();
 *
 * 2) Diamond operator <> (Java 7+) — тип елементів колекції можна не повторювати:
 *    ArrayList<String> list = new ArrayList<>();
 *    // компілятор бере тип із лівої частини
 *
 * УВАГА: var + diamond <> одночасно — не бажано!
 *    var list = new ArrayList<>();  // тип буде ArrayList<Object>
 *
 * Аналогія з життя: коли ви кажете "Дай мені ту зелену кружку" — не треба
 * повторювати "зелену кружку" кожен раз. Компілятор, як розумний співрозмовник,
 * розуміє з контексту.
 *
 * Реальне застосування: скорочення коду у великих проєктах, де повні типи займають
 * багато місця (наприклад, HashMap<String, ArrayList<Integer>>).
 */
public class Example06_VarAndDiamond {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Повний запис (без скорочень)
        // ============================================================
        System.out.println("=== Повний запис ===");

        ArrayList<Integer> scores1 = new ArrayList<Integer>();
        scores1.add(95);
        scores1.add(87);
        scores1.add(92);

        System.out.println("Повний запис: ArrayList<Integer> scores = new ArrayList<Integer>()");
        System.out.println("Оцінки: " + scores1);
        System.out.println();

        // ============================================================
        //   Приклад 2: Diamond operator <>
        // ============================================================
        System.out.println("=== Diamond operator <> ===");

        // Тип вказано зліва — справа можна опустити
        ArrayList<String> cities = new ArrayList<>();  // <> замість <String>
        cities.add("Київ");
        cities.add("Львів");
        cities.add("Одеса");

        System.out.println("З diamond: ArrayList<String> cities = new ArrayList<>()");
        System.out.println("Міста: " + cities);
        System.out.println();

        // ============================================================
        //   Приклад 3: Ключове слово var
        // ============================================================
        System.out.println("=== var ===");

        // Компілятор сам виводить тип з правої частини
        var temperatures = new ArrayList<Double>();
        temperatures.add(22.5);
        temperatures.add(18.3);
        temperatures.add(25.0);

        System.out.println("З var: var temperatures = new ArrayList<Double>()");
        System.out.println("Температури: " + temperatures);

        // var працює і з простими типами
        var name = "Java";               // String
        var version = 17;                 // int
        var pi = 3.14159;                 // double

        System.out.println("var name = \"Java\" -> тип String: " + name);
        System.out.println("var version = 17 -> тип int: " + version);
        System.out.println("var pi = 3.14159 -> тип double: " + pi);
        System.out.println();

        // ============================================================
        //   Приклад 4: var + diamond = погана ідея
        // ============================================================
        System.out.println("=== var + diamond <> ===");

        // НЕ РЕКОМЕНДОВАНО: компілятор не може визначити тип елементів
        var mystery = new ArrayList<>(); // тип = ArrayList<Object>
        mystery.add("Рядок");
        mystery.add(123);
        mystery.add(true);

        // Список став ArrayList<Object> — втрачена типізація!
        System.out.println("var + diamond: " + mystery);
        System.out.println("Тут немає обмеження типу — можна додати що завгодно");
        System.out.println("Це погана практика — уникайте такого запису!");
        System.out.println();

        // ============================================================
        //   Приклад 5: Правильні комбінації
        // ============================================================
        System.out.println("=== Правильні комбінації ===");

        // Варіант 1: повний запис (найнадійніший)
        ArrayList<String> v1 = new ArrayList<String>();
        System.out.println("1) ArrayList<String> v1 = new ArrayList<String>()  [OK]");

        // Варіант 2: diamond (найпоширеніший)
        ArrayList<String> v2 = new ArrayList<>();
        System.out.println("2) ArrayList<String> v2 = new ArrayList<>()        [OK]");

        // Варіант 3: var (зручний, коли тип зрозумілий)
        var v3 = new ArrayList<String>();
        System.out.println("3) var v3 = new ArrayList<String>()                [OK]");

        // Варіант 4: var + diamond (НЕ рекомендовано)
        // var v4 = new ArrayList<>(); // буде ArrayList<Object>
        System.out.println("4) var v4 = new ArrayList<>()                      [ПОГАНО]");
    }
}
