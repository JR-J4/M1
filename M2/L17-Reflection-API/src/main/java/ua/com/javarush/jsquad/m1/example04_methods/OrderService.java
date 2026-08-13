package ua.com.javarush.jsquad.m1.example04_methods;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Сервіс замовлень із методами "всіх сортів": різні модифікатори доступу,
 * різні типи повернення, параметри, перевантаження, {@code throws} та анотації.
 */
public class OrderService {

    private final List<String> orders = new java.util.ArrayList<>();

    @Loggable(level = "DEBUG")
    public String createOrder(String customer, int quantity) {
        String order = customer + " x" + quantity;
        orders.add(order);
        return order;
    }

    /** Перевантажений метод — та сама назва, інші параметри. */
    public String createOrder(String customer) {
        return createOrder(customer, 1);
    }

    @Loggable
    public void cancelOrder(int index) {
        if (index >= 0 && index < orders.size()) {
            orders.remove(index);
        }
    }

    /** Метод з узагальненим типом повернення. */
    public List<String> findAll() {
        return List.copyOf(orders);
    }

    /** Метод, що оголошує перевірювані винятки. */
    public Map<String, Integer> exportToFile(String path) throws IOException, IllegalStateException {
        if (orders.isEmpty()) {
            throw new IllegalStateException("Нема чого експортувати");
        }
        return Map.of(path, orders.size());
    }

    /** Статичний метод. */
    public static double calculateTax(double amount) {
        return amount * 0.2;
    }

    /** Приватний метод — його не видно ззовні, але рефлексія знайде. */
    private String buildSecretCode(String customer) {
        return "SECRET-" + customer.hashCode();
    }

    /** Метод зі змінною кількістю аргументів (varargs). */
    public int totalQuantity(int... quantities) {
        int sum = 0;
        for (int q : quantities) {
            sum += q;
        }
        return sum;
    }

    protected final void internalCleanup() {
        orders.clear();
    }
}
