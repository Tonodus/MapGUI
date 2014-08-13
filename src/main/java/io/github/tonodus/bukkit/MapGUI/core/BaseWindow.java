package io.github.tonodus.bukkit.MapGUI.core;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public class BaseWindow extends ComponentWindow {
    private Color background;

    public BaseWindow() {
        this.background = Color.WHITE;
    }

    public BaseWindow(Color background) {
        this.background = background;
    }

    public void changeBackground(Color newColor) {
        this.background = newColor;
        invalidate();
    }

    /**
     * Override this to draw an image/any other than a color as a background
     */
    @Override
    protected void drawBackground(Graphics2D g, int w, int h) {
        g.setColor(background);
        g.fillRect(0, 0, w, h);
    }
}
