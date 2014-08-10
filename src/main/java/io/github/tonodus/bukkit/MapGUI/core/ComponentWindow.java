package io.github.tonodus.bukkit.MapGUI.core;


import io.github.tonodus.bukkit.MapGUI.api.Component;
import io.github.tonodus.bukkit.MapGUI.api.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public abstract class ComponentWindow extends InputWindow {
    private Collection<Component> cs;
    private MapGUI gui;

    private Component focused = null;

    private MouseListener checkMouse = new CheckMouse();
    private MouseWheelListener checkScroll = new CheckScroll();
    private TextInputListener checkText = new CheckText();

    public ComponentWindow() {
        this.cs = new ArrayList<Component>();
    }

    public void addComponent(Component component) {
        cs.add(component);
        invalidate();
    }

    public void invalidate() {
        gui.invalidate();
    }

    public void removeComponent(Component component) {
        cs.remove(component);
        gui.invalidate();
    }

    public Component getFocused() {
        return focused;
    }

    private void setFocused(Component on) {
        this.focused.onLostFocus();
        this.focused = on;
        if (this.focused != null)
            this.focused.onFocus();

        this.invalidate();
    }

    @Override
    public void attachedOn(MapGUI gui) {
        super.attachedOn(gui);
        super.addMouseListener(checkMouse);
        super.addScrollListener(checkScroll);
        super.addInputListener(checkText);
    }

    @Override
    public void detachedFrom(MapGUI gui) {
        super.detachedFrom(gui);
        super.removeMouseListener(checkMouse);
        super.removeInputListener(checkText);
        super.removeScrollListener(checkScroll);
    }

    @Override
    public void draw(Graphics canvas) {
        drawBackground(canvas, DefaultMapGui.WIDTH, DefaultMapGui.HEIGHT);

        for (Component c : cs)
            c.draw(canvas);
    }

    private Component get(int x, int y) {
        Component found = null;
        for (Component c : cs) {
            if (x >= c.getX() && x <= c.getX() + c.getWidth() &&
                    y >= c.getY() && y <= c.getY() + c.getHeight())
                found = c;
        }

        return found;
    }

    protected abstract void drawBackground(Graphics g, int width, int height);

    private class CheckMouse implements MouseListener {
        @Override
        public void onLeftClick(int x, int y, boolean withShift) {
            Component found = get(x, y);
            setFocused(null);

            if (found != null)
                found.onLeftClick(x, y, withShift);
        }


        @Override
        public void onRightClick(int x, int y, boolean withShift) {
            Component found;
            if ((found = get(x, y)) != null) found.onRightClick(x, y, withShift);
        }

        @Override
        public void onMove(int oldX, int oldY, int newX, int newY) {
            Component found;
            if ((found = get(newX, newY)) != null) found.onMove(oldX, oldY, newX, newY);
        }
    }

    private class CheckScroll implements MouseWheelListener {
        @Override
        public void onMouseWheel(int amount) {
            if (focused != null)
                focused.onMouseWheel(amount);
        }
    }

    private class CheckText implements TextInputListener {
        @Override
        public void onTextInput(String text) {
            if (focused != null)
                focused.onTextInput(text);
        }
    }
}
