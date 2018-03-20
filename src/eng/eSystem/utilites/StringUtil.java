package eng.eSystem.utilites;

public class StringUtil {
  public static boolean isEmpty(java.lang.String s){
    return s == null || s.isEmpty();
  }
  public static boolean isEmptyOrWhitespace(java.lang.String s){
    return s == null || s.trim().isEmpty();
  }

  /**
   * Gets beginning of the text until the delimiter is found.
   * @param text Input text
   * @param delimiter Delimiter value
   * @return Beginning of the string until delimiter, or the whole input text if delimiter was not found.
   */
  public static String getUntil(String text, String delimiter){
    String ret;
    int index = text.indexOf(delimiter);
    if (index <= 0)
      ret = text;
    else
      ret = text.substring(0, index);
    return ret;
  }
}
