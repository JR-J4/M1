package ua.com.javarush.jsquad.m1.example11_summary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Позначає поле, у яке контейнер має підставити інший компонент.
 * Спрощений аналог {@code @Autowired} зі Spring.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Inject {
}
