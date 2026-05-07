package ua.com.javarush.jsquad.m1;

/**
 * Лекція 21: Винятки — Ключове слово finally
 *
 * Блок finally виконується ЗАВЖДИ — незалежно від того, виникла помилка чи ні.
 * Він гарантує виконання «завершального» коду.
 *
 * Синтаксис:
 *   try {
 *       // код, де може виникнути помилка
 *   }
 *   catch (ТипВинятку e) {
 *       // обробка помилки
 *   }
 *   finally {
 *       // виконається ЗАВЖДИ
 *   }
 *
 * Коли виконується finally:
 *   1. Виняток НЕ виник → try → finally
 *   2. Виняток виник і перехоплений → try → catch → finally
 *   3. Виняток виник і НЕ перехоплений → try → finally → програма падає
 *
 * Аналогія з життя: коли виходиш з дому — треба зачинити двері. Чи йдеш на роботу,
 * чи в магазин, чи забув щось і повертаєшся — двері треба зачинити ЗАВЖДИ.
 * finally — це «зачини двері».
 *
 * Реальне застосування: закриття файлів, зʼєднань з БД, звільнення ресурсів,
 * завершення транзакцій, запис у лог.
 */
public class Example04_Finally {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: finally без винятку
        // ============================================================
        System.out.println("=== Блок 1: finally — коли все добре ===");

        try {
            System.out.println("try: Відкриваємо файл...");
            System.out.println("try: Читаємо дані...");
            // Все пройшло без помилок
        }
        catch (Exception e) {
            System.out.println("catch: Помилка — " + e.getMessage());
        }
        finally {
            System.out.println("finally: Закриваємо файл (виконується ЗАВЖДИ)");
        }

        System.out.println("Без помилок: try → finally (catch пропущено)");
        System.out.println();

        // ============================================================
        //   Блок 2: finally з винятком (перехоплений)
        // ============================================================
        System.out.println("=== Блок 2: finally — коли є виняток (перехоплений) ===");

        try {
            System.out.println("try: Підключаємось до бази даних...");
            int result = 10 / 0; // ← виняток!
            System.out.println("try: Запит виконано"); // ← НЕ виконається
        }
        catch (ArithmeticException e) {
            System.out.println("catch: Помилка запиту — " + e.getMessage());
        }
        finally {
            System.out.println("finally: Закриваємо з'єднання з БД");
        }

        System.out.println("З помилкою: try → catch → finally");
        System.out.println();

        // ============================================================
        //   Блок 3: finally БЕЗ catch
        // ============================================================
        System.out.println("=== Блок 3: try-finally без catch ===");

        // finally може бути без catch! Виняток «пролетить» далі,
        // але finally все одно виконається
        try {
                    try {
                        System.out.println("try: Починаємо операцію...");
                        String s = null;
                         s.length(); // ← якщо розкоментувати, виняток пролетить через finally
                        System.out.println("try: Операція завершена");
                    }
                    finally {
                        System.out.println("finally: Очищення ресурсів (навіть без catch!)");
                    }
        }
        catch (Exception e) {
            System.out.println("Зовнішній catch зловив виняток: " + e.getMessage());
        }

        System.out.println();

        // ============================================================
        //   Блок 4: Практичний приклад — імітація роботи з ресурсом
        // ============================================================
        System.out.println("=== Блок 4: Практика — робота з ресурсом ===");

        // Сценарій: читаємо конфігурацію з файлу (імітація)
        String[] configFiles = {"settings.cfg", "missing.cfg", "database.cfg"};

        for (String fileName : configFiles) {
            System.out.println("--- Файл: " + fileName + " ---");
            boolean fileOpened = false;

            try {
                // Імітація: «відкриваємо» файл
                fileOpened = true;
                System.out.println("  Файл відкрито");

                // Імітація: файл «missing.cfg» не знайдений
                if (fileName.equals("missing.cfg")) {
                    throw new RuntimeException("Файл не знайдено: " + fileName);
                }

                System.out.println("  Конфігурацію прочитано");
            }
            catch (RuntimeException e) {
                System.out.println("  Помилка: " + e.getMessage());
            }
            finally {
                // Закриваємо файл ЗАВЖДИ — і при успіху, і при помилці
                if (fileOpened) {
                    System.out.println("  [finally] Файл закрито");
                }
            }
        }

        System.out.println();
        System.out.println("finally — гарантія того, що ресурси будуть звільнені.");
        System.out.println("Навіть якщо в try виникла помилка — finally виконається.");
    }
}
