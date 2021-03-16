package eng.eSystem.collection2;

import eng.eSystem.collection2.subinterfaces.IReadOnlyShared;
import eng.eSystem.functionalInterfaces.Selector;

import java.util.Optional;
import java.util.Random;

public interface IReadOnlySet<T> extends ICollection<T>, IReadOnlyShared<IReadOnlySet<T>, T, ISet<T>> {
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

  default Optional<T> tryFind(int index){
    int c = 0;
    for (T t : this) {
      if (c == index)
        return Optional.of(t);
      c++;
    }
    return Optional.empty();
  }

  default T find(int index) {
    Optional<T> tmp = tryFind(index);
    if (tmp.isEmpty())
      throw new IndexOutOfBoundsException("Not enough items in the set (requested " + index + "), available " + this.size() + ").");
    else
      return tmp.get();
  }

  //TODO nemá toto raději vracet list?
  <V> ISet<V> select(Selector<T, V> selector);

  default <K> ISet<K> selectMany(Selector<T, ISet<K>> selector) {
    ISet<K> ret = new ESet<>();
    this.forEach(q -> ret.addMany(selector.invoke(q)));
    return ret;
  }

  ISet<T> take(int count);

  @Override
  default T getRandom(Random rnd) {
    Common.ensureNotEmpty(this);

    int index = (int) (rnd.nextDouble() * this.size());
    T ret = this.find(index);
    return ret;
  }
}
