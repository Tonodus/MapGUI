package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.Component;
import io.github.tonodus.bukkit.MapGUI.api.*;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public abstract class BaseComponent implements Component {
    protected ComponentsContainer attachedTo = null;
    private int x = 0, y = 0, w = 0, h = 0;
    private DefaultInputController controller;
    private boolean hasFocus = false;
    private boolean mouseWasIn = false;
    private MouseListener checkMouse = new MouseCheck();
    private MouseWheelListener checkWheel = new WheelCheck();
    private TextInputListener checkInput = new InputCheck();

    public BaseComponent() {
        controller = new DefaultInputController();
    }


    @Override
    public void onAttachedTo(ComponentsContainer container) {
        this.attachedTo = container;
        container.addMouseListener(checkMouse);
        container.addInputListener(checkInput);
        container.addScrollListener(checkWheel);
    }

    @Override
    public void onDetachedFrom(ComponentsContainer container) {
        this.attachedTo = null;
        container.removeMouseListener(checkMouse);
        container.removeInputListener(checkInput);
        container.removeScrollListener(checkWheel);
    }

    @Override
    public boolean requestFocus() {
        if (attachedTo instanceof FocusHolder) {
            ((FocusHolder) attachedTo).setFocused(this);
            return true;
        }
        return false;
    }

    /*=============================================================================

    ============================================================================== */
    @Override
    public void invalidate() {
        if (attachedTo != null)
            attachedTo.invalidate();
    }

    @Override
    public void drawAsync(Graphics2D g, int w, int h) {
        drawAsync(g);
    }

    public void setBounds(int x, int y, int w, int h) {
        setPosition(x, y);
        setSize(w, h);
    }

    public void setSize(int w, int h) {
        setWidth(w);
        setHeight(h);
    }

    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public void setWidth(int width) {
        this.w = width;
    }

    @Override
    public int getHeight() {
        return h;
    }

    @Override
    public void setHeight(int height) {
        this.h = height;
    }

    @Override
    public void onLostFocus() {
        hasFocus = false;
    }

    @Override
    public void onFocus() {
        hasFocus = true;
    }

    @Override
    public void addMouseListener(MouseListener listener) {
        controller.addMouseListener(listener);
    }

    @Override
    public void addScrollListener(MouseWheelListener listener) {
        controller.addScrollListener(listener);
    }

    @Override
    public void addInputListener(TextInputListener listener) {
        controller.addInputListener(listener);
    }

    @Override
    public void removeMouseListener(MouseListener listener) {
        controller.removeMouseListener(listener);
    }

    @Override
    public void removeScrollListener(MouseWheelListener listener) {
        controller.removeScrollListener(listener);
    }

    @Override
    public void removeInputListener(TextInputListener listener) {
        controller.removeInputListener(listener);
    }

    public boolean hasFocus() {
        return hasFocus;
    }

    private class MouseCheck implements MouseListener {
        @Override
        public void onLeftClick(int x, int y, boolean withShift) {
            if (attachedTo.getComponentAt(x, y) == BaseComponent.this) {
                requestFocus();
                controller.onLeftClick(x, y, withShift);
            }
        }

        @Override
        public void onRightClick(int x, int y, boolean withShift) {
            if (attachedTo.getComponentAt(x, y) == BaseComponent.this)
                controller.onRightClick(x, y, withShift);
        }

        @Override
        public void onMove(int oldX, int oldY, int newX, int newY) {
            if (attachedTo.getComponentAt(newX, newY) == BaseComponent.this) {
                if (!mouseWasIn) {
                    controller.onMouseEnter(newX, newY);
                    mouseWasIn = true;
                }
                controller.onMove(oldX, oldY, newX, newY);
            } else if (mouseWasIn) {
                controller.onMouseLeave(oldX, oldY);
                mouseWasIn = false;
            }
        }

        @Override
        public void onMouseEnter(int newX, int newY) {
            if (attachedTo.getComponentAt(newX, newY) == BaseComponent.this)
                controller.onMouseEnter(newX, newY);
        }

        @Override
        public void onMouseLeave(int lastX, int lastY) {
            if (attachedTo.getComponentAt(lastX, lastY) == BaseComponent.this)
                controller.onMouseLeave(lastX, lastY);
        }
    }

    private class WheelCheck implements MouseWheelListener {
        @Override
        public void onMouseWheel(int amount) {
            if (hasFocus) controller.onMouseWheel(amount);
        }
    }

    private class InputCheck implements TextInputListener {
        @Override
        public void onTextInput(String text) {
            if (hasFocus) controller.onTextInput(text);
        }
    }
}
