package eng.eSystem.collections.subinterfaces;

import eng.eSystem.collections.ESet;
import eng.eSystem.collections.ICollection;
import eng.eSystem.collections.ISet;
import eng.eSystem.functionalInterfaces.Predicate;
import eng.eSystem.validation.EAssert;

public interface IEditableCollection<T> extends ICollection<T> {
  /**
   * Adds one item in the collection.
   * @param item Item to add
   */
  void add(T item);

  /**
   * Removes all the items from the collection.
   */
  void clear();

  /**
   * Removes one item from the collection (if item does not exist, throws exception).
   * @param item Item to remove, must exist (exception otherwise)
   * @see #tryRemove(Object)
   */
  void remove(T item);

  /**
   * Tries to remove one item from this collection (if item does not exist, does nothing).
   * @param item
   * @see #remove(Object)
   */
  void tryRemove(T item);

  /**
   * Adds multiple elements into this collection.
   * @param items Items to be added.
   */
  default void addMany(Iterable<? extends T> items) {
    for (T item : items) {
      this.add(item);
    }
  }

  /**
   * Adds multiple elements into this collection.
   * @param items Items to be added.
   */
  default void addMany(T[] items) {
    for (T item : items) {
      this.add(item);
    }
  }

  /**
   * Remove items matching predicate from this collection
   * @param predicate Predicate to match to remove item.
   */
  default void remove(Predicate<T> predicate) {
    ISet<T> tmp = new ESet<>();
    for (T t : this) {
      if (predicate.invoke(t)) tmp.add(t);
    }
    for (T t : tmp) {
      this.remove(t);
    }
  }

  /**
   * Removes multiple items from this collection - all must exist.
   * @param items Items to remove, all must exist in this collection
   * @see #tryRemoveMany(Iterable)
   */
  default void removeMany(Iterable<? extends T> items) {
    EAssert.Argument.isNotNull(items, "items");

    items.forEach(q -> this.remove(q));
  }

  /**
   * Retains only items matching predicate in this collection.
   * @param predicate Predicate to match to keep the item
   */
  default void retain(Predicate<T> predicate) {
    this.remove(predicate.negate());
  }

  /**
   * Removes multiple items from this collection - non-existing will be skipped.
   * @param items Items to remove.
   * @see #removeMany(Iterable)
   */
  default void tryRemoveMany(Iterable<? extends T> items) {
    items.forEach(q -> this.tryRemove(q));
  }
}
