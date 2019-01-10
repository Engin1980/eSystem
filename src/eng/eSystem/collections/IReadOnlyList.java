package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.utilites.Selector;
import eng.eSystem.validation.Validator;

import java.util.List;
import java.util.function.Predicate;

public interface IReadOnlyList<T> extends ICollection<T> {

  T get(int index);

  default T tryGet(int index) {
    return tryGet(index, null);
  }

  default T tryGet(int index, T defaultValue) {
    if (index < 0 || index >= this.size())
      return defaultValue;
    else
      return this.get(index);
  }

  default IReadOnlyList<T> get(int fromIndex, int toIndex){
    Validator.check(fromIndex >= 0,
        new IllegalArgumentException("{fromIndex} must be greater or equal zero (is " + fromIndex + ")."));
    Validator.check(fromIndex < toIndex,
        new IllegalArgumentException("{fromIndex} must be smaller than {toIndex} (" + fromIndex + " < " + toIndex + ")."));
    Validator.check(toIndex <= this.size(),
        new IllegalArgumentException("{toIndex} must be smaller than {size} (" + fromIndex + " < " + this.size() + ")."));
    IList<T> ret = new EList<>();

    for (int i = fromIndex; i < toIndex; i++) {
      ret.add(this.get(i));
    }

    return ret;
  }

  IList<T> where(Predicate<T> predicate);

  <V> IList<V> select(Selector<T, V> selector);

  List<T> toList();

  void toList(List<T> target);

  default T getRandom() {
    if (this.isEmpty())
      throw new ElementNotFoundException();

    int index = (int) (Math.random() * this.size());
    T ret = this.get(index);
    return ret;
  }

  default T tryGetRandom() {
    T ret;
    if (this.isEmpty())
      ret = null;
    else
      ret = this.getRandom();
    return ret;
  }

  default int getIndexOf(T item) {
    Integer ret = tryGetIndexOf(item);
    if (ret == null)
      throw new ElementNotFoundException();
    else
      return ret;
  }

  Integer tryGetIndexOf(T item);

  default int getIndexOf(Predicate<T> predicate) {
    Integer ret = tryGetIndexOf(predicate);
    if (ret == null)
      throw new ElementNotFoundException();
    else
      return ret;
  }

  Integer tryGetIndexOf(Predicate<T> predicate);

  IList<T> whereItemClassIs(Class clazz, boolean includeInheritance);

  default IList<T> distinct() {
    return distinct(q -> q);
  }

  <K> IList<T> distinct(Selector<T, K> selector);

  <K> ISet<T> getDuplicateItems(Selector<T, K> selector);

  <K extends Comparable<K>> IList<T> orderBy(Selector<T, K> selector, boolean reverse);

  default <K extends Comparable<K>> IList<T> orderBy(Selector<T, K> selector) {
    return this.orderBy(selector, false);
  }

  IList<T> union(IReadOnlyList<T> otherList);

  IList<T> intersection(IReadOnlyList<T> otherList);

}
