package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.utilites.Action;
import eng.eSystem.utilites.Selector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ESet<T> implements ISet<T> {

  private final static Class DEFAULT_CLASS = HashSet.class;

  private Set<T> inner;

  public ESet(Class innerType) {
    this(innerType, null);
  }

  public ESet(Iterable<? extends T> elements) {
    this(DEFAULT_CLASS, elements);
  }

  public ESet() {
    this(DEFAULT_CLASS, null);
  }

  public ESet(Class innerType, Iterable<? extends T> content) {
    try {
      this.inner = (Set) innerType.newInstance();
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
  public void remove(T item) {
    inner.remove(item);
  }

  @Override
  public void remove(Iterable<? extends T> items) {
    for (T item : items) {
      this.remove(item);
    }
  }

  @Override
  public void remove(Predicate<T> predicate) {
    ISet<T> tmp = this.where(predicate);
    for (T t : tmp) {
      this.remove(tmp);
    }
  }

  @Override
  public void retain(Predicate<T> predicate) {
    ISet<T> tmp = this.where(predicate.negate());
    for (T t : tmp) {
      this.remove(tmp);
    }
  }

  @Override
  public void clear() {
    inner.clear();
  }

  @Override
  public ISet<T> where(Predicate<T> predicate) {
    ESet<T> ret = new ESet<>();
    ret.inner = this.inner.stream().filter(predicate).collect(Collectors.toSet());
    return ret;
  }

  @Override
  public T tryGet(Predicate<T> predicate) {
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
  public T get(Predicate<T> predicate) {
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
  public <V> ISet<V> select(Selector<T, V> selector) {
    ISet<V> ret = new ESet<>();
    for (T t : inner) {
      V v = selector.getValue(t);
      ret.add(v);
    }
    return ret;
  }

  @Override
  public Set<T> toSet() {
    Set<T> ret = new HashSet<>();
    ret.addAll(this.inner);
    return ret;
  }

  @Override
  public void toSet(Set<T> target) {
    target.addAll(this.inner);
  }

  @Override
  public int size() {
    return this.inner.size();
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
  public boolean contains(T item) {
    return this.inner.contains(item);
  }

  @Override
  public void forEach(Action<T> action) {
    for (T t : inner) {
      action.apply(t);
    }
  }

  @Override
  public Iterator<T> iterator() {
    return this.inner.iterator();
  }
}
