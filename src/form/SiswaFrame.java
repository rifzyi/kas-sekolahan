package form;


import controller.*;import java.awt.*;import javax.swing.*;import javax.swing.table.DefaultTableModel;import model.Siswa;import util.*;import util.UIUtils.Option;

public class SiswaFrame extends JPanel{private final SiswaController c=new SiswaController();private final KelasController kc=new KelasController();private final JTextField nis=UIUtils.textField(14),nama=UIUtils.textField(18),cari=UIUtils.textField(18);private final JComboBox<Option> kelas=new JComboBox<>();private final JComboBox<String> jk=new JComboBox<>(new String[]{"Laki-laki","Perempuan"});private final JTextArea alamat=new JTextArea(3,18);private final DefaultTableModel m=new DefaultTableModel(new Object[]{"ID","NIS","Nama Siswa","ID Kelas","Kelas","Jenis Kelamin","Alamat"},0){public boolean isCellEditable(int r,int c){return false;}};private final JTable table=new JTable(m);private int id=0;
 public SiswaFrame(){setLayout(new BorderLayout());JPanel p=UIUtils.page("Data Siswa");p.add(form(),BorderLayout.WEST);p.add(tabel(),BorderLayout.CENTER);add(p);loadKelas();load("");}
 private JPanel form(){JPanel p=UIUtils.card();p.setLayout(new GridBagLayout());GridBagConstraints g=new GridBagConstraints();g.insets=new Insets(6,6,6,6);g.fill=GridBagConstraints.HORIZONTAL;row(p,g,0,"NIS",nis);row(p,g,1,"Nama Siswa",nama);row(p,g,2,"Kelas",kelas);row(p,g,3,"Jenis Kelamin",jk);row(p,g,4,"Alamat",new JScrollPane(alamat));JPanel b=UIUtils.toolbar();JButton tambah=UIUtils.secondaryButton("Tambah"),simpan=UIUtils.primaryButton("Simpan"),edit=UIUtils.secondaryButton("Edit"),hapus=UIUtils.dangerButton("Hapus");b.add(tambah);b.add(simpan);b.add(edit);b.add(hapus);g.gridx=0;g.gridy=5;g.gridwidth=2;p.add(b,g);tambah.addActionListener(e->clear());simpan.addActionListener(e->save());edit.addActionListener(e->update());hapus.addActionListener(e->delete());return p;}
 private JPanel tabel(){JPanel p=UIUtils.card();p.setLayout(new BorderLayout(8,8));JPanel t=UIUtils.toolbar();JButton refresh=UIUtils.secondaryButton("Refresh");t.add(new JLabel("Cari"));t.add(cari);t.add(refresh);p.add(t,BorderLayout.NORTH);p.add(UIUtils.tableScroll(table),BorderLayout.CENTER);UIUtils.bindRealtimeSearch(cari,m,table);refresh.addActionListener(e->{loadKelas();load("");cari.setText("");});table.getSelectionModel().addListSelectionListener(e->select());return p;}
 private void row(JPanel p,GridBagConstraints g,int y,String l,Component comp){g.gridx=0;g.gridy=y;g.gridwidth=1;p.add(new JLabel(l),g);g.gridx=1;p.add(comp,g);}private void loadKelas(){try{kelas.removeAllItems();for(Option o:kc.options())kelas.addItem(o);}catch(Exception e){msg(e);}}private Siswa val(){Option o=(Option)kelas.getSelectedItem();return new Siswa(id,nis.getText().trim(),nama.getText().trim(),o==null?0:o.getId(),"","",jk.getSelectedItem().toString(),alamat.getText().trim());}private boolean valid(){return Validator.required("NIS",nis.getText())&&Validator.required("Nama siswa",nama.getText())&&kelas.getSelectedItem()!=null;}
 private void save(){if(!valid())return;try{c.insert(val());clear();load("");JOptionPane.showMessageDialog(this,"Data siswa tersimpan.");}catch(Exception e){msg(e);}}private void update(){if(id==0||!valid())return;try{c.update(val());clear();load("");JOptionPane.showMessageDialog(this,"Data siswa diperbarui.");}catch(Exception e){msg(e);}}private void delete(){if(id==0)return;if(JOptionPane.showConfirmDialog(this,"Hapus siswa terpilih?","Konfirmasi",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)try{c.delete(id);clear();load("");}catch(Exception e){msg(e);}}
 private void load(String k){try{m.setRowCount(0);for(Siswa s:c.getAll(k))m.addRow(new Object[]{s.getIdSiswa(),s.getNis(),s.getNamaSiswa(),s.getIdKelas(),s.getKodeKelas()+" - "+s.getNamaKelas(),s.getJenisKelamin(),s.getAlamat()});}catch(Exception e){msg(e);}}private void select(){int r=table.getSelectedRow();if(r>=0){r=table.convertRowIndexToModel(r);id=(int)m.getValueAt(r,0);nis.setText(m.getValueAt(r,1).toString());nama.setText(m.getValueAt(r,2).toString());int idK=(int)m.getValueAt(r,3);for(int i=0;i<kelas.getItemCount();i++)if(kelas.getItemAt(i).getId()==idK)kelas.setSelectedIndex(i);jk.setSelectedItem(m.getValueAt(r,5).toString());alamat.setText(String.valueOf(m.getValueAt(r,6)));}}private void clear(){id=0;nis.setText("");nama.setText("");if(kelas.getItemCount()>0)kelas.setSelectedIndex(0);jk.setSelectedIndex(0);alamat.setText("");table.clearSelection();}private void msg(Exception e){JOptionPane.showMessageDialog(this,"Terjadi kesalahan: "+e.getMessage());}}

import controller.SiswaController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Siswa;
import util.UIUtils;

public class SiswaFrame extends JPanel {
  private final SiswaController controller = new SiswaController();
  private final JTextField txtNis = UIUtils.textField(18),
                           txtNama = UIUtils.textField(18),
                           txtKelas = UIUtils.textField(18),
                           txtCari = UIUtils.textField(18);
  private final JComboBox<String> cmbJenis =
      new JComboBox<>(new String[] {"Laki-laki", "Perempuan"});
  private final JTextArea txtAlamat = new JTextArea(3, 18);
  private final DefaultTableModel model =
      new DefaultTableModel(new Object[] {"ID", "NIS", "Nama Siswa", "Kelas",
                                          "Jenis Kelamin", "Alamat"},
                            0) {
        public boolean isCellEditable(int r, int c) { return false; }
      };
  private final JTable table = new JTable(model);
  private int selectedId = 0;

