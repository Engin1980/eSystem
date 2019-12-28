package eng.eSystem.validation;

import eng.eSystem.Producer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class EAssert {

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

  public static void matchPattern(String text, String pattern) {
    Pattern p = Pattern.compile(pattern);
    matchPattern(text, p);
  }

  public static void matchPattern(String text, Pattern pattern) {
    Matcher m = pattern.matcher(text);
    if (!m.find())
      throw new EAssertException(sf("Validation: Value '%s' does not match pattern '%s'.", text, pattern.toString()));
  }

  private static void checkExceptionOnFail(RuntimeException value) {
    if (value == null)
      throw new IllegalArgumentException("E-Assert 'exceptionOnFail' cannot be null.");
  }
}
