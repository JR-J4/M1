package ua.com.javarush.jsquad.m1.example03_static_nested;

import java.util.ArrayList;
import java.util.List;

/**
 * Зовнішній клас {@code Pizza} із <b>вкладеним</b> (статичним внутрішнім)
 * класом {@code Builder} — класичний патерн "Будівельник".
 *
 * <p>{@code Builder} не має жодного стосунку до конкретної піци: він її тільки
 * збирає. Посилання на об'єкт {@code Pizza} йому не потрібне — тому {@code static}.</p>
 */
public class Pizza {

    private static final String BAKERY = "Піцерія «Дві печі»";   // private static зовнішнього класу
    private static int bakedCount = 0;

    private final Size size;
    private final List<String> toppings;
    private final boolean thinDough;

    /** Конструктор приватний: піцу можна отримати ТІЛЬКИ через Builder. */
    private Pizza(Builder builder) {
        this.size = builder.size;
        this.toppings = builder.toppings;
        this.thinDough = builder.thinDough;
    }

    /**
     * Enum, оголошений усередині класу, — теж вкладений тип
     * (слово static писати не треба, воно мається на увазі).
     */
    public enum Size {
        SMALL(25), MEDIUM(30), LARGE(40);

        private final int cm;

        Size(int cm) {
            this.cm = cm;
        }

        public int getCm() {
            return cm;
        }
    }

    /**
     * Вкладений (статичний внутрішній) клас.
     *
     * <p>Особливості вкладених класів:</p>
     * <ul>
     *   <li>створюється без об'єкта зовнішнього класу: {@code new Pizza.Builder(...)};</li>
     *   <li>має доступ до {@code private static} полів і методів зовнішнього класу
     *       ({@code BAKERY}, {@code bakedCount}, {@code log()});</li>
     *   <li>має доступ до приватного конструктора {@code Pizza}.</li>
     * </ul>
     */
    public static class Builder {

        private  Size size;
        private final List<String> toppings = new ArrayList<>();
        private boolean thinDough = false;

        public Builder size(Size size){
            this.size = size;
            return this;
        }

        public Builder topping(String topping) {
            toppings.add(topping);
            return this;
        }

        public Builder thinDough() {
            this.thinDough = true;
            return this;
        }

        public Pizza bake() {
            bakedCount++;                       // private static поле зовнішнього класу
            log("випікаємо піцу №" + bakedCount);
            return new Pizza(this);             // private конструктор зовнішнього класу
        }
    }

    private static void log(String message) {   // private static метод зовнішнього класу
        System.out.println("   [" + BAKERY + "] " + message);
    }

    public static int getBakedCount() {
        return bakedCount;
    }

    @Override
    public String toString() {
        return "Піца " + size + " (" + size.getCm() + " см), тісто "
                + (thinDough ? "тонке" : "звичайне") + ", начинка: " + toppings;
    }
}
