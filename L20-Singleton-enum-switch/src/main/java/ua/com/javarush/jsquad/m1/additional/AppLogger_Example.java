package ua.com.javarush.jsquad.m1.additional;

import java.util.ArrayList;
import java.util.List;

public class AppLogger_Example {

  // 1. Створюємо єдиний екземпляр відразу при завантаженні класу
  private static final AppLogger_Example INSTANCE = new AppLogger_Example();

  private final List<String> logs = new ArrayList<>();

  // 2. Приватний конструктор — заборона створення ззовні
  private AppLogger_Example() {
  }

  // 3. Глобальна точка доступу — завжди повертає той самий обʼєкт
  public static AppLogger_Example getInstance() {
    return INSTANCE;
  }

  public void log(String message) {
    logs.add("[LOG] " + message);
  }

  public void printLogs() {
    for (String entry : logs) {
      System.out.println("  " + entry);
    }
  }

  public int getLogCount() {
    return logs.size();
  }
}
