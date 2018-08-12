package eng.eSystem.swing.extenders;

import eng.eSystem.collections.EList;
import eng.eSystem.collections.IList;
import eng.eSystem.collections.IReadOnlyList;
import eng.eSystem.events.EventSimple;
import eng.eSystem.utilites.Selector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class ListBoxExtender<T> extends WithModelExtender<T, javax.swing.JList> {

  protected final JList control;
  protected final DefaultListModel<BoxItem<T>> model = new DefaultListModel<>();
  private final EventSimple<ListBoxExtender> onSelectionChanged = new EventSimple<>(this);

  public EventSimple<ListBoxExtender> getOnSelectionChanged() {
    return onSelectionChanged;
  }

  public ListBoxExtender(JList control) {
    this.control = control;
    this.control.setModel(this.model);
    this.control.addListSelectionListener(this::control_selectionChanged);
  }

  private void control_selectionChanged(ListSelectionEvent listSelectionEvent) {
    if (listSelectionEvent.getValueIsAdjusting() == false)
      this.onSelectionChanged.raise();
  }

  public ListBoxExtender() {
    this(new JList());
  }

  @Override
  public JList getControl() {
    return this.control;
  }

  @Override
  public void addItem(BoxItem<T> item) {
    model.addElement(item);
  }

  public IReadOnlyList<T> getSelectedItems() {
    IList<T> ret = new EList<>();
    for (int selectedIndex : control.getSelectedIndices()) {
      BoxItem<T> t = model.get(selectedIndex);
      ret.add(t.value);
    }
    return ret;
  }

  public void setSelectedIndex(int index) {
    control.setSelectedIndex(index);
  }

  public void setSelectedLabels(String ... label) {
    IList<String> lst = new EList<>(label);
    this.setSelectedLabels(lst);
  }

  public void setSelectedLabels(Iterable<String> labels){
    IList<Integer> tmp = new EList<>();

    for (String label : labels) {
      int index = getIndexOfLabel(label);
      if (index != -1)
        tmp.add(index);
    }

    int[] arr = new int[tmp.size()];
    for (int i = 0; i < tmp.size(); i++) {
      arr[i] = tmp.get(i);
    }

    this.control.setSelectedIndices(arr);
  }

  public boolean contains(T item) {
    boolean ret = false;
    for (int i = 0; i < model.size(); i++) {
      BoxItem<T> t = model.get(i);
      if (t.value.equals(item)) {
        ret = true;
        break;
      }
    }
    return ret;
  }

  public void setSelectedItems(T ... item) {
    IList<T> lst = new EList<>(item);
    this.setSelectedItems(lst);
  }

  public void setSelectedItems(Iterable<T> items) {
    IList<Integer> tmp = new EList<>();

    for (T item : items) {
      int index = getIndexOfItem(item);
      if (index != -1)
        tmp.add(index);
    }

    int[] arr = new int[tmp.size()];
    for (int i = 0; i < tmp.size(); i++) {
      arr[i] = tmp.get(i);
    }

    this.control.setSelectedIndices(arr);
  }

  protected int getIndexOfItem(T item) {
    int ret = -1;
    for (int i = 0; i < model.size(); i++) {
      BoxItem<T> t = model.get(i);
      if (t.value.equals(item)) {
        ret = i;
        break;
      }
    }
    return ret;
  }

  protected int getIndexOfLabel(String label) {
    int ret = -1;
    for (int i = 0; i < model.size(); i++) {
      BoxItem<T> t = model.get(i);
      if (t.label.equals(label)) {
        ret = i;
        break;
      }
    }
    return ret;
  }

  public void selectAll(){
    int [] arr = new int[this.model.size()];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = i;
    }
    this.control.setSelectedIndices(arr);
  }

  public void selectNone(){
    this.control.setSelectedIndices(new int[0]);
  }
}

