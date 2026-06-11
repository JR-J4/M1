package ua.com.javarush.jsquad.m1.example01_oop_principles;

/**
 * Базовий клас Smartphone (Смартфон).
 *
 * <p>На цьому класі видно одразу 3 принципи ООП:</p>
 * <ul>
 *   <li><b>АБСТРАКЦІЯ:</b> з тисяч характеристик реального телефону ми лишили
 *       лише важливі для нашої задачі — модель, власник, заряд батареї.</li>
 *   <li><b>ІНКАПСУЛЯЦІЯ:</b> поле {@code battery} приховане ({@code private}).
 *       Зовні його не можна зробити від'ємним або більшим за 100.</li>
 *   <li><b>УСПАДКУВАННЯ:</b> від цього класу наслідується {@link GamingPhone}.</li>
 * </ul>
 */
public class Smartphone {

    // Інкапсуляція: усі поля приховані, доступ — лише через методи
    private String model;
    private String owner;
    private int battery; // заряд у відсотках, завжди 0..100

    public Smartphone(String model, String owner) {
        this.model = model;
        this.owner = owner;
        this.battery = 100; // новий телефон — повний заряд
    }

    // Контрольований доступ до заряду: setter не пропускає некоректні значення
    public void charge(int percent) {
        battery = Math.min(100, battery + percent); // не більше 100
        System.out.println("  🔋 " + model + ": заряд " + battery + "%");
    }

    // Метод, який можна перевизначити в нащадку (поліморфізм)
    public void powerOn() {
        System.out.println("  " + model + " вмикається... звичайне завантаження.");
    }

    public String getModel() {
        return model;
    }

    public String getOwner() {
        return owner;
    }

    public int getBattery() {
        return battery;
    }
}
