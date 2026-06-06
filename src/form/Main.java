// File: form/Main.java
package form;

import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import util.UIUtils;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf()); } catch (Exception ex) { ex.printStackTrace(); }
            UIUtils.applyGlobalStyle();
            new LoginFrame().setVisible(true);
        });
    }
}
