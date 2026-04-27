package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;
import java.util.List;

/**
 * Лекція 20: Singleton, enum, switch — Шаблон Singleton (Одинак)
 *
 * Singleton (одинак) — один із найпростіших шаблонів (патернів) проєктування.
 * Він гарантує, що у класу буде лише один екземпляр, і надає глобальну
 * точку доступу до нього.
 *
 * Дві ключові особливості:
 *   1. Приватний конструктор — ніхто не може створити new Singleton() ззовні
 *   2. Публічний статичний метод getInstance() — єдиний спосіб отримати екземпляр
 *
 * Проста реалізація (eager — завчасна ініціалізація):
 *   public class Singleton {
 *       private static final Singleton INSTANCE = new Singleton();
 *       private Singleton() { }
 *       public static Singleton getInstance() { return INSTANCE; }
 *   }
 *
 * Аналогія з життя: президент країни — він може бути тільки один у кожний
 * момент часу. Не можна створити «нового президента» самостійно; доступ
 * до чинного — лише через офіційний канал (getInstance).
 *
 * Реальне застосування: логер (Logger), пул зʼєднань до БД, менеджер
 * конфігурації, налаштування застосунку, кеш.
 */
public class Example04_Singleton {

    // ================================================================
    // Клас БЕЗ Singleton — для демонстрації проблеми
    // ================================================================
    static class NaiveLogger {
        private final List<String> logs = new ArrayList<>();

        // Публічний конструктор — будь-хто може створити новий логер
        public NaiveLogger() { }

        public void log(String message) {
            logs.add(message);
        }

        public List<String> getLogs() {
            return logs;
        }
    }

    // ================================================================
    // Клас-Singleton — Eager (завчасна) ініціалізація
    // ================================================================
    static class AppLogger {
        // 1. Створюємо єдиний екземпляр відразу при завантаженні класу
        private static final AppLogger INSTANCE = new AppLogger();

        private final List<String> logs = new ArrayList<>();

        // 2. Приватний конструктор — заборона створення ззовні
        private AppLogger() {
        }

        // 3. Глобальна точка доступу — завжди повертає той самий обʼєкт
        public static AppLogger getInstance() {
            return INSTANCE;
        }

        public void log(String message) {
            logs.add("[LOG] " + message);
        }

        public void printLogs() {
            for (String entry : logs) {
                System.out.println("  " + entry);
            }
        }

        public int getLogCount() {
            return logs.size();
        }
    }

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Проблема — чому потрібен Singleton
        // ============================================================
        System.out.println("=== Блок 1: Проблема без Singleton ===");

        // Створюємо два логери — вони РІЗНІ обʼєкти!
        NaiveLogger logger1 = new NaiveLogger();
        NaiveLogger logger2 = new NaiveLogger();

        logger1.log("Старт програми");
        logger2.log("Помилка зʼєднання");

        System.out.println("logger1 == logger2? " + (logger1 == logger2)); // false
        System.out.println("Логи logger1: " + logger1.getLogs()); // [Старт програми]
        System.out.println("Логи logger2: " + logger2.getLogs()); // [Помилка зʼєднання]
        System.out.println("Проблема: логи розкидані по різних обʼєктах!");
        System.out.println();

        // ============================================================
        //   Блок 2: Eager Singleton — єдиний екземпляр
        // ============================================================
        System.out.println("=== Блок 2: Singleton — єдиний логер ===");

        // Отримуємо екземпляр через getInstance()
        AppLogger singletonA = AppLogger.getInstance();
        AppLogger singletonB = AppLogger.getInstance();

        // Перевіряємо — це ТОЙ САМИЙ обʼєкт
        System.out.println("singletonA == singletonB? " + (singletonA == singletonB)); // true
        System.out.println("Це один і той самий обʼєкт!");
        System.out.println();

        // ============================================================
        //   Блок 3: Використання Singleton
        // ============================================================
        System.out.println("=== Блок 3: Використання Singleton ===");

        // Логуємо з різних «місць» програми — все потрапляє в один логер
        AppLogger.getInstance().log("Застосунок запущено");
        AppLogger.getInstance().log("Користувач увійшов: admin");
        AppLogger.getInstance().log("Завантажено 150 записів");
        AppLogger.getInstance().log("Користувач вийшов: admin");

        System.out.println("Усі логи в одному місці:");
        AppLogger.getInstance().printLogs();
        System.out.println("Кількість записів: " + AppLogger.getInstance().getLogCount());
        System.out.println();

        // ============================================================
        //   Блок 4: Захист — приватний конструктор
        // ============================================================
        System.out.println("=== Блок 4: Захист — приватний конструктор ===");

        // Спроба створити новий екземпляр:
        // AppLogger another = new AppLogger();  // НЕ СКОМПІЛЮЄТЬСЯ!
        // Помилка: 'AppLogger()' has private access in 'AppLogger'

        System.out.println("new AppLogger() — НЕ скомпілюється (private конструктор)");
        System.out.println();

        System.out.println("Два стовпи Singleton:");
        System.out.println("  1. private конструктор → ніхто не створить new обʼєкт");
        System.out.println("  2. public static getInstance() → єдина точка доступу");
        System.out.println();
        System.out.println("Singleton гарантує: один клас = один екземпляр.");
    }
}
