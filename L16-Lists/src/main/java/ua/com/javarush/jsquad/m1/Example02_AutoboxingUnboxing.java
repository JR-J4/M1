package ua.com.javarush.jsquad.m1;

/**
 * Лекція 16: Списки — Autoboxing та Unboxing
 *
 * Autoboxing — автоматичне перетворення примітивного типу в його клас-обгортку.
 * Unboxing — зворотна операція: обгортка автоматично перетворюється на примітив.
 *
 * Що бачить компілятор:
 *   Ваш код:              Що бачить компілятор:
 *   Integer a = 10;       Integer a = Integer.valueOf(10);
 *   int b = a;            int b = a.intValue();
 *   Integer c = a + b;    Integer c = Integer.valueOf(a.intValue() + b);
 *
 * Аналогія з життя: autoboxing — це як загорнути подарунок у коробку автоматично,
 * коли кладеш на полицю. Unboxing — дістати подарунок із коробки, коли хочеш ним
 * скористатися. Java робить це за вас, без зайвого коду.
 *
 * Реальне застосування: працюючи з колекціями, ви постійно використовуєте
 * autoboxing/unboxing, навіть не помічаючи цього.
 */
public class Example02_AutoboxingUnboxing {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Autoboxing — примітив -> обгортка
        // ============================================================
        System.out.println("=== Autoboxing ===");

        // Уявіть: касир автоматично пакує товар у коробку
        Integer a = 10;          // компілятор: Integer.valueOf(10)
        Double pi = 3.14;        // компілятор: Double.valueOf(3.14)
        Boolean flag = true;     // компілятор: Boolean.valueOf(true)
        Character ch = 'A';      // компілятор: Character.valueOf('A')

        System.out.println("Integer a = " + a);     // 10
        System.out.println("Double pi = " + pi);    // 3.14
        System.out.println("Boolean flag = " + flag); // true
        System.out.println("Character ch = " + ch);  // A
        System.out.println();

        // ============================================================
        //   Приклад 2: Unboxing — обгортка -> примітив
        // ============================================================
        System.out.println("=== Unboxing ===");

        // Уявіть: розпакування посилки — дістаємо вміст
        Integer boxed = 42;
        int unboxed = boxed;     // компілятор: boxed.intValue()

        Double boxedPrice = 99.99;
        double price = boxedPrice; // компілятор: boxedPrice.doubleValue()

        System.out.println("Integer -> int: " + unboxed); // 42
        System.out.println("Double -> double: " + price);  // 99.99
        System.out.println();

        // ============================================================
        //   Приклад 3: Autoboxing в арифметичних виразах
        // ============================================================
        System.out.println("=== Autoboxing в арифметиці ===");

        // Уявіть: калькулятор знижок в інтернет-магазині
        Integer originalPrice = 1000;
        Integer discount = 200;

        // Java автоматично розпаковує, рахує, і пакує назад
        Integer finalPrice = originalPrice - discount;
        // компілятор: Integer.valueOf(originalPrice.intValue() - discount.intValue())

        System.out.println("Початкова ціна: " + originalPrice);
        System.out.println("Знижка: " + discount);
        System.out.println("Фінальна ціна: " + finalPrice);
        System.out.println();

        // ============================================================
        //   Приклад 4: Порівняння обгорток — підступна пастка
        // ============================================================
        System.out.println("=== Порівняння обгорток ===");

        // Уявіть: дві коробки з однаковими цукерками — коробки різні, вміст однаковий
        Integer x = 127;
        Integer y = 127;
        System.out.println("x = 127, y = 127");
        System.out.println("x == y: " + (x == y));           // true (кеш від -128 до 127)
        System.out.println("x.equals(y): " + x.equals(y));   // true

        Integer m = 200;
        Integer n = 200;
        System.out.println("m = 200, n = 200");
        System.out.println("m == n: " + (m == n));           // false (поза кешем!)
        System.out.println("m.equals(n): " + m.equals(n));   // true

        System.out.println("Висновок: завжди використовуйте equals() для порівняння обгорток!");
        System.out.println();

        // ============================================================
        //   Приклад 5: NullPointerException при unboxing
        // ============================================================
        System.out.println("=== Обережно з null! ===");

        Integer nullValue = null;
        System.out.println("Integer nullValue = null");
        // int bad = nullValue; // NullPointerException! Не можна розпакувати null

        // Безпечна перевірка перед розпакуванням
        if (nullValue != null) {
            int safe = nullValue;
            System.out.println("Значення: " + safe);
        } else {
            System.out.println("Значення відсутнє (null) — розпакування неможливе!");
        }
    }
}
