package ua.com.javarush.jsquad.m1.example07_jdk_examples;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Модуль 2. Рівень 15. Внутрішні та вкладені класи
 * <hr>
 * <h3>Тема: Приклади різних типів класів з JDK</h3>
 *
 * <p>Внутрішні та вкладені класи — не екзотика "для співбесіди". Вони всюди
 * в стандартній бібліотеці, і зараз ми подивимося на них наживо.</p>
 *
 * <pre>
 *  1. Приклад внутрішнього класу
 *     У класі AbstractList є внутрішній клас Itr. Це реалізація інтерфейсу
 *     Iterator, який дозволяє по черзі отримувати елементи колекцій.
 *
 *  2. Приклад внутрішнього статичного класу
 *     У класі Integer є внутрішній клас IntegerCache.
 *
 *  3. Приклад внутрішнього анонімного класу
 *     Клас InputStream і його статичний метод nullInputStream.
 * </pre>
 *
 * <h4>Як читати технічні імена класів:</h4>
 * <pre>
 *   java.util.ArrayList$Itr        — клас Itr усередині ArrayList
 *   java.io.InputStream$1          — 1-й анонімний клас усередині InputStream
 *   Outer$1Local                   — локальний клас Local у методі класу Outer
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> службові приміщення в супермаркеті. Покупець їх
 * не бачить, у каталозі вони не значаться, але без них магазин не працює.</p>
 *
 * <p><b>Реальне застосування:</b> вміння прочитати {@code $1} у стектрейсі —
 * цілком практична навичка: одразу видно, що виняток стався в анонімному
 * слухачі, а не в самому класі.</p>
 */
public class Example07_JdkExamples {

    public static void main(String[] args) throws IOException {

        // === 1. Внутрішній клас: ітератор колекції ===
        // Ітератор мусить бачити стан свого списку (масив, розмір, лічильник змін),
        // тому він саме ВНУТРІШНІЙ, а не статичний.
        ArrayList<String> planets = new ArrayList<>(List.of("Меркурій", "Венера", "Земля"));
        Iterator<String> iterator = planets.iterator();

        System.out.println("1. Внутрішній клас — ітератор:");
        System.out.println("   Клас ітератора ArrayList: " + iterator.getClass().getName());
        System.out.print("   Обхід:");
        while (iterator.hasNext()) {
            System.out.print(" " + iterator.next());
        }
        System.out.println();

        // Той самий Itr, але вже з AbstractList — саме про нього йдеться в лекції.
        // Заразом це анонімний нащадок абстрактного класу (див. приклад 05).
        List<String> abstractBased = new AbstractList<>() {
            @Override
            public String get(int index) {
                return "елемент-" + index;
            }

            @Override
            public int size() {
                return 3;
            }
        };

        System.out.println("   Наш анонімний список:     " + abstractBased.getClass().getName());
        System.out.println("   Клас його ітератора:      " + abstractBased.iterator().getClass().getName());
        System.out.println("   Вміст: " + abstractBased);

        System.out.println();

        // === 2. Вкладений (статичний) клас: Integer.IntegerCache ===
        // Усередині Integer є private static class IntegerCache — масив уже готових
        // об'єктів Integer для чисел від -128 до 127. Тому маленькі числа
        // при автопакуванні НЕ створюються заново, а беруться з кешу.
        Integer a = 127, b = 127;         // обидва з кешу → один і той самий об'єкт
        Integer c = 128, d = 128;         // поза кешем → два різні об'єкти

        System.out.println("2. Вкладений клас — Integer.IntegerCache:");
        System.out.println("   127 == 127 → " + (a == b) + "   (той самий об'єкт із кешу)");
        System.out.println("   128 == 128 → " + (c == d) + "  (два нові об'єкти)");
        System.out.println("   128.equals(128) → " + c.equals(d) + "  ← значення все одно рівні");
        System.out.println("   Кеш статичний, бо він спільний для всієї програми,");
        System.out.println("   а не для якогось конкретного об'єкта Integer.");

        System.out.println();

        // === 3. Анонімний клас: InputStream.nullInputStream() ===
        // Метод повертає "порожній" потік — об'єкт анонімного нащадка InputStream.
        // Окремий іменований клас заради цього створювати не стали.
        try (InputStream empty = InputStream.nullInputStream()) {
            System.out.println("3. Анонімний клас — InputStream.nullInputStream():");
            System.out.println("   Клас об'єкта:  " + empty.getClass().getName());
            System.out.println("   Предок:        " + empty.getClass().getSuperclass().getName());
            System.out.println("   read() повертає " + empty.read() + " (одразу кінець потоку)");
            System.out.println("   Це патерн Null Object: замість null — безпечна заглушка.");
        }

        System.out.println();

        // === 4. Ще вкладені класи, які ви вже використовували ===
        Map<String, Integer> stock = new HashMap<>();
        stock.put("кава", 12);

        Map.Entry<String, Integer> entry = stock.entrySet().iterator().next();

        System.out.println("4. Вкладені типи, які трапляються щодня:");
        System.out.println("   Map.Entry (вкладений інтерфейс):   " + Map.Entry.class.getName());
        System.out.println("   Реальний клас пари з HashMap:      " + entry.getClass().getName());
        System.out.println("   Thread.State (вкладений enum):     " + Thread.State.class.getName());
        System.out.println("   Незмінний список із Collections:   "
                + Collections.unmodifiableList(planets).getClass().getName());

        System.out.println();

        // === 5. Чому саме такий вибір зробили автори JDK ===
        System.out.println("5. Логіка вибору в JDK:");
        System.out.println("   Itr          → внутрішній: без свого списку не має сенсу");
        System.out.println("   IntegerCache → вкладений:  спільний на всю програму");
        System.out.println("   ArrayList.Node → вкладений:  вузлів мільйони, зайве посилання дороге");

        System.out.println("   Map.Entry → вкладений:  вузлів мільйони, зайве посилання дороге");
        System.out.println("   InputStream$1 → анонімний: потрібен рівно в одному методі");
    }
}
