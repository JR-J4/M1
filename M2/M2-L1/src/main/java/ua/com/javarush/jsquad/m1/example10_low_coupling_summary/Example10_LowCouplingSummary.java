package ua.com.javarush.jsquad.m1.example10_low_coupling_summary;

/**
 * Модуль 2. Рівень 1. ООП: інкапсуляція, поліморфізм
 * <hr>
 * <h3>Тема: Слабка зв'язність + підсумок усієї лекції</h3>
 *
 * <p><b>Слабка зв'язність (зі слайду):</b> внутрішню структуру продукту потрібно
 * підтримувати в такому стані, який дозволяє вносити зміни з мінімальними переробками.
 * Програма розбивається на шари; один шар звертається до іншого та використовує лише
 * невелику, суворо регламентовану частину його класів.</p>
 *
 * <h4>Як усі 4 принципи працюють разом у цьому прикладі:</h4>
 * <pre>
 *   ┌──────────────────────────────────────────────────────────┐
 *   │ OnlineStore  ──залежить лише від──▶  interface PaymentMethod│
 *   │ (верхній шар)        слабка             pay() / name()      │
 *   │                     зв'язність                ▲             │
 *   └───────────────────────────────────────────────┼────────────┘
 *                          ┌──────────────┬──────────┴──────┐
 *                   CardPayment      PayPalPayment     CryptoPayment
 * </pre>
 * <ul>
 *   <li><b>АБСТРАКЦІЯ:</b> інтерфейс {@code PaymentMethod} — суть "вміє платити",
 *       без деталей.</li>
 *   <li><b>ІНКАПСУЛЯЦІЯ:</b> номер картки/гаманець приховані ({@code private}).</li>
 *   <li><b>ПОЛІМОРФІЗМ:</b> один виклик {@code payment.pay()} — різна логіка.</li>
 *   <li><b>СЛАБКА ЗВ'ЯЗНІСТЬ:</b> магазин не знає про конкретні класи оплати.</li>
 * </ul>
 *
 * <p><b>Головна перевага:</b> щоб додати криптооплату, ми створили лише новий клас
 * {@code CryptoPayment} — і не змінили ані рядка в {@code OnlineStore}.</p>
 */
public class Example10_LowCouplingSummary {

    public static void main(String[] args) {

        OnlineStore store = new OnlineStore();

        // === 1. Один магазин — різні способи оплати (поліморфізм) ===
        System.out.println("=== Один метод checkout() — різні способи оплати ===");
        store.checkout("Навушники", 1200, new CardPayment("4441114422223333"));
        store.checkout("Книга \"Java\"", 350, new PayPalPayment("reader@mail.com"));

        // === 2. Слабка зв'язність: новий спосіб оплати без змін у магазині ===
        System.out.println("=== Додали CryptoPayment — магазин не змінювали! ===");
        store.checkout("Відеокурс", 2000, new CryptoPayment("bc1q...x7"));

        // === 3. Список замовлень — повний поліморфізм ===
        System.out.println("=== Обробка кошика різними способами ===");
        PaymentMethod[] methods = {
                new CardPayment("0000111122223333"),
                new PayPalPayment("user@pay.com"),
                new CryptoPayment("0xABCD...")
        };
        double[] prices = { 99, 149, 199 };
        for (int i = 0; i < methods.length; i++) {
            // checkout не знає і не питає, який це клас — працює через інтерфейс
            store.checkout("Товар №" + (i + 1), prices[i], methods[i]);
        }

        // === ПІДСУМОК ЛЕКЦІЇ ===
        System.out.println("=== ПІДСУМОК: 4 принципи ООП ===");
        System.out.println("1. Абстракція    — виділяємо головне (інтерфейс PaymentMethod).");
        System.out.println("2. Інкапсуляція  — ховаємо дані за private + методами.");
        System.out.println("3. Успадкування  — будуємо класи на основі інших.");
        System.out.println("4. Поліморфізм   — один виклик, різна поведінка.");
        System.out.println("Разом вони роблять код гнучким і легким до змін.");
    }
}
