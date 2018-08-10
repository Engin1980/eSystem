package eng.eSystem.swing.extenders;

import eng.eSystem.utilites.Selector;

abstract class WithModelExtender<T, Ttype> extends  Extender<Ttype> {

  private Selector<T, String> defaultLabelSelector = q->q.toString();

  public WithModelExtender(Selector<T, String> defaultLabelSelector) {
    this.defaultLabelSelector = defaultLabelSelector;
  }

  public WithModelExtender() {
  }

  public Selector<T, String> getDefaultLabelSelector() {
    return defaultLabelSelector;
  }

  public void setDefaultLabelSelector(Selector<T, String> defaultLabelSelector) {
    this.defaultLabelSelector = defaultLabelSelector;
  }

  public void addItems(Iterable<T> items, Selector<T, String> labelSelector) {
    for (T item : items) {
      addItem(item, labelSelector);
    }
  }

  public void addItems(T[] items, Selector<T, String> labelSelector) {
    for (T item : items) {
      addItem(item, labelSelector);
    }
  }

  public void addItems(Iterable<T> items) {
    for (T item : items) {
      addItem(item, this.defaultLabelSelector);
    }
  }

  public void addItems(T[] items) {
    for (T item : items) {
      addItem(item, this.defaultLabelSelector);
    }
  }

  public void addItem(T item, Selector<T, String> labelSelector) {
    addItem(item, labelSelector.getValue(item));
  }

  public void addItem(T item) {
    addItem(item, this.defaultLabelSelector);
  }

  public abstract void addItem(BoxItem<T> item);

  public void addItem(T value, String label) {
    BoxItem<T> bi = new BoxItem<>(value, label);
    this.addItem(bi);
  }

}
