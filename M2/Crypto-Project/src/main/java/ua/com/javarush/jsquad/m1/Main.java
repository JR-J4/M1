package ua.com.javarush.jsquad.m1;

import ua.com.javarush.jsquad.m1.cypher.AdvancedCypher;
import ua.com.javarush.jsquad.m1.cypher.BasicCypher;
import ua.com.javarush.jsquad.m1.cypher.Cypher;
import ua.com.javarush.jsquad.m1.cypher.CypherBase;

import java.nio.file.Files;
import java.nio.file.Path;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public static void main(String[] args) {
    CypherBase cypher = BasicCypher.getInstance();

    String name = cypher.getName();

    System.out.println(name);
  }


  public static void mainLogic(Cypher cypher, String text) {

    String encryptedText = cypher.encrypt(text);
//    Files.writeString(Path.of("test.txt"));

  }
}