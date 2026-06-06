// File: util/Option.java
package util;

/** Item id-label untuk JComboBox. */
public class Option {
    private final int id;
    private final String text;

    public Option(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getLabel() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
