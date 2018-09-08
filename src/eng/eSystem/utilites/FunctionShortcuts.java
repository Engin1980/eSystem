package eng.eSystem.utilites;

public class FunctionShortcuts {

  private FunctionShortcuts() {
  }

  public static String sf(String format, Object... params) {
    return String.format(format, params);
  }

  public static <T> T coalesce(T... items) {
    T ret;
    if (items == null) return null;
    for (int i = 0; i < items.length; i++) {
      if (items[i] != null) return items[i];
    }
    return null;
  }
}
