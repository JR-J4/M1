package ua.com.javarush.jsquad.m1;

/**
 * Лекція 21: Винятки — Множинне перехоплення (multi-catch, Java 7+)
 *
 * Починаючи з Java 7, можна перехоплювати кілька типів винятків
 * в одному блоці catch через оператор | (pipe).
 *
 * Синтаксис:
 *   catch (ТипВинятку1 | ТипВинятку2 | ТипВинятку3 імʼя) {
 *       // спільний код обробки
 *   }
 *
 * Обмеження:
 *   - Типи в multi-catch не можуть бути повʼязані наслідуванням
 *     (не можна писати Exception | RuntimeException — RuntimeException вже входить у Exception)
 *   - Змінна e є effectively final — не можна їй присвоїти інше значення
 *
 * Аналогія з життя: одна мережа для ловлі різних риб. Замість 3 окремих мереж
 * (для окунів, для щук, для карасів) — одна універсальна. Якщо обробка
 * однакова — навіщо дублювати код?
 *
 * Реальне застосування: єдине логування для кількох типів помилок,
 * спільна обробка різних помилок парсингу, уніфіковане повідомлення користувачу.
 */
public class Example07_MultiCatch {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: До Java 7 — окремий catch для кожного типу
        // ============================================================
        System.out.println("=== Блок 1: Окремі catch (до Java 7) ===");

        // Сценарій: парсинг даних — кілька різних помилок з однаковою обробкою
        String input = "abc";

        try {
            int number = Integer.parseInt(input);
            int result = 100 / number;
            String text = null;
            text.length();
        }
        catch (NumberFormatException e) {
            System.out.println("Помилка обробки: " + e.getClass().getSimpleName()
                    + " — " + e.getMessage());
        }
        catch (ArithmeticException e) {
            // Дублювання коду! Та сама логіка обробки
            System.out.println("Помилка обробки: " + e.getClass().getSimpleName()
                    + " — " + e.getMessage());
        }
        catch (NullPointerException e) {
            // І знову дублювання!
            System.out.println("Помилка обробки: " + e.getClass().getSimpleName()
                    + " — " + e.getMessage());
        }

        System.out.println("3 блоки catch з ОДНАКОВИМ кодом — дублювання!");
        System.out.println();

        // ============================================================
        //   Блок 2: Multi-catch (Java 7+) — один catch для кількох типів
        // ============================================================
        System.out.println("=== Блок 2: Multi-catch (Java 7+) ===");

        // Та сама логіка, але без дублювання
        String[] testInputs = {"abc", "0", null, "42"};

        for (String val : testInputs) {
            try {
                int number = Integer.parseInt(val);
                int result = 100 / number;
                System.out.println("  '" + val + "' → результат: " + result);
            }
            catch (NumberFormatException | ArithmeticException | NullPointerException e) {
                // Один блок для трьох типів винятків!
                System.out.println("  '" + val + "' → " + e.getClass().getSimpleName()
                        + ": " + e.getMessage());
            }
            catch (Exception e) {
                System.out.println("Unknown exception");
            }
        }

        System.out.println("Один catch замість трьох — код чистіший!");
        System.out.println();

        // ============================================================
        //   Блок 3: Комбінація multi-catch і окремих catch
        // ============================================================
        System.out.println("=== Блок 3: Multi-catch + окремий catch ===");

        // Деякі помилки обробляємо однаково, а одну — особливо
        String[] data = {"100", "hello", "0", null};

        for (String d : data) {
            try {
                processData(d);
                System.out.println("  '" + d + "' → Оброблено успішно");
            }
            catch (NullPointerException e) {
                // Цей виняток обробляємо ОКРЕМО — він серйозніший
                System.out.println("  '" + d + "' → КРИТИЧНО! Null-значення! Потрібна увага!");
            }
            catch (NumberFormatException | ArithmeticException e) {
                // Ці два — спільна обробка через multi-catch
                System.out.println("  '" + d + "' → Помилка даних: " + e.getMessage());
            }
        }

        System.out.println();

        // ============================================================
        //   Блок 4: Обмеження multi-catch
        // ============================================================
        System.out.println("=== Блок 4: Обмеження multi-catch ===");

        System.out.println("1. Не можна поєднувати батька і нащадка:");
        System.out.println("   catch (Exception | RuntimeException e) — ПОМИЛКА компіляції!");
        System.out.println("   RuntimeException вже входить у Exception.");
        System.out.println();
        System.out.println("2. Змінна e — effectively final:");
        System.out.println("   Не можна присвоїти: e = new Exception(); — ПОМИЛКА!");
        System.out.println();
        System.out.println("3. Multi-catch можна комбінувати з окремими catch:");
        System.out.println("   Порядок: спочатку конкретні/multi-catch, потім загальні.");
    }

    /**
     * Обробка даних — може кинути різні винятки.
     */
    static void processData(String input) {
        int number = Integer.parseInt(input); // NumberFormatException або NullPointerException
        int result = 100 / number;            // ArithmeticException
    }
}
