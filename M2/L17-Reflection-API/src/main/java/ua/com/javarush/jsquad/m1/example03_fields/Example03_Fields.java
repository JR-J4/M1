package ua.com.javarush.jsquad.m1.example03_fields;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;

/**
 * Модуль 2. Рівень 17. Reflection API
 * <hr>
 * <h3>Тема: Отримання даних про поля — клас java.lang.reflect.Field</h3>
 *
 * <p>Кожне поле класу рефлексія представляє об'єктом {@code Field}. Це не саме
 * значення поля, а його <b>опис</b>: як називається, якого типу, з якими
 * модифікаторами та анотаціями.</p>
 *
 * <h4>Як отримати поля:</h4>
 * <pre>
 *   clazz.getFields()               // лише public, ЗАТЕ разом з успадкованими
 *   clazz.getDeclaredFields()       // ВСІ поля цього класу (private теж), без успадкованих
 *   clazz.getField("sku")           // одне public-поле за іменем
 *   clazz.getDeclaredField("title") // одне будь-яке поле цього класу за іменем
 * </pre>
 *
 * <h4>Основні методи класу Field:</h4>
 * <pre>
 *   getName()                 — ім'я поля
 *   getType()                 — оголошений тип поля (як Class)
 *   getGenericType()          — тип разом з узагальненнями: List&lt;String&gt;, а не просто List
 *   getAnnotatedType()        — тип з анотаціями типу
 *   getModifiers()            — модифікатори числом (розшифровує клас Modifier)
 *   getAnnotation(Клас)       — одна анотація вказаного типу або null
 *   getAnnotationsByType(Клас)— усі анотації цього типу (для повторюваних); порожній масив, якщо їх немає
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> {@code Field} — це не товар на складі, а
 * <i>картка обліку</i> цього товару: назва, категорія, одиниця виміру, позначки.
 * Сама коробка з товаром лежить в об'єкті, а картка описує, що це таке.</p>
 *
 * <p><b>Реальне застосування:</b> саме так працює Hibernate: пробігає полями
 * класу, у кожного питає {@code getAnnotation(Column.class)} і будує SQL. Так само
 * Jackson будує JSON, а валідатори перевіряють {@code @NotNull}.</p>
 */
public class Example03_Fields {

