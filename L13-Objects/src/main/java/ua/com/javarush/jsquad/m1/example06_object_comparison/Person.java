package ua.com.javarush.jsquad.m1.example06_object_comparison;

import java.util.Objects;

/**
 * Клас Person — людина з ім'ям та віком.
 *
 * Перевизначає equals() та hashCode() для порівняння за значенням полів,
 * а не за адресою в пам'яті.
 */
public class Person {

    int id;
    private String name;
    private int age;
    private int test;

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Перевизначений equals — порівнює за полями name і age


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;
        return id == person.id && age == person.age && test == person.test && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, test);
    }
}
