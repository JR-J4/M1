package ua.com.javarush.jsquad.m1;

public class PizzaSizeClass {

  public static final PizzaSizeClass SMALL = new PizzaSizeClass(25, 149);


  private final int diameterCm;
  private final int priceUah;

  // Конструктор enum — завжди private (навіть без ключового слова)
  private PizzaSizeClass(int diameterCm, int priceUah) {
    this.diameterCm = diameterCm;
    this.priceUah = priceUah;
  }

  public int getDiameterCm() {
    return diameterCm;
  }

  public int getPriceUah() {
    return priceUah;
  }

  // Власний метод — опис розміру
  public String getDescription() {
     return  " (Ø" + diameterCm + " см) — " + priceUah + " грн";
  }
}
