package eng.eSystem;

import eng.eSystem.functionalInterfaces.Selector;

public class EStringBuilder {
  private final StringBuilder sb;

  public EStringBuilder() {
    sb = new StringBuilder();
  }

  public EStringBuilder(String text) {
    sb = new StringBuilder(text);
  }

  public EStringBuilder(int initialCapacity) {
    sb = new StringBuilder(initialCapacity);
  }

  public EStringBuilder append(String text) {
    sb.append(text);
    return this;
  }

  public EStringBuilder appendFormat(String format, Object... args) {
    append(
        String.format(format, (Object[]) args));
    return this;
  }

  public EStringBuilder appendFormatLine(String format, Object... args) {
    appendLine(
        String.format(format, (Object[]) args));
    return this;
  }

  public <T> EStringBuilder appendItems(Iterable<T> items, String separator) {
    return this.appendItems(items, q -> (q == null) ? "(null)" : q.toString(), separator);
  }

  public <T> EStringBuilder appendItems(Iterable<T> items, Selector<T, String> selector, String separator) {
    boolean isFirst = true;
    for (T item : items) {
      if (isFirst)
        isFirst = false;
      else
        this.append(separator);
      String it = selector.invoke(item);
      this.append(it);
    }
    return this;
  }

  public <T> EStringBuilder appendItems(T[] items, String separator) {
    return this.appendItems(items, q -> (q == null) ? "(null)" : q.toString(), separator);
  }

  public <T> EStringBuilder appendItems(T[] items, Selector<T, String> selector, String separator) {
    boolean isFirst = true;
    for (T item : items) {
      if (isFirst)
        isFirst = false;
      else
        this.append(separator);
      String it = selector.invoke(item);
      this.append(it);
    }
    return this;
  }

  public EStringBuilder appendLine(String line) {
    append(line);
    append("\r\n");
    return this;
  }

  public EStringBuilder appendLine() {
    this.appendLine("");
    return this;
  }

  public void clear() {
    sb.setLength(0);
  }

  public void insert(int index, String text) {
    this.sb.insert(index, text);
  }

  public void insertFormat(int index, String format, Object... args) {
    this.insert(index, String.format(format, args));
  }

  public boolean isEmpty() {
    return sb.length() == 0;
  }

  public int length() {
    return sb.length();
  }

  @Override
  public String toString() {
    return sb.toString();
  }

}
