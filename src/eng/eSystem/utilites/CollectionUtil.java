package eng.eSystem.utilites;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CollectionUtil {
  public interface NumberSelector<T> {
    double getValue(T item);
  }

  public static <T> T tryGetFirst(Iterable<T> lst, Predicate<T> predicate){
    T ret = null;
    for (T t : lst) {
      if (predicate.test(t)){
        ret = t;
        break;
      }
    }
    return ret;
  }
  public static <T> double sum(Iterable<T> lst, NumberSelector<T> selector){
    double sum = 0;
    for (T t : lst) {
      sum += selector.getValue(t);
    }
    return sum;
  }

  public static <T>List<T> where(Iterable<T> lst, Predicate<T> predicate){
    List<T> ret = new ArrayList<>();

    for (T t : lst) {
      if (predicate.test(t))
        ret.add(t);
    }

    return ret;
  }

  /**
   * Gets random element from the list.
   * @param lst List instance
   * @param <T> Type of list item
   * @return
   */
  public static <T> T getRandom(@NotNull java.util.List<T> lst) {

    if (lst == null) {
      throw new IllegalArgumentException("Argument \"lst\" cannot be null.");
    }
    if (lst.isEmpty()) {
      throw new IllegalArgumentException("Argument \"lst\" cannot be empty list.");
    }

    int rnd = (int) (Math.random() * lst.size());
    T ret = lst.get(rnd);
    return ret;

  }
}
