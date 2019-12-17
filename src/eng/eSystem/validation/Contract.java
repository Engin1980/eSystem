package eng.eSystem.validation;

import eng.eSystem.Producer;
import eng.eSystem.collections.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class Contract {
  public static void isNotNull(Object value){
    Contract.isNotNull(value, "");
  }

  public static void isNotNull(Object value, String message){
    if (value == null)
      throw new ContractException("Validation: Value is null. " + message);
  }

  public static void matchPattern(String text, String pattern){
    Pattern p = Pattern.compile(pattern);
    matchPattern(text,p);
  }

  public static void matchPattern(String text, Pattern pattern){
    Matcher m = pattern.matcher(text);
    if (m.find() == false)
      throw new ContractException(sf("Validation: Value '%s' does not match pattern '%s'.", text, pattern.toString()));
  }

  public static <T> void check(Producer<Boolean> check){
    Contract.check(check, new ContractException("Validation: Value check failed."));
  }

  public static <T> void check(Producer<Boolean> check, RuntimeException exceptionOnFail){
    if (exceptionOnFail == null) throw new IllegalArgumentException("Fail exception cannot be null.");
    if (check.produce() == false)
      throw exceptionOnFail;
  }

  public static void check(boolean value){
    Contract.check(value, new ContractException("Validation: Value check failed."));
  }

  public static void check(boolean value, RuntimeException exceptionOnFail){
    if (exceptionOnFail == null) throw new IllegalArgumentException("Fail exception cannot be null.");
    if (value == false)
      throw exceptionOnFail;
  }
}
