package net.miourasaki.motdcrape.config;

import java.awt.*;

public class CrapeComponent {
    private String context;
    private Color color;
    private boolean bold;
    private boolean italic;
    private boolean underlined;
    private boolean strikethrough;
    private boolean obfuscated;

    // 构造方法
    public CrapeComponent(
            String context,
            Color color,
            boolean bold,
            boolean italic,
            boolean underlined,
            boolean strikethrough,
            boolean obfuscated
    ) {
        this.setContext(context);
        this.setColor(color);
        this.setBold(bold);
        this.setItalic(italic);
        this.setUnderlined(underlined);
        this.setStrikethrough(strikethrough);
        this.setObfuscated(obfuscated);
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }

    public boolean isStrikethrough() {
        return strikethrough;
    }

    public void setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
    }

    public boolean isObfuscated() {
        return obfuscated;
    }

    public void setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
    }
}
