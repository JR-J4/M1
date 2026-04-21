package ua.com.javarush.jsquad.m1;

import java.util.ArrayList;
import java.util.Iterator;

public class Task1305 {

  public static void main(String[] args) {
    ArrayList<String> words = new ArrayList<>();
    words.add("Hello world!");
    words.add("Amigo");
    words.add("Elly");
    words.add("Kerry");
    words.add("Bug");
    words.add("bug");
    words.add("BUG");
    words.add("Easy ug");
    words.add("Risha");

    ArrayList<String> copyWordsFirst = new ArrayList<>(words);
    ArrayList<String> copyWordsSecond = new ArrayList<>(words);
    ArrayList<String> copyWordsThird = new ArrayList<>(words);

    removeBugWithFor(copyWordsFirst);
    removeBugWithWhile(copyWordsSecond);
    removeBugWithCopy(copyWordsThird);

    copyWordsFirst.forEach(System.out::println);
    String line = "_________________________";
    System.out.println(line);
    copyWordsSecond.forEach(System.out::println);
    System.out.println(line);
    copyWordsThird.forEach(System.out::println);
    System.out.println(line);
  }

  public static void removeBugWithFor(ArrayList<String> list) {
    for (int i = 0; i < list.size(); i++) {
      String str = list.get(i);

      if (isBug(str)){
        list.remove(str);
        i--;
      }
    }
    //напишіть тут ваш код
  }

  private static boolean isBug(String str) {
    return "bug".equalsIgnoreCase(str);
  }

  public static void removeBugWithWhile(ArrayList<String> list) {
    //напишіть тут ваш код
    Iterator<String> it = list.iterator();
    while (it.hasNext()){
      String str = it.next();
      if (isBug(str)){
        it.remove();
      }
    }
  }

  public static void removeBugWithCopy(ArrayList<String> list) {
    //напишіть тут ваш код
    ArrayList<String> listCopy = new ArrayList<>(list);
    for (String str : listCopy){
      if (isBug(str)){
        list.remove(str);
      }
    }
  }

}
