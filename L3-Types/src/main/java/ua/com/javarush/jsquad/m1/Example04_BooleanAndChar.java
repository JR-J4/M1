package ua.com.javarush.jsquad.m1;

/**
 * Лекція 3: Типи boolean та char
 *
 * boolean — логічний тип. Зберігає тільки 2 значення: true (так) або false (ні).
 *           Як вимикач: увімкнено/вимкнено. Займає 1 біт. За замовчуванням: false.
 *
 * char    — символьний тип. Зберігає ОДИН символ Unicode.
 *           Займає 2 байти (16 біт). Діапазон: 0..65535.
 *           Символ записується в одинарних лапках: 'A', 'Б', '5', '!'
 */
public class Example04_BooleanAndChar {

    public static void main(String[] args) {

        // ============================================================
        //                       BOOLEAN
        // ============================================================
        System.out.println("=== boolean (true / false) ===");

        boolean isJavaFun = true;
        boolean isWeekend = false;
        boolean isAdult = true;

        System.out.println("Java цікава? " + isJavaFun);      // true
        System.out.println("Зараз вихідні? " + isWeekend);    // false
        System.out.println("Повнолітній? " + isAdult);        // true
        System.out.println();

        // boolean часто отримують з порівнянь
        int age = 17;
        boolean canVote = age >= 18;  // 17 >= 18? Ні! -> false
        System.out.println("Вік: " + age + ", може голосувати: " + canVote);

        int temperature = 35;
        boolean isHot = temperature > 30;  // 35 > 30? Так! -> true
        System.out.println("Температура: " + temperature + ", спекотно: " + isHot);

        int a = 5;
        int b = 5;
        boolean isEqual = a == b;  // 5 == 5? Так! -> true
        System.out.println(a + " == " + b + " -> " + isEqual);
        System.out.println();

        // ============================================================
        //                         CHAR
        // ============================================================
        System.out.println("=== char (один символ) ===");

        // char записується в ОДИНАРНИХ лапках '' (String — у подвійних "")
        char letterA = 'A';
        char letterYa = 'Я';
        char digitChar = '7';     // це символ, не число!
        char exclamation = '!';
        char space = ' ';         // пробіл — теж символ

        System.out.println("Латинська: " + letterA);
        System.out.println("Українська: " + letterYa);
        System.out.println("Цифра-символ: " + digitChar);
        System.out.println("Знак оклику: " + exclamation);
        System.out.println();

        // char — це насправді число (код символу в Unicode)
        System.out.println("=== char це число (код Unicode) ===");
        char letterB = 'B';
        int codeB = letterB;  // автоматично перетворюється на число
        System.out.println("Символ: " + letterB + ", код: " + codeB);  // 66

        // І навпаки: число можна перетворити на символ
        char fromCode = 65;  // код 65 = 'A'
        System.out.println("Код 65 = символ: " + fromCode);  // A

        // Можна робити арифметику з char!
        char start = 'A';
        char next = (char) (start + 1);  // A + 1 = B
        System.out.println("'A' + 1 = '" + next + "'");  // B
        System.out.println();

        // === Практичний приклад: перші літери імені ===
        System.out.println("=== Ініціали ===");
        char firstNameInitial = 'О';  // Олександр
        char lastNameInitial = 'Ш';   // Шевченко

        System.out.println("Ініціали: " + firstNameInitial + "." + lastNameInitial + ".");
    }
}
