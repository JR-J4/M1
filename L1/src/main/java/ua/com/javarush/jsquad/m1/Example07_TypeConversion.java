package ua.com.javarush.jsquad.m1;

/**
 * Лекція 1-2: Перетворення типів (String <-> int)
 *
 * Іноді потрібно перетворити текст на число і навпаки:
 *
 * String -> int:  Integer.parseInt("123")      -> 123
 *                 Як прочитати номер будинку з таблички: "25" стає числом 25
 *
 * int -> String:  String.valueOf(123)           -> "123"
 *                 Як написати число на папірці: 25 стає текстом "25"
 *
 * Також: якщо додати число до рядка через +, число автоматично стане текстом.
 */
public class Example07_TypeConversion {

    public static void main(String[] args) {


        // === String -> int (текст у число) ===
        // Використовуємо Integer.parseInt()
        System.out.println("=== String -> int ===");

        String ageText = "25";
        int age = Integer.parseInt(ageText);  // "25" -> 25
        System.out.println("Текст: " + ageText);
        System.out.println("Число: " + age);
        System.out.println("Число + 1 = " + (age + 1));  // 26 (рахує як числа!)

        // Можна одразу з рядка
        int year = Integer.parseInt("2025");
        System.out.println("Рік: " + year);

        System.out.println();

        // === int -> String (число у текст) ===
        // Використовуємо String.valueOf()
        System.out.println("=== int -> String ===");

        int number = 42;
        String numberText = String.valueOf(number);  // 42 -> "42"
        System.out.println("Число як текст: " + numberText);

        // Або простіший спосіб - додати до порожнього рядка
        String easy = "" + 42;  // теж стане "42"
        System.out.println("Простий спосіб: " + easy);

        System.out.println();

        // === Увага! String це НЕ число, навіть якщо виглядає як число ===
        System.out.println("=== Різниця між числом і текстом ===");
        int num1 = 10;
        int num2 = 20;
        System.out.println("int + int:       " + (num1 + num2));    // 30 (додавання)

        String str1 = "10";
        String str2 = "20";
        System.out.println("String + String: " + (str1 + str2));    // "1020" (склеювання!)

        // Щоб порахувати текстові числа - спочатку перетворюємо
        int result = Integer.parseInt(str1) + Integer.parseInt(str2);
        System.out.println("parseInt + parseInt: " + result);        // 30

        System.out.println();

        // === Автоматичне перетворення числа в текст при конкатенації ===
        System.out.println("=== Автоперетворення при + з рядком ===");
        int price = 100;
        // Число автоматично стає текстом коли "зустрічає" String через +
        String priceTag = "Ціна: " + price + " грн";
        System.out.println(priceTag);  // Ціна: 100 грн
    }
}
