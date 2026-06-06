package form;

import controller.PemasukanController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Pemasukan;
import util.UIUtils;

public class PemasukanFrame extends JPanel {
  private final PemasukanController controller = new PemasukanController();
  private final JTextField txtTanggal = UIUtils.textField(12),
                           txtSumber = UIUtils.textField(18),
                           txtNominal = UIUtils.textField(18),
                           txtCari = UIUtils.textField(18);
  private final JTextArea txtKet = new JTextArea(3, 18);
  private final JLabel lblTotal = new JLabel();
  private final DefaultTableModel model = new DefaultTableModel(
      new Object[] {"ID", "Tanggal", "Sumber Dana", "Nominal", "Keterangan"},
      0) {
    public boolean isCellEditable(int r, int c) { return false; }
  };
  private final JTable table = new JTable(model);
  private int selectedId = 0;
  public PemasukanFrame() {
    setLayout(new BorderLayout(12, 12));
    setBackground(UIUtils.LIGHT_BLUE);
    setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
    add(UIUtils.title("Pemasukan"), BorderLayout.NORTH);
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
    addRow(p, g, 1, "Sumber Dana", txtSumber);
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
  private Pemasukan form() {
    LocalDate t = UIUtils.parseDate(txtTanggal.getText());
    double n = UIUtils.parseDouble(txtNominal.getText());
    return new Pemasukan(selectedId, t, txtSumber.getText().trim(), n,
                         txtKet.getText().trim());
  }
  private boolean valid(Pemasukan p) {
    if (p.getTanggal() == null || p.getNominal() < 0 ||
        p.getSumberDana().isEmpty()) {
      JOptionPane.showMessageDialog(
          this, "Tanggal, sumber dana, dan nominal wajib valid");
      return false;
    }
    return true;
  }
  private void save() {
    Pemasukan p = form();
    if (!valid(p))
      return;
    try {
      controller.insert(p);
      clear();
      loadData("");
      JOptionPane.showMessageDialog(this, "Pemasukan berhasil disimpan");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Gagal simpan: " + e.getMessage());
    }
  }
  private void update() {
    Pemasukan p = form();
    if (selectedId == 0 || !valid(p))
      return;
    try {
      controller.update(p);
      clear();
      loadData("");
      JOptionPane.showMessageDialog(this, "Pemasukan berhasil diubah");
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
      for (Pemasukan p : controller.getAll(k))
        model.addRow(
            new Object[] {p.getIdPemasukan(), p.getTanggal(), p.getSumberDana(),
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
      txtSumber.setText(model.getValueAt(r, 2).toString());
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
    txtSumber.setText("");
    txtNominal.setText("");
    txtKet.setText("");
    table.clearSelection();
  }
}
