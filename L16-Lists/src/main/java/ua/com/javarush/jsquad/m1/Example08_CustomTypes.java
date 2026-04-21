package ua.com.javarush.jsquad.m1;

import ua.com.javarush.jsquad.m1.model.Car;

import java.util.ArrayList;

public class Example08_CustomTypes {

  public static void main(String[] args) {

    ArrayList<Car> cars = new ArrayList<>();

    cars.add(new Car("Toyota", "Corolla"));
    cars.add(new Car("Toyota", "Corolla"));

    System.out.println(cars);


    System.out.println(cars.get(0));

  }
}
