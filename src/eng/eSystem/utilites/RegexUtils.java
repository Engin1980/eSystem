package eng.eSystem.utilites;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
  public static String extractGroupContent(String text, String regex, int groupIndex) {
    String ret;
    try {
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(text);
      m.find();
      ret = m.group(groupIndex);
    } catch (Exception ex) {
      throw new IllegalArgumentException(
          "Unable to extract group " + groupIndex + " of pattern " + regex + " from " + text + ".", ex);
    }
    return ret;
  }

  public static String[] extractGroups(String text, String regex) {
    String[] ret;
    try {
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(text);
      m.find();
      ret = new String[m.groupCount()];
      for (int i = 0; i < ret.length; i++) {
        ret[i] = m.group(i);
      }
    } catch (Exception ex) {
      throw new IllegalArgumentException(
          "Unable to extract groups of pattern " + regex + " from " + text + ".", ex);
    }
    return ret;
  }
}
