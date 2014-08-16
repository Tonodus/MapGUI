package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Tonodus (http://tonodus.github.io) on 16.08.2014.
 */
public abstract class AbstractComponentsContainer extends BaseComponent implements ComponentsContainer {
    private Collection<Component> components;
    private DefaultInputController controller;

    public AbstractComponentsContainer() {
        components = new ArrayList<Component>();
        controller = new DefaultInputController();
    }

    @Override
    public void addComponent(Component... components) {
        for (Component c : components)
            addComponent(c);
    }

    @Override
    public Iterable<Component> getComponents() {
        return components;
    }

    @Override
    public void addComponent(Component component) {
        components.add(component);
    }

    @Override
    public void removeComponent(Component component) {
        components.remove(component);
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
