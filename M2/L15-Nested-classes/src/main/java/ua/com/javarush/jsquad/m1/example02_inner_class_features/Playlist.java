package ua.com.javarush.jsquad.m1.example02_inner_class_features;

/**
 * Плейліст із внутрішнім класом {@code Track} — на ньому показуємо
 * всі особливості внутрішніх класів.
 *
 * <p>Зверніть увагу: поле {@code name} є І в {@code Playlist}, І в {@code Track}.
 * Саме тому тут добре видно, навіщо потрібні <b>два this</b>.</p>
 */
public class Playlist {

    private final String name;                 // назва плейліста
    private int trackCount;

    public Playlist(String name) {
        this.name = name;
    }

    /** Внутрішній (не статичний!) клас. Оголошений public → видимий і ззовні. */
    public class Track {

        private final String name;             // назва треку — ім'я збігається з полем Playlist
        private final int seconds;

        // Особливість 2: до Java 16 у внутрішньому класі не можна було оголосити
        // статичні поля й методи (крім констант). З Java 16 (JEP 395) — можна.
        // Ми на Java 17, тому такий лічильник компілюється:
        private static int totalTracksEverCreated = 0;

        public Track(String name, int seconds) {
            this.name = name;
            this.seconds = seconds;
            totalTracksEverCreated++;
            trackCount++;                      // поле зовнішнього об'єкта
        }

        /**
         * Особливість 4: у методах внутрішнього об'єкта доступні ДВА посилання this.
         * <pre>
         *   this.name          — поле цього об'єкта Track
         *   Playlist.this.name — поле зовнішнього об'єкта Playlist
         * </pre>
         */
        public void printFull() {
            System.out.println("   «" + this.name + "» (" + seconds + " с) " +
                    "з плейліста «"
                    + Playlist.this.name + "»");
        }

        /** Повертає посилання на зовнішній об'єкт — доказ, що воно справді існує. */
        public Playlist getOwner() {
            return Playlist.this;
        }

        // Статичний метод усередині внутрішнього класу — теж легально з Java 16:
        public static int getTotalTracksEverCreated() {
            return totalTracksEverCreated;
        }
    }

    /**
     * Особливість 1: усередині СТАТИЧНОГО методу зовнішнього класу немає this,
     * тому просте new Track(...) не скомпілюється — нема звідки взяти
     * зовнішній об'єкт. Але якщо його створити явно — усе працює.
     */
    public static Track createDemoTrack() {
        // return new Track("Demo", 60);            // помилка компіляції: no enclosing instance
        Playlist demo = new Playlist("Демо-плейліст");
        return demo.new Track("Demo", 60);          // так — можна
    }

    public int getTrackCount() {
        return trackCount;
    }

    public String getName() {
        return name;
    }
}
