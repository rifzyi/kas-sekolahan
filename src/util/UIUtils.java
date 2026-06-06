// File: util/UIUtils.java
package util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.text.JTextComponent;

public final class UIUtils {
    public static final Color PRIMARY = new Color(0x2563EB);
    public static final Color SUCCESS = new Color(0x10B981);
    public static final Color DANGER = new Color(0xEF4444);
    public static final Color BACKGROUND = new Color(0xF8FAFC);
    public static final Color SURFACE = Color.WHITE;
    public static final Color TEXT = new Color(0x111827);
    public static final Color MUTED = new Color(0x6B7280);
    public static final Font FONT = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_BOLD = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 22);
    private static final NumberFormat RUPIAH = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));

    private UIUtils() {}

    public static String formatRupiah(double value) {
        return RUPIAH.format(value).replace(",00", "");
    }

    public static double parseRupiah(String value) {
        if (value == null || value.trim().isEmpty()) return 0D;
        String cleaned = value.replace("Rp", "").replace(".", "").replace(",", ".").replaceAll("[^0-9.-]", "").trim();
        if (cleaned.isEmpty()) return 0D;
        try { return Double.parseDouble(cleaned); } catch (NumberFormatException ex) {
            try { return RUPIAH.parse(value).doubleValue(); } catch (ParseException ignored) { return 0D; }
        }
    }

    public static JButton createModernButton(String text, Color color) {
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

    public static JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(FONT);
        field.putClientProperty("JTextField.placeholderText", placeholder);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0xCBD5E1)), new EmptyBorder(8, 10, 8, 10)));
        return field;
    }

    public static void applyGlobalStyle() {
        UIManager.put("defaultFont", FONT);
        UIManager.put("Button.arc", 12);
        UIManager.put("Component.arc", 12);
        UIManager.put("TextComponent.arc", 12);
    }

    public static JPanel page(String title) {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(title(title), BorderLayout.NORTH);
        return panel;
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

    public static <T> JComboBox<T> modernComboBox() {
        JComboBox<T> combo = new JComboBox<>();
        combo.setFont(FONT);
        combo.setBackground(SURFACE);
        return combo;
    }

    public static JTextArea modernTextArea(String placeholder) {
        JTextArea area = new JTextArea(3, 25);
        area.setFont(FONT);
        area.putClientProperty("JTextField.placeholderText", placeholder);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0xCBD5E1)), new EmptyBorder(8, 10, 8, 10)));
        return area;
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
        table.setShowVerticalLines(false);
        table.setRowHeight(30);
        table.setGridColor(new Color(0xE5E7EB));
        table.setSelectionBackground(new Color(0xDBEAFE));
        table.setSelectionForeground(TEXT);
        table.setIntercellSpacing(new Dimension(0, 1));
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(new Color(0xEFF6FF));
        header.setForeground(TEXT);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 36));
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                Component comp = super.getTableCellRendererComponent(t, v, s, f, r, c);
                if (!s) comp.setBackground(r % 2 == 0 ? Color.WHITE : new Color(0xF8FAFC));
                return comp;
            }
        });
    }

    public static JPanel card() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(SURFACE);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0xE5E7EB)), new EmptyBorder(15, 15, 15, 15)));
        return panel;
    }

    public static void showError(Component parent, Exception ex) {
        JOptionPane.showMessageDialog(parent, ex.getMessage(), "Terjadi Kesalahan", JOptionPane.ERROR_MESSAGE);
    }

    public static void setField(JTextComponent field, String value) { field.setText(value == null ? "" : value); }
    public static JButton modernButton(String text, Color color) { return createModernButton(text, color); }
    public static JTextField modernTextField(String placeholder) { return createModernTextField(placeholder); }
    public static String rupiah(double value) { return formatRupiah(value); }
}
