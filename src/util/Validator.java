package util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;

public final class Validator {
  private Validator() {}

  public static boolean required(String label, String value) {
    if (value == null || value.trim().isEmpty()) {
      JOptionPane.showMessageDialog(null, label + " wajib diisi.", "Validasi", JOptionPane.WARNING_MESSAGE);
      return false;
    }
    return true;
  }

  public static LocalDate date(String label, String value) {
    if (!required(label, value)) return null;
    try {
      return LocalDate.parse(value.trim());
    } catch (DateTimeParseException ex) {
      JOptionPane.showMessageDialog(null, label + " harus berformat yyyy-MM-dd.", "Validasi", JOptionPane.WARNING_MESSAGE);
      return null;
    }
  }

  public static double money(String label, String value) {
    if (!required(label, value)) return -1;
    try {
      return Double.parseDouble(value.trim().replace(".", "").replace(",", "."));
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(null, label + " harus berupa angka.", "Validasi", JOptionPane.WARNING_MESSAGE);
      return -1;
    }
  }
}
