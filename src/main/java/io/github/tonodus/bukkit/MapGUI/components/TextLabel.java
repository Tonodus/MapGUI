package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.core.BaseComponent;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Tonodus (http://tonodus.github.io) on 11.08.2014.
 */
public class TextLabel extends BaseComponent {
    private String text;
    private Font font;
    private boolean hasCalc = false;

    public TextLabel() {
        font = new Font(Font.SERIF, Font.PLAIN, 10);
    }

    public TextLabel(String text) {
        this();
        this.text = text;
    }

    public TextLabel calcPreferredSizeNextFrame() {
        hasCalc = true;
        return this;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public void drawAsync(Graphics2D g) {
        Rectangle2D r = font.getStringBounds(text, g.getFontRenderContext());
        if (hasCalc) {
            setWidth((int) (r.getWidth() + 0.5));
            setHeight((int) (r.getHeight() + 0.5));
            hasCalc = false;
            invalidate();
        }

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(text, getX(), (int) (getY() + r.getHeight() + 0.5));
    }

    @Override
    public void updateSync() {

    }
}