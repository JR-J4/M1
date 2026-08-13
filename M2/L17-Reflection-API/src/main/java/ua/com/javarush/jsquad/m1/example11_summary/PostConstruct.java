package ua.com.javarush.jsquad.m1.example11_summary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Метод, який контейнер викличе після того, як усі поля об'єкта заповнені.
 * Аналог справжнього {@code @PostConstruct} з Jakarta EE / Spring.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PostConstruct {
}
