package eng.eSystem.swing.extenders;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatternTextFieldExtender {
  private final JTextField txt;
  private final Pattern pattern;
  private Color errorBackground = Color.YELLOW;
  private Color normalBackground;
  private Color errorForeground;
  private Color normalForeground;

  public RegexPatternTextFieldExtender(JTextField txt, String pattern) {
    this.txt = txt;
    this.pattern = Pattern.compile(pattern);

    this.normalBackground = txt.getBackground();
    this.errorForeground = txt.getForeground();
    this.normalForeground = txt.getForeground();

    addListener();
    doCheck();
  }

  public RegexPatternTextFieldExtender(String pattern) {
    this(new JTextField(), pattern);
  }

  public Color getErrorBackground() {
    return this.errorBackground;
  }

  public void setErrorBackground(Color errorBackground) {
    this.errorBackground = errorBackground;
    doCheck();
  }

  public Color getErrorForeground() {
    return errorForeground;
  }

  public void setErrorForeground(Color errorForeground) {
    this.errorForeground = errorForeground;
    doCheck();
  }

  public Color getNormalBackground() {
    return normalBackground;
  }

  public void setNormalBackground(Color normalBackground) {
    this.normalBackground = normalBackground;
    doCheck();
  }

  public Color getNormalForeground() {
    return normalForeground;
  }

  public void setNormalForeground(Color normalForeground) {
    this.normalForeground = normalForeground;
    doCheck();
  }

  public JTextField getControl() {
    return this.txt;
  }

  public String getText() {
    return this.txt.getText();
  }

  public void setText(String text) {
    this.txt.setText(text);
  }

  private void addListener() {
    // Listen for changes in the text
    this.txt.getDocument().addDocumentListener(new DocumentListener() {
      public void insertUpdate(DocumentEvent e) {
        doCheck();
      }

      public void removeUpdate(DocumentEvent e) {
        doCheck();
      }

      public void changedUpdate(DocumentEvent e) {
        doCheck();
      }
    });
  }

  private void doCheck() {
    Matcher m = this.pattern.matcher(this.txt.getText());
    if (m.find()) {
      txt.setForeground(this.normalForeground);
      txt.setBackground(this.normalBackground);
    } else {
      txt.setForeground(this.errorForeground);
      txt.setBackground(this.errorBackground);
    }
  }
}
