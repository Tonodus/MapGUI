package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 16.08.2014.
 */
public interface ComponentsContainer extends InputListenerController {
    void addComponent(Component... components);

    void addComponent(Component component);

    void removeComponent(Component component);

    Iterable<Component> getComponents();

    void invalidate();
}
