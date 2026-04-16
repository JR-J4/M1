package ua.com.javarush.jsquad.m1;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Лекція 17: Колекції Set — Ітератор
 *
 * Ітератор — це спеціальний об'єкт у колекції, який допомагає обійти
 * ВСІ її елементи та не повторюватись.
 *
 * Отримати ітератор:
 *   Iterator<Тип> it = ім'я.iterator();
 *
 * Три основні методи ітератора:
 *   - Тип     next()     — повертає черговий елемент колекції
 *   - boolean hasNext()  — чи є ще непройдені елементи
 *   - void    remove()   — видаляє ПОТОЧНИЙ елемент (щойно отриманий через next())
 *
 * Особливо корисний для Set, де немає індексів і немає методу get(i) —
 * без ітератора неможливо обійти всі елементи множини.
 *
 * Важливо! Не можна змінювати колекцію (наприклад, викликати set.remove())
 * під час ітерації — це спричинить ConcurrentModificationException.
 * Видаляти треба через сам ітератор — it.remove().
 *
 * Аналогія з життя: ітератор — це екскурсовод. Він знає, куди йти далі,
 * чи є ще зали музею попереду (hasNext), і веде вас від експоната до експоната
 * (next). Якщо вам дозволено щось прибрати — робите це "руками" екскурсовода (remove).
 *
 * Реальне застосування: обхід учасників черги на реєстрацію, фільтрація
 * колекції з видаленням «непотрібних» елементів.
 */
public class Example05_Iterator {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Базовий обхід Set ітератором
        // ============================================================
        System.out.println("=== Базовий обхід ітератором ===");

        Set<String> participants = new HashSet<>();
        participants.add("Анна");
        participants.add("Богдан");
        participants.add("Вікторія");
        participants.add("Григорій");

        // Отримуємо ітератор
        Iterator<String> it = participants.iterator();

        // Обходимо, поки є елементи
        while (it.hasNext()) {
            String name = it.next();
            System.out.println("Учасник: " + name);
        }
        System.out.println();

        // ============================================================
        //   Приклад 2: Три методи ітератора в дії
        // ============================================================
        System.out.println("=== hasNext / next / remove ===");

        Set<Integer> numbers = new HashSet<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);

        Iterator<Integer> numIt = numbers.iterator();

        System.out.println("hasNext() перед обходом: " + numIt.hasNext()); // true

        // Перший виклик next()
        int first = numIt.next();
        System.out.println("Перший next(): " + first);

        // Другий виклик next()
        int second = numIt.next();
        System.out.println("Другий next(): " + second);

        // remove() видаляє ОСТАННІЙ отриманий через next() елемент
        numIt.remove();
        System.out.println("Видалено другий елемент. Set: " + numbers);

        // Дочитуємо решту
        while (numIt.hasNext()) {
            System.out.println("Залишок: " + numIt.next());
        }
        System.out.println();

        // ============================================================
        //   Приклад 3: Сценарій — черга на реєстрацію
        // ============================================================
        System.out.println("=== Черга на реєстрацію (видаляємо без квитка) ===");

        // Учасники черги
        Set<String> queue = new HashSet<>();
        queue.add("Олег:квиток");
        queue.add("Ірина:без квитка");
        queue.add("Тарас:квиток");
        queue.add("Наталя:без квитка");
        queue.add("Дмитро:квиток");

        System.out.println("До реєстрації: " + queue);

        // Видаляємо через ітератор тих, хто без квитка
        Iterator<String> qIt = queue.iterator();
        while (qIt.hasNext()) {
            String person = qIt.next();
            if (person.contains("без квитка")) {
                qIt.remove(); // ПРАВИЛЬНИЙ спосіб видалення під час обходу
                System.out.println("Виганяємо: " + person);
            }
        }

        System.out.println("Після реєстрації: " + queue);
        System.out.println();

        // ============================================================
        //   Приклад 4: Чому НЕ МОЖНА set.remove() під час ітерації
        // ============================================================
        System.out.println("=== Чому set.remove() під час обходу — це погано ===");

        Set<String> demo = new HashSet<>();
        demo.add("A");
        demo.add("B");
        demo.add("C");

        // НЕ ТАК — закоментовано, бо викине ConcurrentModificationException:
        //
        // Iterator<String> badIt = demo.iterator();
        // while (badIt.hasNext()) {
        //     String s = badIt.next();
        //     if (s.equals("B")) {
        //         demo.remove("B");     // <-- заборонено! кине виняток
        //     }
        // }

        // Продемонструємо виняток явно через try/catch:
        try {
            Iterator<String> badIt = demo.iterator();
            while (badIt.hasNext()) {
                String s = badIt.next();
                if (s.equals("B")) {
                    demo.remove("B"); // змінили колекцію повз ітератор → виняток
                }
            }
        } catch (java.util.ConcurrentModificationException ex) {
            System.out.println("Отримали ConcurrentModificationException — саме тому");
            System.out.println("під час обходу треба викликати it.remove(), а не set.remove().");
        }
    }
}
