package ua.com.javarush.jsquad.m1.example03_fields;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Товар інтернет-магазину — навмисно з полями всіх "сортів":
 * різні модифікатори, примітиви, узагальнені типи, масиви та анотації.
 */
public class Product extends Entity {

    /** Константа: public static final. */
    public static final String TABLE_NAME = "products";

    /** Статичне змінне поле — спільний лічильник для всіх товарів. */
    private static int instanceCount;

    @Column(name = "title", nullable = false)
    @Tag("пошук")
    @Tag("вітрина")
    private String title;

    @Column(name = "price")
    private BigDecimal price;

    /** Примітивне поле без анотацій. */
    private int quantity;

    /** Узагальнене поле — тут стане в пригоді getGenericType(). */
    private List<String> categories;

    /** Поле-масив. */
    protected String[] photos;

    /** Package-private поле (без модифікатора доступу). */
    boolean archived;

    /** public-поле самого класу — його побачать обидва методи. */
    public String sku;

    public Product(String title, BigDecimal price, int quantity) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.sku = "SKU-" + (++instanceCount);
        this.categories = List.of("Загальна");
        this.photos = new String[]{"front.jpg"};
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", categories=" + categories +
                ", photos=" + Arrays.toString(photos) +
                ", archived=" + archived +
                ", sku='" + sku + '\'' +
                '}';
    }
}
