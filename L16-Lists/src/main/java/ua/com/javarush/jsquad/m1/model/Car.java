package ua.com.javarush.jsquad.m1.model;

import java.util.Objects;

public class Car {
  private String brand;
  private String model;

  public Car(String brand, String model) {
    this.brand = brand;
    this.model = model;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    Car car = (Car) o;
    return Objects.equals(brand, car.brand) && Objects.equals(model, car.model);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(brand);
    result = 31 * result + Objects.hashCode(model);
    return result;
  }

  @Override
  public String toString() {
    return "Car{" +
            "brand='" + brand + '\'' +
            ", model='" + model + '\'' +
            '}';
  }
}
