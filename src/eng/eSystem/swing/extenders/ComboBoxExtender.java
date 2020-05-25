package eng.eSystem.swing.extenders;

import eng.eSystem.collections.ESet;
import eng.eSystem.collections.IList;
import eng.eSystem.collections.IReadOnlySet;
import eng.eSystem.collections.ISet;
import eng.eSystem.events.EventSimple;
import eng.eSystem.functionalInterfaces.Selector;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.function.Predicate;

public class ComboBoxExtender<T> extends WithModelExtender<T, JComboBox<BoxItem<T>>> {
  private final DefaultComboBoxModel<BoxItem<T>> model = new DefaultComboBoxModel<>();
  private JComboBox<BoxItem<T>> control;
  private EventSimple<ComboBoxExtender> onSelectionChanged = new EventSimple<>(this);

  public ComboBoxExtender(JComboBox control, Selector<T, String> labelSelector) {
    super(labelSelector);
    this.control = control;
    this.control.setModel(this.model);
    this.control.addItemListener(this::control_selectionChanged);
  }

  public ComboBoxExtender(Selector<T, String> labelSelector) {
    this(new JComboBox(), labelSelector);
  }

  public ComboBoxExtender() {
    this(new JComboBox(), q -> q.toString());
  }

  public ComboBoxExtender(IList<T> elements){
    this();
    this.addItems(elements);
  }
  public ComboBoxExtender(T[] elements){
    this();
    this.addItems(elements);
  }

  public ComboBoxExtender(Selector<T, String> labelSelector, IList<T> elements){
    this(labelSelector);
    this.addItems(elements);
  }

  public ComboBoxExtender(Selector<T, String> labelSelector, T[] elements){
    this(labelSelector);
    this.addItems(elements);
  }

  public EventSimple<ComboBoxExtender> getOnSelectionChanged() {
    return onSelectionChanged;
  }

  @Override
  public JComboBox<BoxItem<T>> getControl() {
    return control;
  }

  public T getSelectedItem() {
    T ret;
    int selectedIndex = control.getSelectedIndex();
    if (selectedIndex < 0)
      ret = null;
    else {
      BoxItem<T> t = model.getElementAt(selectedIndex);
      ret = t.value;
    }
    return ret;
  }

  public int getSelectedIndex(){
    return this.control.getSelectedIndex();
  }

  public void setSelectedItem(T item) {
    int index = -1;
    for (int i = 0; i < model.getSize(); i++) {
      BoxItem<T> t = model.getElementAt(i);
      if (t.value.equals(item)) {
        index = i;
        break;
      }
    }
    setSelectedIndex(index);
  }

  public void setSelectedIndex(int index) {
    control.setSelectedIndex(index);
  }

  public void setSelectedLabel(String label) {
    int index = -1;
    for (int i = 0; i < model.getSize(); i++) {
      BoxItem<T> t = model.getElementAt(i);
      if (t.label.equals(label)) {
        index = i;
        break;
      }
    }
    setSelectedIndex(index);
  }

  public void setSelectedItem(Predicate<BoxItem> predicate){
    int index = -1;
    for (int i = 0; i < model.getSize(); i++) {
      BoxItem<T> t = model.getElementAt(i);
      if (predicate.test(t)) {
        index = i;
        break;
      }
    }
    setSelectedIndex(index);
  }

  public T getItem(int index){
    return model.getElementAt(index).value;
  }

  public boolean contains(T item) {
    boolean ret = false;
    for (int i = 0; i < model.getSize(); i++) {
      BoxItem<T> t = model.getElementAt(i);
      if (t.value.equals(item)) {
        ret = true;
        break;
      }
    }
    return ret;
  }

  public void clearItems() {
    model.removeAllElements();
  }

  private void control_selectionChanged(ItemEvent itemEvent) {
    this.onSelectionChanged.raise();
  }

  @Override
  protected void addItem(BoxItem<T> item) {
    this.model.addElement(item);
  }

  @Override
  public IReadOnlySet<T> getItems() {
    ISet<T> ret = new ESet<>();
    for (int i = 0; i < model.getSize(); i++) {
      BoxItem<T> item = model.getElementAt(i);
      ret.add(item.value);
    }
    return ret;
  }

}
