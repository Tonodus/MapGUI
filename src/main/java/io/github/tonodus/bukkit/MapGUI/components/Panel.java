package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.api.Drawable;
import io.github.tonodus.bukkit.MapGUI.core.BaseComponent;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 11.08.2014.
 */
public class Panel extends BaseComponent {
    private Drawable content;

    public Panel(Drawable drawable) {
        this.content = drawable;
    }

    @Override
    public void drawAsync(Graphics2D g) {
        g.translate(getX(), getY());
        content.drawAsync(g);
        g.translate(-getX(), -getY());
    }

    @Override
    public void updateSync() {
        content.updateSync();
    }
}
