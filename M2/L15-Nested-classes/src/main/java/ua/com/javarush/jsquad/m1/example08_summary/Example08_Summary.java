package ua.com.javarush.jsquad.m1.example08_summary;

import java.util.Comparator;

/**
 * Модуль 2. Рівень 15. Внутрішні та вкладені класи
 * <hr>
 * <h3>Тема: Підсумок — служба таксі на внутрішніх, вкладених і анонімних класах</h3>
 *
 * <p>Один робочий сценарій, у якому кожен вид класу стоїть саме там, де треба:</p>
 *
 * <pre>
 *   TaxiService                 — зовнішній клас
 *     ├── static class Driver   — ВКЛАДЕНИЙ: водій не залежить від служби
 *     ├── class Trip            — ВНУТРІШНІЙ: рахує вартість за тарифом СВОЄЇ служби
 *     └── TripListener          — АНОНІМНИЙ клас у місці підписки на подію
 * </pre>
 *
 * <h4>Шпаргалка з лекції:</h4>
 * <pre>
 *   Внутрішній клас   class Inner        outer.new Inner()      є Outer.this
 *   Вкладений клас    static class N     new Outer.N()          немає Outer.this, бачить private static
 *   Анонімний клас    new Тип() { ... }  оголошення + створення одночасно
 *   Локальний клас    class L { ... }    оголошений у тілі методу, має ім'я і конструктор
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> таксопарк. Водій (вкладений) може завтра перейти
 * в іншу службу — він самостійна одиниця. Поїздка (внутрішній) без служби не
 * існує: тариф, місто й статистика — її. А диспетчер-спостерігач (анонімний)
 * потрібен рівно на час зміни.</p>
 *
 * <p><b>Реальне застосування:</b> так і виглядає більшість реального коду —
 * доменні сутності окремими класами, службові структури вкладеними, обробники
 * подій анонімними класами або лямбдами.</p>
 */
public class Example08_Summary {

    public static void main(String[] args) {

        // === 1. Вкладений клас: водіїв створюємо ще до того, як обрали службу ===
        System.out.println("1. Наймаємо водіїв (вкладений клас Driver):");

        TaxiService.Driver oksana = new TaxiService.Driver("Оксана", "Skoda Octavia", 4.9);
        TaxiService.Driver petro = new TaxiService.Driver("Петро", "Renault Logan", 4.5);
        TaxiService.Driver maryna = new TaxiService.Driver("Марина", "Toyota Prius", 4.8);

        TaxiService kyiv = new TaxiService("Київ", 18.50);
        kyiv.hire(oksana);
        kyiv.hire(petro);
        kyiv.hire(maryna);

        System.out.println();

        // === 2. Анонімний клас: сортуємо водіїв за рейтингом ===
        System.out.println("2. Рейтинг водіїв (анонімний Comparator):");

        Comparator<TaxiService.Driver> byRating = new Comparator<>() {
            @Override
            public int compare(TaxiService.Driver a, TaxiService.Driver b) {
                return Double.compare(b.getRating(), a.getRating());   // за спаданням
            }
        };
        kyiv.getDrivers().sort(byRating);

        for (TaxiService.Driver driver : kyiv.getDrivers()) {
            System.out.println("   " + driver);
        }

        System.out.println();

        // === 3. Анонімний клас із власним станом: підписуємося на завершення поїздок ===
        System.out.println("3. Диспетчер стежить за виручкою (анонімний TripListener):");

        kyiv.setListener(new TripListener() {

            private int count = 0;              // власне поле анонімного класу
            private double revenue = 0;

            @Override
            public void onTripFinished(String description, double cost) {
                count++;
                revenue += cost;
                System.out.println("     диспетчер: поїздок " + count
                        + ", виручка " + String.format("%.2f", revenue) + " грн");
            }
        });

        System.out.println();

        // === 4. Внутрішній клас: поїздка рахує вартість за тарифом своєї служби ===
        System.out.println("4. Поїздки (внутрішній клас Trip):");

        TaxiService.Trip trip1 = kyiv.order("Оксана", 7.2);
        trip1.finish();

        TaxiService.Trip trip2 = kyiv.order("Марина", 3.5);
        trip2.finish();

        System.out.println();

        // === 5. Той самий водій в іншому місті — інший тариф ===
        // Доказ, що Trip бере pricePerKm саме зі свого зовнішнього об'єкта.
        System.out.println("5. Інша служба — інший тариф на ту саму відстань:");

        TaxiService lviv = new TaxiService("Львів", 14.00);
        lviv.hire(petro);
        TaxiService.Trip trip3 = lviv.order("Петро", 7.2);

        System.out.println("   Київ, 7.2 км: " + String.format("%.2f", trip1.cost()) + " грн");
        System.out.println("   Львів, 7.2 км: " + String.format("%.2f", trip3.cost()) + " грн");
        trip3.finish();          // слухача у львівської служби немає — рядка диспетчера не буде

        System.out.println("   Усього поїздок у компанії: " + TaxiService.getTotalTrips());

        System.out.println();

        // === 6. Локальний клас: разовий звіт наприкінці зміни ===
        // Потрібен лише в цьому методі, зате з конструктором і кількома методами.
        class ShiftReport {

            private final String title;

            ShiftReport(String title) {
                this.title = title;
            }

            void print() {
                System.out.println("   ┌ " + title);
                System.out.println("   │ Водіїв у Києві: " + kyiv.getDrivers().size());
                System.out.println("   │ Місто: " + kyiv.getCity());
                System.out.println("   └ Поїздок за день: " + TaxiService.getTotalTrips());
            }
        }

        System.out.println("6. Звіт (локальний клас):");
        new ShiftReport("Зміна закрита").print();

        System.out.println();

        // === 7. Хто є хто — за технічними іменами класів ===
        System.out.println("7. Перевіримо себе за іменами класів:");
        System.out.println("   Driver:       " + TaxiService.Driver.class.getName() + "  — вкладений");
        System.out.println("   Trip:         " + TaxiService.Trip.class.getName() + "    — внутрішній");
        System.out.println("   byRating:     " + byRating.getClass().getName() + "      — анонімний");
        System.out.println("   ShiftReport:  " + ShiftReport.class.getName() + " — локальний");

        System.out.println();

        // === 8. Як обрати вид класу — коротко ===
        System.out.println("8. Правило вибору:");
        System.out.println("   Потрібен доступ до полів ОБ'ЄКТА зовнішнього класу? → внутрішній (без static)");
        System.out.println("   Не потрібен?                                        → вкладений (static)");
        System.out.println("   Реалізація потрібна рівно в одному місці?           → анонімний клас");
        System.out.println("   Той самий інтерфейс, один метод, без свого стану?   → лямбда");
    }
}
