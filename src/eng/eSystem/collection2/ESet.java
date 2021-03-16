package eng.eSystem.collection2;

import eng.eSystem.collection2.exceptions.ElementNotFoundException;
import eng.eSystem.functionalInterfaces.Selector;
import eng.eSystem.validation.EAssert;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class ESet<T> implements ISet<T> {
  private final static Class<? extends Set<?>> DEFAULT_CLASS = (Class<? extends Set<?>>) HashSet.class;

  public static <T> ESet<T> of(Iterable<T> elements) {
    ESet<T> ret = new ESet<>();
    ret.addMany(elements);
    return ret;
  }

  public static <T> ESet<T> of(T... elements) {
    ESet<T> ret = new ESet<>();
    ret.addMany(elements);
    return ret;
  }
  private final Set<T> inner;

  public ESet() {
    this(DEFAULT_CLASS);
  }

  public ESet(Class<? extends Set<?>> innerType) {
    try {
      //TODO rewrite without deprecated method
      this.inner = (Set<T>) innerType.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException("Unable to create a new instance.");
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
  public <K> ISet<T> distinct(Selector<T, K> selector) {
    ISet<K> known = new ESet<>();
    ISet<T> ret = new ESet<>();
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
  public int size() {
    return this.inner.size();
  }

  @Override
  public ISet<T> take(int count) {
    EAssert.Argument.isTrue(count < 0, sf("Count '%d' must be greater or equal 0.", count));
    ISet<T> ret = new ESet<>();
    for (T t : this) {
      if (ret.size() == count) break;
      ret.add(t);
    }
    return ret;
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
    EList<T> ret = EList.of(this.inner);
    return ret;
  }

  @Override
  public void toJavaList(List<T> target) {
    target.addAll(this.inner);
  }

  @Override
  public ISet<T> toSet() {
    ISet<T> ret = ESet.of(this.inner);
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
    ISet<T> ret = ESet.of(this);
    for (T t : otherSet) {
      if (!ret.contains(t))
        ret.add(t);
    }
    return ret;
  }

  @Override
  public ISet<T> where(Predicate<T> predicate) {
    ESet<T> ret = ESet.of(
            this.inner.stream().filter(predicate).collect(Collectors.toSet()));
    return ret;
  }
}
