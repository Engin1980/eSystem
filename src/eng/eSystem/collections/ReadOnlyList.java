package eng.eSystem.collections;

import java.util.Iterator;
import java.util.List;

public class ReadOnlyList<T> implements Iterable<T> {

  private final List<T> inner;

  public ReadOnlyList(List<T> list) {
    inner = list;
  }

  @Override
  public Iterator<T> iterator() {
    return inner.iterator();
  }

  public int size() {
    return inner.size();
  }

  public T get(int index) {
    return inner.get(index);
  }
}
