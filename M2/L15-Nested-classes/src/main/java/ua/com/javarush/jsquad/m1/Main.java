package ua.com.javarush.jsquad.m1;

/**
 * Модуль 2. Рівень 15. Внутрішні та Вкладені класи — ЗМІСТ ПРИКЛАДІВ
 *
 * <p>Лекція охоплює: внутрішні класи, внутрішні статичні (вкладені) класи,
 * внутрішні анонімні класи та приклади різних типів класів з JDK.</p>
 *
 * <p>Кожен приклад — самодостатній клас зі своїм {@code main()}.
 * Запускайте їх по черзі (відкрийте файл і натисніть ▶ біля {@code main}).</p>
 *
 * <pre>
 *  №   Тема                                          Клас для запуску
 *  ──────────────────────────────────────────────────────────────────────────────────────────
 *  01  Внутрішні класи (готель і номери)             example01_inner_class.Example01_InnerClass
 *  02  Особливості внутрішніх класів (4 правила)     example02_inner_class_features.Example02_InnerClassFeatures
 *  03  Вкладені (static) класи, патерн Builder       example03_static_nested.Example03_StaticNested
 *  04  Внутрішній чи вкладений — як обрати           example04_inner_vs_nested.Example04_InnerVsNested
 *  05  Анонімні класи (Thread, Comparator, абстракт) example05_anonymous_class.Example05_AnonymousClass
 *  06  Анонімні зсередини: this, захоплення, лямбди  example06_anonymous_details.Example06_AnonymousDetails
 *  07  Приклади з JDK (Itr, IntegerCache, $1)        example07_jdk_examples.Example07_JdkExamples
 *  08  Підсумок: служба таксі                        example08_summary.Example08_Summary
 * </pre>
 *
 * <h4>Коротка шпаргалка:</h4>
 * <pre>
 *   class Inner { }          внутрішній: outer.new Inner(), є приховане Outer.this,
 *                            бачить усі поля зовнішнього ОБ'ЄКТА
 *   static class Nested { }  вкладений:  new Outer.Nested(), зовнішній об'єкт не потрібен,
 *                            бачить private static зовнішнього КЛАСУ
 *   new Тип() { ... }        анонімний:  оголошення класу-нащадка + створення об'єкта одразу,
 *                            технічне ім'я Outer$1
 *   class Local { }          локальний:  оголошений у тілі методу, має ім'я і конструктор
 *
 *   Правило: почни зі static; прибирай static лише тоді,
 *            коли класу справді потрібні поля зовнішнього об'єкта.
 * </pre>
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Модуль 2. Рівень 15 — Внутрішні та Вкладені класи");
        System.out.println("8 прикладів у пакетах example01..example08.");
        System.out.println("Теми: внутрішні класи, вкладені (static) класи, анонімні класи,");
        System.out.println("      локальні класи, приклади з JDK (ArrayList$Itr, IntegerCache, InputStream$1).");
        System.out.println("Відкрийте потрібний ExampleNN_*.java і запустіть його main().");
    }
}
