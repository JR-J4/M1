package ua.com.javarush.jsquad.m1.example05_getters_setters;

/**
 * Лекція 13: Об'єкти
 * <hr>
 * <h3>Тема: Властивості — гетер (getter) і сетер (setter)</h3>
 *
 * <p>В Java прийнято робити всі поля класу <b>private</b>.
 * Доступ до них — тільки через спеціальні методи:</p>
 * <ul>
 *   <li><b>getter</b> (get-метод) — для отримання значення поля</li>
 *   <li><b>setter</b> (set-метод) — для встановлення значення поля</li>
 * </ul>
 *
 * <p><b>Синтаксис:</b></p>
 * <pre>
 *   private int price;
 *
 *   public int getPrice() {       // getter
 *       return price;
 *   }
 *
 *   public void setPrice(int price) {  // setter
 *       this.price = price;
 *   }
 * </pre>
 *
 * <h4>Навіщо getter/setter замість прямого доступу?</h4>
 * <pre>
 *   БЕЗ інкапсуляції:                З інкапсуляцією:
 *   ┌─────────────────────┐          ┌─────────────────────┐
 *   │ product.price = -50 │          │ product.setPrice(-50)│
 *   │ ← Ніхто не перевірив!│         │ → "Помилка! Ціна    │
 *   │ Дані пошкоджені!    │          │    не може бути &lt; 0" │
 *   └─────────────────────┘          └─────────────────────┘
 * </pre>
 *
 * <p><b>Аналогія з життя:</b>
 * Getter/Setter — як віконце каси у банку.
 * Ви не заходите до сейфу особисто (private поле).
 * Ви просите касира (getter) показати баланс
 * або (setter) внести гроші — і касир перевіряє,
 * чи все коректно.</p>
 *
 * <p><b>Реальне застосування:</b>
 * Setter може перевіряти валідність даних (ціна >= 0),
 * логувати зміни, оповіщувати інші частини програми про зміну стану.</p>
 */
public class Example05_GettersSetters {

    public static void main(String[] args) {

        // === 1. Поля private — пряма зміна неможлива ===
        // Сценарій: інтернет-магазин — товари з захищеними даними

        System.out.println("=== Поля private — доступ тільки через методи ===");

        Product laptop = new Product("Ноутбук ASUS", 25000, 10);
        laptop.showInfo();

        // laptop.price = -100;  // ПОМИЛКА КОМПІЛЯЦІЇ! Поле private!

        System.out.println();

        // === 2. Getter — отримуємо значення ===
        // Сценарій: касовий апарат зчитує ціну товару

        System.out.println("=== Getter — отримання значень ===");
        System.out.println("  Назва:    " + laptop.getName());
        System.out.println("  Ціна:     " + laptop.getPrice() + " грн");
        System.out.println("  Кількість: " + laptop.getQuantity() + " шт.");

        System.out.println();

        // === 3. Setter — змінюємо значення з валідацією ===
        // Сценарій: менеджер змінює ціну товару

        System.out.println("=== Setter — зміна значень з перевіркою ===");

        System.out.println("Встановлюємо нову ціну 22000:");
        laptop.setPrice(22000);
        laptop.showInfo();

        System.out.println();

        System.out.println("Спроба встановити від'ємну ціну -500:");
        laptop.setPrice(-500);   // setter відхилить!
        laptop.showInfo();

        System.out.println();

        System.out.println("Спроба встановити від'ємну кількість -10:");
        laptop.setQuantity(-10); // setter відхилить!
        laptop.showInfo();

        System.out.println();

        // === 4. Getter/Setter — конвенція іменування ===

        System.out.println("=== Конвенція іменування ===");
        System.out.println();
        System.out.println("  private String name;");
        System.out.println();
        System.out.println("  public String getName() {    ← get + Name (з великої)");
        System.out.println("      return name;");
        System.out.println("  }");
        System.out.println();
        System.out.println("  public void setName(String name) {  ← set + Name");
        System.out.println("      this.name = name;");
        System.out.println("  }");
        System.out.println();
        System.out.println("  Для boolean:");
        System.out.println("  private boolean active;");
        System.out.println("  public boolean isActive() { ... }   ← is + Active");

        System.out.println();

        // === 5. Практичний приклад — кошик товарів ===
        // Сценарій: формуємо кошик і рахуємо суму

        System.out.println("=== Практика: кошик товарів ===");

        Product phone = new Product("Смартфон Samsung", 15000, 5);
        Product headphones = new Product("Навушники Sony", 3500, 20);

        Product[] cart = {laptop, phone, headphones};

        double totalSum = 0;
        for (Product p : cart) {
            double itemTotal = p.getPrice() * p.getQuantity();
            System.out.println("  " + p.getName() + ": " + p.getPrice() + " x " + p.getQuantity() + " = " + itemTotal + " грн");
            totalSum += itemTotal;
        }
        System.out.println("  ─────────────────────────────────");
        System.out.println("  Загальна сума кошика: " + totalSum + " грн");
    }
}
