package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.Component;
import io.github.tonodus.bukkit.MapGUI.api.ComponentsContainer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Tonodus (http://tonodus.github.io) on 16.08.2014.
 */
public abstract class AbstractComponentsContainer extends BaseComponent implements ComponentsContainer {
    private Collection<Component> components;

    public AbstractComponentsContainer() {
        components = new ArrayList<Component>();
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
        component.onAttachedTo(this);
        components.add(component);
    }

    @Override
    public void removeComponent(Component component) {
        component.onDetachedFrom(this);
        components.remove(component);
    }
}