    public static void main(String[] args) throws Exception {


        Product p = new Product("Title", new BigDecimal("42999.00"), 10);

        System.out.println(p);

        Field titleField = p.getClass().getDeclaredField("title");
        titleField.setAccessible(true);

        titleField.set(p, "New Title");

        titleField.setAccessible(false);

        System.out.println(p);



        Class<Product> productClass = Product.class;

        // === 1. getFields() vs getDeclaredFields() ===
        // Сценарій: треба зрозуміти, які поля взагалі є у класу Product.
        System.out.println("1. getFields() — лише public, але й успадковані:");
        for (Field field : productClass.getFields()) {
            System.out.println("   " + field.getName()
                    + "  (оголошений у класі " + field.getDeclaringClass().getSimpleName() + ")");
        }

        System.out.println();
        System.out.println("   getDeclaredFields() — усі поля САМОГО класу Product:");
        for (Field field : productClass.getDeclaredFields()) {
            System.out.println("   " + Modifier.toString(field.getModifiers()) + " " + field.getName());
        }
        System.out.println("   Зверніть увагу: поля id з батька тут немає — воно оголошене в Entity.");

        System.out.println();

        // === 2. Повний паспорт кожного поля ===
        // Сценарій: генератор документації або ORM обходить поля і збирає метадані.
        System.out.println("2. Детально про кожне поле Product:");
        for (Field field : productClass.getDeclaredFields()) {
            if (field.isSynthetic()) {
                continue;                      // службові поля компілятора нам не цікаві
            }

            int mods = field.getModifiers();
            System.out.println("   ─ " + field.getName());
            System.out.println("      getType():        " + field.getType().getSimpleName());
            System.out.println("      getGenericType(): " + field.getGenericType().getTypeName());
            System.out.println("      модифікатори:     "
                    + (Modifier.toString(mods).isEmpty() ? "(package-private)" : Modifier.toString(mods)));
            System.out.println("      static? " + Modifier.isStatic(mods)
                    + ", final? " + Modifier.isFinal(mods)
                    + ", private? " + Modifier.isPrivate(mods));
        }

        System.out.println();

        // === 3. getType() vs getGenericType() ===
        // Через стирання типів (type erasure) getType() бачить лише "сирий" List.
        // Але оголошення List<String> лишається в метаданих класу — його дістає getGenericType().
        Field categories = productClass.getDeclaredField("categories");

        System.out.println("3. Узагальнені типи:");
        System.out.println("   getType():        " + categories.getType().getName());
        System.out.println("   getGenericType(): " + categories.getGenericType().getTypeName());
        System.out.println("   getAnnotatedType(): " + categories.getAnnotatedType().getType().getTypeName());
        System.out.println("   Саме так Jackson розуміє, що JSON-масив треба скласти у List<String>.");

        Field photos = productClass.getDeclaredField("photos");
        System.out.println("   Поле-масив photos: тип " + photos.getType().getSimpleName()
                + ", тип елемента " + photos.getType().getComponentType().getSimpleName());

        System.out.println();

        // === 4. Анотації полів ===
        // Сценарій: міні-ORM будує SQL-запит, читаючи анотації @Column.
        System.out.println("4. Читаємо анотації і будуємо SQL:");

        StringBuilder sql = new StringBuilder("CREATE TABLE " + Product.TABLE_NAME + " (\n");

        for (Field field : productClass.getDeclaredFields()) {

            Column column = field.getAnnotation(Column.class);   // null, якщо анотації немає
            if (column == null) {
                continue;                                        // поле не мапиться на колонку
            }
            sql.append("   ").append(column.name())
                    .append(' ').append(toSqlType(field.getType()))
                    .append(column.nullable() ? "" : " NOT NULL")
                    .append(",\n");
        }
        sql.setLength(sql.length() - 2);
        System.out.println(sql.append("\n);"));

        System.out.println();

        // === 5. getAnnotation() vs getAnnotationsByType() ===
        // Поле title позначене двома @Tag. Одиничний getAnnotation тут поверне null,
        // бо компілятор загорнув обидві анотації в контейнер @Tags.
        Field title = productClass.getDeclaredField("title");

        System.out.println("5. Повторювані анотації поля title:");
        System.out.println("   getAnnotation(Tag.class):        " + title.getAnnotation(Tag.class));
        System.out.print("   getAnnotationsByType(Tag.class): ");
        for (Tag tag : title.getAnnotationsByType(Tag.class)) {
            System.out.print(tag.value() + " ");
        }
        System.out.println();

        // У поля quantity анотацій немає — повертається порожній масив, а не null
        Field quantity = productClass.getDeclaredField("quantity");
        System.out.println("   у поля quantity тегів: " + quantity.getAnnotationsByType(Tag.class).length
                + " (порожній масив, не null)");

        System.out.println();

        // === 6. Пошук конкретного поля за іменем ===
        System.out.println("6. Пошук поля за іменем:");

        System.out.println("   getField(\"sku\"):          " + productClass.getField("sku").getName()
                + " — public, тому знайшлося");
        System.out.println("   getField(\"id\"):           " + productClass.getField("id").getName()
                + " — public від батька, теж знайшлося");
        System.out.println("   getDeclaredField(\"price\"): " + productClass.getDeclaredField("price").getName()
                + " — private, але оголошене тут");

        try {
            productClass.getField("price");        // private → getField його не бачить
        } catch (NoSuchFieldException e) {
            System.out.println("   getField(\"price\")         -> NoSuchFieldException (бо поле private)");
        }

        try {
            productClass.getDeclaredField("id");   // оголошене в батька, а не тут
        } catch (NoSuchFieldException e) {
            System.out.println("   getDeclaredField(\"id\")    -> NoSuchFieldException (бо оголошене в Entity)");
        }

        System.out.println();

        // === 7. Практика: як обійти ВСІ поля разом із батьківськими ===
        // Ані getFields(), ані getDeclaredFields() поодинці цього не роблять.
        // Фреймворки піднімаються ієрархією вгору циклом — ось як це виглядає.
        System.out.println("7. Усі поля разом зі спадковими (як роблять фреймворки):");

        Product product = new Product("Ноутбук", new BigDecimal("42999.00"), 7);

        Class<?> current = product.getClass();

        while (current != null && current != Object.class) {

            for (Field field : current.getDeclaredFields()) {
                if (field.isSynthetic() || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                field.setAccessible(true);
                System.out.println("   " + String.format("%-12s", field.getName())
                        + " = " + field.get(product)
                        + "   [з класу " + current.getSimpleName() + "]");
            }
            current = current.getSuperclass();     // піднімаємось до батька
        }

        System.out.println();

        // === 8. Статичні поля читаються без об'єкта ===
        // Для static-поля об'єкт не потрібен — передаємо null.
        Field tableName = productClass.getDeclaredField("TABLE_NAME");
        Field counter = productClass.getDeclaredField("instanceCount");
        counter.setAccessible(true);

        System.out.println("8. Статичні поля (об'єкт не потрібен, передаємо null):");
        System.out.println("   TABLE_NAME    = " + tableName.get(null));
        System.out.println("   instanceCount = " + counter.get(null) + " (створено товарів)");
    }

    /**
     * Примітивно зіставляє Java-тип із типом колонки БД.
     * Саме таку таблицю відповідностей має всередині будь-який ORM.
     */
    private static String toSqlType(Class<?> type) {
        if (type == String.class) {
            return "VARCHAR(255)";
        }
        if (type == int.class || type == Integer.class) {
            return "INTEGER";
        }
        if (type == BigDecimal.class) {
            return "DECIMAL(10,2)";
        }
        if (type == boolean.class) {
            return "BOOLEAN";
        }
        return "TEXT";
    }
}
