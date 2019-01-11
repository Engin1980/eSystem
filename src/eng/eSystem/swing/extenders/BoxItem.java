package eng.eSystem.swing.extenders;

class BoxItem<T> {
  public final T value;
  public final String label;

  public BoxItem(T value, String label) {
    this.value = value;
    this.label = label;
  }

  @Override
  public String toString() {
    return label;
  }
}

