package eng.eSystem.validation;

import eng.eSystem.Action;
import eng.eSystem.Producer;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class Validator {
  public static void isNotNull(Object value){
    if (value == null)
      throw new ValidationException("Validation: Value is null.");
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
