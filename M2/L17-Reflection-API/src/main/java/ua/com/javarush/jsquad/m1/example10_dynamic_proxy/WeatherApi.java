package ua.com.javarush.jsquad.m1.example10_dynamic_proxy;

/**
 * Інтерфейс зовнішнього сервісу погоди.
 *
 * <p>Реалізації в проєкті НЕМАЄ — і це навмисно. У прикладі 10 ми створимо
 * робочий об'єкт цього інтерфейсу прямо під час виконання, без жодного класу.
 * Саме так працюють бібліотеки-заглушки на кшталт Mockito.</p>
 */
public interface WeatherApi {

    int getTemperature(String city);

    String getForecast(String city);
}
