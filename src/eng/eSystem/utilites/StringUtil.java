package eng.eSystem.utilites;

public class StringUtil {
  public static boolean isEmpty(java.lang.String s){
    return s == null || s.isEmpty();
  }
  public static boolean isEmptyOrWhitespace(java.lang.String s){
    return s == null || s.trim().isEmpty();
  }
}
