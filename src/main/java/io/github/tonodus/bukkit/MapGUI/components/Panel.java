package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.api.Drawable;
import io.github.tonodus.bukkit.MapGUI.core.BaseComponent;
import io.github.tonodus.bukkit.MapGUI.drawable.ColorDrawable;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 11.08.2014.
 */
public class Panel extends BaseComponent {
    private Drawable content;
    private Drawable background = new ColorDrawable(new Color(0, 0, 0, 255));

    public Panel(Drawable content) {
        this.content = content;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public void setBackground(Color color) {
        this.background = new ColorDrawable(color);
    }

    public void setContent(Drawable content) {
        this.content = content;
    }

    @Override
    public void drawAsync(Graphics2D g) {
        g.translate(getX(), getY());
        drawBackgroundAsync(g);
        drawContentAsync(g);
        g.translate(-getX(), -getY());
    }

    protected void drawBackgroundAsync(Graphics2D g) {
        background.drawAsync(g, getWidth(), getHeight());
    }

    protected void drawContentAsync(Graphics2D g) {
        content.drawAsync(g, getWidth(), getHeight());
    }

    @Override
    public void updateSync() {
        content.updateSync();
        background.updateSync();
    }
}
