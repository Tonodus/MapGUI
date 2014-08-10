package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.*;
import io.github.tonodus.bukkit.MapGUI.api.Window;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public abstract class InputWindow implements Window {
    private DefaultInputController controller;

    public InputWindow() {
        controller = new DefaultInputController();
    }

    @Override
    public abstract void draw(Graphics canvas);

    @Override
    public void attachedOn(MapGUI gui) {
        gui.addInputListener(controller);
        gui.addMouseListener(controller);
        gui.addScrollListener(controller);
    }

    @Override
    public void detachedFrom(MapGUI gui) {
        gui.removeInputListener(controller);
        gui.removeScrollListener(controller);
        gui.removeMouseListener(controller);
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
}
