package form;

import controller.PemasukanController;
import controller.PengeluaranController;
import controller.SiswaController;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.User;
import util.UIUtils;

public class DashboardFrame extends JFrame {
  private final CardLayout cardLayout = new CardLayout();
  private final JPanel content = new JPanel(cardLayout);
  private final User user;

  public DashboardFrame(User user) {
    this.user = user;
    setTitle("Aplikasi Pengelolaan Kas Sekolah - MI Nahdlotut Tholibin");
    setSize(1366, 768);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    add(sidebar(), BorderLayout.WEST);
    add(header(), BorderLayout.NORTH);
    content.add(dashboardPanel(), "Dashboard");
    content.add(new SiswaFrame(), "Data Siswa");
    content.add(new PemasukanFrame(), "Pemasukan");
    content.add(new PengeluaranFrame(), "Pengeluaran");
    content.add(new UserFrame(), "Data User");
    content.add(new LaporanFrame(), "Laporan");
    add(content, BorderLayout.CENTER);
  }

  private JPanel header() {
    JPanel header = new JPanel(new BorderLayout());
    header.setBackground(UIUtils.WHITE);
    header.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
    JLabel title = new JLabel("Rancang Bangun Aplikasi Pengelolaan Kas " +
                              "Sekolah dan Laporan Keuangan Berbasis Desktop");
    title.setFont(UIUtils.FONT_BOLD);
    title.setForeground(UIUtils.TEXT);
    JLabel account = new JLabel(user.getNama() + " (" + user.getRole() + ")");
    account.setFont(UIUtils.FONT);
    account.setForeground(UIUtils.MUTED);
    header.add(title, BorderLayout.WEST);
    header.add(account, BorderLayout.EAST);
    return header;
  }

  private JPanel sidebar() {
    JPanel side = new JPanel(new BorderLayout());
    side.setPreferredSize(new Dimension(230, 0));
    side.setBackground(UIUtils.NAVY);
    side.setBorder(BorderFactory.createEmptyBorder(18, 14, 18, 14));
    JLabel brand = new JLabel("KAS SEKOLAH");
    brand.setFont(UIUtils.FONT_TITLE);
    brand.setForeground(Color.WHITE);
    brand.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    JPanel menu = new JPanel(new GridLayout(0, 1, 0, 10));
    menu.setOpaque(false);
    for (String item : new String[] {"Dashboard", "Data Siswa", "Pemasukan",
                                     "Pengeluaran", "Data User", "Laporan"}) {
      JButton button = menuButton(item);
      button.addActionListener(e -> {
        if (item.equals("Dashboard"))
          refreshDashboard();
        cardLayout.show(content, item);
      });
      menu.add(button);
    }
    JButton logout = menuButton("Logout");
    logout.addActionListener(e -> logout());
    side.add(brand, BorderLayout.NORTH);
    side.add(menu, BorderLayout.CENTER);
    side.add(logout, BorderLayout.SOUTH);
    return side;
  }

  private JButton menuButton(String text) {
    JButton button = new JButton(text);
    button.setFont(UIUtils.FONT_BOLD);
    button.setForeground(Color.WHITE);
    button.setBackground(new Color(30, 64, 120));
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
    return button;
  }

  private JPanel dashboardPanel() {
    JPanel page = UIUtils.page("Dashboard");
    JPanel cards = new JPanel(new GridLayout(2, 2, 18, 18));
    cards.setOpaque(false);
    cards.add(statCard("Total Pemasukan", getTotalPemasukan()));
    cards.add(statCard("Total Pengeluaran", getTotalPengeluaran()));
    cards.add(statCard("Saldo Kas", getSaldo()));
    cards.add(statCard("Jumlah Siswa", getJumlahSiswa()));
    page.add(cards, BorderLayout.CENTER);
    return page;
  }

  private void refreshDashboard() {
    content.remove(0);
    content.add(dashboardPanel(), "Dashboard", 0);
  }

  private JPanel statCard(String label, String value) {
    JPanel card = UIUtils.card();
    card.setLayout(new BorderLayout(8, 8));
    JLabel labelView = new JLabel(label);
    labelView.setFont(UIUtils.FONT_BOLD);
    labelView.setForeground(UIUtils.MUTED);
    JLabel valueView = new JLabel(value);
    valueView.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 30));
    valueView.setForeground(UIUtils.NAVY);
    card.add(labelView, BorderLayout.NORTH);
    card.add(valueView, BorderLayout.CENTER);
    return card;
  }

  private String getTotalPemasukan() {
    try {
      return UIUtils.rupiah(new PemasukanController().total());
    } catch (Exception e) {
      return UIUtils.rupiah(0);
    }
  }

  private String getTotalPengeluaran() {
    try {
      return UIUtils.rupiah(new PengeluaranController().total());
    } catch (Exception e) {
      return UIUtils.rupiah(0);
    }
  }

  private String getSaldo() {
    try {
      return UIUtils.rupiah(new PemasukanController().total() -
                            new PengeluaranController().total());
    } catch (Exception e) {
      return UIUtils.rupiah(0);
    }
  }

  private String getJumlahSiswa() {
    try {
      return String.valueOf(new SiswaController().count());
    } catch (Exception e) {
      return "0";
    }
  }

  private void logout() {
    if (JOptionPane.showConfirmDialog(this, "Keluar dari aplikasi?", "Logout",
                                      JOptionPane.YES_NO_OPTION) ==
        JOptionPane.YES_OPTION) {
      new LoginFrame().setVisible(true);
      dispose();
    }
  }
}
