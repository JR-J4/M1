package ua.com.javarush.jsquad.m1.cypher;

public class AdvancedCypher extends CypherBase {
  private static AdvancedCypher INSTANCE;

  private AdvancedCypher(int id, String name, int complexity) {
    super(id, name, complexity);
  }

  public static AdvancedCypher getInstance(){
    if (INSTANCE == null) {
      INSTANCE = new AdvancedCypher(1, "SuperCypher123", 10);
    }
    return INSTANCE;
  }

  @Override
  public String encrypt(String payload) {
    return "";
  }

  @Override
  public String decrypt(String payload) {
    return "";
  }
}
