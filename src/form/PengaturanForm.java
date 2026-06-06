// File: form/PengaturanForm.java
package form;

import controller.PengaturanController;import java.awt.*;import javax.swing.*;import model.Pengaturan;import util.UIUtils;

public class PengaturanForm extends JPanel implements MainFrame.Refreshable{
    private final PengaturanController controller=new PengaturanController(); private final JTextField nama=UIUtils.createModernTextField("Nama sekolah"), telepon=UIUtils.createModernTextField("Telepon"), kepala=UIUtils.createModernTextField("Kepala sekolah"), bendahara=UIUtils.createModernTextField("Bendahara"); private final JTextArea alamat=UIUtils.modernTextArea("Alamat"); private int id=0;
    public PengaturanForm(){ setLayout(new BorderLayout(0,15)); setBackground(UIUtils.BACKGROUND); setBorder(BorderFactory.createEmptyBorder(15,15,15,15)); add(UIUtils.title("Pengaturan"),BorderLayout.NORTH); JPanel form=new JPanel(new GridLayout(0,2,8,8)); form.setOpaque(false); form.add(UIUtils.label("Nama Sekolah"));form.add(nama); form.add(UIUtils.label("Alamat"));form.add(new JScrollPane(alamat)); form.add(UIUtils.label("Telepon"));form.add(telepon); form.add(UIUtils.label("Kepala Sekolah"));form.add(kepala); form.add(UIUtils.label("Bendahara"));form.add(bendahara); JButton save=UIUtils.createModernButton("Simpan Pengaturan",UIUtils.SUCCESS); save.addActionListener(e->save()); JPanel center=UIUtils.card(); center.add(form,BorderLayout.CENTER); center.add(save,BorderLayout.SOUTH); add(center,BorderLayout.WEST); refreshData(); }
    private void save(){ try{ controller.save(new Pengaturan(id,nama.getText(),alamat.getText(),telepon.getText(),kepala.getText(),bendahara.getText())); JOptionPane.showMessageDialog(this,"Pengaturan tersimpan."); refreshData(); }catch(Exception ex){ UIUtils.showError(this,ex);} }
    public void refreshData(){ try{ Pengaturan p=controller.get(); id=p.getIdPengaturan(); nama.setText(p.getNamaSekolah()); alamat.setText(p.getAlamat()); telepon.setText(p.getTelepon()); kepala.setText(p.getKepalaSekolah()); bendahara.setText(p.getBendahara()); }catch(Exception ex){ UIUtils.showError(this,ex);} }
}
