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

  private static String getLabelString(Path path) {
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

  public HistoryForJFileChooser(Dimension d) {
    initContext();
    initLayout(d);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    // nothing to do here
  }

  public void setHistory(IList<Path> items) {
    lste.addItems(items);
  }

  private void initContext() {
    lst = new JList<>();
    lste = new ListBoxExtender<>(lst, q -> HistoryForJFileChooser.getLabelString(q));
    pnlScroll = new JScrollPane(lst);
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
}
