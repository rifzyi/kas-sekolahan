package util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public final class UIUtils {
  public static final Color NAVY = new Color(16, 42, 87);
  public static final Color BLUE = new Color(37, 99, 235);
  public static final Color LIGHT_BLUE = new Color(239, 246, 255);
  public static final Color WHITE = Color.WHITE;
  public static final Color TEXT = new Color(31, 41, 55);
  public static final Color MUTED = new Color(107, 114, 128);
  public static final Font FONT = new Font("Segoe UI", Font.PLAIN, 14);
  public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
  public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
  private static final NumberFormat CURRENCY =
      NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));

  private UIUtils() {}

  public static void applyGlobalFont(Component component) {
    component.setFont(FONT);
  }

  public static JLabel title(String text) {
    JLabel label = new JLabel(text);
    label.setFont(FONT_TITLE);
    label.setForeground(TEXT);
    return label;
  }

  public static JButton primaryButton(String text) {
    JButton button = new JButton(text);
    button.setFont(FONT_BOLD);
    button.setForeground(WHITE);
    button.setBackground(BLUE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(9, 14, 9, 14));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    return button;
  }

  public static JButton secondaryButton(String text) {
    JButton button = new JButton(text);
    button.setFont(FONT_BOLD);
    button.setForeground(NAVY);
    button.setBackground(WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(203, 213, 225)),
        BorderFactory.createEmptyBorder(8, 14, 8, 14)));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    return button;
  }

  public static JPanel page(String title) {
    JPanel panel = new JPanel(new BorderLayout(12, 12));
    panel.setBackground(LIGHT_BLUE);
    panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
    panel.add(title(title), BorderLayout.NORTH);
    return panel;
  }

  public static JPanel card() {
    JPanel panel = new JPanel();
    panel.setBackground(WHITE);
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(226, 232, 240)),
        BorderFactory.createEmptyBorder(14, 14, 14, 14)));
    return panel;
  }

  public static JTextField textField(int columns) {
    JTextField field = new JTextField(columns);
    field.setFont(FONT);
    field.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(203, 213, 225)),
        BorderFactory.createEmptyBorder(7, 9, 7, 9)));
    return field;
  }

  public static JScrollPane tableScroll(JTable table) {
    table.setFont(FONT);
    table.setRowHeight(28);
    table.setGridColor(new Color(226, 232, 240));
    table.setSelectionBackground(new Color(191, 219, 254));
    JTableHeader header = table.getTableHeader();
    header.setFont(FONT_BOLD);
    header.setBackground(NAVY);
    header.setForeground(WHITE);
    DefaultTableCellRenderer center = new DefaultTableCellRenderer();
    center.setHorizontalAlignment(JLabel.CENTER);
    table.setDefaultRenderer(Object.class, center);
    JScrollPane scroll = new JScrollPane(table);
    scroll.setPreferredSize(new Dimension(800, 280));
    return scroll;
  }

  public static String rupiah(double value) { return CURRENCY.format(value); }

  public static LocalDate parseDate(String value) {
    try {
      return LocalDate.parse(value.trim());
    } catch (DateTimeParseException ex) {
      JOptionPane.showMessageDialog(null, "Format tanggal harus yyyy-MM-dd",
                                    "Validasi", JOptionPane.WARNING_MESSAGE);
      return null;
    }
  }

  public static double parseDouble(String value) {
    try {
      return Double.parseDouble(
          value.trim().replace(".", "").replace(",", "."));
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(null, "Nominal harus berupa angka",
                                    "Validasi", JOptionPane.WARNING_MESSAGE);
      return -1;
    }
  }
}
