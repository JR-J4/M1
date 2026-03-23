package ua.com.javarush.jsquad.m1;

/**
 * Лекцiя 10: Отримання пiдрядкiв i трансформацiя тексту
 *
 * substring(), replace(), replaceAll(), repeat(), trim(), toLowerCase()
 * допомагають чистити i форматувати текст перед збереженням.
 *
 * Аналогiя: перед друком етикетки ми вирiзуємо зайве й замiнюємо
 * помилки на новi символи.
 *
 * Реальне застосування: маскування чутливих даних, пiдготовка звiтiв,
 * нормалiзацiя iмен у CRM.
 */
public class Example04_StringTransformation {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Маскування номера банківської картки
        // ============================================================
        System.out.println("=== substring() + repeat() ===");

        String cardNumber = "5375411122334455";

        String lastDigits = cardNumber.substring(cardNumber.length() - 4);

        String masked = "*".repeat(cardNumber.length() - 4) + lastDigits;

        System.out.println("Відображаємо лише останні цифри: " + masked);
        System.out.println();

        // ============================================================
        //   Приклад 2: Нормалізація імені з форми реєстрації
        // ============================================================
        System.out.println("=== trim(), toLowerCase(), toUpperCase() ===");

        String rawName = "   маРІя   гончАр   ";
        String cleaned = rawName.trim().replaceAll("\\s+", " ");

        String[] parts = cleaned.split(" ");
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].toLowerCase();
            parts[i] = Character.toUpperCase(part.charAt(0)) + part.substring(1);
        }
        String normalizedName = String.join(" ", parts);

        System.out.println("Було: [" + rawName + "]");
        System.out.println("Стало: [" + normalizedName + "]");
        System.out.println();

        // ============================================================
        //   Приклад 3: Оновлення шаблону договору
        // ============================================================
        System.out.println("=== replace(), replaceFirst(), replaceAll() ===");

        String contract = """
                Місто: <CITY>
                Дата: <DATE>
                Сторони: <NAME> та Компанія
                """;

        contract = contract.replace("<CITY>", "Львів")
                .replace("<DATE>", "15.03.2024")
                .replace("<NAME>", "ТОВ \"Ритм\"");

        contract = contract.replaceAll("Компанія", "ТОВ \"Диджитал\"");

        System.out.println(contract);
    }
}
