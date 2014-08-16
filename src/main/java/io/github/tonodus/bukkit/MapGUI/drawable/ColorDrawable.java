package io.github.tonodus.bukkit.MapGUI.drawable;

import io.github.tonodus.bukkit.MapGUI.api.Drawable;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 15.08.2014.
 */
public class ColorDrawable implements Drawable {
    private final Color color;

    public ColorDrawable(Color color) {
        this.color = color;
    }

    @Override
    public void updateSync() {

    }

    @Override
    public void drawAsync(Graphics2D canvas, int width, int height) {
        canvas.setColor(color);
        canvas.fillRect(0, 0, width, height);
    }
}
