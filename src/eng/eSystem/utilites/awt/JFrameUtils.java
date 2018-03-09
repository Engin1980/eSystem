package eng.eSystem.utilites.awt;

import javax.swing.*;

public class JFrameUtils {
  public static void showDialog(JFrame frame){
    frame.setVisible(true);

    while (frame.isVisible()) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException ex) {
      }
    }
  }
}
