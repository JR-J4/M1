package ua.com.javarush.jsquad.m1.example03_multiple_constructors;

/**
 * Лекція 13: Об'єкти
 * <hr>
 * <h3>Тема: Декілька конструкторів. Конструктор за замовчуванням</h3>
 *
 * <p>Клас може мати кілька конструкторів з різними наборами параметрів.
 * Компілятор сам обирає потрібний конструктор за типом і кількістю
 * переданих аргументів.</p>
 *
 * <h4>Декілька конструкторів:</h4>
 * <pre>
 *   class Student {
 *       Student(String name, int age, String group, double gpa) { ... }  ← повний
 *       Student(String name, String group)                      { ... }  ← базовий
 *       Student()                                               { ... }  ← за замовчуванням
 *   }
 *
 *   new Student("Анна", 20, "JR-101", 4.5)  → конструктор 1
 *   new Student("Борис", "JR-102")           → конструктор 2
 *   new Student()                            → конструктор 3
 * </pre>
 *
 * <h4>Конструктор за замовчуванням:</h4>
 * <pre>
 *   Якщо немає жодного конструктора → компілятор додасть порожній public:
 *       public Student() { }
 *
 *   Якщо є хоча б один конструктор → конструктор за замовчуванням
 *   НЕ додається автоматично!
 * </pre>
 *
 * <p><b>Аналогія з життя:</b>
 * Реєстрація студента в університеті. Можна заповнити повну анкету
 * (ім'я, вік, група, бал), або скорочену (тільки ім'я та група),
 * або взагалі залишити пусту форму для заповнення пізніше.</p>
 *
 * <p><b>Реальне застосування:</b>
 * Декілька конструкторів дають гнучкість: можна створити об'єкт
 * з мінімальними даними або з повним набором інформації.</p>
 */
public class Example03_MultipleConstructors {

    public static void main(String[] args) {

        // === 1. Повний конструктор — всі параметри ===
        // Сценарій: реєстрація студента з повною анкетою

        System.out.println("=== 1. Повний конструктор ===");
        Student anna = new Student("Анна Коваленко", 20, "JR-101", 4.5);
        anna.showInfo();

        System.out.println();

        // === 2. Базовий конструктор — мінімум даних ===
        // Сценарій: новий студент, бал ще не визначений

        System.out.println("=== 2. Базовий конструктор ===");
        Student boris = new Student("Борис Мельник", "JR-102");
        boris.showInfo();  // вік = 18, бал = 0.0 (значення за замовчуванням)

        System.out.println();

        // === 3. Конструктор за замовчуванням — без параметрів ===
        // Сценарій: порожня анкета, дані будуть заповнені пізніше

        System.out.println("=== 3. Конструктор за замовчуванням ===");
        Student unknown = new Student();
        unknown.showInfo();

        System.out.println();

        // === 4. Компілятор сам обирає конструктор ===

        System.out.println("=== Як компілятор обирає конструктор ===");
        System.out.println();
        System.out.println("  new Student(\"Анна\", 20, \"JR-101\", 4.5)");
        System.out.println("       │");
        System.out.println("       ▼  4 аргументи: String, int, String, double");
        System.out.println("  Student(String, int, String, double) ← ОБРАНО!");
        System.out.println();
        System.out.println("  new Student(\"Борис\", \"JR-102\")");
        System.out.println("       │");
        System.out.println("       ▼  2 аргументи: String, String");
        System.out.println("  Student(String, String) ← ОБРАНО!");
        System.out.println();
        System.out.println("  new Student()");
        System.out.println("       │");
        System.out.println("       ▼  0 аргументів");
        System.out.println("  Student() ← ОБРАНО!");

        System.out.println();

        // === 5. Важливо: конструктор за замовчуванням і автоматичне додавання ===

        System.out.println("=== Конструктор за замовчуванням — важливий нюанс ===");
        System.out.println();
        System.out.println("  Якщо у класі НЕМАЄ жодного конструктора:");
        System.out.println("  → компілятор додає порожній public конструктор автоматично");
        System.out.println();
        System.out.println("  Якщо у класі Є хоча б ОДИН конструктор:");
        System.out.println("  → конструктор за замовчуванням НЕ додається!");
        System.out.println("  → якщо потрібен — треба написати самостійно");
        System.out.println();
        System.out.println("  У нашому класі Student є 3 конструктори,");
        System.out.println("  включаючи Student() — ми написали його самі.");
    }
}
