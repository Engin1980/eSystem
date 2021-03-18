package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.exceptions.EIllegalArgumentException;
import eng.eSystem.functionalInterfaces.Selector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ESet<T> implements ISet<T> {

  private final static Class<?> DEFAULT_CLASS = HashSet.class;

  private Set<T> inner;

  public ESet(Class<?> innerType) {
    this(innerType, null);
  }

  public ESet(Iterable<? extends T> elements) {
    this(DEFAULT_CLASS, elements);
  }

  public ESet(T[] elements) {
    this(DEFAULT_CLASS, null);
    this.add(elements);
  }

  public ESet() {
    this(DEFAULT_CLASS, null);
  }

  public ESet(Class<?> innerType, Iterable<? extends T> content) {
    try {
      this.inner = (Set<T>) innerType.newInstance();
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
  public void clear() {
    inner.clear();
  }

  @Override
  public boolean contains(T item) {
    return this.inner.contains(item);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ESet && inner.equals(o);
  }

  @Override
  public int hashCode() {
    return inner.hashCode();
  }

  @Override
  public ISet<T> intersection(IReadOnlySet<T> otherSet) {
    ISet<T> ret = new ESet<>();
    for (T t : this) {
      if (otherSet.contains(t))
        ret.add(t);
    }
    return ret;
  }

  @Override
  public Iterator<T> iterator() {
    return this.inner.iterator();
  }

  @Override
  public ISet<T> minus(IReadOnlySet<T> otherList) {
    ISet<T> ret = new ESet<>();
    for (T item : this) {
      if (!otherList.contains(item))
        ret.add(item);
    }
    return ret;
  }

  @Override
  public void remove(T item) {
    if (!inner.contains(item))
      throw new ElementNotFoundException(item);
    inner.remove(item);
  }

  @Override
  public <V> ISet<V> select(Selector<T, V> selector) {
    ISet<V> ret = new ESet<>();
    for (T t : inner) {
      V v = selector.invoke(t);
      ret.add(v);
    }
    return ret;
  }

  @Override
  public ISet<T> selectCount(int count) {
    if (count < 0)
      throw new EIllegalArgumentException("count", count, "Value must be greater or equal 0.");
    ISet<T> ret = new ESet<>();
    for (T t : this) {
      if (ret.size() == count) break;
      ret.add(t);
    }
    return ret;
  }

  @Override
  public int size() {
    return this.inner.size();
  }

  @Override
  public Set<T> toJavaSet() {
    Set<T> ret = new HashSet<>(this.inner);
    return ret;
  }

  @Override
  public void toJavaSet(Set<T> target) {
    target.addAll(this.inner);
  }

  @Override
  public IList<T> toList() {
    EList<T> ret = new EList<>(this.inner);
    return ret;
  }

  @Override
  public ISet<T> toSet() {
    ISet<T> ret = new ESet<>(this.inner);
    return ret;
  }

  @Override
  public String toString() {
    return String.format("ESet{%d items}", this.size());
  }

  @Override
  public void tryRemove(T item) {
    inner.remove(item);
  }

  @Override
  public ISet<T> union(IReadOnlySet<T> otherSet) {
    ISet<T> ret = new ESet<>(this);
    for (T t : otherSet) {
      if (!ret.contains(t))
        ret.add(t);
    }
    return ret;
  }

  @Override
  public ISet<T> where(Predicate<T> predicate) {
    ESet<T> ret = new ESet<>();
    ret.inner = this.inner.stream().filter(predicate).collect(Collectors.toSet());
    return ret;
  }
}