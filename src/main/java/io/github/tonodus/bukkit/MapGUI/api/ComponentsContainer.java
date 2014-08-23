package io.github.tonodus.bukkit.MapGUI.api;

/**
 * Created by Tonodus (http://tonodus.github.io) on 16.08.2014.
 */
public interface ComponentsContainer<C extends Component> extends InputListenerController, FocusHolder {
    void addComponent(C... components);

    void addComponent(C component);

    void removeComponent(C component);

    boolean hasComponent(C component);

    Iterable<C> getComponents();

    void invalidate();

    C getComponentAt(int x, int y);

    /**
     * If this ComponentsContainer itself is not focused, this will return null
     *
     * @return the current focused element, or null if no element is focused or the container itself has no focus
     */
    @Override
    C getFocused();

    /**
     * Set the focus to the current element and tries to request the focus itself for this ComponentsContainer
     *
     * @param component
     */
    @Override
    void setFocused(Component component);
}
