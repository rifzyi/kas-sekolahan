// File: view/DashboardPanel.java
package view;

import controller.DashboardController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import util.UIUtils;

public class DashboardPanel extends JPanel {
    private final DashboardController controller = new DashboardController();
    private final JLabel pemasukanValue = new JLabel("Rp0");
    private final JLabel pengeluaranValue = new JLabel("Rp0");
    private final JLabel saldoValue = new JLabel("Rp0");

    public DashboardPanel() {
        setLayout(new BorderLayout(0, 18));
        setBackground(UIUtils.BACKGROUND);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 24, 24, 24));
        add(UIUtils.title("Dashboard"), BorderLayout.NORTH);
        add(createCards(), BorderLayout.CENTER);
        refresh();
    }

    private JPanel createCards() {
        JPanel cards = new JPanel(new GridLayout(1, 3, 16, 16));
        cards.setOpaque(false);
        cards.add(summaryCard("Total Pemasukan", pemasukanValue, UIUtils.SUCCESS));
        cards.add(summaryCard("Total Pengeluaran", pengeluaranValue, UIUtils.DANGER));
        cards.add(summaryCard("Saldo Akhir", saldoValue, UIUtils.PRIMARY));
        return cards;
    }

    private JPanel summaryCard(String title, JLabel value, Color color) {
        JPanel card = UIUtils.modernCardPanel();
        card.setLayout(new BorderLayout(0, 12));
        JLabel label = new JLabel(title);
        label.setForeground(UIUtils.MUTED);
        value.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 28));
        value.setForeground(color);
        card.add(label, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);
        return card;
    }

    public void refresh() {
        try {
            double[] summary = controller.getSummary();
            pemasukanValue.setText(UIUtils.formatRupiah(summary[0]));
            pengeluaranValue.setText(UIUtils.formatRupiah(summary[1]));
            saldoValue.setText(UIUtils.formatRupiah(summary[2]));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat dashboard: " + ex.getMessage(), "Dashboard", JOptionPane.ERROR_MESSAGE);
        }
    }
}
