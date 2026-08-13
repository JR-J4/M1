package ua.com.javarush.jsquad.m1.example05_static_block;

import java.util.Properties;

/**
 * Приклад із лекції: static змінній {@code namePrefix} потрібна складна
 * ініціалізація — зчитування налаштування з конфігурації.
 * Конструктор не підходить (він про об'єкти), тому використовуємо static-блок.
 */
public class Cat {

    public static String namePrefix;

    static {
        System.out.println("  [Cat] static-блок: читаємо конфігурацію...");
        Properties properties = new Properties();
        // У реальному житті тут було б читання файлу:
        // properties.load(new FileReader("cat.properties"));
        properties.setProperty("name-prefix", "Пан");
        namePrefix = properties.getProperty("name-prefix");
        System.out.println("  [Cat] static-блок: namePrefix = \"" + namePrefix + "\"");
    }

    private final String name;
    private int age;


    public Cat(String name) {
        this.name = name;
    }

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getFullName() {
        return namePrefix + " " + name;
    }
}
