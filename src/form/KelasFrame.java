package form;

import controller.KelasController;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Kelas;
import util.UIUtils;

public class KelasFrame extends JPanel {
  private final KelasController controller = new KelasController();
  private final DefaultTableModel model = new DefaultTableModel(new Object[] {"ID", "Kode Kelas", "Nama Kelas"}, 0) { public boolean isCellEditable(int r, int c) { return false; } };
  private final JTable table = new JTable(model);

  public KelasFrame() { setLayout(new BorderLayout()); add(build(), BorderLayout.CENTER); loadData(); }
  private JPanel build() {
    JPanel page = UIUtils.page("Data Kelas");
    JTextField search = UIUtils.textField(18);
    JButton add = UIUtils.button("+ Tambah Data", UIUtils.GREEN), edit = UIUtils.button("Edit", UIUtils.ORANGE), del = UIUtils.button("Hapus", UIUtils.RED), ref = UIUtils.button("Refresh", UIUtils.BLUE);
    add.addActionListener(e -> showDialog(null)); edit.addActionListener(e -> edit()); del.addActionListener(e -> delete()); ref.addActionListener(e -> loadData());
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model); table.setRowSorter(sorter); UIUtils.bindSearch(search, sorter);
    JPanel center = new JPanel(new BorderLayout(0, 14)); center.setOpaque(false); center.add(UIUtils.toolbar(search, add, edit, del, ref), BorderLayout.NORTH); center.add(UIUtils.tableScroll(table), BorderLayout.CENTER);
    page.add(center, BorderLayout.CENTER); return page;
  }
  private void loadData() { try { model.setRowCount(0); for (Kelas k : controller.getAll()) model.addRow(new Object[] {k.getIdKelas(), k.getKodeKelas(), k.getNamaKelas()}); } catch (Exception e) { error(e); } }
  private void edit() { int r = selected(); if (r < 0) return; showDialog(new Kelas((int) model.getValueAt(r, 0), String.valueOf(model.getValueAt(r, 1)), String.valueOf(model.getValueAt(r, 2)))); }
  private void delete() { int r = selected(); if (r < 0) return; if (JOptionPane.showConfirmDialog(this, "Hapus data kelas?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) try { controller.delete((int) model.getValueAt(r, 0)); loadData(); } catch (Exception e) { error(e); } }
  private int selected() { int r = table.getSelectedRow(); if (r < 0) JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu"); return r < 0 ? -1 : table.convertRowIndexToModel(r); }
  private void showDialog(Kelas data) {
    JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), data == null ? "Tambah Kelas" : "Edit Kelas", Dialog.ModalityType.APPLICATION_MODAL);
    JPanel panel = UIUtils.card(); panel.setLayout(new GridBagLayout()); GridBagConstraints g = new GridBagConstraints(); g.insets = new Insets(8,8,8,8); g.fill = GridBagConstraints.HORIZONTAL;
    JTextField kode = UIUtils.textField(20), nama = UIUtils.textField(20); if (data != null) { kode.setText(data.getKodeKelas()); nama.setText(data.getNamaKelas()); }
    addRow(panel,g,0,"Kode Kelas",kode); addRow(panel,g,1,"Nama Kelas",nama); JButton save = UIUtils.button("Simpan", UIUtils.GREEN); g.gridx=0; g.gridy=2; g.gridwidth=2; panel.add(save,g);
    save.addActionListener(e -> { try { Kelas k = new Kelas(data == null ? 0 : data.getIdKelas(), kode.getText().trim(), nama.getText().trim()); if (data == null) controller.insert(k); else controller.update(k); dialog.dispose(); loadData(); } catch (Exception ex) { error(ex); } });
    dialog.add(panel); dialog.pack(); dialog.setLocationRelativeTo(this); dialog.setVisible(true);
  }
  private void addRow(JPanel p, GridBagConstraints g, int y, String l, JComponent c) { g.gridy=y; g.gridx=0; g.gridwidth=1; p.add(UIUtils.formLabel(l),g); g.gridx=1; p.add(c,g); }
  private void error(Exception e) { JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
}
