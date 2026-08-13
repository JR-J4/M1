package ua.com.javarush.jsquad.m1.example06_creating_objects;

/**
 * Класичний Singleton: конструктор private, щоб ніхто не створив другий об'єкт.
 * У прикладі 06 ми побачимо, що рефлексія цей захист обходить.
 */
public class DatabaseConnection {

    private static final DatabaseConnection INSTANCE = new DatabaseConnection();

    private final String url;

    /** Приватний конструктор — "ззовні мене не створити". */
    private DatabaseConnection() {
        this.url = "jdbc:postgresql://localhost:5432/shop";
    }

    public static DatabaseConnection getInstance() {
        return INSTANCE;
    }

    public String getUrl() {
        return url;
    }
}
