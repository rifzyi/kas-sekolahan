package form;


import controller.PengeluaranController;import java.awt.*;import java.time.LocalDate;import javax.swing.*;import javax.swing.table.DefaultTableModel;import model.Pengeluaran;import util.*;import util.UIUtils.Option;

public class PengeluaranFrame extends JPanel{private final PengeluaranController c=new PengeluaranController();private final JTextField tanggal=UIUtils.textField(12),nominal=UIUtils.textField(14),cari=UIUtils.textField(18);private final JComboBox<Option> kategori=new JComboBox<>();private final JTextArea ket=new JTextArea(3,18);private final JLabel total=new JLabel();private final DefaultTableModel m=new DefaultTableModel(new Object[]{"ID","Tanggal","ID Kategori","Kategori","Nominal","Keterangan"},0){public boolean isCellEditable(int r,int c){return false;}};private final JTable table=new JTable(m);private int id=0;
 public PengeluaranFrame(){setLayout(new BorderLayout());JPanel p=UIUtils.page("Pengeluaran Kas");tanggal.setText(LocalDate.now().toString());p.add(form(),BorderLayout.WEST);p.add(tabel(),BorderLayout.CENTER);add(p);loadKategori();load("");}
 private JPanel form(){JPanel p=UIUtils.card();p.setLayout(new GridBagLayout());GridBagConstraints g=new GridBagConstraints();g.insets=new Insets(6,6,6,6);g.fill=GridBagConstraints.HORIZONTAL;row(p,g,0,"Tanggal",tanggal);row(p,g,1,"Kategori",kategori);row(p,g,2,"Nominal",nominal);row(p,g,3,"Keterangan",new JScrollPane(ket));JPanel b=UIUtils.toolbar();JButton tambah=UIUtils.secondaryButton("Tambah"),simpan=UIUtils.primaryButton("Simpan"),edit=UIUtils.secondaryButton("Edit"),hapus=UIUtils.dangerButton("Hapus");b.add(tambah);b.add(simpan);b.add(edit);b.add(hapus);g.gridx=0;g.gridy=4;g.gridwidth=2;p.add(b,g);tambah.addActionListener(e->clear());simpan.addActionListener(e->save());edit.addActionListener(e->update());hapus.addActionListener(e->delete());return p;}
 private JPanel tabel(){JPanel p=UIUtils.card();p.setLayout(new BorderLayout(8,8));JPanel t=UIUtils.toolbar();JButton refresh=UIUtils.secondaryButton("Refresh");total.setFont(UIUtils.FONT_BOLD);t.add(new JLabel("Cari"));t.add(cari);t.add(refresh);t.add(total);p.add(t,BorderLayout.NORTH);p.add(UIUtils.tableScroll(table),BorderLayout.CENTER);UIUtils.bindRealtimeSearch(cari,m,table);refresh.addActionListener(e->{loadKategori();load("");cari.setText("");});table.getSelectionModel().addListSelectionListener(e->select());return p;}
 private void row(JPanel p,GridBagConstraints g,int y,String l,Component comp){g.gridx=0;g.gridy=y;g.gridwidth=1;p.add(new JLabel(l),g);g.gridx=1;p.add(comp,g);}private void loadKategori(){try{kategori.removeAllItems();for(Option o:c.kategoriOptions())kategori.addItem(o);}catch(Exception e){msg(e);}}private Pengeluaran val(){Option o=(Option)kategori.getSelectedItem();return new Pengeluaran(id,Validator.date("Tanggal",tanggal.getText()),o==null?0:o.getId(),"",Validator.money("Nominal",nominal.getText()),ket.getText().trim());}private boolean valid(Pengeluaran p){return p.getTanggal()!=null&&p.getNominal()>=0&&kategori.getSelectedItem()!=null;}
 private void save(){Pengeluaran p=val();if(!valid(p))return;try{c.insert(p);clear();load("");}catch(Exception e){msg(e);}}private void update(){Pengeluaran p=val();if(id==0||!valid(p))return;try{c.update(p);clear();load("");}catch(Exception e){msg(e);}}private void delete(){if(id==0)return;if(JOptionPane.showConfirmDialog(this,"Hapus pengeluaran?","Konfirmasi",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)try{c.delete(id);clear();load("");}catch(Exception e){msg(e);}}
 private void load(String k){try{m.setRowCount(0);for(Pengeluaran p:c.getAll(k))m.addRow(new Object[]{p.getIdPengeluaran(),p.getTanggal(),p.getIdKategoriPengeluaran(),p.getKategori(),UIUtils.rupiah(p.getNominal()),p.getKeterangan()});total.setText("Total: "+UIUtils.rupiah(c.total()));}catch(Exception e){msg(e);}}private void select(){int r=table.getSelectedRow();if(r>=0){r=table.convertRowIndexToModel(r);id=(int)m.getValueAt(r,0);tanggal.setText(m.getValueAt(r,1).toString());int kid=(int)m.getValueAt(r,2);for(int i=0;i<kategori.getItemCount();i++)if(kategori.getItemAt(i).getId()==kid)kategori.setSelectedIndex(i);nominal.setText(m.getValueAt(r,4).toString().replace("Rp","").replace(" ","").replace(".","").replace(",00","").trim());ket.setText(String.valueOf(m.getValueAt(r,5)));}}private void clear(){id=0;tanggal.setText(LocalDate.now().toString());if(kategori.getItemCount()>0)kategori.setSelectedIndex(0);nominal.setText("");ket.setText("");table.clearSelection();}private void msg(Exception e){JOptionPane.showMessageDialog(this,"Terjadi kesalahan: "+e.getMessage());}}

import controller.PengeluaranController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Pengeluaran;
import util.UIUtils;

public class PengeluaranFrame extends JPanel {
  private final PengeluaranController controller = new PengeluaranController();
  private final JTextField txtTanggal = UIUtils.textField(12),
                           txtKeperluan = UIUtils.textField(18),
                           txtNominal = UIUtils.textField(18),
                           txtCari = UIUtils.textField(18);
  private final JTextArea txtKet = new JTextArea(3, 18);
  private final JLabel lblTotal = new JLabel();
  private final DefaultTableModel model = new DefaultTableModel(
      new Object[] {"ID", "Tanggal", "Keperluan", "Nominal", "Keterangan"}, 0) {
    public boolean isCellEditable(int r, int c) { return false; }
  };
  private final JTable table = new JTable(model);
  private int selectedId = 0;
  public PengeluaranFrame() {
    setLayout(new BorderLayout(12, 12));
    setBackground(UIUtils.LIGHT_BLUE);
    setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
    add(UIUtils.title("Pengeluaran"), BorderLayout.NORTH);
    txtTanggal.setText(LocalDate.now().toString());
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
    addRow(p, g, 0, "Tanggal", txtTanggal);
    addRow(p, g, 1, "Keperluan", txtKeperluan);
    addRow(p, g, 2, "Nominal", txtNominal);
    addRow(p, g, 3, "Keterangan", new JScrollPane(txtKet));
    JPanel btn = new JPanel(new FlowLayout(FlowLayout.LEFT));
    btn.setOpaque(false);
    var tambah = UIUtils.secondaryButton("Tambah");
    var simpan = UIUtils.primaryButton("Simpan");
    var edit = UIUtils.secondaryButton("Edit");
    var hapus = UIUtils.secondaryButton("Hapus");
    btn.add(tambah);
    btn.add(simpan);
    btn.add(edit);
    btn.add(hapus);
    g.gridx = 0;
    g.gridy = 4;
    g.gridwidth = 2;
    p.add(btn, g);
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
    lblTotal.setFont(UIUtils.FONT_BOLD);
    top.add(new JLabel("Cari"));
    top.add(txtCari);
    top.add(cari);
    top.add(refresh);
    top.add(lblTotal);
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
  private Pengeluaran form() {
    LocalDate t = UIUtils.parseDate(txtTanggal.getText());
    double n = UIUtils.parseDouble(txtNominal.getText());
    return new Pengeluaran(selectedId, t, txtKeperluan.getText().trim(), n,
                           txtKet.getText().trim());
  }
  private boolean valid(Pengeluaran p) {
    if (p.getTanggal() == null || p.getNominal() < 0 ||
        p.getKeperluan().isEmpty()) {
      JOptionPane.showMessageDialog(
          this, "Tanggal, keperluan, dan nominal wajib valid");
      return false;
    }
    return true;
  }
  private void save() {
    Pengeluaran p = form();
    if (!valid(p))
      return;
    try {
      controller.insert(p);
      clear();
      loadData("");
      JOptionPane.showMessageDialog(this, "Pengeluaran berhasil disimpan");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Gagal simpan: " + e.getMessage());
    }
  }
  private void update() {
    Pengeluaran p = form();
    if (selectedId == 0 || !valid(p))
      return;
    try {
      controller.update(p);
      clear();
      loadData("");
      JOptionPane.showMessageDialog(this, "Pengeluaran berhasil diubah");
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
      for (Pengeluaran p : controller.getAll(k))
        model.addRow(new Object[] {
            p.getIdPengeluaran(), p.getTanggal(), p.getKeperluan(),
            UIUtils.rupiah(p.getNominal()), p.getKeterangan()});
      lblTotal.setText("Total: " + UIUtils.rupiah(controller.total()));
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Gagal load: " + e.getMessage());
    }
  }
  private void selectRow() {
    int r = table.getSelectedRow();
    if (r >= 0) {
      selectedId = (int)model.getValueAt(r, 0);
      txtTanggal.setText(model.getValueAt(r, 1).toString());
      txtKeperluan.setText(model.getValueAt(r, 2).toString());
      txtNominal.setText(model.getValueAt(r, 3)
                             .toString()
                             .replace("Rp", "")
                             .replace(" ", "")
                             .replace(".", "")
                             .replace(",00", "")
                             .trim());
      txtKet.setText(String.valueOf(model.getValueAt(r, 4)));
    }
  }
  private void clear() {
    selectedId = 0;
    txtTanggal.setText(LocalDate.now().toString());
    txtKeperluan.setText("");
    txtNominal.setText("");
    txtKet.setText("");
    table.clearSelection();
  }
}

