package ua.com.javarush.jsquad.m1.example04_class_loading;

/**
 * Сервіс звітів.
 * Поки до нього ніхто не звертається — JVM його навіть не завантажує.
 */
public class ReportService {

    static {
        System.out.println("  [ReportService] static-блок: шаблони звітів завантажено");
    }

    static void buildReport() {
        System.out.println("  Звіт за день сформовано");
    }
}
