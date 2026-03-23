package ua.com.javarush.jsquad.m1;

public class Test {
  public static void main(String[] args) {
    Car car = new Car();

    car.doors = 8;
    car.speedLimit = 250;


    Car car2 = new Car();
    car2.speedLimit = 120;


    System.out.println(car.getSpeedLimit());

    System.out.println(car2.getSpeedLimit());


  }
}
