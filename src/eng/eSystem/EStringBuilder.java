package eng.eSystem;

import eng.eSystem.utilites.Selector;

public class EStringBuilder {
  private final StringBuilder sb;

  public EStringBuilder() {
    sb = new StringBuilder();
  }

  public EStringBuilder(int initialCapacity) {
    sb = new StringBuilder(initialCapacity);
  }

  public void clear() {
    sb.setLength(0);
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

  public EStringBuilder appendLine(String line) {
    append(line);
    append("\r\n");
    return this;
  }

  public EStringBuilder appendLine() {
    this.appendLine("");
    return this;
  }

  public void insert(int index, String text) {
    this.sb.insert(index, text);
  }

  public void insertFormat(int index, String format, Object... args) {
    this.insert(index, String.format(format, args));
  }

  public <T> EStringBuilder appendItems(Iterable<T> items, Selector<T, String> selector, String separator) {
    boolean isFirst = true;
    for (T item : items) {
      if (isFirst)
        isFirst = false;
      else
        this.append(separator);
      String it = selector.getValue(item);
      this.append(it);
    }
    return this;
  }

  @Override
  public String toString() {
    return sb.toString();
  }

}