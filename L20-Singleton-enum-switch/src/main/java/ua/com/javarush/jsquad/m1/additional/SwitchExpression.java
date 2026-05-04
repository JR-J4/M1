package ua.com.javarush.jsquad.m1.additional;

public class SwitchExpression {

  public static void main(String[] args) {

    Role role = Role.MODERATOR;

    String s = switch (role) {
      case MODERATOR -> "Moderator";
      case USER -> {
        System.out.println("Користувач");
        yield "Просто користувач";
      }
      case ADMIN -> "Це адмін";
    };


    int dayNumber = 3;
    String day = switch (dayNumber) {
      case 1 -> "monday";
      default -> throw new IllegalStateException("Unexpected value: " + dayNumber);
    };
  }
}
