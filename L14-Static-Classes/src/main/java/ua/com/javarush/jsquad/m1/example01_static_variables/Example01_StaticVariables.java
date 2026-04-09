package ua.com.javarush.jsquad.m1.example01_static_variables;

/**
 * Лекція 14: Класи та static
 * <hr>
 * <h3>Тема: Статичні змінні</h3>
 *
 * <p>Коли клас завантажується у пам'ять, для нього відразу створюється
 * <b>статичний об'єкт класу</b>. Цей об'єкт зберігає статичні змінні
 * (статичні поля класу).</p>
 *
 * <p><b>Синтаксис:</b></p>
 * <pre>
 *   static Тип ім'я = значення;
 * </pre>
 *
 * <p>Статичний об'єкт класу існує, навіть якщо не було створено жодного
 * звичайного об'єкта класу.</p>
 *
 * <p><b>Доступ:</b></p>
 * <pre>
 *   Усередині свого класу:  просто за іменем (userCount)
 *   З іншого класу:         Ім'яКласу.змінна (User.userCount)
 * </pre>
 *
 * <p><b>Аналогія з життя:</b>
 * Статична змінна — як лічильник відвідувачів на вході у торговий центр.
 * Він один для всіх, а не у кожного відвідувача свій лічильник.
 * Кожен новий відвідувач збільшує спільне число.</p>
 *
 * <p><b>Реальне застосування:</b>
 * Лічильники створених об'єктів, глобальні налаштування, константи,
 * спільні ресурси (наприклад, пул з'єднань).</p>
 */
public class Example01_StaticVariables {

    public static void main(String[] args) {

        // === 1. Оголошення та використання статичної змінної ===
        // Сценарій: соціальна мережа — рахуємо зареєстрованих користувачів

        System.out.println("=== Оголошення статичної змінної ===");
        System.out.println("Користувачів до створення об'єктів: " + User.userCount);
        // userCount існує ще ДО створення будь-якого об'єкта User!

        System.out.println();

        // === 2. Спільний стан між об'єктами ===
        // Кожен новий User збільшує ОДИН спільний лічильник

        System.out.println("=== Спільний стан між об'єктами ===");

        User user1 = new User("coder_ua", "coder@gmail.com");
        System.out.println("Після створення user1: userCount = " + User.userCount); // 1

        User user2 = new User("java_fan", "fan@gmail.com");
        System.out.println("Після створення user2: userCount = " + User.userCount); // 2

        User user3 = new User("dev_master", "dev@gmail.com");
        System.out.println("Після створення user3: userCount = " + User.userCount); // 3

        System.out.println();

        // === 3. Доступ зсередини та ззовні класу ===
        // Зсередини класу — просто за іменем
        // Ззовні класу — через Ім'яКласу.змінна

        System.out.println("=== Доступ до статичної змінної ===");

        // Ззовні класу — через ім'я класу
        System.out.println("User.userCount = " + User.userCount);

        // Технічно можна і через об'єкт, але це поганий стиль!
        // IDE покаже попередження:
        // System.out.println(user1.userCount); // працює, але не рекомендується

        System.out.println();

        // Виведемо всіх користувачів
        System.out.println("=== Зареєстровані користувачі ===");
        user1.showInfo();
        user2.showInfo();
        user3.showInfo();
        System.out.println("Загалом у мережі: " + User.userCount + " користувачі(ів)");
    }
}
