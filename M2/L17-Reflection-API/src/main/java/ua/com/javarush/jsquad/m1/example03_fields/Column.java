package ua.com.javarush.jsquad.m1.example03_fields;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Власна анотація — позначає, у яку колонку таблиці лягає поле.
 *
 * <p><b>Найважливіше:</b> {@code RetentionPolicy.RUNTIME}. Без нього анотація
 * зникне після компіляції і рефлексія її не побачить. Саме так позначені
 * анотації Spring, JUnit та Hibernate.</p>
 */
@Retention(RetentionPolicy.RUNTIME)   // анотація доступна під час виконання
@Target(ElementType.FIELD)            // її можна вішати лише на поля
public @interface Column {

    /** Ім'я колонки в базі даних. */
    String name();

    /** Чи може колонка бути порожньою. */
    boolean nullable() default true;
}
