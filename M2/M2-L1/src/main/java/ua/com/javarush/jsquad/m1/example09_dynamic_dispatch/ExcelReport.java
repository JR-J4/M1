package ua.com.javarush.jsquad.m1.example09_dynamic_dispatch;

/**
 * Excel-звіт зі своєю реалізацією {@code generate()}.
 */
public class ExcelReport extends Report {

    public ExcelReport(String title) {
        super(title);
    }

    @Override
    public void generate() {
        System.out.println("  📊 Excel: \"" + title + "\" згенеровано (з формулами).");
    }
}
