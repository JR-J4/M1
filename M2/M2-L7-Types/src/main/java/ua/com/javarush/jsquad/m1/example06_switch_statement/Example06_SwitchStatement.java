package ua.com.javarush.jsquad.m1.example06_switch_statement;

/**
 * Модуль 2. Рівень 7. Приведення типів
 * <hr>
 * <h3>Тема: Switch statement — класичний switch (до Java 12)</h3>
 *
 * <p>У Java є 2 варіанти switch: <b>switch statement</b> (класичний) та
 * <b>switch expression</b> (сучасний). Почнімо з класичного — саме таким switch був
 * до 12-ї версії.</p>
 *
 * <h4>Синтаксис:</h4>
 * <pre>
 *   switch (product) {
 *       case "Apple":
 *           productType = "Fruit";
 *           break;              // без break виконання "провалиться" в наступний case
 *       case "Raspberry":
 *           productType = "Berry";
 *           break;
 *       default:                // якщо жоден case не підійшов
 *           productType = "other";
 *           break;
 *   }
 * </pre>
 *
 * <p><b>Дві ключові особливості класичного switch:</b></p>
 * <ul>
 *   <li>він нічого не повертає — результат треба класти у зовнішню змінну;</li>
 *   <li>без {@code break} керування "провалюється" (fall-through) в наступний case.</li>
 * </ul>
 *
 * <p><b>Аналогія з життя:</b> сортувальна стрічка на складі: коробка їде до потрібної
 * гілки, {@code break} — це "стоп, зійшли зі стрічки"; без нього коробка поїде далі.</p>
 */
public class Example06_SwitchStatement {

    public static void main(String[] args) {

        // === 1. Класифікуємо продукт: значення кладемо в зовнішню змінну ===
        System.out.println("=== Класичний switch із break ===");
        String product = "Raspberry";
        String productType;                 // результат — окрема зовнішня змінна
        switch (product) {
            case "Apple":
                productType = "Фрукт";
                break;
            case "Peach":
                productType = "Фрукт";
                break;
            case "Raspberry":
                productType = "Ягода";
                break;
            default:
                productType = "інше";
                break;
        }
        System.out.println("  " + product + " -> " + productType);
        System.out.println();

        // === 2. Свідомий fall-through: групуємо кілька case під один результат ===
        System.out.println("=== Fall-through: місяць -> пора року ===");
        int month = 4;
        String season;
        switch (month) {
            case 12:
            case 1:
            case 2:                 // 12, 1, 2 "провалюються" до спільного коду
                season = "Зима";
                break;
            case 3:
            case 4:
            case 5:
                season = "Весна";
                break;
            case 6:
            case 7:
            case 8:
                season = "Літо";
                break;
            default:
                season = "Осінь";
        }
        System.out.println("  місяць " + month + " -> " + season);
        System.out.println();


        // === 3. Пастка: ЗАБУЛИ break -> код "тече" далі ===
        System.out.println("=== Що буде без break (демонстрація пастки) ===");
        int level = 1;
        switch (level) {
            case 1:
                System.out.println("  рівень 1");
                // break; ← навмисно "забули"
            case 2:
                System.out.println("  рівень 2 (виконався, бо не було break!)");
                break;
            case 3:
                System.out.println("  рівень 3 (сюди вже не дійшли)");
        }
        System.out.println();

        System.out.println("Головне: класичний switch нічого не повертає, а забутий break — часта помилка.");
    }
}
