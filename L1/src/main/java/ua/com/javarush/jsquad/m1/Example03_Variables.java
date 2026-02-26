package ua.com.javarush.jsquad.m1;

/**
 * Лекція 1-2: Змінні в Java
 *
 * Змінна - це як коробка з наклейкою (іменем), в яку можна покласти щось одного типу.
 *
 * Кожна змінна має 3 речі:
 *   1. Тип  - ЩО можна покласти (числа? текст?)
 *   2. Ім'я - назва коробки (щоб знайти її серед інших)
 *   3. Значення - те, що лежить всередині
 *
 * Основні типи на сьогодні:
 *   int    - цілі числа (1, 42, -7, 0)       як кількість яблук: 5 штук
 *   String - текст ("Привіт", "Java")         як ім'я людини: "Олександр"
 */
public class Example03_Variables {

    public static void main(String[] args) {

        int a = 5;
        int b = a + 1;


        System.out.println(a + " - " + b + " = " + (a - b));


        // === Створення змінних типу int (цілі числа) ===
        // Формат: тип ім'я = значення;
        int age = 17;                // вік студента
        int studentsInGroup = 25;    // кількість студентів у групі
        int temperature = -5;        // температура на вулиці (може бути від'ємною!)

        System.out.println("=== Змінні int (цілі числа) ===");
        System.out.println("Вік: " + age);
        System.out.println("Студентів у групі: " + studentsInGroup);
        System.out.println("Температура: " + temperature);

        System.out.println();




        // === Створення змінних типу String (текст) ===
        // Текст завжди в подвійних лапках ""
        String name = "Олександр";       // ім'я
        String city = "Київ";            // місто
        String university = "КПІ";      // університет

        System.out.println("=== Змінні String (текст) ===");
        System.out.println("Ім'я: " + name);
        System.out.println("Місто: " + city);
        System.out.println("Університет: " + university);

        System.out.println();

        // === Змінна може змінювати своє значення ===
        // Тому вона і називається "змінна"!
        int score = 0;
        System.out.println("=== Змінна може змінюватись ===");
        System.out.println("Рахунок на початку: " + score);

        score = 10;  // тип вже НЕ пишемо - змінна вже існує
        System.out.println("Після першого голу: " + score);

        score = 25;
        System.out.println("Після другого голу: " + score);

        System.out.println();

        // === Можна створити змінну без значення, а присвоїти пізніше ===
        int bonus;         // створили коробку, але поки порожня
        bonus = 100;       // поклали значення
        System.out.println("Бонус: " + bonus);

        // === Кілька змінних одного типу через кому ===
        int x = 1, y = 2, z = 3;
        System.out.println("x=" + x + ", y=" + y + ", z=" + z);



    }
}
