package ua.com.javarush.jsquad.m1.example06_anonymous_details;

/**
 * Слухач натискання кнопки.
 *
 * <p>Один абстрактний метод → це функціональний інтерфейс, тому його можна
 * реалізувати і анонімним класом, і лямбдою. На цьому й будується порівняння
 * в прикладі 06.</p>
 */
public interface ClickListener {
    void onClick(String buttonTitle);
}
