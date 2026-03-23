package ua.com.javarush.jsquad.m1;

public class Scope {
  public static int count = 0;
  public static int sum = 0;

  public static void test(int data) {
    sum = sum + data;

    System.out.print("Before: ");
    System.out.println(sum);


    int sum = data * 2;


    System.out.print("After: ");
    System.out.println(sum);
    count++;
  }



  public static void main(String[] args) {
    test(5);

    System.out.println(sum);
  }
}
