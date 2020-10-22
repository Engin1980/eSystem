package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.DuplicitItemException;
import eng.eSystem.functionalInterfaces.Selector;

public class EDistinctList<T> extends EList<T> {

  public enum Behavior {
    ignore,
    exception,
    skip
  }

  private Selector<T, Object> selector;
  private Behavior onDuplicateBehavior;

  public EDistinctList(Selector<T, Object> selector, Behavior onDuplicateBehavior) {
    this.selector = selector;
    this.onDuplicateBehavior = onDuplicateBehavior;
  }

  public EDistinctList() {
    this(q -> q, Behavior.skip);
  }

  public EDistinctList(Behavior onDuplicateBehavior) {
    this(q -> q, onDuplicateBehavior);
  }

  @Override
  public void add(T item) {
    boolean exists = false;
    if (onDuplicateBehavior != Behavior.ignore)
      exists = existsDistinctValue(this.selector.invoke(item));
    if (exists)
      throwIfRequired(item);
    else
      super.add(item);
  }

  @Override
  public void addMany(Iterable<? extends T> items) {
    for (T item : items) {
      this.add(item);
    }
  }

  @Override
  public void addMany(T[] items) {
    for (T item : items) {
      this.add(item);
    }
  }

  @Override
  public void insert(int index, T item) {
    boolean exists = false;
    if (onDuplicateBehavior != Behavior.ignore)
      exists = existsDistinctValue(this.selector.invoke(item));
    if (exists)
      throwIfRequired(item);
    else super.insert(index, item);
  }

  @Override
  public void set(int index, T item) {
    boolean exists = false;
    if (onDuplicateBehavior != Behavior.ignore)
      exists = existsDistinctValue(this.selector.invoke(item));
    if (exists)
      throwIfRequired(item);
    else
      super.set(index, item);
  }

  public Behavior getOnDuplicateBehavior() {
    return onDuplicateBehavior;
  }

  public void setOnDuplicitBehavior(Behavior onDuplicitBehavior, boolean reCheckDuplicits) {
    this.onDuplicateBehavior = onDuplicitBehavior;
    if (reCheckDuplicits)
      recheckDuplicates();
  }

  /**
   * Check for duplicit data and behave accordingly to {@link #getOnDuplicateBehavior()} property value.
   * If {@link #getOnDuplicateBehavior()} is "ignore", then nothing is done.
   */
  public void recheckDuplicates() {
    if (onDuplicateBehavior == Behavior.ignore) return;

    for (int i = this.size() - 1; i >= 0; i--) {
      Object tmA;
      if (this.get(i) == null)
        tmA = null;
      else
        tmA = this.selector.invoke(this.get(i));

      for (int j = 0; j < i; j++) {
        Object tmB;
        if (this.get(j) == null)
          tmB = null;
        else
          tmB = this.selector.invoke(this.get(j));

        boolean isDuplicity;
        if (tmA == null) {
          if (tmB == null) isDuplicity = true;
          else isDuplicity = false;
        } else {
          isDuplicity = tmA.equals(tmB);
        }
        if (isDuplicity){
          if (onDuplicateBehavior == Behavior.skip) {
            this.removeAt(i);
            continue;
          } else {
            throwIfRequired(this.get(i));
          }
        }
      }
    }
  }

  private void throwIfRequired(T item) {
    if (onDuplicateBehavior == Behavior.exception)
      throw new DuplicitItemException("The item " + item + " represents value " + this.selector.invoke(item) + " which already exists in the list.");
  }

  private boolean existsDistinctValue(Object d) {
    boolean ret = this.isAny(q -> {
      boolean rr;
      if (q == null)
        rr = false;
      else {
        Object a = this.selector.invoke(q);
        if (a == null){
          throw new IllegalArgumentException("Distinct list selector cannot return null. Selector returned null for object " + q);
        } else {
          rr = a.equals(d);
        }
      }
      return rr;
    } );
    return ret;
  }
}
