package eng.eSystem.collections;

import eng.eSystem.collections.subinterfaces.IReadOnlyCollection;
import eng.eSystem.functionalInterfaces.Selector;

import java.util.Optional;
import java.util.Random;

public interface IReadOnlySet<T> extends ICollection<T>, IReadOnlyCollection<IReadOnlySet<T>, T, ISet<T>> {
  <V> ISet<V> select(Selector<T, V> selector);

  ISet<T> take(int count);

  default T find(int index) {
    Optional<T> tmp = tryFind(index);
    if (tmp.isEmpty())
      throw new IndexOutOfBoundsException("Not enough items in the set (requested " + index + "), available " + this.size() + ").");
    else
      return tmp.get();
  }

  @Override
  default T getRandom(Random rnd) {
    Common.ensureNotEmpty(this);

    int index = (int) (rnd.nextDouble() * this.size());
    T ret = this.find(index);
    return ret;
  }

  default <K> IMap<K, ISet<T>> groupBy(Selector<T, K> keySelector) {
    EMap<K, ISet<T>> ret = new EMap<>();

    for (T item : this) {
      K key = keySelector.invoke(item);
      if (!ret.containsKey(key))
        ret.set(key, new ESet<>());
      ret.get(key).add(item);
    }

    return ret;
  }

  default <K> ISet<K> selectMany(Selector<T, IReadOnlySet<K>> selector) {
    ISet<K> ret = new ESet<>();
    this.forEach(q -> ret.addMany(selector.invoke(q)));
    return ret;
  }

  default Optional<T> tryFind(int index) {
    int c = 0;
    for (T t : this) {
      if (c == index)
        return Optional.of(t);
      c++;
    }
    return Optional.empty();
  }

  default <V> ISet<V> whereItemClassIs(Class<? extends V> clazz, boolean includeInheritance) {
    ISet<V> ret;
    if (includeInheritance)
      ret = this.where(q -> clazz.isAssignableFrom(q.getClass())).select(q -> (V) q);
    else
      ret = this.where(q -> q.getClass().equals(clazz)).select(q -> (V) q);
    return ret;
  }
}
