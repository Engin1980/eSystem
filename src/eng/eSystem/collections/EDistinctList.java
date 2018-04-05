package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.DuplicitItemException;
import eng.eSystem.utilites.Selector;

public class EDistinctList<T> extends EList<T> {

  public enum Behavior {
    exception,
    skip
  }

  private Selector<T, Object> selector;
  private Behavior onDuplicitBehavior;

  public EDistinctList(Selector<T, Object> selector, Behavior onDuplicitBehavior) {
    this.selector = selector;
    this.onDuplicitBehavior = onDuplicitBehavior;
  }

  public EDistinctList() {
    this(q -> q, Behavior.skip);
  }

  public EDistinctList(Behavior onDuplicitBehavior) {
    this(q -> q, onDuplicitBehavior);
  }

  @Override
  public void add(T item) {
    boolean exists = existsDistinctValue(this.selector.getValue(item));
    if (exists)
      throwIfRequired(item);
    else
      super.add(item);
  }

  @Override
  public void add(Iterable<? extends T> items) {
    for (T item : items) {
      this.add(item);
    }
  }

  @Override
  public void add(T[] items) {
    for (T item : items) {
      this.add(item);
    }
  }

  @Override
  public void insert(int index, T item) {
    boolean exists = existsDistinctValue(this.selector.getValue(item));
    if (exists)
      throwIfRequired(item);
    else super.insert(index,item);
  }

  @Override
  public void set(int index, T item) {
    boolean exists = existsDistinctValue(this.selector.getValue(item));
    if (exists)
      throwIfRequired(item);
    else
      super.set(index, item);
  }

  private void throwIfRequired(T item) {
    if (onDuplicitBehavior == Behavior.exception)
      throw new DuplicitItemException("The item " +item + " represents value " + this.selector.getValue(item) + " which already exists in the list.");
  }

  private boolean existsDistinctValue(Object d) {
    boolean ret = this.isAny(q -> this.selector.getValue(q).equals(d));
    return ret;
  }
}
