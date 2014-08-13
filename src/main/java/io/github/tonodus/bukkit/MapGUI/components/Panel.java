package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.api.Drawable;
import io.github.tonodus.bukkit.MapGUI.core.BaseComponent;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 11.08.2014.
 */
public class Panel extends BaseComponent {
    private Drawable content;
    private Color background = new Color(0, 0, 0, 255);


    public Panel(Drawable drawable) {
        this.content = drawable;
    }

    public void setBackground(Color color) {
        this.background = color;
    }

    @Override
    public void drawAsync(Graphics2D g) {
        g.translate(getX(), getY());
        drawBgAsync(g);
        drawCAsync(g);
        g.translate(-getX(), -getY());
    }

    protected void drawBgAsync(Graphics2D g) {
        g.setColor(background);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    protected void drawCAsync(Graphics2D g) {
        content.drawAsync(g);
    }

    @Override
    public void updateSync() {
        content.updateSync();
    }
}
