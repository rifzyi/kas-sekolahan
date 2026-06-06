// File: view/LoginFrame.java
package view;

import controller.AuthController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.User;
import util.UIUtils;

public class LoginFrame extends JFrame {
    private final AuthController authController = new AuthController();
    private final JTextField usernameField = UIUtils.modernTextField("Username");
    private final JPasswordField passwordField = new JPasswordField();

    public LoginFrame() {
        setTitle("Login - Manajemen Kas Sekolah");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(460, 420));
        setLocationRelativeTo(null);
        buildUi();
    }

    private void buildUi() {
        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(UIUtils.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JPanel card = UIUtils.modernCardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(380, 320));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        JLabel title = UIUtils.title("Manajemen Kas Sekolah");
        JLabel subtitle = new JLabel("Masuk untuk mengelola pemasukan dan pengeluaran");
        subtitle.setForeground(UIUtils.MUTED);

        gbc.gridy = 0;
        card.add(title, gbc);
        gbc.gridy = 1;
        card.add(subtitle, gbc);
        gbc.gridy = 2;
        card.add(UIUtils.label("Username"), gbc);
        gbc.gridy = 3;
        card.add(usernameField, gbc);
        gbc.gridy = 4;
        card.add(UIUtils.label("Password"), gbc);
        gbc.gridy = 5;
        passwordField.setBorder(usernameField.getBorder());
        passwordField.setPreferredSize(usernameField.getPreferredSize());
        card.add(passwordField, gbc);
        gbc.gridy = 6;
        card.add(UIUtils.modernButton("Login", UIUtils.PRIMARY), gbc);

        ((javax.swing.JButton) card.getComponent(6)).addActionListener(event -> login());
        passwordField.addActionListener(event -> login());
        root.add(card);
        add(root, BorderLayout.CENTER);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi.", "Validasi Login", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            User user = authController.login(username, password);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Username atau password salah.", "Login Gagal", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new MainFrame(user).setVisible(true);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal login: " + ex.getMessage(), "Database", JOptionPane.ERROR_MESSAGE);
        }
    }
}
