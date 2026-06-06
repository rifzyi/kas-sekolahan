package form;

import controller.PemasukanController;
import controller.PengeluaranController;
import controller.SiswaController;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import model.User;
import util.UIUtils;

public class DashboardFrame extends JFrame {
  private final CardLayout cardLayout = new CardLayout();
  private final JPanel content = new JPanel(cardLayout);
  private final Map<String, JButton> menuButtons = new HashMap<>();
  private final User user;

  public DashboardFrame(User user) {
    this.user = user;
    setTitle("Sistem Pengelolaan Kas Sekolah MI");
    setSize(1366, 768);
    setMinimumSize(new Dimension(1100, 680));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    add(sidebar(), BorderLayout.WEST);
    add(header(), BorderLayout.NORTH);
    registerPages();
    add(content, BorderLayout.CENTER);
    showPage("Dashboard");
  }

  private void registerPages() {
    content.add(dashboardPanel(), "Dashboard");
    content.add(new SiswaFrame(), "Data Siswa");
    content.add(new KelasFrame(), "Data Kelas");
    content.add(new KategoriPemasukanFrame(), "Kategori Pemasukan");
    content.add(new KategoriPengeluaranFrame(), "Kategori Pengeluaran");
    content.add(new PemasukanFrame(), "Pemasukan");
    content.add(new PengeluaranFrame(), "Pengeluaran");
    content.add(new LaporanFrame(), "Laporan");
    content.add(new PengaturanFrame(), "Pengaturan");
    content.add(new UserFrame(), "User");
  }

  private JPanel sidebar() {
    JPanel sidebar = new JPanel(new BorderLayout());
    sidebar.setPreferredSize(new Dimension(220, 0));
    sidebar.setBackground(UIUtils.NAVY);
    sidebar.setBorder(BorderFactory.createEmptyBorder(18, 12, 18, 12));
    JPanel brand = new JPanel(new BorderLayout(10, 0));
    brand.setOpaque(false);
    brand.add(new JLabel(UIUtils.schoolLogo(46)), BorderLayout.WEST);
    JLabel name = new JLabel("Kas Sekolah MI");
    name.setForeground(Color.WHITE);
    name.setFont(UIUtils.FONT_TITLE);
    brand.add(name, BorderLayout.CENTER);
    JPanel menu = new JPanel(new GridLayout(0, 1, 0, 7));
    menu.setOpaque(false);
    String[] items = {"Dashboard", "Data Siswa", "Data Kelas", "Kategori Pemasukan", "Kategori Pengeluaran", "Pemasukan", "Pengeluaran", "Laporan", "Pengaturan", "User"};
    for (String item : items) {
      JButton button = menuButton(iconFor(item) + "  " + item);
      button.addActionListener(e -> showPage(item));
      menuButtons.put(item, button);
      menu.add(button);
    }
    JButton logout = menuButton("⎋  Logout");
    logout.addActionListener(e -> logout());
    sidebar.add(brand, BorderLayout.NORTH);
    sidebar.add(menu, BorderLayout.CENTER);
    sidebar.add(logout, BorderLayout.SOUTH);
    return sidebar;
  }

