package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.functionalInterfaces.Function2;
import eng.eSystem.functionalInterfaces.Selector;
import eng.eSystem.validation.EAssert;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface IReadOnlyList<T> extends ICollection<T> {

  default IList<T> distinct() {
    return distinct(q -> q);
  }

  <K> IList<T> distinct(Selector<T, K> selector);

  T get(int index);

  default IReadOnlyList<T> get(int fromIndex, int toIndex) {
    EAssert.isTrue(fromIndex >= 0,
        new IllegalArgumentException("{fromIndex} must be greater or equal zero (is " + fromIndex + ")."));
    EAssert.isTrue(fromIndex < toIndex,
        new IllegalArgumentException("{fromIndex} must be smaller than {toIndex} (" + fromIndex + " < " + toIndex + ")."));
    EAssert.isTrue(toIndex <= this.size(),
        new IllegalArgumentException("{toIndex} must be smaller than {size} (" + fromIndex + " < " + this.size() + ")."));
    IList<T> ret = new EList<>();

    for (int i = fromIndex; i < toIndex; i++) {
      ret.add(this.get(i));
    }

    return ret;
  }

  default <K> IMap<K, IList<T>> groupBy(Selector<T, K> keySelector){
    EMap<K, IList<T>> ret = new EMap<>();

    for (T item : this) {
      K key = keySelector.getValue(item);
      if (!ret.containsKey(key))
        ret.set(key, new EList<>());
      ret.get(key).add(item);
    }

    return ret;
  }

  <K> ISet<T> getDuplicateItems(Selector<T, K> selector);

  default int getIndexOf(T item) {
    Integer ret = tryGetIndexOf(item);
    if (ret == null)
      throw new ElementNotFoundException();
    else
      return ret;
  }

  default int getIndexOf(Predicate<T> predicate) {
    Integer ret = tryGetIndexOf(predicate);
    if (ret == null)
      throw new ElementNotFoundException();
    else
      return ret;
  }

  default T getRandom() {
    if (this.isEmpty())
      throw new ElementNotFoundException();

    int index = (int) (Math.random() * this.size());
    T ret = this.get(index);
    return ret;
  }

  IList<T> intersection(IReadOnlyList<T> otherList);

  IList<T> minus(IReadOnlyList<T> otherList);

  <K extends Comparable<K>> IList<T> orderBy(Selector<T, K> selector, boolean reverse);

  default <K extends Comparable<K>> IList<T> orderBy(Selector<T, K> selector) {
    return this.orderBy(selector, false);
  }

  <V> IList<V> select(Selector<T, V> selector);

  default <K> IList<K> selectMany(Selector<T, IList<K>> selector) {
    EList<K> ret = new EList<>();

    this.forEach(q -> ret.addMany(selector.getValue(q)));

    return ret;
  }

  List<T> toJavaList();

  void toJavaList(List<T> target);

  ISet<T> toSet();

  default T tryGet(int index) {
    return tryGet(index, null);
  }

  default T tryGet(int index, T defaultValue) {
    if (index < 0 || index >= this.size())
      return defaultValue;
    else
      return this.get(index);
  }

  Integer tryGetIndexOf(T item);

  Integer tryGetIndexOf(Predicate<T> predicate);

  default T tryGetRandom() {
    T ret;
    if (this.isEmpty())
      ret = null;
    else
      ret = this.getRandom();
    return ret;
  }

  IList<T> union(IReadOnlyList<T> otherList);

  IList<T> where(Predicate<T> predicate);

  <V> IList<V> whereItemClassIs(Class<? extends V> clazz, boolean includeInheritance);

  IReadOnlyList<T> toReversed();
}
