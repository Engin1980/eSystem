package eng.eSystem.swing.extenders;

import eng.eSystem.collections.IReadOnlySet;
import eng.eSystem.utilites.Selector;
import eng.eSystem.validation.EAssert;

abstract class WithModelExtender<T, Ttype> extends  Extender<Ttype> {

  private Selector<T, String> labelSelector;

  public WithModelExtender(Selector<T, String> labelSelector) {
    EAssert.isNotNull(labelSelector);
    this.labelSelector = labelSelector;
  }

  public Selector<T, String> getLabelSelector() {
    return labelSelector;
  }

  public void addItems(Iterable<T> items) {
    for (T item : items) {
      addItem(item);
    }
  }

  public void addItems(T[] items) {
    for (T item : items) {
      addItem(item);
    }
  }

  protected abstract void addItem(BoxItem<T> item);

  public void addItem(T value) {
    String label;
    try{
      label = this.labelSelector.getValue(value);
    } catch (Exception ex){
      throw new IllegalArgumentException("Unable to get label for value " + value + " using predefined label selector.");
    }
    BoxItem<T> bi = new BoxItem<>(value, label);
    this.addItem(bi);
  }

  public abstract IReadOnlySet<T> getItems();
}
