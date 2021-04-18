package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.functionalInterfaces.Selector;
import eng.eSystem.utilites.ObjectUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EList<T> implements IList<T> {

  private final static Class<? extends java.util.List> DEFAULT_CLASS = ArrayList.class;

  public static <T> EList<T> of(Iterable<T> items) {
    EList<T> ret = new EList<>();
    ret.addMany(items);
    return ret;
  }

  public static <T> EList<T> of(T... items) {
    EList<T> ret = new EList<>();
    ret.addMany(items);
    return ret;
  }

  private final List<T> inner;

  //region .ctor

  public EList() {
    this(DEFAULT_CLASS);
  }

  public EList(Class<? extends java.util.List> innerType) {
    this.inner = (java.util.List<T>) Common.provideInstance(innerType);
  }

  //endregion

  //region Instance methods

  @Override
  public void add(T item) {
    inner.add(item);
  }

  @Override
  public void clear() {
    this.inner.clear();
  }

  @Override
  public boolean contains(T item) {
    return this.inner.contains(item);
  }

  @Override
  public <K> IList<T> distinct(Selector<T, K> selector) {
    ISet<K> known = new ESet<>();
    IList<T> ret = new EList<>();
    for (T t : this) {
      K v = selector.invoke(t);
      if (!known.contains(v)) {
        known.add(v);
        ret.add(t);
      }
    }
    return ret;
  }

  @Override
  public boolean equals(Object o) {
    return this == o;
  }

  @Override
  public T get(int index) {
    return inner.get(index);
  }

  @Override
  public <K> ISet<T> getDuplicateItems(Selector<T, K> selector) {
    ISet<K> known = new ESet<>();
    ISet<T> ret = new ESet<>();
    for (T t : this) {
      K v = selector.invoke(t);
      if (!known.contains(v)) {
        known.add(v);
      } else if (!ret.contains(t)) {
        ret.add(t);
      }
    }
    return ret;
  }

  @Override
  public int hashCode() {
    return inner.hashCode();
  }

  @Override
  public void insert(int index, T item) {
    this.inner.add(index, item);
  }

  @Override
  public IList<T> intersection(IReadOnlyList<T> otherCollection) {
    IList<T> ret = new EList<>();
    for (T t : this) {
      if (otherCollection.contains(t))
        ret.add(t);
    }
    return ret;
  }

  @Override
  public Iterator<T> iterator() {
    return inner.iterator();
  }

  @Override
  public IList<T> minus(IReadOnlyList<T> otherCollection) {
    IList<T> ret = new EList<>();
    for (T item : this) {
      if (!otherCollection.contains(item))
        ret.add(item);
    }
    return ret;
  }

  public <K extends Comparable<K>> IList<T> orderBy(Selector<T, K> selector, boolean reverse) {
    IList<T> ret = new EList<T>().with(this);
    ret.sort(selector);
    if (reverse)
      ret.reverse();
    return ret;
  }


  @Override
  public void remove(T item) {
    if (!inner.contains(item))
      throw new ElementNotFoundException(item);
    else
      inner.remove(item);
  }

  @Override
  public void removeAt(int index) {
    inner.remove(index);
  }

  @Override
  public void reverse() {
    Collections.reverse(this.inner);
  }

  @Override
  public <V> IList<V> select(Selector<T, V> selector) {
    IList<V> ret = new EList<>();
    for (T t : this) {
      V v = selector.invoke(t);
      ret.add(v);
    }
    return ret;
  }

  @Override
  public void set(int index, T item) {
    inner.set(index, item);
  }

  @Override
  public void shuffle(Random rnd) {
    Collections.shuffle(this.inner, rnd);
  }

  @Override
  public int size() {
    return inner.size();
  }

  @Override
  public void slice(Predicate<Integer> indexSelector) {
    IList<Integer> indicesToRemove = new EList<>();
    for (int i = this.inner.size() - 1; i >= 0; i--) {
      if (indexSelector.test(i)) indicesToRemove.add(i);
    }
    for (int i = 0; i < indicesToRemove.size(); i++) {
      this.removeAt(indicesToRemove.get(i));
    }
  }

  @Override
  public <K extends Comparable<K>> void sort(Selector<T, K> selector) {

    int nullCount = 0;
    EMap<K, IList<T>> tmp = new EMap<>();
    for (T t : inner) {
      if (t == null)
        nullCount++;
      else {
        K key = selector.invoke(t);
        if (tmp.containsKey(key) == false)
          tmp.set(key, new EList<>());
        tmp.get(key).add(t);
      }
    }

    List<K> lst = tmp.getKeys().toList().toJavaList();
    Collections.sort(lst);

    this.inner.clear();
    for (int i = 0; i < nullCount; i++) {
      this.inner.add(null);
    }

    for (K k : lst) {
      IList<T> val = tmp.get(k);
      for (T t : val) {
        this.inner.add(t);
      }
    }
  }

  @Override
  public void sort(Comparator<T> comparator) {
    java.util.Collections.sort(this.inner, comparator);
  }

  @Override
  public List<T> toJavaList() {
    List<T> ret = new ArrayList<>(this.inner);
    return ret;
  }

  @Override
  public void toJavaList(List<T> target) {
    target.addAll(this.inner);
  }

  @Override
  public void toJavaSet(Set<T> target) {
    target.addAll(this.inner);
  }

  @Override
  public IList<T> toList() {
    IList<T> ret = new EList<T>().with(this);
    return ret;
  }

  @Override
  public IReadOnlyList<T> toReversed() {
    IList<T> ret = new EList<T>().with(this);
    ret.reverse();
    return ret;
  }

  @Override
  public ISet<T> toSet() {
    ISet<T> ret = new ESet<T>().with(this.inner);
    return ret;
  }

  @Override
  public IReadOnlyList<T> toShuffled() {
    IList<T> ret = new EList<T>().with(this);
    ret.shuffle();
    return ret;
  }

  @Override
  public String toString() {
    return String.format("EList{%d items}", this.size());
  }

  @Override
  public Optional<T> tryGetLast(Predicate<T> predicate) {

    if (inner instanceof LinkedList) {
      T tmp;
      Iterator<T> iter = ((LinkedList<T>) inner).descendingIterator();
      while (iter.hasNext()) {
        tmp = iter.next();
        if (predicate.test(tmp))
          return Optional.of(tmp);
      }
      return Optional.empty();
    } else if (inner instanceof ArrayList) {
      T tmp;
      for (int i = this.size() - 1; i >= 0; i--) {
        tmp = this.get(i);
        if (predicate.test(tmp))
          return Optional.of(tmp);
      }
      return Optional.empty();
    } else {
      Optional<T> ret = Optional.empty();
      for (T t : this) {
        if (predicate.test(t))
          ret = Optional.of(t);
      }
      return ret;
    }
  }

  @Override
  public OptionalInt tryIndexOf(T item) {
    for (int i = 0; i < this.inner.size(); i++) {
      if (ObjectUtils.equals(this.inner.get(i), item)) {
        return OptionalInt.of(i);
      }
    }
    return OptionalInt.empty();
  }

  @Override
  public OptionalInt tryIndexOf(Predicate<T> predicate) {
    for (int i = 0; i < this.inner.size(); i++) {
      if (predicate.test(this.inner.get(i))) {
        return OptionalInt.of(i);
      }
    }
    return OptionalInt.empty();
  }

  @Override
  public void tryRemove(T item) {
    inner.remove(item);
  }

  @Override
  public IList<T> union(IReadOnlyList<T> otherCollection) {
    IList<T> ret = new EList<T>().with(this);
    for (T t : otherCollection) {
      if (ret.contains(t) == false)
        ret.add(t);
    }
    return ret;
  }

  @Override
  public IList<T> where(Predicate<T> predicate) {
    IList<T> ret = new EList<T>().with(
            this.inner.stream().filter(predicate).collect(Collectors.toList()));
    return ret;
  }
  // endregion
}
