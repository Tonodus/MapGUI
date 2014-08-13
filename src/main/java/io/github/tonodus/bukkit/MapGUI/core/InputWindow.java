package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public abstract class InputWindow implements Window {
    private DefaultInputController<MapGUI, Window> controller;

    public InputWindow() {
        controller = new DefaultInputController<MapGUI, Window>() {

            @Override
            protected Window convert(MapGUI input) {
                return InputWindow.this;
            }
        };
    }

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
    public void addMouseListener(MouseListener<Window> listener) {
        controller.addMouseListener(listener);
    }

    @Override
    public void addScrollListener(MouseWheelListener<Window> listener) {
        controller.addScrollListener(listener);
    }

    @Override
    public void addInputListener(TextInputListener<Window> listener) {
        controller.addInputListener(listener);
    }

    @Override
    public void removeMouseListener(MouseListener<Window> listener) {
        controller.removeMouseListener(listener);
    }

    @Override
    public void removeScrollListener(MouseWheelListener<Window> listener) {
        controller.removeScrollListener(listener);
    }

    @Override
    public void removeInputListener(TextInputListener listener) {
        controller.removeInputListener(listener);
    }
}
