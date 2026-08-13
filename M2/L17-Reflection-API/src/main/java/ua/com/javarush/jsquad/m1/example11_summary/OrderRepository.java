package ua.com.javarush.jsquad.m1.example11_summary;

import java.util.ArrayList;
import java.util.List;

/**
 * "Сховище" замовлень. Інтерфейсу не має — тому контейнер підставить
 * його в залежності як є, без проксі.
 */
@Component
public class OrderRepository {

    @Value("db.url")
    private String url;

    private final List<String> orders = new ArrayList<>();

    @PostConstruct
    public void connect() {
        System.out.println("      [OrderRepository] з'єднання з " + url);
    }

    public void save(String order) {
        orders.add(order);
    }

    public int count() {
        return orders.size();
    }

    public List<String> findAll() {
        return List.copyOf(orders);
    }
}
