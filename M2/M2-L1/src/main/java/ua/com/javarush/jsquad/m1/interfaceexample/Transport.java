package ua.com.javarush.jsquad.m1.interfaceexample;

public abstract class Transport {
  protected int capacity;

  public Transport(int capacity) {
    this.capacity = capacity;
  }

  public int getCapacity() {
    return capacity;
  }


}
