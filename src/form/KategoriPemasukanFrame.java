package form;

import controller.PemasukanController;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import util.UIUtils;
import util.UIUtils.Option;

public class KategoriPemasukanFrame extends JPanel {
  private final PemasukanController controller = new PemasukanController();
  private final DefaultTableModel model = new DefaultTableModel(new Object[] {"ID", "Nama Kategori"}, 0) { public boolean isCellEditable(int r, int c) { return false; } };
  private final JTable table = new JTable(model);
  public KategoriPemasukanFrame() { setLayout(new BorderLayout()); add(build(), BorderLayout.CENTER); loadData(); }
  private JPanel build() { JPanel page=UIUtils.page("Kategori Pemasukan"); JTextField search=UIUtils.textField(18); JButton add=UIUtils.button("+ Tambah Data",UIUtils.GREEN), edit=UIUtils.button("Edit",UIUtils.ORANGE), del=UIUtils.button("Hapus",UIUtils.RED), ref=UIUtils.button("Refresh",UIUtils.BLUE); add.addActionListener(e->dialog(0,"")); edit.addActionListener(e->edit()); del.addActionListener(e->delete()); ref.addActionListener(e->loadData()); TableRowSorter<DefaultTableModel> sorter=new TableRowSorter<>(model); table.setRowSorter(sorter); UIUtils.bindSearch(search,sorter); JPanel center=new JPanel(new BorderLayout(0,14)); center.setOpaque(false); center.add(UIUtils.toolbar(search,add,edit,del,ref),BorderLayout.NORTH); center.add(UIUtils.tableScroll(table),BorderLayout.CENTER); page.add(center,BorderLayout.CENTER); return page; }
  private void loadData(){ try{ model.setRowCount(0); for(Option o:controller.kategoriAll()) model.addRow(new Object[]{o.getId(),o.getLabel()}); }catch(Exception e){error(e);} }
  private int selected(){int v=table.getSelectedRow(); if(v<0) JOptionPane.showMessageDialog(this,"Pilih data terlebih dahulu"); return v<0?-1:table.convertRowIndexToModel(v);}
  private void edit(){int r=selected(); if(r>=0) dialog((int)model.getValueAt(r,0), String.valueOf(model.getValueAt(r,1)));}
  private void delete(){int r=selected(); if(r>=0 && JOptionPane.showConfirmDialog(this,"Hapus kategori pemasukan?","Konfirmasi",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) try{controller.deleteKategori((int)model.getValueAt(r,0)); loadData();}catch(Exception e){error(e);}}
  private void dialog(int id,String value){JDialog d=new JDialog(SwingUtilities.getWindowAncestor(this),id==0?"Tambah Kategori Pemasukan":"Edit Kategori Pemasukan",Dialog.ModalityType.APPLICATION_MODAL); JPanel p=UIUtils.card(); p.setLayout(new GridBagLayout()); GridBagConstraints g=new GridBagConstraints(); g.insets=new Insets(8,8,8,8); g.fill=GridBagConstraints.HORIZONTAL; JTextField nama=UIUtils.textField(24); nama.setText(value); g.gridx=0;g.gridy=0;p.add(UIUtils.formLabel("Nama Kategori"),g);g.gridx=1;p.add(nama,g); JButton save=UIUtils.button("Simpan",UIUtils.GREEN); g.gridx=0;g.gridy=1;g.gridwidth=2;p.add(save,g); save.addActionListener(e->{try{ if(id==0) controller.insertKategori(nama.getText().trim()); else controller.updateKategori(id,nama.getText().trim()); d.dispose(); loadData(); }catch(Exception ex){error(ex);}}); d.add(p); d.pack(); d.setLocationRelativeTo(this); d.setVisible(true);}
  private void error(Exception e){JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} }
