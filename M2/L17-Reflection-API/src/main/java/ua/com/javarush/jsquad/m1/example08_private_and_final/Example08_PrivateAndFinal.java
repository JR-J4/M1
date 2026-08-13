package ua.com.javarush.jsquad.m1.example08_private_and_final;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Модуль 2. Рівень 17. Reflection API
 * <hr>
 * <h3>Тема: Заміна значення private та final змінних, метод setAccessible()</h3>
 *
 * <p>Як і у випадку з отриманням значення private змінної або зміни final
 * змінної, перед використанням одного з методів {@code set} потрібно викликати
 * метод {@code setAccessible(true)}. Якщо цього не зробити, при спробі змінити
 * таку змінну ми отримаємо {@code IllegalAccessException}.</p>
 *
 * <h4>Приклад із лекції:</h4>
 * <pre>
 *   Cat cat = new Cat("Tom");
 *   Class&lt;? extends Cat&gt; catClass = cat.getClass();
 *   Field nameField = catClass.getDeclaredField("name");
 *   nameField.setAccessible(true);
 *   nameField.set(cat, "Jerry");
 *   nameField.setAccessible(false);   // ← гарна практика
 * </pre>
 *
 * <p>Після зміни поля хорошою практикою вважається повернути прапорець
 * "Accessible" до стану {@code false}.</p>
 *
 * <h4>Що з чим працює:</h4>
 * <pre>
 *   private поле              — так, після setAccessible(true)
 *   final поле об'єкта        — так, після setAccessible(true) (але це дуже погана ідея)
 *   static final поле         — НІ, буде IllegalAccessException
 *   final з константним виразом — значення в полі зміниться, але код цього не помітить
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> {@code setAccessible(true)} — це зняти пломбу з
 * приладу. Формально ви отримали доступ до всіх гвинтиків. Але з моменту зняття
 * пломби виробник більше не відповідає за те, що прилад працюватиме правильно.</p>
 *
 * <p><b>Реальне застосування:</b> Jackson і Hibernate постійно роблять
 * {@code setAccessible(true)}, щоб заповнити приватні поля ваших класів без
 * сеттерів. У звичайному прикладному коді це майже завжди означає, що щось
 * спроєктовано неправильно.</p>
 */
public class Example08_PrivateAndFinal {

