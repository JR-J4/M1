package ua.com.javarush.jsquad.m1.example08_type_casting;

/**
 * Модуль 2. Рівень 1. ООП: інкапсуляція, поліморфізм
 * <hr>
 * <h3>Тема: Поліморфізм — приведення типів (розширення та звуження)</h3>
 *
 * <p>При наслідуванні клас отримує всі методи та дані батьківського класу, тому об'єкт
 * цього класу дозволяється зберігати у змінній батьківського класу (і "батька" цього
 * "батька", аж до {@code Object}).</p>
 *
 * <h4>Розширення типу (upcasting) — автоматично:</h4>
 * <pre>
 *   Animal a = new Dog("Рекс");   // Спадкоємця "розширили" до Предка
 *   // через змінну a можна викликати ТІЛЬКИ методи, описані в Animal!
 * </pre>
 *
 * <h4>Звуження типу (downcasting) — явно, з перевіркою:</h4>
 * <pre>
 *   if (a instanceof Dog) {       // спершу перевіряємо!
 *       Dog d = (Dog) a;          // потім звужуємо
 *       d.bark();
 *   }
 * </pre>
 *
 * <p><b>Увага:</b> звуження можна зробити й без перевірки. Але якщо у змінній лежав
 * об'єкт не того класу — буде виняток {@code ClassCastException} (у лекції він названий
 * "InvalidClassCastException"; точна назва в Java — <b>{@code ClassCastException}</b>).</p>
 *
 * <p><b>Аналогія з життя:</b> у документах ви — "людина" (широкий тип). Але щоб
 * скористатись правами водія, треба підтвердити (перевірити!), що ви саме водій,
 * і лише тоді "звузити" роль до водія.</p>
 */
public class Example08_TypeCasting {

    public static void main(String[] args) {

        // === 1. РОЗШИРЕННЯ (upcasting) — автоматично, без зусиль ===
        System.out.println("=== Розширення типу: Dog → Animal ===");
        Animal animal = new Dog("Рекс"); // об'єкт Dog у змінній типу Animal
        animal.eat(); // ✔ метод Animal доступний
        // animal.bark(); // ✖ ПОМИЛКА: тип змінної Animal не знає про bark()!
        System.out.println("Через Animal видно лише методи Animal (bark() недоступний).");
        System.out.println();

        // === 2. ЗВУЖЕННЯ (downcasting) з перевіркою instanceof ===
        System.out.println("=== Звуження типу з перевіркою ===");
        if (animal instanceof Dog) {       // перевіряємо, що це справді Dog
            Dog dog = (Dog) animal;        // безпечно звужуємо
            dog.bark();                    // тепер bark() доступний!
        }
        System.out.println();

        // === 3. instanceof з автоматичним приведенням (Java 16+) ===
        System.out.println("=== Скорочений синтаксис instanceof ===");
        if (animal instanceof Dog dog) {   // перевірка + звуження в одному рядку
            dog.bark();
        }
        System.out.println();

        // === 4. ClassCastException — звуження без перевірки до НЕ того типу ===
        System.out.println("=== Небезпечне звуження без перевірки ===");
        Animal someAnimal = new Cat("Мурка"); // насправді це Cat!
        try {
            Dog wrong = (Dog) someAnimal;  // намагаємось видати кота за собаку
            wrong.bark();
        } catch (ClassCastException e) {
            System.out.println("  ✖ Спіймано ClassCastException!");
            System.out.println("  Кота не можна привести до Dog: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Правило: перед звуженням завжди перевіряй тип через instanceof.");
    }
}
