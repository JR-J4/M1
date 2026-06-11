package ua.com.javarush.jsquad.m1.example04_access_modifiers;

/**
 * Модуль 2. Рівень 1. ООП: інкапсуляція, поліморфізм
 * <hr>
 * <h3>Тема: Інкапсуляція — модифікатори доступу</h3>
 *
 * <p>Приховування даних у Java забезпечують <b>модифікатори доступу</b>:
 * {@code private}, {@code protected}, package default (без слова) та {@code public}.
 * Вони визначають, ХТО має право бачити поле чи метод.</p>
 *
 * <p><b>Аналогія з життя:</b> банківська картка.
 * Номер картки можна показати касиру ({@code public}). Ім'я власника бачать у банку
 * ({@code package}). PIN-код не знає ніхто, крім вас ({@code private}) — навіть інші
 * системи банку дістаються до грошей лише через перевірку PIN.</p>
 *
 * <p><b>Правило хорошого тону:</b> робіть поля {@code private}, а доступ давайте через
 * методи. Так клас контролює свій стан.</p>
 *
 * <p><b>Реальне застосування:</b> бібліотечні класи лишають {@code public} лише те,
 * чим справді можна користуватись, а решту ховають — щоб ніхто не зламав внутрішню логіку.</p>
 */
public class Example04_AccessModifiers {

    public static void main(String[] args) {

        BankCard card = new BankCard("4441 1144 2222 3333", "Ірина", 1234);

        // === 1. Доступ із зовнішнього класу (інший пакет — як цей Example, так і будь-який) ===
        System.out.println("=== Доступ ЗВІДСИ (зовнішній код) ===");
        System.out.println("public cardNumber: " + card.cardNumber); // ✔ можна

        // card.pinCode;       // ✖ private — ПОМИЛКА КОМПІЛЯЦІЇ
        // card.holderName;    // ✔ тут можна, бо Example у тому ж пакеті
        System.out.println("private pinCode:   ✖ напряму недоступний");
        System.out.println();

        // === 2. Працюємо лише через публічний метод ===
        System.out.println("=== Контрольований доступ через public-метод ===");
        card.withdraw(0000, 100); // невірний PIN — відмова
        card.withdraw(1234, 100); // вірний PIN — операція проходить
        System.out.println();

        // === 3. Що бачить сусід по пакету ===
        System.out.println("=== Клас із того ж пакета (CardInspector) ===");
        new CardInspector().inspect(card);
        System.out.println();

        System.out.println("Висновок: private — найнадійніший захист даних.");
        System.out.println("Зовнішній світ працює лише через публічні методи.");
    }
}
