package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.api.Component;
import io.github.tonodus.bukkit.MapGUI.api.ComponentsContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tonodus (http://tonodus.github.io) on 16.08.2014.
 */
public abstract class AbstractComponentsContainer<C extends Component> extends BaseComponent implements ComponentsContainer<C> {
    private final List<C> components;
    private C focused = null;

    public AbstractComponentsContainer() {
        components = new ArrayList<C>();
    }

    @Override
    public void addComponent(C... components) {
        for (C c : components)
            addComponent(c);
    }

    @Override
    public Iterable<C> getComponents() {
        return components;
    }

    @Override

    public boolean hasComponent(C component) {
        return components.indexOf(component) != -1;
    }

    @Override
    public void addComponent(C component) {
        component.onAttachedTo(this);
        components.add(component);
    }

    @Override
    public void removeComponent(C component) {
        component.onDetachedFrom(this);
        components.remove(component);
    }

    @Override
    public C getComponentAt(int x, int y) {
        for (int i = components.size() - 1; i >= 0; i--) {
            C c = components.get(i);
            if (x >= c.getX() && x <= c.getX() + c.getWidth() &&
                    y >= c.getY() && y <= c.getY() + c.getHeight())
                return c;
        }


        return null;
    }

    @Override
    public C getFocused() {
        return this.hasFocus() ? focused : null;
    }

    @Override
    public void setFocused(Component now) {
        Component before = this.focused;

        if (before != null)
            before.onLostFocus();

        this.focused = (C) now;

        if (!hasFocus())
            attachedTo.setFocused(this); //onFocus called => now.onFocus called
        else
            now.onFocus(); //we have focus already
    }

    @Override
    public void onLostFocus() {
        super.onLostFocus();
        if (focused != null)
            focused.onLostFocus();
    }

    @Override
    public void onFocus() {
        super.onFocus();
        if (focused != null)
            focused.onFocus();
    }
}
