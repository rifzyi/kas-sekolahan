package form;

import controller.PengaturanController;
import java.awt.*;
import javax.swing.*;
import model.Pengaturan;
import util.UIUtils;

public class PengaturanFrame extends JPanel {
  private final PengaturanController controller=new PengaturanController();
  private final JTextField nama=UIUtils.textField(24), telepon=UIUtils.textField(18), email=UIUtils.textField(18), kepala=UIUtils.textField(18), bendahara=UIUtils.textField(18), logo=UIUtils.textField(18);
  private final JTextArea alamat=new JTextArea(4,24);
  private int id;
  public PengaturanFrame(){setLayout(new BorderLayout()); add(build(),BorderLayout.CENTER); load();}
  private JPanel build(){JPanel page=UIUtils.page("Pengaturan"); JPanel card=UIUtils.card(); card.setLayout(new GridBagLayout()); GridBagConstraints g=new GridBagConstraints(); g.insets=new Insets(8,8,8,8); g.fill=GridBagConstraints.HORIZONTAL; alamat.setFont(UIUtils.FONT); addRow(card,g,0,"Nama Sekolah",nama); addRow(card,g,1,"Alamat",new JScrollPane(alamat)); addRow(card,g,2,"Telepon",telepon); addRow(card,g,3,"Email",email); addRow(card,g,4,"Kepala Sekolah",kepala); addRow(card,g,5,"Bendahara",bendahara); addRow(card,g,6,"Logo Path",logo); JButton save=UIUtils.button("Simpan Pengaturan",UIUtils.GREEN); g.gridx=0;g.gridy=7;g.gridwidth=2;card.add(save,g); save.addActionListener(e->save()); page.add(card,BorderLayout.NORTH); return page;}
  private void load(){try{Pengaturan p=controller.get(); id=p.getIdPengaturan(); nama.setText(p.getNamaSekolah()); alamat.setText(p.getAlamat()); telepon.setText(p.getTelepon()); email.setText(p.getEmail()); kepala.setText(p.getKepalaSekolah()); bendahara.setText(p.getBendahara()); logo.setText(p.getLogoPath());}catch(Exception e){error(e);}}
  private void save(){try{controller.save(new Pengaturan(id,nama.getText().trim(),alamat.getText().trim(),telepon.getText().trim(),email.getText().trim(),kepala.getText().trim(),bendahara.getText().trim(),logo.getText().trim())); JOptionPane.showMessageDialog(this,"Pengaturan berhasil disimpan"); load();}catch(Exception e){error(e);}}
  private void addRow(JPanel p,GridBagConstraints g,int y,String l,JComponent c){g.gridy=y;g.gridx=0;g.gridwidth=1;p.add(UIUtils.formLabel(l),g);g.gridx=1;p.add(c,g);} private void error(Exception e){JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} }
