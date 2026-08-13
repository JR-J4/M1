package ua.com.javarush.jsquad.m1.example04_inner_vs_nested;

import java.lang.reflect.Field;

/**
 * Модуль 2. Рівень 15. Внутрішні та вкладені класи
 * <hr>
 * <h3>Тема: Внутрішній чи вкладений — як обрати</h3>
 *
 * <p>Обидва оголошуються всередині іншого класу, але різниця одна й головна:
 * <b>наявність прихованого посилання на зовнішній об'єкт</b>.</p>
 *
 * <pre>
 *                              внутрішній (inner)        вкладений (static nested)
 *   ────────────────────────────────────────────────────────────────────────────────
 *   слово static              немає                      є
 *   посилання на Outer.this   є (приховане поле this$0)  немає
 *   як створити               outer.new Inner()          new Outer.Nested()
 *   бачить поля об'єкта Outer так                        ні
 *   бачить private static     так                        так
 *   тримає Outer у пам'яті    так (ризик витоку)         ні
 *   приклад із JDK            ArrayList.Itr              Integer.IntegerCache, Map.Entry
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> закладка в книжці і сама сторінка. Закладка (внутрішній
 * клас) має сенс лише в конкретній книжці й "пам'ятає" її. Сторінка як аркуш паперу
 * (вкладений клас) — просто носій тексту, книжку вона не пам'ятає.</p>
 *
 * <p><b>Реальне застосування:</b> правило-підказка — <i>почни зі static; прибери
 * static тільки тоді, коли класу справді потрібен доступ до полів зовнішнього
 * об'єкта</i>. Саме так радить Effective Java, і саме так зроблено в JDK.</p>
 */
public class Example04_InnerVsNested {

    public static void main(String[] args) throws Exception {

        // === 1. Наповнюємо список: вузли (вкладені) створює сам список ===
        SimpleList shopping = new SimpleList("Покупки");
        shopping.add("хліб");
        shopping.add("молоко");
        shopping.add("кава");

        System.out.println("1. Список створено, елементів: " + shopping.getSize());

        System.out.println();

        // === 2. Курсор (внутрішній клас) читає стан свого списку ===
        SimpleList.Cursor cursor = shopping.cursor();
        System.out.println("2. " + cursor.describe());
        System.out.print("   Обхід:");
        while (cursor.hasNext()) {
            System.out.print(" " + cursor.next());
        }
        System.out.println();

        System.out.println();

        // === 3. Зв'язок живий: додали елемент — курсор бачить нове значення size ===
        // Сценарій: у курсора не копія числа, а посилання на об'єкт списку.
        SimpleList.Cursor cursor2 = shopping.cursor();
        System.out.println("3. До додавання: " + cursor2.describe());
        shopping.add("сир");
        System.out.println("   Після додавання: " + cursor2.describe());

        System.out.println();

        // === 4. Вкладений Node створюється без жодного списку ===
        // Порівняйте два рядки: вузлу зовнішній об'єкт не потрібен, курсору — потрібен.
        SimpleList.Node loneNode = new SimpleList.Node("вузол сам по собі");
        System.out.println("4. Вкладений вузол створено без списку: " + loneNode.getClass().getName());
        // SimpleList.Cursor loneCursor = new SimpleList.Cursor();   // помилка компіляції
        System.out.println("   А от new SimpleList.Cursor() не скомпілюється — потрібен об'єкт списку.");

        System.out.println();

        // === 5. Доказ: подивимося на поля класів через рефлексію ===
        // Компілятор сам додає внутрішньому класу приховане поле this$0.
        System.out.println("5. Поля, які реально є в класах (включно з прихованими):");
        printFields(SimpleList.Cursor.class);
        printFields(SimpleList.Node.class);
        System.out.println("   this$0 — те саме приховане посилання на зовнішній об'єкт.");

        System.out.println();

        // === 6. Чому це важливо: витік пам'яті ===
        // Сценарій: віддали "легкий" об'єкт назовні, а разом з ним — і весь важкий список.
        System.out.println("6. Практичний наслідок:");
        System.out.println("   Поки живий один Cursor, збирач сміття не прибере ВЕСЬ SimpleList.");
        System.out.println("   Якщо клас не читає полів зовнішнього об'єкта — ставте static,");
        System.out.println("   інакше отримаєте зайве утримання пам'яті на рівному місці.");
    }

    /** Друкує оголошені поля класу — включно зі згенерованими компілятором. */
    private static void printFields(Class<?> type) {
        System.out.print("   " + type.getSimpleName() + ":");
        for (Field field : type.getDeclaredFields()) {
            System.out.print(" " + field.getName() + " (" + field.getType().getSimpleName() + ")");
        }
        System.out.println();
    }
}
