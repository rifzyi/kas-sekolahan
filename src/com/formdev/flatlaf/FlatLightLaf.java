// File: com/formdev/flatlaf/FlatLightLaf.java
package com.formdev.flatlaf;

import javax.swing.plaf.metal.MetalLookAndFeel;

/** Minimal fallback FlatLaf-compatible class so the project compiles without external jars. */
public class FlatLightLaf extends MetalLookAndFeel {
    @Override public String getName() { return "FlatLaf Light"; }
    @Override public String getID() { return "FlatLightLaf"; }
    @Override public String getDescription() { return "Fallback FlatLightLaf implementation"; }
}
