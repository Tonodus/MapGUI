package io.github.tonodus.bukkit.MapGUI.core;


import io.github.tonodus.bukkit.MapGUI.api.Component;
import io.github.tonodus.bukkit.MapGUI.api.ComponentsContainer;
import io.github.tonodus.bukkit.MapGUI.api.FocusHolder;
import io.github.tonodus.bukkit.MapGUI.api.MapGUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public abstract class ComponentWindow extends InputWindow implements ComponentsContainer, FocusHolder {
    private Collection<Component> cs;
    private MapGUI gui;

    private Component focused = null;

    public ComponentWindow() {
        this.cs = new ArrayList<Component>();
    }

    public Iterable<Component> getComponents() {
        return cs;
    }

    public void addComponent(Component... components) {
        for (Component c : components)
            addComponent(c);
    }

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

    public void removeComponent(Component component) {
        component.onDetachedFrom(this);
        cs.remove(component);
        gui.invalidate();
    }

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
    public void drawAsync(Graphics2D g, int w, int h) {
        drawAsync(g);
    }

    @Override
    public void drawAsync(Graphics2D canvas) {
        drawBackground(canvas, DefaultMapGui.WIDTH, DefaultMapGui.HEIGHT);

        for (Component c : cs) {
            Shape s = canvas.getClip();
            canvas.clipRect(c.getX(), c.getY(), c.getWidth(), c.getHeight());
            c.drawAsync(canvas);
            canvas.setClip(s);
        }
    }


    protected abstract void drawBackground(Graphics2D g, int width, int height);
}
