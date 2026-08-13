package ua.com.javarush.jsquad.m1.example04_methods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Позначає метод, виклики якого треба логувати.
 * Аналог {@code @Transactional} чи {@code @Cacheable} зі Spring.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Loggable {

    /** Рівень логування. */
    String level() default "INFO";
}
