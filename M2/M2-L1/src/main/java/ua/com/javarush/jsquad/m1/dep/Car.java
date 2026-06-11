package ua.com.javarush.jsquad.m1.dep;

import java.util.ArrayList;

public class Car {

  private Engine engine;

  private Person driver;
  private ArrayList<Person> passangers;

  public Car() {

  }

  public Engine getEngine() {
    return engine;
  }

  public void setEngine(Engine engine) {
    this.engine = engine;
  }

  public void addPassanger(Person person) {
    passangers.add(person);
  }

  public Person getDriver() {
    return driver;
  }

  public void setDriver(Person driver) {
    this.driver = driver;
  }

  public void drive(){

  }
}
