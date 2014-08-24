package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.api.Component;
import io.github.tonodus.bukkit.MapGUI.core.AbstractComponentsContainer;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 16.08.2014.
 */
public class ComponentsContainerComponent<C extends Component> extends AbstractComponentsContainer<C> {
    @Override
    public void drawAsync(Graphics2D old) {
        Graphics2D g = (Graphics2D) old.create();
        for (Component c : getComponents()) {
            g.clipRect(c.getX(), c.getY(), c.getWidth(), c.getHeight());
            c.drawAsync(g);
        }
        g.dispose();
    }

    @Override
    public void setX(int newX) {
        int diff = newX - super.getX();
        for (Component c : getComponents())
            c.setX(c.getX() + diff);
    }

    @Override
    public void setY(int newY) {
        int diff = newY - super.getY();
        for (Component c : getComponents())
            c.setY(c.getY() + diff);
    }

    @Override
    public void updateSync() {
        for (Component c : getComponents())
            c.updateSync();
    }
}
