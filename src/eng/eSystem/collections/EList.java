package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.utilites.ObjectUtils;
import eng.eSystem.utilites.Selector;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EList<T> implements IList<T> {

  private final static Class DEFAULT_CLASS = ArrayList.class;

  private List<T> inner;

  public EList(Class innerType) {
    this(innerType, null);
  }

  public EList(Iterable<? extends T> elements) {
    this(DEFAULT_CLASS, elements);
  }

  public EList(T[] elements) {
    this(DEFAULT_CLASS, null);
    this.add(elements);
  }

  public EList() {
    this(DEFAULT_CLASS, null);
  }

  public EList(Class innerType, Iterable<? extends T> content) {
    try {
      this.inner = (List) innerType.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException("Unable to create a new instance.", e);
    }
    if (content != null)
      for (T t : content) {
        inner.add(t);
      }
  }

  @Override
  public void add(T item) {
    inner.add(item);
  }

  @Override
  public void insert(int index, T item) {
    this.inner.add(index, item);
  }

  @Override
  public void set(int index, T item) {
    inner.set(index, item);
  }

  @Override
  public void removeAt(int index) {
    inner.remove((int) index);
  }

  @Override
  public void tryRemove(T item) {
    if (item != null && item.getClass().equals(int.class)) {
      inner.remove((Integer) item);
    } else {
      inner.remove(item);
    }
  }

  @Override
  public void remove(T item) {
    if (item != null && item.getClass().equals(int.class)) {
      inner.remove((Integer) item);
    } else {
      if (inner.contains(item) == false)
        throw new ElementNotFoundException(item);
      else
        inner.remove(item);
    }
  }

  @Override
  public void clear() {
    this.inner.clear();
  }

  @Override
  public void reverse() {
    Collections.reverse(this.inner);
  }

  @Override
  public <K extends Comparable<K>> void sort(Selector<T, K> selector) {

    int nullCount = 0;
    EMap<K, IList<T>> tmp = new EMap<>();
    for (T t : inner) {
      if (t == null)
        nullCount++;
      else {
        K key = selector.getValue(t);
        if (tmp.containsKey(key) == false)
          tmp.set(key, new EList<>());
        tmp.get(key).add(t);
      }
    }

    List<K> lst = tmp.getKeys().toList().toList();
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
  public T get(int index) {
    return inner.get(index);
  }

  @Override
  public IList<T> where(Predicate<T> predicate) {
    EList<T> ret = new EList<>();
    ret.inner = this.inner.stream().filter(predicate).collect(Collectors.toList());
    return ret;
  }

  @Override
  public <V> IList<V> select(Selector<T, V> selector) {
    IList<V> ret = new EList<>();
    for (T t : this) {
      V v = selector.getValue(t);
      ret.add(v);
    }
    return ret;
  }

  @Override
  public List<T> toList() {
    List<T> ret = new ArrayList<>();
    ret.addAll(this.inner);
    return ret;
  }

  @Override
  public void toList(List<T> target) {
    target.addAll(this.inner);
  }

  @Override
  public Integer tryGetIndexOf(T item) {
    Integer ret = null;
    for (int i = 0; i < this.inner.size(); i++) {
      if (ObjectUtils.equals(this.inner.get(i), item)) {
        ret = i;
        break;
      }
    }
    return ret;
  }

  @Override
  public Integer tryGetIndexOf(Predicate<T> predicate) {
    Integer ret = null;
    for (int i = 0; i < this.inner.size(); i++) {
      if (predicate.test(this.inner.get(i))) {
        ret = i;
        break;
      }
    }
    return ret;
  }

  @Override
  public IList<T> whereItemClassIs(Class clazz, boolean includeInheritance) {
    IList<T> ret;
    if (includeInheritance)
      ret = this.where(q -> clazz.isAssignableFrom(q.getClass()));
    else
      ret = this.where(q -> q.getClass().equals(clazz));
    return ret;
  }

  @Override
  public <K> IList<T> distinct(Selector<T, K> selector) {
    ISet<K> known = new ESet<>();
    IList<T> ret = new EList<>();
    for (T t : this) {
      K v = selector.getValue(t);
      if (known.contains(v)) continue;
      else {
        known.add(v);
        ret.add(t);
      }
    }
    return ret;
  }

  @Override
  public <K> ISet<T> getDuplicateItems(Selector<T, K> selector) {
    ISet<K> known = new ESet<>();
    ISet<T> ret = new ESet<>();
    for (T t : this) {
      K v = selector.getValue(t);
      if (!known.contains(v)) {
        known.add(v);
      } else if (!ret.contains(t)) {
        ret.add(t);
      }
    }
    return ret;
  }

  public <K extends Comparable<K>> IList<T> orderBy(Selector<T, K> selector, boolean reverse) {
    EList<T> ret = new EList<>(this);
    ret.sort(selector);
    if (reverse)
      ret.reverse();
    return ret;
  }

  @Override
  public IList<T> union(IReadOnlyList<T> otherList) {
    IList<T> ret = new EList<>(this);
    for (T t : otherList) {
      if (ret.contains(t) == false)
        ret.add(t);
    }
    return ret;
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
  public int size() {
    return inner.size();
  }

  @Override
  public boolean contains(T item) {
    return this.inner.contains(item);
  }

  @Override
  public Iterator<T> iterator() {
    return inner.iterator();
  }

  @Override
  public int hashCode() {
    return inner.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof EList)
      return inner.equals(((EList) o).inner);
    else
      return false;
  }

  @Override
  public String toString() {
    return String.format("EList{%d items}", this.size());
  }

  /**
   *
   * @param predicate
   * @param defaultValue
   * @return
   * Overridden due to performance
   */
  public T tryGetLast(Predicate<T> predicate, T defaultValue) {
    T ret;
    if (inner instanceof LinkedList){
      Iterator<T> iter = ((LinkedList<T>) inner).descendingIterator();
      while (iter.hasNext()){
        ret = iter.next();
        if (predicate.test(ret))
          return ret;
      }
      return defaultValue;
    } else if (inner instanceof ArrayList){
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
}
