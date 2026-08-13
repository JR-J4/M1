package ua.com.javarush.jsquad.m1.example02_default_constructor;

/**
 * Модуль 2. Рівень 8. Особливості виклику конструкторів. Блок static
 * <hr>
 * <h3>Тема: Конструктор за замовчуванням (default constructor)</h3>
 *
 * <p>Якщо в класу нема жодного конструктора, його буде створено автоматично.
 * Компілятор додає порожній конструктор без параметрів:</p>
 * <pre>
 *   class Book {
 *   }
 *   // компілятор "дописує" сам:
 *   class Book {
 *       Book() {
 *           super();
 *       }
 *   }
 * </pre>
 *
 * <p>Але щойно ви оголосите СВІЙ конструктор (будь-який) — автоматичний зникає,
 * і викликати {@code new Book()} без параметрів більше не вийде.</p>
 *
 * <p><b>Аналогія з життя:</b> у новому телефоні вже є стандартний рінгтон —
 * ним можна користуватись одразу. Але якщо ви поставили власну мелодію,
 * стандартна більше не грає.</p>
 *
 * <p><b>Реальне застосування:</b> часта помилка початківців — додати конструктор
 * з параметрами і "зламати" весь код, який створював об'єкт через {@code new X()}.</p>
 */
public class Example02_DefaultConstructor {

    public static void main(String[] args) {

        // === 1. Клас БЕЗ жодного конструктора ===
        System.out.println("=== Book: конструктора нема, але new Book() працює ===");
        // Ми не писали конструктор для Book, але компілятор створив його автоматично
        Book book = new Book();
        book.title = "Пригоди Тома Сойєра";
        System.out.println("Книгу створено: " + book.title);
        System.out.println();

        // === 2. Клас зі СВОЇМ конструктором — дефолтний зник ===
        System.out.println("=== Ticket: є свій конструктор, дефолтного більше нема ===");
        Ticket ticket = new Ticket("Київ — Львів");
        System.out.println("Квиток створено: " + ticket.getRoute());

        // Ticket empty = new Ticket(); // ПОМИЛКА КОМПІЛЯЦІЇ!
        // Конструктора без параметрів більше не існує:
        // ми оголосили Ticket(String route), і компілятор нічого не додає
        System.out.println();

        // === 3. Якщо потрібні обидва — оголошуємо обидва вручну ===
        System.out.println("=== Parcel: два конструктори, оголошені вручну ===");
        Parcel standard = new Parcel();            // свій конструктор без параметрів
        Parcel heavy = new Parcel(25);             // і свій з параметром
        System.out.println("Посилка 1: " + standard.getWeight() + " кг");
        System.out.println("Посилка 2: " + heavy.getWeight() + " кг");
        System.out.println();

        System.out.println("Головне: автоматичний конструктор існує лише доти,");
        System.out.println("доки в класі не з'явився хоч один власний.");
    }
}

/** Книга: жодного конструктора — компілятор створить Book() автоматично. */
class Book {
    String title;
}

/** Квиток: є власний конструктор, тому new Ticket() без параметрів не скомпілюється. */
class Ticket {
    private final String route;

    Ticket(String route) {
        this.route = route;
    }

    String getRoute() {
        return route;
    }
}

/** Посилка: потрібні обидва варіанти — оголошуємо обидва конструктори вручну. */
class Parcel {
    private final int weight;

    Parcel() {
        this.weight = 1; // стандартна вага
    }

    Parcel(int weight) {
        this.weight = weight;
    }

    int getWeight() {
        return weight;
    }
}
