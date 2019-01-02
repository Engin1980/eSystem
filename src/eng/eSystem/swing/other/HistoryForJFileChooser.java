package eng.eSystem.swing.other;

import eng.eSystem.collections.IList;
import eng.eSystem.swing.LayoutManager;
import eng.eSystem.swing.extenders.ListBoxExtender;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.nio.file.Path;

public class HistoryForJFileChooser extends JFileChooserAsidePanel {

  private JScrollPane pnlScroll;
  private JList<Path> lst;
  private ListBoxExtender<Path> lste;

  public HistoryForJFileChooser(Dimension d) {
    initContext();
    initLayout(d);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    // nothing to do here
  }

<<<<<<< HEAD
  public void setHistory(EList<Path> items) {
=======
  public void setHistory(IList<Path> items) {
>>>>>>> 58d093d0eadba87eb50b16b61e50d237a255bc4c
    lste.addItems(items);
  }

  private void initContext() {
    lst = new JList<>();
    lste = new ListBoxExtender<>(lst);
    pnlScroll = new JScrollPane(lst);
<<<<<<< HEAD
    this.lste.setDefaultLabelSelector(q -> HistoryForJFileChooser.getLabelString(q));
=======
    this.lste.setDefaultLabelSelector(q -> this.getLabelString(q));
>>>>>>> 58d093d0eadba87eb50b16b61e50d237a255bc4c
    lst.addListSelectionListener(this::lst_ListSelectionListener);
  }

  private void lst_ListSelectionListener(ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) return;
    Path path = lste.getSelectedItems().getFirst();
    java.io.File file = path.toFile();
    if (file.isDirectory())
      super.getJFileChooser().setCurrentDirectory(file);
    else
      super.getJFileChooser().setSelectedFile(file);
  }

  private void initLayout(Dimension d) {
    LayoutManager.setFixedSize(this, d);
    this.setLayout(new BorderLayout(8, 8));
    this.setBorder(BorderFactory.createTitledBorder("History:"));
    this.add(pnlScroll);
  }

<<<<<<< HEAD
  private static String getLabelString(Path path) {
=======
  private String getLabelString(Path path) {
>>>>>>> 58d093d0eadba87eb50b16b61e50d237a255bc4c
    String name;
    String parent;

    if (path.getNameCount() < 2) {
      name = path.toString();
      parent = null;
    } else {
      name = path.getName(path.getNameCount() - 1).toString();
      parent = path.getParent().toString();
    }

    String ret;
    if (parent == null)
      ret = name;
    else
      ret = name + " (" + parent + ")";
    return ret;
  }
}
