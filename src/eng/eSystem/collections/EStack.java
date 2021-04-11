package eng.eSystem.collections;

import java.util.Stack;

public class EStack<T> implements IStack<T> {
  private final Stack<T> inner;

  public EStack() {
    this(new Stack<>());
  }

  private EStack(Stack<T> inner) {
    this.inner = inner;
  }

  @Override
  public EStack<T> copy() {
    EStack<T> ret = new EStack<>((Stack) inner.clone());
    return ret;
  }

  @Override
  public boolean isEmpty() {
    return this.inner.size() == 0;
  }

  @Override
  public T peek() {
    T ret = inner.peek();
    return ret;
  }

  @Override
  public T pop() {
    T ret = inner.pop();
    return ret;
  }

  @Override
  public void push(T item) {
    inner.push(item);
  }

  @Override
  public int size() {
    return inner.size();
  }

  @Override
  public IList<T> toList() {
    EList<T> ret = new EList<>();
    for (T t : inner) {
      ret.add(t);
    }
    return ret;
  }
}
