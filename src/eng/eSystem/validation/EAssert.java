package eng.eSystem.validation;

import eng.eSystem.utilites.ObjectUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class EAssert {

  public static class Argument {

    public static void isFalse(boolean value) {
      EAssert.Argument.isFalse(value, () -> "");
    }

    public static void isFalse(boolean value, String message) {
      EAssert.Argument.isFalse(value, () -> message);
    }

    public static void isFalse(boolean value, ErrorMessageProducer messageProducer) {
      if (value) EAssert.raise("Argument does not fulfil requirement (must be false). ", messageProducer);
    }

    public static void isNonemptyString(String value) {
      EAssert.Argument.isNonemptyString(value, "");
    }

    public static void isNonemptyString(String value, String message) {
      EAssert.Argument.isNonemptyString(value, () -> message);
    }

    public static void isNonemptyString(String value, ErrorMessageProducer messageProducer) {
      EAssert.Argument.isNotNull(value, messageProducer);
      if (value.length() == 0) EAssert.raise("Argument cannot be null or empty string. ", messageProducer);
    }

    public static void isNotNull(Object value) {
      EAssert.Argument.isNotNull(value, "");
    }

    public static void isNotNull(Object value, String message) {
      EAssert.Argument.isNotNull(value, () -> message);
    }

    public static void isNotNull(Object value, ErrorMessageProducer messageProducer) {
      if (value == null) EAssert.raise("Argument cannot be null.", messageProducer);
    }

    public static void isTrue(boolean value) {
      EAssert.Argument.isTrue(value, () -> "");
    }

    public static void isTrue(boolean value, String message) {
      EAssert.Argument.isTrue(value, () -> message);
    }

    public static void isTrue(boolean value, ErrorMessageProducer messageProducer) {
      if (!value) EAssert.raise("Argument does not fulfil requirement (must be true). ", messageProducer);
    }


    public static void matchPattern(String value, String pattern, String argumentName) {
      EAssert.matchPattern(value, pattern,
              sf("Argument '%s' with value '%s' does not match regex pattern '%s'.", argumentName, value, pattern));
    }

    public static void matchPattern(String value, String pattern) {
      EAssert.matchPattern(value, pattern,
              sf("Argument with value '%s' does not match regex pattern '%s'.", value, pattern));
    }
  }

  private static final String TEXT_ERR = "E-Assert failed. ";
  private static final String TEXT_EMPTY_STRING = TEXT_ERR + "String expression cannot be null or zero-lenght.";
  private static final String TEXT_NOT_FALSE = TEXT_ERR + "Expression should be false.";
  private static final String TEXT_NOT_NULL = TEXT_ERR + "Value should not be null.";
  private static final String TEXT_NOT_TRUE = TEXT_ERR + "Expression should be true.";
  private static final String TEXT_NOT_EQUAL = TEXT_ERR + "Values are not equal.";

  // region equals

  public static void equals(Object first, Object second) {
    EAssert.equals(first, second, () -> TEXT_NOT_EQUAL);
  }

  public static void equals(Object first, Object second, String message) {
    equals(first, second, () -> message);
  }

  public static void equals(Object first, Object second, ErrorMessageProducer producer) {
    if (!ObjectUtils.equals(first, second)) raise(producer);
  }

  public static void equals(Object first, Object second, ExceptionProducer producer) {
    if (!ObjectUtils.equals(first, second)) raise(producer);
  }

  public static void equals(long first, long second) {
    EAssert.equals(first, second, () -> TEXT_NOT_EQUAL);
  }

  public static void equals(long first, long second, String message) {
    equals(first, second, () -> message);
  }

  public static void equals(long first, long second, ErrorMessageProducer producer) {
    if (!ObjectUtils.equals(first, second)) raise(producer);
  }

  public static void equals(long first, long second, ExceptionProducer producer) {
    if (!ObjectUtils.equals(first, second)) raise(producer);
  }

  public static void equals(double first, double second) {
    EAssert.equals(first, second, () -> TEXT_NOT_EQUAL);
  }

  public static void equals(double first, double second, String message) {
    equals(first, second, () -> message);
  }

  public static void equals(double first, double second, ErrorMessageProducer producer) {
    if (!ObjectUtils.equals(first, second)) raise(producer);
  }

  public static void equals(double first, double second, ExceptionProducer producer) {
    if (!ObjectUtils.equals(first, second)) raise(producer);
  }

// endregion equals

  //region notEquals

  public static void notEquals(Object first, Object second) {
    EAssert.notEquals(first, second, () -> TEXT_NOT_EQUAL);
  }

  public static void notEquals(Object first, Object second, String message) {
    notEquals(first, second, () -> message);
  }

  public static void notEquals(Object first, Object second, ErrorMessageProducer producer) {
    if (ObjectUtils.equals(first, second)) raise(producer);
  }

  public static void notEquals(Object first, Object second, ExceptionProducer producer) {
    if (ObjectUtils.equals(first, second)) raise(producer);
  }

  public static void notEquals(long first, long second) {
    EAssert.notEquals(first, second, () -> TEXT_NOT_EQUAL);
  }

  public static void notEquals(long first, long second, String message) {
    EAssert.notEquals(first, second, () -> message);
  }

  public static void notEquals(long first, long second, ErrorMessageProducer producer) {
    if (ObjectUtils.equals(first, second)) raise(producer);
  }

  public static void notEquals(long first, long second, ExceptionProducer producer) {
    if (ObjectUtils.equals(first, second)) raise(producer);
  }

  public static void notEquals(double first, double second) {
    EAssert.notEquals(first, second, () -> TEXT_NOT_EQUAL);
  }

  public static void notEquals(double first, double second, String message) {
    EAssert.notEquals(first, second, () -> message);
  }

  public static void notEquals(double first, double second, ErrorMessageProducer producer) {
    if (ObjectUtils.equals(first, second)) raise(producer);
  }

  public static void notEquals(double first, double second, ExceptionProducer producer) {
    if (ObjectUtils.equals(first, second)) raise(producer);
  }

  //endregion notEquals

  //region isFalse

  public static void isFalse(boolean value) {
    EAssert.isFalse(value, () -> TEXT_NOT_FALSE);
  }

  public static void isFalse(boolean value, String message) {
    isFalse(value, () -> message);
  }

  public static void isFalse(boolean value, ErrorMessageProducer producer) {
    if (value) raise(producer);
  }

  public static void isFalse(boolean value, ExceptionProducer producer) {
    if (value) raise(producer);
  }

  //endregion isFalse

  //region isNonemptyString

  public static void isNonemptyString(String value) {
    EAssert.isNonemptyString(value, TEXT_EMPTY_STRING);
  }

  public static void isNonemptyString(String value, String message) {
    EAssert.isNonemptyString(value, () -> message);
  }

  public static void isNonemptyString(String value, ExceptionProducer producer) {
    EAssert.isNotNull(value, producer);
    EAssert.isTrue(value.length() > 0, producer);
  }

  public static void isNonemptyString(String value, ErrorMessageProducer producer) {
    EAssert.isNotNull(value, producer);
    EAssert.isTrue(value.length() > 0, producer);
  }

  // endregion isNonemptyString

  //region isNotNull

  public static void isNotNull(Object value) {
    EAssert.isNotNull(value, TEXT_NOT_NULL);
  }

  public static void isNotNull(Object value, String message) {
    EAssert.isNotNull(value, () -> message);
  }

  public static void isNotNull(Object value, ErrorMessageProducer producer) {
    if (value == null) raise(producer);
  }

  public static void isNotNull(Object value, ExceptionProducer producer) {
    if (value == null) raise(producer);
  }

  // endregion isNotNull

  //region isTrue

  public static void isTrue(boolean value) {
    EAssert.isTrue(value, () -> TEXT_NOT_TRUE);
  }

  public static void isTrue(boolean value, String message) {
    EAssert.isTrue(value, () -> message);
  }

  public static void isTrue(boolean value, ErrorMessageProducer producer) {
    if (!value) raise(producer);
  }

  public static void isTrue(boolean value, ExceptionProducer producer) {
    if (!value) raise(producer);
  }

  // endregion isTrue

  public static void isXor(boolean... value) {
    boolean isFound = false;
    for (boolean b : value) {
      if (b) {
        if (!isFound)
          isFound = true;
        else
          throw new EAssertException("Multiple of XOR boolean expressions is true.");
      }
    }
    if (!isFound) raise(() -> "Any of XOR boolean expressions is true.");
  }

  //region matchPattern

  public static void matchPattern(String text, String pattern) {
    Pattern p = Pattern.compile(pattern);
    matchPattern(text, p);
  }

  public static void matchPattern(String text, String pattern, String message) {
    Pattern p = Pattern.compile(pattern);
    matchPattern(text, p, message);
  }

  public static void matchPattern(String text, Pattern pattern) {
    matchPattern(text, pattern,
            sf("Validation: Value '%s' does not match pattern '%s'.", text, pattern.toString()));
  }

  public static void matchPattern(String text, Pattern pattern, String message) {
    Matcher m = pattern.matcher(text);
    if (!m.find()) raise(() -> message);
  }

  //endregion matchPattern

  //region Private methods

  private static void raise(String baseText, ErrorMessageProducer errorMessageProducer) {
    String errorMessage;
    try {
      errorMessage = errorMessageProducer.produce();
    } catch (Exception e) {
      throw new EAssertRaiseException(e);
    }
    throw new EAssertException(baseText + errorMessage);
  }

  private static void raise(ExceptionProducer exceptionOnFailProducer) {
    RuntimeException exception;
    try {
      exception = exceptionOnFailProducer.produce();
    } catch (Exception e) {
      throw new EAssertRaiseException(e);
    }
    throw exception;
  }

  private static void raise(ErrorMessageProducer errorMessageProducer) {
    String errorMessage;
    try {
      errorMessage = errorMessageProducer.produce();
    } catch (Exception e) {
      throw new EAssertRaiseException(e);
    }
    throw new EAssertException(errorMessage);
  }

  // endregion Private methods
}
