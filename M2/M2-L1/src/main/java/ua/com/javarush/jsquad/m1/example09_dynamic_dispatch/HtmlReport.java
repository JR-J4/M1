package ua.com.javarush.jsquad.m1.example09_dynamic_dispatch;

/**
 * HTML-звіт зі своєю реалізацією {@code generate()}.
 */
public class HtmlReport extends Report {

    public HtmlReport(String title) {
        super(title);
    }

    @Override
    public void generate() {
        System.out.println("  🌐 HTML: \"" + title + "\" згенеровано (для браузера).");
    }
}
