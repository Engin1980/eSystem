package eng.eSystem.utilites;

public interface Action<T> {
  void apply(T item);
}
