package eng.eSystem.swing.other;

import eng.eSystem.validation.EAssert;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;

public abstract class JFileChooserAsidePanel extends JComponent implements PropertyChangeListener {

  /**
   * Used to define layout of aside panels.
   */
  public static class LayoutDefinition {
    private eOrientation orientation;
    private int totalWidth;
    private int totalHeight;

    public LayoutDefinition(eOrientation orientation, int totalWidth, int totalHeight) {
      this.orientation = orientation;
      this.totalWidth = totalWidth;
      this.totalHeight = totalHeight;
    }

    public eOrientation getOrientation() {
      return orientation;
    }

    public int getTotalWidth() {
      return totalWidth;
    }

    public int getTotalHeight() {
      return totalHeight;
    }
  }

  /**
   * Represents orientation.
   */
  public enum eOrientation {
    horizontal,
    vertical
  }

  private JFileChooser jFileChooser;

  public static void bind(JFileChooser chooser, LayoutDefinition layoutDefinition, JFileChooserAsidePanel... panels) {
    EAssert.isNotNull(chooser);
    EAssert.isNotNull(layoutDefinition);
    for (JFileChooserAsidePanel panel : panels) {
      EAssert.isNotNull(panel);
    }

    for (JFileChooserAsidePanel panel : panels) {
      chooser.addPropertyChangeListener(panel);
      panel.jFileChooser = chooser;
    }
    JPanel contentPanel = new JPanel();
    if (layoutDefinition.getOrientation() == JFileChooserAsidePanel.eOrientation.horizontal)
      contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
    else
      contentPanel.setLayout((new BoxLayout(contentPanel, BoxLayout.Y_AXIS)));
    Dimension dimension = new Dimension(layoutDefinition.getTotalWidth(), layoutDefinition.getTotalHeight());
    eng.eSystem.swing.LayoutManager.setFixedSize(contentPanel, dimension);
    for (JFileChooserAsidePanel panel : panels) {
      contentPanel.add(panel);
    }
    chooser.setAccessory(contentPanel);
  }

  protected JFileChooser getJFileChooser() {
    return this.jFileChooser;
  }
}
