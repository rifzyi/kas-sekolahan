package form;

import controller.PemasukanController;
import controller.PengeluaranController;
import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Pemasukan;
import model.Pengeluaran;
import util.UIUtils;

public class LaporanFrame extends JPanel {
  private final PemasukanController pemasukan=new PemasukanController();
  private final PengeluaranController pengeluaran=new PengeluaranController();
  private final JTextField awal=UIUtils.textField(12), akhir=UIUtils.textField(12);
  private final JLabel totalMasuk=new JLabel(), totalKeluar=new JLabel(), saldo=new JLabel();
  private final DefaultTableModel model=new DefaultTableModel(new Object[]{"Tanggal","Jenis","Kategori","Nominal","Keterangan"},0){public boolean isCellEditable(int r,int c){return false;}};
  private final JTable table=new JTable(model);
  public LaporanFrame(){setLayout(new BorderLayout()); add(build(),BorderLayout.CENTER); tampilkan();}
  private JPanel build(){JPanel page=UIUtils.page("Laporan"); awal.setText(LocalDate.now().withDayOfMonth(1).toString()); akhir.setText(LocalDate.now().toString()); JButton show=UIUtils.button("Tampilkan",UIUtils.BLUE), cetak=UIUtils.button("Cetak PDF",Color.GRAY); show.addActionListener(e->tampilkan()); cetak.addActionListener(e->UIUtils.exportTableToPdf(this,table,"Laporan Kas Sekolah",summary())); JPanel filter=new JPanel(new FlowLayout(FlowLayout.LEFT,10,0)); filter.setOpaque(false); filter.add(UIUtils.formLabel("Tanggal Awal")); filter.add(awal); filter.add(UIUtils.formLabel("Tanggal Akhir")); filter.add(akhir); filter.add(show); filter.add(cetak); JPanel cards=new JPanel(new GridLayout(1,3,14,14)); cards.setOpaque(false); cards.add(labelCard("Total Pemasukan",totalMasuk,UIUtils.GREEN)); cards.add(labelCard("Total Pengeluaran",totalKeluar,UIUtils.RED)); cards.add(labelCard("Saldo Akhir",saldo,UIUtils.BLUE)); JPanel center=new JPanel(new BorderLayout(0,14)); center.setOpaque(false); JPanel top=new JPanel(new BorderLayout(0,14)); top.setOpaque(false); top.add(filter,BorderLayout.NORTH); top.add(cards,BorderLayout.CENTER); center.add(top,BorderLayout.NORTH); center.add(UIUtils.tableScroll(table),BorderLayout.CENTER); page.add(center,BorderLayout.CENTER); return page;}
  private JPanel labelCard(String title,JLabel value,Color color){JPanel c=UIUtils.card(); c.setLayout(new GridLayout(2,1)); JLabel t=new JLabel(title); t.setFont(UIUtils.FONT_BOLD); t.setForeground(UIUtils.MUTED); value.setFont(new Font("Segoe UI",Font.BOLD,20)); value.setForeground(color); c.add(t); c.add(value); return c;}
  private void tampilkan(){try{LocalDate a=LocalDate.parse(awal.getText().trim()), b=LocalDate.parse(akhir.getText().trim()); model.setRowCount(0); for(Pemasukan p:pemasukan.getByRange(a,b)) model.addRow(new Object[]{p.getTanggal(),"Pemasukan",p.getKategori(),UIUtils.rupiah(p.getNominal()),p.getKeterangan()}); for(Pengeluaran p:pengeluaran.getByRange(a,b)) model.addRow(new Object[]{p.getTanggal(),"Pengeluaran",p.getKategori(),UIUtils.rupiah(p.getNominal()),p.getKeterangan()}); double m=pemasukan.total(a,b), k=pengeluaran.total(a,b); totalMasuk.setText(UIUtils.rupiah(m)); totalKeluar.setText(UIUtils.rupiah(k)); saldo.setText(UIUtils.rupiah(m-k));}catch(Exception e){JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}}
  private String summary(){return "Total Pemasukan: "+totalMasuk.getText()+" | Total Pengeluaran: "+totalKeluar.getText()+" | Saldo Akhir: "+saldo.getText();}
}
