package ua.com.javarush.jsquad.m1.example11_summary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Позначає поле, значення якого треба взяти з налаштувань за ключем.
 * Спрощений аналог {@code @Value("${...}")} зі Spring.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Value {

    /** Ключ у файлі налаштувань. */
    String value();
}
