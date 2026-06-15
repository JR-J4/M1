package ua.com.javarush.jsquad.m1.cypher;

public interface Cypher {
  String encrypt(String payload);
  String decrypt(String payload);

  int getId();
  String getName();
  int getComplexity();
}
