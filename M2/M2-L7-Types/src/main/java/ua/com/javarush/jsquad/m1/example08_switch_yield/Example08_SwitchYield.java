package ua.com.javarush.jsquad.m1.example08_switch_yield;

/**
 * Модуль 2. Рівень 7. Приведення типів
 * <hr>
 * <h3>Тема: Ключове слово {@code yield} — коли в гілці switch кілька рядків коду</h3>
 *
 * <p>Стрілка {@code ->} зручна, коли гілка — це одне значення. А якщо потрібно не лише
 * повернути результат, а й виконати кілька рядків коду, тіло гілки беруть у фігурні
 * дужки, і результат повертають ключовим словом {@code yield}:</p>
 *
 * <pre>
 *   String type = switch (product) {
 *       case "Apple", "Peach" -> {
 *           System.out.println("This is fruit");   // кілька дій
 *           yield "Fruit";                          // повертаємо результат усього switch
 *       }
 *       default -> "other";
 *   };
 * </pre>
 *
 * <p><b>{@code yield} проти {@code break}:</b> {@code break} лише виходив із гілки
 * {@code case} (нічого не повертав), а {@code yield} повертає результат із <b>усього</b>
 * switch — тобто виступає в ролі внутрішнього {@code return}. {@code yield} з'явився
 * в Java 13 і замінив спробу використати {@code break} зі значенням.</p>
 *
 * <p><b>Аналогія з життя:</b> касир не просто називає ціну — він ще й пробиває чек,
 * пакує товар (кілька дій), а вже потім віддає решту (yield — фінальний результат).</p>
 */
public class Example08_SwitchYield {

    public static void main(String[] args) {

        // === 1. Блок { } + yield: кілька дій і повернення результату ===
        System.out.println("=== Гілка з кількох рядків через yield ===");
        String product = "Apple";
        String productType = switch (product) {
            case "Apple", "Peach" -> {
                System.out.println("  (лог) обробляємо фрукт: " + product);
                yield "Фрукт";                     // результат усього switch // RETURN
            }
            case "Raspberry" -> {
                System.out.println("  (лог) обробляємо ягоду: " + product);
                yield "Ягода";
            }
            default -> "інше";
        };
        System.out.println("  результат: " + product + " -> " + productType);
        System.out.println();

        // === 2. yield із проміжними обчисленнями ===
        System.out.println("=== Вартість доставки з проміжним розрахунком ===");
        String city = "Київ";
        int weightKg = 5;
        int deliveryPrice = switch (city) {
            case "Київ" -> {
                int base = 50;                     // проміжні обчислення
                int perKg = 10;
                yield base + perKg * weightKg;     // 50 + 10*5 = 100
            }
            case "Львів", "Одеса" -> {
                int base = 70;
                yield base + 12 * weightKg;
            }
            default -> 150;                        // фіксована ціна для інших міст
        };
        System.out.println("  " + city + ", " + weightKg + " кг -> " + deliveryPrice + " грн");
        System.out.println();

        // === 3. Можна поєднувати: одні гілки зі стрілкою, інші — з yield ===
        System.out.println("=== Оцінка за балом (стрілка + блок yield) ===");
        System.out.println("  95 -> " + grade(95));
        System.out.println("  82 -> " + grade(82));
        System.out.println("  40 -> " + grade(40));
        System.out.println();

        System.out.println("Головне: у багаторядковій гілці результат повертає yield (внутрішній return switch).");
    }

    private static String grade(int score) {
        return "Result: " + switch (score / 10) {
            case 10, 9 -> "Відмінно";
            case 8, 7 -> "Добре";
            case 6, 5 -> "Задовільно";
            default -> {
                String note = "Незадовільно";      // кілька рядків -> потрібен блок і yield
                yield note + " (потрібно перескласти)";
            }
        };
    }
}
