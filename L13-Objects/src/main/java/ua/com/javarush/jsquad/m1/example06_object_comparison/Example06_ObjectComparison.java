package ua.com.javarush.jsquad.m1.example06_object_comparison;

/**
 * Лекція 13: Об'єкти
 * <hr>
 * <h3>Тема: Порівняння об'єктів у Java — equals() та hashCode()</h3>
 *
 * <p>Об'єкти Java можна порівнювати двома способами:</p>
 * <ul>
 *   <li><b>==</b> — порівнює посилання (адреси в пам'яті)</li>
 *   <li><b>equals()</b> — порівнює вміст (значення полів)</li>
 * </ul>
 *
 * <h4>== vs equals():</h4>
 * <pre>
 *   Person p1 = new Person("Анна", 25);
 *   Person p2 = new Person("Анна", 25);
 *
 *   p1 == p2      → false (різні об'єкти в пам'яті!)
 *   p1.equals(p2) → true  (однакові ім'я та вік)
 *
 *     Пам'ять:
 *     ┌──────────┐     ┌──────────┐
 *     │  p1      │     │  p2      │
 *     │ @0x100 ──┼──▶  │ @0x200 ──┼──▶
 *     └──────────┘     └──────────┘
 *      "Анна", 25       "Анна", 25
 *      Різні адреси, але однаковий вміст!
 * </pre>
 *
 * <h4>Клас Object:</h4>
 * <pre>
 *   Усі класи Java успадковують від Object.
 *   Object має методи: equals(), hashCode(), toString()
 *
 *   За замовчуванням equals() використовує == (порівнює адреси).
 *   Щоб порівнювати за вмістом — потрібно перевизначити equals().
 * </pre>
 *
 * <h4>Контракт equals/hashCode:</h4>
 * <pre>
 *   ✔ Однакові об'єкти → завжди однаковий hashCode
 *   ✔ Різні об'єкти → можуть мати однаковий hashCode
 *   ✔ Різний hashCode → об'єкти точно різні
 * </pre>
 *
 * <p><b>Аналогія з життя:</b>
 * == це як порівнювати паспорти за серійним номером документа
 * (чи це один і той самий документ?).
 * equals() — як порівнювати людей за ПІБ та датою народження
 * (чи це одна і та сама людина?).</p>
 *
 * <p><b>Реальне застосування:</b>
 * Без правильного equals() неможливо знайти об'єкт у колекції,
 * перевірити дублікати, видалити елемент зі списку тощо.</p>
 */
public class Example06_ObjectComparison {

    public static void main(String[] args) {

        // === 1. Оператор == порівнює ПОСИЛАННЯ ===
        // Сценарій: реєстрація користувачів — чи це один і той самий об'єкт?

        System.out.println("=== Оператор == (порівняння посилань) ===");

        Person person1 = new Person(1, "Asdas", 25);
        Person person2 = new Person(1, "ggggg", 3);
        Person person3 = person1;  // person3 вказує на той самий об'єкт


        System.out.println( person1.equals(person2) );


        System.out.println("person1: " + person1);
        System.out.println("person2: " + person2);
        System.out.println("person3: " + person3);
        System.out.println();

        System.out.println("person1 == person2: " + (person1 == person2)); // false — різні об'єкти!
        System.out.println("person1 == person3: " + (person1 == person3)); // true — той самий об'єкт

        System.out.println();
        System.out.println("  Пам'ять:");
        System.out.println("  person1 ──▶ [Анна, 25] @obj1");
        System.out.println("  person2 ──▶ [Анна, 25] @obj2   ← інший об'єкт!");
        System.out.println("  person3 ──▶ [Анна, 25] @obj1   ← той самий, що й person1");

        System.out.println();

        // === 2. Метод equals() порівнює ВМІСТ ===
        // Сценарій: перевіряємо, чи це та сама людина (за полями)

        System.out.println("=== Метод equals() (порівняння вмісту) ===");

        System.out.println("person1.equals(person2): " + person1.equals(person2)); // true!
        System.out.println("person1.equals(person3): " + person1.equals(person3)); // true

        Person person4 = new Person(1,"Борис", 30);
        System.out.println("person1.equals(person4): " + person1.equals(person4)); // false

        System.out.println();

        // === 3. equals() за замовчуванням (клас Object) ===
        // Без перевизначення equals() працює як ==

        System.out.println("=== equals() за замовчуванням ===");
        System.out.println();
        System.out.println("  У класі Object метод equals() реалізований так:");
        System.out.println();
        System.out.println("  public boolean equals(Object obj) {");
        System.out.println("      return this == obj;  ← просто порівнює адреси!");
        System.out.println("  }");
        System.out.println();
        System.out.println("  Тому якщо НЕ перевизначити equals() у своєму класі,");
        System.out.println("  він буде порівнювати посилання, а не вміст!");

        System.out.println();

        // === 4. Метод hashCode() ===
        // hashCode повертає числовий «відбиток» об'єкта

        System.out.println("=== Метод hashCode() ===");

        System.out.println("person1.hashCode(): " + person1.hashCode());
        System.out.println("person2.hashCode(): " + person2.hashCode());
        System.out.println("person4.hashCode(): " + person4.hashCode());

        System.out.println();
        System.out.println("person1 і person2 рівні (equals) → hashCode однаковий: "
                + (person1.hashCode() == person2.hashCode()));
        System.out.println("person1 і person4 різні → hashCode різний: "
                + (person1.hashCode() != person4.hashCode()));

        System.out.println();

        // === 5. Контракт equals/hashCode ===

        System.out.println("=== Контракт equals / hashCode ===");
        System.out.println();
        System.out.println("  ┌──────────────────────────────────────────────┐");
        System.out.println("  │ Правила (контракт):                         │");
        System.out.println("  │                                              │");
        System.out.println("  │ 1. a.equals(b) == true                      │");
        System.out.println("  │    → a.hashCode() == b.hashCode()  ЗАВЖДИ   │");
        System.out.println("  │                                              │");
        System.out.println("  │ 2. a.hashCode() == b.hashCode()             │");
        System.out.println("  │    → a.equals(b) МОЖЕ бути true або false   │");
        System.out.println("  │                                              │");
        System.out.println("  │ 3. a.hashCode() != b.hashCode()             │");
        System.out.println("  │    → a.equals(b) == false  ЗАВЖДИ           │");
        System.out.println("  └──────────────────────────────────────────────┘");
        System.out.println();
        System.out.println("  Якщо перевизначив equals() → обов'язково перевизнач hashCode()!");

        System.out.println();

        // === 6. Порівняння рядків — типова помилка ===

        System.out.println("=== Порівняння String: == vs equals() ===");

        String s1 = new String("Привіт");
        String s2 = new String("Привіт");

        System.out.println("new String(\"Привіт\") == new String(\"Привіт\"): " + (s1 == s2));       // false!
        System.out.println("new String(\"Привіт\").equals(new String(\"Привіт\")): " + s1.equals(s2)); // true!
        System.out.println();
        System.out.println("Завжди порівнюйте рядки через .equals(), а не через ==!");
    }
}
