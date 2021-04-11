package eng.eSystem.collections;

import java.util.Optional;

public interface IStack<T> extends IReadOnlyStack<T> {

  void push(T item);

  T pop();

  default Optional<T> tryPop() {
    if (isEmpty())
      return Optional.empty();
    else
      return Optional.of(this.pop());
  }
}
