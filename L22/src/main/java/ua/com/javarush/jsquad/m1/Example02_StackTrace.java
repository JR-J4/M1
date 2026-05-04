package ua.com.javarush.jsquad.m1;

/**
 * Лекція 22: Винятки 2 — Stack Trace
 *
 * Stack trace — це список методів, починаючи з поточного і до main(),
 * який показує ланцюжок викликів. Кожен елемент — StackTraceElement.
 *
 * Отримати stack trace можна двома способами:
 *   1) Thread.currentThread().getStackTrace() — поточний стек потоку
 *   2) exception.getStackTrace() — стек на момент створення винятку
 *
 * Аналогія з життя: уяви слідчого, який відновлює хронологію подій.
 * Stack trace — це «журнал подій»: хто кого викликав, в якому порядку,
 * і де саме сталася проблема. Як GPS-трекер для коду.
 *
 * Реальне застосування: діагностика помилок у продакшені, логування,
 * побудова звітів про помилки, дебагінг складних ланцюжків викликів.
 */
public class Example02_StackTrace {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Отримання stack trace поточного потоку
        // ============================================================
        System.out.println("=== Блок 1: Thread.currentThread().getStackTrace() ===");

        // Сценарій: хочемо дізнатися, хто викликав поточний метод
        StackTraceElement[] methods = Thread.currentThread().getStackTrace();

        System.out.println("Поточний стек викликів:");
        for (StackTraceElement element : methods) {
            System.out.println("  " + element.getClassName() + "." + element.getMethodName()
                    + " (рядок " + element.getLineNumber() + ")");
        }

        System.out.println();

        // ============================================================
        //   Блок 2: Stack trace з вкладених методів
        // ============================================================
        System.out.println("=== Блок 2: Ланцюжок викликів ===");

        // Сценарій: система обробки замовлень — main → processOrder → validateItem → checkStock
        processOrder();

        System.out.println();

        // ============================================================
        //   Блок 3: StackTraceElement — детальна інформація
        // ============================================================
        System.out.println("=== Блок 3: Методи StackTraceElement ===");

        // Сценарій: логування інформації про місце виклику
        logCurrentLocation();

        System.out.println();

        // ============================================================
        //   Блок 4: Stack trace з винятку
        // ============================================================
        System.out.println("=== Блок 4: exception.getStackTrace() ===");

        // Сценарій: перехоплюємо виняток і аналізуємо його стек
        try {
            levelA();
        } catch (Exception e) {
            System.out.println("Виняток: " + e.getMessage());
            System.out.println("Стек винятку:");

            StackTraceElement[] exceptionStack = e.getStackTrace();
            for (StackTraceElement element : exceptionStack) {
                System.out.println("  → " + element.getClassName()
                        + "." + element.getMethodName()
                        + " (" + element.getFileName() + ":" + element.getLineNumber() + ")");
            }
        }

        System.out.println();
        System.out.println("Stack trace допомагає знайти ТОЧНЕ місце помилки в коді.");
        System.out.println("getStackTrace() повертає масив StackTraceElement[].");
    }

    // --- Блок 2: Ланцюжок викликів ---

    static void processOrder() {
        System.out.println("processOrder() викликає validateItem()...");
        validateItem();
    }

    static void validateItem() {
        System.out.println("validateItem() викликає checkStock()...");
        checkStock();
    }

    static void checkStock() {
        System.out.println("checkStock() — перевіряємо стек:");
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        // Пропускаємо getStackTrace() і показуємо наші методи
        for (StackTraceElement element : stack) {
            if (element.getClassName().contains("Example02")) {
                System.out.println("  ↑ " + element.getMethodName() + " (рядок " + element.getLineNumber() + ")");
            }
        }
    }

    // --- Блок 3: Детальна інформація ---

    static void logCurrentLocation() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        // Елемент [0] — getStackTrace(), [1] — наш метод, [2] — хто нас викликав
        if (stack.length >= 3) {
            StackTraceElement caller = stack[2]; // хто викликав logCurrentLocation()

            System.out.println("Інформація про виклик:");
            System.out.println("  getClassName()     → " + caller.getClassName());
            System.out.println("  getMethodName()    → " + caller.getMethodName());
            System.out.println("  getFileName()      → " + caller.getFileName());
            System.out.println("  getLineNumber()    → " + caller.getLineNumber());
            System.out.println("  getModuleName()    → " + caller.getModuleName());
            System.out.println("  getModuleVersion() → " + caller.getModuleVersion());
        }
    }

    // --- Блок 4: Вкладені виклики для винятку ---

    static void levelA() {
        levelB();
    }

    static void levelB() {
        levelC();
    }

    static void levelC() {
        throw new RuntimeException("Помилка на рівні C!");
    }
}
