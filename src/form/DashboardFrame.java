package form;


import controller.*;import java.awt.*;import java.time.*;import java.time.format.DateTimeFormatter;import javax.swing.*;import model.User;import util.UIUtils;

public class DashboardFrame extends JFrame{private final CardLayout cards=new CardLayout();private final JPanel content=new JPanel(cards);private final JLabel clock=new JLabel();private final User user;
 public DashboardFrame(User user){this.user=user;setTitle("Kas Sekolah - MI Nahdlotut Tholibin");setSize(1366,768);setLocationRelativeTo(null);setDefaultCloseOperation(EXIT_ON_CLOSE);setLayout(new BorderLayout());add(sidebar(),BorderLayout.WEST);add(header(),BorderLayout.NORTH);registerPages();add(content,BorderLayout.CENTER);startClock();}
 private void registerPages(){content.add(dashboard(),"Dashboard");content.add(new KelasFrame(),"Data Kelas");content.add(new SiswaFrame(),"Data Siswa");content.add(new KategoriPemasukanFrame(),"Kategori Pemasukan");content.add(new PemasukanFrame(),"Pemasukan Kas");content.add(new KategoriPengeluaranFrame(),"Kategori Pengeluaran");content.add(new PengeluaranFrame(),"Pengeluaran Kas");content.add(new RiwayatTransaksiFrame(),"Riwayat Transaksi");content.add(new LaporanFrame(),"Laporan");content.add(new UserFrame(),"Data User");content.add(new PengaturanFrame(),"Pengaturan");}
 private JPanel header(){JPanel h=new JPanel(new BorderLayout());h.setBackground(UIUtils.CARD);h.setBorder(BorderFactory.createEmptyBorder(12,20,12,20));JLabel title=new JLabel("Rancang Bangun Aplikasi Pengelolaan Kas Sekolah dan Laporan Keuangan Berbasis Desktop");title.setFont(UIUtils.FONT_BOLD);JLabel account=new JLabel(user.getNama()+" ("+user.getRole()+")   |   ");account.setFont(UIUtils.FONT);JPanel right=new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));right.setOpaque(false);right.add(account);right.add(clock);h.add(title,BorderLayout.WEST);h.add(right,BorderLayout.EAST);return h;}
 private JPanel sidebar(){JPanel s=new JPanel(new BorderLayout());s.setPreferredSize(new Dimension(260,0));s.setBackground(UIUtils.SIDEBAR);s.setBorder(BorderFactory.createEmptyBorder(18,14,18,14));JLabel brand=new JLabel("  KAS SEKOLAH",UIUtils.schoolLogo(38),JLabel.LEFT);brand.setFont(UIUtils.FONT_TITLE);brand.setForeground(Color.WHITE);JPanel menu=new JPanel(new GridLayout(0,1,0,7));menu.setOpaque(false);String[] items={"Dashboard","Data Kelas","Data Siswa","Kategori Pemasukan","Pemasukan Kas","Kategori Pengeluaran","Pengeluaran Kas","Riwayat Transaksi","Laporan","Data User","Pengaturan"};for(String item:items){JButton b=menuButton(item);b.addActionListener(e->{if(item.equals("Dashboard")){content.remove(0);content.add(dashboard(),"Dashboard",0);}cards.show(content,item);});menu.add(b);}JButton logout=menuButton("Logout");logout.addActionListener(e->logout());s.add(brand,BorderLayout.NORTH);s.add(menu,BorderLayout.CENTER);s.add(logout,BorderLayout.SOUTH);return s;}
 private JButton menuButton(String text){JButton b=new JButton(text);b.setFont(UIUtils.FONT_BOLD);b.setForeground(Color.WHITE);b.setBackground(new Color(0x1D4ED8));b.setFocusPainted(false);b.setHorizontalAlignment(SwingConstants.LEFT);b.setBorder(BorderFactory.createEmptyBorder(10,14,10,14));return b;}
 private JPanel dashboard(){JPanel p=UIUtils.page("Dashboard");JPanel grid=new JPanel(new GridLayout(2,2,18,18));grid.setOpaque(false);grid.add(stat("Total Siswa",String.valueOf(siswa())));grid.add(stat("Total Pemasukan",UIUtils.rupiah(masuk())));grid.add(stat("Total Pengeluaran",UIUtils.rupiah(keluar())));grid.add(stat("Saldo Kas",UIUtils.rupiah(masuk()-keluar())));p.add(grid,BorderLayout.CENTER);JLabel date=new JLabel("Tanggal hari ini: "+LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));date.setFont(UIUtils.FONT_BOLD);p.add(date,BorderLayout.SOUTH);return p;}
 private JPanel stat(String label,String value){JPanel c=UIUtils.card();c.setLayout(new BorderLayout(8,8));JLabel l=new JLabel(label);l.setFont(UIUtils.FONT_BOLD);l.setForeground(UIUtils.MUTED);JLabel v=new JLabel(value);v.setFont(new Font("Segoe UI",Font.BOLD,32));v.setForeground(UIUtils.SIDEBAR);c.add(l,BorderLayout.NORTH);c.add(v,BorderLayout.CENTER);return c;}
 private int siswa(){try{return new SiswaController().count();}catch(Exception e){return 0;}}private double masuk(){try{return new PemasukanController().total();}catch(Exception e){return 0;}}private double keluar(){try{return new PengeluaranController().total();}catch(Exception e){return 0;}}
 private void startClock(){clock.setFont(UIUtils.FONT_BOLD);new Timer(1000,e->clock.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))).start();}
 private void logout(){if(JOptionPane.showConfirmDialog(this,"Logout dari aplikasi?","Konfirmasi",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){new LoginFrame().setVisible(true);dispose();}}

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
