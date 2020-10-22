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
    int index = 0;
    for (Producer<T> producer : producers) {
      try {
        ret = producer.invoke();
      } catch (Exception e) {
        throw new IllegalArgumentException(sf("Coalesce(...) failed. Producer at index %d caused an exception.", index), e);
      }
      if (ret != null) break;
      index++;
    }
    return ret;
  }

  public static String sf(String format, Object... params) {
    return String.format(format, params);
  }

  private FunctionShortcuts() {
  }
}
