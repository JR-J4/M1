package ua.com.javarush.jsquad.m1.example03_fields;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Контейнер для повторюваної анотації {@link Tag}.
 * Компілятор сам загортає кілька {@code @Tag} у цей контейнер.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Tags {

    Tag[] value();
}
