package ua.com.javarush.jsquad.m1.summary;

import java.util.Stack;

public class ErrorLogger implements AutoCloseable {
  private final Stack<String> errorStack = new Stack<>();
  private int totalErrors = 0;

  ErrorLogger() {
    System.out.println("[Logger] Систему логування запущено");
  }

  void log(Exception e) {
    totalErrors++;

    // Збираємо інформацію з stack trace
    StackTraceElement[] trace = e.getStackTrace();
    String location = (trace.length > 0)
            ? trace[0].getMethodName() + "() рядок " + trace[0].getLineNumber()
            : "невідомо";

    String orderId = (e instanceof OrderException)
            ? ((OrderException) e).getOrderId()
            : "N/A";

    String logEntry = "[#" + totalErrors + "] Замовлення: " + orderId
            + " | " + e.getMessage()
            + " | Місце: " + location;

    errorStack.push(logEntry);
    System.out.println("[Logger] Записано помилку #" + totalErrors);
  }

  void printReport() {
    System.out.println("========== ЗВІТ ПОМИЛОК ==========");
    System.out.println("Всього помилок: " + totalErrors);

    if (errorStack.empty()) {
      System.out.println("Помилок не знайдено!");
    } else {
      System.out.println("Останні помилки (від найновішої):");
      // Виводимо зі стеку — остання помилка першою
      Stack<String> temp = new Stack<>();
      temp.addAll(errorStack);
      while (!temp.empty()) {
        System.out.println("  " + temp.pop());
      }
    }
    System.out.println("===================================");
  }

  @Override
  public void close() {
    System.out.println("[Logger] Систему логування закрито (AutoCloseable). "
            + "Зафіксовано " + totalErrors + " помилок.");
  }
}