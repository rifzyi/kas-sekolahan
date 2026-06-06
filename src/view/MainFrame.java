// File: view/MainFrame.java
package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.User;
import util.UIUtils;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    public MainFrame(User user) {
        setTitle("Manajemen Kas Sekolah - " + user.getNama());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1120, 720));
        setLocationRelativeTo(null);
        buildUi(user);
    }

    private void buildUi(User user) {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIUtils.BACKGROUND);
        root.add(createSidebar(user), BorderLayout.WEST);

        contentPanel.setBackground(UIUtils.BACKGROUND);
        contentPanel.add(new DashboardPanel(), "Dashboard");
        contentPanel.add(new SiswaPanel(), "Siswa");
        contentPanel.add(new PemasukanPanel(), "Pemasukan");
        contentPanel.add(new PengeluaranPanel(), "Pengeluaran");
        contentPanel.add(new PengaturanPanel(), "Pengaturan");
        root.add(contentPanel, BorderLayout.CENTER);
        add(root);
    }

    private JPanel createSidebar(User user) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new javax.swing.BoxLayout(sidebar, javax.swing.BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBackground(UIUtils.SIDEBAR);
        sidebar.setBorder(BorderFactory.createEmptyBorder(24, 16, 24, 16));

        JLabel appName = new JLabel("Kas Sekolah");
        appName.setForeground(Color.WHITE);
        appName.setFont(new Font("SansSerif", Font.BOLD, 24));
        JLabel account = new JLabel(user.getNama() + " • " + user.getRole());
        account.setForeground(new Color(0x9CA3AF));
        account.setBorder(BorderFactory.createEmptyBorder(4, 0, 24, 0));
        sidebar.add(appName);
        sidebar.add(account);

        addMenu(sidebar, "Dashboard");
        addMenu(sidebar, "Siswa");
        addMenu(sidebar, "Pemasukan");
        addMenu(sidebar, "Pengeluaran");
        addMenu(sidebar, "Pengaturan");
        return sidebar;
    }

    private void addMenu(JPanel sidebar, String name) {
        JButton button = new JButton(name);
        button.setAlignmentX(LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        button.setForeground(Color.WHITE);
        button.setBackground(UIUtils.SIDEBAR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        button.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        button.addActionListener(event -> cardLayout.show(contentPanel, name));
        sidebar.add(button);
        sidebar.add(javax.swing.Box.createVerticalStrut(6));
    }
}
