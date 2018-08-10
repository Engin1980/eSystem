package eng.eSystem.swing.extenders;

import eng.eSystem.events.EventSimple;
import eng.eSystem.utilites.Selector;

import javax.swing.*;
import java.awt.event.ItemEvent;

public class ComboBoxExtender<T> extends WithModelExtender<T, JComboBox<BoxItem<T>>> {
  private JComboBox<BoxItem<T>> control;
  private final DefaultComboBoxModel<BoxItem<T>> model = new DefaultComboBoxModel<>();
  private EventSimple<ComboBoxExtender> onSelectionChanged = new EventSimple<>(this);

  public EventSimple<ComboBoxExtender> getOnSelectionChanged() {
    return onSelectionChanged;
  }

  @Override
  public JComboBox<BoxItem<T>> getControl() {
    return control;
  }

  public ComboBoxExtender(JComboBox control) {
    this.control = control;
    this.control.setModel(this.model);
    this.control.addItemListener(this::control_selectionChanged);
  }

  private void control_selectionChanged(ItemEvent itemEvent) {
    this.onSelectionChanged.raise();
  }

  public ComboBoxExtender() {
    this(new JComboBox());
  }

  @Override
  public void addItem(BoxItem<T> item) {
    model.addElement(item);
  }

  public T getSelectedItem() {
    int selectedIndex = control.getSelectedIndex();
    BoxItem<T> t = model.getElementAt(selectedIndex);
    T ret = t.value;
    return ret;
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

}
