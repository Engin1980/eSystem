package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.utilites.ObjectUtils;
import eng.eSystem.utilites.Selector;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
  public void add(Iterable<? extends T> items) {
    for (T item : items) {
      inner.add(item);
    }
  }

  @Override
  public void add(T[] items) {
    for (T item : items) {
      inner.add(item);
    }
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
    if (item.getClass().equals(int.class)) {
      inner.remove((Integer) item);
    } else {
      inner.remove(item);
    }
  }

  @Override
  public void remove(T item) {
    if (item.getClass().equals(int.class)) {
      inner.remove((Integer) item);
    } else {
      if (inner.contains(item) == false)
        throw new ElementNotFoundException(item);
      else
        inner.remove(item);
    }
  }

  @Override
  public void remove(Iterable<? extends T> items) {
    for (T item : items) {
      this.remove(item);
    }
  }

  @Override
  public void remove(Predicate<T> predicate) {
    IList<T> tmp = this.where(predicate);
    for (T t : tmp) {
      this.remove(t);
    }
  }

  @Override
  public void retain(Predicate<T> predicate) {
    IList<T> tmp = this.where(predicate.negate());
    for (T t : tmp) {
      this.remove(t);
    }
  }

  @Override
  public void clear() {
    this.inner.clear();
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
  public T tryGetFirst(Predicate<T> predicate) {
    T ret = null;
    for (T t : inner) {
      if (predicate.test(t)) {
        ret = t;
        break;
      }
    }
    return ret;
  }

  @Override
  public T getFirst(Predicate<T> predicate) {
    T ret = null;
    boolean isFound = false;
    for (T t : inner) {
      if (predicate.test(t)) {
        isFound = true;
        ret = t;
        break;
      }
    }
    if (!isFound)
      throw new ElementNotFoundException();
    return ret;
  }

  @Override
  public T tryGetLast(Predicate<T> predicate) {
    T ret = null;
    for (T t : inner) {
      if (predicate.test(t)) {
        ret = t;
      }
    }
    return ret;
  }

  @Override
  public T getLast(Predicate<T> predicate) {
    T ret = null;
    boolean isFound = false;
    for (T t : inner) {
      if (predicate.test(t)) {
        ret = t;
        isFound = true;
      }
    }
    if (!isFound)
      throw new ElementNotFoundException();
    return ret;
  }

  @Override
  public boolean isAny(Predicate<T> predicate) {
    if (predicate == null) {
      throw new IllegalArgumentException("Value of {predicate} cannot not be null.");
    }

    boolean ret = this.inner.stream().anyMatch(predicate);
    return ret;
  }

  @Override
  public boolean isAll(Predicate<T> predicate) {
    if (predicate == null) {
      throw new IllegalArgumentException("Value of {predicate} cannot not be null.");
    }

    boolean ret = this.inner.stream().allMatch(predicate);
    return ret;
  }

  @Override
  public double sum(Selector<T, Double> selector) {
    double ret = 0;
    for (T t : inner) {
      ret += selector.getValue(t);
    }
    return ret;
  }

  @Override
  public double min(Selector<T, Double> selector) {
    double ret = Double.MAX_VALUE;
    for (T t : inner) {
      ret = Math.min(ret, selector.getValue(t));
    }
    return ret;
  }

  @Override
  public double max(Selector<T, Double> selector) {
    double ret = Double.MIN_VALUE;
    for (T t : inner) {
      ret = Math.max(ret, selector.getValue(t));
    }
    return ret;
  }

  @Override
  public int count(Predicate<T> predicate) {
    int ret = 0;
    for (T t : inner) {
      if (predicate.test(t))
        ret++;
    }
    return ret;
  }

  @Override
  public <V> IList<V> select(Selector<T, V> selector) {
    IList<V> ret = new EList<>();
    for (T t : inner) {
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
  public T[] toArray(Class<T> clazz) {
    T[] ret = (T[]) Array.newInstance(clazz, this.size());
    for (int i = 0; i < this.size(); i++) {
      ret[i] = get(i);
    }
    return ret;
  }

  @Override
  public void toList(List<T> target) {
    target.addAll(this.inner);
  }

  @Override
  public T getRandom() {
    if (this.isEmpty())
      throw new ElementNotFoundException();

    int index = (int) (Math.random() * this.size());
    T ret = this.get(index);
    return ret;
  }

  @Override
  public T tryGetRandom() {
    T ret;
    if (this.isEmpty())
      ret = null;
    else
      ret = this.getRandom();
    return ret;
  }

  @Override
  public int getIndexOf(T item) {
    Integer ret = tryGetIndexOf(item);
    if (ret == null)
      throw new ElementNotFoundException();
    else
      return ret;
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
  public int getIndexOf(Predicate<T> predicate) {
    Integer ret = tryGetIndexOf(predicate);
    if (ret == null)
      throw new ElementNotFoundException();
    else
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
  public int size() {
    return inner.size();
  }

  @Override
  public boolean isEmpty() {
    return this.inner.isEmpty();
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
    return inner.equals(o);
  }

  @Override
  public String toString() {
    return String.format("EList{%d items}", this.size());
  }

}
