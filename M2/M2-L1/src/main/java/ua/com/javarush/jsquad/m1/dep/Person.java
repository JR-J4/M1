package ua.com.javarush.jsquad.m1.dep;

public class Person {


  public void driveCar(Car car) {
    car.setDriver(this);
    car.drive();
  }
}
