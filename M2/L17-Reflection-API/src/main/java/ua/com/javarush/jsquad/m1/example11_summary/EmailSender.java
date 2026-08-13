package ua.com.javarush.jsquad.m1.example11_summary;

/**
 * Відправник сповіщень електронною поштою.
 *
 * <p>Зверніть увагу: у класі немає конструктора з параметрами, немає сеттерів,
 * а поле {@code fromAddress} приватне. Заповнити його — задача контейнера.</p>
 */
@Component
public class EmailSender implements NotificationSender {

    @Value("mail.from")
    private String fromAddress;

    private int sentCount;

    @PostConstruct
    public void init() {
        System.out.println("      [EmailSender] готовий, адреса відправника: " + fromAddress);
    }

    @Override
    public void send(String to, String message) {
        sentCount++;
        System.out.println("      Лист #" + sentCount + " від " + fromAddress + " до " + to);
        System.out.println("      Текст: " + message);
    }
}
