package form;

import controller.UserController;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.User;
import util.UIUtils;

public class UserFrame extends JPanel {
  private final UserController controller=new UserController();
  private final DefaultTableModel model=new DefaultTableModel(new Object[]{"ID","Nama","Username","Password","Role"},0){public boolean isCellEditable(int r,int c){return false;}};
  private final JTable table=new JTable(model);
  public UserFrame(){setLayout(new BorderLayout()); add(build(),BorderLayout.CENTER); loadData();}
  private JPanel build(){JPanel page=UIUtils.page("User"); JTextField search=UIUtils.textField(18); JButton add=UIUtils.button("+ Tambah Data",UIUtils.GREEN), edit=UIUtils.button("Edit",UIUtils.ORANGE), del=UIUtils.button("Hapus",UIUtils.RED), ref=UIUtils.button("Refresh",UIUtils.BLUE); add.addActionListener(e->dialog(null)); edit.addActionListener(e->edit()); del.addActionListener(e->delete()); ref.addActionListener(e->loadData()); TableRowSorter<DefaultTableModel> sorter=new TableRowSorter<>(model); table.setRowSorter(sorter); UIUtils.bindSearch(search,sorter); JPanel center=new JPanel(new BorderLayout(0,14)); center.setOpaque(false); center.add(UIUtils.toolbar(search,add,edit,del,ref),BorderLayout.NORTH); center.add(UIUtils.tableScroll(table),BorderLayout.CENTER); page.add(center,BorderLayout.CENTER); return page;}
  private void loadData(){try{model.setRowCount(0); for(User u:controller.getAll()) model.addRow(new Object[]{u.getIdUser(),u.getNama(),u.getUsername(),u.getPassword(),u.getRole()});}catch(Exception e){error(e);}}
  private int selected(){int v=table.getSelectedRow(); if(v<0) JOptionPane.showMessageDialog(this,"Pilih data terlebih dahulu"); return v<0?-1:table.convertRowIndexToModel(v);}
  private void edit(){int r=selected(); if(r>=0) dialog(new User((int)model.getValueAt(r,0),String.valueOf(model.getValueAt(r,1)),String.valueOf(model.getValueAt(r,2)),String.valueOf(model.getValueAt(r,3)),String.valueOf(model.getValueAt(r,4))));}
  private void delete(){int r=selected(); if(r>=0 && JOptionPane.showConfirmDialog(this,"Hapus user?","Konfirmasi",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) try{controller.delete((int)model.getValueAt(r,0)); loadData();}catch(Exception e){error(e);}}
  private void dialog(User data){JDialog d=new JDialog(SwingUtilities.getWindowAncestor(this),data==null?"Tambah User":"Edit User",Dialog.ModalityType.APPLICATION_MODAL); JPanel p=UIUtils.card(); p.setLayout(new GridBagLayout()); GridBagConstraints g=new GridBagConstraints(); g.insets=new Insets(8,8,8,8); g.fill=GridBagConstraints.HORIZONTAL; JTextField nama=UIUtils.textField(22), username=UIUtils.textField(22); JPasswordField pass=new JPasswordField(22); pass.setBorder(nama.getBorder()); JComboBox<String> role=new JComboBox<>(new String[]{"Admin","Bendahara"}); if(data!=null){nama.setText(data.getNama()); username.setText(data.getUsername()); pass.setText(data.getPassword()); role.setSelectedItem(data.getRole());} addRow(p,g,0,"Nama",nama); addRow(p,g,1,"Username",username); addRow(p,g,2,"Password",pass); addRow(p,g,3,"Role",role); JButton save=UIUtils.button("Simpan",UIUtils.GREEN); g.gridx=0;g.gridy=4;g.gridwidth=2;p.add(save,g); save.addActionListener(e->{try{User u=new User(data==null?0:data.getIdUser(),nama.getText().trim(),username.getText().trim(),new String(pass.getPassword()),String.valueOf(role.getSelectedItem())); if(data==null)controller.insert(u); else controller.update(u); d.dispose(); loadData();}catch(Exception ex){error(ex);}}); d.add(p); d.pack(); d.setLocationRelativeTo(this); d.setVisible(true);}
  private void addRow(JPanel p,GridBagConstraints g,int y,String l,JComponent c){g.gridy=y;g.gridx=0;g.gridwidth=1;p.add(UIUtils.formLabel(l),g);g.gridx=1;p.add(c,g);} private void error(Exception e){JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} }
