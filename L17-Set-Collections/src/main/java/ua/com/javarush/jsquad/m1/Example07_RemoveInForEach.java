package ua.com.javarush.jsquad.m1;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Лекція 17: Колекції Set — Видалення елемента в циклі for-each
 *
 * У циклу for-each є один серйозний недолік: він НЕ ВМІЄ правильно видаляти
 * елементи з колекції, яку саме обходить.
 *
 * Якщо в тілі for-each зробити set.remove(...), то під час наступної ітерації
 * прихований ітератор помітить, що колекція змінилась "повз нього", і викине
 * ConcurrentModificationException.
 *
 * Є ТРИ способи обійти це обмеження:
 *   1. Використати інший тип циклу (while + ітератор).
 *   2. Явне використання ітератора (it.remove()).
 *   3. Використати копію колекції (обходимо копію, видаляємо з оригіналу).
 *
 * Важливо! Не можна змінювати колекцію, поки ви обходите її через ітератор
 * (ні явний, ні прихований у for-each).
 *
 * Задача з лекції: видалити від'ємні числа з HashSet<Integer>.
 *
 * Аналогія з життя: не можна рахувати гостей на вечірці і одночасно випроваджувати
 * їх за двері — збиваєшся з рахунку. Або спочатку склади список тих, кого просиш піти,
 * або йди зі списком у руці й викреслюй людей з нього, а не з кімнати.
 *
 * Реальне застосування: чистка кешу від застарілих записів, видалення "мертвих"
 * з'єднань, фільтрація подій перед обробкою.
 */
public class Example07_RemoveInForEach {

    public static void main(String[] args) {

        // ============================================================
        //   Проблема: НЕПРАВИЛЬНЕ видалення у for-each
        // ============================================================
        System.out.println("=== Проблема: set.remove() у for-each ===");

        Set<Integer> numbers = new HashSet<>();
        numbers.add(7);
        numbers.add(-2);
        numbers.add(-7);
        numbers.add(14);
        numbers.add(4);
        System.out.println("Початкові числа: " + numbers);

        // НЕ ТАК — закоментовано, бо викине ConcurrentModificationException:
        //
        // for (Integer number : numbers) {
        //     if (number < 0) {
        //         numbers.remove(number);   // <-- модифікуємо колекцію під час обходу!
        //     }
        // }

        // Продемонструємо виняток явно через try/catch:
        try {
            for (Integer number : numbers) {
                if (number < 0) {
                    numbers.remove(number); // кидає ConcurrentModificationException
                }
            }
        } catch (ConcurrentModificationException ex) {
            System.out.println("Отримали ConcurrentModificationException!");
            System.out.println("Висновок: у for-each прямий set.remove() заборонений.");
        }
        System.out.println();

        // ============================================================
        //   Спосіб 1: інший тип циклу (while + ітератор)
        // ============================================================
        System.out.println("=== Спосіб 1: while + Iterator ===");

        Set<Integer> numbers1 = new HashSet<>();
        numbers1.add(7);
        numbers1.add(-2);
        numbers1.add(-7);
        numbers1.add(14);
        numbers1.add(4);
        System.out.println("До:    " + numbers1);

        Iterator<Integer> it = numbers1.iterator();
        while (it.hasNext()) {
            int number = it.next();
            if (number < 0) {
                it.remove(); // ПРАВИЛЬНО: видаляємо через ітератор
            }
        }
        System.out.println("Після: " + numbers1);
        System.out.println();

        // ============================================================
        //   Спосіб 2: явне використання ітератора у for-циклі
        // ============================================================
        System.out.println("=== Спосіб 2: явний ітератор (for-форма) ===");

        Set<Integer> numbers2 = new HashSet<>();
        numbers2.add(7);
        numbers2.add(-2);
        numbers2.add(-7);
        numbers2.add(14);
        numbers2.add(4);
        System.out.println("До:    " + numbers2);

        // Той самий ітератор, але в іншій формі запису (for замість while)
        for (Iterator<Integer> iter = numbers2.iterator(); iter.hasNext(); ) {
            int number = iter.next();
            if (number < 0) {
                iter.remove();
            }
        }
        System.out.println("Після: " + numbers2);
        System.out.println();

        // ============================================================
        //   Спосіб 3: копія колекції (приклад з PDF)
        // ============================================================
        System.out.println("=== Спосіб 3: копія колекції ===");

        Set<Integer> numbers3 = new HashSet<>();
        numbers3.add(7);
        numbers3.add(-2);
        numbers3.add(-7);
        numbers3.add(14);
        numbers3.add(4);
        System.out.println("До:    " + numbers3);

        // Створюємо копію — обходимо КОПІЮ, а видаляємо з ОРИГІНАЛУ.
        // Ітератор копії нічого не знає про зміни в оригіналі — винятку не буде.
        Set<Integer> copyNumbers = new HashSet<>(numbers3);
        for (Integer number : copyNumbers) {
            if (number < 0) {
                numbers3.remove(number);
            }
        }
        System.out.println("Після: " + numbers3);
        System.out.println();

        // ============================================================
        //   Підсумок: який спосіб обрати
        // ============================================================
        System.out.println("=== Який спосіб обрати? ===");
        System.out.println("1) while + it.remove()  — найефективніше, без зайвої пам'яті.");
        System.out.println("2) for + it.remove()    — те саме, компактніше.");
        System.out.println("3) копія колекції       — коли потрібна стара версія для звіту/логу.");
    }
}
