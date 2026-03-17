package ua.com.javarush.jsquad.m1;

import java.util.Objects;
import java.util.Scanner;

public class Test_0508 {
  public static void main(String[] args) {
    Scanner console = new Scanner(System.in);
    String[] strings = new String[6];

    for (int i = 0; i < strings.length; i++) {
      strings[i] = console.nextLine();
    }

    // ["null", "World", "null",  "Test", ]

    for (int i = 0; i < strings.length; i++) {

      for (int j = i + 1; j < strings.length; j++) {
        if (Objects.isNull(strings[i]) || Objects.isNull(strings[j])) {
          continue;
        }

        if (Objects.equals(strings[i], strings[j])) {
          strings[i] = null;
          strings[j] = null;
        }

      }

    }

    for (int i = 0; i < strings.length; i++) {
      System.out.print(strings[i] + ", ");
    }

  }
}
