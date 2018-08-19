package eng.eSystem.utilites;

public class StringUtils {

  public static boolean isNullOrEmpty(String value){
    boolean ret = value == null || value.length() == 0;
    return ret;
  }

  public static boolean isNullOrWhitespace(String value){
    boolean ret = value == null || value.trim().length() == 0;
    return ret;
  }

  private StringUtils() {
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
