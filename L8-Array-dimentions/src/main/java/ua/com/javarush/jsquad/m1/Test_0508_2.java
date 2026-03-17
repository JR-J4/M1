package ua.com.javarush.jsquad.m1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Test_0508_2 {
  public static String[] strings;

  public static void main(String[] args) {
    //напишіть тут ваш код
    Scanner sc = new Scanner(System.in);

    strings = new String[6];

    for (int i = 0; i < strings.length; i++) {
      strings[i] = sc.nextLine();
    }

    String[] sorted = Arrays.copyOf(strings, strings.length);


    // ["A", "A", "A", "B" "C" "C"]


    for (int i = 0; i < sorted.length - 1; i++) {
      for (int j = i + 1; j < sorted.length; j++) {

      }
    }



    // -1 - Good!
    for (int i = 0; i < strings.length - 1; i++) {

      boolean b = false; // GOOD

      if (strings[i] == null) continue; // GOOD

      for (int j = i + 1; j < strings.length; j++) {

        if (strings[j] == null) continue;

        if (strings[i].equals(strings[j])) {
          strings[j] = null;
          b = true;
        }

      }

      if (b) strings[i] = null;
    }




    for (int i = 0; i < strings.length; i++) {
      System.out.print(strings[i] + ", ");
    }
  }

}