    public static void main(String[] args) throws Exception {

        SecretBox box = new SecretBox("Адміністратор", "код-1234");
        Class<SecretBox> boxClass = SecretBox.class;

        // === 1. Без setAccessible(true) приватне поле недоступне ===
        System.out.println("1. Спроба без setAccessible():");

        Field secret = boxClass.getDeclaredField("secret");
        System.out.println("   Поле знайшли: " + secret.getName()
                + " (getDeclaredField бачить private без проблем)");

        try {
            secret.get(box);          // саме тут спрацьовує захист
        } catch (IllegalAccessException e) {
            System.out.println("   secret.get(box) -> IllegalAccessException");
            System.out.println("   Знайти поле можна завжди, а от читати/писати — ні.");
        }

        // canAccess() перевіряє доступ, не кидаючи винятку
        System.out.println("   secret.canAccess(box) -> " + secret.canAccess(box));

        System.out.println();

        // === 2. setAccessible(true) — і захист знято ===
        System.out.println("2. Після setAccessible(true):");

        secret.setAccessible(true);
        System.out.println("   Прочитали:  " + secret.get(box));

        secret.set(box, "код-9999");
        System.out.println("   Записали:   " + box.getSecret());

        // Гарна практика з лекції — повернути прапорець назад
        secret.setAccessible(false);
        System.out.println("   Повернули setAccessible(false), canAccess -> " + secret.canAccess(box));

        System.out.println();

        // === 3. trySetAccessible() — м'який варіант ===
        // Не кидає виняток, а повертає false, якщо доступ отримати не вдалося.
        Field attempts = boxClass.getDeclaredField("attempts");

        System.out.println("3. trySetAccessible() замість винятків:");
        System.out.println("   для нашого поля attempts: " + attempts.trySetAccessible());

        Field jdkField = String.class.getDeclaredField("hash");
        System.out.println("   для поля з java.lang.String: " + jdkField.trySetAccessible()
                + " (модулі JDK закриті — і жодного винятку)");

        System.out.println();

        // === 4. final-поле об'єкта ===
        // Сценарій: createdBy оголошено final і задано в конструкторі.
        Field createdBy = boxClass.getDeclaredField("createdBy");

        System.out.println("4. final-поле об'єкта (createdBy):");
        System.out.println("   final? " + Modifier.isFinal(createdBy.getModifiers()));
        System.out.println("   До зміни:  " + box.getCreatedBy());

        createdBy.setAccessible(true);
        createdBy.set(box, "Зловмисник");

        System.out.println("   Після зміни: " + box.getCreatedBy());
        System.out.println("   Так, final-поле об'єкта рефлексія змінити може.");
        System.out.println("   Але робити так не варто: JVM вважає final-поля незмінними");
        System.out.println("   і оптимізує код, спираючись на цю обіцянку. У нових версіях");
        System.out.println("   Java таку зміну дедалі сильніше обмежують.");

        System.out.println();

        // === 5. Пастка: final з константним виразом ===
        // Поле label оголошене як: private final String label = "Оригінальна етикетка";
        // Це "константна змінна" — компілятор підставив її значення прямо в getLabel().
        Field label = boxClass.getDeclaredField("label");
        label.setAccessible(true);

        System.out.println("5. Пастка з константним виразом:");
        System.out.println("   getLabel() до зміни:      " + box.getLabel());

        label.set(box, "ПІДРОБКА");

        System.out.println("   label.get(box) після:     " + label.get(box) + "   <- поле справді змінилося");
        System.out.println("   getLabel() після:         " + box.getLabel() + "   <- а метод повертає старе!");
        System.out.println("   Причина: компілятор перетворив тіло getLabel() на");
        System.out.println("   return \"Оригінальна етикетка\"; — поля там уже нема, є літерал.");

        System.out.println();

        // === 6. static final змінити не вийде ===
        Field maxAttempts = boxClass.getDeclaredField("MAX_ATTEMPTS");
        maxAttempts.setAccessible(true);

        System.out.println("6. static final поле (MAX_ATTEMPTS = " + SecretBox.MAX_ATTEMPTS + "):");
        System.out.println("   static? " + Modifier.isStatic(maxAttempts.getModifiers())
                + ", final? " + Modifier.isFinal(maxAttempts.getModifiers()));

        try {
            maxAttempts.setInt(null, 999);
            System.out.println("   Змінено на " + maxAttempts.getInt(null));
        } catch (IllegalAccessException e) {
            System.out.println("   setInt(null, 999) -> IllegalAccessException");
            System.out.println("   " + e.getMessage());
            System.out.println("   Навіть setAccessible(true) тут не допомагає — це остаточна межа.");
        }

        System.out.println();

        // === 7. Практика: читаємо приватний стан чужого об'єкта ===
        // Сценарій: у тесті треба перевірити внутрішній стан, до якого немає геттера.
        // Це чи не єдиний випадок, коли setAccessible у прикладному коді виправданий.
        System.out.println("7. Де це справді доречно — перевірка стану в тестах:");

        SecretBox underTest = new SecretBox("Тест", "початковий");
        Field attemptsField = boxClass.getDeclaredField("attempts");
        attemptsField.setAccessible(true);
        attemptsField.setInt(underTest, 2);          // імітуємо стан "було 2 спроби"

        System.out.println("   Підготували стан: attempts = " + underTest.getAttempts());
        System.out.println("   Такий об'єкт неможливо створити через публічний API,");
        System.out.println("   але для перевірки граничного випадку він потрібен.");

        System.out.println();

        // === 8. Підсумкова шпаргалка ===
        System.out.println("8. Що можна, а що ні:");
        System.out.println("   private поле                -> setAccessible(true), і все працює");
        System.out.println("   final поле об'єкта          -> технічно можна, але це погана ідея");
        System.out.println("   static final поле           -> IllegalAccessException, змінити не можна");
        System.out.println("   final з константним виразом -> поле зміниться, поведінка коду — ні");
        System.out.println("   поля класів JDK             -> InaccessibleObjectException (закриті модулі)");
        System.out.println("   після роботи                -> setAccessible(false), як радить лекція");
    }
}
