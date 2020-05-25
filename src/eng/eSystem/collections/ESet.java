package eng.eSystem.collections;

import eng.eSystem.exceptions.EIllegalArgumentException;
import eng.eSystem.functionalInterfaces.Selector;

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

  public ESet(T[] elements) {
    this(DEFAULT_CLASS, null);
    this.add(elements);
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
  public void remove(T item) {
    inner.remove(item);
  }

  @Override
  public void clear() {
    inner.clear();
  }

  @Override
  public IList<T> toList() {
    EList<T> ret = new EList<>(this.inner);
    return ret;
  }

  @Override
  public ISet<T> where(Predicate<T> predicate) {
    ESet<T> ret = new ESet<>();
    ret.inner = this.inner.stream().filter(predicate).collect(Collectors.toSet());
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
  public ISet<T> union(IReadOnlySet<T> otherSet) {
    ISet<T> ret = new ESet<>(this);
    for (T t : otherSet) {
      if (ret.contains(t) == false)
        ret.add(t);
    }
    return ret;
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
  public int size() {
    return this.inner.size();
  }

  @Override
  public boolean contains(T item) {
    return this.inner.contains(item);
  }

  @Override
  public Iterator<T> iterator() {
    return this.inner.iterator();
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
    return String.format("ESet{%d items}", this.size());
  }
}
