package ua.com.javarush.jsquad.m1.example01_inner_class;

/**
 * Модуль 2. Рівень 15. Внутрішні та вкладені класи
 * <hr>
 * <h3>Тема: Внутрішні класи (inner classes)</h3>
 *
 * <p>Якщо один клас оголосити всередині іншого — такий клас називається
 * <b>внутрішнім</b>. Об'єкти внутрішніх класів при цьому "вкладені" в об'єкти
 * зовнішніх класів і можуть звертатися до їхніх змінних (навіть приватних).</p>
 *
 * <h4>Синтаксис:</h4>
 * <pre>
 *   class Outer {              // зовнішній клас
 *       private int x;
 *       class Inner {          // внутрішній клас (БЕЗ слова static)
 *           void print() {
 *               System.out.println(x);   // бачить поля зовнішнього об'єкта
 *           }
 *       }
 *   }
 *
 *   Outer outer = new Outer();               // 1) спершу зовнішній об'єкт
 *   Outer.Inner inner = outer.new Inner();   // 2) потім внутрішній — через нього
 * </pre>
 *
 * <p><b>Аналогія з життя:</b> готель і номер у ньому. Номер не існує сам по собі —
 * він завжди <i>чийсь</i> номер, у конкретному готелі. Спитати "яка адреса цього
 * номера?" можна, бо номер "знає" свій готель. А ось збудувати номер без готелю
 * не вийде — саме тому не буває {@code new Room()} без об'єкта {@code Hotel}.</p>
 *
 * <p><b>Реальне застосування:</b> внутрішній клас беруть тоді, коли допоміжний
 * об'єкт не має сенсу окремо від головного і мусить читати його стан: ітератор
 * колекції, курсор, вузол дерева з посиланням на власника, "вкладка" всередині
 * вікна.</p>
 */
public class Example01_InnerClass {

    public static void main(String[] args) {

        // === 1. Об'єкт внутрішнього класу створює сам зовнішній об'єкт ===
        // Сценарій: готель бронює номери. Метод bookRoom() усередині Hotel
        // просто пише new Room(...), бо об'єкт Hotel там уже є.
        Hotel hotel = new Hotel("Едельвейс", "Львів");

        System.out.println("1. Номери, заброньовані самим готелем:");

        Hotel.Room room101 = hotel.bookRoom(101, 2);
        Hotel.Room room102 = hotel.bookRoom(102, 3);

        room101.describe();
        room102.describe();

        System.out.println();

        // === 2. Внутрішній клас читає і змінює приватні поля зовнішнього ===
        // Конструктор Room робив bookedRooms++ у зовнішньому об'єкті.
        System.out.println("2. Заброньовано номерів: " + hotel.getBookedRooms());
        room101.checkIn("Олена Кравчук");     // викликає приватний log() зовнішнього класу

        System.out.println();

        // === 3. Створення внутрішнього об'єкта ЗЗОВНІ: outer.new Inner() ===
        // Сценарій: адміністратор працює з готелем напряму.
        // Зверніть увагу на незвичний синтаксис — крапка, new, ім'я класу.
        System.out.println("3. Номер, створений ззовні через hotel.new Room(...):");

        Hotel.Room room201 = hotel.new Room(201, 1);   // саме hotel.new, а не просто new
        room201.describe();
        System.out.println("   Заброньовано номерів: " + hotel.getBookedRooms());

        // Так НЕ можна — немає об'єкта Hotel, до якого прив'язати номер:
        // Hotel.Room ghost = new Hotel.Room(999, 1);   // помилка компіляції

        System.out.println();

        // === 4. Кожен внутрішній об'єкт прив'язаний до СВОГО зовнішнього ===
        // Сценарій: два різні готелі — номери з однаковими цифрами, але різні адреси.
        Hotel seaside = new Hotel("Прибій", "Одеса");
        Hotel.Room seasideRoom = seaside.bookRoom(101, 2);

        System.out.println("4. Однаковий номер 101 у двох готелях:");
        room101.describe();          // Львів
        seasideRoom.describe();      // Одеса

        System.out.println("   У «Едельвейсі» заброньовано: " + hotel.getBookedRooms());
        System.out.println("   У «Прибої» заброньовано:     " + seaside.getBookedRooms());

        System.out.println();

        // === 5. Як внутрішній клас виглядає для JVM ===
        // Компілятор створює окремий .class-файл з іменем Зовнішній$Внутрішній
        System.out.println("5. Ім'я класу об'єкта room101: " + room101.getClass().getName());
        System.out.println("   Знак $ означає: клас Room оголошений усередині Hotel.");
        System.out.println("   Зовнішній клас для Room:    " + Hotel.Room.class.getEnclosingClass().getSimpleName());
    }
}
