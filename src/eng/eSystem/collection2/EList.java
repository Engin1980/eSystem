package eng.eSystem.collection2;

import eng.eSystem.collection2.exceptions.ElementNotFoundException;
import eng.eSystem.functionalInterfaces.Selector;
import eng.eSystem.utilites.ObjectUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EList<T>  implements IList<T>{

  private final static Class<? extends java.util.List<?>> DEFAULT_CLASS = (Class<? extends List<?>>) ArrayList.class;

  //region Static methods
  public static <T> EList<T> of(T... elements) {
    EList<T> ret = new EList<>();
    ret.addMany(elements);
    return ret;
  }

  //TODO add for eset
  public static <T> EList<T> of(Iterable<T> items) {
    EList<T> ret = new EList<>();
    ret.addMany(items);
    return ret;
  }
  //endregion

  private final List<T> inner;

  //region .ctor

  private EList(List<T> inner) {
    this.inner = inner;
  }

  public EList() {
    this(DEFAULT_CLASS);
  }

  public EList(Class<? extends java.util.List<?>> innerType) {
    try {
      //TODO resolve obsolete method
      this.inner = (List) innerType.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException("Unable to create a new instance of " + innerType.getName() + ".", e);
    }
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
      return Objects.equals(this, o);
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
  public IList<T> intersection(IReadOnlyList<T> otherList) {
    IList<T> ret = new EList<>();
    for (T t : this) {
      if (otherList.contains(t))
        ret.add(t);
    }
    return ret;
  }

  @Override
  public Iterator<T> iterator() {
    return inner.iterator();
  }

  @Override
  public IList<T> minus(IReadOnlyList<T> otherList) {
    IList<T> ret = new EList<>();
    for (T item : this) {
      if (!otherList.contains(item))
        ret.add(item);
    }
    return ret;
  }

  public <K extends Comparable<K>> IList<T> orderBy(Selector<T, K> selector, boolean reverse) {
    EList<T> ret = EList.of(this);
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
  public IReadOnlyList<T> toReversed() {
    IList<T> ret = new EList<>(this);
    ret.reverse();
    return ret;
  }

  @Override
  public ISet<T> toSet() {
    ISet<T> ret = ESet.with(this.inner);
    return ret;
  }

  @Override
  public IList<T> toList() {
    IList<T> ret = new EList<>(this.inner);
    return ret;
  }

  @Override
  public String toString() {
    return String.format("EList{%d items}", this.size());
  }

  @Override
  public Optional<Integer> tryIndexOf(T item) {
    for (int i = 0; i < this.inner.size(); i++) {
      if (ObjectUtils.equals(this.inner.get(i), item)) {
        return Optional.of(i);
      }
    }
    return Optional.empty();
  }

  @Override
  public Optional<Integer> tryIndexOf(Predicate<T> predicate) {
    for (int i = 0; i < this.inner.size(); i++) {
      if (predicate.test(this.inner.get(i))) {
        return Optional.of(i);
      }
    }
    return Optional.empty();
  }

  @Override
  public Optional<T> tryGetLast(Predicate<T> predicate) {
    T ret;
    if (inner instanceof LinkedList) {
      Iterator<T> iter = ((LinkedList<T>) inner).descendingIterator();
      while (iter.hasNext()) {
        ret = iter.next();
        if (predicate.test(ret))
          return ret;
      }
      return defaultValue;
    } else if (inner instanceof ArrayList) {
      for (int i = this.size() - 1; i >= 0; i--) {
        ret = this.get(i);
        if (predicate.test(ret))
          return ret;
      }
      return defaultValue;
    } else {
      ret = defaultValue;
      for (T t : this) {
        if (predicate.test(t))
          ret = t;
      }
      return ret;
    }
  }

  @Override
  public void tryRemove(T item) {
    inner.remove(item);
  }

  @Override
  public IList<T> union(IReadOnlyList<T> otherList) {
    IList<T> ret = EList.of(this);
    for (T t : otherList) {
      if (ret.contains(t) == false)
        ret.add(t);
    }
    return ret;
  }

  @Override
  public IList<T> where(Predicate<T> predicate) {
    EList<T> ret = new EList<>();
    ret.inner = this.inner.stream().filter(predicate).collect(Collectors.toList());
    return ret;
  }
  // endregion
}
