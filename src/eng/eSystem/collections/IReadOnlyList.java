package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.collections.subinterfaces.IReadOnlyCollection;
import eng.eSystem.functionalInterfaces.Selector;
import eng.eSystem.validation.EAssert;

import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public interface IReadOnlyList<T> extends ICollection<T>, IReadOnlyCollection<IReadOnlyList<T>, T, IList<T>> {

  T get(int index);

  <K> ISet<T> getDuplicateItems(Selector<T, K> selector);

  Optional<Integer> tryIndexOf(T item);

  Optional<Integer> tryIndexOf(Predicate<T> predicate);

  <K extends Comparable<K>> IList<T> orderBy(Selector<T, K> selector, boolean reverse);

  <V> IList<V> select(Selector<T, V> selector);

  IReadOnlyList<T> toReversed();

  IReadOnlyList<T> toShuffled();

  default IList<T> distinct() {
    return distinct(q -> q);
  }

  default IReadOnlyList<T> getMany(int fromIndex, int toIndex) {
    EAssert.isTrue(
            fromIndex >= 0,
            () -> new IllegalArgumentException("{fromIndex} must be greater or equal zero (is " + fromIndex + ")."));
    EAssert.isTrue(
            fromIndex < toIndex,
            () -> new IllegalArgumentException("{fromIndex} must be smaller than {toIndex} (" + fromIndex + " < " + toIndex + ")."));
    EAssert.isTrue(
            toIndex <= this.size(),
            () -> new IllegalArgumentException("{toIndex} must be smaller than {size} (" + fromIndex + " < " + this.size() + ")."));
    EList<T> ret = new EList<>();

    for (int i = fromIndex; i < toIndex; i++) {
      ret.add(this.get(i));
    }

    return ret;
  }

  @Override
  default T getRandom(Random rnd) {
    Common.ensureNotEmpty(this);

    int index = (int) (rnd.nextDouble() * this.size());
    T ret = this.get(index);
    return ret;
  }

  default <K> IMap<K, eng.eSystem.collections.IList<T>> groupBy(Selector<T, K> keySelector) {
    EMap<K, eng.eSystem.collections.IList<T>> ret = new EMap<>();

    for (T item : this) {
      K key = keySelector.invoke(item);
      if (!ret.containsKey(key))
        ret.set(key, new eng.eSystem.collections.EList<>());
      ret.get(key).add(item);
    }

    return ret;
  }

  default int indexOf(T item) {
    Optional<Integer> ret = tryIndexOf(item);
    if (ret.isEmpty())
      throw new ElementNotFoundException();
    else
      return ret.get();
  }

  default int indexOf(Predicate<T> predicate) {
    Optional<Integer> ret = tryIndexOf(predicate);
    if (ret.isEmpty())
      throw new ElementNotFoundException();
    else
      return ret.get();
  }

  default <K extends Comparable<K>> IList<T> orderBy(Selector<T, K> selector) {
    return this.orderBy(selector, false);
  }

  default <K> IList<K> selectMany(Selector<T, IReadOnlyList<K>> selector) {
    EList<K> ret = new EList<>();
    this.forEach(q -> ret.addMany(selector.invoke(q)));
    return ret;
  }

  default Optional<T> tryGet(int index) {
    if (index < 0 || index >= this.size())
      return Optional.empty();
    else
      return Optional.of(this.get(index));
  }

  default <V> IList<V> whereItemClassIs(Class<? extends V> clazz, boolean includeInheritance) {
    IList<V> ret;
    if (includeInheritance)
      ret = this.where(q -> clazz.isAssignableFrom(q.getClass())).select(q -> (V) q);
    else
      ret = this.where(q -> q.getClass().equals(clazz)).select(q -> (V) q);
    return ret;
  }
}
