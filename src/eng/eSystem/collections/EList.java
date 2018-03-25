package eng.eSystem.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EList<T> implements IList<T> {

  private final static Class DEFAULT_CLASS = ArrayList.class;

  private final List<T> inner;

  public EList(Class innerType) {
    this(innerType, null);
  }

  public EList(Iterable<? extends T> elements) {
    this(DEFAULT_CLASS, elements);
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
  public T get(int index) {
    return inner.get(index);
  }

  @Override
  public int size() {
    return inner.size();
  }

  @Override
  public Iterator<T> iterator() {
    return inner.iterator();
  }
}
