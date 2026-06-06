// File: view/PengaturanPanel.java
package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.UIUtils;

public class PengaturanPanel extends JPanel {
    public PengaturanPanel() {
        setLayout(new BorderLayout(0, 16));
        setBackground(UIUtils.BACKGROUND);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 24, 24, 24));
        add(UIUtils.title("Pengaturan"), BorderLayout.NORTH);

        JPanel card = UIUtils.modernCardPanel();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        card.add(new JLabel("Halaman pengaturan sekolah siap dikembangkan untuk nama sekolah, alamat, kontak, bendahara, dan logo."), gbc);
        add(card, BorderLayout.CENTER);
    }
}
