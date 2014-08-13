package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.core.BaseComponent;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 11.08.2014.
 */
public class Rect extends BaseComponent {
    @Override
    public void drawAsync(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void updateSync() {

    }
}
