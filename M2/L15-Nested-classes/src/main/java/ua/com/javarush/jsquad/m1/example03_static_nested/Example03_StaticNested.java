package ua.com.javarush.jsquad.m1.example03_static_nested;

/**
 * Модуль 2. Рівень 15. Внутрішні та вкладені класи
 * <hr>
 * <h3>Тема: Внутрішні статичні класи (вкладені класи)</h3>
 *
 * <p>Внутрішні статичні класи ще називають <b>вкладеними</b>. Перед оголошенням
 * внутрішнього класу ставимо {@code static} — і внутрішній клас стає вкладеним.</p>
 *
 * <p>Слово {@code static} перед класом означає те саме, що й перед методом:
 * <b>клас не містить посилання на об'єкт зовнішнього класу</b>. У вкладених
 * статичних класів немає прихованих посилань на зовнішній об'єкт, у якому
 * вони оголошені.</p>
 *
 * <h4>Синтаксис:</h4>
 * <pre>
 *   class Outer {
 *       private static int counter;
 *       static class Nested {              // вкладений клас
 *           void print() {
 *               System.out.println(counter);   // бачить private static зовнішнього класу
 *           }
 *       }
 *   }
 *
 *   Outer.Nested nested = new Outer.Nested();   // об'єкт Outer НЕ потрібен
 * </pre>
 *
 * <h4>Дві особливості вкладених класів:</h4>
 * <pre>
 *   1. При створенні об'єктів вкладеного класу поза межами зовнішнього класу-батька
 *      потрібно ще вказати через крапку й ім'я зовнішнього класу: new Outer.Nested().
 *   2. Вкладений клас та його об'єкти мають доступ до private static змінних
 *      і методів зовнішнього класу-батька.
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> піцерія і бланк замовлення. Бланк лежить у піцерії,
 * але він не "чийсь" — це просто аркуш. Заповнити його може будь-хто, і жодну
 * конкретну піцу він не пам'ятає. Зате знає правила саме цієї піцерії.</p>
 *
 * <p><b>Реальне застосування:</b> Builder (як тут), вузол списку {@code Node},
 * пара ключ-значення {@code Map.Entry}, кеш {@code Integer.IntegerCache}.
 * Правило: <b>якщо внутрішньому класу не потрібен зовнішній об'єкт — пишіть static</b>.</p>
 */
public class Example03_StaticNested {

    public static void main(String[] args) {

        // === 1. Вкладений клас створюється БЕЗ об'єкта зовнішнього класу ===
        // Сценарій: збираємо піцу через Builder. Об'єкта Pizza ще не існує!
        System.out.println("1. Збираємо піцу будівельником:");

        Pizza pepperoni = new Pizza.Builder()   // ← ім'я зовнішнього класу через крапку
                .size(Pizza.Size.MEDIUM)
                .topping("пепероні")
                .topping("моцарела")
                .thinDough()
                .bake();

        System.out.println("   " + pepperoni);

        System.out.println();

        // === 2. Той самий Builder — ще одна піца, зовсім незалежна ===
        System.out.println("2. Друга піца тим самим вкладеним класом:");

        Pizza veggie = new Pizza.Builder()
                .topping("гриби")
                .topping("оливки")
                .topping("перець")
                .bake();

        System.out.println("   " + veggie);
        System.out.println("   Усього випечено піц: " + Pizza.getBakedCount());

        System.out.println();

        // === 3. Доступ вкладеного класу до private static зовнішнього ===
        // Builder всередині bake() робив: bakedCount++ і log(...) —
        // обидва члени приватні й статичні, але для вкладеного класу вони "свої".
        System.out.println("3. Доступ до private static зовнішнього класу:");
        System.out.println("   Рядки [Піцерія «Дві печі»] вище надрукував саме Builder,");
        System.out.println("   викликавши private static метод log() зовнішнього класу Pizza.");

        System.out.println();

        // === 4. Об'єкт вкладеного класу можна тримати окремо ===
        // Сценарій: заготовка замовлення, яку добудуємо пізніше.
        System.out.println("4. Об'єкт Builder живе сам по собі:");

        Pizza.Builder draft = new Pizza.Builder();
        draft.topping("шинка");
        System.out.println("   Заготовку створено, піци ще немає...");
        draft.topping("ананас");
        Pizza hawaii = draft.bake();
        System.out.println("   " + hawaii);

        System.out.println();

        // === 5. Enum усередині класу — теж вкладений тип ===
        // Для enum, record та interface усередині класу слово static мається на увазі.
        System.out.println("5. Вкладений enum Pizza.Size:");
        for (Pizza.Size size : Pizza.Size.values()) {
            System.out.println("   " + size + " → " + size.getCm() + " см");
        }

        System.out.println();

        // === 6. Як це виглядає для JVM ===
        System.out.println("6. Імена класів:");
        System.out.println("   Builder: " + Pizza.Builder.class.getName());
        System.out.println("   Size:    " + Pizza.Size.class.getName());
        System.out.println("   Знак $ такий самий, як у внутрішніх класів, —");
        System.out.println("   різниця лише в тому, що прихованого посилання на Pizza тут немає.");
    }
}
