// File: form/DashboardForm.java
package form;

import controller.DashboardController;import java.awt.*;import javax.swing.*;import javax.swing.table.DefaultTableModel;import util.UIUtils;

public class DashboardForm extends JPanel implements MainFrame.Refreshable{
    private final DashboardController controller=new DashboardController(); private final JLabel saldo=new JLabel(); private final JLabel pemasukan=new JLabel(); private final JLabel pengeluaran=new JLabel(); private final DefaultTableModel model=new DefaultTableModel(new Object[]{"Tanggal","Jenis","Kategori","Nominal","Keterangan"},0){public boolean isCellEditable(int r,int c){return false;}};
    public DashboardForm(){ setLayout(new BorderLayout(0,15)); setBackground(UIUtils.BACKGROUND); setBorder(BorderFactory.createEmptyBorder(15,15,15,15)); add(UIUtils.title("Dashboard"),BorderLayout.NORTH); JPanel center=new JPanel(new BorderLayout(0,15)); center.setOpaque(false); JPanel cards=new JPanel(new GridLayout(1,3,15,15)); cards.setOpaque(false); cards.add(summary("Saldo",saldo,UIUtils.PRIMARY)); cards.add(summary("Pemasukan",pemasukan,UIUtils.SUCCESS)); cards.add(summary("Pengeluaran",pengeluaran,UIUtils.DANGER)); center.add(cards,BorderLayout.NORTH); JTable table=new JTable(model); center.add(UIUtils.modernScrollPane(table),BorderLayout.CENTER); add(center,BorderLayout.CENTER); refreshData(); }
    private JPanel summary(String title,JLabel value,Color color){ JPanel p=UIUtils.card(); p.setLayout(new GridLayout(2,1)); JLabel t=UIUtils.label(title); value.setFont(new Font("SansSerif",Font.BOLD,20)); value.setForeground(color); p.add(t); p.add(value); return p; }
    public void refreshData(){ try{ saldo.setText(UIUtils.formatRupiah(controller.getSaldo())); pemasukan.setText(UIUtils.formatRupiah(controller.getTotalPemasukan())); pengeluaran.setText(UIUtils.formatRupiah(controller.getTotalPengeluaran())); model.setRowCount(0); for(Object[] row:controller.getRecentTransactions()) model.addRow(row); }catch(Exception ex){ UIUtils.showError(this,ex);} }
}
