package ua.com.javarush.jsquad.m1;

import java.util.Scanner;

public class Task {

  static String[] strings;

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    StringBuilder a = new StringBuilder();

    a.append("hello").append("world");


    // 2\n
    // -> \n

    if (scanner.hasNextInt()) {
      int sizeOfArr = scanner.nextInt();
      strings = new String[sizeOfArr];
    }

    for (int i = 0; i < strings.length; i++) {
      strings[i] = scanner.nextLine();
    }

    for (int i = 0; i < strings.length; i++) {
      String currentString = strings[i];
      if (strings[i] == null) {
        continue;
      }
      for (int j = i + 1; j < strings.length; j++) {
        if (strings[j] != null && currentString.equals(strings[j])) {
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

