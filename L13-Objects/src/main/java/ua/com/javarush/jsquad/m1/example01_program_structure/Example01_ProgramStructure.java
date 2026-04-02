package ua.com.javarush.jsquad.m1.example01_program_structure;

/**
 * Лекція 13: Об'єкти
 * <hr>
 * <h3>Тема: Улаштування Java-програми — класи та об'єкти</h3>
 *
 * <p>Кожна програма на Java складається з класів та об'єктів.
 * Клас — це шаблон (креслення), а об'єкт — конкретний екземпляр,
 * створений на основі цього шаблону.</p>
 *
 * <p><b>Синтаксис:</b></p>
 * <pre>
 *   class НазваКласу {
 *       // поля (дані)
 *       // методи (дії)
 *   }
 *
 *   НазваКласу змінна = new НазваКласу();
 * </pre>
 *
 * <h4>Клас vs Об'єкт:</h4>
 * <pre>
 *    КЛАС (креслення)              ОБ'ЄКТИ (екземпляри)
 *  ┌─────────────────┐
 *  │   Messenger     │          ┌──────────────────┐
 *  │─────────────────│   new    │ msg1              │
 *  │ sender: String  │────────▶ │ sender = "Олена"  │
 *  │ text: String    │   │      │ text = "Привіт!"  │
 *  │─────────────────│   │      └──────────────────┘
 *  │ send()          │   │
 *  │ showInfo()      │   │      ┌──────────────────┐
 *  └─────────────────┘   │      │ msg2              │
 *                        └────▶ │ sender = "Тарас"  │
 *                               │ text = "Бувай!"   │
 *                               └──────────────────┘
 * </pre>
 *
 * <p><b>Аналогія з життя:</b>
 * Програміст — як проєктувальник. Він малює креслення (пише клас),
 * а потім на основі креслення створює конкретні деталі (об'єкти).
 * Як архітектор, що проєктує план будинку, а будівельники будують
 * конкретні будинки за цим планом.</p>
 *
 * <p><b>Реальне застосування:</b>
 * Абсолютно все в Java — об'єкти: String, Scanner, масиви.
 * Розуміння структури «клас → об'єкт» — фундамент Java-програмування.</p>
 */
public class Example01_ProgramStructure {

    public static void main(String[] args) {

        // === 1. Клас — це шаблон, об'єкт — конкретний екземпляр ===
        // Сценарій: месенджер — кожне повідомлення є об'єктом класу Message

        System.out.println("=== Клас — шаблон, об'єкт — екземпляр ===");

        Message msg1 = new Message();
        msg1.sender = "Олена";
        msg1.text = "Привіт! Як справи?";

        Message msg2 = new Message();
        msg2.sender = "Тарас";
        msg2.text = "Все добре, дякую!";

        msg1.showInfo();
        msg2.showInfo();

        System.out.println("Два об'єкти — один клас Message!");

        System.out.println();

        // === 2. У кожному класі є дані (поля) та методи ===
        // Сценарій: поля зберігають стан, методи виконують дії

        System.out.println("=== Поля (дані) та методи (дії) ===");
        System.out.println();
        System.out.println("  Клас Message:");
        System.out.println("  ┌──────────────────────────────┐");
        System.out.println("  │ ПОЛЯ (дані / стан):          │");
        System.out.println("  │   sender — хто відправив     │");
        System.out.println("  │   text   — текст повідомлення│");
        System.out.println("  │──────────────────────────────│");
        System.out.println("  │ МЕТОДИ (дії / поведінка):    │");
        System.out.println("  │   showInfo() — показати      │");
        System.out.println("  │   send()     — відправити    │");
        System.out.println("  └──────────────────────────────┘");
        System.out.println();

        msg1.send();
        msg2.send();

        System.out.println();

        // === 3. Кожен об'єкт має власний стан ===
        // Сценарій: зміна одного об'єкта не впливає на інший

        System.out.println("=== Об'єкти незалежні один від одного ===");

        msg1.text = "Змінений текст!";

        System.out.println("msg1 після зміни:");
        msg1.showInfo();

        System.out.println("msg2 не змінився:");
        msg2.showInfo();

        System.out.println();

        // === 4. Стандартні об'єкти Java ===
        // Навіть String, масив, StringBuilder — це все об'єкти класів

        System.out.println("=== Стандартні об'єкти Java ===");

        String name = new String("JavaRush");           // String — клас
        StringBuilder sb = new StringBuilder("Привіт"); // StringBuilder — клас
        int[] numbers = new int[3];                      // масив — теж об'єкт

        System.out.println("String: " + name);
        System.out.println("StringBuilder: " + sb);
        System.out.println("int[3]: довжина = " + numbers.length);
        System.out.println();
        System.out.println("Все в Java — це об'єкти класів!");
    }
}
