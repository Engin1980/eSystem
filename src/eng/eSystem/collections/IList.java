package eng.eSystem.collections;

public interface IList<T> extends IReadOnlyList<T> {
  void add(T item);

  void add(Iterable<? extends T> items);

  void add(T[] items);
}
