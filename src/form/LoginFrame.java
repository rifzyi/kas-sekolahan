package form;

import controller.UserController;
import java.awt.*;
import javax.swing.*;
import model.User;
import util.UIUtils;

public class LoginFrame extends JFrame {
  private final JTextField username = UIUtils.textField(20);
  private final JPasswordField password = new JPasswordField(20);

  public LoginFrame() {
    setTitle("Login - Sistem Pengelolaan Kas Sekolah MI");
    setSize(980, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new GridLayout(1, 2));
    add(hero());
    add(form());
  }

  private JPanel hero() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(UIUtils.NAVY);
    JLabel title = new JLabel("KAS SEKOLAH MI", UIUtils.schoolLogo(74), JLabel.CENTER);
    title.setVerticalTextPosition(JLabel.BOTTOM);
    title.setHorizontalTextPosition(JLabel.CENTER);
    title.setForeground(Color.WHITE);
    title.setFont(new Font("Segoe UI", Font.BOLD, 32));
    JLabel sub = new JLabel("Dashboard administrasi kas sekolah modern");
    sub.setForeground(new Color(0xD6E9FF));
    sub.setFont(UIUtils.FONT_BOLD);
    JPanel box = new JPanel(new GridLayout(0, 1, 0, 18));
    box.setOpaque(false);
    box.add(title); box.add(sub);
    panel.add(box);
    return panel;
  }

  private JPanel form() {
    JPanel wrap = new JPanel(new GridBagLayout());
    wrap.setBackground(UIUtils.BACKGROUND);
    JPanel card = UIUtils.card();
    card.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
    JLabel title = new JLabel("Masuk Aplikasi");
    title.setFont(new Font("Segoe UI", Font.BOLD, 26));
    title.setForeground(UIUtils.NAVY);
    card.add(title, gbc);
    password.setFont(UIUtils.FONT);
    password.setBorder(username.getBorder());
    password.setPreferredSize(username.getPreferredSize());
    addRow(card, gbc, 1, "Username", username);
    addRow(card, gbc, 2, "Password", password);
    JButton login = UIUtils.button("Login", UIUtils.BLUE);
    login.addActionListener(e -> login());
    gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
    card.add(login, gbc);
    JLabel hint = new JLabel("Default: admin / admin123");
    hint.setForeground(UIUtils.MUTED);
    hint.setFont(UIUtils.FONT);
    gbc.gridy = 4;
    card.add(hint, gbc);
    wrap.add(card);
    return wrap;
  }

  private void addRow(JPanel panel, GridBagConstraints gbc, int y, String label, JComponent field) {
    gbc.gridy = y; gbc.gridx = 0; gbc.gridwidth = 1; gbc.weightx = 0;
    panel.add(UIUtils.formLabel(label), gbc);
    gbc.gridx = 1; gbc.weightx = 1;
    panel.add(field, gbc);
  }

  private void login() {
    try {
      User user = new UserController().login(username.getText().trim(), new String(password.getPassword()));
      if (user == null) { JOptionPane.showMessageDialog(this, "Username atau password salah", "Login", JOptionPane.WARNING_MESSAGE); return; }
      new DashboardFrame(user).setVisible(true);
      dispose();
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Database", JOptionPane.ERROR_MESSAGE);
    }
  }
}
