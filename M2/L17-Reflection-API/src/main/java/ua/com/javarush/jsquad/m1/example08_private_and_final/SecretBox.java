package ua.com.javarush.jsquad.m1.example08_private_and_final;

/**
 * Скринька з полями різного ступеня "захищеності" —
 * від звичайного private до static final.
 */
public class SecretBox {

    /** static final — константа рівня класу. Змінити її рефлексією не вийде. */
    public static final int MAX_ATTEMPTS = 3;

    /**
     * final-поле, ініціалізоване <b>константним виразом</b>.
     * Компілятор підставляє його значення прямо в місця використання —
     * саме тому в прикладі 08 виникає "магія" з незмінним getLabel().
     */
    private final String label = "Оригінальна етикетка";

    /** final-поле, значення якого відоме лише під час виконання. */
    private final String createdBy;

    /** Звичайне приватне поле. */
    private String secret;

    private int attempts;

    public SecretBox(String createdBy, String secret) {
        this.createdBy = createdBy;
        this.secret = secret;
    }

    /** Компілятор перетворив тіло цього методу на {@code return "Оригінальна етикетка";} */
    public String getLabel() {
        return label;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getSecret() {
        return secret;
    }

    public int getAttempts() {
        return attempts;
    }
}
