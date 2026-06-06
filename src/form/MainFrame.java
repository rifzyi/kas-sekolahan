// File: form/MainFrame.java
package form;

import java.awt.*;import java.util.Arrays;import javax.swing.*;import util.SessionManager;import util.UIUtils;

public class MainFrame extends JFrame {
    private final JPanel contentPanel=new JPanel(new CardLayout());
    public MainFrame(){ setTitle("SKM - Sistem Keuangan Madrasah"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(1200,760); setLocationRelativeTo(null); JPanel root=new JPanel(new BorderLayout()); root.add(sidebar(),BorderLayout.WEST); contentPanel.add(new DashboardForm(),"Dashboard"); contentPanel.add(new SiswaForm(),"Siswa"); contentPanel.add(new PemasukanForm(),"Pemasukan"); contentPanel.add(new PengeluaranForm(),"Pengeluaran"); contentPanel.add(new TabunganForm(),"Tabungan"); contentPanel.add(new LaporanForm(),"Laporan"); contentPanel.add(new PengaturanForm(),"Pengaturan"); root.add(contentPanel,BorderLayout.CENTER); setContentPane(root); }
    private JPanel sidebar(){ JPanel side=new JPanel(new GridLayout(0,1,0,8)); side.setPreferredSize(new Dimension(220,0)); side.setBackground(new Color(0x1E293B)); side.setBorder(BorderFactory.createEmptyBorder(15,15,15,15)); JLabel brand=new JLabel("SKM Madrasah"); brand.setForeground(Color.WHITE); brand.setFont(new Font("SansSerif",Font.BOLD,18)); side.add(brand); String[] menus={"Dashboard","Siswa","Pemasukan","Pengeluaran","Tabungan","Laporan","Pengaturan"}; for(String menu:menus){ JButton b=UIUtils.createModernButton(menu,UIUtils.PRIMARY); b.addActionListener(e->show(menu)); side.add(b);} JButton logout=UIUtils.createModernButton("Logout",UIUtils.DANGER); logout.addActionListener(e->{SessionManager.getInstance().clear(); new LoginFrame().setVisible(true); dispose();}); side.add(logout); return side; }
    private void show(String name){ Component c=contentPanel.getComponent(Arrays.asList("Dashboard","Siswa","Pemasukan","Pengeluaran","Tabungan","Laporan","Pengaturan").indexOf(name)); if(c instanceof Refreshable r) r.refreshData(); ((CardLayout)contentPanel.getLayout()).show(contentPanel,name); }
    public interface Refreshable{ void refreshData(); }
}
