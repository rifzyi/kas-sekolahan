package form;


import controller.UserController;import java.awt.*;import javax.swing.*;import javax.swing.table.DefaultTableModel;import model.User;import util.*;

public class UserFrame extends JPanel{private final UserController c=new UserController();private final JTextField nama=UIUtils.textField(18),username=UIUtils.textField(18),cari=UIUtils.textField(18);private final JPasswordField password=new JPasswordField(18);private final JComboBox<String> role=new JComboBox<>(new String[]{"Admin","Bendahara"});private final DefaultTableModel m=new DefaultTableModel(new Object[]{"ID","Nama","Username","Password","Role"},0){public boolean isCellEditable(int r,int c){return false;}};private final JTable table=new JTable(m);private int id=0;
 public UserFrame(){setLayout(new BorderLayout());JPanel p=UIUtils.page("Data User");password.setBorder(nama.getBorder());p.add(form(),BorderLayout.WEST);p.add(tabel(),BorderLayout.CENTER);add(p);load("");}
 private JPanel form(){JPanel p=UIUtils.card();p.setLayout(new GridBagLayout());GridBagConstraints g=new GridBagConstraints();g.insets=new Insets(6,6,6,6);g.fill=GridBagConstraints.HORIZONTAL;row(p,g,0,"Nama",nama);row(p,g,1,"Username",username);row(p,g,2,"Password",password);row(p,g,3,"Role",role);JPanel b=UIUtils.toolbar();JButton tambah=UIUtils.secondaryButton("Tambah"),simpan=UIUtils.primaryButton("Simpan"),edit=UIUtils.secondaryButton("Edit"),hapus=UIUtils.dangerButton("Hapus");b.add(tambah);b.add(simpan);b.add(edit);b.add(hapus);g.gridx=0;g.gridy=4;g.gridwidth=2;p.add(b,g);tambah.addActionListener(e->clear());simpan.addActionListener(e->save());edit.addActionListener(e->update());hapus.addActionListener(e->delete());return p;}
 private JPanel tabel(){JPanel p=UIUtils.card();p.setLayout(new BorderLayout(8,8));JPanel t=UIUtils.toolbar();JButton refresh=UIUtils.secondaryButton("Refresh");t.add(new JLabel("Cari"));t.add(cari);t.add(refresh);p.add(t,BorderLayout.NORTH);p.add(UIUtils.tableScroll(table),BorderLayout.CENTER);UIUtils.bindRealtimeSearch(cari,m,table);refresh.addActionListener(e->{cari.setText("");load("");});table.getSelectionModel().addListSelectionListener(e->select());return p;}
 private void row(JPanel p,GridBagConstraints g,int y,String l,Component comp){g.gridx=0;g.gridy=y;g.gridwidth=1;p.add(new JLabel(l),g);g.gridx=1;p.add(comp,g);}private User val(){return new User(id,nama.getText().trim(),username.getText().trim(),new String(password.getPassword()),role.getSelectedItem().toString());}private boolean valid(){return Validator.required("Nama",nama.getText())&&Validator.required("Username",username.getText())&&Validator.required("Password",new String(password.getPassword()));}
 private void save(){if(!valid())return;try{c.insert(val());clear();load("");}catch(Exception e){msg(e);}}private void update(){if(id==0||!valid())return;try{c.update(val());clear();load("");}catch(Exception e){msg(e);}}private void delete(){if(id==0)return;if(JOptionPane.showConfirmDialog(this,"Hapus user?","Konfirmasi",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)try{c.delete(id);clear();load("");}catch(Exception e){msg(e);}}
 private void load(String k){try{m.setRowCount(0);for(User u:c.getAll(k))m.addRow(new Object[]{u.getIdUser(),u.getNama(),u.getUsername(),u.getPassword(),u.getRole()});}catch(Exception e){msg(e);}}private void select(){int r=table.getSelectedRow();if(r>=0){r=table.convertRowIndexToModel(r);id=(int)m.getValueAt(r,0);nama.setText(m.getValueAt(r,1).toString());username.setText(m.getValueAt(r,2).toString());password.setText(m.getValueAt(r,3).toString());role.setSelectedItem(m.getValueAt(r,4).toString());}}private void clear(){id=0;nama.setText("");username.setText("");password.setText("");role.setSelectedIndex(0);table.clearSelection();}private void msg(Exception e){JOptionPane.showMessageDialog(this,"Terjadi kesalahan: "+e.getMessage());}}
=======
import controller.UserController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.User;
import util.UIUtils;

public class UserFrame extends JPanel {
  private final UserController controller = new UserController();
  private final JTextField txtNama = UIUtils.textField(18),
                           txtUsername = UIUtils.textField(18),
                           txtCari = UIUtils.textField(18);
  private final JPasswordField txtPassword = new JPasswordField(18);
  private final JComboBox<String> cmbRole =
      new JComboBox<>(new String[] {"Admin", "Bendahara"});
  private final DefaultTableModel model = new DefaultTableModel(
      new Object[] {"ID", "Nama", "Username", "Password", "Role"}, 0) {
    public boolean isCellEditable(int r, int c) { return false; }
  };
  private final JTable table = new JTable(model);
  private int selectedId = 0;
  public UserFrame() {
    setLayout(new BorderLayout(12, 12));
    setBackground(UIUtils.LIGHT_BLUE);
    setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
    add(UIUtils.title("Data User"), BorderLayout.NORTH);
    txtPassword.setBorder(txtNama.getBorder());
    add(formPanel(), BorderLayout.WEST);
    add(tablePanel(), BorderLayout.CENTER);
    loadData("");
  }
  private JPanel formPanel() {
    JPanel p = UIUtils.card();
    p.setLayout(new GridBagLayout());
    GridBagConstraints g = new GridBagConstraints();
    g.insets = new Insets(6, 6, 6, 6);
    g.fill = GridBagConstraints.HORIZONTAL;
    addRow(p, g, 0, "Nama", txtNama);
    addRow(p, g, 1, "Username", txtUsername);
    addRow(p, g, 2, "Password", txtPassword);
    addRow(p, g, 3, "Role", cmbRole);
    JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));
    b.setOpaque(false);
    var tambah = UIUtils.secondaryButton("Tambah");
    var simpan = UIUtils.primaryButton("Simpan");
    var edit = UIUtils.secondaryButton("Edit");
    var hapus = UIUtils.secondaryButton("Hapus");
    b.add(tambah);
    b.add(simpan);
    b.add(edit);
    b.add(hapus);
    g.gridx = 0;
    g.gridy = 4;
    g.gridwidth = 2;
    p.add(b, g);
    tambah.addActionListener(e -> clear());
    simpan.addActionListener(e -> save());
    edit.addActionListener(e -> update());
    hapus.addActionListener(e -> delete());
    return p;
  }
  private void addRow(JPanel p, GridBagConstraints g, int y, String l,
                      java.awt.Component c) {
    g.gridx = 0;
    g.gridy = y;
    g.gridwidth = 1;
    p.add(new JLabel(l), g);
    g.gridx = 1;
    p.add(c, g);
  }
  private JPanel tablePanel() {
    JPanel p = UIUtils.card();
    p.setLayout(new BorderLayout(8, 8));
    JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
    top.setOpaque(false);
    var cari = UIUtils.primaryButton("Cari");
    var refresh = UIUtils.secondaryButton("Refresh");
    top.add(new JLabel("Cari"));
    top.add(txtCari);
    top.add(cari);
    top.add(refresh);
    p.add(top, BorderLayout.NORTH);
    p.add(UIUtils.tableScroll(table), BorderLayout.CENTER);
    cari.addActionListener(e -> loadData(txtCari.getText()));
    refresh.addActionListener(e -> {
      txtCari.setText("");
      loadData("");
    });
    table.getSelectionModel().addListSelectionListener(e -> selectRow());
    return p;
  }
  private User form() {
    return new User(selectedId, txtNama.getText().trim(),
                    txtUsername.getText().trim(),
                    new String(txtPassword.getPassword()),
                    cmbRole.getSelectedItem().toString());
  }
  private boolean valid(User u) {
    if (u.getNama().isEmpty() || u.getUsername().isEmpty() ||
        u.getPassword().isEmpty()) {
      JOptionPane.showMessageDialog(this,
                                    "Nama, username, dan password wajib diisi");
      return false;
    }
    return true;
  }
  private void save() {
    User u = form();
    if (!valid(u))
      return;
    try {
      controller.insert(u);
      clear();
      loadData("");
      JOptionPane.showMessageDialog(this, "User berhasil disimpan");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Gagal simpan: " + e.getMessage());
    }
  }
  private void update() {
    User u = form();
    if (selectedId == 0 || !valid(u))
      return;
    try {
      controller.update(u);
      clear();
      loadData("");
      JOptionPane.showMessageDialog(this, "User berhasil diubah");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Gagal edit: " + e.getMessage());
    }
  }
  private void delete() {
    if (selectedId == 0)
      return;
    if (JOptionPane.showConfirmDialog(
            this, "Hapus user terpilih?", "Konfirmasi",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
      try {
        controller.delete(selectedId);
        clear();
        loadData("");
      } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal hapus: " + e.getMessage());
      }
  }
  private void loadData(String k) {
    try {
      model.setRowCount(0);
      for (User u : controller.getAll(k))
        model.addRow(new Object[] {u.getIdUser(), u.getNama(), u.getUsername(),
                                   u.getPassword(), u.getRole()});
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Gagal load: " + e.getMessage());
    }
  }
  private void selectRow() {
    int r = table.getSelectedRow();
    if (r >= 0) {
      selectedId = (int)model.getValueAt(r, 0);
      txtNama.setText(model.getValueAt(r, 1).toString());
      txtUsername.setText(model.getValueAt(r, 2).toString());
      txtPassword.setText(model.getValueAt(r, 3).toString());
      cmbRole.setSelectedItem(model.getValueAt(r, 4).toString());
    }
  }
  private void clear() {
    selectedId = 0;
    txtNama.setText("");
    txtUsername.setText("");
    txtPassword.setText("");
    cmbRole.setSelectedIndex(0);
    table.clearSelection();
  }
}

