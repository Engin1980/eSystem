package eng.eSystem.utilites;

import eng.eSystem.collections.IList;
import eng.eSystem.functionalInterfaces.Selector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class CollectionUtils {

  public static <T> T tryGetFirst(Iterable<T> lst, Predicate<T> predicate) {
    T ret = null;
    for (T t : lst) {
      if (predicate.test(t)) {
        ret = t;
        break;
      }
    }
    return ret;
  }

  public static <T> T tryGetFirst(Iterable<T> lst){
    return tryGetFirst(lst, o -> true);
  }

  public static <T> T tryGetLast(Iterable<T> lst, Predicate<T> predicate) {
    T ret = null;
    for (T t : lst) {
      if (predicate.test(t)) {
        ret = t;
      }
    }
    return ret;
  }

  public static <T> T tryGetLast(Iterable<T> lst){
    return tryGetLast(lst, o -> true);
  }

  public static <T> double sum(Iterable<T> lst, Selector<T, Double> selector) {
    double sum = 0;
    for (T t : lst) {
      sum += selector.getValue(t);
    }
    return sum;
  }

  public static <T,K> List<K> select(Iterable <T> lst, Selector<T,K> selector){
    List<K> ret = new ArrayList<>();
    for (T t : lst) {
      K k = selector.getValue(t);
      ret.add(k);
    }
    return ret;
  }

  public static <T> List<T> where(Iterable<T> lst, Predicate<T> predicate) {
    List<T> ret = new ArrayList<>();

    for (T t : lst) {
      if (predicate.test(t))
        ret.add(t);
    }

    return ret;
  }

  public static <T> boolean containsSameItems (IList<T> a, IList<T> b){
    return containsSameItems(a.toJavaList(), b.toJavaList());
  }

  public static <T> boolean containsSameItems (List<T> a, List<T> b){
    if (a.size() != b.size())
      return false;

    List<T> ac = new ArrayList<>(a);
    List<T> bc = new ArrayList<>(b);

    while (ac.size() > 0){
      T it = ac.get(0);
      if (bc.contains(it)){
        ac.remove(it);
        bc.remove(it);
      }
      else
        return false;
    }

    return true;
  }

  /**
   * Gets random element from the list.
   *
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

  /**
   * Gets random element from the list.
   *
   * @param lst                List instance
   * @param customRandomObject Custom random object
   * @param <T>                Type of list item
   * @return
   */
  public static <T> T getRandom(@NotNull java.util.List<T> lst, @NotNull Random customRandomObject) {

    if (lst == null) {
      throw new IllegalArgumentException("Argument \"lst\" cannot be null.");
    }
    if (lst.isEmpty()) {
      throw new IllegalArgumentException("Argument \"lst\" cannot be empty list.");
    }

    int rnd = (int) (customRandomObject.nextDouble() * lst.size());
    T ret = lst.get(rnd);
    return ret;

  }
}
