package ua.com.javarush.jsquad.m1.example02_instanceof_inheritance;


public interface AnimalVisitor {

    void visit(Dog dog);

    void visit(Duck duck);
}
