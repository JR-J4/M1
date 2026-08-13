package ua.com.javarush.jsquad.m1.example09_invoke_methods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Власна анотація {@code @Test} — спрощена копія тієї, що є в JUnit.
 * Позначений нею метод має запустити наш "міні-фреймворк".
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {

    /** Опис тесту для звіту. */
    String value() default "";
}
