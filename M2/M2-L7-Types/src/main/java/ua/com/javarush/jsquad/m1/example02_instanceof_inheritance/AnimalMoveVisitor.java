package ua.com.javarush.jsquad.m1.example02_instanceof_inheritance;

public class AnimalMoveVisitor implements AnimalVisitor {

    @Override
    public void visit(Dog dog) {
        dog.walk();
    }

    @Override
    public void visit(Duck duck) {
        duck.swim();
    }
}
