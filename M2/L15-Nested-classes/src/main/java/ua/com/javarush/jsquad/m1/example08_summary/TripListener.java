package ua.com.javarush.jsquad.m1.example08_summary;

/**
 * Слухач подій поїздки. Реалізовуватимемо його анонімним класом
 * прямо в місці підписки.
 */
public interface TripListener {
    void onTripFinished(String description, double cost);
}
