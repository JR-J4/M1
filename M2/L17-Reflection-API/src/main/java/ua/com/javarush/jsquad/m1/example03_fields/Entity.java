package ua.com.javarush.jsquad.m1.example03_fields;

/**
 * Батьківський клас із public-полем.
 * Потрібен, щоб показати різницю: {@code getFields()} бачить успадковані
 * public-поля, а {@code getDeclaredFields()} — ні.
 */
public class Entity {

    /** public-поле батька — його побачить getFields() у нащадка. */
    public long id;

    /** private-поле батька — його не побачить жоден з двох методів у нащадка. */
    private String createdBy = "system";

    public String getCreatedBy() {
        return createdBy;
    }
}
