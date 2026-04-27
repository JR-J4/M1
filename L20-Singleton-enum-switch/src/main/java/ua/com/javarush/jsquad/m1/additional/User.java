package ua.com.javarush.jsquad.m1.additional;

import java.util.Objects;
import java.util.Set;

public class User {
  private String name;

  private Role role;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;
    return Objects.equals(name, user.name) && role == user.role;
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(name);
    result = 31 * result + Objects.hashCode(role);
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
            "name='" + name + '\'' +
            ", role=" + role +
            '}';
  }
}
