package ua.com.javarush.jsquad.m1;

/**
 * Лекція 21: Винятки — Checked-винятки та throws
 *
 * Checked-винятки — це винятки, які компілятор ЗОБОВ'ЯЗУЄ обробити.
 * Якщо метод може викинути checked-виняток, є два варіанти:
 *   1. Перехопити його в try-catch
 *   2. Оголосити в сигнатурі методу через throws
 *
 * Синтаксис:
 *   тип метод(параметри) throws ТипВинятку1, ТипВинятку2 {
 *       // код, що може викинути виняток
 *   }
 *
 * Недоліки checked-винятків:
 *   - Якщо додати checked-виняток до популярного методу — потрібно змінити
 *     ВСІ методи, що його викликають (ланцюжок throws)
 *   - Код стає захаращеним throws-оголошеннями
 *   - 95% сучасних фреймворків використовують unchecked-винятки
 *
 * Аналогія з життя: checked-виняток — як обовʼязкова страховка для подорожі.
 * Компілятор (митниця) не пустить тебе далі, поки не покажеш страховку (catch)
 * або не передаси відповідальність комусь іншому (throws).
 *
 * Реальне застосування: робота з файлами (IOException), мережею (SocketException),
 * базою даних (SQLException), рефлексією (ReflectiveOperationException).
 */
public class Example06_CheckedExceptions {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Виклик методу з throws — перехоплення через catch
        // ============================================================
        System.out.println("=== Блок 1: throws і перехоплення у виклику ===");

        // Сценарій: читання конфігурації — файл може не існувати
        String[] fileNames = {"config.yaml", "", null, "settings.json"};


        for (String file : fileNames) {
            try {
                String content = readConfig(file);
                System.out.println("  '" + file + "' → " + content);
            }
            catch (Exception e) {
                System.out.println("  '" + file + "' → Помилка: " + e.getMessage());
            }
        }

        System.out.println();

        // ============================================================
        //   Блок 2: Ланцюжок throws — прокидання вгору
        // ============================================================
        System.out.println("=== Блок 2: Ланцюжок throws ===");

        // Метод main → loadSettings → readConfig
        // Якщо readConfig кидає checked-виняток, loadSettings має або
        // зловити його, або додати throws у свою сигнатуру

        try {
            loadSettings("app.properties");
            System.out.println("Налаштування завантажені!");
        }
        catch (Exception e) {
            System.out.println("Не вдалося завантажити: " + e.getMessage());
        }

        try {
            loadSettings("");
            System.out.println("Налаштування завантажені!");
        }
        catch (Exception e) {
            System.out.println("Не вдалося завантажити: " + e.getMessage());
        }

        System.out.println();

        // ============================================================
        //   Блок 3: Декілька checked-винятків у throws
        // ============================================================
        System.out.println("=== Блок 3: Кілька винятків у throws ===");

        // Метод може оголошувати кілька checked-винятків через кому
        try {
            connectToDatabase("localhost", 5432, "admin");
            System.out.println("Підключення успішне!");
        }
        catch (Exception e) {
            System.out.println("Помилка підключення: " + e.getClass().getSimpleName()
                    + " — " + e.getMessage());
        }

        try {
            connectToDatabase("", 5432, "admin");
        }
        catch (Exception e) {
            System.out.println("Помилка підключення: " + e.getClass().getSimpleName()
                    + " — " + e.getMessage());
        }

        System.out.println();

        // ============================================================
        //   Блок 4: Недоліки checked-винятків
        // ============================================================
        System.out.println("=== Блок 4: Проблема ланцюжка throws ===");

        System.out.println("Уяви: метод readConfig() додав checked-виняток.");
        System.out.println("Тепер ВСІ методи, що викликають readConfig(), мають:");
        System.out.println("  1. Додати try-catch, АБО");
        System.out.println("  2. Додати throws у свою сигнатуру");
        System.out.println();
        System.out.println("  readConfig() throws Exception     ← тут виникає");
        System.out.println("  loadSettings() throws Exception   ← прокидається");
        System.out.println("  initApp() throws Exception        ← прокидається");
        System.out.println("  main()                            ← тут ловимо (або теж throws)");
        System.out.println();
        System.out.println("Тому 95% сучасних фреймворків (Spring, Hibernate)");
        System.out.println("використовують RuntimeException (unchecked) замість checked.");
    }

    // --- Допоміжні методи ---

    /**
     * Читання конфігурації — кидає checked Exception, якщо файл не знайдено.
     * Кожен, хто викликає цей метод, ЗОБОВ'ЯЗАНИЙ обробити виняток!
     */
    static String readConfig(String fileName) throws Exception {
        if (fileName == null) {
            throw new Exception("Імʼя файлу не може бути null");
        }
        if (fileName.isEmpty()) {
            throw new Exception("Імʼя файлу не може бути порожнім");
        }
        return "вміст-" + fileName; // імітація читання
    }

    /**
     * Завантаження налаштувань — прокидає виняток з readConfig вгору.
     * Не обробляє сама, а додає throws у сигнатуру.
     */
    static void loadSettings(String path) throws Exception {
        // throws «прокидає» виняток з readConfig до того, хто викликав loadSettings
        String config = readConfig(path);
        System.out.println("  Прочитано: " + config);
    }

    /**
     * Підключення до БД — може кидати різні checked-винятки.
     */
    static void connectToDatabase(String host, int port, String user) throws Exception {
        if (host == null || host.isEmpty()) {
            throw new Exception("Хост не може бути порожнім");
        }
        if (port <= 0 || port > 65535) {
            throw new Exception("Некоректний порт: " + port);
        }
        // Імітація успішного підключення
        System.out.println("  Підключено до " + host + ":" + port + " як " + user);
    }
}
