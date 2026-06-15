package ua.com.javarush.jsquad.m1.animal;

public class Main {
  public static void main(String[] args) {
    Animal catObject = new Cat();
    Animal dog = new Dog();

    dog.test();

    Bird bird = new Bird();

    bird.move();
  }
}
