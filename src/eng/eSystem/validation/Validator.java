package eng.eSystem.validation;

import eng.eSystem.Producer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class Validator {
  public static void isNotNull(Object value){
    Validator.isNotNull(value, "");
  }

  public static void isNotNull(Object value, String message){
    if (value == null)
      throw new ValidationException("Validation: Value is null. " + message);
  }

  public static void matchPattern(String text, String pattern){
    Pattern p = Pattern.compile(pattern);
    matchPattern(text,p);
  }

  public static void matchPattern(String text, Pattern pattern){
    Matcher m = pattern.matcher(text);
    if (m.find() == false)
      throw new ValidationException(sf("Validation: Value '%s' does not match pattern '%s'.", text, pattern.toString()));
  }

  public static <T> void check(Producer<Boolean> check){
    if (check.produce() == false)
      throw new ValidationException("Validation: Value check failed.");
  }

  public static void check(boolean value){
    if (value == false)
      throw new ValidationException("Validation: Value check failed.");
  }
}
