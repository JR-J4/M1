package ua.com.javarush.jsquad.m1.example11_summary;

/**
 * Головний сервіс магазину.
 *
 * <p>Найважливіше в цьому класі — те, чого в ньому <b>немає</b>: жодного
 * {@code new}, жодного читання конфігу, жодного логування. Клас лише оголошує,
 * що йому потрібно, а все інше робить контейнер через рефлексію.</p>
 */
@Component
public class OrderService {

    @Inject
    private OrderRepository repository;        // клас без інтерфейсу — прийде як є

    @Inject
    private NotificationSender sender;         // інтерфейс — прийде загорнутим у проксі

    @Value("shop.maxItems")
    private int maxItems;

    @PostConstruct
    public void ready() {
        System.out.println("      [OrderService] готовий, ліміт товарів у замовленні: " + maxItems);
    }

    public void placeOrder(String customer, String product, int quantity) {
        if (quantity > maxItems) {
            throw new IllegalArgumentException(
                    "Не більше " + maxItems + " одиниць в одному замовленні (замовлено " + quantity + ")");
        }

        String order = customer + ": " + product + " x" + quantity;
        repository.save(order);
        sender.send(customer, "Ваше замовлення прийнято — " + product + " x" + quantity);
    }

    public int totalOrders() {
        return repository.count();
    }
}
