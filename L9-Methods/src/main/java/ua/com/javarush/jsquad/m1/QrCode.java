package ua.com.javarush.jsquad.m1;

import java.io.IOException;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Scanner;

public class QrCode {

  public static String[][] expectedArray = new String[3][3];
  public static String[][] actualArray = new String[3][3];

  static {
    Random random = new Random();
    for (int i = 0; i < expectedArray.length; i++) {
      for (int j = 0; j < expectedArray[0].length; j++) {
        OptionalInt optionalInt = random.ints(0, 2).findFirst();
        expectedArray[i][j] = optionalInt.isPresent() && optionalInt.getAsInt() == 1 ? " " : "#";
      }
    }
  }

  // # # #
  // # # #
  // # # #

  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);

    for (int i = 0; i < actualArray.length; i++) {
      for (int j = 0; j < actualArray[i].length; j++) {
        actualArray[i][j] = scanner.nextLine();
      }
    }

    System.out.println(Arrays.deepEquals(expectedArray, actualArray));

  }
}
