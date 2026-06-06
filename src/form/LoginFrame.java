package form;


import controller.PengaturanController;import controller.UserController;import java.awt.*;import javax.swing.*;import model.Pengaturan;import model.User;import util.UIUtils;

public class LoginFrame extends JFrame{private final JTextField txtUser=UIUtils.textField(18);private final JPasswordField txtPass=new JPasswordField(18);private final UserController controller=new UserController();
 public LoginFrame(){setTitle("Login - Kas Sekolah");setSize(920,560);setLocationRelativeTo(null);setDefaultCloseOperation(EXIT_ON_CLOSE);setLayout(new GridLayout(1,2));setResizable(false);Pengaturan p=loadPengaturan();add(heroPanel(p));add(formPanel(p));}
 private Pengaturan loadPengaturan(){try{return new PengaturanController().get();}catch(Exception e){return new Pengaturan(0,"MI Nahdlotut Tholibin","","","","","","");}}
 private JPanel heroPanel(Pengaturan p){JPanel hero=new JPanel(new GridBagLayout());hero.setBackground(UIUtils.SIDEBAR);GridBagConstraints g=new GridBagConstraints();g.insets=new Insets(10,20,10,20);g.gridy=0;JLabel logo=new JLabel(UIUtils.schoolLogo(118));hero.add(logo,g);g.gridy++;JLabel title=new JLabel(p.getNamaSekolah());title.setFont(new Font("Segoe UI",Font.BOLD,26));title.setForeground(Color.WHITE);hero.add(title,g);g.gridy++;JLabel sub=new JLabel("Aplikasi Pengelolaan Kas Sekolah");sub.setFont(UIUtils.FONT_BOLD);sub.setForeground(new Color(0xDBEAFE));hero.add(sub,g);return hero;}
 private JPanel formPanel(Pengaturan p){JPanel panel=new JPanel(new GridBagLayout());panel.setBackground(UIUtils.BACKGROUND);panel.setBorder(BorderFactory.createEmptyBorder(40,55,40,55));GridBagConstraints g=new GridBagConstraints();g.insets=new Insets(8,0,8,0);g.fill=GridBagConstraints.HORIZONTAL;g.gridy=0;JLabel title=UIUtils.title("Masuk Aplikasi");panel.add(title,g);g.gridy++;JLabel info=new JLabel("Gunakan akun admin default: admin / admin123");info.setForeground(UIUtils.MUTED);panel.add(info,g);g.gridy++;panel.add(new JLabel("Username"),g);g.gridy++;panel.add(txtUser,g);g.gridy++;panel.add(new JLabel("Password"),g);g.gridy++;txtPass.setFont(UIUtils.FONT);txtPass.setBorder(txtUser.getBorder());panel.add(txtPass,g);g.gridy++;JPanel buttons=UIUtils.toolbar();JButton keluar=UIUtils.secondaryButton("Keluar");JButton login=UIUtils.primaryButton("Login");buttons.add(keluar);buttons.add(login);panel.add(buttons,g);login.addActionListener(e->login());keluar.addActionListener(e->System.exit(0));getRootPane().setDefaultButton(login);return panel;}
 private void login(){String username=txtUser.getText().trim();String password=new String(txtPass.getPassword());if(username.isEmpty()||password.isEmpty()){JOptionPane.showMessageDialog(this,"Username dan password wajib diisi.","Validasi",JOptionPane.WARNING_MESSAGE);return;}try{User user=controller.login(username,password);if(user==null){JOptionPane.showMessageDialog(this,"Login gagal. Username atau password salah.","Login",JOptionPane.ERROR_MESSAGE);return;}new DashboardFrame(user).setVisible(true);dispose();}catch(Exception ex){JOptionPane.showMessageDialog(this,"Gagal login: "+ex.getMessage(),"Database",JOptionPane.ERROR_MESSAGE);}}

import controller.UserController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.User;
import util.UIUtils;

public class LoginFrame extends JFrame {
  private final JTextField txtUser = UIUtils.textField(18);
  private final JPasswordField txtPass = new JPasswordField(18);
  private final UserController controller = new UserController();

  public LoginFrame() {
    setTitle("Login Kas Sekolah");
    setSize(480, 360);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(false);

    JPanel header = new JPanel(new BorderLayout());
    header.setBackground(UIUtils.NAVY);
    header.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
    JLabel title = new JLabel("KAS SEKOLAH");
    title.setFont(UIUtils.FONT_TITLE);
    title.setForeground(Color.WHITE);
    JLabel subtitle = new JLabel("MI Nahdlotut Tholibin");
    subtitle.setForeground(new Color(219, 234, 254));
    subtitle.setFont(UIUtils.FONT);
    header.add(title, BorderLayout.NORTH);
    header.add(subtitle, BorderLayout.SOUTH);

    JPanel form = new JPanel(new GridBagLayout());
    form.setBackground(UIUtils.WHITE);
    form.setBorder(BorderFactory.createEmptyBorder(22, 34, 26, 34));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    form.add(new JLabel("Username"), gbc);
    gbc.gridx = 1;
    form.add(txtUser, gbc);
    gbc.gridx = 0;
    gbc.gridy = 1;
    form.add(new JLabel("Password"), gbc);
    txtPass.setFont(UIUtils.FONT);
    txtPass.setBorder(txtUser.getBorder());
    gbc.gridx = 1;
    form.add(txtPass, gbc);
    JButton btnLogin = UIUtils.primaryButton("Login");
    JButton btnKeluar = UIUtils.secondaryButton("Keluar");
    gbc.gridy = 2;
    gbc.gridx = 0;
    form.add(btnKeluar, gbc);
    gbc.gridx = 1;
    form.add(btnLogin, gbc);

    add(header, BorderLayout.NORTH);
    add(form, BorderLayout.CENTER);
    getRootPane().setDefaultButton(btnLogin);

    btnLogin.addActionListener(e -> login());
    btnKeluar.addActionListener(e -> System.exit(0));
  }

  private void login() {
    String username = txtUser.getText().trim();
    String password = new String(txtPass.getPassword());
    if (username.isEmpty() || password.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Username dan password wajib diisi",
                                    "Validasi", JOptionPane.WARNING_MESSAGE);
      return;
    }
    try {
      User user = controller.login(username, password);
      if (user == null) {
        JOptionPane.showMessageDialog(
            this, "Login gagal. Username atau password salah.", "Login",
            JOptionPane.ERROR_MESSAGE);
        return;
      }
      new DashboardFrame(user).setVisible(true);
      dispose();
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, "Gagal login: " + ex.getMessage(),
                                    "Database", JOptionPane.ERROR_MESSAGE);
    }
  }

}
