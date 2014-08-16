package io.github.tonodus.bukkit.MapGUI.api;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public interface Drawable {
    public void updateSync();

    public void drawAsync(Graphics2D canvas, int width, int height);
}
