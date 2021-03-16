package eng.eSystem.collection2.subinterfaces;

public interface IEditableCollection<T> {
  void add(T item);

  default void addMany(Iterable<? extends T> items) {
    for (T item : items) {
      this.add(item);
    }
  }

  default void addMany(T[] items) {
    for (T item : items) {
      this.add(item);
    }
  }

  void clear();

  void remove(T item);

  default void removeMany(Iterable<? extends T> items) {
    EAssert.Argument.isNotNull(items, "items");

    for (T item : items) {
      this.remove(item);
    }
  }

  void tryRemove(T item);

  default void remove(Predicate<T> predicate) {
    ISet<T> tmp = this.where(predicate);
    for (T t : tmp) {
      this.remove(tmp);
    }
  }

  default void retain(Predicate<T> predicate) {
    ISet<T> tmp = this.where(predicate.negate());
    for (T t : tmp) {
      this.remove(tmp);
    }
  }

  default void tryRemoveMany(Iterable<? extends T> items) {
    items.forEach(q -> this.tryRemove(q));
  }
}
