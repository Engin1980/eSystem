package eng.eSystem.swing.extenders;

import eng.eSystem.collections.*;
import eng.eSystem.events.EventSimple;
import eng.eSystem.functionalInterfaces.Selector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckedListBoxExtender<T> extends ListBoxExtender<T>{

  private final ISet<BoxItem<T>> checkedItems = new ESet<>();
  private final EventSimple<CheckedListBoxExtender> onCheckedChanged = new EventSimple<>(this);

  public EventSimple<CheckedListBoxExtender> getOnCheckedChanged() {
    return onCheckedChanged;
  }

  public CheckedListBoxExtender() {
    this(new JList(), q->q.toString());
  }

  public CheckedListBoxExtender(JList control, Selector<T,String> labelSelector) {
    super(control, labelSelector);
    this.control.setCellRenderer(new StringCheckListCellRenderer(checkedItems));
    this.control.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        int index = control.locationToIndex(e.getPoint());
        if (index != -1) {
          switchCheckState(index);
          control.repaint();
          onCheckedChanged.raise();
        }
      }
    });
    this.control.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
          changeStateOfSelected();
          control.repaint();
          onCheckedChanged.raise();
        }
      }
    });
  }

  private void switchCheckState(int index) {
    BoxItem<T> bi = model.get(index);
    if (checkedItems.contains(bi))
      checkedItems.remove(bi);
    else
      checkedItems.add(bi);
  }

  public IReadOnlySet<T> getCheckedItems(){
    ISet<T> ret = this.checkedItems.select(q->q.value);
    return ret;
  }

  public void setCheckedItems(Iterable<T> items){
    this.checkedItems.clear();
    for (T item : items) {
      int index = super.getIndexOfItem(item);
      if (index != -1)
        checkedItems.add(model.get(index));
    }
    this.control.repaint();
    onCheckedChanged.raise();
  }

  private void changeStateOfSelected() {
    int[] indices = control.getSelectedIndices();
    boolean isAllChecked = true;
    for (int index : indices) {
      if (checkedItems.contains(model.get(index)) == false) {
        isAllChecked = false;
        break;
      }
    }

    if (isAllChecked) {
      for (int index : indices) {
        checkedItems.remove(model.get(index));
      }
    } else {
      for (int index : indices) {
        checkedItems.add(model.get(index));
      }
    }
  }

  public void checkAll(){
    ISet<T> tmp = new ESet<>();
    for (int i = 0; i < this.model.size(); i++) {
      tmp.add(this.model.get(i).value);
    }
    setCheckedItems(tmp);
  }

  public void checkNone(){
    setCheckedItems(new ESet<>());
  }

  @Override
  public void clearItems(){
    super.clearItems();
    this.checkedItems.clear();
  }

}

class StringCheckListCellRenderer<T> extends JCheckBox implements ListCellRenderer<BoxItem<T>> {

  private final ISet<BoxItem<T>> checkedItems;
  private BoxItem<T> myValue;

  public StringCheckListCellRenderer(ISet<BoxItem<T>> checkedItems) {
    this.checkedItems = checkedItems;
    this.addActionListener(q -> System.out.println("Action"));
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        setSelected(!isSelected());
      }
    });
  }

  @Override
  public Component getListCellRendererComponent(JList<? extends BoxItem<T>> list, BoxItem<T> value, int index, boolean isSelected, boolean cellHasFocus) {
    this.setComponentOrientation(list.getComponentOrientation());
    this.setFont(list.getFont());
    this.setText(value.label);
    this.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
    this.setForeground(list.getForeground());
    this.setSelected(checkedItems.contains(value));
    this.setEnabled(list.isEnabled());
    this.myValue = value;


    return this;
  }
}
