package eng.eSystem.validation;

import eng.eSystem.Producer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static eng.eSystem.utilites.FunctionShortcuts.coalesce;
import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class EAssert {

  public static class Argument {

    public static void isNonemptyString(String value) {
      EAssert.isNonemptyString(value, "Argument cannot be null or empty string. ");
    }

    public static void isNonemptyString(String value, String argumentName) {
      EAssert.isNonemptyString(value, sf("Argument '%s' cannot be null or empty string.", argumentName));
    }

    public static void isNotNull(Object value) {
      EAssert.isNotNull(value, "Argument cannot be null.");
    }

    public static void isNotNull(Object value, String argumentName) {
      EAssert.isNotNull(value, sf("Argument '%s' cannot be null.", argumentName));
    }

    public static void isTrue(boolean value) {
      EAssert.Argument.isTrue(value, null);
    }

    public static void isTrue(boolean value, String message) {
      EAssert.isTrue(value, "Argument does not fulfil requirement. " + coalesce(message, ""));
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
  private static final String TEXT_NOT_NULL = TEXT_ERR + "Value should not be null.";
  private static final String TEXT_NOT_TRUE = TEXT_ERR + "Expression should be true.";
  private static final String TEXT_NOT_FALSE = TEXT_ERR + "Expression should be false.";
  private static final String TEXT_EMPTY_STRING = TEXT_ERR + "String expression cannot be null or zero-lenght.";

  public static void isFalse(Producer<Boolean> check) {
    EAssert.isTrue(check, TEXT_NOT_FALSE);
  }

  public static void isFalse(Producer<Boolean> check, String errorMessage) {
    EAssert.isFalse(check, new EAssertException(errorMessage));
  }

  public static void isFalse(Producer<Boolean> check, RuntimeException exceptionOnFail) {
    checkExceptionOnFail(exceptionOnFail);
    if (check.produce()) throw exceptionOnFail;
  }

  public static void isFalse(boolean value) {
    EAssert.isFalse(value, TEXT_NOT_FALSE);
  }

  public static void isFalse(boolean value, String errorMessage) {
    EAssert.isFalse(value, new EAssertException(errorMessage));
  }

  public static void isFalse(boolean value, RuntimeException exceptionOnFail) {
    checkExceptionOnFail(exceptionOnFail);
    if (value) throw exceptionOnFail;
  }

  public static void isNonemptyString(String value) {
    EAssert.isNotNull(value, TEXT_EMPTY_STRING);
    EAssert.isTrue(value.length() > 0, TEXT_EMPTY_STRING);
  }

  public static void isNonemptyString(String value, String message) {
    EAssert.isNotNull(value, message);
    EAssert.isTrue(value.length() > 0, message);
  }

  public static void isNonemptyString(String value, RuntimeException exceptionOnFail) {
    EAssert.isNotNull(value, exceptionOnFail);
    EAssert.isTrue(value.length() > 0, exceptionOnFail);
  }

  public static void isNotNull(Object value) {
    EAssert.isNotNull(value, TEXT_NOT_NULL);
  }

  public static void isNotNull(Object value, String message) {
    isNotNull(value, new EAssertException(message));
  }

  public static void isNotNull(Object value, RuntimeException exceptionOnFail) {
    checkExceptionOnFail(exceptionOnFail);
    if (value == null) throw exceptionOnFail;
  }

  public static void isTrue(Producer<Boolean> check) {
    EAssert.isTrue(check, TEXT_NOT_TRUE);
  }

  public static void isTrue(Producer<Boolean> check, String errorMessage) {
    EAssert.isTrue(check, new EAssertException(errorMessage));
  }

  public static void isTrue(Producer<Boolean> check, RuntimeException exceptionOnFail) {
    checkExceptionOnFail(exceptionOnFail);
    if (!check.produce()) throw exceptionOnFail;
  }

  public static void isTrue(boolean value) {
    EAssert.isTrue(value, TEXT_NOT_TRUE);
  }

  public static void isTrue(boolean value, String errorMessage) {
    EAssert.isTrue(value, new EAssertException(errorMessage));
  }

  public static void isTrue(boolean value, RuntimeException exceptionOnFail) {
    checkExceptionOnFail(exceptionOnFail);
    if (!value) throw exceptionOnFail;
  }

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
    if (!isFound)
      throw new EAssertException("Any of XOR boolean expressions is true.");
  }

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
    if (!m.find())
      throw new EAssertException(message);
  }

  private static void checkExceptionOnFail(RuntimeException value) {
    if (value == null)
      throw new IllegalArgumentException("E-Assert 'exceptionOnFail' cannot be null.");
  }
}
