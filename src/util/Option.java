// File: util/Option.java
package util;

/** Helper item JComboBox yang menyimpan id database dan teks tampilan. */
public class Option {
    private final int id;
    private final String text;

    public Option(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() { return id; }
    public String getText() { return text; }
    @Override public String toString() { return text; }
}
