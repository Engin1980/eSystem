package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.utilites.Selector;

import java.util.ArrayList;
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
      throw new RuntimeException("Unable to create a new instance.");
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
    this.inner.add(index,item);
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
  public void remove(T item) {
    if (item.getClass().equals(int.class)) {
      inner.remove((Integer) item);
    } else {
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
    boolean ret = this.inner.stream().anyMatch(predicate);
    return ret;
  }

  @Override
  public boolean isAll(Predicate<T> predicate) {
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
  public void toList(List<T> target) {
    target.addAll(this.inner);
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
  public String toString() {
    return String.format("EList{%d items}", this.size());
  }

  @Override
  public int hashCode() {
    return inner.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return inner.equals(o);
  }
}
