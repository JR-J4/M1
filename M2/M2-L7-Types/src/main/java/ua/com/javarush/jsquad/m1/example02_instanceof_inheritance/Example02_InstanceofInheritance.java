package ua.com.javarush.jsquad.m1.example02_instanceof_inheritance;

/**
 * Модуль 2. Рівень 7. Приведення типів
 * <hr>
 * <h3>Тема: {@code instanceof} враховує успадкування та інтерфейси</h3>
 *
 * <p>Вираз {@code a instanceof B} поверне {@code true}, якщо:</p>
 * <ol>
 *   <li>змінна {@code a} зберігає посилання на об'єкт типу {@code B};</li>
 *   <li>{@code a} зберігає об'єкт, клас якого <b>успадкований</b> від {@code B};</li>
 *   <li>{@code a} зберігає об'єкт, який <b>реалізує інтерфейс</b> {@code B}.</li>
 * </ol>
 * <p>У всіх інших випадках — {@code false}.</p>
 *
 * <p><b>Аналогія з життя:</b> качка — це водночас "качка", "тварина" і "той, хто вміє
 * плавати". Якщо запитати "качка — тварина?" — так. "Качка вміє плавати?" — так.
 * А от "собака вміє плавати (за контрактом Swimmer)?" — у нашій моделі ні.</p>
 *
 * <p><b>Реальне застосування:</b> зібрати з різнорідного списку лише ті об'єкти,
 * що вміють щось конкретне (реалізують потрібний інтерфейс).</p>
 */
public class Example02_InstanceofInheritance {

    public static void main(String[] args) {

        Dog dog = new Dog("Рекс");
        Duck duck = new Duck("Кряк");

        // === 1. Об'єкт може підходити одразу під кілька типів ===
        System.out.println("=== Качка: скільки типів одразу? ===");
        System.out.println("duck instanceof Duck    -> " + (duck instanceof Duck));    // true: свій клас
        System.out.println("duck instanceof Animal  -> " + (duck instanceof Animal));  // true: батьківський клас
        System.out.println("duck instanceof Swimmer -> " + (duck instanceof Swimmer)); // true: інтерфейс
        System.out.println();

        // === 2. Собака — тварина, але Swimmer не реалізує ===
        System.out.println("=== Собака ===");
        System.out.println("dog instanceof Animal   -> " + (dog instanceof Animal));   // true
        System.out.println("dog instanceof Swimmer  -> " + (dog instanceof Swimmer));  // false
        System.out.println();

        // === 3. Практика: відправимо у воду лише тих, хто вміє плавати ===
        System.out.println("=== Відправляємо плавати лише Swimmer ===");
        Animal[] animals = {dog, duck, new Duck("Дональд"), new Dog("Бобік")};

        for (Animal animal : animals) {
            animal.move();
        }

        AnimalMoveVisitor animalMoveVisitor = new AnimalMoveVisitor();

        for (Animal animal : animals) {
            animal.accept(animalMoveVisitor);
        }


        System.out.println();

        System.out.println("Головне: instanceof бачить увесь ланцюжок типів — клас, предків та інтерфейси.");
    }
}
