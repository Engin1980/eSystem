package eng.eSystem.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class Validator {
  public static void isNotNull(Object value){
    if (value == null)
      throw new ValidationException("Value is null.");
  }

  public static void matchPattern(String text, String pattern){
    Pattern p = Pattern.compile(pattern);
    matchPattern(text,p);
  }

  public static void matchPattern(String text, Pattern pattern){
    Matcher m = pattern.matcher(text);
    if (m.find() == false)
      throw new ValidationException(sf("Value '%s' does not match pattern '%s'.", text, pattern.toString()));
  }
}
