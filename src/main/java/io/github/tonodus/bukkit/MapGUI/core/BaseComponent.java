package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.Component;
import io.github.tonodus.bukkit.MapGUI.api.MouseListener;
import io.github.tonodus.bukkit.MapGUI.api.MouseWheelListener;
import io.github.tonodus.bukkit.MapGUI.api.TextInputListener;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public abstract class BaseComponent implements Component {
    private int x, y, w, h;
    private DefaultInputController controller;
    private boolean hasFocus = false;
    private int height;
    private int width;

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

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
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

    @Override
    public void onLeftClick(int x, int y, boolean withShift) {
        controller.onLeftClick(x, y, withShift);
    }

    @Override
    public void onRightClick(int x, int y, boolean withShift) {
        controller.onRightClick(x, y, withShift);
    }

    @Override
    public void onMove(int oldX, int oldY, int newX, int newY) {
        controller.onMove(oldX, oldY, newX, newY);
    }

    @Override
    public void onMouseWheel(int amount) {
        controller.onMouseWheel(amount);
    }

    @Override
    public void onTextInput(String text) {
        controller.onTextInput(text);
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
