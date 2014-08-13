package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public abstract class BaseComponent implements Component {
    protected Window attachedTo = null;
    private int x = 0, y = 0, w = 0, h = 0;
    private DefaultInputController<Window, Component> controller;
    private boolean hasFocus = false;
    private boolean mouseWasIn = false;
    private MouseListener<Window> checkMouse = new MouseCheck();
    private MouseWheelListener<Window> checkWheel = new WheelCheck();
    private TextInputListener<Window> checkInput = new InputCheck();

    public BaseComponent() {
        controller = new DefaultInputController<Window, Component>() {
            @Override
            protected Component convert(Window input) {
                return BaseComponent.this;
            }
        };
    }

    @Override
    public void invalidate() {
        attachedTo.invalidate();
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
    public void onAttachedTo(Window window) {
        this.attachedTo = window;
        window.addMouseListener(checkMouse);
        window.addInputListener(checkInput);
        window.addScrollListener(checkWheel);
    }

    @Override
    public void onDetachedFrom(Window window) {
        this.attachedTo = null;
        window.removeMouseListener(checkMouse);
        window.removeInputListener(checkInput);
        window.removeScrollListener(checkWheel);
    }

    private boolean is(int x, int y) {
        if (x >= getX() && x <= getX() + getWidth() &&
                y >= getY() && y <= getY() + getHeight())
            return true;
        return false;
    }

    @Override
    public boolean requestFocus() {
        if (attachedTo instanceof FocusWindow) {
            ((FocusWindow) attachedTo).setFocused(this);
            return true;
        }
        return false;
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
    public void addMouseListener(MouseListener<Component> listener) {
        controller.addMouseListener(listener);
    }

    @Override
    public void addScrollListener(MouseWheelListener<Component> listener) {
        controller.addScrollListener(listener);
    }

    @Override
    public void addInputListener(TextInputListener<Component> listener) {
        controller.addInputListener(listener);
    }

    @Override
    public void removeMouseListener(MouseListener<Component> listener) {
        controller.removeMouseListener(listener);
    }

    @Override
    public void removeScrollListener(MouseWheelListener<Component> listener) {
        controller.removeScrollListener(listener);
    }

    @Override
    public void removeInputListener(TextInputListener<Component> listener) {
        controller.removeInputListener(listener);
    }

    private class MouseCheck implements MouseListener<Window> {
        @Override
        public void onLeftClick(int x, int y, boolean withShift, Window owner) {
            if (is(x, y)) {
                requestFocus();
                controller.onLeftClick(x, y, withShift, owner);
            }
        }

        @Override
        public void onRightClick(int x, int y, boolean withShift, Window owner) {
            if (is(x, y))
                controller.onRightClick(x, y, withShift, owner);
        }

        @Override
        public void onMove(int oldX, int oldY, int newX, int newY, Window owner) {
            if (is(newX, newY)) {
                if (!mouseWasIn) {
                    controller.onMouseEnter(newX, newY, owner);
                    mouseWasIn = true;
                }
                controller.onMove(oldX, oldY, newX, newY, owner);
            } else if (mouseWasIn) {
                controller.onMouseLeave(oldX, oldY, owner);
                mouseWasIn = false;
            }
        }

        //[[ Never called in window
        @Override
        public void onMouseEnter(int newX, int newY, Window owner) {
            controller.onMouseEnter(newX, newY, owner);
        }

        @Override
        public void onMouseLeave(int lastX, int lastY, Window owner) {
            controller.onMouseLeave(lastX, lastY, owner);
        }
        // ]]
    }

    private class WheelCheck implements MouseWheelListener<Window> {
        @Override
        public void onMouseWheel(int amount, Window owner) {
            controller.onMouseWheel(amount, owner);
        }
    }

    private class InputCheck implements TextInputListener<Window> {
        @Override
        public void onTextInput(String text, Window owner) {
            controller.onTextInput(text, owner);
        }
    }
}
