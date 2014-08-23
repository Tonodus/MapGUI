package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.core.BaseComponent;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 23.08.2014.
 */
public class ImageComponent extends BaseComponent {
    private Image image;
    private Mode mode;

    public ImageComponent(Image image) {
        this.image = image;
        setSize(image.getWidth(null), image.getHeight(null));
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public void drawAsync(Graphics2D g) {
        if (mode == Mode.STRETCH)
            g.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
        else
            g.drawImage(image, getX(), getY(), null);
    }

    @Override
    public void updateSync() {

    }

    public enum Mode {
        CUT, STRETCH
    }
}
