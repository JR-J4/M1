package ua.com.javarush.jsquad.m1.example06_creating_objects;

import java.io.IOException;

/**
 * Клас, конструктор якого завжди падає з <b>перевірюваним</b> винятком.
 * Потрібен, щоб показати різницю в обробці винятків між
 * {@code Class.newInstance()} та {@code Constructor.newInstance()}.
 */
public class ReportGenerator {

    public ReportGenerator() throws IOException {
        throw new IOException("Немає доступу до теки зі звітами");
    }
}
