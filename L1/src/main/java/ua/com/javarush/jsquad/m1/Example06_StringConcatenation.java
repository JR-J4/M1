package ua.com.javarush.jsquad.m1;

/**
 * Лекція 1-2: Конкатенація (склеювання рядків)
 *
 * Конкатенація - це "склеювання" рядків за допомогою знака +
 * Як скласти пазл зі слів: "Привіт" + " " + "світе" = "Привіт світе"
 *
 * Важливо: якщо хоча б одна сторона + це String,
 * результат ЗАВЖДИ буде String (текст), навіть якщо друга сторона - число!
 */
public class Example06_StringConcatenation {

    public static void main(String[] args) {

        // === Склеювання рядків ===
        System.out.println("=== Склеювання рядків ===");
        String firstName = "Тарас";
        String lastName = "Шевченко";

        // Catena -> ланцюг ; Con - поєднувати
        //  H E L L O + asdasd

        // Без пробілу - слова "злипнуться"
        String fullNameBad = firstName + lastName;
        System.out.println("Без пробілу: " + fullNameBad);  // ТарасШевченко

        // З пробілом - додаємо " " між словами
        String fullName = firstName + " " + lastName;
        System.out.println("З пробілом: " + fullName);      // Тарас Шевченко

        System.out.println();

        // === Число + рядок = рядок (число перетворюється на текст!) ===
        System.out.println("=== Число + рядок = рядок ===");
        int age = 25;
        String message = "Мені " + age + " років";
        System.out.println(message);  // Мені 25 років

        // Увага! Порядок має значення:
        int a = 5;
        System.out.println("Число: 5" + a + a);    // "Число: 55"  (обидва a стали текстом!)
        System.out.println("Сума: " + (a + a));    // "Сума: 10"  (дужки спочатку рахують!)
        System.out.println(a + a + " - сума");     // "10 - сума" (спочатку додались числа)

        System.out.println();

        // === Реальний приклад: формуємо повідомлення ===
        System.out.println("=== Повідомлення для користувача ===");
        String userName = "Аня";
        int score = 95;
        String subject = "Java";

        String report = "Студент " + userName + " отримав " + score + " балів з " + subject;
        System.out.println(report);
        // Студент Аня отримав 95 балів з Java

        // === Ще приклад: адреса ===
        String street = "вул. Хрещатик";
        int houseNumber = 1;
        String apartment = "кв. 42";

        String address = street + ", " + houseNumber + ", " + apartment;
        System.out.println("Адреса: " + address);
        // Адреса: вул. Хрещатик, 1, кв. 42
    }
}
