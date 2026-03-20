package ua.com.javarush.jsquad.m1;

/**
 * Лекція 9: Методи — Модифікатори доступу
 *
 * Доступ до методів регулюють модифікатори: {@code public}, {@code protected}, {@code private} або відсутність (package-private).
 * Вони визначають, хто може викликати метод.
 *
 * Аналогія: двері офісу можуть бути відкритими (public), з бейджем співробітника (protected), чи тільки для охорони (private).
 * Реальне застосування: публічні API доступні клієнтам, захищені — лише підкласам, приватні — для внутрішніх технічних дій.
 */
public class Example07_AccessModifiers {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: public — відкрито для всіх
        // ============================================================
        System.out.println("=== public метод ===");
        CustomerPortal portal = new CustomerPortal();
        portal.exportInvoice();

        System.out.println();

        // ============================================================
        //   Приклад 2: protected — доступний підкласам
        // ============================================================
        System.out.println("=== protected метод ===");
        RiskDashboard dashboard = new RiskDashboard();
        dashboard.runDailyChecks();

        System.out.println();

        // ============================================================
        //   Приклад 3: private — лише для внутрішніх задач
        // ============================================================
        System.out.println("=== private метод ===");
        PaymentGateway gateway = new PaymentGateway();
        gateway.processTransaction();
    }

    static class CustomerPortal {
        public void exportInvoice() {
            System.out.println("Передаємо клієнту PDF рахунку — метод public викликається з будь-якого класу.");
        }
    }

    static class BaseWorkflow {
        protected void notifySupervisor(String message) {
            System.out.println("Сповіщення супервайзеру: " + message);
        }
    }

    static class RiskDashboard extends BaseWorkflow {
        public void runDailyChecks() {
            System.out.println("Перевіряємо платіжні ліміти...");
            notifySupervisor("Виявлено підозрілу активність"); // доступний, бо клас наслідується
        }
    }

    static class PaymentGateway {
        public void processTransaction() {
            System.out.println("Обробляємо транзакцію клієнта");
            logSensitiveData(); // доступний лише тут
        }

        private void logSensitiveData() {
            System.out.println(" -> Ведемо закритий технічний журнал (private)");
        }
    }
}
