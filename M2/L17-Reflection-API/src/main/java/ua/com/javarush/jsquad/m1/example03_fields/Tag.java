package ua.com.javarush.jsquad.m1.example03_fields;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Повторювана анотація — одне поле можна позначити кількома тегами.
 * Потрібна, щоб показати метод {@code getAnnotationsByType()}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(Tags.class)   // дозволяє писати @Tag двічі поспіль
public @interface Tag {

    String value();
}
