package ua.com.javarush.jsquad.m1.example03_constructor_chain;

/**
 * Житловий будинок: успадковує будівлю.
 * Його конструктор виконується ДРУГИМ — після конструктора базового класу.
 */
public class House extends Building {

    double windows;

    public House() {
        // Тут НЕЯВНО викликається super() — компілятор додає його сам
        System.out.println("  2. House(): стіни та дах зведено");
    }

    public House(int floors) {
        super(floors); // ЯВНО передаємо параметр конструктору базового класу
        System.out.println("  2. House(" + floors + "): стіни та дах для "
                + floors + " поверхів");
    }
}
