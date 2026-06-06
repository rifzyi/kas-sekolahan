// File: view/PemasukanPanel.java
package view;

import controller.PemasukanController;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Pemasukan;
import util.Option;
import util.UIUtils;

public class PemasukanPanel extends JPanel {
    private final PemasukanController controller = new PemasukanController();
    private final DefaultTableModel tableModel = new DefaultTableModel(new Object[] {"ID", "Tanggal", "Kategori", "Nominal", "Keterangan"}, 0) { public boolean isCellEditable(int r, int c) { return false; } };
    private final JTable table = new JTable(tableModel);
    private final JTextField searchField = UIUtils.modernTextField("Cari pemasukan...");
    private final JTextField tanggalField = UIUtils.modernTextField("YYYY-MM-DD");
    private final JComboBox<Option> kategoriCombo = UIUtils.modernComboBox();
    private final JTextField nominalField = UIUtils.modernTextField("Nominal");
    private final JTextArea keteranganArea = UIUtils.modernTextArea("Keterangan");
    private int selectedId = 0;

    public PemasukanPanel() {
        setLayout(new BorderLayout(0, 16));
        setBackground(UIUtils.BACKGROUND);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 24, 24, 24));
        add(UIUtils.title("Data Pemasukan"), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
        bindEvents();
        loadKategori();
        loadData();
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);
        JButton refreshButton = UIUtils.modernButton("Refresh", UIUtils.PRIMARY);
        JPanel top = new JPanel(new BorderLayout(12, 0));
        top.setOpaque(false);
        top.add(searchField, BorderLayout.CENTER);
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        addField(form, gbc, 0, 0, "Tanggal", tanggalField);
        addField(form, gbc, 1, 0, "Kategori", kategoriCombo);
        addField(form, gbc, 2, 0, "Nominal", nominalField);
        addField(form, gbc, 0, 2, "Keterangan", new JScrollPane(keteranganArea));
        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        JButton saveButton = UIUtils.modernButton("Simpan", UIUtils.SUCCESS);
        JButton updateButton = UIUtils.modernButton("Update", UIUtils.PRIMARY);
        JButton deleteButton = UIUtils.modernButton("Hapus", UIUtils.DANGER);
        JButton resetButton = UIUtils.modernButton("Reset", UIUtils.MUTED);
        buttons.add(saveButton); buttons.add(updateButton); buttons.add(deleteButton); buttons.add(resetButton);
        saveButton.addActionListener(event -> save());
        updateButton.addActionListener(event -> update());
        deleteButton.addActionListener(event -> delete());
        resetButton.addActionListener(event -> resetForm());
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        form.add(buttons, gbc);
        return form;
    }

    private void addField(JPanel form, GridBagConstraints gbc, int x, int y, String label, java.awt.Component component) {
        gbc.gridx = x; gbc.gridy = y; gbc.gridwidth = 1;
        form.add(UIUtils.label(label), gbc);
        gbc.gridy = y + 1;
        form.add(component, gbc);
    }

    private void bindEvents() {
        searchField.addActionListener(event -> loadData());
        table.getSelectionModel().addListSelectionListener(event -> { if (!event.getValueIsAdjusting() && table.getSelectedRow() >= 0) fillFormFromTable(); });
    }

    private void loadKategori() {
        try {
            kategoriCombo.removeAllItems();
            for (Option option : controller.getKategoriOptions()) kategoriCombo.addItem(option);
        } catch (Exception ex) { showError("Gagal memuat kategori", ex); }
    }

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            for (Pemasukan pemasukan : controller.getAll(searchField.getText())) {
                tableModel.addRow(new Object[] {pemasukan.getIdPemasukan(), pemasukan.getTanggal(), pemasukan.getNamaKategori(), UIUtils.formatRupiah(pemasukan.getNominal()), pemasukan.getKeterangan()});
            }
        } catch (Exception ex) { showError("Gagal memuat pemasukan", ex); }
    }

    private void save() {
        Pemasukan pemasukan = readForm(false);
        if (pemasukan == null) return;
        try { controller.insert(pemasukan); loadData(); resetForm(); } catch (Exception ex) { showError("Gagal menyimpan pemasukan", ex); }
    }

    private void update() {
        if (selectedId == 0) { JOptionPane.showMessageDialog(this, "Pilih baris pemasukan terlebih dahulu."); return; }
        Pemasukan pemasukan = readForm(true);
        if (pemasukan == null) return;
        try { controller.update(pemasukan); loadData(); resetForm(); } catch (Exception ex) { showError("Gagal mengupdate pemasukan", ex); }
    }

    private void delete() {
        if (selectedId == 0) { JOptionPane.showMessageDialog(this, "Pilih baris pemasukan terlebih dahulu."); return; }
        if (JOptionPane.showConfirmDialog(this, "Hapus data pemasukan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try { controller.delete(selectedId); loadData(); resetForm(); } catch (Exception ex) { showError("Gagal menghapus pemasukan", ex); }
    }

    private Pemasukan readForm(boolean includeId) {
        if (tanggalField.getText().trim().isEmpty() || nominalField.getText().trim().isEmpty() || kategoriCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Tanggal, kategori, dan nominal wajib diisi.", "Validasi", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        LocalDate tanggal;
        double nominal;
        try { tanggal = LocalDate.parse(tanggalField.getText().trim()); } catch (DateTimeParseException ex) { JOptionPane.showMessageDialog(this, "Format tanggal harus YYYY-MM-DD."); return null; }
        try { nominal = Double.parseDouble(nominalField.getText().trim().replace(".", "").replace(",", ".")); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Nominal harus berupa angka."); return null; }
        Option kategori = (Option) kategoriCombo.getSelectedItem();
        Pemasukan pemasukan = new Pemasukan();
        if (includeId) pemasukan.setIdPemasukan(selectedId);
        pemasukan.setTanggal(tanggal);
        pemasukan.setIdKategoriPemasukan(kategori.getId());
        pemasukan.setNominal(nominal);
        pemasukan.setKeterangan(keteranganArea.getText().trim());
        return pemasukan;
    }

    private void fillFormFromTable() {
        int row = table.convertRowIndexToModel(table.getSelectedRow());
        selectedId = (Integer) tableModel.getValueAt(row, 0);
        tanggalField.setText(String.valueOf(tableModel.getValueAt(row, 1)));
        selectComboByText(kategoriCombo, String.valueOf(tableModel.getValueAt(row, 2)));
        nominalField.setText(String.valueOf(tableModel.getValueAt(row, 3)).replace("Rp", "").replace(".", "").trim());
        keteranganArea.setText(String.valueOf(tableModel.getValueAt(row, 4)));
    }

    private void selectComboByText(JComboBox<Option> combo, String text) {
        for (int i = 0; i < combo.getItemCount(); i++) if (combo.getItemAt(i).toString().equals(text)) combo.setSelectedIndex(i);
    }

    private void resetForm() {
        selectedId = 0; tanggalField.setText(""); nominalField.setText(""); keteranganArea.setText(""); if (kategoriCombo.getItemCount() > 0) kategoriCombo.setSelectedIndex(0); table.clearSelection();
    }

    private void showError(String message, Exception ex) { JOptionPane.showMessageDialog(this, message + ": " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
}
