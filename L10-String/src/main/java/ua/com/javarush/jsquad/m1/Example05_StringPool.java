package ua.com.javarush.jsquad.m1;

/**
 * Лекцiя 10: String Pool та метод intern()
 *
 * Лiтерали зберiгаються у спецiальному пулi рядкiв. Виклик intern()
 * додає рядок до пулу i повертає посилання на його єдиний екземпляр.
 *
 * Аналогiя: бейджi з iменами учасникiв. У спiльнiй коробцi лежить
 * по одному бейджу на iм'я — щоб не друкувати зайвих копiй.
 *
 * Реальне застосування: кешування ролей, довiдникiв, кодiв помилок,
 * де однакових рядкiв дуже багато.
 */
public class Example05_StringPool {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Різниця між == і equals()
        // ============================================================
        System.out.println("=== Лiтерали проти new String() ===");

        String literalA = "CRM";
        String literalB = "CRM";
        String constructed = new String("CRM");

        System.out.println("literalA == literalB: " + (literalA == literalB));
        System.out.println("literalA == constructed: " + (literalA == constructed));
        System.out.println("literalA.equals(constructed): " + literalA.equals(constructed));
        System.out.println();

        // ============================================================
        //   Приклад 2: Використання intern() для економії пам'яті
        // ============================================================
        System.out.println("=== intern() ===");

        String statusFromDb = new String("APPROVED");
        String pooledStatus = statusFromDb.intern();

        System.out.println("До intern(): " + (statusFromDb == "APPROVED"));
        System.out.println("Пiсля intern(): " + (pooledStatus == "APPROVED"));
        System.out.println();

        // ============================================================
        //   Приклад 3: Каталог повторюваних тегів
        // ============================================================
        System.out.println("=== Масове застосування intern() ===");

        String[] tags = {
                new String("video"),
                new String("article"),
                new String("video"),
                new String("podcast"),
                new String("video")
        };

        for (int i = 0; i < tags.length; i++) {
            tags[i] = tags[i].intern();
        }

        System.out.println("video посилання однакові: " + (tags[0] == tags[2]));
        System.out.println("video == podcast: " + (tags[0] == tags[3]));
    }
}
