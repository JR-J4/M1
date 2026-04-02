package ua.com.javarush.jsquad.m1.example04_field_initialization;

/**
 * Клас для демонстрації значень за замовчуванням полів класу.
 *
 * Якщо поле не ініціалізоване явно, Java присвоює значення за замовчуванням.
 */
public class DefaultValues {

    int intValue;           // → 0
    long longValue;         // → 0
    double doubleValue;     // → 0.0
    float floatValue;       // → 0.0
    boolean booleanValue;   // → false
    char charValue;         // → '\0' (нульовий символ)
    String stringValue;     // → null
    int[] arrayValue;       // → null


    public DefaultValues() {
        System.out.println("  int:     " + intValue);
        System.out.println("  long:    " + longValue);
        System.out.println("  double:  " + doubleValue);
        System.out.println("  float:   " + floatValue);
        System.out.println("  boolean: " + booleanValue);
        System.out.println("  char:    '" + charValue + "' (код: " + (int) charValue + ")");
        System.out.println("  String:  " + stringValue);
        System.out.println("  int[]:   " + arrayValue);
    }

    public void showAll() {

    }

    public void showAll(String s) {

    }
}
