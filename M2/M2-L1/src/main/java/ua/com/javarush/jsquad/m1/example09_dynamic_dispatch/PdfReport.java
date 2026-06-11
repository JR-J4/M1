package ua.com.javarush.jsquad.m1.example09_dynamic_dispatch;

/**
 * PDF-звіт. Має ще й власний метод {@code addWatermark()},
 * якого немає в батьківському {@link Report}.
 */
public class PdfReport extends Report {

    public PdfReport(String title) {
        super(title);
    }

    @Override
    public void generate() {
        System.out.println("  📄 PDF: \"" + title + "\" згенеровано (зі стисненням).");
    }

    // Метод, який існує ЛИШЕ в типі PdfReport
    public void addWatermark() {
        System.out.println("  💧 Додаю водяний знак на PDF.");
    }
}
