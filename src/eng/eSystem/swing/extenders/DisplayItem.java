package eng.eSystem.swing.extenders;

import java.util.Objects;

public class DisplayItem<T> {
  public final String label;
  public final T value;

  public DisplayItem(String label, T value) {
    this.label = label;
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DisplayItem<?> that = (DisplayItem<?>) o;
    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
