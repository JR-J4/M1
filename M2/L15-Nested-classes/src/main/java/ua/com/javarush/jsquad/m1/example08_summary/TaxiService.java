package ua.com.javarush.jsquad.m1.example08_summary;

import java.util.ArrayList;
import java.util.List;

/**
 * Служба таксі — усі три види класів з лекції в одному робочому класі.
 *
 * <ul>
 *   <li>{@code Driver} — <b>вкладений</b> (static): водій існує сам по собі,
 *       його можна створити до появи служби й передати в іншу службу;</li>
 *   <li>{@code Trip} — <b>внутрішній</b>: поїздка рахує вартість за тарифом
 *       СВОЄЇ служби, тому їй потрібне посилання на неї;</li>
 *   <li>слухач {@code TripListener} — <b>анонімний</b> клас, який створює
 *       той, хто підписується на подію.</li>
 * </ul>
 */
public class TaxiService {

    private static final String COMPANY = "Швидке таксі";   // private static
    private static int totalTrips = 0;

    private final String city;
    private final double pricePerKm;
    private final List<Driver> drivers = new ArrayList<>();
    private TripListener listener;

    public TaxiService(String city, double pricePerKm) {
        this.city = city;
        this.pricePerKm = pricePerKm;
    }

    /**
     * ВКЛАДЕНИЙ клас: посилання на службу не потрібне.
     * Створюється як {@code new TaxiService.Driver(...)}.
     */
    public static class Driver {

        private final String name;
        private final String car;
        private final double rating;

        public Driver(String name, String car, double rating) {
            this.name = name;
            this.car = car;
            this.rating = rating;
            // Доступ до private static зовнішнього класу — можна:
            System.out.println("   [" + COMPANY + "] зареєстровано водія " + name);
        }

        public String getName() {
            return name;
        }

        public double getRating() {
            return rating;
        }

        @Override
        public String toString() {
            return name + " (" + car + ", рейтинг " + rating + ")";
        }
    }

    /**
     * ВНУТРІШНІЙ клас: бачить pricePerKm, city і список водіїв своєї служби.
     */
    public class Trip {

        private final Driver driver;
        private final double km;

        private Trip(Driver driver, double km) {
            this.driver = driver;
            this.km = km;
        }

        /** Тариф беремо із зовнішнього об'єкта — у кожного міста він свій. */
        public double cost() {
            return km * pricePerKm;
        }

        public void finish() {
            totalTrips++;                                  // private static зовнішнього класу
            String description = driver.getName() + " довіз(ла) " + km + " км по м. " + city;
            System.out.println("   ✔ " + description + " — " + String.format("%.2f", cost()) + " грн");

            if (listener != null) {                        // поле зовнішнього об'єкта
                listener.onTripFinished(description, cost());
            }
        }
    }

    /** Фабрика поїздок: тільки служба знає, кого призначити. */
    public Trip order(String driverName, double km) {
        for (Driver driver : drivers) {
            if (driver.getName().equals(driverName)) {
                return new Trip(driver, km);               // всередині класу об'єкт служби вже є
            }
        }
        throw new IllegalArgumentException("Водія " + driverName + " немає у службі");
    }

    public void hire(Driver driver) {
        drivers.add(driver);
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setListener(TripListener listener) {
        this.listener = listener;
    }

    public static int getTotalTrips() {
        return totalTrips;
    }

    public String getCity() {
        return city;
    }
}
