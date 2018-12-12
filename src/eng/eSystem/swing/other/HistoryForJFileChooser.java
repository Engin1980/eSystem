package eng.eSystem.swing.other;

import eng.eSystem.collections.EList;
import eng.eSystem.swing.LayoutManager;
import eng.eSystem.swing.extenders.ListBoxExtender;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.nio.file.Path;

public class HistoryForJFileChooser extends JFileChooserAsidePanel {

  public static class HistoryItem {
    public final Path path;

    public HistoryItem(Path path) {
      this.path = path;
    }

    public Path getPath(){
      return this.path;
    }

    public String getLabelString() {
      String name;
      String parent;

      if (path.getNameCount() < 2)
      {
        name = path.toString();
        parent = null;
      } else {
        name = path.getName(path.getNameCount()-1).toString();
        parent = path.getParent().toString();
      }

      String ret;
      if (parent == null)
        ret = name;
      else
        ret = name + " (" + parent + ")";
      return ret;
    }

    public String getFullPath() {
      return path.toString();
    }

    public String getFullPathAbsolute() {
      return path.toAbsolutePath().toString();
    }
  }

  private JScrollPane pnlScroll;
  private JList<HistoryItem> lst;
  private ListBoxExtender<HistoryItem> lste;

  public HistoryForJFileChooser(Dimension d) {
    initContext();
    initLayout(d);

  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    System.out.println("evt!" + evt.getPropertyName());

  }

  public void setHistory(EList<HistoryItem> items) {
    lste.addItems(items);
  }

  private void initContext() {
    lst = new JList<>();
    lste = new ListBoxExtender<>(lst);
    pnlScroll = new JScrollPane(lst);
    this.lste.setDefaultLabelSelector(q -> q.getLabelString());
    lst.addListSelectionListener(this::lst_ListSelectionListener);
  }

  private void lst_ListSelectionListener(ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) return;
    HistoryItem hi = lste.getSelectedItems().getFirst();
    java.io.File file = hi.getPath().toFile();
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
