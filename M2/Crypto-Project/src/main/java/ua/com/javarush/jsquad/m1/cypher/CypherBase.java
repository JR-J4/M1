package ua.com.javarush.jsquad.m1.cypher;

public abstract class CypherBase implements Cypher {
  private int id;
  private String name;
  private int complexity;

  protected CypherBase(int id, String name, int complexity) {
    this.id = id;
    this.name = name;
    this.complexity = complexity;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getComplexity() {
    return complexity;
  }

  protected abstract Number test();
}
