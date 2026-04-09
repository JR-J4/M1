package ua.com.javarush.jsquad.m1.example06_initialization_order;

/**
 * Клас Application — демонстрація порядку ініціалізації в Java.
 *
 * <p>Порядок ініціалізації (з лекції):</p>
 * <ol>
 *   <li>Статичні блоки та поля зовнішнього класу</li>
 *   <li>Статичні блоки та поля внутрішнього класу</li>
 *   <li>Блоки ініціалізації та нестатичні поля зовнішнього класу</li>
 *   <li>Конструктор зовнішнього класу</li>
 *   <li>Блоки ініціалізації та нестатичні поля внутрішнього класу</li>
 *   <li>Конструктор внутрішнього класу</li>
 * </ol>
 */
public class Application {

    // --- Статичні елементи зовнішнього класу (крок 1) ---

    static String appName = initStaticField();

    static String test;

    static {
        test = "Asd";

        System.out.println("  1. [ЗОВНІШНІЙ] Статичний блок ініціалізації");
    }

    static {
        System.out.println("  1. [ЗОВНІШНІЙ] Статичний блок ініціалізації");
    }

    private static String initStaticField() {
        System.out.println("  1. [ЗОВНІШНІЙ] Статичне поле appName ініціалізовано");
        return "MyApp";
    }

    // --- Нестатичні елементи зовнішнього класу (крок 3) ---

    private String version = initInstanceField();

    {
        System.out.println("  3. [ЗОВНІШНІЙ] Блок ініціалізації екземпляра");
    }

    private String initInstanceField() {
        System.out.println("  3. [ЗОВНІШНІЙ] Нестатичне поле version ініціалізовано");
        return "1.0";
    }

    // --- Конструктор зовнішнього класу (крок 4) ---

    public Application() {
        System.out.println("  4. [ЗОВНІШНІЙ] Конструктор Application()");
    }

    // ──────────────────────────────────────────────────
    // Внутрішній клас Module
    // ──────────────────────────────────────────────────
    public class Module {

        // Нестатичні елементи внутрішнього класу (крок 5)

        private String moduleName = initModuleField();

        {
            System.out.println("  5. [ВНУТРІШНІЙ] Блок ініціалізації екземпляра Module");
        }

        private String initModuleField() {
            System.out.println("  5. [ВНУТРІШНІЙ] Нестатичне поле moduleName ініціалізовано");
            return "CoreModule";
        }

        // Конструктор внутрішнього класу (крок 6)
        public Module() {
            System.out.println("  6. [ВНУТРІШНІЙ] Конструктор Module()");
        }

        public void showInfo() {
            System.out.println("  Додаток: " + appName + " v" + version + ", модуль: " + moduleName);
        }
    }
}
