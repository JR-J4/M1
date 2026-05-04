package ua.com.javarush.jsquad.m1.exception;

public class SeatIsTakenException extends RuntimeException {


  public SeatIsTakenException(String message) {
    super(message);
  }

  public SeatIsTakenException(String message, Throwable cause) {
    super(message, cause);
  }

}