  public SiswaFrame() {
    setLayout(new BorderLayout(12, 12));
    setBackground(UIUtils.LIGHT_BLUE);
    setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 18, 18, 18));
    add(UIUtils.title("Data Siswa"), BorderLayout.NORTH);
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
    addRow(p, g, 0, "NIS", txtNis);
    addRow(p, g, 1, "Nama Siswa", txtNama);
    addRow(p, g, 2, "Kelas", txtKelas);
    addRow(p, g, 3, "Jenis Kelamin", cmbJenis);
    addRow(p, g, 4, "Alamat", new javax.swing.JScrollPane(txtAlamat));
    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
    buttons.setOpaque(false);
    var bTambah = UIUtils.secondaryButton("Tambah");
    var bSimpan = UIUtils.primaryButton("Simpan");
    var bEdit = UIUtils.secondaryButton("Edit");
    var bHapus = UIUtils.secondaryButton("Hapus");
    buttons.add(bTambah);
    buttons.add(bSimpan);
    buttons.add(bEdit);
    buttons.add(bHapus);
    g.gridx = 0;
    g.gridy = 5;
    g.gridwidth = 2;
    p.add(buttons, g);
    bTambah.addActionListener(e -> clear());
    bSimpan.addActionListener(e -> save());
    bEdit.addActionListener(e -> update());
    bHapus.addActionListener(e -> delete());
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
  private Siswa form() {
    return new Siswa(selectedId, txtNis.getText().trim(),
                     txtNama.getText().trim(), txtKelas.getText().trim(),
                     cmbJenis.getSelectedItem().toString(),
                     txtAlamat.getText().trim());
  }
  private boolean valid() {
    if (txtNis.getText().trim().isEmpty() ||
        txtNama.getText().trim().isEmpty() ||
        txtKelas.getText().trim().isEmpty()) {
      JOptionPane.showMessageDialog(this, "NIS, nama, dan kelas wajib diisi");
      return false;
    }
    return true;
  }
  private void save() {
    if (!valid())
      return;
    try {
      controller.insert(form());
      clear();
      loadData("");
      JOptionPane.showMessageDialog(this, "Data siswa berhasil disimpan");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Gagal simpan: " + e.getMessage());
    }
  }
  private void update() {
    if (selectedId == 0 || !valid())
      return;
    try {
      controller.update(form());
      clear();
      loadData("");
      JOptionPane.showMessageDialog(this, "Data siswa berhasil diubah");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Gagal edit: " + e.getMessage());
    }
  }
  private void delete() {
    if (selectedId == 0)
      return;
    if (JOptionPane.showConfirmDialog(
            this, "Hapus data terpilih?", "Konfirmasi",
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
      List<Siswa> data = controller.getAll(k);
      for (Siswa s : data)
        model.addRow(new Object[] {s.getIdSiswa(), s.getNis(), s.getNamaSiswa(),
                                   s.getKelas(), s.getJenisKelamin(),
                                   s.getAlamat()});
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Gagal load: " + e.getMessage());
    }
  }
  private void selectRow() {
    int r = table.getSelectedRow();
    if (r >= 0) {
      selectedId = (int)model.getValueAt(r, 0);
      txtNis.setText(model.getValueAt(r, 1).toString());
      txtNama.setText(model.getValueAt(r, 2).toString());
      txtKelas.setText(model.getValueAt(r, 3).toString());
      cmbJenis.setSelectedItem(model.getValueAt(r, 4).toString());
      txtAlamat.setText(String.valueOf(model.getValueAt(r, 5)));
    }
  }
  private void clear() {
    selectedId = 0;
    txtNis.setText("");
    txtNama.setText("");
    txtKelas.setText("");
    cmbJenis.setSelectedIndex(0);
    txtAlamat.setText("");
    table.clearSelection();
  }
}

