package ua.com.javarush.jsquad.m1;

/**
 * Лекція 20: Singleton, enum, switch — Оператор switch (основи)
 *
 * switch — оператор множинного вибору. Він порівнює значення виразу
 * з кількома варіантами (case) і виконує відповідний блок коду.
 *
 * Синтаксис:
 *   switch (вираз) {
 *       case значення1: код1; break;
 *       case значення2: код2; break;
 *       default: кодЗаЗамовчуванням;
 *   }
 *
 * Важливо: без break виконання «провалюється» (fall-through) —
 * виконуються ВСІ наступні блоки до кінця switch.
 *
 * default — аналог else у if-else, спрацьовує коли жоден case не підійшов.
 *
 * Аналогія з життя: ліфт з кнопками поверхів. Натискаєш «3» — ліфт їде
 * на 3 поверх. Без break — ніби зламаний ліфт, що обʼїжджає всі поверхи
 * після обраного.
 *
 * Реальне застосування: вибір пунктів меню, логіка за днем тижня,
 * диспетчеризація команд, прості автомати станів.
 */
public class Example05_SwitchBasics {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Базовий switch з break
        // ============================================================
        System.out.println("=== Блок 1: Базовий switch з break ===");

        int dayNumber = 3;

        // Визначаємо назву дня за номером
        switch (dayNumber) {
            case 1:
                System.out.println("Понеділок");
                break;
            case 2:
                System.out.println("Вівторок");
                break;
            case 3:
                System.out.println("Середа");
                break;
            case 4:
                System.out.println("Четвер");
                break;
            case 5:
                System.out.println("Пʼятниця");
                break;
            case 6:
                System.out.println("Субота");
                break;
            case 7:
                System.out.println("Неділя");
                break;
            default:
                System.out.println("Не знайдено");
                break;
        }

        System.out.println("З break — виконався тільки case 3");
        System.out.println();

        // ============================================================
        //   Блок 2: Fall-through без break — небезпека!
        // ============================================================
        System.out.println("=== Блок 2: Fall-through (без break) ===");

        int floor = 3;
        System.out.println("Зайшли на поверх " + floor + " (без break):");

        // БЕЗ break — виконуються ВСІ case після знайденого!
        switch (floor) {
            case 1:
                System.out.println("  Поверх 1 — Рецепція");
            case 2:
                System.out.println("  Поверх 2 — Бухгалтерія");
            case 3:
                System.out.println("  Поверх 3 — IT-відділ");       // ← починається тут
            case 4:
                System.out.println("  Поверх 4 — Маркетинг");       // ← і провалюється далі
            case 5:
                System.out.println("  Поверх 5 — Директор");        // ← і далі
        }

        System.out.println("Без break виконались case 3, 4 і 5!");
        System.out.println();

        // ============================================================
        //   Блок 3: Навмисний fall-through — групування
        // ============================================================
        System.out.println("=== Блок 3: Корисний fall-through — групування ===");

        // Визначаємо: робочий день чи вихідний?
        for (int day = 1; day <= 7; day++) {
            String type;
            switch (day) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    type = "Робочий день";
                    break;
                case 6:
                case 7:
                    type = "Вихідний";
                    break;
                default:
                    type = "Невідомий";
            }
            System.out.println("  День " + day + " → " + type);
        }

        System.out.println("Cases 1-5 «провалюються» до спільного коду — це зручно!");
        System.out.println();

        // ============================================================
        //   Блок 4: default — коли нічого не підійшло
        // ============================================================
        System.out.println("=== Блок 4: default — дія за замовчуванням ===");

        // default — як else в if-else
        int[] testMonths = {1, 6, 12, 0, 13, -1};

        for (int month : testMonths) {
            switch (month) {
                case 1:
                    System.out.println("  " + month + " → Січень");
                    break;
                case 6:
                    System.out.println("  " + month + " → Червень");
                    break;
                case 12:
                    System.out.println("  " + month + " → Грудень");
                    break;
                default:
                    System.out.println("  " + month + " → Невідомий місяць!");
            }
        }

        System.out.println();
        System.out.println("default обробляє всі значення, що не підійшли під case.");
        System.out.println("В останнього варіанта break можна не писати — він і так останній.");
    }
}
