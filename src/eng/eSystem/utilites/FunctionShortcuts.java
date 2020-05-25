package eng.eSystem.utilites;

import eng.eSystem.functionalInterfaces.Producer;

public class FunctionShortcuts {

  @SafeVarargs
  public static <T> T coalesce(T... items) {
    if (items == null) return null;
    for (T item : items) {
      if (item != null) return item;
    }
    return null;
  }

  @SafeVarargs
  public static <T> T coalesce(Producer<T>... producers) {
    T ret = null;
    if (producers == null) return null;
    for (Producer<T> producer : producers) {
      ret = producer.produce();
      if (ret != null) break;
    }
    return ret;
  }

  public static String sf(String format, Object... params) {
    return String.format(format, params);
  }

  private FunctionShortcuts() {
  }
}
