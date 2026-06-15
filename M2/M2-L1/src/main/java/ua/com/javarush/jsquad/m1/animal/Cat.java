package ua.com.javarush.jsquad.m1.animal;

import java.io.Serializable;
import java.util.Iterator;

public class Cat extends Animal {
  @Override
  public void move() {
    System.out.println("Walk");
  }

  public Iterator<Cat> iterator() {
    return null;
  }
}
