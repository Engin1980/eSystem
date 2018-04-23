package eng.eSystem.utilites;

public class StringUtils {

  public static boolean isNullOrEmpty(String value){
    boolean ret;
    if (value == null)
      ret = true;
    else if (value.length() == 0)
      ret = true;
    else
      ret = false;
    return ret;
  }

  public static boolean isNullOrWhitespace(String value){
    boolean ret;
    if (value == null)
      ret = true;
    else if (value.trim().length() == 0)
      ret = true;
    else
      ret = false;
    return ret;
  }

  private StringUtils() {
  }
}
