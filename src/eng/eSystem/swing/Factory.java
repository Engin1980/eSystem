package eng.eSystem.swing;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Factory {
  public static JButton createButton(String title, ActionListener action) {
    JButton ret = new JButton(title);
    ret.addActionListener(action);
    return ret;
  }

  public static JScrollBar createHorizontalBar(int minimum, int maximum, int value) {
    JScrollBar ret = new JScrollBar(JScrollBar.HORIZONTAL);
    ret.getModel().setRangeProperties(value, 0, minimum, maximum, true);
    return ret;
  }

  public static JScrollBar createVerticalBar(int minimum, int maximum, int value) {
    JScrollBar ret = new JScrollBar(JScrollBar.VERTICAL);
    ret.getModel().setRangeProperties(value, 0, minimum, maximum, true);
    return ret;
  }
}
