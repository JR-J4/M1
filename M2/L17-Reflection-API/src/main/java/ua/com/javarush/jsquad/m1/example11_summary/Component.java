package ua.com.javarush.jsquad.m1.example11_summary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Позначає клас, об'єкт якого має створити наш міні-контейнер.
 * Спрощений аналог {@code @Component} зі Spring.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
}
