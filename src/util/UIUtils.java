// File: util/UIUtils.java
package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.text.JTextComponent;

/** Utilitas styling modern untuk Swing + fallback jika FlatLaf tidak tersedia. */
public final class UIUtils {
    public static final Color BACKGROUND = new Color(0xF3F4F6);
    public static final Color SURFACE = Color.WHITE;
    public static final Color PRIMARY = new Color(0x2563EB);
    public static final Color SUCCESS = new Color(0x10B981);
    public static final Color DANGER = new Color(0xEF4444);
    public static final Color WARNING = new Color(0xF59E0B);
    public static final Color TEXT = new Color(0x111827);
    public static final Color MUTED = new Color(0x6B7280);
    public static final Color BORDER = new Color(0xD1D5DB);
    public static final Color SIDEBAR = new Color(0x111827);
    public static final Font FONT = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 22);
    private static final NumberFormat RUPIAH = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    private UIUtils() {
    }

    public static void installFlatLafIfAvailable() {
        try {
            Class<?> flatLight = Class.forName("com.formdev.flatlaf.FlatLightLaf");
            flatLight.getMethod("setup").invoke(null);
        } catch (ReflectiveOperationException ex) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Fallback terakhir: gunakan Look and Feel default Swing.
            }
        }
    }

    public static void applyGlobalStyle() {
        UIManager.put("defaultFont", FONT);
        UIManager.put("Table.font", FONT);
        UIManager.put("TableHeader.font", FONT_BOLD);
        UIManager.put("Button.font", FONT_BOLD);
        UIManager.put("TextField.font", FONT);
        UIManager.put("ComboBox.font", FONT);
        UIManager.put("Label.font", FONT);
    }

    public static JButton modernButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(FONT_BOLD);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
        return button;
    }

    public static JTextField modernTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(FONT);
        field.putClientProperty("JTextField.placeholderText", placeholder);
        field.setBorder(BorderFactory.createCompoundBorder(new SoftBorder(BORDER, 12), new EmptyBorder(8, 12, 8, 12)));
        field.setPreferredSize(new Dimension(180, 40));
        return field;
    }

    public static JTextArea modernTextArea(String placeholder) {
        JTextArea area = new JTextArea(3, 20);
        area.setFont(FONT);
        area.putClientProperty("JTextField.placeholderText", placeholder);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(new SoftBorder(BORDER, 12), new EmptyBorder(8, 12, 8, 12)));
        return area;
    }

    public static <T> JComboBox<T> modernComboBox() {
        JComboBox<T> combo = new JComboBox<>();
        combo.setFont(FONT);
        combo.setBackground(SURFACE);
        combo.setBorder(BorderFactory.createCompoundBorder(new SoftBorder(BORDER, 12), new EmptyBorder(4, 8, 4, 8)));
        combo.setPreferredSize(new Dimension(180, 40));
        return combo;
    }

    public static JPanel modernCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(SURFACE);
        panel.setBorder(BorderFactory.createCompoundBorder(new SoftBorder(new Color(0xE5E7EB), 18), new EmptyBorder(16, 16, 16, 16)));
        return panel;
    }


    public static JPanel page(String titleText) {
        JPanel panel = new JPanel(new java.awt.BorderLayout(0, 18));
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));
        panel.add(title(titleText), java.awt.BorderLayout.NORTH);
        return panel;
    }

    public static JLabel formLabel(String text) {
        return label(text);
    }

    public static JPanel toolbar(JTextField search, JButton... buttons) {
        JPanel panel = new JPanel(new java.awt.BorderLayout(12, 0));
        panel.setOpaque(false);
        JPanel left = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        left.setOpaque(false);
        for (JButton button : buttons) left.add(button);
        JPanel right = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));
        right.setOpaque(false);
        right.add(new JLabel("Cari Data:"));
        right.add(search);
        panel.add(left, java.awt.BorderLayout.WEST);
        panel.add(right, java.awt.BorderLayout.EAST);
        return panel;
    }

    public static void bindSearch(JTextField field, javax.swing.table.TableRowSorter<?> sorter) {
        field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void filter() {
                String text = field.getText().trim();
                sorter.setRowFilter(text.isEmpty() ? null : javax.swing.RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(text)));
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
        });
    }

    public static String rupiah(double value) {
        return formatRupiah(value);
    }

    public static void exportTableToPdf(Component parent, JTable table, String title, String summary) {
        javax.swing.JOptionPane.showMessageDialog(parent, "Fitur cetak PDF tidak tersedia pada UIUtils modern ini.\n" + title + "\n" + summary, "Cetak", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public static String formatRupiah(double value) {
        return RUPIAH.format(value).replace(",00", "");
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_TITLE);
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_BOLD);
        label.setForeground(TEXT);
        return label;
    }

    public static JScrollPane modernScrollPane(JTable table) {
        styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0xE5E7EB)));
        scrollPane.getViewport().setBackground(SURFACE);
        return scrollPane;
    }

    public static void styleTable(JTable table) {
        table.setFont(FONT);
        table.setRowHeight(34);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(0xE5E7EB));
        table.setSelectionBackground(new Color(0xDBEAFE));
        table.setSelectionForeground(TEXT);
        table.setIntercellSpacing(new Dimension(0, 1));
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(new Color(0xEFF6FF));
        header.setForeground(TEXT);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 38));
        table.setDefaultRenderer(Object.class, new StripedRenderer());
    }

    public static void setPadding(JComponent component) {
        component.setBorder(BorderFactory.createCompoundBorder(component.getBorder(), new EmptyBorder(10, 10, 10, 10)));
    }

    public static void setField(JTextComponent field, String value) {
        field.setText(value == null ? "" : value);
    }

    // Alias kompatibilitas untuk form lama di repo.
    public static JButton button(String text, Color color) { return modernButton(text, color); }
    public static JTextField textField(int columns) { JTextField f = modernTextField(""); f.setColumns(columns); return f; }
    public static JPanel card() { return modernCardPanel(); }
    public static JScrollPane tableScroll(JTable table) { return modernScrollPane(table); }
    public static final Color BLUE = PRIMARY;
    public static final Color GREEN = SUCCESS;
    public static final Color RED = DANGER;
    public static final Color ORANGE = WARNING;
    public static final Color NAVY = SIDEBAR;
    public static final Color WHITE = Color.WHITE;
    public static final Color CARD = SURFACE;
    public static final Color CYAN = new Color(0x06B6D4);


    public static javax.swing.ImageIcon schoolLogo(int size) {
        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(PRIMARY);
        g2.fillOval(0, 0, size - 1, size - 1);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(14, size / 3)));
        String text = "KS";
        int width = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, (size - width) / 2, size / 2 + g2.getFontMetrics().getAscent() / 3 - 2);
        g2.dispose();
        return new javax.swing.ImageIcon(image);
    }

    /** Kompatibilitas untuk kode lama yang memakai util.UIUtils.Option. */
    public static class Option extends util.Option {
        public Option(int id, String label) {
            super(id, label);
        }
    }

    private static class StripedRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!isSelected) {
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(0xF9FAFB));
                c.setForeground(TEXT);
            }
            setBorder(new EmptyBorder(0, 10, 0, 10));
            return c;
        }
    }

    private static class SoftBorder extends AbstractBorder {
        private final Color color;
        private final int radius;

        SoftBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(1, 1, 1, 1);
        }
    }
}