  private JPanel header() {
    JPanel header = new JPanel(new BorderLayout());
    header.setPreferredSize(new Dimension(0, 60));
    header.setBackground(UIUtils.BLUE);
    header.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 24));
    JLabel title = new JLabel("SISTEM PENGELOLAAN KAS SEKOLAH");
    title.setForeground(Color.WHITE);
    title.setFont(new Font("Segoe UI", Font.BOLD, 18));
    JLabel account = new JLabel(user.getNama() + "  •  " + user.getRole() + "   ●");
    account.setForeground(Color.WHITE);
    account.setFont(UIUtils.FONT_BOLD);
    JLabel clock = new JLabel();
    clock.setForeground(Color.WHITE);
    clock.setFont(UIUtils.FONT_BOLD);
    new Timer(1000, e -> clock.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))).start();
    JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 17));
    right.setOpaque(false);
    right.add(clock);
    right.add(account);
    header.add(title, BorderLayout.WEST);
    header.add(right, BorderLayout.EAST);
    return header;
  }

  private void showPage(String page) {
    if ("Dashboard".equals(page)) {
      content.remove(0);
      content.add(dashboardPanel(), "Dashboard", 0);
    }
    cardLayout.show(content, page);
    menuButtons.forEach((name, button) -> button.setBackground(name.equals(page) ? UIUtils.CYAN : UIUtils.NAVY));
  }

  private JButton menuButton(String text) {
    JButton button = new JButton(text);
    button.setHorizontalAlignment(SwingConstants.LEFT);
    button.setFont(UIUtils.FONT_BOLD);
    button.setForeground(Color.WHITE);
    button.setBackground(UIUtils.NAVY);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(11, 12, 11, 12));
    button.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent e) { if (button.getBackground().equals(UIUtils.NAVY)) button.setBackground(new Color(0x1976D2)); }
      public void mouseExited(java.awt.event.MouseEvent e) { if (!menuButtons.containsValue(button) || !button.getBackground().equals(UIUtils.CYAN)) button.setBackground(UIUtils.NAVY); }
    });
    return button;
  }

  private JPanel dashboardPanel() {
    JPanel panel = UIUtils.page("Dashboard");
    JPanel grid = new JPanel(new GridLayout(1, 4, 18, 18));
    grid.setOpaque(false);
    double masuk = safeTotalMasuk();
    double keluar = safeTotalKeluar();
    grid.add(statCard("👨‍🎓", "Jumlah Siswa", String.valueOf(safeSiswa()), UIUtils.BLUE));
    grid.add(statCard("⬇", "Total Pemasukan", UIUtils.rupiah(masuk), UIUtils.GREEN));
    grid.add(statCard("⬆", "Total Pengeluaran", UIUtils.rupiah(keluar), UIUtils.ORANGE));
    grid.add(statCard("💰", "Saldo Kas", UIUtils.rupiah(masuk - keluar), UIUtils.RED));
    JPanel body = new JPanel(new BorderLayout());
    body.setOpaque(false);
    body.add(grid, BorderLayout.NORTH);
    panel.add(body, BorderLayout.CENTER);
    return panel;
  }

  private JPanel statCard(String icon, String label, String value, Color color) {
    JPanel card = UIUtils.card();
    card.setLayout(new BorderLayout(12, 10));
    JLabel iconLabel = new JLabel(icon);
    iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
    iconLabel.setForeground(color);
    JLabel labelView = new JLabel(label);
    labelView.setFont(UIUtils.FONT_BOLD);
    labelView.setForeground(UIUtils.MUTED);
    JLabel valueView = new JLabel(value);
    valueView.setFont(new Font("Segoe UI", Font.BOLD, 24));
    valueView.setForeground(color);
    card.add(iconLabel, BorderLayout.WEST);
    JPanel text = new JPanel(new GridLayout(2, 1));
    text.setOpaque(false);
    text.add(labelView);
    text.add(valueView);
    card.add(text, BorderLayout.CENTER);
    return card;
  }

  private String iconFor(String item) {
    if (item.contains("Siswa")) return "👨‍🎓";
    if (item.contains("Kelas")) return "🏫";
    if (item.contains("Pemasukan")) return "⬇";
    if (item.contains("Pengeluaran")) return "⬆";
    if (item.contains("Laporan")) return "📄";
    if (item.contains("Pengaturan")) return "⚙";
    if (item.contains("User")) return "👤";
    return "▣";
  }

  private int safeSiswa() { try { return new SiswaController().count(); } catch (Exception e) { return 0; } }
  private double safeTotalMasuk() { try { return new PemasukanController().total(); } catch (Exception e) { return 0; } }
  private double safeTotalKeluar() { try { return new PengeluaranController().total(); } catch (Exception e) { return 0; } }
  private void logout() { if (JOptionPane.showConfirmDialog(this, "Logout dari aplikasi?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) { new LoginFrame().setVisible(true); dispose(); } }
}
