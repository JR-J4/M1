package ua.com.javarush.jsquad.m1.example07_field_state;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Модуль 2. Рівень 17. Reflection API
 * <hr>
 * <h3>Тема: Зміна внутрішнього стану об'єкта за допомогою рефлексії</h3>
 *
 * <p>Об'єкт {@code Field} — це лише опис поля, він не зберігає значень. Щоб
 * прочитати чи записати значення, ми завжди передаємо <b>об'єкт</b>, у якого це
 * поле беремо:</p>
 *
 * <pre>
 *   field.get(об'єкт)            — прочитати значення поля в цього об'єкта
 *   field.set(об'єкт, значення)  — записати значення поля цьому об'єкту
 * </pre>
 *
 * <h4>Типізовані методи для примітивів:</h4>
 * <pre>
 *   Записати: setByte(), setShort(), setInt(), setLong(),
 *             setFloat(), setDouble(), setChar(), setBoolean(),
 *             а також set() — він приймає посилальні типи.
 *   Прочитати: getByte(), getShort(), getInt(), getLong(),
 *              getFloat(), getDouble(), getChar(), getBoolean(), get().
 * </pre>
 *
 * <h4>Приклад із лекції:</h4>
 * <pre>
 *   Cat cat = new Cat("Tom");
 *   Class&lt;? extends Cat&gt; catClass = cat.getClass();
 *   Field nameField = catClass.getField("name");
 *   nameField.set(cat, "Jerry");     // ← ОБОВ'ЯЗКОВО двома аргументами
 * </pre>
 *
 * <p><b>Увага, важлива деталь:</b> у слайді лекції записано {@code nameField.set("Jerry")}
 * — з одним аргументом. Так код не скомпілюється: сигнатура методу завжди
 * {@code set(Object obj, Object value)}. Першим аргументом іде об'єкт, у якого
 * змінюємо поле (для статичних полів там {@code null}), другим — нове значення.</p>
 *
 * <p><b>Аналогія з життя:</b> {@code Field} — це бланк "графа №5 у паспорті".
 * Сам бланк порожній: щоб дізнатися чи змінити значення, треба сказати, ЧИЙ саме
 * паспорт ви берете до рук.</p>
 *
 * <p><b>Реальне застосування:</b> так Jackson заповнює поля об'єкта значеннями з
 * JSON, а Hibernate — значеннями з рядка таблиці. Жодних сеттерів для цього не треба.</p>
 */
public class Example07_FieldState {

    public static void main(String[] args) throws Exception {

        // === 1. Приклад із лекції: міняємо ім'я кота ===
        Cat cat = new Cat("Tom");

        System.out.println("1. Приклад із лекції:");
        System.out.println("   До зміни:    " + cat);

        Class<? extends Cat> catClass = cat.getClass();
        Field nameField = catClass.getField("name");     // поле public, тому getField() працює
        nameField.set(cat, "Jerry");                     // об'єкт + нове значення

        System.out.println("   Після зміни: " + cat);
        System.out.println("   Прочитати назад: nameField.get(cat) -> " + nameField.get(cat));

        System.out.println();

        // === 2. Поле належить об'єкту, а не класу ===
        // Той самий Field можна застосувати до різних об'єктів — це просто "ключ".
        Cat murchyk = new Cat("Мурчик");
        Cat barsyk = new Cat("Барсик");

        System.out.println("2. Один Field — багато об'єктів:");
        System.out.println("   " + nameField.get(murchyk) + " і " + nameField.get(barsyk));

        nameField.set(murchyk, "Мурчик Другий");
        System.out.println("   Змінили лише першого: " + murchyk + " / " + barsyk);

        System.out.println();

        // === 3. Статичні поля: об'єкт не потрібен, передаємо null ===
        Field catCount = catClass.getField("catCount");

        System.out.println("3. Статичне поле (об'єкт не потрібен):");
        System.out.println("   Створено котів: " + catCount.get(null));

        catCount.setInt(null, 100);
        System.out.println("   Після setInt(null, 100): " + Cat.catCount);

        System.out.println();

        // === 4. Типізовані сеттери для кожного примітиву ===
        // Сценарій: завантажувач конфігурації заповнює налаштування гри.
        GameSettings settings = new GameSettings();
        Class<GameSettings> settingsClass = GameSettings.class;

        settingsClass.getField("difficulty").setByte(settings, (byte) 3);
        settingsClass.getField("maxPlayers").setShort(settings, (short) 16);
        settingsClass.getField("screenWidth").setInt(settings, 1920);
        settingsClass.getField("seed").setLong(settings, 987654321L);
        settingsClass.getField("volume").setFloat(settings, 0.75f);
        settingsClass.getField("gameSpeed").setDouble(settings, 1.5);
        settingsClass.getField("controlKey").setChar(settings, 'W');
        settingsClass.getField("fullScreen").setBoolean(settings, true);

        // Для посилальних типів — універсальний set()
        settingsClass.getField("playerName").set(settings, "Олександр");
        settingsClass.getField("highScores").set(settings, new int[]{1200, 980, 750});

        System.out.println("4. Налаштування, заповнені через типізовані сеттери:");
        System.out.println(settings);

        System.out.println();

        // === 5. Типізовані геттери ===
        System.out.println("5. Читання типізованими геттерами:");
        System.out.println("   getInt(screenWidth):     " + settingsClass.getField("screenWidth").getInt(settings));
        System.out.println("   getBoolean(fullScreen):  " + settingsClass.getField("fullScreen").getBoolean(settings));
        System.out.println("   getChar(controlKey):     " + settingsClass.getField("controlKey").getChar(settings));
        System.out.println("   get(screenWidth):        " + settingsClass.getField("screenWidth").get(settings)
                + "   (універсальний get() сам запакував int в Integer)");

        System.out.println();

        // === 6. Типові помилки ===
        System.out.println("6. Чого рефлексія не пробачає:");

        // 6.1 Не той тип значення
        try {
            settingsClass.getField("screenWidth").set(settings, "широкий");
        } catch (IllegalArgumentException e) {
            System.out.println("   set(int-полю, String) -> IllegalArgumentException");
        }

        // 6.2 Звуження типу теж заборонене: у int-поле не можна покласти long
        try {
            settingsClass.getField("screenWidth").setLong(settings, 5_000_000_000L);
        } catch (IllegalArgumentException e) {
            System.out.println("   setLong() для int-поля -> IllegalArgumentException (звуження заборонене)");
        }
        // А ось розширення працює: byte -> int це нормально
        settingsClass.getField("seed").setInt(settings, 42);
        System.out.println("   setInt() для long-поля -> " + settings.seed + " (розширення дозволене)");

        // 6.3 Забули передати об'єкт для нестатичного поля
        try {
            nameField.set(null, "Привид");
        } catch (NullPointerException e) {
            System.out.println("   set(null, ...) для НЕстатичного поля -> NullPointerException");
        }

        // 6.4 Об'єкт іншого класу
        try {
            nameField.set(settings, "Кіт?");
        } catch (IllegalArgumentException e) {
            System.out.println("   Field від Cat + об'єкт GameSettings -> IllegalArgumentException");
        }

        System.out.println();

        // === 7. Практика: універсальний завантажувач конфігурації ===
        // Сценарій: у нас є Map з налаштуваннями (уявіть, що прочитали з файлу).
        // Один метод розкладає їх по полях будь-якого об'єкта — це і є "міні-Jackson".
        Map<String, String> config = new LinkedHashMap<>();
        config.put("screenWidth", "2560");
        config.put("fullScreen", "false");
        config.put("volume", "0.35");
        config.put("playerName", "Марта");
        config.put("controlKey", "S");

        System.out.println("7. Заповнюємо об'єкт значеннями з конфігу:");
        GameSettings loaded = new GameSettings();
        bind(loaded, config);
        System.out.println(loaded);
    }

    /**
     * Розкладає рядкові значення з мапи по полях об'єкта, орієнтуючись на тип поля.
     *
     * <p>Саме так влаштовані {@code @Value} у Spring та десеріалізація в Jackson:
     * знайти поле за іменем → перетворити рядок на потрібний тип → записати.</p>
     */
    private static void bind(Object target, Map<String, String> values) throws Exception {
        Class<?> type = target.getClass();

        for (Map.Entry<String, String> entry : values.entrySet()) {
            Field field;
            try {
                field = type.getDeclaredField(entry.getKey());
            } catch (NoSuchFieldException e) {
                System.out.println("   [пропущено] поля '" + entry.getKey() + "' у класі немає");
                continue;
            }

            field.setAccessible(true);
            String raw = entry.getValue();
            Class<?> fieldType = field.getType();

            // Перетворюємо рядок на тип поля — тип ми дізнались під час виконання
            if (fieldType == int.class) {
                field.setInt(target, Integer.parseInt(raw));
            } else if (fieldType == long.class) {
                field.setLong(target, Long.parseLong(raw));
            } else if (fieldType == double.class) {
                field.setDouble(target, Double.parseDouble(raw));
            } else if (fieldType == float.class) {
                field.setFloat(target, Float.parseFloat(raw));
            } else if (fieldType == boolean.class) {
                field.setBoolean(target, Boolean.parseBoolean(raw));
            } else if (fieldType == char.class) {
                field.setChar(target, raw.charAt(0));
            } else if (fieldType == String.class) {
                field.set(target, raw);
            } else {
                System.out.println("   [пропущено] тип " + fieldType.getSimpleName() + " не підтримується");
                continue;
            }
            System.out.println("   " + entry.getKey() + " <- \"" + raw + "\" (як " + fieldType.getSimpleName() + ")");
        }
    }
}
