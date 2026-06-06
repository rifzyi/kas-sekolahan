package form;

import controller.PemasukanController;
import controller.PengeluaranController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Pemasukan;
import model.Pengeluaran;
import util.UIUtils;

public class LaporanFrame extends JPanel {
  private final PemasukanController pemasukanController =
      new PemasukanController();
  private final PengeluaranController pengeluaranController =
      new PengeluaranController();
  private final JTextField txtAwal = UIUtils.textField(10),
                           txtAkhir = UIUtils.textField(10);
  private final JLabel lblRingkasan = new JLabel();
  private final DefaultTableModel model = new DefaultTableModel(
      new Object[] {"Tanggal", "Jenis", "Uraian", "Pemasukan", "Pengeluaran",
                    "Keterangan"},
      0) {
    public boolean isCellEditable(int r, int c) { return false; }
  };
  private final JTable table = new JTable(model);
  public LaporanFrame() {
    setLayout(new BorderLayout(12, 12));
    setBackground(UIUtils.LIGHT_BLUE);
    setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
    add(UIUtils.title("Laporan Keuangan"), BorderLayout.NORTH);
    txtAwal.setText(LocalDate.now().withDayOfMonth(1).toString());
    txtAkhir.setText(LocalDate.now().toString());
    add(filterPanel(), BorderLayout.CENTER);
  }
  private JPanel filterPanel() {
    JPanel wrap = UIUtils.card();
    wrap.setLayout(new BorderLayout(8, 8));
    JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
    top.setOpaque(false);
    var tampil = UIUtils.primaryButton("Tampilkan");
    lblRingkasan.setFont(UIUtils.FONT_BOLD);
    top.add(new JLabel("Tanggal Awal"));
    top.add(txtAwal);
    top.add(new JLabel("Tanggal Akhir"));
    top.add(txtAkhir);
    top.add(tampil);
    wrap.add(top, BorderLayout.NORTH);
    wrap.add(UIUtils.tableScroll(table), BorderLayout.CENTER);
    wrap.add(lblRingkasan, BorderLayout.SOUTH);
    tampil.addActionListener(e -> loadReport());
    loadReport();
    return wrap;
  }
  private void loadReport() {
    LocalDate awal = UIUtils.parseDate(txtAwal.getText());
    LocalDate akhir = UIUtils.parseDate(txtAkhir.getText());
    if (awal == null || akhir == null)
      return;
    if (akhir.isBefore(awal)) {
      JOptionPane.showMessageDialog(
          this, "Tanggal akhir tidak boleh sebelum tanggal awal");
      return;
    }
    try {
      model.setRowCount(0);
      for (Pemasukan p : pemasukanController.getByDate(awal, akhir))
        model.addRow(new Object[] {
            p.getTanggal(), "Pemasukan", p.getSumberDana(),
            UIUtils.rupiah(p.getNominal()), "-", p.getKeterangan()});
      for (Pengeluaran p : pengeluaranController.getByDate(awal, akhir))
        model.addRow(
            new Object[] {p.getTanggal(), "Pengeluaran", p.getKeperluan(), "-",
                          UIUtils.rupiah(p.getNominal()), p.getKeterangan()});
      double masuk = pemasukanController.total(awal, akhir);
      double keluar = pengeluaranController.total(awal, akhir);
      lblRingkasan.setText(
          "Total Pemasukan: " + UIUtils.rupiah(masuk) +
          "    Total Pengeluaran: " + UIUtils.rupiah(keluar) +
          "    Saldo Akhir: " + UIUtils.rupiah(masuk - keluar));
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Gagal tampilkan laporan: " +
                                              e.getMessage());
    }
  }
}
