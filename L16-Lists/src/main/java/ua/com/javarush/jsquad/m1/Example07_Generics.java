package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;

/**
 * Лекція 16: Списки — Параметризовані типи (Generics)
 *
 * Дженерики — це можливість додавати до типів типи-параметри.
 * Утворюються складні складові типи вигляду:
 *   ОсновнийТип<ТипПараметр>
 *
 * Факти про дженерики:
 *   - У класів може бути декілька типів-параметрів:
 *     ОсновнийТип<Тип1, Тип2, Тип3>
 *   - Складні типи можна використовувати як параметри:
 *     ОсновнийТип<ТипПараметр<ТипПараметрПараметра>>
 *   - Масив з параметризованих типів:
 *     ОсновнийТип<Тип>[] array = new ОсновнийТип<Тип>[довжина];
 *
 * Стирання типів (Type Erasure): після компіляції інформація про типи-параметри
 * видаляється. ArrayList<String> стає просто ArrayList з приведенням типів.
 *
 * Аналогія з життя: дженерик — це як коробка з етикеткою. Коробка однакова,
 * але етикетка каже, що в ній: "Іграшки", "Книги", "Посуд".
 * Без етикетки (без дженерика) можна покласти що завгодно — і буде хаос.
 *
 * Реальне застосування: типобезпечні колекції, де компілятор перевіряє правильність
 * типів ще на етапі компіляції, а не під час виконання.
 */
public class Example07_Generics {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Навіщо потрібні дженерики?
        // ============================================================
        System.out.println("=== Навіщо потрібні дженерики? ===");

        // БЕЗ дженериків (raw type) — небезпечно
        ArrayList rawList = new ArrayList(); // без <Тип>
        rawList.add("Текст");
        rawList.add(123);
        rawList.add(true);
        System.out.println("Raw list: " + rawList);
        // При зчитуванні не знаємо, що там — можливий ClassCastException
        // String s = (String) rawList.get(1); // ClassCastException!

        // З дженериком — безпечно
        ArrayList<String> safeList = new ArrayList<>();
        safeList.add("Текст");
        // safeList.add(123);    // помилка компіляції!
        // safeList.add(true);   // помилка компіляції!

        String s = safeList.get(0); // не потрібне приведення типу
        System.out.println("Safe list: " + safeList);
        System.out.println("Елемент: " + s);
        System.out.println();

        // ============================================================
        //   Приклад 2: Дженерики на прикладі різних колекцій
        // ============================================================
        System.out.println("=== Дженерики з різними типами ===");

        // Уявіть: різні контейнери для різних продуктів
        ArrayList<Integer> ages = new ArrayList<>();
        ages.add(25);
        ages.add(30);
        ages.add(22);
        System.out.println("Вік студентів: " + ages);

        ArrayList<Double> weights = new ArrayList<>();
        weights.add(65.5);
        weights.add(72.3);
        weights.add(58.0);
        System.out.println("Вага (кг): " + weights);

        ArrayList<Character> letters = new ArrayList<>();
        letters.add('J');
        letters.add('A');
        letters.add('V');
        letters.add('A');
        System.out.println("Літери: " + letters);
        System.out.println();

        // ============================================================
        //   Приклад 3: Вкладені дженерики (список списків)
        // ============================================================
        System.out.println("=== Вкладені дженерики ===");

        // Уявіть: розклад занять — кожен день має список предметів
        ArrayList<ArrayList<String>> schedule = new ArrayList<>();

        ArrayList<String> monday = new ArrayList<>();
        monday.add("Математика");
        monday.add("Фізика");
        monday.add("Java");

        ArrayList<String> tuesday = new ArrayList<>();
        tuesday.add("Англійська");
        tuesday.add("Алгоритми");

        ArrayList<String> wednesday = new ArrayList<>();
        wednesday.add("Бази даних");
        wednesday.add("Java");
        wednesday.add("Тестування");

        schedule.add(monday);
        schedule.add(tuesday);
        schedule.add(wednesday);

        String[] days = {"Понеділок", "Вівторок", "Середа"};
        for (int i = 0; i < schedule.size(); i++) {
            System.out.println(days[i] + ": " + schedule.get(i));
        }
        System.out.println();

        // ============================================================
        //   Приклад 4: Масив ArrayList (масив з параметризованих типів)
        // ============================================================
        System.out.println("=== Масив ArrayList ===");

        // Уявіть: 3 класи в школі, у кожному — список учнів
        // Java не дозволяє створити new ArrayList<String>[3] напряму через стирання типів,
        // тому створюємо масив без параметра і отримуємо попередження компілятора.
        // Анотація @SuppressWarnings("unchecked") вимикає це попередження.
        @SuppressWarnings("unchecked")
        ArrayList<String>[] classes = new ArrayList[3];

        classes[0] = new ArrayList<>();
        classes[0].add("Анна");
        classes[0].add("Богдан");

        classes[1] = new ArrayList<>();
        classes[1].add("Вікторія");
        classes[1].add("Григорій");
        classes[1].add("Дарина");

        classes[2] = new ArrayList<>();
        classes[2].add("Євген");

        for (int i = 0; i < classes.length; i++) {
            System.out.println("Клас " + (i + 1) + ": " + classes[i]);
        }
        System.out.println();

        // ============================================================
        //   Приклад 5: Стирання типів (Type Erasure)
        // ============================================================
        System.out.println("=== Стирання типів ===");

        // Після компіляції дженерики "стираються"
        ArrayList<String> stringList = new ArrayList<>();
        ArrayList<Integer> intList = new ArrayList<>();

        // Під час виконання обидва — просто ArrayList
        System.out.println("stringList.getClass(): " + stringList.getClass().getName());
        System.out.println("intList.getClass():    " + intList.getClass().getName());
        System.out.println("Однаковий клас? " + (stringList.getClass() == intList.getClass())); // true

        System.out.println("Дженерики існують тільки на етапі компіляції!");
        System.out.println("Після компіляції тип-параметр стирається — це називають Type Erasure.");
    }
}
