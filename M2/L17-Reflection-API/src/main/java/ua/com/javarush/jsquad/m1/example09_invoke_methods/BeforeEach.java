package ua.com.javarush.jsquad.m1.example09_invoke_methods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Метод, позначений цією анотацією, наш міні-фреймворк викликає
 * перед кожним тестом — так само, як це робить JUnit.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BeforeEach {
}
