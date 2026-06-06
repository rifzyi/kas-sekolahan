package util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

public final class UIUtils {
  public static final Color SIDEBAR = new Color(0x1E3A8A);
  public static final Color BACKGROUND = new Color(0xF8FAFC);
  public static final Color LIGHT_BLUE = BACKGROUND;
  public static final Color CARD = Color.WHITE;
  public static final Color ACCENT = new Color(0x2563EB);
  public static final Color TEXT = new Color(0x0F172A);
  public static final Color MUTED = new Color(0x64748B);
  public static final Color DANGER = new Color(0xDC2626);
  public static final Font FONT = new Font("Segoe UI", Font.PLAIN, 14);
  public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
  public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
  private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));

  private UIUtils() {}

  public static JLabel title(String text) {
    JLabel label = new JLabel(text);
    label.setFont(FONT_TITLE);
    label.setForeground(TEXT);
    return label;
  }

  public static JButton button(String text, Color background, Color foreground) {
    JButton button = new JButton(text);
    button.setFont(FONT_BOLD);
    button.setForeground(foreground);
    button.setBackground(background);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(9, 14, 9, 14));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    return button;
  }

  public static JButton primaryButton(String text) { return button(text, ACCENT, Color.WHITE); }
  public static JButton dangerButton(String text) { return button(text, DANGER, Color.WHITE); }

  public static JButton secondaryButton(String text) {
    JButton button = button(text, CARD, SIDEBAR);
    button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0xCBD5E1)), BorderFactory.createEmptyBorder(8, 14, 8, 14)));
    return button;
  }

  public static JTextField textField(int columns) {
    JTextField field = new JTextField(columns);
    field.setFont(FONT);
    field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0xCBD5E1)), BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    return field;
  }

  public static JPanel page(String title) {
    JPanel panel = new JPanel(new BorderLayout(14, 14));
    panel.setBackground(BACKGROUND);
    panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
    panel.add(title(title), BorderLayout.NORTH);
    return panel;
  }

  public static JPanel card() {
    JPanel panel = new JPanel();
    panel.setBackground(CARD);
    panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0xE2E8F0)), BorderFactory.createEmptyBorder(16, 16, 16, 16)));
    return panel;
  }

  public static JScrollPane tableScroll(JTable table) {
    table.setFont(FONT);
    table.setRowHeight(30);
    table.setGridColor(new Color(0xE2E8F0));
    table.setSelectionBackground(new Color(0xDBEAFE));
    table.setSelectionForeground(TEXT);
    JTableHeader header = table.getTableHeader();
    header.setFont(FONT_BOLD);
    header.setBackground(SIDEBAR);
    header.setForeground(Color.WHITE);
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    renderer.setHorizontalAlignment(JLabel.CENTER);
    table.setDefaultRenderer(Object.class, renderer);
    JScrollPane scroll = new JScrollPane(table);
    scroll.setPreferredSize(new Dimension(780, 330));
    return scroll;
  }

  public static JPanel toolbar() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
    panel.setOpaque(false);
    return panel;
  }

  public static void bindRealtimeSearch(JTextField field, DefaultTableModel model, JTable table) {
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    table.setRowSorter(sorter);
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

  public static String rupiah(double value) { return CURRENCY.format(value); }

  public static ImageIcon schoolLogo(int size) {
    BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = image.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setColor(SIDEBAR);
    g.fillOval(0, 0, size - 1, size - 1);
    g.setColor(Color.WHITE);
    g.setFont(new Font("Segoe UI", Font.BOLD, Math.max(14, size / 5)));
    String text = "MI";
    int w = g.getFontMetrics().stringWidth(text);
    g.drawString(text, (size - w) / 2, size / 2 + g.getFontMetrics().getAscent() / 3);
    g.dispose();
    return new ImageIcon(image);
  }

  public static class Option {
    private final int id;
    private final String label;
    public Option(int id, String label) { this.id = id; this.label = label; }
    public int getId() { return id; }
    @Override public String toString() { return label; }
  }

  public static File choosePdf(Component parent) {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(new FileNameExtensionFilter("PDF Document", "pdf"));
    if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) return null;
    File file = chooser.getSelectedFile();
    if (!file.getName().toLowerCase().endsWith(".pdf")) file = new File(file.getParentFile(), file.getName() + ".pdf");
    return file;
  }

  public static void exportTableToPdf(Component parent, JTable table, String title, String summary) {
    File file = choosePdf(parent);
    if (file == null) return;
    try {
      writeSimplePdf(file, table, title, summary);
      if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(file);
      JOptionPane.showMessageDialog(parent, "PDF berhasil dibuat: " + file.getAbsolutePath());
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(parent, "Gagal export PDF: " + ex.getMessage(), "Export PDF", JOptionPane.ERROR_MESSAGE);
    }
  }

  private static void writeSimplePdf(File file, JTable table, String title, String summary) throws Exception {
    StringBuilder text = new StringBuilder(title).append("\n").append("Tanggal cetak: ").append(LocalDate.now()).append("\n").append(summary).append("\n\n");
    for (int c = 0; c < table.getColumnCount(); c++) text.append(table.getColumnName(c)).append(c == table.getColumnCount() - 1 ? "\n" : " | ");
    int maxRows = Math.min(table.getRowCount(), 35);
    for (int r = 0; r < maxRows; r++) {
      for (int c = 0; c < table.getColumnCount(); c++) text.append(String.valueOf(table.getValueAt(r, c))).append(c == table.getColumnCount() - 1 ? "\n" : " | ");
    }
    if (table.getRowCount() > maxRows) text.append("... ").append(table.getRowCount() - maxRows).append(" baris lainnya\n");
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

  public static void saveLogo(Component parent, File source, File target) {
    try {
      if (source != null) ImageIO.write(ImageIO.read(source), "png", target);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(parent, "Gagal menyimpan logo: " + ex.getMessage());
    }
  }
}
