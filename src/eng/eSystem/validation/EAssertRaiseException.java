package eng.eSystem.validation;

public class EAssertRaiseException extends RuntimeException {
  public EAssertRaiseException(Throwable cause) {
    super("E-Assertion failed. However, producer used to produce the related exception/message has failed too.", cause);
  }
}
