package ua.com.javarush.jsquad.m1.additional;

import java.util.Set;

public enum Role {
  ADMIN(Set.of(Permission.ALL)),
  MODERATOR(Set.of(Permission.READ, Permission.MODIFY)),
  USER(Set.of(Permission.READ));

  private Set<Permission> permissions;

  Role(Set<Permission> permissions) {
    this.permissions = permissions;
  }
}
