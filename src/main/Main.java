// File: main/Main.java
package main;

import javax.swing.SwingUtilities;
import util.UIUtils;
import view.LoginFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UIUtils.installFlatLafIfAvailable();
            UIUtils.applyGlobalStyle();
            new LoginFrame().setVisible(true);
        });
    }
}
