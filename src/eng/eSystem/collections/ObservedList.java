package eng.eSystem.collections;

import com.sun.deploy.security.MSCryptoDSASignature;
import com.sun.istack.internal.Nullable;
import eng.eSystem.events.Event;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Represents list observable for adding/removing elements.
 *
 * @param <T> Type of elements in the list.
 */
public class ObservedList<T> implements List<T> {

  public static class EventArgs<T> {
    public final int index;
    public final T element;

    public EventArgs(int index, T element) {
      this.index = index;
      this.element = element;
    }
  }
  private List<T> inner;
  private eng.eSystem.events.Event<ObservedList<T>, EventArgs<T>> addedEvent = new Event<>(this);
  private eng.eSystem.events.Event<ObservedList<T>, EventArgs<T>> removedEvent = new Event<>(this);

  public ObservedList() {
    inner = new ArrayList<>();
  }

  /**
   * Wraps another collection into observed list.
   *
   * @param wrappedCollection
   */
  public ObservedList(List<T> wrappedCollection) {
    this.inner = wrappedCollection;
  }

  @Override
  public int size() {
    return inner.size();
  }

  @Override
  public boolean isEmpty() {
    return inner.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return inner.contains(o);
  }

  @Override
  public Iterator<T> iterator() {
    return inner.iterator();
  }

  @Override
  public Object[] toArray() {
    return inner.toArray();
  }

  @Override
  public <T1> T1[] toArray(T1[] a) {
    return inner.toArray(a);
  }

  @Override
  public boolean add(T t) {
    inner.add(t);
    EventArgs<T> e = new EventArgs<>(inner.size() - 1, t);
    addedEvent.raise(e);
    return true;
  }

  @Override
  public boolean remove(Object o) {
    int index = this.indexOf(o);
    boolean ret = false;
    if (index>= 0) {
      ret = true;
      T elm = this.get(index);
      this.inner.remove(index);
      EventArgs<T> e = new EventArgs<>(index, elm);
      removedEvent.raise(e);
    }
    return ret;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return inner.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    boolean ret = false;
    for (T t : c) {
      ret = ret || this.add(t);
    }
    return ret;
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    for (T item : c) {
      inner.add(index, item);
      index++;
    }
    return true;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    boolean ret = false;
    for (Object t : c) {
      ret = ret || this.remove(t);
    }
    return ret;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void replaceAll(UnaryOperator<T> operator) {
    for (int i = 0; i < this.size(); i++) {
      T oldItem = this.get(i);
      T newItem = operator.apply(oldItem);
      this.set(i, newItem);
    }
  }

  @Override
  public void sort(Comparator<? super T> c) {
    this.inner.sort(c);
  }

  @Override
  public void clear() {
    for (T t : inner) {
      remove(t);
    }
  }

  @Override
  public T get(int index) {
    return inner.get(index);
  }

  @Override
  public T set(int index, T element) {
    T oldItem = this.get(index);
    this.inner.set(index, element);
    EventArgs<T> e;

    e = new EventArgs<>(index, oldItem);
    removedEvent.raise(e);

    e = new EventArgs<>(index, element);
    addedEvent.raise(e);
    return oldItem;
  }

  @Override
  public void add(int index, T element) {
    this.inner.add(index, element);
    this.addedEvent.raise(
        new EventArgs<>(index, element)
    );
  }

  @Override
  public T remove(int index) {
    T ret = this.get(index);
    this.inner.remove(index);
    this.removedEvent.raise(
        new EventArgs<>(index, ret)
    );
    return ret;
  }

  @Override
  public int indexOf(Object o) {
    return inner.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return this.inner.lastIndexOf(o);
  }

  @Override
  public ListIterator<T> listIterator() {
    return this.inner.listIterator();
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    return this.inner.listIterator(index);
  }

  /**
   * Returns sub-list of the list. Returned object is no more observed list.
   *
   * @param fromIndex
   * @param toIndex
   * @return
   */
  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return this.inner.subList(fromIndex, toIndex);
  }

  @Override
  public Spliterator<T> spliterator() {
    return inner.spliterator();
  }

  @Override
  public boolean removeIf(Predicate<? super T> filter) {
    boolean ret = false;
    for (T item : inner) {
      if (filter.test(item)) {
        ret = ret || this.remove(item);
      }
    }
    return ret;
  }

  @Override
  public Stream<T> stream() {
    return inner.stream();
  }

  @Override
  public Stream<T> parallelStream() {
    return inner.parallelStream();
  }

  /**
   * Try to get an element by the index. If operation fails, "null" is returned.
   */
  @Nullable
  public T tryGet(int index) {
    T ret;
    try {
      ret = this.get(index);
    } catch (Throwable t) {
      ret = null;
    }
    return ret;
  }

  @Override
  public void forEach(Consumer<? super T> action) {
    inner.forEach(action);
  }
}
