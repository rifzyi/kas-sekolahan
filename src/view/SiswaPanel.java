// File: view/SiswaPanel.java
package view;

import controller.SiswaController;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Siswa;
import util.Option;
import util.UIUtils;

public class SiswaPanel extends JPanel {
    private final SiswaController controller = new SiswaController();
    private final DefaultTableModel tableModel = new DefaultTableModel(new Object[] {"ID", "NIS", "Nama Siswa", "Kelas", "Jenis Kelamin", "Alamat"}, 0) { public boolean isCellEditable(int r, int c) { return false; } };
    private final JTable table = new JTable(tableModel);
    private final JTextField searchField = UIUtils.modernTextField("Cari siswa...");
    private final JTextField nisField = UIUtils.modernTextField("NIS");
    private final JTextField namaField = UIUtils.modernTextField("Nama siswa");
    private final JComboBox<Option> kelasCombo = UIUtils.modernComboBox();
    private final JComboBox<String> genderCombo = UIUtils.modernComboBox();
    private final JTextArea alamatArea = UIUtils.modernTextArea("Alamat");
    private int selectedId = 0;

    public SiswaPanel() {
        setLayout(new BorderLayout(0, 16));
        setBackground(UIUtils.BACKGROUND);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 24, 24, 24));
        add(UIUtils.title("Data Siswa"), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
        bindEvents();
        loadKelas();
        loadData();
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);

        JPanel top = new JPanel(new BorderLayout(12, 0));
        top.setOpaque(false);
        top.add(searchField, BorderLayout.CENTER);
        JButton refreshButton = UIUtils.modernButton("Refresh", UIUtils.PRIMARY);
        top.add(refreshButton, BorderLayout.EAST);
        refreshButton.addActionListener(event -> loadData());
        content.add(top, BorderLayout.NORTH);

        content.add(UIUtils.modernScrollPane(table), BorderLayout.CENTER);
        content.add(createForm(), BorderLayout.SOUTH);
        return content;
    }

    private JPanel createForm() {
        JPanel form = UIUtils.modernCardPanel();
        form.setLayout(new GridBagLayout());
        genderCombo.addItem("Laki-laki");
        genderCombo.addItem("Perempuan");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        addField(form, gbc, 0, 0, "NIS", nisField);
        addField(form, gbc, 1, 0, "Nama", namaField);
        addField(form, gbc, 2, 0, "Kelas", kelasCombo);
        addField(form, gbc, 0, 2, "Jenis Kelamin", genderCombo);
        addField(form, gbc, 1, 2, "Alamat", new JScrollPane(alamatArea));

        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        JButton saveButton = UIUtils.modernButton("Simpan", UIUtils.SUCCESS);
        JButton updateButton = UIUtils.modernButton("Update", UIUtils.PRIMARY);
        JButton deleteButton = UIUtils.modernButton("Hapus", UIUtils.DANGER);
        JButton resetButton = UIUtils.modernButton("Reset", UIUtils.MUTED);
        buttons.add(saveButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);
        buttons.add(resetButton);
        saveButton.addActionListener(event -> save());
        updateButton.addActionListener(event -> update());
        deleteButton.addActionListener(event -> delete());
        resetButton.addActionListener(event -> resetForm());
        gbc.gridx = 2; gbc.gridy = 2; gbc.gridheight = 2;
        form.add(buttons, gbc);
        return form;
    }

    private void addField(JPanel form, GridBagConstraints gbc, int x, int y, String label, java.awt.Component component) {
        gbc.gridx = x; gbc.gridy = y; gbc.gridheight = 1;
        form.add(UIUtils.label(label), gbc);
        gbc.gridy = y + 1;
        form.add(component, gbc);
    }

    private void bindEvents() {
        searchField.addActionListener(event -> loadData());
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() >= 0) fillFormFromTable();
        });
    }

    private void loadKelas() {
        try {
            kelasCombo.removeAllItems();
            for (Option option : controller.getKelasOptions()) kelasCombo.addItem(option);
        } catch (Exception ex) {
            showError("Gagal memuat kelas", ex);
        }
    }

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            List<Siswa> data = controller.getAll(searchField.getText());
            for (Siswa siswa : data) {
                tableModel.addRow(new Object[] {siswa.getIdSiswa(), siswa.getNis(), siswa.getNamaSiswa(), siswa.getNamaKelas(), siswa.getJenisKelamin(), siswa.getAlamat()});
            }
        } catch (Exception ex) {
            showError("Gagal memuat siswa", ex);
        }
    }

    private void save() {
        Siswa siswa = readForm(false);
        if (siswa == null) return;
        try {
            controller.insert(siswa);
            loadData();
            resetForm();
        } catch (Exception ex) {
            showError("Gagal menyimpan siswa", ex);
        }
    }

    private void update() {
        if (selectedId == 0) { JOptionPane.showMessageDialog(this, "Pilih baris siswa terlebih dahulu."); return; }
        Siswa siswa = readForm(true);
        if (siswa == null) return;
        try {
            controller.update(siswa);
            loadData();
            resetForm();
        } catch (Exception ex) {
            showError("Gagal mengupdate siswa", ex);
        }
    }

    private void delete() {
        if (selectedId == 0) { JOptionPane.showMessageDialog(this, "Pilih baris siswa terlebih dahulu."); return; }
        if (JOptionPane.showConfirmDialog(this, "Hapus data siswa ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            controller.delete(selectedId);
            loadData();
            resetForm();
        } catch (Exception ex) {
            showError("Gagal menghapus siswa", ex);
        }
    }

    private Siswa readForm(boolean includeId) {
        if (nisField.getText().trim().isEmpty() || namaField.getText().trim().isEmpty() || kelasCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "NIS, nama siswa, dan kelas wajib diisi.", "Validasi", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        Option kelas = (Option) kelasCombo.getSelectedItem();
        Siswa siswa = new Siswa();
        if (includeId) siswa.setIdSiswa(selectedId);
        siswa.setNis(nisField.getText().trim());
        siswa.setNamaSiswa(namaField.getText().trim());
        siswa.setIdKelas(kelas.getId());
        siswa.setJenisKelamin((String) genderCombo.getSelectedItem());
        siswa.setAlamat(alamatArea.getText().trim());
        return siswa;
    }

    private void fillFormFromTable() {
        int row = table.convertRowIndexToModel(table.getSelectedRow());
        selectedId = (Integer) tableModel.getValueAt(row, 0);
        nisField.setText(String.valueOf(tableModel.getValueAt(row, 1)));
        namaField.setText(String.valueOf(tableModel.getValueAt(row, 2)));
        selectComboByText(kelasCombo, String.valueOf(tableModel.getValueAt(row, 3)));
        genderCombo.setSelectedItem(String.valueOf(tableModel.getValueAt(row, 4)));
        alamatArea.setText(String.valueOf(tableModel.getValueAt(row, 5)));
    }

    private void selectComboByText(JComboBox<Option> combo, String text) {
        for (int i = 0; i < combo.getItemCount(); i++) if (combo.getItemAt(i).toString().equals(text)) combo.setSelectedIndex(i);
    }

    private void resetForm() {
        selectedId = 0;
        nisField.setText("");
        namaField.setText("");
        alamatArea.setText("");
        if (kelasCombo.getItemCount() > 0) kelasCombo.setSelectedIndex(0);
        genderCombo.setSelectedIndex(0);
        table.clearSelection();
    }

    private void showError(String message, Exception ex) {
        JOptionPane.showMessageDialog(this, message + ": " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
