package ua.com.javarush.jsquad.m1.example01_inner_class;

/**
 * Зовнішній клас {@code Hotel} із <b>внутрішнім</b> класом {@code Room}.
 *
 * <p>Клас {@code Room} оголошено всередині {@code Hotel} <b>без слова static</b> —
 * отже, це саме внутрішній клас. Кожен об'єкт {@code Room} "живе" всередині
 * конкретного об'єкта {@code Hotel} і має приховане посилання на нього.</p>
 */
public class Hotel {

    private final String name;
    private final String city;
    private int bookedRooms;          // приватне поле зовнішнього класу

    public Hotel(String name, String city) {
        this.name = name;
        this.city = city;
    }

    /**
     * Внутрішній клас. Немає слова static → у кожного об'єкта Room
     * є приховане посилання на об'єкт Hotel, у якому він створений.
     */
    public class Room {

        private final int number;
        private final int beds;

        public Room(int number, int beds) {
            this.number = number;
            this.beds = beds;
            bookedRooms++;            // напряму змінюємо ПРИВАТНЕ поле зовнішнього об'єкта
        }

        class Booked {
            private final int number;


            class status {
                public void test(){
                    Hotel.this.bookedRooms++;
                }
            }

            public Booked(int number, int number1) {
              this.number = number1;
            }
        }

        /** Внутрішній клас бачить приватні поля зовнішнього об'єкта без геттерів. */
        public void describe() {
            System.out.println("   Номер " + number + ", ліжок: " + beds + " — готель «"
                    + name + "», м. " + city);
        }

        /** І приватні методи зовнішнього класу — теж. */
        public void checkIn(String guest) {
            log("Заселення: " + guest + " → номер " + number);
        }
    }

    /** Найзручніший спосіб віддати назовні об'єкт внутрішнього класу — метод-фабрика. */
    public Room bookRoom(int number, int beds) {
        return new Room(number, beds);   // ми всередині Hotel → об'єкт Hotel уже є (this)
    }

    public int getBookedRooms() {
        return bookedRooms;
    }

    private void log(String message) {   // приватний метод, а внутрішній клас його викликає
        System.out.println("   [" + name + "] " + message);
    }
}
