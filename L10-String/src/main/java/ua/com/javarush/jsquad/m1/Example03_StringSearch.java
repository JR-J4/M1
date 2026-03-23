package ua.com.javarush.jsquad.m1;

/**
 * Лекцiя 10: Пошук у рядках
 *
 * Методи indexOf(), lastIndexOf(), matches() допомагають знайти
 * пiдрядки i перевiрити формат тексту.
 *
 * Аналогiя: шукаємо номер телефону в довгому чатi — читаємо з початку
 * чи з кiнця, залежно вiд ситуацiї.
 *
 * Реальне застосування: модерацiя текстiв, пошук тегiв у HTML, перевiрка
 * номерiв накладних за шаблоном.
 */
public class Example03_StringSearch {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Виявлення ключового слова у відгуку клієнта
        // ============================================================
        System.out.println("=== indexOf() ===");

        String feedback = "Отримав замовлення, хотів би знижку на наступну покупку. знижку";
        int discountWordIndex = feedback.indexOf("знижку");

        if (discountWordIndex >= 0) {
            System.out.println("Менеджеру варто відповісти: клієнт просить знижку.");
            System.out.println("Індекс слова 'знижку': " + discountWordIndex);
        }
        System.out.println();

        // ============================================================
        //   Приклад 2: Останнє входження тега у HTML-шаблоні
        // ============================================================
        System.out.println("=== lastIndexOf() ===");

        String html = "<div><p>Новини</p><p>Останні статті</p></div>";
        int lastParagraph = html.lastIndexOf("<p>");

        System.out.println("Останній <p> знайдено на позиції: " + lastParagraph);
        System.out.println("Можемо вставити рекламний блок після цього тега.");
        System.out.println();

        // ============================================================
        //   Приклад 3: Перевірка формату серійного номера
        // ============================================================
        System.out.println("=== matches() з регулярним виразом ===");

        String serial = "SN-2024-AB12";
        boolean valid = serial.matches("SN-\\d{4}-[A-Z]{2}\\d{2}");

        System.out.println("Серійний номер валідний: " + valid);
        System.out.println("Шаблон: SN-YYYY-LLNN (Y — цифри року, L — літери, N — цифри).");
    }
}
