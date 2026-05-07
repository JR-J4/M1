package ua.com.javarush.jsquad.m1;

/**
 * Лекція 21: Винятки — Декілька блоків catch
 *
 * Після одного try можна написати кілька блоків catch для різних типів винятків.
 * Кожен тип обробляється по-своєму. Java перевіряє блоки catch зверху вниз
 * і виконує ТІЛЬКИ перший підходящий.
 *
 * Важливо: порядок catch має значення!
 * - Спочатку конкретніші винятки (нащадки)
 * - Потім загальніші (батьківські)
 * - Якщо поставити Exception першим — решта catch стануть недосяжними (помилка компіляції)
 *
 * Аналогія з життя: система фільтрів на заводі. Перший фільтр ловить великі
 * частинки, другий — менші, третій — найдрібніші. Якщо поставити найтонший фільтр
 * першим — він зловить все, і решта фільтрів стануть непотрібними.
 *
 * Реальне застосування: обробка різних помилок вводу, різні реакції на різні
 * типи мережевих помилок, логування з різними рівнями серйозності.
 */
public class Example02_MultipleCatch {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Декілька catch для різних винятків
        // ============================================================
        System.out.println("=== Блок 1: Різні catch для різних помилок ===");

        // Сценарій: обробка даних з форми — можуть бути різні помилки
        String[] formData = {"100", "0", "abc", null};

        for (String input : formData) {
            try {
                System.out.print("Обробка '" + input + "': ");
                int length = input.length();         // може бути NullPointerException
                int number = Integer.parseInt(input); // може бути NumberFormatException
                int result = 1000 / number;           // може бути ArithmeticException
                System.out.println("результат = " + result);
            }
            catch (NullPointerException e) {
                System.out.println("ПОМИЛКА — значення null!");
            }
            catch (NumberFormatException e) {
                System.out.println("ПОМИЛКА — не число! (" + e.getMessage() + ")");
            }
            catch (ArithmeticException e) {
                System.out.println("ПОМИЛКА — ділення на нуль!");
            }
        }

        System.out.println("Кожен виняток потрапив у свій catch-блок");
        System.out.println();

        // ============================================================
        //   Блок 2: Порядок catch — від конкретного до загального
        // ============================================================
        System.out.println("=== Блок 2: Порядок catch має значення ===");

        // Спрацьовує ТІЛЬКИ перший підходящий catch
        try {
            int[] arr = {1, 2, 3};
            throw new IllegalArgumentException();

//            System.out.println(arr[10]); // ArrayIndexOutOfBoundsException
        }
        catch (ArrayIndexOutOfBoundsException e) {
            // Конкретний виняток — ловимо першим
            System.out.println("Спрацював конкретний catch: ArrayIndexOutOfBoundsException");
        }
        catch (RuntimeException e) {
            // Батьківський клас — ловить ВСЕ, що не зловив попередній
            System.out.println("Спрацював загальний catch: RuntimeException");
        }
        catch (Exception e) {
            // Ще загальніший — ловить решту
            System.out.println("Спрацював найзагальніший catch: Exception");
        }

        System.out.println("ArrayIndexOutOfBoundsException → RuntimeException → Exception");
        System.out.println("Java обирає ПЕРШИЙ підходящий catch і пропускає решту");
        System.out.println();

        // ============================================================
        //   Блок 3: Exception як «пастка для всього»
        // ============================================================
        System.out.println("=== Блок 3: Загальний catch (Exception) ===");

        // Сценарій: коли не важливо, ЯКА саме помилка — просто логуємо
        String[] testValues = {"42", "hello", null, "0"};

        for (String val : testValues) {
            try {
                int number = Integer.parseInt(val);
                int result = 100 / number;
                System.out.println("  '" + val + "' → " + result);
            }
            catch (Exception e) {
                // Exception ловить БУДЬ-ЯКИЙ виняток
                System.out.println("  '" + val + "' → Помилка: "
                        + e.getClass().getSimpleName() + " — " + e.getMessage());
            }
        }

        System.out.println();
        System.out.println("Exception — батьківський клас для всіх винятків.");
        System.out.println("Якщо поставити його ПЕРШИМ — решта catch не скомпілюються!");
        System.out.println("Правило: від конкретного → до загального.");
    }
}
