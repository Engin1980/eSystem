package eng.eSystem.utilites;

import eng.eSystem.EStringBuilder;
import eng.eSystem.collections.EList;
import eng.eSystem.collections.IList;

public class StringUtils {

  /**
   * Gets beginning of the text until the delimiter is found.
   *
   * @param text      Input text
   * @param delimiter Delimiter value
   * @return Beginning of the string until delimiter, or the whole input text if delimiter was not found.
   */
  public static String getUntil(String text, String delimiter) {
    String ret;
    int index = text.indexOf(delimiter);
    if (index <= 0)
      ret = text;
    else
      ret = text.substring(0, index);
    return ret;
  }

  public static boolean isNullOrEmpty(String value) {
    boolean ret = value == null || value.length() == 0;
    return ret;
  }

  public static boolean isNullOrWhitespace(String value) {
    boolean ret = value == null || value.trim().length() == 0;
    return ret;
  }

  public static String join(String delimiter, String... items) {
    IList<String> lst = new EList<>(items);
    return StringUtils.join(delimiter, lst);
  }

  public static String join(String delimiter, Iterable<String> items) {
    EStringBuilder sb = new EStringBuilder();
    sb.appendItems(items, delimiter);
    return sb.toString();
  }

  private StringUtils() {
  }


}
