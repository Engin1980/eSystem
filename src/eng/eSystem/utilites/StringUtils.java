package eng.eSystem.utilites;

import eng.eSystem.EStringBuilder;
import eng.eSystem.collections.EList;
import eng.eSystem.collections.IList;
import eng.eSystem.validation.EAssert;

public class StringUtils {

  private static final int PAD_LIMIT = 10000;

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
    IList<String> lst = EList.of(items);
    return StringUtils.join(delimiter, lst);
  }

  public static String join(String delimiter, Iterable<String> items) {
    EStringBuilder sb = new EStringBuilder();
    sb.appendItems(items, delimiter);
    return sb.toString();
  }

  public static String padLeft(String str, int size, char padChar) {
    EAssert.Argument.isNotNull(str, "str");
    EAssert.Argument.isTrue(size >= 0, "Size must be equal or greater than zero.");
    String tmp = preparePaddingString(str,size,padChar);
    if (tmp != null)
      return tmp.concat(str);
    else
      return str;
  }

  public static String padRight(String str, int size, char padChar) {
    EAssert.Argument.isNotNull(str, "str");
    EAssert.Argument.isTrue(size >= 0, "Size must be equal or greater than zero.");
    String tmp = preparePaddingString(str,size,padChar);
    if (tmp != null)
      return str.concat(tmp);
    else
      return str;
  }

  public static String padRight(String str, int size) {
    return padRight(str, size, ' ');
  }

  private static String preparePaddingString(String str, int size, char padChar) {
    String ret;
    int strLen = str.length();
    int pads = size - strLen;
    if (pads <= 0)
      ret = null; // returns original String when possible
    else {
      char[] padding = new char[pads];
      for (int i = 0; i < pads; i++) {
        padding[i] = padChar;
      }
      ret = new String(padding);
    }
    return ret;
  }

  private StringUtils() {
  }

  public static String repeat(String str, int count){
    EAssert.Argument.isNotNull(str, "str");
    EAssert.Argument.isTrue(count >= 0, "Repeat count must be >= 0.");
    StringBuilder ret = new StringBuilder();
    for (int i = 0; i < count; i++) {
      ret.append(str);
    }
    return ret.toString();
  }

}
