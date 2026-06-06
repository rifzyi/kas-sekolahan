package util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UIUtils {
  public static final Color NAVY = new Color(0x0D47A1);
  public static final Color BLUE = new Color(0x1565C0);
  public static final Color CYAN = new Color(0x00BCD4);
  public static final Color GREEN = new Color(0x2ECC71);
  public static final Color ORANGE = new Color(0xF39C12);
  public static final Color RED = new Color(0xE74C3C);
  public static final Color WHITE = Color.WHITE;
  public static final Color BACKGROUND = new Color(0xF5F7FA);
  public static final Color CARD = Color.WHITE;
  public static final Color MUTED = new Color(0x64748B);
  public static final Font FONT = new Font("Segoe UI", Font.PLAIN, 14);
  public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
  public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 20);
  private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

  private UIUtils() {}

  public static JPanel page(String title) {
    JPanel panel = new JPanel(new BorderLayout(0, 18));
    panel.setBackground(BACKGROUND);
    panel.setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));
    JLabel label = new JLabel(title);
    label.setFont(new Font("Segoe UI", Font.BOLD, 24));
    label.setForeground(NAVY);
    panel.add(label, BorderLayout.NORTH);
    return panel;
  }

  public static JPanel card() {
    JPanel panel = new JPanel();
    panel.setBackground(CARD);
    panel.setBorder(new ShadowBorder());
    return panel;
  }

  public static JTextField textField(int columns) {
    JTextField field = new JTextField(columns);
    field.setFont(FONT);
    field.setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(new Color(0xD9E2EC), 12), BorderFactory.createEmptyBorder(8, 12, 8, 12)));
    field.setPreferredSize(new Dimension(field.getPreferredSize().width, 38));
    return field;
  }

  public static JButton button(String text, Color color) {
    JButton button = new JButton(text);
    button.setFont(FONT_BOLD);
    button.setForeground(WHITE);
    button.setBackground(color);
    button.setOpaque(true);
    button.setFocusPainted(false);
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    button.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
    return button;
  }

  public static JLabel formLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(FONT_BOLD);
    label.setForeground(new Color(0x334155));
    return label;
  }

  public static JScrollPane tableScroll(JTable table) {
    table.setFont(FONT);
    table.setRowHeight(32);
    table.setGridColor(new Color(0xE5E7EB));
    table.setSelectionBackground(new Color(0xBBDEFB));
    table.setSelectionForeground(new Color(0x0F172A));
    table.setShowVerticalLines(false);
    JTableHeader header = table.getTableHeader();
    header.setBackground(BLUE);
    header.setForeground(WHITE);
    header.setFont(FONT_BOLD);
    header.setPreferredSize(new Dimension(header.getPreferredSize().width, 38));
    DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
    renderer.setHorizontalAlignment(SwingConstants.LEFT);
    JScrollPane scroll = new JScrollPane(table);
    scroll.setBorder(BorderFactory.createLineBorder(new Color(0xE2E8F0)));
    scroll.getViewport().setBackground(WHITE);
    return scroll;
  }

  public static JPanel toolbar(JTextField search, JButton... buttons) {
    JPanel panel = new JPanel(new BorderLayout(12, 0));
    panel.setOpaque(false);
    JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
    left.setOpaque(false);
    for (JButton button : buttons) left.add(button);
    JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
    right.setOpaque(false);
    right.add(new JLabel("Cari Data:"));
    right.add(search);
    panel.add(left, BorderLayout.WEST);
    panel.add(right, BorderLayout.EAST);
    return panel;
  }

  public static void bindSearch(JTextField field, TableRowSorter<?> sorter) {
    field.getDocument().addDocumentListener(new DocumentListener() {
      private void filter() {
        String text = field.getText().trim();
        sorter.setRowFilter(text.isEmpty() ? null : RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(text)));
      }
      public void insertUpdate(DocumentEvent e) { filter(); }
      public void removeUpdate(DocumentEvent e) { filter(); }
      public void changedUpdate(DocumentEvent e) { filter(); }
    });
  }

  public static void setField(JTextComponent field, String value) { field.setText(value == null ? "" : value); }
  public static String rupiah(double value) { return CURRENCY.format(value); }

  public static ImageIcon schoolLogo(int size) {
    BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = image.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setColor(WHITE);
    g.fillOval(0, 0, size - 1, size - 1);
    g.setColor(NAVY);
    g.setFont(new Font("Segoe UI", Font.BOLD, Math.max(14, size / 3)));
    String text = "MI";
    int w = g.getFontMetrics().stringWidth(text);
    g.drawString(text, (size - w) / 2, size / 2 + g.getFontMetrics().getAscent() / 3 - 2);
    g.dispose();
    return new ImageIcon(image);
  }

  public static void installFlatLafIfAvailable() {
    try {
      Class<?> laf = Class.forName("com.formdev.flatlaf.FlatLightLaf");
      laf.getMethod("setup").invoke(null);
    } catch (Exception ignored) {}
  }

  public static class Option {
    private final int id;
    private final String label;
    public Option(int id, String label) { this.id = id; this.label = label; }
    public int getId() { return id; }
    public String getLabel() { return label; }
    @Override public String toString() { return label; }
  }

  public static File choosePdf(Component parent) {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(new FileNameExtensionFilter("PDF Document", "pdf"));
    if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) return null;
    File file = chooser.getSelectedFile();
    return file.getName().toLowerCase().endsWith(".pdf") ? file : new File(file.getParentFile(), file.getName() + ".pdf");
  }

  public static void exportTableToPdf(Component parent, JTable table, String title, String summary) {
    File file = choosePdf(parent);
    if (file == null) return;
    try {
      writeSimplePdf(file, table, title, summary);
      if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(file);
      JOptionPane.showMessageDialog(parent, "PDF berhasil dibuat: " + file.getAbsolutePath());
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(parent, "Gagal cetak PDF: " + ex.getMessage(), "Cetak PDF", JOptionPane.ERROR_MESSAGE);
    }
  }

  private static void writeSimplePdf(File file, JTable table, String title, String summary) throws Exception {
    StringBuilder text = new StringBuilder(title).append("\nTanggal cetak: ").append(LocalDate.now()).append("\n").append(summary).append("\n\n");
    for (int c = 0; c < table.getColumnCount(); c++) text.append(table.getColumnName(c)).append(c == table.getColumnCount() - 1 ? "\n" : " | ");
    for (int r = 0; r < Math.min(table.getRowCount(), 40); r++) {
      for (int c = 0; c < table.getColumnCount(); c++) text.append(table.getValueAt(r, c)).append(c == table.getColumnCount() - 1 ? "\n" : " | ");
    }
    String[] lines = text.toString().split("\n");
    StringBuilder stream = new StringBuilder("BT /F1 10 Tf 40 790 Td 12 TL\n");
    for (String line : lines) stream.append("(").append(escapePdf(line.length() > 115 ? line.substring(0, 115) : line)).append(") Tj T*\n");
    stream.append("ET");
    byte[] streamBytes = stream.toString().getBytes(StandardCharsets.ISO_8859_1);
    ByteArrayOutputStream pdf = new ByteArrayOutputStream();
    int[] offset = new int[6];
    pdf.write("%PDF-1.4\n".getBytes(StandardCharsets.ISO_8859_1));
    offset[1] = pdf.size(); pdf.write("1 0 obj << /Type /Catalog /Pages 2 0 R >> endobj\n".getBytes(StandardCharsets.ISO_8859_1));
    offset[2] = pdf.size(); pdf.write("2 0 obj << /Type /Pages /Kids [3 0 R] /Count 1 >> endobj\n".getBytes(StandardCharsets.ISO_8859_1));
    offset[3] = pdf.size(); pdf.write("3 0 obj << /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] /Resources << /Font << /F1 5 0 R >> >> /Contents 4 0 R >> endobj\n".getBytes(StandardCharsets.ISO_8859_1));
    offset[4] = pdf.size(); pdf.write(("4 0 obj << /Length " + streamBytes.length + " >> stream\n").getBytes(StandardCharsets.ISO_8859_1)); pdf.write(streamBytes); pdf.write("\nendstream endobj\n".getBytes(StandardCharsets.ISO_8859_1));
    offset[5] = pdf.size(); pdf.write("5 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> endobj\n".getBytes(StandardCharsets.ISO_8859_1));
    int xref = pdf.size(); pdf.write("xref\n0 6\n0000000000 65535 f \n".getBytes(StandardCharsets.ISO_8859_1));
    for (int i = 1; i <= 5; i++) pdf.write(String.format("%010d 00000 n \n", offset[i]).getBytes(StandardCharsets.ISO_8859_1));
    pdf.write(("trailer << /Size 6 /Root 1 0 R >>\nstartxref\n" + xref + "\n%%EOF").getBytes(StandardCharsets.ISO_8859_1));
    try (FileOutputStream out = new FileOutputStream(file)) { pdf.writeTo(out); }
  }

  private static String escapePdf(String text) { return text.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)"); }

  private static class RoundedBorder extends AbstractBorder {
    private final Color color;
    private final int radius;
    RoundedBorder(Color color, int radius) { this.color = color; this.radius = radius; }
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(color);
      g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
      g2.dispose();
    }
  }

  private static class ShadowBorder extends AbstractBorder {
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(new Color(0, 0, 0, 18));
      g2.fillRoundRect(x + 3, y + 4, width - 6, height - 7, 22, 22);
      g2.setColor(CARD);
      g2.fillRoundRect(x, y, width - 7, height - 8, 22, 22);
      g2.dispose();
    }
    public java.awt.Insets getBorderInsets(Component c) { return new java.awt.Insets(16, 16, 18, 18); }
    public java.awt.Insets getBorderInsets(Component c, java.awt.Insets insets) { insets.set(16, 16, 18, 18); return insets; }
  }
}
