package ua.com.javarush.jsquad.m1.cypher;

public class BasicCypher extends CypherBase {

  private static BasicCypher INSTANCE;

  private BasicCypher(int id, String name, int complexity) {
    super(id, name, complexity);
  }

  public static BasicCypher getInstance(){
    if (INSTANCE == null) {
      INSTANCE = new BasicCypher(1, "BasicCypher123", 10);
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
