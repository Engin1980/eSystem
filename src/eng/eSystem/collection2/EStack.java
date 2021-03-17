package eng.eSystem.collection2;

import java.util.Stack;

public class EStack<T> {
  private final Stack<T> inner;

  public EStack() {
    this(new Stack<>());
  }

  private EStack(Stack<T> inner){
    this.inner = inner;
  }

  public void push (T item){
    inner.push(item);
  }

  public T pop(){
    T ret = inner.pop();
    return ret;
  }

  public int size(){
    return inner.size();
  }

  public T peek(){
    T ret = inner.peek();
    return ret;
  }

  public IList toIList(){
    EList ret = new EList();
    for (T t : inner) {
      ret.add(t);
    }
    return ret;
  }

  public EStack<T> clone(){
    EStack<T> ret = new EStack<>((Stack) inner.clone());
    return ret;
  }
}
