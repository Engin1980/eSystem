package eng.eSystem.utilites;

/**
 * Deprecated.
 * @param <T>
 * @see eng.eSystem.functionalInterfaces.Action
 */
@Deprecated // replace by consumer
public interface Action<T> {
  void apply(T item);
}
