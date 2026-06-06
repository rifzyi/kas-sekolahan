package form;

import controller.SiswaController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Siswa;
import util.UIUtils;
import util.UIUtils.Option;

public class SiswaFrame extends JPanel {
  private final SiswaController controller = new SiswaController();
  private final DefaultTableModel model = new DefaultTableModel(new Object[] {"ID", "NIS", "Nama Siswa", "Kelas", "Jenis Kelamin", "Alamat", "ID Kelas"}, 0) { public boolean isCellEditable(int r, int c) { return false; } };
  private final JTable table = new JTable(model);
  public SiswaFrame(){setLayout(new BorderLayout()); add(build(),BorderLayout.CENTER); loadData();}
  private JPanel build(){JPanel page=UIUtils.page("Data Siswa"); JTextField search=UIUtils.textField(18); JButton add=UIUtils.button("+ Tambah Data",UIUtils.GREEN), edit=UIUtils.button("Edit",UIUtils.ORANGE), del=UIUtils.button("Hapus",UIUtils.RED), ref=UIUtils.button("Refresh",UIUtils.BLUE); add.addActionListener(e->dialog(null)); edit.addActionListener(e->edit()); del.addActionListener(e->delete()); ref.addActionListener(e->loadData()); TableRowSorter<DefaultTableModel> sorter=new TableRowSorter<>(model); table.setRowSorter(sorter); UIUtils.bindSearch(search,sorter); table.removeColumn(table.getColumnModel().getColumn(6)); JPanel center=new JPanel(new BorderLayout(0,14)); center.setOpaque(false); center.add(UIUtils.toolbar(search,add,edit,del,ref),BorderLayout.NORTH); center.add(UIUtils.tableScroll(table),BorderLayout.CENTER); page.add(center,BorderLayout.CENTER); return page;}
  private void loadData(){try{model.setRowCount(0); for(Siswa s:controller.getAll()) model.addRow(new Object[]{s.getIdSiswa(),s.getNis(),s.getNamaSiswa(),s.getKelas(),s.getJenisKelamin(),s.getAlamat(),s.getIdKelas()});}catch(Exception e){error(e);}}
  private int selected(){int v=table.getSelectedRow(); if(v<0) JOptionPane.showMessageDialog(this,"Pilih data terlebih dahulu"); return v<0?-1:table.convertRowIndexToModel(v);}
  private void edit(){int r=selected(); if(r>=0) dialog(new Siswa((int)model.getValueAt(r,0),String.valueOf(model.getValueAt(r,1)),String.valueOf(model.getValueAt(r,2)),(int)model.getValueAt(r,6),String.valueOf(model.getValueAt(r,3)),String.valueOf(model.getValueAt(r,4)),String.valueOf(model.getValueAt(r,5))));}
  private void delete(){int r=selected(); if(r>=0 && JOptionPane.showConfirmDialog(this,"Hapus data siswa?","Konfirmasi",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) try{controller.delete((int)model.getValueAt(r,0)); loadData();}catch(Exception e){error(e);}}
  private void dialog(Siswa data){JDialog d=new JDialog(SwingUtilities.getWindowAncestor(this),data==null?"Tambah Siswa":"Edit Siswa",Dialog.ModalityType.APPLICATION_MODAL); JPanel p=UIUtils.card(); p.setLayout(new GridBagLayout()); GridBagConstraints g=new GridBagConstraints(); g.insets=new Insets(8,8,8,8); g.fill=GridBagConstraints.HORIZONTAL; JTextField nis=UIUtils.textField(22), nama=UIUtils.textField(22); JComboBox<Option> kelas=new JComboBox<>(); JComboBox<String> jk=new JComboBox<>(new String[]{"Laki-laki","Perempuan"}); JTextArea alamat=new JTextArea(4,22); alamat.setFont(UIUtils.FONT); try{List<Option> opts=controller.kelasOptions(); for(Option o:opts) kelas.addItem(o);}catch(Exception e){error(e);} if(data!=null){nis.setText(data.getNis());nama.setText(data.getNamaSiswa());select(kelas,data.getIdKelas());jk.setSelectedItem(data.getJenisKelamin());alamat.setText(data.getAlamat());} addRow(p,g,0,"NIS",nis); addRow(p,g,1,"Nama Siswa",nama); addRow(p,g,2,"Kelas",kelas); addRow(p,g,3,"Jenis Kelamin",jk); addRow(p,g,4,"Alamat",new JScrollPane(alamat)); JButton save=UIUtils.button("Simpan",UIUtils.GREEN); g.gridx=0;g.gridy=5;g.gridwidth=2;p.add(save,g); save.addActionListener(e->{try{Option o=(Option)kelas.getSelectedItem(); Siswa s=new Siswa(data==null?0:data.getIdSiswa(),nis.getText().trim(),nama.getText().trim(),o==null?0:o.getId(),null,String.valueOf(jk.getSelectedItem()),alamat.getText().trim()); if(data==null)controller.insert(s); else controller.update(s); d.dispose(); loadData();}catch(Exception ex){error(ex);}}); d.add(p); d.pack(); d.setLocationRelativeTo(this); d.setVisible(true);}
  private void select(JComboBox<Option> cb,int id){for(int i=0;i<cb.getItemCount();i++) if(cb.getItemAt(i).getId()==id) cb.setSelectedIndex(i);} private void addRow(JPanel p,GridBagConstraints g,int y,String l,JComponent c){g.gridy=y;g.gridx=0;g.gridwidth=1;p.add(UIUtils.formLabel(l),g);g.gridx=1;p.add(c,g);} private void error(Exception e){JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} }
