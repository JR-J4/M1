package ua.com.javarush.jsquad.m1;

/*
Чому в першому випадку де два чара 'a' та 'a' зрівнюемо пише - ОДНАКОВІ

А у другому випадку (4 зрівняння) - пише Хіба таке може бути? Зрівнюемо також два чара 'a' та 'a', по ідеї повинно бути однакові....

Незрозуміло.... як воно до ELSE доходить та виконує його
 */

//

public class Example {
  public static void main(String[] args) {
    compare('a', 'a');
    compare('a', 'б');
    compare('р', 'в');
    compare('а', 'а');
  }

  public static void compare(Character first, Character second) {



//    int f = first;
//    int s = second;
//
//    System.out.println(f);
//    System.out.println(s);
//
//    System.out.println(f == s);
//    System.out.println(first == second);


    if (first.equals(second)) {
      System.out.println("однакові");
    } else if (first > second) {
      System.out.println("більше");
    } else if (first < second) {
      System.out.println("менше");
    } else {
      System.out.println("Хіба таке може бути???");
    }
  }
}
