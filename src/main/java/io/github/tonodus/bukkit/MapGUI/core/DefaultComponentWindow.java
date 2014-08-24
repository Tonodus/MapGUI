package io.github.tonodus.bukkit.MapGUI.core;


import io.github.tonodus.bukkit.MapGUI.api.Component;
import io.github.tonodus.bukkit.MapGUI.api.ComponentWindow;
import io.github.tonodus.bukkit.MapGUI.api.MapGUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public abstract class DefaultComponentWindow extends InputWindow implements ComponentWindow {
    private List<Component> cs;
    private MapGUI gui;

    private Component focused = null;

    public DefaultComponentWindow() {
        this.cs = new ArrayList<Component>();
    }

    public Iterable<Component> getComponents() {
        return cs;
    }

    @Override
    public void addComponent(Component... components) {
        for (Component c : components)
            addComponent(c);
    }

    @Override
    public void addComponent(Component component) {
        component.onAttachedTo(this);
        cs.add(component);
        invalidate();
    }

    @Override
    public void attachedOn(MapGUI gui) {
        super.attachedOn(gui);
        this.gui = gui;
    }

    @Override
    public void detachedFrom(MapGUI gui) {
        super.detachedFrom(gui);
        this.gui = gui;
    }

    @Override
    public void invalidate() {
        if (gui != null)
            gui.invalidate();
    }

    @Override
    public void removeComponent(Component component) {
        component.onDetachedFrom(this);
        cs.remove(component);
        gui.invalidate();
    }

    @Override
    public Component getFocused() {
        return focused;
    }

    @Override
    public void setFocused(Component on) {
        if (this.focused != null)
            this.focused.onLostFocus();
        this.focused = on;
        if (this.focused != null)
            this.focused.onFocus();

        this.invalidate();
    }

    @Override
    public void updateSync() {
        for (Component c : cs)
            c.updateSync();
    }

    @Override
    public boolean hasComponent(Component c) {
        return cs.indexOf(c) != -1;
    }

    @Override
    public void drawAsync(Graphics2D g, int w, int h) {
        drawAsync(g);
    }

    @Override
    public void drawAsync(Graphics2D canvas) {
        drawBackground(canvas, DefaultPlayerMapGUI.WIDTH, DefaultPlayerMapGUI.HEIGHT);

        for (Component c : cs) {
            Shape s = canvas.getClip();
            canvas.setClip(c.getX(), c.getY(), c.getWidth(), c.getHeight());
            c.drawAsync(canvas);
            canvas.setClip(s);
        }
    }

    @Override
    public io.github.tonodus.bukkit.MapGUI.api.Component getComponentAt(int x, int y) {
        for (int i = cs.size() - 1; i >= 0; i--) {
            io.github.tonodus.bukkit.MapGUI.api.Component c = cs.get(i);
            if (x >= c.getX() && x <= c.getX() + c.getWidth() &&
                    y >= c.getY() && y <= c.getY() + c.getHeight())
                return c;
        }


        return null;
    }

    protected abstract void drawBackground(Graphics2D g, int width, int height);
}
