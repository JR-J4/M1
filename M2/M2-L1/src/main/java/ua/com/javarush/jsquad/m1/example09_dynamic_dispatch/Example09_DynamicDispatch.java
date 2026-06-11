package ua.com.javarush.jsquad.m1.example09_dynamic_dispatch;

/**
 * Модуль 2. Рівень 1. ООП: інкапсуляція, поліморфізм
 * <hr>
 * <h3>Тема: Поліморфізм — динамічна диспетчеризація методів</h3>
 *
 * <p>Це найважливіша частина поліморфізму. Зі слайду лекції:</p>
 * <blockquote>
 *   <b>Набір методів</b>, які можна викликати у змінній, визначається <b>типом ЗМІННОЇ</b>.<br>
 *   А <b>який саме метод</b> / яку реалізацію буде викликано, визначається
 *   <b>типом / класом ОБ'ЄКТА</b>, посилання на яке зберігає змінна.
 * </blockquote>
 *
 * <pre>
 *   Report r = new PdfReport("Звіт");
 *        ▲                ▲
 *        │                └── тип ОБ'ЄКТА → яка реалізація generate() виконається
 *        └── тип ЗМІННОЇ → які методи взагалі можна викликати
 *
 *   r.generate();      // ✔ викличе PdfReport.generate()  (вирішує об'єкт)
 *   r.addWatermark();  // ✖ не скомпілюється (вирішує тип змінної Report)
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> ви замовляєте "звіт" (тип змінної — Report). Який саме —
 * PDF, Excel чи HTML — залежить від того, що реально поклали в коробку (тип об'єкта).
 * Команда одна: "згенеруй". Результат — різний.</p>
 *
 * <p><b>Реальне застосування:</b> ця "магія" дозволяє писати код, який працює з
 * майбутніми класами. Додамо завтра {@code XmlReport} — цей цикл нижче не зміниться.</p>
 */
public class Example09_DynamicDispatch {

    public static void main(String[] args) {

        // === 1. Тип змінної визначає, ЩО можна викликати ===
        System.out.println("=== Тип ЗМІННОЇ визначає набір методів ===");
        Report report = new PdfReport("Квартальний"); // об'єкт PdfReport у змінній Report
        report.generate();        // ✔ generate() є в типі Report
        // report.addWatermark(); // ✖ ПОМИЛКА: тип Report не має методу addWatermark()
        System.out.println("Через змінну Report метод addWatermark() недоступний,");
        System.out.println("хоча об'єкт PdfReport його має. Вирішує ТИП ЗМІННОЇ.");
        System.out.println();

        // === 2. Тип об'єкта визначає, ЯКА реалізація виконається ===
        System.out.println("=== Тип ОБ'ЄКТА визначає реалізацію (пізнє зв'язування) ===");
        Report r1 = new PdfReport("Продажі");
        Report r2 = new ExcelReport("Бюджет");
        Report r3 = new HtmlReport("Дашборд");
        r1.generate(); // PdfReport.generate()
        r2.generate(); // ExcelReport.generate()
        r3.generate(); // HtmlReport.generate()
        System.out.println("Змінні однакові (Report), а методи спрацювали РІЗНІ — за об'єктом.");
        System.out.println();

        // === 3. Поліморфізм у дії: однаковий код для різних звітів ===
        System.out.println("=== Один цикл генерує всі формати ===");
        Report[] reports = {
                new PdfReport("Звіт A"),
                new ExcelReport("Звіт B"),
                new HtmlReport("Звіт C")
        };
        for (Report report2 : reports) {
            report2.generate(); // JVM сама обере потрібну реалізацію для кожного об'єкта
        }
        System.out.println();

        // === 4. Щоб дістати "особливі" методи — звужуємо тип ===
        System.out.println("=== Доступ до методів конкретного класу ===");
        if (report instanceof PdfReport pdf) {
            pdf.addWatermark(); // тепер тип PdfReport — метод доступний
        }
        System.out.println();

        System.out.println("Динамічна диспетчеризація — серце поліморфізму:");
        System.out.println("реалізацію обирає ОБ'ЄКТ під час виконання, а не компілятор.");
    }
}
