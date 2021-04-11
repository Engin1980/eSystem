package eng.eSystem.collections;

import java.util.Optional;

public interface IReadOnlyStack<T> {
  boolean isEmpty();

  int size();

  T peek();

  IList<T> toList();

  default java.util.List<T> toJavaList() {
    return this.toList().toJavaList();
  }

  default Optional<T> tryPeek() {
    if (isEmpty())
      return Optional.empty();
    else
      return Optional.of(this.peek());
  }

  EStack<T> copy();
}
