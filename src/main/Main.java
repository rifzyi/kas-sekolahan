package main;

import form.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import util.UIUtils;

public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      UIUtils.installFlatLafIfAvailable();
      try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
      new LoginFrame().setVisible(true);
    });
  }
}
